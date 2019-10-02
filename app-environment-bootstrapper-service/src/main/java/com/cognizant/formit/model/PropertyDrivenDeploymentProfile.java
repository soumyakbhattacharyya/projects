/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;

/**
 *
 * @author Cognizant
 */
public class PropertyDrivenDeploymentProfile implements IDeploymentProfile {

	// source
	private Properties properties;
	// returned value
	private Map<String, String> profileMap = Collections.synchronizedMap(new HashMap<String, String>());
	private static final Logger l = Logger.getLogger(DefaultDeploymentProfile.class);

	/**
	 * Requires to be instantiated with a source destination to read from
	 *
	 * @param profileProp path to .properties file which consists of key value pair
	 * @return
	 */
	public static IDeploymentProfile newInstance(final Properties properties) {
		return new PropertyDrivenDeploymentProfile(properties);
	}

	private PropertyDrivenDeploymentProfile(Properties properties) {
		this.properties = properties;
	}

	@Override
	public Map<String, String> get() throws ConfigurationException {
		return profileMap.isEmpty() ? load() : this.profileMap;
	}

	@Override
	public Map<String, String> load() throws ConfigurationException {
		// Initialize profile property which will be used to decorate the config        
		if (null != properties && !properties.isEmpty()) {
			Set<Object> keys = properties.keySet();
			Iterator<Object> itr = keys.iterator();
			while (itr.hasNext()) {
				final String name = String.valueOf(itr.next());
				profileMap.put(name, properties.getProperty(name));
			}
		}
		return profileMap;
	}
}
