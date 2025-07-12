package pt.cc.util.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import pt.cc.adapters.GenericAbstractAdapter;
import pt.cc.adapters.ICasterProperties;

@Component
@ComponentScan(basePackages = "pt.cc")
public class IdGeneratorAdapter extends GenericAbstractAdapter implements IdGenerator {

	@Autowired
	private ICasterProperties casterProperties;
	private static Properties properties = new Properties();
	private File propertyFile = null;
	private String rootPath = "E:\\CasterL2\\";
	private String appConfigPath = null;

	@PostConstruct
	public void init() {
		super.init();
		appConfigPath = rootPath + casterProperties.getPropertyConfig();
		logger.logInfo("appConfigPath " + appConfigPath);
		propertyFile = new File(appConfigPath);
		loadProperties();
	}

	private void setPropertyValue(String key, Object value) {
		if (value == null) {
			logger.logInfo(value + " value can not be put into Properties");
			return;
		}
		properties.put(key, value);
		try (FileOutputStream outputStream = new FileOutputStream(propertyFile)) {
			properties.store(outputStream, "Updated");
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer getIntegerPropertyValue(String key) {
		Object object = properties.get(key);
		// logger.logInfo("object " + object + " class " + object.getClass());

		if (object != null && object instanceof String) {
			Integer value = Integer.valueOf(Integer.parseInt((String) object));
			int updatedValue = value.intValue() + 1;
			setPropertyValue(key, String.valueOf(updatedValue));
			return Integer.valueOf(updatedValue);
		}
		return Integer.valueOf(1);
	}

	@Override
	public Double getDoublePropertyValue(String key) {
		return null;
	}

	@Override
	public String getStringPropertyValue(String key) {
		return null;
	}

	@Override
	public Date getDatePropertyValue(String key) {
		return null;
	}

	private void loadProperties() {
		InputStream input = null;
		try {
			input = new FileInputStream(propertyFile);

			// Load the properties file
			properties.load(input);
			properties.forEach((key, value) -> {
				logger.logInfo("Properties Key : " + key + " Value " + value);
			});

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean checkFileExist(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return true;
	}

}
