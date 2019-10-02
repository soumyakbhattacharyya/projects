package com.cognizant.cloudset.core;

import java.io.Serializable;
import java.util.Set;

/** 
 * JPA class representing Account 
 * The corresponding mapping table is account 
 */

public class Account implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @generated
	 */
	
	private Integer accountId;

	/**
	 * 
	 * @generated
	 */
	
	private String name;

	/**
	 * 
	 * @generated
	 */
	
	private String externalId;

	/**
	 * 
	 * @generated
	 */
	
	private String primaryContactId;

	/**
	 * 
	 * @generated
	 */
	private String primaryContactEmail;

	private BusinessUnit businessUnit;

	private Set<Project> projectss;
	private String authenticationKey;
	
	private String buAuthenticationKey;

	

	/**
	 *
	 * @generated
	 */
	public Account() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setAccountId(final Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getAccountId() {
		return this.accountId;
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
	public BusinessUnit getBusinessUnit() {
		return this.businessUnit;
	}

	/**
	 * 
	 * @generated
	 */
	public void setBusinessUnit(final BusinessUnit businessUnit) {
		this.businessUnit = businessUnit;

	}

	/**
	 * 
	 * @generated
	 */
	public Set<Project> getProjectss() {
		return this.projectss;
	}

	/**
	 * 
	 * @generated
	 */
	public void setProjectss(final Set<Project> projectss) {
		this.projectss = projectss;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final Account that) {
		setAccountId(that.getAccountId());
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
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("accountId=[").append(accountId).append("] ");
		buffer.append("name=[").append(name).append("] ");
		buffer.append("externalId=[").append(externalId).append("] ");
		buffer.append("primaryContactId=[").append(primaryContactId).append("] ");
		buffer.append("primaryContactEmail=[").append(primaryContactEmail).append("] ");

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
		final Account other = (Account) obj;
		if (accountId == null) {
			if (other.accountId != null) {
				return false;
			}
		} else if (!accountId.equals(other.accountId)) {
			return false;
		}
		return true;
	}

	public String getBuAuthenticationKey() {
		return buAuthenticationKey;
	}

	public void setBuAuthenticationKey(String buAuthenticationKey) {
		this.buAuthenticationKey = buAuthenticationKey;
	}

	public String getAuthenticationKey() {
		return authenticationKey;
	}

	public void setAuthenticationKey(String authenticationKey) {
		this.authenticationKey = authenticationKey;
	}
}
