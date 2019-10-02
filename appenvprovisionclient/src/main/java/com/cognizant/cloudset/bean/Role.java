package com.cognizant.cloudset.bean;

import java.io.Serializable;
import java.util.Set;

/**
 * JPA class representing Role The corresponding mapping table is role
 */

public class Role implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer roleId;

	private String roleName;

	private String roleDescription;

	private boolean platformRole;

	private Set<RoleServiceItemCatalogMap> roleServiceItemCatalogMapss;

	private Set<UserRoleProjectServiceCatalogMap> userRoleProjectServiceCatalogMapss;

	private Set<ServiceCatalog> serviceCatalogCollection;

	/**
	 * 
	 * @generated
	 */
	public Role() {
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

	public void setRoleName(final String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 
	 * @generated
	 */
	public String getRoleName() {
		return this.roleName;
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

	public Set<ServiceCatalog> getServiceCatalogCollection() {
		return serviceCatalogCollection;
	}

	public void setServiceCatalogCollection(
			Set<ServiceCatalog> serviceCatalogCollection) {
		this.serviceCatalogCollection = serviceCatalogCollection;
	}

	public boolean isPlatformRole() {
		return platformRole;
	}

	public void setPlatformRole(boolean platformRole) {
		this.platformRole = platformRole;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final Role that) {
		setRoleId(that.getRoleId());
		setRoleName(that.getRoleName());
	}

	/**
	 * @generated
	 * 
	 */
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("roleId=[").append(roleId).append("] ");
		buffer.append("roleName=[").append(roleName).append("] ");

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
		final Role other = (Role) obj;
		if (roleId == null) {
			if (other.roleId != null) {
				return false;
			}
		} else if (!roleId.equals(other.roleId)) {
			return false;
		}
		return true;
	}
}
