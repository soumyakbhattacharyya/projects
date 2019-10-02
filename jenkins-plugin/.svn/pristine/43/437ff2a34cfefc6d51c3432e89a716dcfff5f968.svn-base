package com.cognizant.cloudset.plugins;
import hudson.Extension;
import hudson.model.Action;
import hudson.model.JobProperty;
import hudson.model.AbstractProject;
import hudson.model.Queue.QueueDecisionHandler;
import hudson.model.Queue.Task;

import java.util.List;
import java.util.logging.Logger;

import org.jenkinsci.plugins.extendjob.JobPropertiesExtension;

@Extension
public class CloudSetQueueDecisionHandler extends QueueDecisionHandler {

	Logger LOGGER = Logger.getLogger(CloudSetQueueDecisionHandler.class.getName());
	
	@Override
	public boolean shouldSchedule(Task p, List<Action> actions) {
		if(p == null) {
			LOGGER.info("No Task to schedule. Cancelling schedule.");
			return false;
		}
		
		AbstractProject project = (AbstractProject) p;
		String assignedLabel = project.getAssignedLabelString();
		
		if(assignedLabel == null) {
			LOGGER.info("Label not found. Cancelling schedule");
			return false;
		}
		
		// If no label is assigned to a job, Jenkins adds a LabelAtom object
		// named 'master' to the job at the time of job scheduling.
		if(assignedLabel.equals("master")) {
			LOGGER.info("No specific label found. Setting 'master' as label");
			PropertiesManager manager = new PropertiesManager();
			if(manager != null && manager.allowMasterAsSlave()) {
				LOGGER.info("System allows using master as slave. Scheduling job.");
				return true;	
			}
			LOGGER.info("System does not allow using master as slave. Cancelling schedule.");
			return false;
		}
		
		LOGGER.info("Label assigned for task "+assignedLabel);
		String projectIdFromLbl = extractProjectIDfromLabel(assignedLabel);
		LOGGER.info("Project id from label "+projectIdFromLbl);
		
		if(projectIdFromLbl == null) {
			LOGGER.info("No valid project id for label. Cancelling schedule.");
			return false;
		}
		
		JobPropertiesExtension propExtn = null;
		List<JobProperty> propList = project.getAllProperties();
		
		for(JobProperty property:propList) {
			if(property instanceof JobPropertiesExtension) {
				propExtn = (JobPropertiesExtension) property;
				break;
			}
		}
		
		if(propExtn == null || propExtn.getProjectID() == null) {
			LOGGER.info("No valid project id for project. Cancelling schedule.");
			return false;
		}
		
		if(propExtn.getProjectID().equals(projectIdFromLbl)) {
			LOGGER.info("Valid label for job found. Scheduling job.");
			return true;
		} else {
			LOGGER.info("Invalid label for job. Cancelling schedule");
			return false;
		}
	}
	
	private String extractProjectIDfromLabel(String lblStr) {
		if(!lblStr.contains("_") || lblStr.endsWith("_")) {
			return null;
		}
		String[] split = lblStr.split("_");
		return split[split.length-1];
	}
	
}

