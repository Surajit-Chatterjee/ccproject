package pt.cc.adapters.persistence.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

import jakarta.annotation.PostConstruct;
import pt.cc.adapters.GenericAbstractAdapter;
import pt.cc.adapters.persistence.DataPersister;
import pt.cc.adapters.persistence.Version;

public abstract class FileDataPersister  extends GenericAbstractAdapter implements DataPersister {

	private static final long serialVersionUID = 1L;
	private Version version = new Version("1.0");
	private File file;	

	@PostConstruct
	public void init() {
		super.init();
	}

	@Override
	public void persist(Object object) {
		if (createFileIfNotExist(getFile())) {			
			ObjectOutputStream oos = null;
			try {
				oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(getFile())));
				oos.writeObject(new Date());
				oos.writeObject(version);
				oos.writeObject(object);
				logger.logInfo(object + " Written to file");
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (oos != null) {
					try {
						oos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	private boolean createFileIfNotExist(File file) {
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

	public File getFile() {
		return file;
	}


}
