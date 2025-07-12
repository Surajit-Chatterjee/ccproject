package pt.cc.heat;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.List;

import pt.cc.plan.Plan;

public interface HeatPool extends Serializable,Remote {
	
	
	public void addNewHeat(Heat heat) throws IllegalArgumentException;

	public Heat createNewHeat();

	public List<Heat> getHeats();
	
	public List<Heat> findHeat(Integer planId);
	
	public Heat findForUpdate(int heatId);
	
	public Heat findHeat(int heatId);
	
	public void commit(Heat updHeat,String commitReason);
	
	public void deleteHeat(Heat heat);
	
	public void exchengeHeatPlan(Heat heat,Plan plan);
}
	