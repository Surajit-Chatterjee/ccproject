package pt.cc.heat;

import java.io.Serializable;
import java.util.Date;

public interface Heat extends Serializable, Cloneable {

	public void setPlanName(String planName);

	public String getPlanName();

	public void setPlanId(Integer planId);

	public Integer getPlanId();

	public void setHeatId(int heatId);

	public int getHeatId();

	public HeatState getHeatState();

	public void setHeatState(HeatState heatState);

	public Date getCreationTime();

	public void setCreationTime(Date date);

	public String getHeatName();

	public String getSteelGrade();

	public void setHeatName(String heatName);

	public void setSteelGrade(String steelGrade);

	public Date getOpenTime();

	public void setOpenTime(Date openTime);

	public double getNetWeight();

	public void setNetWeight(double netWeight);

}
