package pt.cc.heat;

import java.io.Serializable;
import java.util.Date;

public class HeatPlanWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String heatName;
	private String steelGrade;
	private Date creationDate;
	private HeatState heatState;
	private String planName;

	/**
	 * @return the heatName
	 */
	public String getHeatName() {
		return heatName;
	}

	/**
	 * @param heatName the heatName to set
	 */
	public void setHeatName(String heatName) {
		this.heatName = heatName;
	}

	/**
	 * @return the steelGrade
	 */
	public String getSteelGrade() {
		return steelGrade;
	}

	/**
	 * @param steelGrade the steelGrade to set
	 */
	public void setSteelGrade(String steelGrade) {
		this.steelGrade = steelGrade;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the heatState
	 */
	public HeatState getHeatState() {
		return heatState;
	}

	/**
	 * @param heatState the heatState to set
	 */
	public void setHeatState(HeatState heatState) {
		this.heatState = heatState;
	}

	/**
	 * @return the planName
	 */
	public String getPlanName() {
		return planName;
	}

	/**
	 * @param planName the planName to set
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}

}
