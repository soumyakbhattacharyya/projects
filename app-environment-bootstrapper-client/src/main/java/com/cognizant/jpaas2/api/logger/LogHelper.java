/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.logger;

import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author cognizant
 */
public final class LogHelper {

	private final AtomicInteger index = new AtomicInteger();
	//private final StringBuilder builder = new StringBuilder(20);
	private final String CHECK_POINT = "CHECKPOINT";
	private final String SEPARATOR = "#";
	private final AppEnvInstance appEnvInstance;
	/**
	 * constants
	 */
	public final static String EXECUTOR_INITIALIZATION = "initialized executor service for launching deployment";
	public final static String VALIDATE_LAUNCH_PRECONDITION = "launch preconditions passed appropriately";
	public final static String DISCOVER_RESOURCE_REQUIREMENT = "analyzed bootstrapping sequence to discover () following number of VMs required to support deployment";
	public final static String SETUP_DEPLOYMENT_CONFIGURATION = "deployment configuration validated";
	public final static String SPAWN_CLOUD_REQOURCE = "() cloud resource(s) have been spawned";
	public final static String REFRESH_CLOUD_RESOURCE = "() cloud resource's state has been refreshed";
	public final static String DEFINE_CLOUD_RESOURCE_IN_CMDB = "cloud resource definition has been produced in the CMDB";
	public final static String TAG_SEQUENCE_TO_NODE = "bootstrapping sequence(s) have been successfully tagged to cloud resource(s)";
	public final static String PREPARE_TRIGGERING_OPTION = "triggering options for underlying jobs loaded";
	public final static String EXECUTE = "sequence execution started";

	private LogHelper(AppEnvInstance appEnvInstance) {
		this.appEnvInstance = appEnvInstance;
	}

	/**
	 * adds a prefix
	 *
	 * @return string builder to further append
	 */
	public final StringBuilder prefix() {
		if (null != appEnvInstance && StringUtils.isNotBlank(appEnvInstance.getInstanceId())) {
			final StringBuilder builder = new StringBuilder(20);
			return builder.append(CHECK_POINT).
					append(SEPARATOR).
					append(index.getAndIncrement()).
					append(SEPARATOR).
					append(appEnvInstance.getInstanceId());
		} else {
			throw new IllegalStateException("Instance id of the instance unavailable");
		}
	}

	/**
	 * static factory
	 *
	 * @return
	 */
	public final static LogHelper newLogHelper(AppEnvInstance appEnvInstance) {
		return new LogHelper(appEnvInstance);
	}

	/**
	 * utility for loging a message
	 *
	 * @param message
	 * @return
	 */
	public final String getLoggable(final String message) {
		return prefix().append("  ").append(message).append("\n").toString();
	}
}
