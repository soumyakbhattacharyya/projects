/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.model;

import java.util.Map;
import org.apache.commons.configuration.ConfigurationException;

/**
 * represents a profile
 *
 * By default this supports building a profile by reading a properties file. However to extend it to read the properties from other sources
 * implement load and get methods accordingly
 *
 * @author Cognizant
 */
public interface IDeploymentProfile {

	/**
	 * get pre - populated profile map
	 *
	 * @return
	 * @throws ConfigurationException
	 */
	Map<String, String> get() throws ConfigurationException;

	/**
	 * load profile map
	 *
	 * @return map where key represents profile property name and value correspondingly
	 * @throws ConfigurationException
	 */
	public Map<String, String> load() throws ConfigurationException;
}
