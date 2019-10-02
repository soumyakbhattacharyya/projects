package hudson.plugins.cloudset.rbac.authorization;


import hudson.Extension;
import hudson.model.AbstractItem;
import hudson.model.Computer;
import hudson.model.Descriptor;
import hudson.model.Job;
import hudson.model.View;
import hudson.plugins.cloudset.rbac.constants.CloudSetRbacConstants;
import hudson.plugins.cloudset.rbac.util.RBacUtil;
import hudson.security.ACL;
import hudson.security.AuthorizationStrategy;
import hudson.security.Permission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import org.acegisecurity.Authentication;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Role-based authorization strategy.
 * 
 */
public class CloudSetRoleBasedAuthorizationStrategy extends AuthorizationStrategy {

    private final CloudSetAuthorizationUserACL rootACL;    
    private static final Logger log = Logger
            .getLogger(CloudSetRoleBasedAuthorizationStrategy.class.getName());
    
    
    @DataBoundConstructor
    public CloudSetRoleBasedAuthorizationStrategy(String rbacURL,String keyPath,String rbacPassword,String cacheTime,boolean preFlightEnabled){
        super();
        rootACL = new CloudSetAuthorizationUserACL(rbacURL, keyPath, rbacPassword,cacheTime,preFlightEnabled);
    }
    
    @Override
    public ACL getRootACL() {
        return rootACL;
    }

    @Override
    public Collection<String> getGroups() {
        return new ArrayList<String>(0);
    }
    
    @Override
    public ACL getACL(final Job<?,?> item) {
    	 return new ACL() {
             @Override
             public boolean hasPermission(Authentication a, Permission permission) {                 
                 boolean hasPermission = false;                 
                 hasPermission = RBacUtil.isJobForThisUser(item,a);
                 if(hasPermission){
                	 hasPermission = rootACL.hasPermission(a,permission);
                 }
                 return hasPermission;
             }
         };     
    }

    @Override
    public ACL getACL(AbstractItem project) {
      return new CloudSetAuthorizationUserACL(getRbacURL(), getKeyPath(), getRbacPassword(),project,getCacheTime());
    }

    @Override
    public ACL getACL(Computer computer) {
      return new CloudSetAuthorizationUserACL(getRbacURL(), getKeyPath(), getRbacPassword(),computer,getCacheTime());
    }

    @Extension
	public static final class DescriptorImpl extends
			Descriptor<AuthorizationStrategy> {

		public String getDisplayName() {
			return "CloudSet Authorization Strategy";
		}

		public String getHelpFile() {
			return "/plugin/cloudset-rbac/help/help-authorization-strategy.html";
		}
	}

    public String getRbacURL() {
        return rootACL.getRbacURL();
    }

    public String getKeyPath() {
        return rootACL.getKeyPath();
    }

    public String getRbacPassword() {
        return rootACL.getRbacPassword();
    }
    
    public String getCacheTime(){
    	return rootACL.getCacheTime();
    }
    
    public boolean getPreFlightEnabled(){
    	return rootACL.getPreFlightEnabled();
    }
        
	@Override
	public ACL getACL(final View item) {
		return new ACL() {
			@Override
			public boolean hasPermission(Authentication a, Permission permission) {
				//log.info("Item name is " + item.getDisplayName());
				ACL base = item.getOwner().getACL();
				//log.info("****************item.getDisplayName()"
				//		+ item.getDisplayName());
												
				boolean hasPermission = base.hasPermission(a, permission);
				if (!hasPermission && permission == View.READ) {
					if (item.getDisplayName()!= null && item.getDisplayName().equalsIgnoreCase("All") || item.getDisplayName().equalsIgnoreCase(CloudSetRbacConstants.VIEW_ABORT_JOB) ) {
						return true;
					} else {
						if(hasPermission = RBacUtil.isViewForThisUser(item,a)){
							return base.hasPermission(a, View.CONFIGURE)
								|| !item.getItems().isEmpty();
						}
					}
				}

				return hasPermission;
			}
		};
	}
    
    
}
