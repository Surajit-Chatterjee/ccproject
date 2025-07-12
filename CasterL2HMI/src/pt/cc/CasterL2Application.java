package pt.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import pt.cc.heat.Heat;
import pt.cc.heat.HeatPool;

@SpringBootApplication
public class CasterL2Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CasterL2Application.class, args);
		
		for(String name : ctx.getBeanDefinitionNames()) {
			logger.logInfo("Loaded bean "+name);
		}
		
		HeatPool heatPool = ctx.getBean("HeatPool");
		Heat heat =heatPool.createNewHeat();
		heat.setHeatName("H1010");
		logger.logInfo("Heat Name is "+heat.getHeatName());
		
		
		logger.logInfo();
	}

}
