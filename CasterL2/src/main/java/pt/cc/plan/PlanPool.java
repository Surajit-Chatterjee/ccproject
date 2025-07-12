package pt.cc.plan;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.List;

public interface PlanPool extends Remote, Serializable {
	
	public DefaultPlan createPlan();
	public DefaultPlan createPlan(String planName);
	public List<Plan> getAllPlan();
	public void addNewPlan(Plan plan);
	public void removePlan(Plan plan);
	
	public Plan findPlan(String planName);// todo use plan filter later
	
	public Plan findPlanForUpdate(String planName);
	
	public Plan findPlanForUpdate(int planId);
	
	public void commitPlan(Plan plan);
	
	
	public void copyPlan(Plan originalPlan,String newPlanName);
	
	public PlanProduct createPlanProduct();
	

}
