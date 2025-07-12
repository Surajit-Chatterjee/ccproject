package pt.cc.adapters.persistence.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import pt.cc.adapters.GenericAbstractAdapter;
import pt.cc.adapters.persistence.DataReader;
import pt.cc.adapters.persistence.Version;

@Component
public abstract class FileDataReader extends GenericAbstractAdapter implements DataReader {

	private File file;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		super.init();
	}

	@Override
	public Object read() {
		ArrayList<Object> objects = new ArrayList<Object>();
		if (checkFileExist(getFile())) {
			ObjectInputStream objectinputstream = null;
			try {
				FileInputStream streamIn = new FileInputStream(getFile());
				objectinputstream = new ObjectInputStream(streamIn);
				Object o = objectinputstream.readObject();
				try {
					// while (true) {
					// we should leave this loop with an exception
					o = objectinputstream.readObject();
					if (o == null) {
						logger.logInfo("Recoverer " + getClass() + " could not find any data in file ");
						return null;
					}
					if (o instanceof Version) {
						logger.logInfo(getClass() + ": reading back objects archived on " + " with version " + o);
						Object objectToRecover = objectinputstream.readObject();
						if (objectToRecover == null) {
							logger.logInfo(getClass() + ": ObjectToRecover in file " + getFile()
									+ " is null. File seems to be empty. No recovery necessary");

						}
						return objectToRecover;
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			} catch (Exception e) {
				logger.logInfo("Error Occured as " + e.getMessage());
				// e.printStackTrace();
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
		return objects;
	}

	public File getFile() {
		return file;
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
