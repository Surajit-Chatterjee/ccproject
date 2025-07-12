package pt.cc.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.springframework.context.ApplicationContext;

import pt.cc.heat.HeatPool;

public class RMIRegistryServer implements Runnable {
	
	private HeatPool heatPool;
	private ApplicationContext serverContext;
	
	public RMIRegistryServer(HeatPool heatPool,ApplicationContext serverContext) {
		this.heatPool = heatPool;
		this.serverContext = serverContext;
	}
	public void run() {
	//	while(true) {
		//	Registry registry;
		//	try {
			//	registry = LocateRegistry.createRegistry(1099);
		//		registry.bind("RegistryServer", serverContext);
		//	} catch (Exception e) {
				// TODO Auto-generated catch block
		//		e.printStackTrace();
		//	} 
		}
	//}

}
