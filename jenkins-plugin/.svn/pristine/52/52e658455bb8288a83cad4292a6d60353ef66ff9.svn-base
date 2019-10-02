package com.cognizant.plugins;
import java.io.IOException;
import java.util.List;

import hudson.Plugin;
import hudson.init.InitMilestone;
import hudson.init.Initializer;
import hudson.model.View;
import jenkins.model.Jenkins;

public class RestartAbortedJobs extends Plugin {
	
	@Override
	public void start() throws Exception {
	}
	
	@Override
	public void stop() throws Exception {
	}
	
	@Initializer(after=InitMilestone.JOB_LOADED)
	public static void init() throws IOException {
		AbortedJobsNotifierView abortedJobsNotifierView = new AbortedJobsNotifierView("Aborted Jobs");
		Jenkins jenkins = Jenkins.getInstance();
		boolean found = false;
		List<View> views = (List<View>) jenkins.getViews();
		if(views != null && !views.isEmpty()) {
			for(View view:views) {
				if(view.getDisplayName().equalsIgnoreCase("Aborted Jobs")) {
					found = true;
					break;
				}
			}
		}
		if(!found) {
			jenkins.addView(abortedJobsNotifierView);
		}
	}
	
}

