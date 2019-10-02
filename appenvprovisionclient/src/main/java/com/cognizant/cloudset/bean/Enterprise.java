package com.cognizant.cloudset.bean;

import java.io.Serializable;
import java.util.Set;

/**
 * JPA class representing Enterprise The corresponding mapping table is
 * enterprise
 */

public class Enterprise implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer enterpriseId;

	private String name;
	private String primaryContactId;

	private String primaryContactEmail;

	private Set<BusinessUnit> businessUnitss;

	private String authenticationKey;

	private String emailDomain;

	/**
	 * 
	 * @generated
	 */
	public Enterprise() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setEnterpriseId(final Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getEnterpriseId() {
		return this.enterpriseId;
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
	public Set<BusinessUnit> getBusinessUnitss() {
		return this.businessUnitss;
	}

	/**
	 * 
	 * @generated
	 */
	public void setBusinessUnitss(final Set<BusinessUnit> businessUnitss) {
		this.businessUnitss = businessUnitss;
	}

	/*
	 * public Set<ServiceCatalog> getServiceCatalogCollection() { return
	 * serviceCatalogCollection; }
	 * 
	 * public void setServiceCatalogCollection(Set<ServiceCatalog>
	 * serviceCatalogCollection) { this.serviceCatalogCollection =
	 * serviceCatalogCollection; }
	 */

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final Enterprise that) {
		setEnterpriseId(that.getEnterpriseId());
		setName(that.getName());
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
				+ ((enterpriseId == null) ? 0 : enterpriseId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("enterpriseId=[").append(enterpriseId).append("] ");
		buffer.append("name=[").append(name).append("] ");
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
		final Enterprise other = (Enterprise) obj;
		if (enterpriseId == null) {
			if (other.enterpriseId != null) {
				return false;
			}
		} else if (!enterpriseId.equals(other.enterpriseId)) {
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

	public String getEmailDomain() {
		return emailDomain;
	}

	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}
}
