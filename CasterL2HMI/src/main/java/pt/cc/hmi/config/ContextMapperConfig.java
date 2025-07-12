package pt.cc.hmi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextMapperConfig {

	public ContextMapperConfig() {
	}

	@Bean(name = "beanMapper")
	public BeanMapper configureBeanMapper() {
		return new BeanMapper();
	}

	@Bean(name = "viewLoader")
	public ViewLoader configureViewLoader() {
		return new ViewLoader();
	}

}
