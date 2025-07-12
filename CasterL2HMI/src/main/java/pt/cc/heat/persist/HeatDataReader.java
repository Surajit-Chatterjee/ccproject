package pt.cc.heat.persist;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import pt.cc.adapters.ICasterProperties;
import pt.cc.adapters.persistence.file.FileDataReader;
import pt.cc.heat.Heat;
import pt.cc.heat.HeatPool;

@Component("HeatDataReader")
public class HeatDataReader extends FileDataReader implements HeatReader {

	@Autowired
	private ICasterProperties casterProperties;
	@Autowired
	private HeatPool heatPool;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static File file = new File("E:\\CasterL2");
	// private String rootPath =
	// Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private String appConfigPath = null;
	// private File file = null;

	@PostConstruct
	public void init() {
		appConfigPath = file.getPath() + "\\" + casterProperties.getSerializerHeatFileName();
		file = new File(appConfigPath);
		heatPool.getHeats().addAll(readHeatData());
	}

	@Override
	public List<Heat> readHeatData() {
		List<Heat> updateHeats = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Heat> heatList = (List<Heat>) super.read();
		heatList.forEach(heat -> {
			updateHeats.add(heat);
		});
		return updateHeats;
	}

	@Override
	public Object read() {
		return super.read();
	}

	@Override
	public File getFile() {
		return file;
	}
}
