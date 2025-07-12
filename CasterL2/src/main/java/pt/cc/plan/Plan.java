/**
 * 
 */
package pt.cc.plan;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 */
public interface Plan extends Serializable{

	public String getPlanName();
	public int getPlanId();	
	public Date getPlanCreationTime();
	public double getTotalWeight();
	
	
	//public void setPlanCreationTime(Date date);

}
