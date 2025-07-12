/**
 * 
 */
package pt.cc.adapters;

/**
 * 
 */
public interface ICasterProperties {

	public int getCasterId();

	public String getCasterName();

	public String getSerializerPlanFileName();

	public String getSerializerHeatFileName();

	public String getPropertyConfig();

	public boolean isLotPlanningUsed();

	public boolean isHeatBasedPlanning();

	public int getNrOfStrand();

	public String getCssFileName();

	public String getControllerViewPath();

	public String getFxmlSuffix();

}
