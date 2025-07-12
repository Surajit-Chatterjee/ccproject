package pt.cc.plan.persist;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import pt.cc.adapters.ICasterProperties;
import pt.cc.adapters.persistence.file.FileDataReader;
import pt.cc.plan.Plan;
import pt.cc.plan.PlanPool;

@Component("PlanDataReader")
public class PlanDataReader extends FileDataReader implements PlanReader {
	@Autowired
	private ICasterProperties casterProperties;
	@Autowired
	private PlanPool planPool;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static File file = new File("E:\\CasterL2");
	// private String rootPath =
	// Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private String appConfigPath = null;

	@PostConstruct
	public void init() {
		appConfigPath = file.getPath() + "\\" + casterProperties.getSerializerPlanFileName();
		file = new File(appConfigPath);
		planPool.getAllPlan().addAll(readPlanData());	
		System.out.println("PlanDataReader plan "+planPool.getAllPlan());
	}

	@Override
	public List<Plan> readPlanData() {
		List<Plan> updatedPlans = new ArrayList<>();
		List<Plan> planList = (List<Plan>) super.read();
		planList.forEach(plan -> {
			updatedPlans.add(plan);
		});
		return updatedPlans;
	}

	@Override
	public File getFile() {
		return file;
	}
}
