/**
 *
 */
package com.cognizant.formit.model;

/**
 * Template info for spawning a new VM
 *
 */
public class VMTemplate {

	private String mi;
	private String description;
	private String zone;
	private String securityGroups;
	private int sshPort;
	/* Profile links to an execution environment */
	private String profileId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof VMTemplate)) {
			return false;
		}
		VMTemplate other = (VMTemplate) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (mi == null) {
			if (other.mi != null) {
				return false;
			}
		} else if (!mi.equals(other.mi)) {
			return false;
		}
		if (profileId == null) {
			if (other.profileId != null) {
				return false;
			}
		} else if (!profileId.equals(other.profileId)) {
			return false;
		}
		if (securityGroups == null) {
			if (other.securityGroups != null) {
				return false;
			}
		} else if (!securityGroups.equals(other.securityGroups)) {
			return false;
		}
		if (sshPort != other.sshPort) {
			return false;
		}
		if (zone == null) {
			if (other.zone != null) {
				return false;
			}
		} else if (!zone.equals(other.zone)) {
			return false;
		}
		return true;
	}

	/**
	 * @param mi
	 * @param description
	 * @param zone
	 * @param securityGroups
	 * @param sshPort
	 * @param profileId
	 */
	private VMTemplate(String mi, String description, String zone,
			String securityGroups, int sshPort, String profileId) {
		super();
		this.mi = mi;
		this.description = description;
		this.zone = zone;
		this.securityGroups = securityGroups;
		this.sshPort = sshPort;
		this.profileId = profileId;
	}

	/**
	 *
	 */
	private VMTemplate() {
		super();
	}

	public static VMTemplate _new(String mi, String description, String zone,
			String securityGroups, int sshPort, String profileId) {
		return new VMTemplate(mi, description, zone,
				securityGroups, sshPort, profileId);
	}

	/**
	 *
	 */
	public static VMTemplate _new() {
		return new VMTemplate();
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the mi
	 */
	public String getMi() {
		return mi;
	}

	/**
	 * @return the profileId
	 */
	public String getProfileId() {
		return profileId;
	}

	/**
	 * @return the securityGroups
	 */
	public String getSecurityGroups() {
		return securityGroups;
	}

	/**
	 * @return the sshPort
	 */
	public int getSshPort() {
		return sshPort;
	}

	/**
	 * @return the zone
	 */
	public String getZone() {
		return zone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((mi == null) ? 0 : mi.hashCode());
		result = prime * result
				+ ((profileId == null) ? 0 : profileId.hashCode());
		result = prime * result
				+ ((securityGroups == null) ? 0 : securityGroups.hashCode());
		result = prime * result + sshPort;
		result = prime * result + ((zone == null) ? 0 : zone.hashCode());
		return result;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param mi the mi to set
	 */
	public void setMi(String mi) {
		this.mi = mi;
	}

	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	/**
	 * @param securityGroups the securityGroups to set
	 */
	public void setSecurityGroups(String securityGroups) {
		this.securityGroups = securityGroups;
	}

	/**
	 * @param sshPort the sshPort to set
	 */
	public void setSshPort(int sshPort) {
		this.sshPort = sshPort;
	}

	/**
	 * @param zone the zone to set
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VMTemplate [mi=" + mi + ", description=" + description
				+ ", zone=" + zone + ", securityGroups=" + securityGroups
				+ ", sshPort=" + sshPort + ", profileId=" + profileId + "]";
	}
}
