package com.cognizant.nodes;

import java.io.Serializable;

import com.cognizant.monitor.State;

public class ClusterNode implements Serializable{
	
	private static final long serialVersionUID = 7526471155622776147L;
	
	private String name;
	private String ipAddress;
	private long totalMem;
	private long freeMem;
	
	private State state;
	
	public ClusterNode(String name, String ipAddress, long totalMem,
			long freeMem) {
		super();
		this.name = name;
		this.ipAddress = ipAddress;
		this.totalMem = totalMem;
		this.freeMem = freeMem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public long getTotalMem() {
		return totalMem;
	}

	public void setTotalMem(long totalMem) {
		this.totalMem = totalMem;
	}

	public long getFreeMem() {
		return freeMem;
	}

	public void setFreeMem(long freeMem) {
		this.freeMem = freeMem;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClusterNode [name=" + name + ", ipAddress=" + ipAddress
				+ ", totalMem=" + totalMem + ", freeMem=" + freeMem + ", state=" + state+ "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClusterNode other = (ClusterNode) obj;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
