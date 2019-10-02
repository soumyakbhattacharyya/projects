/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.model;

import com.cognizant.formit.util.property.PropertyUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import static org.fest.assertions.api.Assertions.assertThat; // main one

/**
 * reads a properties file and represents it as a KV store
 *
 * @author Cognizant
 */
public class DefaultDeploymentProfile implements IDeploymentProfile {

	// source
	private String profileProp;
	// returned value
	private Map<String, String> profileMap = Collections.synchronizedMap(new HashMap<String, String>());
	private static final Logger l = Logger.getLogger(DefaultDeploymentProfile.class);

	/**
	 * Requires to be instantiated with a source destination to read from
	 *
	 * @param profileProp path to .properties file which consists of key value pair
	 * @return
	 */
	public static IDeploymentProfile newInstance(String profileProp) {
		return new DefaultDeploymentProfile(profileProp);
	}

	private DefaultDeploymentProfile(String profileProp) {
		this.profileProp = profileProp;
	}

	public Map<String, String> get() throws ConfigurationException {
		return profileMap.isEmpty() ? load() : this.profileMap;

	}

	/**
	 * Loads the profile properties from the path being supplied with
	 *
	 * @return
	 * @throws ConfigurationException
	 */
	public Map<String, String> load() throws ConfigurationException {
		// Initialize profile property which will be used to decorate the config
		assertThat(profileProp).isNotNull();
//        Configuration config = new PropertiesConfiguration(profileProp);
//        Iterator iterator = config.getKeys();
//        while (iterator.hasNext()) {
//            final String name = String.valueOf(iterator.next());
//            profileMap.put(name, config.getString(name));
//        }
		return PropertyUtil.prop2Map(profileProp);
	}
}
