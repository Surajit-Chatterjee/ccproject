/**
 * 
 */
package pt.cc.adapters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 */
@Component("casterProperties")
public class CasterPropertiesAdapter extends GenericAbstractAdapter implements ICasterProperties {

	@Value("${caster.id}")
	private int casterId;
	@Value("${caster.name}")
	private String casterName;
	@Value("${serializer-file-heat}")
	private String serializerHeatFileName;
	@Value("${serializer-file-plan}")
	private String serializerPlanFileName;
	@Value("${property-file}")
	private String propertyConfig;
	@Value("${lot-planning-used}")
	private boolean lotPlanningUsed;
	@Value("${heat-based-plan-used}")
	private boolean heatBasedPlanningUsed;
	@Value("${nr-of-strand}")
	private int nrOfStrand;
	@Value("${css-resource-name}")
	private String cssFileName;
	@Value("${view-path}")
	private String controllerViewPath;
	@Value("${fxml-suffix}")
	private String fxmlSuffix;

	@Override
	public String getFxmlSuffix() {
		return fxmlSuffix;
	}

	@Override
	public String getControllerViewPath() {
		return controllerViewPath;
	}

	@Override
	public String getCssFileName() {
		return cssFileName;
	}

	@Override
	public int getCasterId() {
		return casterId;
	}

	@Override
	public String getCasterName() {
		return casterName;
	}

	@Override
	public int getNrOfStrand() {
		return nrOfStrand;
	}

	@Override
	public String getSerializerPlanFileName() {
		return serializerPlanFileName;
	}

	@Override
	public String getSerializerHeatFileName() {
		return serializerHeatFileName;
	}

	@Override
	public String getPropertyConfig() {
		return propertyConfig;
	}

	@Override
	public boolean isLotPlanningUsed() {
		return lotPlanningUsed;
	}

	@Override
	public boolean isHeatBasedPlanning() {
		return heatBasedPlanningUsed;
	}
}
