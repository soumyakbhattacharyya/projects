package com.cognizant.cloudset.client;

import com.cognizant.cloudset.bean.AppEnvInstance;
import com.cognizant.cloudset.bean.AppEnvMaster;

import org.apache.cxf.jaxrs.client.WebClient;

import com.cognizant.cloudset.command.Command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class RestClient {

	public static Object callRestService(Command command,
			String AEP_SERVICE_URL, String path, Class resultClass,
			Object input, String FORMAT) throws Exception {

		WebClient client = null;
		boolean useProxy = (null != System.getProperty(Constants.MandatoryParam.USE_PROXY)) && System.getProperty(Constants.MandatoryParam.USE_PROXY).equalsIgnoreCase("Y") ? true : false;
		if (useProxy) {
			System.out.println("PATH IS ***** " + AEP_SERVICE_URL + path);
			String proxyServer = System.getProperty(Constants.OptionalParam.PROXY_SERVER);
			int proxyServerPort = Integer.parseInt(System.getProperty(Constants.OptionalParam.PROXY_SERVER_PORT));
			String proxyUid = System.getProperty(Constants.OptionalParam.PROXY_UID);
			String proxyPwd = System.getProperty(Constants.OptionalParam.PROXY_PWD);
			client = WebClient.create(AEP_SERVICE_URL, proxyUid, proxyPwd, null);

			System.out.println("Client is "+client);
			String authorizationHeader = "Basic " + org.apache.cxf.common.util.Base64Utility.encode((proxyUid + ":" + proxyPwd).getBytes());

			// proxies
			//WebClient.client(proxy).header("Authorization", authorizationHeader);

			// web clients
			//client.header("Authorization", authorizationHeader);

			HTTPConduit conduit = (HTTPConduit) WebClient.getConfig(client).getConduit();
			HTTPClientPolicy policy = conduit.getClient();
			policy.setProxyServer(proxyServer);
			policy.setProxyServerPort(proxyServerPort);

			policy.setConnectionTimeout(36000);
			policy.setAllowChunking(false);

		} else {
			System.out.println("PATH IS ***** " + AEP_SERVICE_URL + path);
			client = WebClient.create(AEP_SERVICE_URL);
			System.out.println("Client is "+client);
		}

		client.path(path).accept(FORMAT).type(FORMAT);

		try {
			switch (command) {
				case GET_COLLECTION:
					return client.getCollection(resultClass);
				case GET:
					return client.get(resultClass);
				case POST:
					return client.post(input, resultClass);
				case DELETE:
					return client.delete();
				default:
					throw new Exception("Invalid command found");
			}
		} catch (Exception e) {
			throw e;
		}

	}

	public static void main(String args[]) throws Exception {

		String task = System.getProperty(Constants.TASK);

		if (Constants.Activity.LIST.equals(task)) {
			listProfiles();
		} else if (Constants.Activity.START.equals(task)) {
			String profileId = System.getProperty(Constants.OptionalParam.PROFILE_ID);
			String warFileLocation = System.getProperty(Constants.OptionalParam.WAR_FILE_LOCATION);
			if (StringUtils.isEmpty(profileId) || StringUtils.isEmpty(warFileLocation)) {
				throw new RuntimeException("profile id and / or binary location is empty");
			}
			start(profileId, warFileLocation);
		}else if (Constants.Activity.START_AND_WAIT.equals(task)) {
			String profileId = System.getProperty(Constants.OptionalParam.PROFILE_ID);
			String warFileLocation = System.getProperty(Constants.OptionalParam.WAR_FILE_LOCATION);
			if (StringUtils.isEmpty(profileId) || StringUtils.isEmpty(warFileLocation)) {
				throw new RuntimeException("profile id and / or binary location is empty");
			}
			startAndWait(profileId, warFileLocation);
		} else if (Constants.Activity.STOP.equals(task)) {
			String profileId = System.getProperty(Constants.OptionalParam.PROFILE_ID);
			String appInstanceId = System.getProperty(Constants.OptionalParam.APP_INSTANCE_ID);
			if (StringUtils.isEmpty(profileId) || StringUtils.isEmpty(appInstanceId)) {
				throw new RuntimeException("profile id and / or app instance id is empty");
			}
			stop(profileId, appInstanceId);
		} else if (Constants.Activity.STATUS.equals(task)) {
			String profileId = System.getProperty(Constants.OptionalParam.PROFILE_ID);
			String appInstanceId = System.getProperty(Constants.OptionalParam.APP_INSTANCE_ID);
			if (StringUtils.isEmpty(profileId) || StringUtils.isEmpty(appInstanceId)) {
				throw new RuntimeException("profile id and / or app instance id is empty");
			}
			status(profileId, appInstanceId);
		} else if (Constants.Activity.KILL_INSTANCES.equals(task)) {
			String time = System.getProperty(Constants.AEP_INST_LIFETIME);
			if(time != null) {
				
				String profileId = System.getProperty(Constants.OptionalParam.PROFILE_ID);
				List<AppEnvInstance> list = getInstanceStatus(profileId, time.split(":"));
				if(!list.isEmpty()) {
					killInstances(list, profileId);
				}
				
			}
		}
	}

	public static List<AppEnvInstance> getInstanceStatus(String profileId, String[] lifetime) {
		
        int hours = 0;
        int minutes = 0;

        List<AppEnvInstance> list = new ArrayList<AppEnvInstance>();

        String projectID = Constants.MandatoryParam.PROJECT_ID;
        String providerID = Constants.MandatoryParam.PROVIDER_ID;
        String regionID = Constants.MandatoryParam.REGION_ID;
        String accountID = Constants.MandatoryParam.ACCOUNT_ID;
        String profileID = profileId;

        String path = "/aepService/" + projectID + "/providers/" + providerID + "/regions/" + regionID + "/accounts/" + accountID + "/profiles/" + profileID + "/instances/";

        try {
                System.out.print("Allowable time for instance: ");
                if(lifetime.length > 1) {
                        hours = Integer.parseInt(lifetime[0]);
                        minutes = Integer.parseInt(lifetime[1]);
                        System.out.println(hours+" Hours "+minutes+" Minutes");
                } else {
                        hours = Integer.parseInt(lifetime[0]);
                        System.out.println(hours+" Hours ");
                }

                List<AppEnvInstance> result = (List<AppEnvInstance>) RestClient.callRestService(
                                Command.GET_COLLECTION,Constants.Internal.AEP_SERVICE_URL, path, AppEnvInstance.class,null, Constants.Internal.TYPE_XML);

                for(AppEnvInstance instance:result) {
                        Date startup = instance.getStartTimestamp();
                        Date now = new Date();

                        long diffInMins = (now.getTime()-startup.getTime()) / (1000 * 60) % 60;
                        long diffHours = (now.getTime()-startup.getTime()) / (60 * 60 * 1000) % 24;

                        if(instance.getStatus().equals(Constants.APP_ENV_FAIL_STATUS)) {
                                list.add(instance);
                                System.out.println("Adding failed instance "+instance.getInstanceId()+" to kill list");
                        }

                        if(instance.getStatus().equals(Constants.APP_ENV_SUCCESS_STATUS)) {
                                if(diffHours > hours || (diffHours == hours && diffInMins >= minutes)) {
                                        list.add(instance);
                                        System.out.println("Instance up for "+diffHours+":"+diffInMins);
                                        System.out.println("Adding instance "+instance.getId()+" to kill list");
                                }
                        }

                }

        } catch (Exception e) {
                e.printStackTrace();
        }

        return list;
	}
	
	public static void killInstances(List<AppEnvInstance> list, String profileId) {
		
		 String projectID = Constants.MandatoryParam.PROJECT_ID;
         String providerID = Constants.MandatoryParam.PROVIDER_ID;
         String regionID = Constants.MandatoryParam.REGION_ID;
         String accountID = Constants.MandatoryParam.ACCOUNT_ID;
         String profileID = profileId;
         List<AppEnvInstance> instances = list;

         for(AppEnvInstance instance:instances) {
                 String path = "/aepService/" + projectID + "/providers/"+providerID+"/regions/"+regionID+"/accounts/"+accountID+"/profiles/"+profileID+"/instances/"+instance.getId();

                 try {
                         System.out.println("Terminating instance "+instance.getId());
                         Response response = (Response) RestClient.callRestService(
                                         Command.DELETE,Constants.Internal.AEP_SERVICE_URL, path, java.lang.Boolean.class,null, Constants.Internal.TYPE_XML);

                 } catch (Exception e) {
                         e.printStackTrace();
                 }
         }
	}
	
	public static void start(final String profileId, String warLocation) throws Exception {

		String projectID = Constants.MandatoryParam.PROJECT_ID;
		String providerID = Constants.MandatoryParam.PROVIDER_ID;
		String regionID = Constants.MandatoryParam.REGION_ID;
		String accountID = Constants.MandatoryParam.ACCOUNT_ID;
		String profileID = profileId;

		String path = "/aepService/" + projectID + "/providers/" + providerID + "/regions/" + regionID + "/accounts/" + accountID + "/profiles/" + profileID + "/instances/createInstance";
		AppEnvInstance instance = new AppEnvInstance();
		instance.setAccountNumber(Constants.MandatoryParam.CLOUD_ACC_ID);
		AppEnvMaster master = new AppEnvMaster();
		master.setId(1l);
		instance.setAppEnvMaster(master);
		instance.setDescription("Scripps trial instantiation request");
		instance.setProjectId(projectID);
		instance.setProperties("{schema-installer-on-mysql.dbschema1.sqlschema-name=mirror-1.0.0-update-comments.sql," + "jboss6-war-installer.server.war.name=" + warLocation + ",jboss6-war-installer.server.war.datasource1.name=ctmirror-ds,jboss6-war-installer.server.war.database1.name=ctmirror,"+
		"schema-installer-on-mysql.dbschema2.sqlschema-name=mediator-1.0.0-update-comments.sql,"+
			"jboss6-war-installer.server.war.datasource2.name=ctrequest-ds,jboss6-war-installer.server.war.database2.name=ctrequests,}");
		instance.setStartTimestamp(new Date());

		instance.setRoleId(Constants.OptionalParam.ROLE_ID);
		instance.setUserId(Constants.OptionalParam.USER_ID);
		instance.setServiceId(Constants.OptionalParam.SERVICE_ID);
		System.out.println(":::----------------:::::::" + instance);
		try {
			instance = (AppEnvInstance) RestClient.callRestService(Command.POST, Constants.Internal.AEP_SERVICE_URL, path, AppEnvInstance.class, instance, Constants.Internal.TYPE_XML);
			System.out.println("COGNIZANT::CLOUDSET::AEP::INSTANCE_ID::" + instance);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}

	}

	public static void stop(final String profileId, final String appInstanceId) {

		String projectID = Constants.MandatoryParam.PROJECT_ID;
		String providerID = Constants.MandatoryParam.PROVIDER_ID;
		String regionID = Constants.MandatoryParam.REGION_ID;
		String accountID = Constants.MandatoryParam.ACCOUNT_ID;
		String profileID = profileId;
		String appInstanceID = appInstanceId;

		String path = "/aepService/" + projectID + "/providers/" + providerID + "/regions/" + regionID + "/accounts/" + accountID + "/profiles/" + profileID + "/instances/" + appInstanceID;

		try {
			Response response = (Response) RestClient.callRestService(
					Command.DELETE, Constants.Internal.AEP_SERVICE_URL, path, java.lang.Boolean.class, null, Constants.Internal.TYPE_XML);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void status(final String profileId, final String appInstanceId) {

		String projectID = Constants.MandatoryParam.PROJECT_ID;
		String providerID = Constants.MandatoryParam.PROVIDER_ID;
		String regionID = Constants.MandatoryParam.REGION_ID;
		String accountID = Constants.MandatoryParam.ACCOUNT_ID;
		String profileID = profileId;
		String appInstanceID = appInstanceId;

		String path = "/aepService/" + projectID + "/providers/" + providerID + "/regions/" + regionID + "/accounts/" + accountID + "/profiles/" + profileID + "/instances/" + appInstanceID + "";

		try {
			AppEnvInstance result = (AppEnvInstance) RestClient.callRestService(
					Command.GET, Constants.Internal.AEP_SERVICE_URL, path, AppEnvInstance.class, null, Constants.Internal.TYPE_XML);
			System.out.println(result.getStatus());


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void listProfiles() throws Exception {

		String projectID = Constants.MandatoryParam.PROJECT_ID;
		String providerID = Constants.MandatoryParam.PROVIDER_ID;
		String regionID = Constants.MandatoryParam.REGION_ID;
		String accountID = Constants.MandatoryParam.ACCOUNT_ID;

		String path = "/aepService/" + projectID + "/providers/" + providerID + "/regions/" + regionID + "/accounts/" + accountID + "/profiles/";

		try {
			List<AppEnvMaster> result = (List<AppEnvMaster>) RestClient.callRestService(
					Command.GET_COLLECTION, Constants.Internal.AEP_SERVICE_URL, path, AppEnvMaster.class, null, Constants.Internal.TYPE_XML);

			for (AppEnvMaster profile : result) {
				System.out.println("------------------------------------------------");
				System.out.println("Profile Detail " + profile);
				System.out.println("------------------------------------------------");
			}
		} catch (Exception e) {
			throw e;
		}

	}
	
	public static void startAndWait(final String profileId, String warLocation) throws Exception {

		String projectID = Constants.MandatoryParam.PROJECT_ID;
		String providerID = Constants.MandatoryParam.PROVIDER_ID;
		String regionID = Constants.MandatoryParam.REGION_ID;
		String accountID = Constants.MandatoryParam.ACCOUNT_ID;
		String profileID = profileId;

		String path = "/aepService/" + projectID + "/providers/" + providerID + "/regions/" + regionID + "/accounts/" + accountID + "/profiles/" + profileID + "/instances/createInstance";
		AppEnvInstance instance = new AppEnvInstance();
		instance.setAccountNumber(Constants.MandatoryParam.CLOUD_ACC_ID);
		AppEnvMaster master = new AppEnvMaster();
		master.setId(1l);
		instance.setAppEnvMaster(master);
		instance.setDescription("Scripps trial instantiation request");
		instance.setProjectId(projectID);
		instance.setProperties("{schema-installer-on-mysql.dbschema1.sqlschema-name=mirror-1.0.0-update-comments.sql," + "jboss6-war-installer.server.war.name=" + warLocation + ",jboss6-war-installer.server.war.datasource1.name=ctmirror-ds,jboss6-war-installer.server.war.database1.name=ctmirror,"+
		"schema-installer-on-mysql.dbschema2.sqlschema-name=mediator-1.0.0-update-comments.sql,"+
			"jboss6-war-installer.server.war.datasource2.name=ctrequest-ds,jboss6-war-installer.server.war.database2.name=ctrequests,}");
		instance.setStartTimestamp(new Date());

		instance.setRoleId(Constants.OptionalParam.ROLE_ID);
		instance.setUserId(Constants.OptionalParam.USER_ID);
		instance.setServiceId(Constants.OptionalParam.SERVICE_ID);
		System.out.println(":::----------------:::::::" + instance);
		try {
			instance = (AppEnvInstance) RestClient.callRestService(Command.POST, Constants.Internal.AEP_SERVICE_URL, path, AppEnvInstance.class, instance, Constants.Internal.TYPE_XML);
			System.out.println("COGNIZANT::CLOUDSET::AEP::INSTANCE_ID::" + instance);
			
			Thread.sleep(10000);
			path = "/aepService/" + projectID + "/providers/" + providerID + "/regions/" + regionID + "/accounts/" + accountID + "/profiles/" + profileID + "/instances/" + instance.getId() + "";

			try {
				instance = (AppEnvInstance) RestClient.callRestService(
						Command.GET, Constants.Internal.AEP_SERVICE_URL, path, AppEnvInstance.class, null, Constants.Internal.TYPE_XML);
				System.out.println(instance);
				
				boolean deploymentCompleted = false;
				
				while(!deploymentCompleted){
					Thread.sleep(30000);
					instance = (AppEnvInstance) RestClient.callRestService(
							Command.GET, Constants.Internal.AEP_SERVICE_URL, path, AppEnvInstance.class, null, Constants.Internal.TYPE_XML);
					if(instance.getStatus().equals(Constants.APP_ENV_FAIL_STATUS) || instance.getStatus().equals(Constants.APP_ENV_SUCCESS_STATUS)){
						deploymentCompleted = true;
					}
					System.out.println("**** Waiting for Deployment to be completed ****");
				}
				System.out.println("Deployment is Completed With status as "+instance.getStatus());				

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}

	}
}
