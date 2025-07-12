package pt.cc.adapters.persistence.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import pt.cc.adapters.GenericAbstractAdapter;
import pt.cc.adapters.persistence.DataReader;
import pt.cc.log.ILogger;

@Component
public class DBDataReader extends GenericAbstractAdapter implements DataReader {
	private static File file = new File("E:\\CasterL2\\heats.ser");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		super.init();
	}

	@Override
	public List<Object> read() {
		ArrayList<Object> objects = new ArrayList<Object>();
		if (checkFileExist(file)) {
			ObjectInputStream objectinputstream = null;
			try {
				FileInputStream streamIn = new FileInputStream(file);
				objectinputstream = new ObjectInputStream(streamIn);
				Object o = objectinputstream.readObject();
				try {
					while (true) {
						// we should leave this loop with an exception
						o = objectinputstream.readObject();
						objects.add(o);
					}
				} catch (Exception ex) {
					// ex.printStackTrace();
				}

				// logger.logInfo(recordList.get(i));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (objectinputstream != null) {
					try {
						objectinputstream.close();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		logger.logInfo("Read from the file " + file.getName() + " Total Objects " + objects);
		return objects;

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
