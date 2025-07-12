package pt.cc.plan;

import java.util.Objects;

@SuppressWarnings("serial")
public class DefaultPlanProduct implements PlanProduct {

	/**
	 * 
	 */

	private int planProductId;
	private double aimLength;
	private double minLength;
	private double maxLength;
	private String name;
	private double weight;
	private double width;
	private double thickness;
	private int strandNumber;
	private int planId;

	public DefaultPlanProduct() {

	}

	public DefaultPlanProduct(int planProductId) {
		this.planProductId = planProductId;
	}

	public DefaultPlanProduct(DefaultPlanProduct originalPlanProduct) {
		setAimLength(aimLength);
		setMaxLength(maxLength);
		setMinLength(minLength);
		setWeight(weight);
		setWidth(width);
		setThickness(thickness);

	}

	@Override
	public int getStrandNumber() {
		return strandNumber;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	/**
	 * @param strandNumber the strandNumber to set
	 */
	public void setStrandNumber(int strandNumber) {
		this.strandNumber = strandNumber;
	}

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * @return the thickness
	 */
	public double getThickness() {
		return thickness;
	}

	/**
	 * @param thickness the thickness to set
	 */
	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	/**
	 * @param planProductId the planProductId to set
	 */
	public void setPlanProductId(int planProductId) {
		this.planProductId = planProductId;
	}

	/**
	 * @param aimLength the lengthAim to set
	 */
	public void setAimLength(double aimLength) {
		this.aimLength = aimLength;
	}

	/**
	 * @param minLength the minLength to set
	 */
	public void setMinLength(double minLength) {
		this.minLength = minLength;
	}

	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(double maxLength) {
		this.maxLength = maxLength;
	}

	@Override
	public double getAimLength() {
		return aimLength;
	}

	@Override
	public double getMinLength() {
		return minLength;
	}

	@Override
	public double getMaxLength() {
		return maxLength;
	}

	@Override
	public int getPlanProductId() {
		return planProductId;
	}

	@Override
	public int getPlanId() {
		return 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DefaultPlanProduct [planProductId=");
		builder.append(planProductId);
		builder.append(", aimLength=");
		builder.append(aimLength);
		builder.append(", minLength=");
		builder.append(minLength);
		builder.append(", maxLength=");
		builder.append(maxLength);
		builder.append(", name=");
		builder.append(name);
		builder.append(", weight=");
		builder.append(weight);
		builder.append(", width=");
		builder.append(width);
		builder.append(", thickness=");
		builder.append(thickness);
		builder.append(", strandNumber=");
		builder.append(strandNumber);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultPlanProduct other = (DefaultPlanProduct) obj;
		return planId == other.planId && planProductId == other.planProductId && strandNumber == other.strandNumber;
	}

}
