package com.cognizant.monitor;

import hudson.model.JobProperty;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import org.jenkinsci.plugins.extendjob.JobPropertiesExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class AbortedJobsMonitor {
	
	private Logger LOGGER = Logger.getLogger(AbortedJobsMonitor.class.getName());
	
	private static AbortedJobsMonitor abortedJobsMonitor = new AbortedJobsMonitor();
	
	private static final String ABORTED_JOB_FOLDERNAME = "abortedjobs";
	private static String jenkinsHome;
	private static String abortedJobsFolder;
	
	private AbortedJobsMonitor() {
		prepareAbortedJobsLocation();
	}
	
	public static AbortedJobsMonitor getInstance() {
		return abortedJobsMonitor;
	}
	
	private void prepareAbortedJobsLocation() {
		try {
			jenkinsHome = System.getProperty("JENKINS_HOME");
			if(jenkinsHome == null) {
				throw new Exception("Jenkins home not found. Could not create directory for aborted jobs.");
			} else {
				LOGGER.info("Jenkins home is "+jenkinsHome);
				abortedJobsFolder = jenkinsHome+ABORTED_JOB_FOLDERNAME;
				File file = new File(abortedJobsFolder);
				if(file.exists() && file.isDirectory()) {
					LOGGER.info("Found directory for aborted jobs");
					return;
				}
				file.mkdir();
			}
		} catch(Exception ex) {
			LOGGER.severe(ex.getMessage());
		}
	}
	
	public void onJobStarted(AbstractBuild build) {
		File jobFile = null;
		PropertiesManager manager = new PropertiesManager();
		
		JobPropertiesExtension property = null;
		AbstractProject proj = build.getProject();
		String jobName = proj.getName();
		
		if(manager.isUsingCloudSetAuthorization()) {
			List<JobProperty> list = proj.getAllProperties();
			for(JobProperty prop:list) {
				if(prop instanceof JobPropertiesExtension) {
					property = (JobPropertiesExtension)prop;
					break;
				}
			}
			jobFile = new File(abortedJobsFolder+"/"+property.getProjectID()+"_"+jobName);	
		} else {
			jobFile = new File(abortedJobsFolder+"/"+jobName);
		}

		try {
			boolean created = jobFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onJobFinished(AbstractBuild build) {
		File jobFile = null;
		PropertiesManager manager = new PropertiesManager();
		
		JobPropertiesExtension property = null;
		AbstractProject proj = build.getProject();
		String jobName = proj.getName();
		
		if(manager.isUsingCloudSetAuthorization()) {
			List<JobProperty> list = proj.getAllProperties();
			for(JobProperty prop:list) {
				if(prop instanceof JobPropertiesExtension) {
					property = (JobPropertiesExtension)prop;
					break;
				}
			}
			jobFile = new File(abortedJobsFolder+"/"+property.getProjectID()+"_"+jobName);	
		} else {
			jobFile = new File(abortedJobsFolder+"/"+jobName);
		}
		
		if(jobFile.exists()) {
			jobFile.delete();
		}
	}
}
