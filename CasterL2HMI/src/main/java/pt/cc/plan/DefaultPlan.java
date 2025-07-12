package pt.cc.plan;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class DefaultPlan implements Plan, Cloneable {

	private int planId;
	private String planName;
	private double totalWeight;
	
	private Date planCreationTime;
	private final ArrayList<PlanProduct> plannedProducts = new ArrayList<PlanProduct>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefaultPlan() {

	}

	public DefaultPlan(int planId) {
		setPlanId(planId);
		setPlanCreationTime(new Date());
	}

	public DefaultPlan(int planId, String planName) {
		setPlanId(planId);
		setName(planName);
		setPlanCreationTime(new Date());
	}

	public DefaultPlan(int planId, Plan originalPlan) {
		setPlanId(planId);
		setPlanCreationTime(new Date());
		setPlannedProducts(((DefaultPlan) originalPlan).getPlannedProducts());
	}

	@Override
	public String getPlanName() {
		return this.planName;
	}

	public void setName(String planName) {
		this.planName = planName;
	}

	@Override
	public int getPlanId() {
		return this.planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	@Override
	public Date getPlanCreationTime() {
		return this.planCreationTime;
	}

	public void setPlanCreationTime(Date date) {
		this.planCreationTime = date;

	}

	@Override
	public int hashCode() {
		return Objects.hash(planName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultPlan other = (DefaultPlan) obj;
		return Objects.equals(planName, other.planName);
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone(); // Performs a shallow copy
	}

	public ArrayList<PlanProduct> getPlannedProducts() {
		return plannedProducts == null ? new ArrayList<PlanProduct>() : plannedProducts ;
	}

	public void setPlannedProducts(ArrayList<PlanProduct> plannedProducts) {
		this.plannedProducts.clear();
		this.plannedProducts.addAll(plannedProducts);
	}

	@Override	
	public double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DefaultPlan [planId=");
		builder.append(planId);
		builder.append(", planName=");
		builder.append(planName);
		builder.append(", totalWeight=");
		builder.append(totalWeight);
		builder.append(", planCreationTime=");
		builder.append(planCreationTime);
		builder.append(", plannedProducts=");
		builder.append(plannedProducts);
		builder.append("]");
		return builder.toString();
	}
	
	

}
