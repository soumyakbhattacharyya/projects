package com.cognizant.cloudset.model;

import java.io.Serializable;

public class InstanceStatus implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String infoURL;
	private String message;
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
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((infoURL == null) ? 0 : infoURL.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		InstanceStatus other = (InstanceStatus) obj;
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
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InstanceStatus [id=" + id + ", infoURL=" + infoURL
				+ ", message=" + message + "]";
	}
}
