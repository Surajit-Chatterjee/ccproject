package pt.cc.plan;

import java.io.Serializable;

public interface PlanProduct extends Serializable {
	public static final long serialVersionUID = -3574960824092378031L;
	
	public int getPlanId();
	public int getStrandNumber();

	/**
	 * 
	 * @return name of the plan product
	 */
	public String getName();

	/**
	 * 
	 * @return width
	 */

	public double getWidth();

	/**
	 * 
	 * @return thickness
	 */
	public double getThickness();

	/**
	 * 
	 * @return weight
	 */
	public double getWeight();

	/**
	 * @return the lengthAim
	 */
	public double getAimLength();

	/**
	 * @return the minLength
	 */
	public double getMinLength();

	/**
	 * @return the maxLength
	 */
	public double getMaxLength();

	/**
	 * 
	 * @return planProductId
	 */
	public int getPlanProductId();

}
