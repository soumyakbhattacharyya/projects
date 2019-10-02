package com.cognizant.cloudset.bean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * JPA class representing CloudAccount The corresponding mapping table is
 * cloud_account
 */

@XmlRootElement(name = "CloudAccount")
public class CloudAccount implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer cloudAccountId;

	private String externalCloudAccountId;
	private String credentialUrl;

	private Boolean activeFlag;

	private String publicCredentials;

	private String privateCredentials;

	private String shortDescription;

	private String longDescription;

	private Date dateCreated;

	private Date dateLastUpdated;

	private String eucalyptusUser;

	private CloudProvider cloudProvider;

	private CloudUser cloudUser;

	private CloudRegion cloudRegion;

	private byte[] securityKey;

	private byte[] securityCert;

	/**
	 * 
	 * @generated
	 */
	public CloudAccount() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setCloudAccountId(final Integer cloudAccountId) {
		this.cloudAccountId = cloudAccountId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getCloudAccountId() {
		return this.cloudAccountId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setExternalCloudAccountId(final String externalCloudAccountId) {
		this.externalCloudAccountId = externalCloudAccountId;
	}

	/**
	 * 
	 * @generated
	 */
	public String getExternalCloudAccountId() {
		return this.externalCloudAccountId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setCredentialUrl(final String credentialUrl) {
		this.credentialUrl = credentialUrl;
	}

	/**
	 * 
	 * @generated
	 */
	public String getCredentialUrl() {
		return this.credentialUrl;
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

	public void setPublicCredentials(final String publicCredentials) {
		this.publicCredentials = publicCredentials;
	}

	/**
	 * 
	 * @generated
	 */
	public String getPublicCredentials() {
		return this.publicCredentials;
	}

	/**
	 * 
	 * @generated
	 */

	public void setPrivateCredentials(final String privateCredentials) {
		this.privateCredentials = privateCredentials;
	}

	/**
	 * 
	 * @generated
	 */
	public String getPrivateCredentials() {
		return this.privateCredentials;
	}

	/**
	 * 
	 * @generated
	 */

	public void setShortDescription(final String shortDescription) {
		this.shortDescription = shortDescription;
	}

	/**
	 * 
	 * @generated
	 */
	public String getShortDescription() {
		return this.shortDescription;
	}

	/**
	 * 
	 * @generated
	 */

	public void setLongDescription(final String longDescription) {
		this.longDescription = longDescription;
	}

	/**
	 * 
	 * @generated
	 */
	public String getLongDescription() {
		return this.longDescription;
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

	public void setEucalyptusUser(final String eucalyptusUser) {
		this.eucalyptusUser = eucalyptusUser;
	}

	/**
	 * 
	 * @generated
	 */
	public String getEucalyptusUser() {
		return this.eucalyptusUser;
	}

	/**
	 * 
	 * @generated
	 */
	@XmlTransient
	public CloudProvider getCloudProvider() {
		return this.cloudProvider;
	}

	/**
	 * 
	 * @generated
	 */
	public void setCloudProvider(final CloudProvider cloudProvider) {
		this.cloudProvider = cloudProvider;

	}

	/**
	 * 
	 * @generated
	 */
	@XmlTransient
	public CloudUser getCloudUser() {
		return this.cloudUser;
	}

	/**
	 * 
	 * @generated
	 */
	public void setCloudUser(final CloudUser cloudUser) {
		this.cloudUser = cloudUser;

	}

	/**
	 * 
	 * @generated
	 */
	@XmlTransient
	public CloudRegion getCloudRegion() {
		return this.cloudRegion;
	}

	/**
	 * 
	 * @generated
	 */
	public void setCloudRegion(final CloudRegion cloudRegion) {
		this.cloudRegion = cloudRegion;

	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final CloudAccount that) {
		setCloudAccountId(that.getCloudAccountId());
		setExternalCloudAccountId(that.getExternalCloudAccountId());
		setCredentialUrl(that.getCredentialUrl());
		setActiveFlag(that.getActiveFlag());
		setPublicCredentials(that.getPublicCredentials());
		setPrivateCredentials(that.getPrivateCredentials());
		setShortDescription(that.getShortDescription());
		setLongDescription(that.getLongDescription());
		setDateCreated(that.getDateCreated());
		setDateLastUpdated(that.getDateLastUpdated());
		setEucalyptusUser(that.getEucalyptusUser());
	}

	/**
	 * @generated
	 * 
	 */
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cloudAccountId == null) ? 0 : cloudAccountId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("cloudAccountId=[").append(cloudAccountId).append("] ");
		buffer.append("externalCloudAccountId=[")
				.append(externalCloudAccountId).append("] ");
		buffer.append("credentialUrl=[").append(credentialUrl).append("] ");
		buffer.append("activeFlag=[").append(activeFlag).append("] ");
		buffer.append("publicCredentials=[").append(publicCredentials)
				.append("] ");
		buffer.append("privateCredentials=[").append(privateCredentials)
				.append("] ");
		buffer.append("shortDescription=[").append(shortDescription)
				.append("] ");
		buffer.append("longDescription=[").append(longDescription).append("] ");
		buffer.append("dateCreated=[").append(dateCreated).append("] ");
		buffer.append("dateLastUpdated=[").append(dateLastUpdated).append("] ");
		buffer.append("eucalyptusUser=[").append(eucalyptusUser).append("] ");

		return buffer.toString();
	}

	public byte[] getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(byte[] securityKey) {
		this.securityKey = securityKey;
	}

	public byte[] getSecurityCert() {
		return securityCert;
	}

	public void setSecurityCert(byte[] securityCert) {
		this.securityCert = securityCert;
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
		final CloudAccount other = (CloudAccount) obj;
		if (cloudAccountId == null) {
			if (other.cloudAccountId != null) {
				return false;
			}
		} else if (!cloudAccountId.equals(other.cloudAccountId)) {
			return false;
		}
		return true;
	}
}
