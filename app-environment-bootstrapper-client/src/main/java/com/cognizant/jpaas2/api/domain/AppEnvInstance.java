package com.cognizant.jpaas2.api.domain;
// Generated Jan 15, 2013 1:14:03 PM by Hibernate Tools 3.2.1.GA

import com.cognizant.jpaas2.resource.VirtualMachineType;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * AppEnvInstance generated by hbm2java
 */
public class AppEnvInstance implements java.io.Serializable {

	private Long id;
	private AppEnvMaster appEnvMaster;
	private String instanceId;
	private String status;
	private String properties;
	private Date startTimestamp;

	public List<VirtualMachineType> getMachines() {
		return machines;
	}

	public void setMachines(List<VirtualMachineType> machines) {
		this.machines = machines;
	}
	// non - persistent field
	private SortedSet<AppEnvTaskOverview> sortedMessages;
	private List<VirtualMachineType> machines;

	public String getAccountNumber() {
		return accountNumber;
	}

	public SortedSet<AppEnvTaskOverview> getSortedMessages() {
		return sortedMessages;
	}

	public void setSortedMessages(SortedSet<AppEnvTaskOverview> sortedMessages) {
		this.sortedMessages = sortedMessages;
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
	private Date endTimestamp;
	private Set<AppEnvTaskOverview> appEnvTaskOverviews = new HashSet<AppEnvTaskOverview>(0);
	private AppEnvInstanceDetail appEnvInstanceDetail;
	private String accountNumber;
	private String projectId;

	public String getVmDetail() {
		return vmDetail;
	}

	public void setVmDetail(String vmDetail) {
		this.vmDetail = vmDetail;
	}
	private String userId;
	private String reason;
	private String description;
	private String roleId;
	private String serviceId;
	private String vmDetail;

	public AppEnvInstanceDetail getAppEnvInstanceDetail() {
		return appEnvInstanceDetail;
	}

	public AppEnvInstance() {
	}

	public AppEnvInstance(AppEnvMaster appEnvMaster, String status, String properties, Date startTimestamp) {
		this.appEnvMaster = appEnvMaster;
		this.status = status;
		this.properties = properties;
		this.startTimestamp = startTimestamp;
	}

	public AppEnvInstance(AppEnvMaster appEnvMaster, String instanceId, String status, String properties, Date startTimestamp, Date endTimestamp, Set<AppEnvTaskOverview> appEnvTaskOverviews) {
		this.appEnvMaster = appEnvMaster;
		this.instanceId = instanceId;
		this.status = status;
		this.properties = properties;
		this.startTimestamp = startTimestamp;
		this.endTimestamp = endTimestamp;
		this.appEnvTaskOverviews = appEnvTaskOverviews;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppEnvMaster getAppEnvMaster() {
		return this.appEnvMaster;
	}

	public void setAppEnvMaster(AppEnvMaster appEnvMaster) {
		this.appEnvMaster = appEnvMaster;
	}

	public String getInstanceId() {
		return this.instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProperties() {
		return this.properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public Date getStartTimestamp() {
		return this.startTimestamp;
	}

	public void setStartTimestamp(Date startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public Date getEndTimestamp() {
		return this.endTimestamp;
	}

	public void setEndTimestamp(Date endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public Set<AppEnvTaskOverview> getAppEnvTaskOverviews() {
		return this.appEnvTaskOverviews;
	}

	public void setAppEnvTaskOverviews(Set<AppEnvTaskOverview> appEnvTaskOverviews) {
		this.appEnvTaskOverviews = appEnvTaskOverviews;
	}

	/**
	 * Utility methods
	 */
	/**
	 * Returns if an instance has been torn down
	 *
	 * @return
	 */
	public Boolean isTornedDown() {
		return Boolean.TRUE ? ExecutionStatus.TORNDOWN.toString().equals(this.getStatus()) : Boolean.FALSE;
	}

	/**
	 * Update the status
	 *
	 * @param appEnvTaskOverview
	 */
	public void updateStatus(AppEnvTaskOverview appEnvTaskOverview) {
		this.getAppEnvTaskOverviews().add(appEnvTaskOverview);
	}

	public void setAppEnvInstanceDetail(AppEnvInstanceDetail appEnvInstanceDetail) {
		appEnvInstanceDetail = appEnvInstanceDetail;
	}

	/**
	 * The status of an execution
	 */
	public static enum ExecutionStatus {

		RUNNING, SUCCEEDED, FAILED, ABORTED, TORNDOWN, SCHEDULED, TORNDOWN_COMPLETED, TORNDOWN_FAILED;

		@Override
		public String toString() {
			switch (this) {
				case RUNNING:
					return "RUNNING";
				case SUCCEEDED:
					return "SUCCEEDED";
				case FAILED:
					return "FAILED";
				case ABORTED:
					return "ABORTED";
				case TORNDOWN:
					return "TORNDOWN";
				case SCHEDULED:
					return "SCHEDULED";
				case TORNDOWN_COMPLETED:
					return "TORNDOWN_COMPLETED";
				case TORNDOWN_FAILED:
					return "TORNDOWN_FAILED";
			}
			return null;
		}

		/**
		 *
		 * @param enumType
		 * @param value
		 * @return
		 */
		public static ExecutionStatus valueOf(ExecutionStatus enumType, String value) {
			if (value.equalsIgnoreCase(RUNNING.toString())) {
				return ExecutionStatus.RUNNING;
			} else if (value.equalsIgnoreCase(SUCCEEDED.toString())) {
				return ExecutionStatus.SUCCEEDED;
			} else if (value.equalsIgnoreCase(FAILED.toString())) {
				return ExecutionStatus.FAILED;
			} else if (value.equalsIgnoreCase(ABORTED.toString())) {
				return ExecutionStatus.ABORTED;
			} else if (value.equalsIgnoreCase(TORNDOWN.toString())) {
				return ExecutionStatus.TORNDOWN;
			} else if (value.equalsIgnoreCase(SCHEDULED.toString())) {
				return ExecutionStatus.SCHEDULED;
			} else if (value.equalsIgnoreCase(TORNDOWN_COMPLETED.toString())) {
				return ExecutionStatus.TORNDOWN_COMPLETED;
			} else if (value.equalsIgnoreCase(TORNDOWN_FAILED.toString())) {
				return ExecutionStatus.TORNDOWN_FAILED;
			} else {
				return null;
			}
		}
	}
}
