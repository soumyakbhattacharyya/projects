package com.cognizant.cloudset.core;

import java.io.Serializable;
import java.util.Set;

/**
 * JPA class representing ServiceCatalog The corresponding mapping table is
 * service_catalog
 */
public class ServiceCatalog implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer serviceId;

	private String serviceName;

	private String serviceType;

	private Set<RoleServiceItemCatalogMap> roleServiceItemCatalogMapss;

	private Set<ServiceItemCatalog> serviceItemCatalogss;

	private Set<UserRoleProjectServiceCatalogMap> userRoleProjectServiceCatalogMapss;

	// @JoinTable(name = "project_service_catalog_map", joinColumns = {
	// @JoinColumn(name = "service_id", referencedColumnName = "service_id")},
	// inverseJoinColumns = {
	// @JoinColumn(name = "project_id", referencedColumnName = "project_id")})
	// @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE},
	// fetch=FetchType.LAZY)
	// private Set<Project> projectCollection;

	/*
	 * @ManyToMany(mappedBy = "serviceCatalogCollection") private
	 * Set<Enterprise> enterpriseCollection;
	 */

	private Set<Role> roleCollection;

	private Set<ProjectServiceSubscription> projectServiceSubscriptions;

	/**
	 * 
	 * @generated
	 */
	public ServiceCatalog() {
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

	public void setServiceName(final String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * 
	 * @generated
	 */
	public String getServiceName() {
		return this.serviceName;
	}

	/**
	 * 
	 * @generated
	 */

	public void setServiceType(final String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * 
	 * @generated
	 */
	public String getServiceType() {
		return this.serviceType;
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
	 * 
	 * @generated
	 */
	public Set<ServiceItemCatalog> getServiceItemCatalogss() {
		return this.serviceItemCatalogss;
	}

	/**
	 * 
	 * @generated
	 */
	public void setServiceItemCatalogss(
			final Set<ServiceItemCatalog> serviceItemCatalogss) {
		this.serviceItemCatalogss = serviceItemCatalogss;
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

	// public Set<Project> getProjectCollection() {
	// return projectCollection;
	// }
	//
	// public void setProjectCollection(Set<Project> projectCollection) {
	// this.projectCollection = projectCollection;
	// }

	/*
	 * public Set<Enterprise> getEnterpriseCollection() { return
	 * enterpriseCollection; }
	 * 
	 * public void setEnterpriseCollection(Set<Enterprise> enterpriseCollection)
	 * { this.enterpriseCollection = enterpriseCollection; }
	 */

	public Set<Role> getRoleCollection() {
		return roleCollection;
	}

	public void setRoleCollection(Set<Role> roleCollection) {
		this.roleCollection = roleCollection;
	}

	public Set<ProjectServiceSubscription> getProjectServiceSubscriptions() {
		return projectServiceSubscriptions;
	}

	public void setProjectServiceSubscriptions(
			Set<ProjectServiceSubscription> projectServiceSubscriptions) {
		this.projectServiceSubscriptions = projectServiceSubscriptions;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final ServiceCatalog that) {
		setServiceId(that.getServiceId());
		setServiceName(that.getServiceName());
		setServiceType(that.getServiceType());
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
				+ ((serviceId == null) ? 0 : serviceId.hashCode());
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
		buffer.append("serviceName=[").append(serviceName).append("] ");
		buffer.append("serviceType=[").append(serviceType).append("] ");

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
		final ServiceCatalog other = (ServiceCatalog) obj;
		if (serviceId == null) {
			if (other.serviceId != null) {
				return false;
			}
		} else if (!serviceId.equals(other.serviceId)) {
			return false;
		}
		return true;
	}
}
