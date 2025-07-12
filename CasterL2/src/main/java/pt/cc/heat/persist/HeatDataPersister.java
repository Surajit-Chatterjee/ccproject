package pt.cc.heat.persist;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import pt.cc.adapters.ICasterProperties;
import pt.cc.adapters.persistence.file.FileDataPersister;
import pt.cc.heat.Heat;
import pt.cc.heat.HeatPool;

@Component
public class HeatDataPersister extends FileDataPersister implements HeatPersister {


	@Autowired
	private ICasterProperties casterProperties;
	
	// private String rootPath =
	// Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private String appConfigPath = null;
	// private File file = null;
	private static File file = new File("E:\\CasterL2");

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private HeatPool heatPool;

	@PostConstruct
	public void init() {
		super.init();
		appConfigPath = file.getPath() + "\\" + casterProperties.getSerializerHeatFileName();
		logger.logInfo("file ser path " + appConfigPath);
		file = new File(appConfigPath);
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	@PreDestroy
	public void persist() {
		super.persist(heatPool.getHeats());
		List<Heat> heatList = heatPool.getHeats();
		StringBuilder heatBuilder = new StringBuilder("At Predestroy : Available Heat in pool   \n");
		for (Heat heat : heatList) {
			heatBuilder.append(heat.getHeatName()).append(" -- ").append(heat.getPlanName()).append("\n");
		}
		logger.logInfo(heatBuilder.toString());
		super.persist(heatList);
	}

	/*
	 * @EventListener public void heatPoolEventPerformed(HeatPoolEvent event) { if
	 * (event.getState() == HeatPoolEventState.HEAT_ADDED || event.getState() ==
	 * HeatPoolEventState.HEAT_CHANGED || event.getState() ==
	 * HeatPoolEventState.HEAT_REMOVED) {
	 * logger.logInfo(event.getHeat().getHeatName() + " Is Persisted in File " +
	 * file.getName()); persist(heatPool.getHeats()); } }
	 */

}
