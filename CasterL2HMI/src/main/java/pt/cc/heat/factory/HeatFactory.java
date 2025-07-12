package pt.cc.heat.factory;

import java.io.Serializable;

import pt.cc.heat.Heat;

public interface HeatFactory extends Serializable {
	public Heat createHeat();

	public Integer generateHeatId();
	
	public Heat createHeat(int heatId);
}
