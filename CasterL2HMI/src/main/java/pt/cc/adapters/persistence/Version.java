package pt.cc.adapters.persistence;

import java.io.Serializable;

public class Version implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;

	/**
	 * @param id
	 */
	public Version(String id) {
		super();
		this.id = id;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Version other = (Version) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
