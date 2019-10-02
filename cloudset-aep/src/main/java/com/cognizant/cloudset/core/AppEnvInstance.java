package com.cognizant.cloudset.core;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * JPA class representing AppEnvInstance The corresponding mapping table is
 * app_env_instance
 */
@XmlRootElement(name = "AppEnvInstance")
public class AppEnvInstance implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @generated
	 */
	private Long id;

	/**
	 * 
	 * @generated
	 */
	private String instanceId;

	private String status;

	private String properties;

	private Date startTimestamp;

	private Date endTimestamp;

	private String accountNumber;

	private String projectId;

	private String userId;

	private String reason;

	private String description;

	private String roleId;

	private String serviceId;

	private String vmDetails;

	private AppEnvMaster appEnvMaster;

	@XmlTransient
	private Set<AppEnvTaskOverview> appEnvTaskOverviewss;

	/**
	 * 
	 * @generated
	 */
	public AppEnvInstance() {
	}

	/**
	 * 
	 * @generated
	 */

	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @generated
	 */
	public Long getId() {
		return this.id;
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

	public void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * 
	 * @generated
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * 
	 * @generated
	 */

	public void setProperties(final String properties) {
		this.properties = properties;
	}

	/**
	 * 
	 * @generated
	 */

	public String getProperties() {
		return this.properties;
	}

	/**
	 * 
	 * @generated
	 */

	public void setStartTimestamp(final Date startTimestamp) {
		if (startTimestamp != null)
			this.startTimestamp = (Date) startTimestamp.clone();
		else
			this.startTimestamp = null;
	}

	/**
	 * 
	 * @generated
	 */
	public Date getStartTimestamp() {
		if (this.startTimestamp != null)
			return (Date) this.startTimestamp.clone();
		else
			return null;
	}

	/**
	 * 
	 * @generated
	 */

	public void setEndTimestamp(final Date endTimestamp) {
		if (endTimestamp != null)
			this.endTimestamp = (Date) endTimestamp.clone();
		else
			this.endTimestamp = null;
	}

	/**
	 * 
	 * @generated
	 */
	public Date getEndTimestamp() {
		if (this.endTimestamp != null)
			return (Date) this.endTimestamp.clone();
		else
			return null;
	}

	/**
	 * 
	 * @generated
	 */
	@XmlTransient
	public AppEnvMaster getAppEnvMaster() {
		return this.appEnvMaster;
	}

	/**
	 * 
	 * @generated
	 */
	public void setAppEnvMaster(final AppEnvMaster appEnvMaster) {
		this.appEnvMaster = appEnvMaster;

	}

	/**
	 * 
	 * @generated
	 */
	@XmlTransient
	public Set<AppEnvTaskOverview> getAppEnvTaskOverviewss() {
		return this.appEnvTaskOverviewss;
	}

	/**
	 * 
	 * @generated
	 */
	public void setAppEnvTaskOverviewss(
			final Set<AppEnvTaskOverview> appEnvTaskOverviewss) {
		this.appEnvTaskOverviewss = appEnvTaskOverviewss;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 * 
	 * @generated
	 */
	public void copy(final AppEnvInstance that) {
		setId(that.getId());
		setInstanceId(that.getInstanceId());
		setStatus(that.getStatus());
		setProperties(that.getProperties());
		setStartTimestamp(that.getStartTimestamp());
		setEndTimestamp(that.getEndTimestamp());
	}

	/**
	 * @generated
	 * 
	 */
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "AppEnvInstance [id=" + id + ", instanceId=" + instanceId
				+ ", status=" + status + ", properties=" + properties
				+ ", startTimestamp=" + startTimestamp + ", endTimestamp="
				+ endTimestamp + ", accountNumber=" + accountNumber
				+ ", projectId=" + projectId + ", userId=" + userId
				+ ", reason=" + reason + ", description=" + description
				+ ", roleId=" + roleId + ", serviceId=" + serviceId + "]";
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
		final AppEnvInstance other = (AppEnvInstance) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getVmDetails() {
		return vmDetails;
	}

	public void setVmDetails(String vmDetails) {
		this.vmDetails = vmDetails;
	}

}
