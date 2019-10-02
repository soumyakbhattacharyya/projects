package com.cognizant.cloudset.core;

import java.io.Serializable;
import java.util.Set;

/**
 * JPA class representing User The corresponding mapping table is user
 */

public class User implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer userId;

	private String firstname;

	private String lastname;

	private String email;

	private Set<UserRoleProjectServiceCatalogMap> userRoleProjectServiceCatalogMapss;

	private Set<Project> projectCollection;

	/**
	 * 
	 * @generated
	 */
	public User() {
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

	public void setFirstname(final String firstname) {
		this.firstname = firstname;
	}

	/**
	 * 
	 * @generated
	 */
	public String getFirstname() {
		return this.firstname;
	}

	/**
	 * 
	 * @generated
	 */

	public void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	/**
	 * 
	 * @generated
	 */
	public String getLastname() {
		return this.lastname;
	}

	/**
	 * 
	 * @generated
	 */

	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * 
	 * @generated
	 */
	public String getEmail() {
		return this.email;
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

	public Set<Project> getProjectCollection() {
		return projectCollection;
	}

	public void setProjectCollection(Set<Project> projectCollection) {
		this.projectCollection = projectCollection;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final User that) {
		setUserId(that.getUserId());
		setFirstname(that.getFirstname());
		setLastname(that.getLastname());
		setEmail(that.getEmail());
	}

	/**
	 * @generated
	 * 
	 */
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("userId=[").append(userId).append("] ");
		buffer.append("firstname=[").append(firstname).append("] ");
		buffer.append("lastname=[").append(lastname).append("] ");
		buffer.append("email=[").append(email).append("] ");

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
		final User other = (User) obj;
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}
		return true;
	}
}
