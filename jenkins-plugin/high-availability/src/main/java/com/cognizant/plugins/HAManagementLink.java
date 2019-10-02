package com.cognizant.plugins;

import java.io.IOException;

import javax.servlet.ServletException;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import jenkins.model.Jenkins;
import hudson.Extension;
import hudson.model.ManagementLink;
import hudson.model.RootAction;

@Extension
public class HAManagementLink extends ManagementLink {
	
	static final String DISPLAY_NAME = "High Availability";

	public String getDisplayName() {
		return DISPLAY_NAME;
	}
	
	@Override
	public String getDescription() {
		return "High availability status of jenkins";
	}

	@Override
	public String getIconFileName() {
		return "/plugin/disk-usage/icons/diskusage48.png";
	}

	@Override
	public String getUrlName() {
		return "high-availability";
	}
	
	public void doIndex(StaplerRequest req, StaplerResponse res) throws ServletException, IOException{
		JenkinsHA plugin = Jenkins.getInstance().getPlugin(JenkinsHA.class);
		res.sendRedirect(Jenkins.getInstance().getRootUrlFromRequest() + "plugin/high-availability");
    }

}
