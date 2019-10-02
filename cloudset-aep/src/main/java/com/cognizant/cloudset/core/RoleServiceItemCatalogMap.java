package com.cognizant.cloudset.core;

import java.io.Serializable;
import java.util.Date;

/** 
 * JPA class representing RoleServiceItemCatalogMap 
 * The corresponding mapping table is role_service_item_catalog_map 
 */

public class RoleServiceItemCatalogMap implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private RoleServiceItemCatalogMapId roleServiceItemCatalogMapId;

	private Date allocationDate;

	private Byte hasExpired;

	private Date expiryDate;

	private Role role;

	private ServiceItemCatalog serviceItemCatalog;

	private ServiceCatalog serviceCatalog;

	/**
	 *
	 * @generated
	 */
	public RoleServiceItemCatalogMap() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setRoleServiceItemCatalogMapId(final RoleServiceItemCatalogMapId roleServiceItemCatalogMapId) {
		this.roleServiceItemCatalogMapId = roleServiceItemCatalogMapId;
	}

	/**
	 * 
	 * @generated
	 */
	public RoleServiceItemCatalogMapId getRoleServiceItemCatalogMapId() {
		return this.roleServiceItemCatalogMapId;
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
			this.roleServiceItemCatalogMapId.setRoleId(this.role.getRoleId());
		}

	}

	/**
	 * 
	 * @generated
	 */
	public ServiceItemCatalog getServiceItemCatalog() {
		return this.serviceItemCatalog;
	}

	/**
	 * 
	 * @generated
	 */
	public void setServiceItemCatalog(final ServiceItemCatalog serviceItemCatalog) {
		this.serviceItemCatalog = serviceItemCatalog;
		if (this.serviceItemCatalog != null && this.serviceItemCatalog.getServiceItemId() != null) {
			this.roleServiceItemCatalogMapId.setServiceItemId(this.serviceItemCatalog.getServiceItemId());
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
		if (this.serviceCatalog != null && this.serviceCatalog.getServiceId() != null) {
			this.roleServiceItemCatalogMapId.setServiceId(this.serviceCatalog.getServiceId());
		}

	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final RoleServiceItemCatalogMap that) {
		setRoleServiceItemCatalogMapId(that.getRoleServiceItemCatalogMapId());
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
		result = prime * result + ((roleServiceItemCatalogMapId == null) ? 0 : roleServiceItemCatalogMapId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("roleServiceItemCatalogMapId=[").append(roleServiceItemCatalogMapId).append("] ");
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
		final RoleServiceItemCatalogMap other = (RoleServiceItemCatalogMap) obj;
		if (roleServiceItemCatalogMapId == null) {
			if (other.roleServiceItemCatalogMapId != null) {
				return false;
			}
		} else if (!roleServiceItemCatalogMapId.equals(other.roleServiceItemCatalogMapId)) {
			return false;
		}
		return true;
	}
}
