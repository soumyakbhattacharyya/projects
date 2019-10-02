/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.client;

/**
 * constant file
 *
 * @author Cognizant
 */
public class Constants {

	static String TASK = "TASK";
	static String AEP_INST_LIFETIME = "AEP_INST_LIFETIME";
	static final String APP_ENV_SUCCESS_STATUS = "SUCCEEDED";
	static final String APP_ENV_FAIL_STATUS = "FAILED";

	public static interface Internal {

		String TYPE_XML = "application/xml";
		String AEP_SERVICE_URL = "http://54.225.108.236:8580/appenvprovisionservice-webapp-1.0";
	}

	public static interface MandatoryParam {

		String USE_PROXY = "USE_PROXY";
		String PROJECT_ID = "P-26902";
		String PROVIDER_ID = "4";
		String REGION_ID = "3";
		String ACCOUNT_ID = "20";
		String CLOUD_ACC_ID = "62134720226229";
	}

	public static interface OptionalParam {

		String PROXY_SERVER = "PROXY_SERVER";
		String PROXY_SERVER_PORT = "PROXY_SERVER_PORT";
		String PROXY_UID = "PROXY_UID";
		String PROXY_PWD = "PROXY_PWD";
		String APP_INSTANCE_ID = "APP_INSTANCE_ID";
		String PROFILE_ID = "PROFILE_ID";
		String ROLE_ID = "8";
		String USER_ID = "vijay.srinivasan2@cognizant.com";
		String WAR_FILE_LOCATION = "WAR_FILE_LOCATION";
		String SERVICE_ID = "1";
	}

	public static interface Activity {

		String LIST = "LIST";
		String START = "START";
		String STOP = "STOP";
		String STATUS = "STATUS";
		String KILL_INSTANCES = "KILL_INSTANCES";
		String START_AND_WAIT = "START_AND_WAIT";
	}
	//		String projectID = "P-26902";
//		String providerID = "4";
//		String regionID = "3";
//		String accountID ="20"; 
}
