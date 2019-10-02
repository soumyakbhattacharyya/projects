package com.cognizant.cloudset.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * JPA class representing ProjectServiceSubscription The corresponding mapping
 * table is project_service_subscription
 */

public class ProjectServiceSubscription implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer projServSubsId;

	private Date startDate;

	private Date endDate;

	private ServiceCatalog serviceCatalog;

	private Project project;

	private boolean hasExpired;

	private boolean active;

	/**
	 * 
	 * @generated
	 */
	public ProjectServiceSubscription() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setProjServSubsId(final Integer projServSubsId) {
		this.projServSubsId = projServSubsId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getProjServSubsId() {
		return this.projServSubsId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setStartDate(final Date startDate) {
		if (startDate != null)
			this.startDate = (Date) startDate.clone();
		else
			this.startDate = null;
	}

	/**
	 * 
	 * @generated
	 */
	public Date getStartDate() {
		if (this.startDate != null)
			return (Date) this.startDate.clone();
		else
			return null;
	}

	/**
	 * 
	 * @generated
	 */

	public void setEndDate(final Date endDate) {
		if (endDate != null)
			this.endDate = (Date) endDate.clone();
		else
			this.endDate = null;
	}

	/**
	 * 
	 * @generated
	 */
	public Date getEndDate() {
		if (this.endDate != null)
			return (Date) this.endDate.clone();
		else
			return null;
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
	public Project getProject() {
		return this.project;
	}

	/**
	 * 
	 * @generated
	 */
	public void setProject(final Project project) {
		this.project = project;

	}

	public boolean isHasExpired() {
		return hasExpired;
	}

	public void setHasExpired(boolean hasExpired) {
		this.hasExpired = hasExpired;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final ProjectServiceSubscription that) {
		setProjServSubsId(that.getProjServSubsId());
		setStartDate(that.getStartDate());
		setEndDate(that.getEndDate());
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
				+ ((projServSubsId == null) ? 0 : projServSubsId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("projServSubsId=[").append(projServSubsId).append("] ");
		buffer.append("startDate=[").append(startDate).append("] ");
		buffer.append("endDate=[").append(endDate).append("] ");

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
		final ProjectServiceSubscription other = (ProjectServiceSubscription) obj;
		if (projServSubsId == null) {
			if (other.projServSubsId != null) {
				return false;
			}
		} else if (!projServSubsId.equals(other.projServSubsId)) {
			return false;
		}
		return true;
	}
}
