package com.cognizant.cloudset.model;

import java.io.Serializable;

public class Cloud implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String description;
	private String logoURL;
	private String infoURL;
	
	public Cloud() {
		
	}
	
	/**
	 * @return the cloudID
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param cloudID the cloudID to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the cloudName
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param cloudName the cloudName to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the logoURL
	 */
	public String getLogoURL() {
		return logoURL;
	}
	/**
	 * @param logoURL the logoURL to set
	 */
	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
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
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((infoURL == null) ? 0 : infoURL.hashCode());
		result = prime * result + ((logoURL == null) ? 0 : logoURL.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cloud [id=" + id + ", name=" + name + ", description="
				+ description + ", logoURL=" + logoURL + ", infoURL=" + infoURL
				+ "]";
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
		Cloud other = (Cloud) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
		if (logoURL == null) {
			if (other.logoURL != null)
				return false;
		} else if (!logoURL.equals(other.logoURL))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
