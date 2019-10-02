package com.cognizant.cloudset.bean;

import java.io.Serializable;

/**
 * The primary key class for the UserRoleProjectServiceCatalogMapId Pojo.
 * 
 */

public class UserRoleProjectServiceCatalogMapId implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer projectId;

	private Integer roleId;

	private Integer serviceId;
	private Integer userId;

	private Integer cloudAccountId;

	/**
	 * 
	 * @generated
	 */
	public UserRoleProjectServiceCatalogMapId() {
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
	public void setRoleId(final Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getRoleId() {
		return this.roleId;
	}

	/**
	 * 
	 * @generated
	 */
	public void setServiceId(final Integer serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getServiceId() {
		return this.serviceId;
	}

	/**
	 * 
	 * @generated
	 */
	public void setUserId(final Integer userId) {
		this.userId = userId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getUserId() {
		return this.userId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getCloudAccountId() {
		return cloudAccountId;
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
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserRoleProjectServiceCatalogMapId)) {
			return false;
		}
		UserRoleProjectServiceCatalogMapId castOther = (UserRoleProjectServiceCatalogMapId) other;
		return (this.projectId.equals(castOther.projectId)) &&

		(this.roleId.equals(castOther.roleId)) &&

		(this.serviceId.equals(castOther.serviceId)) &&

		(this.userId.equals(castOther.userId) &&

		(this.cloudAccountId.equals(castOther.cloudAccountId)))

		;

	}

	/**
	 * @generated
	 * 
	 */
	@Override
	public int hashCode() {
		int result = 1;
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
		buffer.append("roleId=[").append(roleId).append("] ");
		buffer.append("serviceId=[").append(serviceId).append("] ");
		buffer.append("userId=[").append(userId).append("] ");
		buffer.append("cloudAccountId=[").append(cloudAccountId).append("] ");

		return buffer.toString();
	}
}
