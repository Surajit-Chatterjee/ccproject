/**
 * 
 */
package pt.cc.plan.factory;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pt.cc.plan.DefaultPlan;
import pt.cc.plan.DefaultPlanProduct;
import pt.cc.plan.Plan;
import pt.cc.plan.PlanProduct;
import pt.cc.util.generator.IdGeneratorAdapter;

/**
 * 
 */
@Component
public class DefaultPlanFactory implements PlanFactory {

	@Autowired
	private IdGeneratorAdapter proopertyReader;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public DefaultPlan createPlan() {
		DefaultPlan plan = new DefaultPlan(generatePlanId().intValue());
		plan.setPlanCreationTime(new Date());
		return plan;
	}

	@Override
	public Integer generatePlanId() {
		Integer planId = proopertyReader.getIntegerPropertyValue("plan.planId");
		return planId;
	}

	@Override
	public Integer generatePlanProductId() {
		Integer planProductId = proopertyReader.getIntegerPropertyValue("plan.planProductId");
		return planProductId;
	}

	@Override
	public Plan createPlan(String planName) {
		return new DefaultPlan(generatePlanId().intValue(), planName);
	}

	@Override
	public Plan copyPlan(Plan originalPlan) {
		return new DefaultPlan(generatePlanId().intValue(), originalPlan);
	}

	@Override
	public PlanProduct createPlanProduct() {
		return new DefaultPlanProduct(generatePlanProductId().intValue());
	}
}
