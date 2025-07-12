package pt.cc.util.generator;

import java.util.Date;

public interface IdGenerator {	
	//public void setPropertyValue(String key,Object value);	
	public Integer getIntegerPropertyValue(String key);
	public Double getDoublePropertyValue(String key);
	public String getStringPropertyValue(String key);
	public Date getDatePropertyValue(String key);
}
