package com.cognizant.cloudset.bean;

import java.io.Serializable;

/** 
 * The primary key class for the UserPortalRoleProjectServiceMapId Pojo. 
 *
 */

public class UserPortalRoleProjectServiceMapId implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer projectId;
	private Integer serviceId;
	private String userId;

	/**
	 *
	 * @generated
	 */
	public UserPortalRoleProjectServiceMapId() {
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
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	/**
	 * 
	 * @generated
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 
	 * @generated
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserPortalRoleProjectServiceMapId)) {
			return false;
		}
		UserPortalRoleProjectServiceMapId castOther = (UserPortalRoleProjectServiceMapId) other;
		return (this.projectId.equals(castOther.projectId)) &&

		(this.serviceId.equals(castOther.serviceId)) &&

		(this.userId.equals(castOther.userId))

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
		buffer.append("serviceId=[").append(serviceId).append("] ");
		buffer.append("userId=[").append(userId).append("] ");

		return buffer.toString();
	}
}
