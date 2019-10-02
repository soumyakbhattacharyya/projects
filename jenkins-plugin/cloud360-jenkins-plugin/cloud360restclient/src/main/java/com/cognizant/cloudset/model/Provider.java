package com.cognizant.cloudset.model;

import java.io.Serializable;

public class Provider implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String providerType;
	private String cloudId;
	private String infoURL;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the providerType
	 */
	public String getProviderType() {
		return providerType;
	}
	/**
	 * @param providerType the providerType to set
	 */
	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}
	/**
	 * @return the cloudId
	 */
	public String getCloudId() {
		return cloudId;
	}
	/**
	 * @param cloudId the cloudId to set
	 */
	public void setCloudId(String cloudId) {
		this.cloudId = cloudId;
	}
	/**
	 * @return the infoURL
	 */
	public String getInfoURL() {
		return infoURL;
	}
	/**
	 * @param infoURL the infoURL to set
	 */
	public void setInfoURL(String infoURL) {
		this.infoURL = infoURL;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cloudId == null) ? 0 : cloudId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((infoURL == null) ? 0 : infoURL.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((providerType == null) ? 0 : providerType.hashCode());
		return result;
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
		Provider other = (Provider) obj;
		if (cloudId == null) {
			if (other.cloudId != null)
				return false;
		} else if (!cloudId.equals(other.cloudId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (infoURL == null) {
			if (other.infoURL != null)
				return false;
		} else if (!infoURL.equals(other.infoURL))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (providerType == null) {
			if (other.providerType != null)
				return false;
		} else if (!providerType.equals(other.providerType))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Provider [id=" + id + ", name=" + name + ", providerType="
				+ providerType + ", cloudId=" + cloudId + ", infoURL="
				+ infoURL + "]";
	}
	
}
