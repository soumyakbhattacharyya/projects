package com.cognizant.cloudset.core;

import java.io.Serializable;
import java.util.Date;

/**
 * JPA class representing ServiceOrder The corresponding mapping table is
 * service_order
 */

public class ServiceOrder implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer serviceOrderId;

	private String detailedDescription;

	private Integer resourceCount;

	private Integer resourceSize;

	private String approvedBy;

	private String instanceId;

	private Date dateRequested;

	private Date processDate;

	private String raisedBy;

	private String rejectionReason;

	private CloudProvider cloudProvider;

	private CloudRegion cloudRegion;

	private Project project;

	private CloudAccount cloudAccount;

	/**
	 * 
	 * @generated
	 */
	public ServiceOrder() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setServiceOrderId(final Integer serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getServiceOrderId() {
		return this.serviceOrderId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setDetailedDescription(final String detailedDescription) {
		this.detailedDescription = detailedDescription;
	}

	/**
	 * 
	 * @generated
	 */
	public String getDetailedDescription() {
		return this.detailedDescription;
	}

	/**
	 * 
	 * @generated
	 */

	public void setResourceCount(final Integer resourceCount) {
		this.resourceCount = resourceCount;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getResourceCount() {
		return this.resourceCount;
	}

	/**
	 * 
	 * @generated
	 */

	public void setResourceSize(final Integer resourceSize) {
		this.resourceSize = resourceSize;
	}

	/**
	 * 
	 * @generated
	 */
	public Integer getResourceSize() {
		return this.resourceSize;
	}

	/**
	 * 
	 * @generated
	 */

	public void setApprovedBy(final String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * 
	 * @generated
	 */
	public String getApprovedBy() {
		return this.approvedBy;
	}

	/**
	 * 
	 * @generated
	 */

	public void setInstanceId(final String instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * 
	 * @generated
	 */
	public String getInstanceId() {
		return this.instanceId;
	}

	/**
	 * 
	 * @generated
	 */

	public void setDateRequested(final Date dateRequested) {
		if (dateRequested != null)
			this.dateRequested = (Date) dateRequested.clone();
		else
			this.dateRequested = null;
	}

	/**
	 * 
	 * @generated
	 */
	public Date getDateRequested() {
		if (this.dateRequested != null)
			return (Date) this.dateRequested.clone();
		else
			return null;
	}

	/**
	 * 
	 * @generated
	 */

	public void setProcessDate(final Date processDate) {
		if (processDate != null)
			this.processDate = (Date) processDate.clone();
		else
			this.processDate = null;
	}

	/**
	 * 
	 * @generated
	 */
	public Date getProcessDate() {
		if (this.processDate != null)
			return (Date) this.processDate.clone();
		else
			return null;
	}

	/**
	 * 
	 * @generated
	 */

	public void setRaisedBy(final String raisedBy) {
		this.raisedBy = raisedBy;
	}

	/**
	 * 
	 * @generated
	 */
	public String getRaisedBy() {
		return this.raisedBy;
	}

	/**
	 * 
	 * @generated
	 */

	public void setRejectionReason(final String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	/**
	 * 
	 * @generated
	 */
	public String getRejectionReason() {
		return this.rejectionReason;
	}

	/**
	 * 
	 * @generated
	 */
	public CloudProvider getCloudProvider() {
		return this.cloudProvider;
	}

	/**
	 * 
	 * @generated
	 */
	public void setCloudProvider(final CloudProvider cloudProvider) {
		this.cloudProvider = cloudProvider;

	}

	/**
	 * 
	 * @generated
	 */
	public CloudRegion getCloudRegion() {
		return this.cloudRegion;
	}

	/**
	 * 
	 * @generated
	 */
	public void setCloudRegion(final CloudRegion cloudRegion) {
		this.cloudRegion = cloudRegion;

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

	/**
	 * 
	 * @generated
	 */
	public CloudAccount getCloudAccount() {
		return this.cloudAccount;
	}

	/**
	 * 
	 * @generated
	 */
	public void setCloudAccount(final CloudAccount cloudAccount) {
		this.cloudAccount = cloudAccount;

	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final ServiceOrder that) {
		setServiceOrderId(that.getServiceOrderId());
		setDetailedDescription(that.getDetailedDescription());
		// setBaseImageId(that.getBaseImageId());
		setResourceCount(that.getResourceCount());
		setResourceSize(that.getResourceSize());
		setApprovedBy(that.getApprovedBy());
		setInstanceId(that.getInstanceId());
		setDateRequested(that.getDateRequested());
		setProcessDate(that.getProcessDate());
		setRaisedBy(that.getRaisedBy());
		setRejectionReason(that.getRejectionReason());
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
				+ ((serviceOrderId == null) ? 0 : serviceOrderId.hashCode());
		return result;
	}

	/**
	 * Returns a textual representation of a bean.
	 * 
	 * @generated
	 */
	public String toString() {

		final StringBuilder buffer = new StringBuilder();

		buffer.append("serviceOrderId=[").append(serviceOrderId).append("] ");
		buffer.append("detailedDescription=[").append(detailedDescription)
				.append("] ");
		// buffer.append("baseImageId=[").append(baseImageId).append("] ");
		buffer.append("resourceCount=[").append(resourceCount).append("] ");
		buffer.append("resourceSize=[").append(resourceSize).append("] ");
		buffer.append("approvedBy=[").append(approvedBy).append("] ");
		buffer.append("instanceId=[").append(instanceId).append("] ");
		buffer.append("dateRequested=[").append(dateRequested).append("] ");
		buffer.append("processDate=[").append(processDate).append("] ");
		buffer.append("raisedBy=[").append(raisedBy).append("] ");
		buffer.append("rejectionReason=[").append(rejectionReason).append("] ");

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
		final ServiceOrder other = (ServiceOrder) obj;
		if (serviceOrderId == null) {
			if (other.serviceOrderId != null) {
				return false;
			}
		} else if (!serviceOrderId.equals(other.serviceOrderId)) {
			return false;
		}
		return true;
	}
}
