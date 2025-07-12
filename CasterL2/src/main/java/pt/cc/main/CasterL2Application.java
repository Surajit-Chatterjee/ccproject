package pt.cc.main;

import java.rmi.Remote;
import java.util.concurrent.CountDownLatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import pt.cc.heat.HeatPool;

@ComponentScan(basePackages = "pt.cc")
@SpringBootApplication
public class CasterL2Application implements Remote {

	public static void main(String[] args) {
		CasterL2Application cl = new CasterL2Application();
		ApplicationContext ctx = SpringApplication.run(CasterL2Application.class, args);

		/*
		 * for (String name : ctx.getBeanDefinitionNames()) {
		 * logger.logInfo("Loaded bean " + name); }
		 */

		HeatPool heatPool = (HeatPool) ctx.getBean("HeatPool");
		Thread rmiThread = null;
		try {
			rmiThread = new Thread(new RMIRegistryServer(heatPool,ctx));
			rmiThread.start();
			System.out.println("RMI Server Started");
		} catch (Exception e) {
			rmiThread.stop();
		}
		
		try {
			new CountDownLatch(1).await();
		} catch (InterruptedException e) {		
			e.printStackTrace();
		}
	}

}
