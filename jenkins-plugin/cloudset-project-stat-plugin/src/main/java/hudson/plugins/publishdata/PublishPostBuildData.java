package hudson.plugins.publishdata;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.plugins.publishdata.dto.BuildDataDTO;
import hudson.plugins.publishdata.dto.JPaaSDBServiceInfo;
import hudson.plugins.publishdata.util.JPaaSDBUtil;
import hudson.plugins.publishdata.util.JPaasBuildServiceProviderUtil;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import java.io.IOException;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

/**
 * This Plugin can be used to Post the build log summary into Database
 * @author 309657
 *
 */
public class PublishPostBuildData extends Notifier{

	
	final static Logger logger = Logger.getLogger(PublishPostBuildData.class.getName());
	
	@DataBoundConstructor
	public PublishPostBuildData(){}
	
	
	@Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
            throws InterruptedException, IOException {
		String url=null;
		String keyStore=null;
		String userPassword=null;
        	if(getDescriptor().getBaasBuildLogDetailsServiceUrl()!=null &&
        			getDescriptor().getBaasBuildLogDetailsServiceKeyStore()!=null && 
        				getDescriptor().getBaasBuildLogDetailsServicePasswd() !=null){ 		

        		url=getDescriptor().getBaasBuildLogDetailsServiceUrl();
        		keyStore=getDescriptor().getBaasBuildLogDetailsServiceKeyStore();
        		userPassword = getDescriptor().getBaasBuildLogDetailsServicePasswd();
        		
        		JPaaSDBServiceInfo dbInfo = new JPaaSDBServiceInfo();
        		
        		dbInfo.setBuildLogDetailsServiceKeyStore(keyStore);
        		dbInfo.setBuildLogDetailsServicePasswd(userPassword);
        		dbInfo.setBuildLogDetailsServiceUrl(url);
        		 		
        		BuildDataDTO buildData = JPaasBuildServiceProviderUtil.populateBuildData(build);
        		buildData = JPaasBuildServiceProviderUtil.setProjectNUserId(build,buildData);
        		
        		int response =JPaaSDBUtil.postBuildData(buildData, dbInfo);
        		
        		logger.info("DB Information----- " +dbInfo.toString());
        		logger.info("Build Log Information----- " +buildData.toString());
        		if(response==1){
        			logger.info("---Build Data has been posted successfully---");
        			listener.getLogger().println("--------------------");
        			listener.getLogger().println("---Build Data has been posted successfully---");
        		}else{
        			logger.info("---Build Data post failed---");
        			listener.getLogger().println("--------------------");
        			listener.getLogger().println("---Build Data post failed---");
        		}
        		
        		
        	}else{
        		listener.getLogger().println("Service UrlEndPoint, KeyStore, Password cannot be blank");
        	}
        	
        	return true;
    }
	
	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.NONE;
	}
	
	@Override
    public DescriptorImplementation getDescriptor() {
        return (DescriptorImplementation)super.getDescriptor();
    }

	
	 	@Extension
	    public static final class DescriptorImplementation extends BuildStepDescriptor<Publisher> {
	        /**
	         * Baas build information to publish data 
	         */
	        private String baasBuildLogDetailsServiceUrl;
	        private String baasBuildLogDetailsServiceKeyStore;
	        private String baasBuildLogDetailsServicePasswd;

	       
	        public DescriptorImplementation() {
	            load();
	        }
	        /**
	         * This name in the configuration screen.
	         */
	        public String getDisplayName() {
	            return "Publish Post Build Data";
	        }
	        
	        @Override
			public boolean isApplicable(Class<? extends AbstractProject> arg0) {
				return true;
			}
	        
	        @Override
	        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
	          
	        	baasBuildLogDetailsServiceUrl = formData.getString("baasBuildLogDetailsServiceUrl");
	        	baasBuildLogDetailsServiceKeyStore = formData.getString("baasBuildLogDetailsServiceKeyStore");
	        	baasBuildLogDetailsServicePasswd = formData.getString("baasBuildLogDetailsServicePasswd");
	        	
	            save();
	            return super.configure(req,formData);
	        }

	        public String getBaasBuildLogDetailsServiceUrl() {
	            return baasBuildLogDetailsServiceUrl;
	        }
	        public String getBaasBuildLogDetailsServiceKeyStore() {
	            return baasBuildLogDetailsServiceKeyStore;
	        }
	        public String getBaasBuildLogDetailsServicePasswd() {
	            return baasBuildLogDetailsServicePasswd;
	        }
			
	    }
}
