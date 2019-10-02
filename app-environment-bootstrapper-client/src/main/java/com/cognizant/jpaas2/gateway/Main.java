/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.gateway;

import com.cognizant.jpaas2.api.SingletonDriver;
import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * main entry point for the app
 *
 * @author cognizant
 */
public class Main {

	public static void main(String args[]) throws InstantiationException, IllegalAccessException {
//		System.setProperty("CONFIG_FILE_PATH", "D:/app-environment-bootstrapper-client-0.0.1-configuration/");
//		System.setProperty("MODE", "TERMINATE");
		if (StringUtils.isNotBlank(System.getProperty("MODE")) && "LAUNCH".equals(System.getProperty("MODE"))) {
			launch();
		}
		if (StringUtils.isNotBlank(System.getProperty("MODE")) && "TERMINATE".equals(System.getProperty("MODE"))) {
			terminate();
		}

	}

	private static void launch() throws InstantiationException, IllegalAccessException {
		SingletonDriver driver = SingletonDriver.getInstance();
		// get all sheduled launches
		List<AppEnvInstance> result = driver.findScheduledLaunches();
		// place scheduled lauch requests into the queue
		if (result != null && result.size() > 0) {
			boolean isSuccess = driver.enQueueLaunchRequests(result);
		}
		// process requets
		driver.process();
	}

	private static void terminate() throws InstantiationException, IllegalAccessException {
		SingletonDriver driver = SingletonDriver.getInstance();
		// get all sheduled launches
		List<AppEnvInstance> result = driver.findScheduledTerminations();
		// place scheduled lauch requests into the queue
		if (result != null && result.size() > 0) {
			boolean isSuccess = driver.enQueueTerminationRequests(result);
		}
		// process requets
		driver.process();
	}
}
