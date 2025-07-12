package pt.cc.adapters.persistence;

import java.io.Serializable;

public interface DataReader extends Serializable{
	public Object read();
}
