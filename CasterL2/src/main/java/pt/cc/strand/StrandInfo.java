package pt.cc.strand;

import java.io.Serializable;

public class StrandInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9222828032034540666L;
	
	private int strandNr;
	private int strandName;
	public StrandInfo(int strandNr, int strandName) {
		super();
		this.strandNr = strandNr;
		this.strandName = strandName;
	}
	/**
	 * @return the strandNr
	 */
	public int getStrandNr() {
		return strandNr;
	}
	/**
	 * @return the strandName
	 */
	public int getStrandName() {
		return strandName;
	}
	
	
	
}
