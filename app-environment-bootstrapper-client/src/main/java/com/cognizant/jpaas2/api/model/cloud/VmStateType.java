package com.cognizant.jpaas2.api.model.cloud;

public enum VmStateType {

	PAUSED,
	PENDING,
	RUNNING,
	REBOOTING,
	STOPPING,
	TERMINATED;

	public String value() {
		return name();
	}

	public static VmStateType fromValue(String v) {
		return valueOf(v);
	}
}
