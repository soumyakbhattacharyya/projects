/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.domain;

import com.cognizant.jpaas2.api.model.cloud.VirtualMachine;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author Cognizant
 */
public class AppEnvInstanceDetail {

	public Properties getEnvironmentProperties() {
		return environmentProperties;
	}

	public void setEnvironmentProperties(Properties environmentProperties) {
		this.environmentProperties = environmentProperties;
	}

	public Set<VirtualMachine> getMachines() {
		return machines;
	}

	public void setMachines(Set<VirtualMachine> machines) {
		this.machines = machines;
	}
	private Properties environmentProperties;
	private Set<VirtualMachine> machines = new HashSet<VirtualMachine>(0);
}
