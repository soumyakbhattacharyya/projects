package com.cognizant.monitor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesManager {
	
	Logger LOGGER = Logger.getLogger(PropertiesManager.class.getName());
	
	private static final String propertyFileName = "clustermanagment.properties";
	private static String propertyFile;
	
	private Properties props;
	
	private FileInputStream propInputStream;
	
	public PropertiesManager() {
		try {
			
			LOGGER.info("Reading clustermanagment properties");
			props = new Properties();
			
			String jenkinsHome = System.getProperty("JENKINS_HOME");
			LOGGER.info("JENKINS HOME IS "+jenkinsHome);
			if(jenkinsHome == null) {
				throw new Exception("JENKINS_HOME not found");
			}
			propertyFile = jenkinsHome+propertyFileName;
			LOGGER.info("Properties file is "+propertyFile);
			
			propInputStream = new FileInputStream(propertyFile);
			props.load(propInputStream);
			LOGGER.info("Successfully loaded properties file: "+propertyFile);
		} catch (FileNotFoundException e) {
			LOGGER.severe("Error loading properties file: "+propertyFile);
		} catch (IOException e) {
			LOGGER.severe("Error loading properties file: "+propertyFile);
		} catch(Exception e) {
			LOGGER.severe(e.getMessage());
		}
	}
	
	public boolean isPrimaryNodeRevertMode() {
		if(props != null) {
			String flag = props.getProperty("cluster.node-revert.mode");
			return Boolean.parseBoolean(flag);
		}
		return false;
	}
	
	public String getPrimaryNodeName() {
		if(props != null) {
			return props.getProperty("cluster.node-primary");
		}
		return null;
	}
	
	public String getInitialTCPHosts() {
		if(props != null) {
			return props.getProperty("cluster.tcpping.initial_hosts");
		}
		return null;
	}
	
	public String getjenkinsUrl() {
		if(props != null) {
			return props.getProperty("jenkins.url");
		}
		return null;
	}

}
