package pt.cc.heat.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import pt.cc.adapters.AbstractInitializer;
import pt.cc.adapters.Caster;
import pt.cc.adapters.ICasterProperties;
import pt.cc.annotations.CheckObject;
import pt.cc.heat.BaseHeat;
import pt.cc.heat.Heat;
import pt.cc.heat.HeatPool;
import pt.cc.heat.HeatPoolEvent;
import pt.cc.heat.HeatPoolEventState;
import pt.cc.heat.factory.HeatFactory;
import pt.cc.plan.Plan;
import pt.cc.plan.PlanEvent;
import pt.cc.plan.PlanPoolEventState;

@EnableAsync
@Component("HeatPool")
public class DefaultHeatPool extends AbstractInitializer implements HeatPool {
	private List<Heat> heatList = new CopyOnWriteArrayList<Heat>();

	@Autowired
	private ICasterProperties casterProperties;
	@Autowired
	private HeatFactory heatFactory;
	@CheckObject
	@Autowired
	private Caster caster;
	@Autowired
	private ApplicationEventPublisher heatPoolEventPublisher;
	final transient ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	@Override
	public void init() {
		super.init();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void addNewHeat(Heat heat) throws IllegalArgumentException {
		if (!heatList.contains(heat)) {
			heatList.add(heat);
			logger.logInfo("new heat " + heat.getHeatName() + " Created ");
			HeatPoolEvent event = new HeatPoolEvent(this, HeatPoolEventState.HEAT_ADDED, heat);
			heatPoolEventPublisher.publishEvent(event);// 2 separate jvm can not be notified
		}
	}

	@Override
	public void deleteHeat(Heat heat) {
		final WriteLock writeLock = this.lock.writeLock();
		writeLock.lock();
		try {
			boolean removed = getHeats().remove(heat);
			logger.logInfo("Heat " + heat.getHeatName() + " removed from Pool " + removed);
			HeatPoolEvent event = new HeatPoolEvent(this, HeatPoolEventState.HEAT_REMOVED, heat);
			heatPoolEventPublisher.publishEvent(event);
		} finally {
			writeLock.unlock();
		}

	}

	@Override
	public Heat createNewHeat() {
		return heatFactory.createHeat();
	}

	@Override
	public List<Heat> getHeats() {
		return heatList;
	}

	@Override
	public List<Heat> findHeat(Integer planId) {
		// logger.logInfo("findHeat(Integer planId) " + heatList + " plan Id " +
		// planId);
		if (!heatList.isEmpty()) {
			return heatList.stream()
					.filter(heat -> heat.getPlanId() != null && heat.getPlanId().intValue() == planId.intValue())
					.collect(Collectors.toList());

		}

		return new ArrayList<>();
	}

	@Override
	public Heat findForUpdate(int heatId) {
		Heat updatebleHeat = null;
		final WriteLock writeLock = this.lock.writeLock();
		writeLock.lock();
		try {
			if (!heatList.isEmpty()) {
				Optional<Heat> optionalHeat = heatList.stream().filter(heat -> heat.getHeatId() == heatId).findAny();
				if (optionalHeat.isPresent()) {
					updatebleHeat = optionalHeat.get();
				}
			}
		} finally {
			writeLock.unlock();
		}

		return updatebleHeat;
	}

	@Override
	public Heat findHeat(int heatId) {
		Optional<Heat> optionalHeat = heatList.stream().filter(heat -> heat.getHeatId() == heatId).findFirst();
		if (optionalHeat.isPresent()) {
			BaseHeat originalHeat = (BaseHeat) optionalHeat.get();
			try {
				return (Heat) originalHeat.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void exchengeHeatPlan(Heat selectedHeat, Plan selectedNewPlan) {
		if (selectedNewPlan != null) {
			System.out.println("casterProperties.isHeatBasedPlanning() "+casterProperties.isHeatBasedPlanning());
			if (casterProperties.isHeatBasedPlanning()) {
				List<Heat> heats = getHeats();

				for (Heat heat : heats) {
					if (heat.getPlanId() != null && (heat.getPlanId().intValue() == selectedNewPlan.getPlanId())) {
						Heat updHeat = findForUpdate(heat.getHeatId());
						updHeat.setPlanId(null);
						updHeat.setPlanName(null);
						commit(updHeat, selectedHeat.getPlanName() + " Plan is UnAssigned from the Heat "
								+ selectedHeat.getHeatName());
						break;
					}
				}
			}
			try {
				Heat updHeat = findForUpdate(selectedHeat.getHeatId());
				updHeat.setPlanId(Integer.valueOf(selectedNewPlan.getPlanId()));
				updHeat.setPlanName(selectedNewPlan.getPlanName());
				commit(updHeat, updHeat.getHeatName() + " assigned with plan " + selectedNewPlan.getPlanName());

			} catch (Exception e) {
				logger.logError("Problem in exchanging heat plan ", e);
			}

		} else {
			logger.logError("Selected Plan is null ");
		}

	}

	@EventListener
	public void planPoolEventPerformed(PlanEvent event) {
		if (event.getState() == PlanPoolEventState.PLAN_REMOVED) {
			Plan deletedPlan = event.getPlan();
			logger.logInfo("planPoolEventPerformed plan remove " + deletedPlan);
			List<Heat> heats = findHeat(Integer.valueOf(deletedPlan.getPlanId()));
			if (heats != null && !heats.isEmpty()) {
				heats.forEach(heat -> {
					logger.logInfo("Heat " + heat.getHeatName() + " will be modified no plan attached");
					Heat updHeat = findForUpdate(heat.getHeatId());
					if (updHeat != null) {
						updHeat.setPlanId(null);
						updHeat.setPlanName(null);
						commit(updHeat, deletedPlan.getPlanName() + " Plan is UnAssigned from the Heat "
								+ updHeat.getHeatName());
					}

				});

			}
		}
	}

	@Override
	public void commit(Heat updHeat, String commitReason) {
		final WriteLock writeLock = this.lock.writeLock();
		writeLock.lock();
		try {
			int index = getHeats().indexOf(updHeat);
			// logger.logInfo("index of heat " + updHeat.getHeatName() + " is " + index);
			getHeats().set(index, updHeat);
			logger.logInfo("Heat " + updHeat.getHeatName() + " Committed Successfully, Reason is " + commitReason);
			HeatPoolEvent heatPoolEvent = new HeatPoolEvent(this, HeatPoolEventState.HEAT_CHANGED, updHeat);
			heatPoolEventPublisher.publishEvent(heatPoolEvent);
		} finally {
			writeLock.unlock();
		}

	}

}
