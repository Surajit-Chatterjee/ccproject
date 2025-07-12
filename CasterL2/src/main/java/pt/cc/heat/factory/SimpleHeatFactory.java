package pt.cc.heat.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pt.cc.heat.BaseHeat;
import pt.cc.heat.Heat;
import pt.cc.util.generator.IdGeneratorAdapter;

@Component
public class SimpleHeatFactory implements HeatFactory {

	@Autowired
	private IdGeneratorAdapter proopertyReader;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Heat createHeat() {
		return new BaseHeat(generateHeatId().intValue());
	}

	@Override
	public Heat createHeat(int heatId) {
		return new BaseHeat(generateHeatId().intValue());
	}

	@Override
	public Integer generateHeatId() {
		Integer heatId = proopertyReader.getIntegerPropertyValue("heat.heatId");
		return heatId;
	}

}
