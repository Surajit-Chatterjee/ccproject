package pt.cc.plan.persist;

import java.io.Serializable;
import java.util.List;

import pt.cc.plan.Plan;

public interface PlanReader extends Serializable {

	public List<Plan> readPlanData();
}
