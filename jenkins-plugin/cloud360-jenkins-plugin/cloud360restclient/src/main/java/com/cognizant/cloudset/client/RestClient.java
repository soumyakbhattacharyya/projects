package com.cognizant.cloudset.client;

import org.apache.cxf.jaxrs.client.WebClient;

import com.cognizant.cloudset.command.Command;
import com.cognizant.cloudset.constants.Cloud360Constants;
import com.cognizant.cloudset.message.Cloud360MessageExtractor;
import com.cognizant.cloudset.message.Cloud360Response;

public class RestClient {
	
	public static Object callRestService(Command command,
			String AEP_SERVICE_URL, String path, Class resultClass,
			Object input, String FORMAT) throws Exception {

		WebClient client = WebClient.create(AEP_SERVICE_URL);
		client.path(path).accept(FORMAT).type(FORMAT);
		try {
			switch (command) {
			case GET_COLLECTION:
				return client.getCollection(resultClass);				
			case GET:
				return client.get(resultClass);
			case POST:			
				return client.post(input,resultClass);
			case DELETE:
				return client.delete();
			default:
				throw new Exception("Invalid command found");
			}
		} catch (Exception e) {
			throw e;
		}

	}
	
	private Cloud360MessageExtractor messageExtractor;
	
	public RestClient(String restUrl, String userId, String password) {
		messageExtractor = new Cloud360MessageExtractor(restUrl, userId, password);
	}
	
	/**Returns list of all available clouds
	 * @return Cloud360Response object containing list of Cloud objects
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getAllClouds() {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_ALL_CLOUDS, null);
		return response;
	}
	
	/**Returns details of cloud corresponding to the cloud id
	 * @param cloudId
	 * @return Cloud360Response object containing list of a Cloud object
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getCloudDetailsById(String cloudId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_CLOUD_DETAILS_BY_ID, cloudId);
		return response;
	}
	
	/**Returns list of all cloud providers
	 * @param cloudId
	 * @return Cloud360Response object containing list of Provider objects
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getAllProviders(String cloudId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_ALL_PROVIDERS, cloudId);
		return response;
	}
	
	/**Returns details of provider corresponding to the provider id
	 * @param providerId
	 * @return Cloud360Response object containing list of a Provider object
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getProviderDetailsById(String providerId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_PROVIDER_DETAILS_BY_ID, providerId);
		return response;
	}
	
	/**Returns list of all Users for a cloud
	 * @param cloudId
	 * @return Cloud360Response object containing list of User objects
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getAllUsersForACloud(String cloudId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_ALL_USERS_FOR_A_CLOUD, cloudId);
		return response;
	}
	
	/**Returns details of a Users corresponding to user id
	 * @param userId
	 * @return Cloud360Response object containing list of a User object
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getUserDetailsById(String userId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_USER_DETAILS_BY_ID, userId);
		return response;
	}
	
	/**Returns list of all User Groups for a cloud
	 * @param cloudId
	 * @return Cloud360Response object containing list of UserGroup objects
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getAllUserGroupsForACloud(String cloudId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_ALL_USER_GROUPS_FOR_A_CLOUD, cloudId);
		return response;
	}
	
	/**Returns details of a UserGroup corresponding  to user group id
	 * @param userGroupId
	 * @return Cloud360Response object containing list of a UserGroup object
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getUserGroupDetailsById(String userGroupId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_USER_GROUP_DETAILS_BY_ID, userGroupId);
		return response;
	}
	
	/**Returns list of all Application Profiles for a cloud
	 * @param cloudId
	 * @return Cloud360Response object containing list of ApplicationProfile objects
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getListOfApplicationProfilesForACloud(String cloudId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_LIST_OF_APP_PROFILES_FOR_A_CLOUD, cloudId);
		return response;
	}
	
	/**Returns details of an ApplicationProfile corresponding to app profile id
	 * @param appProfileId
	 * @return Cloud360Response object containing list of an ApplicationProfile object
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getApplicationProfileDetailsById(String appProfileId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_APP_PROFILE_DETAILS_BY_ID, appProfileId);
		return response;
	}
	
	/**Returns list of all Applications in an cloud
	 * @param cloudId
	 * @return Cloud360Response object containing list of Application objects
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getListOfApplicationsInACloud(String cloudId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_LIST_OF_APPS_IN_A_CLOUD, cloudId);
		return response;
	}
	
	/**Returns details of an Application corresponding to appId
	 * @param appId
	 * @return Cloud360Response object containing list of an Application object
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getApplicationDetailsById(String appId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_APP_DETAILS_BY_ID, appId);
		return response;
	}
	
	/**Returns list of Compute Profiles for a cloud
	 * @param cloudId
	 * @return Cloud360Response object containing list of ComputeProfile objects
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getListOfComputeProfilesForACloud(String cloudId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_LIST_OF_COMPUTE_PROFILES_FOR_A_CLOUD, cloudId);
		return response;
	}
	
	/**Returns details of a Compute Profile corresponding to compute profile id
	 * @param computeProfileId
	 * @return Cloud360Response object containing list of a ComputeProfile object
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getComputeProfileDetailsById(String computeProfileId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_COMPUTE_PROFILE_DETAILS_BY_ID, computeProfileId);
		return response;
	}
	
	/**Creates a new Instance from a compute profile identified by computeProfileId
	 * @param computeProfileId
	 * @return Cloud360Response object containing list of Message objects
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response createComputeInstanceFromComputeProfile(String computeProfileId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.CREATE_COMPUTE_INSTANCE_FROM_COMPUTE_PROFILE, computeProfileId);
		return response;
	}

	
	/**Delete the Instance corresponding to the computeId
	 * @param computeId
	 * @return Cloud360Response object containing list of Instance object
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response deProvisionComputeInstance(String computeId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.DE_PROVISION_COMPUTE_INSTANCE, computeId);
		return response;
	}
	
	/**Starts the Instance corresponding to compute id
	 * @param computeId
	 * @return Cloud360Response object containing list of Instance objects
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response startComputeInstance(String computeId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.START_COMPUTE_INSTANCE, computeId);
		return response;
	}
	
	/**Stops the Instance corresponding to compute id
	 * @param computeId
	 * @return Cloud360Response object containing list of Instance objects
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response stopComputeInstance(String computeId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.STOP_COMPUTE_INSTANCE, computeId);
		return response;
	}
	
	/**
	 * Returns list of compute instances for a cloud or cloud provider.
	 * @param id may be cloudId or providerId
	 * @return Cloud360Response object containing list of Instance objects
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getComputeInstances(String id) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_COMPUTE_INSTANCES, id);
		return response;
	}
	
	/**Returns list of a compute instance corresponding to compute id.
	 * @param computeId
	 * @return Cloud360Response object containing list of an Instance object
	 * 			<br>and response code. SUCCESS = 8000 and ERROR = 8001
	 */
	public Cloud360Response getComputeInstanceDetailsById(String computeId) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_COMPUTE_INSTANCE_DETAILS_BY_ID, computeId);
		return response;
	}
	
	public Cloud360Response getInstanceStatus(String trackingID) {
		Cloud360Response response = messageExtractor.extractMessage(
				Cloud360Constants.GET_COMPUTE_INSTANCE_STATUS, trackingID);
		return response;
	}
	
	
	public static void main(String args[]) {
		
		/*String xml = "START<Instances><Instance><id>asdiuggasdg</id><name>MYUOP</name><deployment>Staging</deployment></Instance></Instances>";
		String xml2 = "<Instances><Instance><message>Message parsed</message></Instance></Instances>";
		Instance ins = (Instance) Cloud360MessageExtractor.populateObjectFromXML(xml, null, Instance.class).get(0);
		Message ins2 = (Message) Cloud360MessageExtractor.populateObjectFromXML(xml2, null, Message.class).get(0);
		System.err.println(ins);
		System.err.println(ins2);*/
		
		/*Cloud360Response resp = getAllClouds();
		if(resp.getResponseCode() == Cloud360Constants.SUCCESS) {
			List<Cloud> list = (List<Cloud>) resp.getResponseResult();
			Cloud c = list.get(0);
			System.out.println(c.toString());
			
			resp = getAllProviders(c.getId());
			Provider p = ((List<Provider>) resp.getResponseResult()).get(0);
			System.out.println(p.toString());
			
			resp = getAllUsersForACloud(c.getId());
			User user = ((List<User>) resp.getResponseResult()).get(0);
			System.out.println(user.toString());
			
			resp = getAllUserGroupsForACloud(c.getId());
			UserGroup ug = ((List<UserGroup>) resp.getResponseResult()).get(0);
			System.out.println(ug.toString());
		}*/
	}
		
}
