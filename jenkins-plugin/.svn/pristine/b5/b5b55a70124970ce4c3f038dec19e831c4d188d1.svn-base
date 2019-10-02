package org.jenkinsci.plugins.extendjob;

import java.util.logging.Logger;

import hudson.Launcher;	
import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.AbstractProject;
import hudson.model.Job;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;
import hudson.plugins.cloudset.rbac.authorization.CloudSetAuthorizationUserACL;
import hudson.plugins.cloudset.rbac.constants.CloudSetRbacConstants;
import hudson.tasks.Builder;
import javax.servlet.http.HttpSession;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Sample {@link Builder}.
 *
 * <p> When the user configures the project and enables this builder,
 * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked and a new
 * {@link JobPropertiesExtension} is created. The created instance is persisted
 * to the project configuration XML by using XStream, so this allows you to use
 * instance fields (like {@link #name}) to remember the configuration.
 *
 * <p> When a build is performed, the
 * {@link #perform(AbstractBuild, Launcher, BuildListener)} method will be
 * invoked.
 *
 * @author Kohsuke Kawaguchi
 */
public class JobPropertiesExtension extends JobProperty<AbstractProject<?, ?>> {

    public final String projectID;
    public final String userID;
    private static final Logger log = Logger
			.getLogger(JobPropertiesExtension.class.getName());
    
    @DataBoundConstructor
    public JobPropertiesExtension(String projectID,String userID) {

        String default_Project_Id = CloudSetRbacConstants.DEFAULT_PROJECT_ID;
        String default_User_Id = CloudSetRbacConstants.USER_ID;
        
        if (Stapler.getCurrentRequest() != null
                && Stapler.getCurrentRequest().getSession() != null) {
            HttpSession sess = Stapler.getCurrentRequest().getSession();
            if (sess != null) {
            	if(sess.getAttribute(CloudSetRbacConstants.PROJECT_ID)!= null){
            		default_Project_Id = (String)sess.getAttribute(CloudSetRbacConstants.PROJECT_ID);
            	}
            	if(sess.getAttribute(CloudSetRbacConstants.USER_ID)!= null){
            		default_User_Id = (String)sess.getAttribute(CloudSetRbacConstants.USER_ID);
            	}
            } else {
            	log.info("***********SESSION IS NULL*******");
            }
        }
        this.projectID = default_Project_Id;
        this.userID = default_User_Id;
    }

    @Extension
    public static class DescriptorImpl extends JobPropertyDescriptor {

        @Override
        public String getDisplayName() {
            return "projectID ";
        }

        @Override
        public boolean isApplicable(java.lang.Class<? extends Job> jobType) {
            return AbstractProject.class.isAssignableFrom(jobType);
        }
    }

	public String getProjectID() {
		return projectID;
	}

	public String getUserID() {
		return userID;
	}
		    
    
}
