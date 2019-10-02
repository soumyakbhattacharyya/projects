package org.jenkinsci.plugins.extendview;

import hudson.Extension;
import hudson.model.View;
import hudson.model.ViewProperty;
import hudson.model.ViewPropertyDescriptor;
import hudson.plugins.cloudset.rbac.constants.CloudSetRbacConstants;

import javax.servlet.http.HttpSession;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.Stapler;

public class ViewProjectProperties extends ViewProperty{

	 public final String projectID;

	    @DataBoundConstructor
	    public ViewProjectProperties(final String projectID) {

	        String default_Project_Id = CloudSetRbacConstants.DEFAULT_PROJECT_ID;
	        if (Stapler.getCurrentRequest() != null
	                && Stapler.getCurrentRequest().getSession() != null) {
	            HttpSession sess = Stapler.getCurrentRequest().getSession();
	            if (sess != null && sess.getAttribute(CloudSetRbacConstants.PROJECT_ID)!= null) {
	                default_Project_Id = (String)sess.getAttribute(CloudSetRbacConstants.PROJECT_ID);
	            } else {
	                System.out.println("***********SESSION IS NULL*******");
	            }
	        }
	        this.projectID = default_Project_Id;
	    }
	    
	    
	    
	    @Extension
	    public static class DescriptorImpl extends ViewPropertyDescriptor {

	        @Override
	        public String getDisplayName() {
	            return "Project Specific";
	        }
	        
	        @Override
	        public boolean isEnabledFor(View view) {
	            return true;
	        }	        
	       	       
	    }

		public String getProjectID() {
			return projectID;
		}
	
}
