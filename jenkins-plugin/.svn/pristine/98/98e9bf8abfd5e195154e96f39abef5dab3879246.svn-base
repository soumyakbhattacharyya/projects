/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework;

/**
 * preflight constants 
 * @author cognizant
 */
public final class Constants {

	// flags to accompany commands 
	public static final String BASE_OPTION_SERVER_FLAG = "-s"; // the server URL (defaults to the JENKINS_URL env var)
	public static final String BUILD_OPTION_WAIT_FLAG = "-s"; // wait until the completion/abortion of the command
	public static final String BUILD_OPTION_VERBOSE_FLAG = "-v"; // prints console output for a build. to be used with above
	public static final String BUILD_OPTION_PARAMETER = "-p"; // specify the build parameters in the key=value format
	public static final String RSA_FILE_PARAMETER = "-i"; // specify the RSA file parameter to be supllied in login process
	public static final String PROJECT_ID_PARAMETER = "--cliProjectID"; //specify the client Project Id in key-value format
	
	// jenkins cli commands
	public static final String BUILD_COMMAND = "build"; // starts a build and waits for the completion
	public static final String GET_JOB_COMMAND = "get-job"; // get job configuration
	public static final String SHOW_JOB_LOG_COMMAND = "show-log"; // gets log of a build job
	public static final String LOGIN_ARG ="login"; // login to ci server
	public static final String LOGOUT_ARG ="logout"; // logout from the ci server
	
	// read from configuration file
	public static final String JENKINS_URL = PropertyConstants.constantsReader.buildServerUrl();
	public static final String ID_RSA_FILE = PropertyConstants.constantsReader.userSSHKeyfileLocation();
	public static final int RETRY_COUNT = Integer.parseInt(PropertyConstants.constantsReader.retryCount());
	public static final int RETRY_TIME = Integer.parseInt(PropertyConstants.constantsReader.retryTime());
	public static final String NAME_OF_JOB_GENERATOR = PropertyConstants.constantsReader.jobGeneratorName();
	public static final String DEV_STAGING_AREA = System.getProperty("user.home") + java.io.File.separator + ".buildbox";
	public static final String LAST_PREFLIGHT_TIMESTAMP = "LAST_PREFLIGHT_TIMESTAMP";	
	public static final String GET_JOB_RESPONSE_TXT = DEV_STAGING_AREA + java.io.File.separator + "GET_JOB_RESPONSE_TXT.txt";
	public static final String SHOW_JOB_STATUS_TXT = DEV_STAGING_AREA + java.io.File.separator + "SHOW_JOB_STATUS_TXT.txt";
	public static final String TEXT_FILE_EXTENTION = ".txt";
	public static final String DOT_GIT_REPOSITORY = ".git";
		
	
	// constants of the job generator 
	public static final String WORKSPACE_PARAM = "workspace_loc";
	public static final String BUILD_COMMAND_PARAM = "build_command";
	public static final String TO_MAIL_PARAM = "to_mail";
	public static final String REQUESTER_PARAM = "requester";
	public static final String ENVIRONMENT_PARAM = "environment";
	public static final String WORKSPACE_NAME_PARAM = "workspace_name";
	public static final String IS_SONAR_ENABLE="is_sonar_enable";
	public static final String PROJECT_ID="project_id";		
	
	//string searched to discover if a job has ended successfully or not
	public static final String FAILURE="Finished: FAILURE";
	public static final String ORIGIN = "origin";
	public static final String GENERATED_JOB_POSTFIX = "-pb";
	
}
