package pt.cc.plan;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import pt.cc.adapters.GenericAbstractAdapter;
import pt.cc.exceptions.PlanIsNullException;
import pt.cc.plan.factory.PlanFactory;

@Component("PlanPool")
public class SimplePlanPool extends GenericAbstractAdapter implements PlanPool {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5432382657787214747L;
	private final List<Plan> plans = new CopyOnWriteArrayList<>();
	final transient ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	@Autowired
	private PlanFactory planFactory;
	@Autowired
	private ApplicationEventPublisher planPoolEventPublisher;

	@PostConstruct
	public void init() {
		super.init();
	}

	@Override
	public PlanProduct createPlanProduct() {
		return planFactory.createPlanProduct();
	}

	@Override
	public DefaultPlan createPlan() {
		DefaultPlan plan = (DefaultPlan) planFactory.createPlan();
		return plan;
	}

	@Override
	public synchronized void copyPlan(Plan originalPlan, String newPlanName) {
		try {
			DefaultPlan copiedPlan = (DefaultPlan) planFactory.copyPlan(originalPlan);
			// TODO SET more properties of original plan to this plan
			copiedPlan.setName(newPlanName);
			addNewPlan(copiedPlan);
			logger.logInfo("originalDefPlan plan " + originalPlan + " new Plan copiedPlan " + copiedPlan);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Plan> getAllPlan() {
		return plans;
	}

	@Override
	public void addNewPlan(Plan newPlan) {
		final WriteLock writeLock = this.lock.writeLock();
		writeLock.lock();
		try {
			if (!plans.contains(newPlan)) {
				plans.add(newPlan);
				// notifies all
				planPoolEventPublisher.publishEvent(new PlanEvent(this, PlanPoolEventState.NEW_PLAN_ADDED, newPlan));
				logger.logInfo("Plan " + newPlan.getPlanName() + " Added Successfully");
			} else {
				logger.logError(newPlan.getPlanName() + " Plan Already Exists");
			}
		} catch (Exception e) {
			logger.logError("", e);

		} finally {
			writeLock.unlock();
		}

	}

	@Override
	public DefaultPlan createPlan(String planName) {
		return (DefaultPlan) planFactory.createPlan(planName);
	}

	@Override
	public Plan findPlan(String planName) {
		Optional<Plan> plan = plans.stream().filter(p -> p.getPlanName().equalsIgnoreCase(planName)).findAny();
		if (plan.isPresent()) {
			try {
				return (DefaultPlan) ((DefaultPlan) plan.get()).clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Plan findPlanForUpdate(String planName) {
		for (Plan plan : plans) {
			if (plan.getPlanName().equalsIgnoreCase(planName)) {
				System.out.println("match plan " + plan.getPlanName() + " -- " + planName);
				return plan;
			}
		}
		return null;
	}

	@Override
	public void removePlan(Plan plan) {
		if (plan == null) {
			throw new PlanIsNullException("Plan is Null , check");
		}
		logger.logInfo("removePlan " + plan.toString());
		if (plan != null) {
			plans.remove(plan);
			planPoolEventPublisher.publishEvent(new PlanEvent(this, PlanPoolEventState.PLAN_REMOVED, plan));
		}

	}

	@Override
	public Plan findPlanForUpdate(int planId) {
		for (Plan plan : plans) {
			if (plan.getPlanId() == planId) {
				//System.out.println("match plan "+plan.getPlanName()+" -- "+planName);			
				return plan;
			}
		}
		return null;
	}

	@Override
	public void commitPlan(Plan plan) {
		final WriteLock writeLock = this.lock.writeLock();
		writeLock.lock();
		try {
			int index = getAllPlan().indexOf(plan);
			logger.logInfo("index of plan " + plan.getPlanName() + " is " + index);
			getAllPlan().set(index, plan);
			logger.logInfo("Plan " + plan.getPlanName() + " Committed Successfully");
			planPoolEventPublisher.publishEvent(new PlanEvent(this, PlanPoolEventState.PLAN_CHANGED, plan));
		} catch (Exception e) {
			logger.logError("Exception in committing plan " + plan.getPlanName(), e);
		} finally {
			writeLock.unlock();
		}

	}

}
