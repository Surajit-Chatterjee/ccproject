package pt.cc.plan.factory;

import java.io.Serializable;

import pt.cc.plan.Plan;
import pt.cc.plan.PlanProduct;

public interface PlanFactory extends Serializable {
	public Plan createPlan();

	public Plan createPlan(String planName);

	public Integer generatePlanId();

	public Integer generatePlanProductId();

	public Plan copyPlan(Plan priginalPlan);

	public PlanProduct createPlanProduct();
}
