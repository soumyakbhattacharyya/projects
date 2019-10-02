package com.cognizant.cloudset.bean;

import java.io.Serializable;
import java.util.Set;

/**
 * JPA class representing BusinessUnit The corresponding mapping table is
 * business_unit
 */

public class BusinessUnit implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer businessUnitId;

	private String name;

	private String externalId;

	private String primaryContactId;

	private String primaryContactEmail;

	private Enterprise enterprise;

	private Set<Account> accountss;

	private String authenticationKey;

	private String entAuthenticationKey;

	/**
	 * 
	 * @generated
	 */
	public BusinessUnit() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setBusinessUnitId(final Integer businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getBusinessUnitId() {
		return this.businessUnitId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * 
	 * @generated
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @generated
	 */

	public void setExternalId(final String externalId) {
		this.externalId = externalId;
	}

	/**
	 * 
	 * @generated
	 */
	public String getExternalId() {
		return this.externalId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setPrimaryContactId(final String primaryContactId) {
		this.primaryContactId = primaryContactId;
	}

	/**
	 * 
	 * @generated
	 */
	public String getPrimaryContactId() {
		return this.primaryContactId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setPrimaryContactEmail(final String primaryContactEmail) {
		this.primaryContactEmail = primaryContactEmail;
	}

	/**
	 * 
	 * @generated
	 */
	public String getPrimaryContactEmail() {
		return this.primaryContactEmail;
	}

	/**
	 * 
	 * @generated
	 */
	public Enterprise getEnterprise() {
		return this.enterprise;
	}

	/**
	 * 
	 * @generated
	 */
	public void setEnterprise(final Enterprise enterprise) {
		this.enterprise = enterprise;

	}

	/**
	 * 
	 * @generated
	 */
	public Set<Account> getAccountss() {
		return this.accountss;
	}

	/**
	 * 
	 * @generated
	 */
	public void setAccountss(final Set<Account> accountss) {
		this.accountss = accountss;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final BusinessUnit that) {
		setBusinessUnitId(that.getBusinessUnitId());
		setName(that.getName());
		setExternalId(that.getExternalId());
		setPrimaryContactId(that.getPrimaryContactId());
		setPrimaryContactEmail(that.getPrimaryContactEmail());
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
				+ ((businessUnitId == null) ? 0 : businessUnitId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("businessUnitId=[").append(businessUnitId).append("] ");
		buffer.append("name=[").append(name).append("] ");
		buffer.append("externalId=[").append(externalId).append("] ");
		buffer.append("primaryContactId=[").append(primaryContactId)
				.append("] ");
		buffer.append("primaryContactEmail=[").append(primaryContactEmail)
				.append("] ");

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
		final BusinessUnit other = (BusinessUnit) obj;
		if (businessUnitId == null) {
			if (other.businessUnitId != null) {
				return false;
			}
		} else if (!businessUnitId.equals(other.businessUnitId)) {
			return false;
		}
		return true;
	}

	public String getAuthenticationKey() {
		return authenticationKey;
	}

	public void setAuthenticationKey(String authenticationKey) {
		this.authenticationKey = authenticationKey;
	}

	public String getEntAuthenticationKey() {
		return entAuthenticationKey;
	}

	public void setEntAuthenticationKey(String entAuthenticationKey) {
		this.entAuthenticationKey = entAuthenticationKey;
	}
}
