package hudson.plugins.publishdata.constants;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import com.pholser.util.properties.PropertyBinder;

/**
 * @author 239913
 * 
 */
public final class JPaaSConstant {
	
	private static final Logger LOGGER = Logger.getLogger(JPaaSConstant.class.getName());

	// When a user logs in, user id is stored in HTTPSession for all future
	// purposes
	// This is the key for the same
	//public static final String JPAAS_USER = "JPAAS_USER";
	public static final String BUILD_AUDIT_TIME_FORMAT = "E yyyy.MM.dd 'at' hh:mm:ss a zzz";
	//public static final String BUILD_SERVICE_ENDPOINT = "http://10.236.159.94:8080/JpaasBuildService/services/JpaasBuildServiceImpl.JpaasBuildServiceImplHttpSoap11Endpoint/";
	/*
	 * When user is authenticated & authorized by Cognizant LDAP and JPaaS
	 * respectively, for him the list of projects and the accounts under these
	 * projects, are returned by ACL service This date is saved in user's
	 * HTTPSession with the following as key with Map<ProjectId,
	 * List<AccountId>>, as value
	 *///public static final String USER_PROJECTS_SESSION_VAR = "USER_PROJECTS";

	 /*
	  * Project id and UserId will be used to fetch information from session
	  * 
	  */
	 public static final String PROJECT_ID = "PROJECTID";
	 public static final String USER_ID = "USERID";
	 
	 /*
	  * The following constants will be used to evaluate the build time in millisecs
	  */
	 public static final String MONTH = "month";
	 public static final String DAY = "day";
	 public static final String HOUR = "hour";
	 public static final String MIN = "min";
	 public static final String SEC = "sec";
	 public static final String COUNTING = "and";
	/* public static final String USER_NAME = "username";
	// public static final String ACCOUNT_ID = "accountId";
	
	 * This constant represents projectId tag's name of the job's config xml,
	 * this is used to get the project id of the user who created the job
	 
	//public static final String PROJECT_ID = "projectId";
	public static final String ACCOUNT_ID = "accountId";
	public static final String SECURITY_GROUP = "securityGroup";
	public static final String SECURITY_FILE_NAME = "securityKeyFile";
	
	 * This constant represents rootModule tag's name of the job's config xml,
	 * this is used to get the project's artifact id and group id
	 
	public static final String ROOT_MODULE = "rootModule";
	
	
	 * This constant represents group id tag's value from job's config xml,
	 * this is used to get the project's group id
	 
	public static final String GROUP_ID = "groupId";
	
        public static final int BaaS_Service_Id = 1;

	
	 * This constant represents artifact id tag's value from job's config xml,
	 * this is used to get the project's artifact id 
	 
	public static final String ARTIFACT_ID = "artifactId";
	
	
	 * Represents sonar publisher tag inside job's config.xml
	 *   
	 
	public static final String SONAR_PUBLISHER = "hudson.plugins.sonar.SonarPublisher";
	
	// Represents Sonar builder tag of pre and post build steps.
	public static final String SONAR_BUILDER_TAG = "hudson.plugins.sonar.SonarRunnerBuilder";
	
	// Represents pre build tag of job's config.xml
	public static final String PRE_BUILDER_TAG = "prebuilders";
	
	// Represents post build tag of job's config.xml
	public static final String POST_BUILDER_TAG = "postbuilders";
	
	// Represents properties tag inside SonarRunnerBuilder of pre or post build step
	public static final String SONAR_PROP = "properties";
	
	// Represents sonar index in case of stand alone sonar analysis
	public static final String PROJECT_KEY = "sonar.projectKey";

	// public static final String SECURITY_GROUP = "securityGroup";
	// public static final String SECURITY_KEY = "securityKeyFile";

	// TODO : These flags should be removed after refactoring the code
	// TODO : Business wise these are irrelevant. Just kept as of now
	// for backward compatibility.
	public static final String USER_ID_FOR_EC2 = "USER_ID_FOR_EC2";
	public static final String ACCOUNT_ID_FOR_EC2 = "ACCOUNT_ID_FOR_EC2";
	public static final String SECURITY_GROUP_FOR_EC2 = "SECURITY_GROUP_FOR_EC2";
	public static final String SECURITY_KEY_FOR_EC2 = "SECURITY_KEY_FOR_EC2";
	public static final String NA_FOR_CONSUMER = "NA_FOR_CONSUMER";

	public static final Map<String, JPaaSUser> map = Collections
			.synchronizedMap(new HashMap<String, JPaaSUser>());

	// User authorization cache related constants
	public static final String CACHE_REFRESH_RATE = "CACHE_REFRESH_RATE";
	public static final String USE_CACHE = "USE_CACHE";

	// VM Policies related constants
	public static final String NO_KILL_INDICATOR_PHRASE = "i-";
	public static final String JIT = "JIT";
	public static final String POOLED = "POOLED";
	public static final String THIRDPARTY = "EXT#";
	public static final String SWARMSLAVE = "SWARN";
	
	public enum POLICY {
		JIT, POOLED, THIRDPARTY,SWARN,NONE
	}

	// RTC Q item status related constants
	public static final String RTC_Q_STS_PENDING = "NOT_STARTED";
	public static final String RTC_Q_STS_COMPLETED_SUCCESSFULLY = "COMPLETED";
	public static final String RTC_Q_STS_COMPLETED_WITH_ERROR = "ERROR";
	public static final String RTC_Q_STS_RUNNING = "IN_PROGRESS";*/

	/*
	 * Represents credential for connecting to JPaaS DB These are read from
	 * jpaas-db.properties which is mandated for being found at JENKINS_HOME The
	 * name represents that these credential are used by BaaS to connect JPaaS
	 * backend database
	 */
	//public static JPaaSDBParameter jPaaSDBParameter;

	/**
	 * Properties to connect JPaaS's ACL Service, RTC Q Querying Service and Database  
	 */
	public static JPaaSIntegrationProperties jPaaSIntegrationProperties;
	
	/*
	 * Initializer for JPaaSIntegrationProperties
	 */
	static {
		PropertyBinder<JPaaSIntegrationProperties> binder = PropertyBinder.forType(JPaaSIntegrationProperties.class);
		try {
			String propFileLoc = System.getProperty("JENKINS_HOME")	+ "/jpaas-integration.properties";
			jPaaSIntegrationProperties = binder.bind(new File(propFileLoc));
		} catch (IOException e) {
			// This is NOOP situation for BaaS
			// BaaS wont start unless jpaas-integration.properties is being found under
			// Jenkins home
			e.printStackTrace();
			LOGGER.info("Could nor read jpaas-integration.properties @ Jenkins home"); 
			LOGGER.info("Please resolve this as otherwise BaaS will not initialize");			
			System.exit(1);
		}
	}
//TODO to be removed	
	
//	/**
//	 * Properties that represents database credential BaaS uses for posting
//	 * build end auditing information to provision db of JPaaS This information
//	 * is fetched by project statistics plugin to show up infrastructure usage
//	 * detail to the user
//	 */
//	static {
//		PropertyBinder<JPaaSDBParameter> binder = PropertyBinder
//				.forType(JPaaSDBParameter.class);
//		try {
//			String propFileLoc = System.getProperty("JENKINS_HOME")
//					+ "/jpaas-db.properties";
//			jPaaSDBParameter = binder.bind(new File(propFileLoc));
//		} catch (IOException e) {
//			// This is NOOP situation for BaaS
//			// BaaS wont start unless jpaas-db.properties is being found under
//			// Jenkins home
//			System.out
//					.println("jpaas-db.properties is missing @ Jenkins home .... CRITICAL ISSUE .... PLEASE RESOLVE BEFORE PRECEDING");
//			e.printStackTrace();
//			System.exit(1);
//		}
//	}
	/**
	 * Mode is supplied to the runtime via -DMODE parameter or by mentioning in
	 * CATALINA.properties file A Jenkins installation can run in one single
	 * mode only Presently Jenkins runs in default mode in which case it
	 * monitors its own queue for triggering build Otherwise if MODE is being
	 * set to RTC it monitors RTC queue for triggering build job See @NodeProvisioner
	 * for usage of this parameter
	 */
	/*public static final String MODE = "MODE";
	public static final String DEFAULT = "DEFAULT";
	public static final String RTC = "RTC";

	*//**
	 * This map hardcodes a specific URI to a different permission than the
	 * originally intended. This is to fake the default authorization mechanism.
	 * See usage @SidACL fakePermissionImplied method This technique is
	 * introduced to facilitate release managers save SVN credential which by
	 * default is something that Jenkins administrator can do
	 *//*
	public static final Map<String, String> URI_FAKED_PERMISSION_MAP = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				{
					put("/scm/SubversionSCM/postCredential", "Item.CONFIGURE");
				}
			});

	
	 * The values of following constants are deliberately kept in small case as
	 * they represent tag names of the configuration xml for the dummy jobs
	 * Values corresponding to these tags are used in tern for connecting to RTC
	 * Q
	 
	public static final String REPO_LOC = "repositoryLocation";
	public static final String WORKSPACE_NAME = "workspaceName";
	public static final String RTC_USER_NAME = "rtcUserName";
	public static final String PWD = "password";
	// This is not a tag in the resultant xml. This is just an action for
	// communicating the bridge to RTC Q
	public static final String GET_QUEUE_DETAIL = "GetQueueDetails";

	*//**
	 * BaaS can operate in normal mode where it manages its own build queue Or
	 * else it can listen to RTC build Q for receiving build notification and
	 * take needful action
	 * 
	 * @return current mode (either DEFAULT or RTC)
	 *//*
	public static final String getMode() {
		return (RTC.equals(System.getProperty(JPaaSConstant.MODE))) ? RTC
				: DEFAULT;
	}*/

}
