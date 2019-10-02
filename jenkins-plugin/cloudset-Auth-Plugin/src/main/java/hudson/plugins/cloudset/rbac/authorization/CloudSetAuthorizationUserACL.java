/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hudson.plugins.cloudset.rbac.authorization;

import hudson.plugins.cloudset.rbac.authentication.CloudSetAuthenticationException;
import hudson.plugins.cloudset.rbac.authentication.CloudSetUserDetail;
import hudson.plugins.cloudset.rbac.constants.CloudSetRbacConstants;
import hudson.plugins.cloudset.rbac.util.ACLCache;
import hudson.plugins.cloudset.rbac.util.RBacUtil;
import hudson.security.ACL;
import hudson.security.AccessControlled;
import hudson.security.Permission;
import hudson.security.SecurityRealm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.commons.collections.MapUtils;
import org.kohsuke.stapler.Stapler;

/**
 * 
 * @author 272959
 */
public class CloudSetAuthorizationUserACL extends ACL {

	private static final Logger log = Logger
			.getLogger(CloudSetAuthorizationUserACL.class.getName());
	private String rbacURL;
	private String keyPath;
	private String rbacPassword;
	private String cacheTime;
	private AccessControlled item;
	private boolean preFlightEnabled; 
	
	@Override
	public boolean hasPermission(Authentication a, Permission prmsn) {
		if (rbacURL != null) {
			try {

				String permissionImplied = prmsn.getId();
				//log.info("===============================================permissionImplied is "+permissionImplied);
				String projectID = "";
				String role = null;
				
				CloudSetUserDetail cloudUser = null;
				
				if(a.getPrincipal() instanceof CloudSetUserDetail){					
					cloudUser = (CloudSetUserDetail) a.getPrincipal();
					projectID = cloudUser.getProjectId();
					
				}
				else if(a instanceof UsernamePasswordAuthenticationToken && a.getAuthorities()!= null && a.getAuthorities().length > 1){
					UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken)a;
					GrantedAuthority[] authList = user.getAuthorities();
					for(GrantedAuthority auth:authList){
						if(auth!= SecurityRealm.AUTHENTICATED_AUTHORITY){
							projectID = auth.getAuthority();							
							cloudUser = new CloudSetUserDetail((String)a.getPrincipal(), "", projectID, true, 
									true, true, true, authList, false);
						}
					}
				}
				

				String authenticatedUserName = a.getName();
				// log.info("Username ----------" + authenticatedUserName);
				// log.info("cloudUser ProjectID " + cloudUser);

				if (Stapler.getCurrentRequest() != null
						&& Stapler.getCurrentRequest().getSession() != null) {
					HttpSession sess = Stapler.getCurrentRequest().getSession();
					if (sess != null && cloudUser != null
							&& cloudUser.getProjectId() != null) {
						sess.setAttribute(CloudSetRbacConstants.PROJECT_ID,
								cloudUser.getProjectId());
						sess.setAttribute(CloudSetRbacConstants.USER_ID,
								authenticatedUserName);

						if (isPreFlightEnabled()) {
							// get projectRole and save in Session.
							try {
								Set<String> roleList = RBacUtil.getUserRoles(
										authenticatedUserName, projectID,
										rbacURL, keyPath, rbacPassword,
										CloudSetRbacConstants.BAAS_SERVICE_ID);
								if (roleList == null || roleList.isEmpty()) {
									roleList = RBacUtil
											.getUserRoles(
													authenticatedUserName,
													projectID,
													rbacURL,
													keyPath,
													rbacPassword,
													CloudSetRbacConstants.PRE_FLIGHT_SERVICE_ID);

								}
								role = RBacUtil.getBaaSSpecificRole(roleList);
								if (role != null)
									sess.setAttribute(
											CloudSetRbacConstants.USER_ROLE,
											role);
							} catch (Exception e) {
								log.severe("Error occured while getting Roles for the user");
							}
						}

					}
				}
				
				
				
				if(authenticatedUserName.equalsIgnoreCase(CloudSetRbacConstants.ANONYMOUS)){
						return false;
				}	
				
				if (authenticatedUserName
						.equalsIgnoreCase(ACL.SYSTEM.getName())) {
					return true;
				}
				if (cloudUser == null || projectID == null
						|| projectID.equalsIgnoreCase("null")
						|| projectID.trim().isEmpty()) {
					return false;
				}
				
				//PreFligt Check
				if(role!= null && isPreFlightEnabled() && role.equalsIgnoreCase(CloudSetRbacConstants.PRE_FLIGHT_RELEASE_MANAGER)){
					return chechForPreFlightBuild(projectID,
							authenticatedUserName, permissionImplied);
				}
				
				//log.info("PreFlight Build Enabled :: " +isPreFlightEnabled());
				//log.info("Permission request is :: "+permissionImplied);
				//log.info("Is call from PreFlight :: " +cloudUser.isCliUser());

				if (cloudUser.isCliUser()) {
					if (isPreFlightEnabled()) {
						return chechForPreFlightBuild(projectID,
								authenticatedUserName, permissionImplied);
					} else {
						return false;
					}
				}
				
				Boolean isAuthorized = false;
				ACLCache aclCache = ACLCache.getThis(cacheTime);
				// Does cache has project permission map for the user
				Map<String, Map> projectPermissionMap = (Map<String, Map>) aclCache
						.get(authenticatedUserName);

												
				if (MapUtils.isEmpty(projectPermissionMap)) {

					projectPermissionMap = checkAndAddPermission(
							projectPermissionMap, authenticatedUserName,
							projectID, rbacURL, keyPath,
							rbacPassword, permissionImplied);
					aclCache.put(authenticatedUserName, projectPermissionMap);
					return (projectPermissionMap != null);
				} else {
					Map<String, Boolean> permissionMap = (Map<String, Boolean>) projectPermissionMap
							.get(projectID);
					if (permissionMap == null) {
						projectPermissionMap = checkAndAddPermission(
								projectPermissionMap, authenticatedUserName,
								projectID, rbacURL, keyPath,
								rbacPassword, permissionImplied);
						return (projectPermissionMap != null);
					} else {
						Boolean status = permissionMap.get(permissionImplied);
						if (null == status) {
//							log.info("######################Permission Level Cache miss!!#############################");
//							log.info("Cache miss for the permission implied "
//									+ permissionImplied);
							// This is the first time we are trying to find the
							// authorization for the permission implied
							// Get the authorization
							List<String> permissionList = RBacUtil
									.getUserPermission(authenticatedUserName,
											projectID, rbacURL, keyPath,
											rbacPassword,CloudSetRbacConstants.BAAS_SERVICE_ID);
							for (String permiString : permissionList) {
								if (permissionImplied
										.equalsIgnoreCase(permiString)) {
									isAuthorized = true;
									break;
								} else {
									isAuthorized = false;
								}
							}
							// Save to the map
							permissionMap.put(permissionImplied, isAuthorized);
							// Print Map after placing new finding
							log.info("Permission map after update "
									+ aclCache.getObjects());
							// Return
							return isAuthorized;
						} else {
							// LOGGER.info("User "+userId+" has permission for "+permissionImplied+" - "+isAuthorized);
							isAuthorized = status;
							return isAuthorized;
						}
					}
				}

			} catch (Exception ex) {
				Logger.getLogger(CloudSetAuthorizationUserACL.class.getName())
						.log(Level.SEVERE, null, ex);
				throw new CloudSetAuthenticationException(
						"Invalid Cloudset User");
			}
		}
		return false;
	}
	
	/**
	 * checks for enabling or disabling pre-flight build
	 * @param projectID
	 * @param authenticatedUserName
	 * @param permissionImplied
	 * @return
	 * @throws Exception
	 */
	private boolean chechForPreFlightBuild(String projectID,String authenticatedUserName,String permissionImplied) throws Exception{
		//Get the Authorization for CLI 
		List<String> permissionListForCLI =null;
		boolean hasPreFlightPermission=false;								
				permissionListForCLI= RBacUtil
						.getUserPermission(authenticatedUserName,
								projectID, rbacURL, keyPath,
								rbacPassword,CloudSetRbacConstants.PRE_FLIGHT_SERVICE_ID);
			if(permissionListForCLI!= null && !permissionListForCLI.isEmpty()){
				for(String prmmsns : permissionListForCLI){
					log.info("CLI >>>>>>>>> " +prmmsns);
					if(prmmsns.equalsIgnoreCase(permissionImplied)){
						log.info("CLI >>>>>>>>> permission no. "+permissionListForCLI.indexOf(prmmsns)+"--" +prmmsns +" Found match with required "+permissionImplied);
						hasPreFlightPermission= true;
						break;
					}
				}
			}else{
				return hasPreFlightPermission;
			}
			return hasPreFlightPermission;
		}
	
	public Map<String, Map> checkAndAddPermission(
			Map<String, Map> projectPermissionMap,
			String authenticatedUserName, String projectID, String rbacURL,
			String keyPath, String rbacPassword, String permissionImplied)
			throws Exception {
		Boolean isAuthorized = false;
		List<String> permissionList = RBacUtil.getUserPermission(
				authenticatedUserName, projectID, rbacURL, keyPath,
				rbacPassword,CloudSetRbacConstants.BAAS_SERVICE_ID);
		for (String permiString : permissionList) {
			if (permissionImplied.equalsIgnoreCase(permiString)) {
				isAuthorized = true;
				Map<String, Boolean> permissionMap = new HashMap<String, Boolean>();
				permissionMap.put(permissionImplied, isAuthorized);
				if(projectPermissionMap == null){
					projectPermissionMap = new HashMap<String, Map>();
				}
				projectPermissionMap.put(projectID, permissionMap);
				return projectPermissionMap;
			}
		}
		return projectPermissionMap;
	}

	public CloudSetAuthorizationUserACL(String rbacURL, String keyPath,
			String rbacPassword, String cacheTime,boolean preFlightEnabled) {
		super();
		this.rbacURL = rbacURL;
		this.keyPath = keyPath;
		this.rbacPassword = rbacPassword;
		this.cacheTime = cacheTime;
		this.preFlightEnabled=preFlightEnabled;
	}

	public CloudSetAuthorizationUserACL(String rbacURL, String keyPath,
			String rbacPassword, AccessControlled item, String cacheTime) {
		super();
		this.rbacURL = rbacURL;
		this.keyPath = keyPath;
		this.rbacPassword = rbacPassword;
		this.item = item;
		this.cacheTime = cacheTime;
	}

	public String getRbacURL() {
		return rbacURL;
	}

	public String getKeyPath() {
		return keyPath;
	}

	public String getRbacPassword() {
		return rbacPassword;
	}

	public String getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(String cacheTime) {
		this.cacheTime = cacheTime;
	}
	public boolean getPreFlightEnabled() {
		return preFlightEnabled;
	}
	public boolean isPreFlightEnabled(){
		return getPreFlightEnabled();
	}
}
