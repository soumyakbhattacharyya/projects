package com.cognizant.jpaas2.api.model.cloud;

public class PhysicalMachine {

	private Integer cpuCount;
	private String description;
	private Integer diskSizeInGb;
	private String name;
	private String productId;
	private Integer ramInMb;

	public Integer getCpuCount() {
		return cpuCount;
	}

	public void setCpuCount(Integer cpuCount) {
		this.cpuCount = cpuCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDiskSizeInGb() {
		return diskSizeInGb;
	}

	public void setDiskSizeInGb(Integer diskSizeInGb) {
		this.diskSizeInGb = diskSizeInGb;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getRamInMb() {
		return ramInMb;
	}

	public void setRamInMb(Integer ramInMb) {
		this.ramInMb = ramInMb;
	}
}
