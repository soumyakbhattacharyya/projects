/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.command;

import com.cognizant.jpaas2.api.SingletonDriver;
import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import com.cognizant.jpaas2.api.domain.AppEnvTaskOverview;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * get state of the instance and associated status objects
 *
 * @author Cognizant
 */
public final class GetDeploymentStatusCommand implements Command {

	private AppEnvInstance appEnvInstance;

	public void execute() {
		AppEnvInstance appEnvInstance = getAppEnvInstance();
		SortedSet<AppEnvTaskOverview> sortedSet = new TreeSet(appEnvInstance.getAppEnvTaskOverviews());
		appEnvInstance.setSortedMessages(sortedSet);
	}

	public AppEnvInstance getAppEnvInstance() {
		return appEnvInstance;
	}

	public void setAppEnvInstance(AppEnvInstance appEnvInstance) {
		this.appEnvInstance = appEnvInstance;
	}
}
