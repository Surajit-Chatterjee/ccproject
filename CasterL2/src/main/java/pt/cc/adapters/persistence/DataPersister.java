package pt.cc.adapters.persistence;

import java.io.Serializable;

public interface DataPersister extends Serializable{
	public void persist(Object object);
}
