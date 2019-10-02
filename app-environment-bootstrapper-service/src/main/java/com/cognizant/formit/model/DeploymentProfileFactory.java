/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.model;

import java.util.Properties;

/**
 * Factory to produce a deployment profile
 *
 * @author Cognizant
 */
public class DeploymentProfileFactory {

	private static DeploymentProfileFactory uniqueInstance;

	private DeploymentProfileFactory() {
	}

	public static synchronized DeploymentProfileFactory getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new DeploymentProfileFactory();
		}
		return uniqueInstance;
	}

	/**
	 * get a deployment profile, optionally use source argument to pass a hint / information that concrete implementation can use
	 *
	 * @param profileSourceType
	 * @param source
	 * @return
	 */
	public IDeploymentProfile getDP(final ProfileSourceType profileSourceType, final Object source) {

		IDeploymentProfile deploymentProfile = null;

		switch (profileSourceType) {
			case PROPERTY_FILE:
				if (source instanceof String) {
					String profileFilePath = String.valueOf(source);
					deploymentProfile = DefaultDeploymentProfile.newInstance(profileFilePath);
					return deploymentProfile;
				}
				break;
			case IN_MEMORY:
				if (source instanceof Properties) {
					deploymentProfile = PropertyDrivenDeploymentProfile.newInstance((Properties) source);
					return deploymentProfile;
				}
				break;
		}
		return deploymentProfile;
	}

	/**
	 * Types of sources from where profile properties can be constructed from
	 */
	public static enum ProfileSourceType {

		PROPERTY_FILE, IN_MEMORY;
	}
}
