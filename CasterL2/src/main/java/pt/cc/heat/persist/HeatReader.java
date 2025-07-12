package pt.cc.heat.persist;

import java.io.Serializable;
import java.util.List;

import pt.cc.heat.Heat;

public interface HeatReader extends Serializable{
	public List<Heat> readHeatData();
}
