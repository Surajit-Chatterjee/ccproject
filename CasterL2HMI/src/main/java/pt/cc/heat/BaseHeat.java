package pt.cc.heat;

import java.util.Date;
import java.util.Objects;

public class BaseHeat implements Heat {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String heatName;
	private String steelGrade;
	private Date openTime;
	private Date creationTime;
	private HeatState heatState;
	private int heatId;
	private double netWeight;
//	private double grossWeight;

	// plan info in heat
	private Integer planId;
	private String planName;

	public BaseHeat() {

	}

	public BaseHeat(int heatId) {
		setHeatId(heatId);
		setHeatState(HeatState.PLAN);
		setCreationTime(new Date());
	}

	public BaseHeat(String heatName, String steelGrade) {
		super();
		this.heatName = heatName;
		this.steelGrade = steelGrade;
		setCreationTime(new Date());
	}

	@Override
	public String getHeatName() {
		return heatName;
	}

	@Override
	public String getSteelGrade() {
		return steelGrade;
	}

	@Override
	public void setHeatName(String heatName) {
		this.heatName = heatName;
	}

	@Override
	public void setSteelGrade(String steelGrade) {
		this.steelGrade = steelGrade;

	}

	@Override
	public int hashCode() {
		return Objects.hash(heatName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseHeat other = (BaseHeat) obj;
		return Objects.equals(heatName, other.heatName);
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone(); // Performs a shallow copy
	}

	@Override
	public HeatState getHeatState() {
		return heatState;
	}

	@Override
	public void setHeatState(HeatState heatState) {
		this.heatState = heatState;

	}

	@Override
	public void setHeatId(int heatId) {
		this.heatId = heatId;
	}

	@Override
	public int getHeatId() {
		return this.heatId;
	}

	@Override
	public void setPlanId(Integer planId) {
		this.planId = planId;

	}

	@Override
	public Integer getPlanId() {
		return this.planId;
	}

	@Override
	public void setPlanName(String planName) {
		this.planName = planName;

	}

	@Override
	public String getPlanName() {
		return this.planName;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(double netWeight) {
		this.netWeight = netWeight;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseHeat [heatName=");
		builder.append(heatName);
		builder.append(", netWeight=");
		builder.append(netWeight);
		builder.append(", steelGrade=");
		builder.append(steelGrade);
		builder.append(", openTime=");
		builder.append(openTime);
		builder.append(", heatState=");
		builder.append(heatState);
		builder.append(", heatId=");
		builder.append(heatId);
		builder.append(", planId=");
		builder.append(planId);
		builder.append(", planName=");
		builder.append(planName);
		builder.append("]");
		return builder.toString();
	}

}
