package pt.cc.plan.persist;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import pt.cc.adapters.ICasterProperties;
import pt.cc.adapters.persistence.file.FileDataPersister;
import pt.cc.log.ILogger;
import pt.cc.plan.Plan;
import pt.cc.plan.PlanPool;

@Component
public class PlanDataPersister extends FileDataPersister implements PlanPersister {

	@Autowired
	private ICasterProperties casterProperties;

	private static File file = new File("E:\\CasterL2");
	// private String rootPath =
	// Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private String appConfigPath = null;
	// private File file = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Autowired
	private PlanPool planPool;

	@PostConstruct
	public void init() {
		super.init();
		appConfigPath = file.getPath() + "\\" + casterProperties.getSerializerPlanFileName();
		file = new File(appConfigPath);
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	@PreDestroy
	public void persist() {
		List<Plan> planList = planPool.getAllPlan();
		StringBuilder planBuilder = new StringBuilder("At Predestroy : Available plan in pool   \n");
		for (Plan plan : planList) {
			planBuilder.append(plan.getPlanName()).append("\n");
		}
		logger.logInfo(planBuilder.toString());

		super.persist(planList);
	}

	/*
	 * @PreDestroy //@EventListener public void planPoolEventPerformed(PlanPoolEvent
	 * event) { if (event.getState() == PlanPoolEventState.NEW_PLAN_ADDED ||
	 * event.getState() == PlanPoolEventState.PLAN_REMOVED) {
	 * logger.logInfo(getClass().getEnclosingMethod()+" -> " +
	 * event.getPlan().getPlanName() + " Is Persisted in File " + file.getName());
	 * persist(planPool.getAllPlan()); } }
	 */

}
