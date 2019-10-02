package com.cognizant.cloudset.bean;

import java.io.Serializable;

/** 
 * The primary key class for the RoleServiceItemCatalogMapId Pojo. 
 *
 */

public class RoleServiceItemCatalogMapId implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;


	private Integer serviceId;
	private Integer serviceItemId;
	private Integer roleId;

	/**
	 *
	 * @generated
	 */
	public RoleServiceItemCatalogMapId() {
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
	public void setServiceItemId(final Integer serviceItemId) {
		this.serviceItemId = serviceItemId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getServiceItemId() {
		return this.serviceItemId;
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
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RoleServiceItemCatalogMapId)) {
			return false;
		}
		RoleServiceItemCatalogMapId castOther = (RoleServiceItemCatalogMapId) other;
		return (this.serviceId.equals(castOther.serviceId)) &&

		(this.serviceItemId.equals(castOther.serviceItemId)) &&

		(this.roleId.equals(castOther.roleId))

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

		buffer.append("serviceId=[").append(serviceId).append("] ");
		buffer.append("serviceItemId=[").append(serviceItemId).append("] ");
		buffer.append("roleId=[").append(roleId).append("] ");

		return buffer.toString();
	}
}
