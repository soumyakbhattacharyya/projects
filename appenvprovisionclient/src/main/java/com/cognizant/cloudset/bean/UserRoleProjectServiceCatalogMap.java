package com.cognizant.cloudset.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * JPA class representing UserRoleProjectServiceCatalogMap The corresponding
 * mapping table is user_role_project_service_catalog_map
 */

public class UserRoleProjectServiceCatalogMap implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private UserRoleProjectServiceCatalogMapId userRoleProjectServiceCatalogMapId;

	private Date allocationDate;

	private Byte hasExpired;

	private Date expiryDate;

	private User user;

	private Role role;

	private ServiceCatalog serviceCatalog;

	private Project project;

	private CloudAccount cloudAccount;

	/**
	 * 
	 * @generated
	 */
	public UserRoleProjectServiceCatalogMap() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setUserRoleProjectServiceCatalogMapId(
			final UserRoleProjectServiceCatalogMapId userRoleProjectServiceCatalogMapId) {
		this.userRoleProjectServiceCatalogMapId = userRoleProjectServiceCatalogMapId;
	}

	/**
	 * 
	 * @generated
	 */
	public UserRoleProjectServiceCatalogMapId getUserRoleProjectServiceCatalogMapId() {
		return this.userRoleProjectServiceCatalogMapId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setAllocationDate(final Date allocationDate) {
		if (allocationDate != null)
			this.allocationDate = (Date) allocationDate.clone();
		else
			this.allocationDate = null;
	}

	/**
	 * 
	 * @generated
	 */
	public Date getAllocationDate() {
		if (this.allocationDate != null)
			return (Date) this.allocationDate.clone();
		else
			return null;
	}

	/**
	 * 
	 * @generated
	 */

	public void setHasExpired(final Byte hasExpired) {
		this.hasExpired = hasExpired;
	}

	/**
	 * 
	 * @generated
	 */
	public Byte getHasExpired() {
		return this.hasExpired;
	}

	/**
	 * 
	 * @generated
	 */

	public void setExpiryDate(final Date expiryDate) {
		if (expiryDate != null)
			this.expiryDate = (Date) expiryDate.clone();
		else
			this.expiryDate = null;
	}

	/**
	 * 
	 * @generated
	 */
	public Date getExpiryDate() {
		if (this.expiryDate != null)
			return (Date) this.expiryDate.clone();
		else
			return null;
	}

	/**
	 * 
	 * @generated
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * 
	 * @generated
	 */
	public void setUser(final User user) {
		this.user = user;
		if (this.user != null && this.user.getUserId() != null) {
			this.userRoleProjectServiceCatalogMapId.setUserId(this.user
					.getUserId());
		}

	}

	/**
	 * 
	 * @generated
	 */
	public Role getRole() {
		return this.role;
	}

	/**
	 * 
	 * @generated
	 */
	public void setRole(final Role role) {
		this.role = role;
		if (this.role != null && this.role.getRoleId() != null) {
			this.userRoleProjectServiceCatalogMapId.setRoleId(this.role
					.getRoleId());
		}

	}

	/**
	 * 
	 * @generated
	 */
	public ServiceCatalog getServiceCatalog() {
		return this.serviceCatalog;
	}

	/**
	 * 
	 * @generated
	 */
	public void setServiceCatalog(final ServiceCatalog serviceCatalog) {
		this.serviceCatalog = serviceCatalog;
		if (this.serviceCatalog != null
				&& this.serviceCatalog.getServiceId() != null) {
			this.userRoleProjectServiceCatalogMapId
					.setServiceId(this.serviceCatalog.getServiceId());
		}

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
		if (this.project != null && this.project.getProjectId() != null) {
			this.userRoleProjectServiceCatalogMapId.setProjectId(this.project
					.getProjectId());
		}

	}

	/**
	 * 
	 * @generated
	 */
	public CloudAccount getCloudAccount() {
		return this.cloudAccount;
	}

	/**
	 * 
	 * @generated
	 */
	public void setCloudAccount(CloudAccount cloudAccount) {
		this.cloudAccount = cloudAccount;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final UserRoleProjectServiceCatalogMap that) {
		setUserRoleProjectServiceCatalogMapId(that
				.getUserRoleProjectServiceCatalogMapId());
		setAllocationDate(that.getAllocationDate());
		setHasExpired(that.getHasExpired());
		setExpiryDate(that.getExpiryDate());
	}

	/**
	 * @generated
	 * 
	 */
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((userRoleProjectServiceCatalogMapId == null) ? 0
						: userRoleProjectServiceCatalogMapId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("userRoleProjectServiceCatalogMapId=[")
				.append(userRoleProjectServiceCatalogMapId).append("] ");
		buffer.append("allocationDate=[").append(allocationDate).append("] ");
		buffer.append("hasExpired=[").append(hasExpired).append("] ");
		buffer.append("expiryDate=[").append(expiryDate).append("] ");

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
		final UserRoleProjectServiceCatalogMap other = (UserRoleProjectServiceCatalogMap) obj;
		if (userRoleProjectServiceCatalogMapId == null) {
			if (other.userRoleProjectServiceCatalogMapId != null) {
				return false;
			}
		} else if (!userRoleProjectServiceCatalogMapId
				.equals(other.userRoleProjectServiceCatalogMapId)) {
			return false;
		}
		return true;
	}
}
