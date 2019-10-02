package com.cognizant.cloudset.core;

import java.io.Serializable;
import java.util.Date;

/**
 * JPA class representing UserPortalRoleProjectServiceMap The corresponding
 * mapping table is user_portal_role_project_service_map
 */

public class UserPortalRoleProjectServiceMap implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private UserPortalRoleProjectServiceMapId userPortalRoleProjectServiceMapId;

	private String authority;

	private Date allocationDate;

	private Byte hasExpired;

	private Date expiryDate;

	private ServiceCatalog serviceCatalog;

	private Project project;

	/**
	 * 
	 * @generated
	 */
	public UserPortalRoleProjectServiceMap() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setUserPortalRoleProjectServiceMapId(
			final UserPortalRoleProjectServiceMapId userPortalRoleProjectServiceMapId) {
		this.userPortalRoleProjectServiceMapId = userPortalRoleProjectServiceMapId;
	}

	/**
	 * 
	 * @generated
	 */
	public UserPortalRoleProjectServiceMapId getUserPortalRoleProjectServiceMapId() {
		return this.userPortalRoleProjectServiceMapId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	/**
	 * 
	 * @generated
	 */
	public String getAuthority() {
		return this.authority;
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
			this.userPortalRoleProjectServiceMapId
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
			this.userPortalRoleProjectServiceMapId.setProjectId(this.project
					.getProjectId());
		}

	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final UserPortalRoleProjectServiceMap that) {
		setUserPortalRoleProjectServiceMapId(that
				.getUserPortalRoleProjectServiceMapId());
		setAuthority(that.getAuthority());
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
				+ ((userPortalRoleProjectServiceMapId == null) ? 0
						: userPortalRoleProjectServiceMapId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("userPortalRoleProjectServiceMapId=[")
				.append(userPortalRoleProjectServiceMapId).append("] ");
		buffer.append("authority=[").append(authority).append("] ");
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
		final UserPortalRoleProjectServiceMap other = (UserPortalRoleProjectServiceMap) obj;
		if (userPortalRoleProjectServiceMapId == null) {
			if (other.userPortalRoleProjectServiceMapId != null) {
				return false;
			}
		} else if (!userPortalRoleProjectServiceMapId
				.equals(other.userPortalRoleProjectServiceMapId)) {
			return false;
		}
		return true;
	}
}
