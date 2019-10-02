package hudson.plugins.cloudset.constant;

public interface ClientConstants {
	
	
	public static final String BaaS_SERVICE_ID = "1";	
	public static final String BaaS_DEFAULT_ROLE = "8";
	
	public static final String JPAAS_USER = "JPAAS_USER";
	public static final String TYPE_XML = "application/xml";
	public static final String REST_SERVICE_NAME="/aepService/";
	
	public static final String REST_PROVIDER_STRING="/providers/";
	public static final String REST_REGION_STRING = "/regions/";
	public static final String REST_ACCOUNTS_STRING = "/accounts/";
	public static final String REST_PROFILES_STRING = "/profiles/";
	public static final String REST_PROFILES_PARAM = "/params/";
	public static final String REST_GET_INSTANCE = "/instances/";
	public static final String REST_CREATE_INSTANCE = "/instances/createInstance";
	public static final String REST_GET_INSTANCE_TASKS = "/tasks";
	public static final String REST_GET_INSTANCE_VMS = "/vms";
	
	public static final String REST_DEPLOYMENT_DESCRIPTION = "SPAWNING APP ENV FROM BAAS";
	public static final String REST_DEPLOYMENT_REASON = "SPAWNING APP ENV FROM BAAS";
	
	public static final String APP_ENV_SUCCESS_STATUS = "SUCCEEDED";
	public static final String APP_ENV_FAIL_STATUS = "FAILED";
	public static final String APP_ENV_SCHEDULED_STATUS = "SCHEDULED";
	public static final String APP_ENV_RUNNING_STATUS = "RUNNING";
}
