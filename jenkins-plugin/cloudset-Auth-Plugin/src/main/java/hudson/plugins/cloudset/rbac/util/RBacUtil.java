/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hudson.plugins.cloudset.rbac.util;

import hudson.model.Job;
import hudson.model.View;
import hudson.plugins.cloudset.rbac.authentication.CloudSetUserDetail;
import hudson.plugins.cloudset.rbac.constants.CloudSetRbacConstants;
import hudson.security.ACL;
import hudson.security.SecurityRealm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.jenkinsci.plugins.extendjob.JobPropertiesExtension;
import org.jenkinsci.plugins.extendview.ViewProjectProperties;
import org.kohsuke.stapler.Stapler;

import com.cognizant.jpaas.rbacclient.RbacClientUtil;
import com.cognizant.jpaas.service.definations.AuthenticateUserRequest;
import com.cognizant.jpaas.service.definations.AuthenticateUserResponse;
import com.cognizant.jpaas.service.definations.GetServiceRolesForUserRequest;
import com.cognizant.jpaas.service.definations.GetServiceRolesForUserResponse;
import com.cognizant.jpaas.service.definations.GetUserPermissionsForServiceRequest;
import com.cognizant.jpaas.service.definations.GetUserPermissionsForServiceResponse;
import com.cognizant.jpaas.service.definations.GetUserProjectsRequest;
import com.cognizant.jpaas.service.definations.GetUserProjectsResponse;
import com.cognizant.jpaas.service.definations.ProjectType;
import com.cognizant.jpaas.service.definations.RoleType;
import com.cognizant.jpaas.service.definations.ServiceItemActionType;

/**
 * 
 * @author 272959
 */
public class RBacUtil {

	private static final Logger log = Logger
			.getLogger(RBacUtil.class.getName());

	public static boolean isUserAuthenticated(String username, String password,
			String myRbacServerURL, String securityKeyfilePath,
			String rbacPassword) throws Exception {

		AuthenticateUserResponse response = null;
		try {
			checkIfSecurityEnabled(myRbacServerURL, securityKeyfilePath,
					rbacPassword);
			RbacClientUtil rbacClientUtil = RbacClientUtil
					.getInstance(myRbacServerURL);
			AuthenticateUserRequest request = new AuthenticateUserRequest();

			request.setUserId(username);
			request.setPassword(password);		

			response = rbacClientUtil.authenticateUser(request);
		} catch (Exception e) {
			throw e;
		}
		return response.isAuthenticated();
	}

	private static void checkIfSecurityEnabled(String myRbacServerURL,
			String securityKeyfilePath, String rbacPassword) {

		if (myRbacServerURL.startsWith("https")) {
			SecurityUtil.applySSLInformation(securityKeyfilePath, rbacPassword);
		}

	}

	/**
	 * Call the Rbac web service to get the list of permission
	 * for a user with a specific userid , projectid and
	 * Service id 
	 * 
	 * @param username
	 * @param projectID
	 * @param myRbacServerURL
	 * @param securityKeyfilePath
	 * @param rbacPassword
	 * @return
	 * @throws Exception
	 */
	public static List<String> getUserPermission(String username,
			String projectID, String myRbacServerURL,
			String securityKeyfilePath, String rbacPassword,int ServiceID) throws Exception {

		GetUserPermissionsForServiceResponse response = null;
		List<String> permissionList = new ArrayList<String>();

		try {
			checkIfSecurityEnabled(myRbacServerURL, securityKeyfilePath,
					rbacPassword);
			RbacClientUtil rbacClientUtil = RbacClientUtil
					.getInstance(myRbacServerURL);
			GetUserPermissionsForServiceRequest request = new GetUserPermissionsForServiceRequest();

			try {
				 request.setUserId(username);
				//request.setUserId(Integer.parseInt(username));
			} catch (Exception e) {
				log.severe(" Number format exception");
				return new ArrayList<String>();
			}
			request.setProjectId(projectID);
			request.setServiceId(ServiceID);			
			response = rbacClientUtil.getUserServicePermission(request);
			for (ServiceItemActionType serviceItem : response
					.getServiceItemActions()) {
				permissionList.add(serviceItem.getServiceItemName());
			}
		} catch (Exception e) {
			throw e;
		}
		return permissionList;
	}
	
	
//	/**
//	 * Call the Rbac web service to get the list of permission
//	 * for a user with a specific userid , projectid and
//	 * Service id for 
//	 * 
//	 * @param username
//	 * @param projectID
//	 * @param myRbacServerURL
//	 * @param securityKeyfilePath
//	 * @param rbacPassword
//	 * @return
//	 * @throws Exception
//	 */
//	public static List<String> getUserPermissionForCLI(String username,
//			String projectID, String myRbacServerURL,
//			String securityKeyfilePath, String rbacPassword) throws Exception {
//
//		GetUserPermissionsForServiceResponse response = null;
//		List<String> permissionList = new ArrayList<String>();
//
//		try {
//			log.info("Getting User Permission For CLI User ----------- ");
//			checkIfSecurityEnabled(myRbacServerURL, securityKeyfilePath,
//					rbacPassword);
//			RbacClientUtil rbacClientUtil = RbacClientUtil
//					.getInstance(myRbacServerURL);
//			GetUserPermissionsForServiceRequest request = new GetUserPermissionsForServiceRequest();
//
//			
//			try {
//				request.setUserId(Integer.parseInt(username));
//			} catch (Exception e) {
//				log.severe(" Number format exception");
//				return new ArrayList<String>();
//			}
//			request.setProjectId(projectID);
//			request.setServiceId(CloudSetRbacConstants.PRE_FLIGHT_SERVICE_ID);
//
//			response = rbacClientUtil.getUserServicePermission(request);
//			log.info("SERVICE_NAME will be retrieved for ----------- "+CloudSetRbacConstants.PRE_FLIGHT_SERVICE_ID);
//			for (ServiceItemActionType serviceItem : response
//					.getServiceItemActions()) {
//				permissionList.add(serviceItem.getServiceItemName());
//			}
//		} catch (Exception e) {
//			throw e;
//		}
//		return permissionList;
//	}
	
	
	public static String getBaaSSpecificRole(Set<String> roleList) throws Exception {
		String baasRole = "default";				
		//Set<String> roleList = getUserRoles(username,projectId,myRbacServerURL,securityKeyfilePath,rbacPassword,serviceId);
		for(String role:roleList){
			if(role!=null){
				for(Roles jenkineRole:Roles.values()){
					if(role.equalsIgnoreCase(jenkineRole.name())){
						baasRole = role;
					}
				}				
			}
		}
		return baasRole;
	}
	
	public static Set<String> getUserRoles(String username, String projectId,
			String myRbacServerURL, String securityKeyfilePath,
			String rbacPassword, int serviceId) throws Exception {

		Set<String> rolesList = new HashSet<String>();
		checkIfSecurityEnabled(myRbacServerURL, securityKeyfilePath,
				rbacPassword);
		RbacClientUtil rbacClientUtil = RbacClientUtil
				.getInstance(myRbacServerURL);
		GetServiceRolesForUserResponse roles = new GetServiceRolesForUserResponse();
		GetServiceRolesForUserRequest roleRequest = new GetServiceRolesForUserRequest();
		roleRequest.setProjectId(projectId);
		roleRequest.setServiceId(serviceId);
		roleRequest.setUserId(username);
		roles = rbacClientUtil.getServiceRolesForUser(roleRequest);

		if (roles != null && roles.getRoles() != null
				&& !roles.getRoles().isEmpty()) {
			for (RoleType role : roles.getRoles()) {
				rolesList.add(role.getName());
			}
		}
		return rolesList;
	}
	
	
	public static Set<String> getUserProjects(String username,
			String myRbacServerURL, String securityKeyfilePath,
			String rbacPassword) throws Exception {

		SortedSet<String> userProjectList = new TreeSet<String>();
		GetUserProjectsResponse response = null;
		try {
			checkIfSecurityEnabled(myRbacServerURL,securityKeyfilePath,rbacPassword);
			RbacClientUtil rbacClientUtil = RbacClientUtil
					.getInstance(myRbacServerURL);
			GetUserProjectsRequest request = new GetUserProjectsRequest();

			 request.setUserId(username);
			//request.setUserId(Integer.parseInt(username));
			response = rbacClientUtil.getProjects(request);
			List<ProjectType> projectList = response.getProjects();

			// check if the project has some roles associated with the user
			for (ProjectType project : projectList) {
				GetServiceRolesForUserResponse roles = new GetServiceRolesForUserResponse();
				GetServiceRolesForUserRequest roleRequest = new GetServiceRolesForUserRequest();
				roleRequest.setProjectId(project.getExternalId());
				roleRequest.setServiceId(CloudSetRbacConstants.BAAS_SERVICE_ID);
				roleRequest.setUserId(username);
				roles = rbacClientUtil.getServiceRolesForUser(roleRequest);
								
				if (roles != null && roles.getRoles() != null
						&& !roles.getRoles().isEmpty()) {
					userProjectList.add(project.getName()+"("+project.getExternalId()+")");
				}
				
				// get the list of project for which the user has Pre-Flight-Release-Manager Role 
				roleRequest.setServiceId(CloudSetRbacConstants.PRE_FLIGHT_SERVICE_ID);
				roles = rbacClientUtil.getServiceRolesForUser(roleRequest);
				if(roles != null && roles.getRoles() != null
						&& !roles.getRoles().isEmpty()){
					for(RoleType preflightRole:roles.getRoles()){
						if(preflightRole!= null && preflightRole.getName()!= null &&
								preflightRole.getName().trim().equalsIgnoreCase(CloudSetRbacConstants.PRE_FLIGHT_RELEASE_MANAGER)){
							userProjectList.add(project.getName()+"("+project.getExternalId()+")");						
						}
					}
				}	

			}
		} catch (Exception e) {
			throw e;
		}
		return userProjectList;
	}

//	public static boolean isItemForThisProject(TopLevelItem item) {
//		Collection<? extends Job> jobs = item.getAllJobs();
//		boolean isReleatedJob = false;
//		for (Job job : jobs) {
////			log.info("**************Properites are " + job.getProperties());
//			if (job.getProperties().isEmpty()) {
//				return true;
//			}
//			Iterator iter = job.getProperties().entrySet().iterator();
//			while (iter.hasNext()) {
//				Map.Entry pairs = (Map.Entry) iter.next();
//				//log.info(pairs.getKey() + " = " + pairs.getValue());
//				if (pairs.getValue() instanceof org.jenkinsci.plugins.extendjob.JobPropertiesExtension) {
//					//log.info("The value details ");
//					JobPropertiesExtension jobProperites = (org.jenkinsci.plugins.extendjob.JobPropertiesExtension) pairs
//							.getValue();
//					//log.info("Project details " + jobProperites.projectID);
//					if (jobProperites.getProjectID().equals(
//							getProjectIDFromSession())) {
//						isReleatedJob = true;
//					} else {
//						isReleatedJob = false;
//					}
//				}
//			}
//		}
//		return isReleatedJob;
//	}
	
	public static boolean isViewForThisUser(View item,Authentication a) {

		boolean isReleatedJob = false;
		//log.info("**************Properites are " + job.getProperties());
						
		if(a.getPrincipal().equals(ACL.SYSTEM.getName())){
			return true;
		}
		
		if (item.getProperties() == null || item.getProperties().isEmpty()) {
			isReleatedJob = true;
		} else {
			Iterator iter = item.getAllProperties().iterator();
			while (iter.hasNext()) {
				Object obj =  iter.next();
				//log.info(pairs.getKey() + " = " + pairs.getValue());
				if (obj instanceof org.jenkinsci.plugins.extendview.ViewProjectProperties) {
					//log.info("The value details ");					
					ViewProjectProperties viewProperites = (org.jenkinsci.plugins.extendview.ViewProjectProperties) obj;
					//log.info("Project details " + jobProperites.projectID);
					if (viewProperites.getProjectID().equals(
							getProjectIDFromSession(a))) {
						isReleatedJob = true;
					} else {
						isReleatedJob = false;
					}
				}
			}
		}
		return isReleatedJob;
	}

	public static boolean isJobForThisUser(Job job,Authentication a) {

		boolean isReleatedJob = false;
		//log.info("**************Properites are " + job.getProperties());
		if(a.getPrincipal().equals(ACL.SYSTEM.getName())){
			return true;
		}
		if (job.getProperties() == null || job.getProperties().isEmpty()) {
			isReleatedJob = true;
		} else {
			Iterator iter = job.getProperties().entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry pairs = (Map.Entry) iter.next();
				//log.info(pairs.getKey() + " = " + pairs.getValue());
				if (pairs.getValue() instanceof org.jenkinsci.plugins.extendjob.JobPropertiesExtension) {
					//log.info("The value details ");
					JobPropertiesExtension jobProperites = (org.jenkinsci.plugins.extendjob.JobPropertiesExtension) pairs
							.getValue();
					//log.info("Project details " + jobProperites.projectID);
					if (jobProperites.getProjectID().equals(
							getProjectIDFromSession(a))) {
						isReleatedJob = true;
					} else {
						isReleatedJob = false;
					}
				}
			}
		}
		return isReleatedJob;
	}

	public static String getProjectIDFromSession(Authentication a) {

		String default_Project_Id = CloudSetRbacConstants.DEFAULT_PROJECT_ID;
		if (Stapler.getCurrentRequest() != null
				&& Stapler.getCurrentRequest().getSession() != null) {
			HttpSession sess = Stapler.getCurrentRequest().getSession();
			if (sess != null
					&& sess.getAttribute(CloudSetRbacConstants.PROJECT_ID) != null) {
				default_Project_Id = (String) sess
						.getAttribute(CloudSetRbacConstants.PROJECT_ID);
			} else {
				log.severe("***********SESSION IS NULL*******");
			}
		}else if(a!= null && a.getPrincipal()!= null && a.getPrincipal() instanceof CloudSetUserDetail){
			try{
				CloudSetUserDetail cloudUser = (CloudSetUserDetail) a.getPrincipal();
				if(cloudUser != null && cloudUser.getProjectId()!= null)
					default_Project_Id = cloudUser.getProjectId();
				
			}catch(Exception e){
				default_Project_Id = CloudSetRbacConstants.DEFAULT_PROJECT_ID;
			}
		}else if(a.getPrincipal()!= null && a.isAuthenticated()){
			// This will be executed for CLI
			String userName = (String)a.getPrincipal();
			for(GrantedAuthority grantAuth : a.getAuthorities()){
				if(grantAuth!= SecurityRealm.AUTHENTICATED_AUTHORITY){					
					default_Project_Id = grantAuth.getAuthority();
				}
			}
		}
		return default_Project_Id;
	}
	
	/**
	 * ByDefault we show the Project ExternalID and Project Details in
	 * the dropdown. We need to extract the projectid/project external id from
	 * the given complete String
	 * 
	 */
	public static String getProjectExternalID(String completeProjectDetails) {
		String startBracket = "(";
		String endBracket = ")";
		String externalId = completeProjectDetails;

		try {
			if (completeProjectDetails != null
					&& completeProjectDetails.contains(startBracket)
					&& completeProjectDetails.contains(endBracket)) {
				int startIndex = completeProjectDetails.indexOf(startBracket);
				int endIndex = completeProjectDetails.indexOf(endBracket);			
				externalId = completeProjectDetails.substring(startIndex + 1,
						endIndex);
			}
		} catch (Exception e) {

		}
		
		return externalId;
	}
}
