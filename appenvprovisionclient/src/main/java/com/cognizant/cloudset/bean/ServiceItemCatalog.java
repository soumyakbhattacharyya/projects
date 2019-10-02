package com.cognizant.cloudset.bean;

import java.io.Serializable;
import java.util.Set;

/**
 * JPA class representing ServiceItemCatalog The corresponding mapping table is
 * service_item_catalog
 */

public class ServiceItemCatalog implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer serviceItemId;

	private String itemName;

	private String itemActionName;

	private ServiceCatalog serviceCatalog;

	private Set<RoleServiceItemCatalogMap> roleServiceItemCatalogMapss;

	/**
	 * 
	 * @generated
	 */
	public ServiceItemCatalog() {
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

	public void setItemName(final String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 
	 * @generated
	 */
	public String getItemName() {
		return this.itemName;
	}

	public String getItemActionName() {
		return itemActionName;
	}

	public void setItemActionName(String itemActionName) {
		this.itemActionName = itemActionName;
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

	}

	/**
	 * 
	 * @generated
	 */
	public Set<RoleServiceItemCatalogMap> getRoleServiceItemCatalogMapss() {
		return this.roleServiceItemCatalogMapss;
	}

	/**
	 * 
	 * @generated
	 */
	public void setRoleServiceItemCatalogMapss(
			final Set<RoleServiceItemCatalogMap> roleServiceItemCatalogMapss) {
		this.roleServiceItemCatalogMapss = roleServiceItemCatalogMapss;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final ServiceItemCatalog that) {
		setServiceItemId(that.getServiceItemId());
		setItemName(that.getItemName());
		setItemActionName(that.getItemActionName());
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
				+ ((serviceItemId == null) ? 0 : serviceItemId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("serviceItemId=[").append(serviceItemId).append("] ");
		buffer.append("itemName=[").append(itemName).append("] ");
		buffer.append("itemActionName=[").append(itemActionName).append("] ");

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
		final ServiceItemCatalog other = (ServiceItemCatalog) obj;
		if (serviceItemId == null) {
			if (other.serviceItemId != null) {
				return false;
			}
		} else if (!serviceItemId.equals(other.serviceItemId)) {
			return false;
		}
		return true;
	}
}
