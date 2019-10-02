package com.cognizant.cloudset;

import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.cognizant.cloudset.client.RestClient;
import com.cognizant.cloudset.constants.Cloud360Constants;
import com.cognizant.cloudset.message.Cloud360Response;
import com.cognizant.cloudset.model.Cloud;
import com.cognizant.cloudset.model.Provider;
import com.cognizant.cloudset.model.User;
import com.cognizant.cloudset.model.UserGroup;

public class Cloud360RestTest {
	
	private Properties props;
	private String cloudId = "YzAxMDAwMDE";
	
	private String restUrl = "http://10.242.138.85:8080/cloudone-ic-services-0.0.1-SNAPSHOT/api/v1/";
	private String userId = "swastika.basu@cognizant.com";
	private String password = "password-1";

	@Test
	public void testGetAllCloud() {
		try {
			RestClient client = new RestClient(restUrl, userId, password);
			Cloud360Response resp = client.getAllClouds();
			if(resp.getResponseCode() == Cloud360Constants.SUCCESS) {
				List<Cloud> list = (List<Cloud>) resp.getResponseResult();
				
				Cloud cl = list.get(0);
				cloudId = cl.getId();
				System.out.println(cl);
				Assert.assertNotNull("Get All Clouds returning null", list);
				Assert.assertNotNull("Get All cloud list is empty", cl);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testgetCloudDetailsById() {
		try {
			RestClient client = new RestClient(restUrl, userId, password);
			Cloud360Response resp = client.getCloudDetailsById(cloudId);
			if(resp.getResponseCode() == Cloud360Constants.SUCCESS) {
				List<Cloud> list = (List<Cloud>) resp.getResponseResult();
				
				Cloud cl = list.get(0);
				System.out.println(cl);
				Assert.assertNotNull("Get Cloud details returning null", list);
				Assert.assertTrue(cloudId.equals(cl.getId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllProviders() {
		String providerId = null;
		try {
			RestClient client = new RestClient(restUrl, userId, password);
			Cloud360Response resp = client.getAllProviders(cloudId);
			if(resp.getResponseCode() == Cloud360Constants.SUCCESS) {
				List<Provider> list = (List<Provider>) resp.getResponseResult();
				
				Provider pr = list.get(0);
				System.out.println(pr);
				providerId = list.get(0).getId();
				Assert.assertNotNull("Get All Provider returning null", list);
				Assert.assertNotNull("Get All Provider list is empty", pr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetProviderDetailsById() {
		String providerId = null;
		try {
			RestClient client = new RestClient(restUrl, userId, password);
			Cloud360Response resp = client.getAllProviders(cloudId);
			if(resp.getResponseCode() == Cloud360Constants.SUCCESS) {
				List<Provider> list = (List<Provider>) resp.getResponseResult();
				
				Provider pr = list.get(0);
				providerId = pr.getId();
				
				resp = client.getProviderDetailsById(providerId);
				if(resp.getResponseCode() == Cloud360Constants.SUCCESS) {
					Assert.assertTrue(providerId.equals(((List<Provider>)resp.getResponseResult()).get(0).getId()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllUsersForACloud() {
		String userId = null;
		try {
			RestClient client = new RestClient(restUrl, userId, password);
			Cloud360Response resp = client.getAllUsersForACloud(cloudId);
			if(resp.getResponseCode() == Cloud360Constants.SUCCESS) {
				List<User> list = (List<User>) resp.getResponseResult();
				
				User ur = list.get(0);
				System.out.println(ur);
				userId = list.get(0).getId();
				Assert.assertNotNull("Get All User returning null", list);
				Assert.assertNotNull("Get All User list is empty", ur);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetUserDetailsById() {
		String userId = null;
		try {
			RestClient client = new RestClient(restUrl, userId, password);
			Cloud360Response resp = client.getAllUsersForACloud(cloudId);
			if(resp.getResponseCode() == Cloud360Constants.SUCCESS) {
				List<User> list = (List<User>) resp.getResponseResult();
				
				User ur = list.get(0);
				userId = ur.getId();
				
				resp = client.getUserDetailsById(userId);
				if(resp.getResponseCode() == Cloud360Constants.SUCCESS) {
					Assert.assertTrue(userId.equals(((List<User>)resp.getResponseResult()).get(0).getId()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllUserGroupsForACloud() {
		String userGroupId = null;
		try {
			RestClient client = new RestClient(restUrl, userId, password);
			Cloud360Response resp = client.getAllUserGroupsForACloud(cloudId);
			if(resp.getResponseCode() == Cloud360Constants.SUCCESS) {
				List<UserGroup> list = (List<UserGroup>) resp.getResponseResult();
				
				UserGroup ug = list.get(0);
				System.out.println(ug);
				userGroupId = list.get(0).getId();
				Assert.assertNotNull("Get All UserGroup returning null", list);
				Assert.assertNotNull("Get All UserGroup list is empty", ug);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetUserGroupDetailsById() {
		String userGroupId = null;
		try {
			RestClient client = new RestClient(restUrl, userId, password);
			Cloud360Response resp = client.getAllUserGroupsForACloud(cloudId);
			if(resp.getResponseCode() == Cloud360Constants.SUCCESS) {
				List<UserGroup> list = (List<UserGroup>) resp.getResponseResult();
				
				UserGroup ug = list.get(0);
				userGroupId = ug.getId();
				
				resp = client.getUserGroupDetailsById(userGroupId);
				if(resp.getResponseCode() == Cloud360Constants.SUCCESS) {
					Assert.assertTrue(userGroupId.equals(((List<UserGroup>)resp.getResponseResult()).get(0).getId()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
