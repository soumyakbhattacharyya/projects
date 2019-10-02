package com.cognizant.cloudset.core;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

/**
 * JPA class representing CloudUser The corresponding mapping table is
 * cloud_user
 */

public class CloudUser implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer cloudUserId;

	private String cloudUserName;

	private String emailId;

	private Boolean activeFlag;

	private Date dateCreated;

	private Date dateLastUpdated;

	private CloudProvider cloudProvider;

	private CloudRegion cloudRegion;

	private Project project;

	private Set<CloudAccount> cloudAccountss;

	/**
	 * 
	 * @generated
	 */
	public CloudUser() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setCloudUserId(final Integer cloudUserId) {
		this.cloudUserId = cloudUserId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getCloudUserId() {
		return this.cloudUserId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setCloudUserName(final String cloudUserName) {
		this.cloudUserName = cloudUserName;
	}

	/**
	 * 
	 * @generated
	 */
	public String getCloudUserName() {
		return this.cloudUserName;
	}

	/**
	 * 
	 * @generated
	 */

	public void setEmailId(final String emailId) {
		this.emailId = emailId;
	}

	/**
	 * 
	 * @generated
	 */
	public String getEmailId() {
		return this.emailId;
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
	 * 
	 * @generated
	 */
	public Project getProject() {
		return this.project;
	}

	/**
	 * 
	 * @generated
	 */
	public void setProject(final Project project) {
		this.project = project;

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
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final CloudUser that) {
		setCloudUserId(that.getCloudUserId());
		setCloudUserName(that.getCloudUserName());
		setEmailId(that.getEmailId());
		setActiveFlag(that.getActiveFlag());
		setDateCreated(that.getDateCreated());
		setDateLastUpdated(that.getDateLastUpdated());
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
				+ ((cloudUserId == null) ? 0 : cloudUserId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("cloudUserId=[").append(cloudUserId).append("] ");
		buffer.append("cloudUserName=[").append(cloudUserName).append("] ");
		buffer.append("emailId=[").append(emailId).append("] ");
		buffer.append("activeFlag=[").append(activeFlag).append("] ");
		buffer.append("dateCreated=[").append(dateCreated).append("] ");
		buffer.append("dateLastUpdated=[").append(dateLastUpdated).append("] ");

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
		final CloudUser other = (CloudUser) obj;
		if (cloudUserId == null) {
			if (other.cloudUserId != null) {
				return false;
			}
		} else if (!cloudUserId.equals(other.cloudUserId)) {
			return false;
		}
		return true;
	}
}
