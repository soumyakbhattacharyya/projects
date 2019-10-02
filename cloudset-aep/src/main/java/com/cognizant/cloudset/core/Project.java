package com.cognizant.cloudset.core;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * JPA class representing Project The corresponding mapping table is project
 */

public class Project implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer projectId;

	private String name;

	private String primaryContactId;

	private String primaryContactEmail;

	private String externalId;
	private Account account;

	private Set<UserRoleProjectServiceCatalogMap> userRoleProjectServiceCatalogMapss;

	private Set<User> userCollection;

	private Set<ProjectServiceSubscription> projectServiceSubscriptionss;

	private String accAuthenticationKey;

	private Date startDate;

	private Date endDate;

	private boolean hasExpired;

	/**
	 * 
	 * @generated
	 */
	public Project() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setProjectId(final Integer projectId) {
		this.projectId = projectId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getProjectId() {
		return this.projectId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * 
	 * @generated
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @generated
	 */

	public void setPrimaryContactId(final String primaryContactId) {
		this.primaryContactId = primaryContactId;
	}

	/**
	 * 
	 * @generated
	 */
	public String getPrimaryContactId() {
		return this.primaryContactId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setPrimaryContactEmail(final String primaryContactEmail) {
		this.primaryContactEmail = primaryContactEmail;
	}

	/**
	 * 
	 * @generated
	 */
	public String getPrimaryContactEmail() {
		return this.primaryContactEmail;
	}

	/**
	 * 
	 * @generated
	 */
	public Account getAccount() {
		return this.account;
	}

	/**
	 * 
	 * @generated
	 */
	public void setAccount(final Account account) {
		this.account = account;

	}

	/**
	 * 
	 * @generated
	 */
	public Set<UserRoleProjectServiceCatalogMap> getUserRoleProjectServiceCatalogMapss() {
		return this.userRoleProjectServiceCatalogMapss;
	}

	/**
	 * 
	 * @generated
	 */
	public void setUserRoleProjectServiceCatalogMapss(
			final Set<UserRoleProjectServiceCatalogMap> userRoleProjectServiceCatalogMapss) {
		this.userRoleProjectServiceCatalogMapss = userRoleProjectServiceCatalogMapss;
	}

	public Set<User> getUserCollection() {
		return userCollection;
	}

	public void setUserCollection(Set<User> userCollection) {
		this.userCollection = userCollection;
	}

	// public Set<ServiceCatalog> getServiceCatalogCollection() {
	// return serviceCatalogCollection;
	// }
	//
	// public void setServiceCatalogCollection(Set<ServiceCatalog>
	// serviceCatalogCollection) {
	// this.serviceCatalogCollection = serviceCatalogCollection;
	// }

	/**
	 * 
	 * @generated
	 */
	public String getExternalId() {
		return externalId;
	}

	/**
	 * 
	 * @generated
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	/**
	 * 
	 * @generated
	 */
	public Set<ProjectServiceSubscription> getProjectServiceSubscriptionss() {
		return projectServiceSubscriptionss;
	}

	/**
	 * 
	 * @generated
	 */
	public void setProjectServiceSubscriptionss(
			Set<ProjectServiceSubscription> projectServiceSubscriptionss) {
		this.projectServiceSubscriptionss = projectServiceSubscriptionss;
	}

	/**
	 * 
	 * @generated
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 
	 * @generated
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 
	 * @generated
	 */
	public boolean isHasExpired() {
		return hasExpired;
	}

	/**
	 * 
	 * @generated
	 */
	public void setHasExpired(boolean hasExpired) {
		this.hasExpired = hasExpired;
	}

	/**
	 * 
	 * @generated
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * 
	 * @generated
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final Project that) {
		setProjectId(that.getProjectId());
		setName(that.getName());
		setPrimaryContactId(that.getPrimaryContactId());
		setPrimaryContactEmail(that.getPrimaryContactEmail());
		setExternalId(that.getExternalId());
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
				+ ((projectId == null) ? 0 : projectId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("projectId=[").append(projectId).append("] ");
		buffer.append("name=[").append(name).append("] ");
		buffer.append("primaryContactId=[").append(primaryContactId)
				.append("] ");
		buffer.append("externalId=[").append(externalId).append("] ");
		buffer.append("primaryContactEmail=[").append(primaryContactEmail)
				.append("] ");

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
		final Project other = (Project) obj;
		if (projectId == null) {
			if (other.projectId != null) {
				return false;
			}
		} else if (!projectId.equals(other.projectId)) {
			return false;
		}
		return true;
	}

	public String getAccAuthenticationKey() {
		return accAuthenticationKey;
	}

	public void setAccAuthenticationKey(String accAuthenticationKey) {
		this.accAuthenticationKey = accAuthenticationKey;
	}
}
