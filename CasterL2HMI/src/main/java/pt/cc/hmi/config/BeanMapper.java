package pt.cc.hmi.config;

import java.util.HashMap;
import java.util.Map;

public class BeanMapper {
	private Map<String, Object> beanMapper = new HashMap<>();

	public BeanMapper() {		
	}

	public void setBeanMap(String name, Object bean) {
		beanMapper.put(name, bean);
	}

	public Object getBean(String name) {
		return beanMapper.get(name);
	}

}
