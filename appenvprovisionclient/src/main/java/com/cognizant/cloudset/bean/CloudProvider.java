package com.cognizant.cloudset.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/** 
 * JPA class representing CloudProvider 
 * The corresponding mapping table is cloud_provider 
 */
@XmlRootElement(name = "CloudProvider")
public class CloudProvider implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer providerId;

		private String shortDesc;
	
	private String cloudName;
	
	private String displayName;

		private String providerUrl;

	private String longDesc;

	private Boolean activeFlag;

	private Date dateCreated;

	private Date dateLastUpdated;

	private Integer lastUsedBy;


	private Set<CloudAccount> cloudAccountss;


	private Set<CloudUser> cloudUserss;

   /**
	 *
	 * @generated
	 */
	public CloudProvider() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setProviderId(final Integer providerId) {
		this.providerId = providerId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getProviderId() {
		return this.providerId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setShortDesc(final String shortDesc) {
		this.shortDesc = shortDesc;
	}

	/**
	 * 
	 * @generated
	 */
	public String getShortDesc() {
		return this.shortDesc;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 
	 * @generated
	 */

	public void setProviderUrl(final String providerUrl) {
		this.providerUrl = providerUrl;
	}

	/**
	 * 
	 * @generated
	 */
	public String getProviderUrl() {
		return this.providerUrl;
	}

	/**
	 * 
	 * @generated
	 */

	public void setLongDesc(final String longDesc) {
		this.longDesc = longDesc;
	}

	/**
	 * 
	 * @generated
	 */
	public String getLongDesc() {
		return this.longDesc;
	}

	/**
	 * 
	 * @generated
	 */

	public void setActiveFlag(final Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * 
	 * @generated
	 */
	public Boolean getActiveFlag() {
		return this.activeFlag;
	}

	/**
	 * 
	 * @generated
	 */

	public void setDateCreated(final Date dateCreated) {
		if (dateCreated != null)
			this.dateCreated = (Date) dateCreated.clone();
		else
			this.dateCreated = null;
	}

	/**
	 * 
	 * @generated
	 */
	public Date getDateCreated() {
		if (this.dateCreated != null)
			return (Date) this.dateCreated.clone();
		else
			return null;
	}

	/**
	 * 
	 * @generated
	 */

	public void setDateLastUpdated(final Date dateLastUpdated) {
		if (dateLastUpdated != null)
			this.dateLastUpdated = (Date) dateLastUpdated.clone();
		else
			this.dateLastUpdated = null;
	}

	/**
	 * 
	 * @generated
	 */
	public Date getDateLastUpdated() {
		if (this.dateLastUpdated != null)
			return (Date) this.dateLastUpdated.clone();
		else
			return null;
	}

	/**
	 * 
	 * @generated
	 */

	public void setLastUsedBy(final Integer lastUsedBy) {
		this.lastUsedBy = lastUsedBy;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getLastUsedBy() {
		return this.lastUsedBy;
	}

	/**
	 * 
	 * @generated
	 */
	 @XmlTransient
	public Set<CloudAccount> getCloudAccountss() {
		return this.cloudAccountss;
	}

	/**
	 * 
	 * @generated
	 */
	public void setCloudAccountss(final Set<CloudAccount> cloudAccountss) {
		this.cloudAccountss = cloudAccountss;
	}

	
	/**
	 * 
	 * @generated
	 */
	 @XmlTransient
	public Set<CloudUser> getCloudUserss() {
		return this.cloudUserss;
	}

	/**
	 * 
	 * @generated
	 */
	public void setCloudUserss(final Set<CloudUser> cloudUserss) {
		this.cloudUserss = cloudUserss;
	}
	
	public String getCloudName() {
		return cloudName;
	}

	public void setCloudName(String cloudName) {
		this.cloudName = cloudName;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final CloudProvider that) {
		setProviderId(that.getProviderId());
		setShortDesc(that.getShortDesc());
		setProviderUrl(that.getProviderUrl());
		setLongDesc(that.getLongDesc());
		setActiveFlag(that.getActiveFlag());
		setDateCreated(that.getDateCreated());
		setDateLastUpdated(that.getDateLastUpdated());
		setLastUsedBy(that.getLastUsedBy());
	}

	/**
	 * @generated
	 * 
	 */
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((providerId == null) ? 0 : providerId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("providerId=[").append(providerId).append("] ");
		buffer.append("shortDesc=[").append(shortDesc).append("] ");
		buffer.append("providerUrl=[").append(providerUrl).append("] ");
		buffer.append("longDesc=[").append(longDesc).append("] ");
		buffer.append("activeFlag=[").append(activeFlag).append("] ");
		buffer.append("dateCreated=[").append(dateCreated).append("] ");
		buffer.append("dateLastUpdated=[").append(dateLastUpdated).append("] ");
		buffer.append("lastUsedBy=[").append(lastUsedBy).append("] ");

		return buffer.toString();
	}

	/**
	 * @generated
	 * 
	 */
	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CloudProvider other = (CloudProvider) obj;
		if (providerId == null) {
			if (other.providerId != null) {
				return false;
			}
		} else if (!providerId.equals(other.providerId)) {
			return false;
		}
		return true;
	}
}
