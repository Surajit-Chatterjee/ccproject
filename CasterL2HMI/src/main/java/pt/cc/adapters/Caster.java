package pt.cc.adapters;

import java.io.Serializable;
import java.rmi.Remote;

public interface Caster extends Remote,Serializable {

	/**
	 * @return the id if the Caster (unique for all casters of a customer)
	 */
	public int getCasterId();

	/**
	 * @return the name of the Caster
	 */
	public String getCasterName();
}
