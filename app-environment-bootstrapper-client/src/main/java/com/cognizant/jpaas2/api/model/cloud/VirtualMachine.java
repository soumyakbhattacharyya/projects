package com.cognizant.jpaas2.api.model.cloud;

import java.util.ArrayList;
import java.util.List;

public class VirtualMachine {

	private Boolean clonable;
	private Long creationTimestamp;
	private VmStateType currentState;
	private String description;
	private Boolean imagable;
	private Long lastBootTimestamp;
	private Long lastPauseTimestamp;
	private String name;
	private Boolean pausable;
	private Boolean persistent;
	private String privateDnsAddress;
	private List<String> privateIpAddresses;
	private PhysicalMachine product;
	private String providerAssignedIpAddressId;
	private String providerDataCenterId;
	private String providerMachineImageId;
	private String providerOwnerId;
	private String providerRegionId;
	private String providerSubnetId;
	private String providerVirtualMachineId;
	private String providerVlanId;
	private String publicDnsAddress;
	private List<String> publicIpAddresses;
	private Boolean rebootable;
	private String rootPassword;
	private String rootUser;
	private Long terminationTimestamp;

	public Boolean getClonable() {
		return clonable;
	}

	public void setClonable(Boolean clonable) {
		this.clonable = clonable;
	}

	public Long getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public VmStateType getCurrentState() {
		return currentState;
	}

	public void setCurrentState(VmStateType currentState) {
		this.currentState = currentState;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getImagable() {
		return imagable;
	}

	public void setImagable(Boolean imagable) {
		this.imagable = imagable;
	}

	public Long getLastBootTimestamp() {
		return lastBootTimestamp;
	}

	public void setLastBootTimestamp(Long lastBootTimestamp) {
		this.lastBootTimestamp = lastBootTimestamp;
	}

	public Long getLastPauseTimestamp() {
		return lastPauseTimestamp;
	}

	public void setLastPauseTimestamp(Long lastPauseTimestamp) {
		this.lastPauseTimestamp = lastPauseTimestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getPausable() {
		return pausable;
	}

	public void setPausable(Boolean pausable) {
		this.pausable = pausable;
	}

	public Boolean getPersistent() {
		return persistent;
	}

	public void setPersistent(Boolean persistent) {
		this.persistent = persistent;
	}

	public String getPrivateDnsAddress() {
		return privateDnsAddress;
	}

	public void setPrivateDnsAddress(String privateDnsAddress) {
		this.privateDnsAddress = privateDnsAddress;
	}

	public List<String> getPrivateIpAddresses() {
		return privateIpAddresses;
	}

	public void setPrivateIpAddresses(List<String> privateIpAddresses) {
		this.privateIpAddresses = privateIpAddresses;
	}

	public PhysicalMachine getProduct() {
		return product;
	}

	public void setProduct(PhysicalMachine product) {
		this.product = product;
	}

	public String getProviderAssignedIpAddressId() {
		return providerAssignedIpAddressId;
	}

	public void setProviderAssignedIpAddressId(
			String providerAssignedIpAddressId) {
		this.providerAssignedIpAddressId = providerAssignedIpAddressId;
	}

	public String getProviderDataCenterId() {
		return providerDataCenterId;
	}

	public void setProviderDataCenterId(String providerDataCenterId) {
		this.providerDataCenterId = providerDataCenterId;
	}

	public String getProviderMachineImageId() {
		return providerMachineImageId;
	}

	public void setProviderMachineImageId(String providerMachineImageId) {
		this.providerMachineImageId = providerMachineImageId;
	}

	public String getProviderOwnerId() {
		return providerOwnerId;
	}

	public void setProviderOwnerId(String providerOwnerId) {
		this.providerOwnerId = providerOwnerId;
	}

	public String getProviderRegionId() {
		return providerRegionId;
	}

	public void setProviderRegionId(String providerRegionId) {
		this.providerRegionId = providerRegionId;
	}

	public String getProviderSubnetId() {
		return providerSubnetId;
	}

	public void setProviderSubnetId(String providerSubnetId) {
		this.providerSubnetId = providerSubnetId;
	}

	public String getProviderVirtualMachineId() {
		return providerVirtualMachineId;
	}

	public void setProviderVirtualMachineId(String providerVirtualMachineId) {
		this.providerVirtualMachineId = providerVirtualMachineId;
	}

	public String getProviderVlanId() {
		return providerVlanId;
	}

	public void setProviderVlanId(String providerVlanId) {
		this.providerVlanId = providerVlanId;
	}

	public String getPublicDnsAddress() {
		return publicDnsAddress;
	}

	public void setPublicDnsAddress(String publicDnsAddress) {
		this.publicDnsAddress = publicDnsAddress;
	}

	public List<String> getPublicIpAddresses() {
		return publicIpAddresses;
	}

	public void setPublicIpAddresses(List<String> publicIpAddresses) {
		this.publicIpAddresses = publicIpAddresses;
	}

	public Boolean getRebootable() {
		return rebootable;
	}

	public void setRebootable(Boolean rebootable) {
		this.rebootable = rebootable;
	}

	public String getRootPassword() {
		return rootPassword;
	}

	public void setRootPassword(String rootPassword) {
		this.rootPassword = rootPassword;
	}

	public String getRootUser() {
		return rootUser;
	}

	public void setRootUser(String rootUser) {
		this.rootUser = rootUser;
	}

	public Long getTerminationTimestamp() {
		return terminationTimestamp;
	}

	public void setTerminationTimestamp(Long terminationTimestamp) {
		this.terminationTimestamp = terminationTimestamp;
	}
}
