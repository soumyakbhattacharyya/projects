package hudson.plugins.cloudset.cloudsetaep;

import hudson.CopyOnWrite;
import hudson.Extension;
import hudson.FilePath;
import hudson.FilePath.FileCallable;
import hudson.Launcher;
import hudson.cognizant.jpaas.pojo.JPaaSUser;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.plugins.cloudset.constant.ClientConstants;
import hudson.plugins.cloudset.util.SSHUtil;
import hudson.plugins.cloudset.util.StringUtil;
import hudson.remoting.VirtualChannel;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Builder;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import hudson.util.FormValidation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;

import com.cognizant.cloudset.client.RestClient;
import com.cognizant.cloudset.command.Command;
import com.cognizant.cloudset.core.AppEnvInstance;
import com.cognizant.cloudset.core.AppEnvMaster;
import com.cognizant.cloudset.core.AppEnvParams;
import com.cognizant.cloudset.core.AppEnvTaskOverview;
import com.cognizant.cloudset.core.CloudAccount;
import com.cognizant.cloudset.core.CloudProvider;
import com.cognizant.cloudset.core.CloudRegion;
import com.cognizant.cloudset.core.MachineDetails;

/**
 * Sample {@link Builder}.
 * 
 * <p>
 * When the user configures the project and enables this builder,
 * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked and a new
 * {@link AppDeployer} is created. The created instance is persisted to the
 * project configuration XML by using XStream, so this allows you to use
 * instance fields (like {@link #name}) to remember the configuration.
 * 
 * <p>
 * When a build is performed, the
 * {@link #perform(AbstractBuild, Launcher, BuildListener)} method will be
 * invoked.
 * 
 * @author Cloud-COE
 * 
 * 
 */
public class AppDeployer extends Notifier {

	private final AEPConfiguration aepConfiguration;

	private static final Logger LOGGER = Logger.getLogger(AppDeployer.class
			.getName());

	@DataBoundConstructor
	public AppDeployer(AEPConfiguration aepConfiguration) {
		this.aepConfiguration = aepConfiguration;
	}

	public AEPConfiguration getAepConfiguration() {
		return aepConfiguration;
	}
	
	@Override
	public boolean perform(AbstractBuild build, Launcher launcher,
			final BuildListener listener) throws IOException, InterruptedException {
		System.out
				.println("-----------------Calling the build part-------------------------"
						+ aepConfiguration.getInstallationURL());

		if (aepConfiguration.getInstallationURL() != null
				&& aepConfiguration.getAepCloudProviderConfiguration() != null
				&& aepConfiguration.getAepCloudProviderConfiguration()
						.getCloudProvider() != null
				&& aepConfiguration.getAepCloudProviderConfiguration()
						.getAepCloudRegion() != null
				&& aepConfiguration.getAepCloudProviderConfiguration()
						.getAepCloudRegion().getRegionName() != null
				&& aepConfiguration.getAepCloudProviderConfiguration()
						.getAepCloudRegion().getAepCloudAccount() != null
				&& aepConfiguration.getAepCloudProviderConfiguration()
						.getAepCloudRegion().getAepCloudAccount()
						.getCloudAccountName() != null
				&& aepConfiguration.getAepCloudProviderConfiguration()
						.getAepCloudRegion().getAepCloudAccount()
						.getAepCloudEnvMasters() != null
				&& aepConfiguration.getAepCloudProviderConfiguration()
						.getAepCloudRegion().getAepCloudAccount()
						.getAepCloudEnvMasters().getCloudEnvMasterName() != null
				&& aepConfiguration.getAepCloudProviderConfiguration()
						.getAepCloudRegion().getAepCloudAccount()
						.getAepCloudEnvMasters().getParamString() != null
				&& aepConfiguration.getAepCloudProviderConfiguration()
						.getAepCloudRegion().getAepCloudAccount()
						.getAepCloudEnvMasters().getProjectID() != null
				&& aepConfiguration.getAepCloudProviderConfiguration()
						.getAepCloudRegion().getAepCloudAccount()
						.getAepCloudEnvMasters().getProjectUserID() != null
				&& aepConfiguration.getAepCloudProviderConfiguration()
				.getAepCloudRegion().getAepCloudAccount()
				.getAepCloudEnvMasters().getWarName() != null) {
			
			LOGGER.info("TRIGGERING LAUNCH OPERATION");
			
			String fileToCopy = aepConfiguration.getAepCloudProviderConfiguration()
					.getAepCloudRegion().getAepCloudAccount()
					.getAepCloudEnvMasters().getWarName();
			
			String fileName = ""; 
						
			if(!fileToCopy.contains("*/")){
				fileName = fileToCopy;
				fileToCopy = "**/"+fileToCopy;
						
			}else{
				int startIndex = fileToCopy.lastIndexOf("/");
				fileName = fileToCopy.substring(startIndex);
			}
			
			FilePath[] files = build.getWorkspace().list(fileToCopy);	
			
			listener.getLogger().println("build.getWorkspace(). "+build.getWorkspace().toString());
			listener.getLogger().println("files contains "+files.length);			
			
			for(FilePath filePath:files){
				listener.getLogger().println("Artifact details "+filePath);				
				listener.getLogger().println("filePath.toURI().toString() is "+filePath.toURI().toString());		
				
				listener.getLogger().println("filePath.toURI().toString() is "+filePath.toString());
        								
				// copy the file to the jenkins machine temporarily
				File file = new File("/EBS/Install/"+fileName);
				FileOutputStream fout = new FileOutputStream(file);
				filePath.copyTo(fout);					
				try {					
					listener.getLogger().println("***SSH transfering file *** "+file.getName());
					listener.getLogger().println("***SSH transfering file path *** "+file.getPath());
					
					SSHUtil.sendFile(file.getPath(),
							"/share/Complete_File_Server/AEP-BaaS-Artifacts/",
							"root", "root", null, "10.242.138.41", 22, 0);
					listener.getLogger().println("file is copied successfully");
				} catch (Exception e) {
					listener.error("Can not copy files for exception "+e.getMessage());
				}     		
							
			}
			
			listener.getLogger().println("buidl has artifact "+aepConfiguration.getAepCloudProviderConfiguration().getAepCloudRegion().getAepCloudAccount().getAepCloudEnvMasters().getWarName());
					
			listener.getLogger().println("------------------Triggering build deployment operation---------------");
			

			String providerID = StringUtil.getOnlyIDFromString(aepConfiguration
					.getAepCloudProviderConfiguration().getCloudProvider());
			String cloudRegionID = StringUtil
					.getOnlyIDFromString(aepConfiguration
							.getAepCloudProviderConfiguration()
							.getAepCloudRegion().getRegionName());
			String cloudAccountID = StringUtil
					.getOnlyIDFromString(aepConfiguration
							.getAepCloudProviderConfiguration()
							.getAepCloudRegion().getAepCloudAccount()
							.getCloudAccountName());
			String aepMasterID = StringUtil
					.getOnlyIDFromString(aepConfiguration
							.getAepCloudProviderConfiguration()
							.getAepCloudRegion().getAepCloudAccount()
							.getAepCloudEnvMasters().getCloudEnvMasterName());
			String paramString = aepConfiguration
					.getAepCloudProviderConfiguration().getAepCloudRegion()
					.getAepCloudAccount().getAepCloudEnvMasters()
					.getParamString();
			String projectID = aepConfiguration
					.getAepCloudProviderConfiguration().getAepCloudRegion()
					.getAepCloudAccount().getAepCloudEnvMasters()
					.getProjectID();
			String projectUsrID = aepConfiguration
					.getAepCloudProviderConfiguration().getAepCloudRegion()
					.getAepCloudAccount().getAepCloudEnvMasters()
					.getProjectUserID();

			// Create an appEnvinstance and populate the object
			AppEnvInstance appEnvInstance = new AppEnvInstance();
			appEnvInstance.setAccountNumber(StringUtil
					.getDetailFromString(aepConfiguration
							.getAepCloudProviderConfiguration()
							.getAepCloudRegion().getAepCloudAccount()
							.getCloudAccountName()));

			AppEnvMaster master = new AppEnvMaster();
			master.setId(Long.parseLong(aepMasterID));
			appEnvInstance.setAppEnvMaster(master);

			appEnvInstance.setProjectId(projectID);
			appEnvInstance.setUserId(projectUsrID);
			appEnvInstance.setRoleId(ClientConstants.BaaS_DEFAULT_ROLE);
			
			
			if (paramString.contains("\"")) {
				appEnvInstance.setProperties(paramString.replaceAll("\"", "")
						.trim());
			}
			listener.getLogger().println("Setting param as "+paramString);
			appEnvInstance.setReason(ClientConstants.REST_DEPLOYMENT_REASON);
			appEnvInstance
					.setDescription(ClientConstants.REST_DEPLOYMENT_DESCRIPTION);
			appEnvInstance.setServiceId(ClientConstants.BaaS_SERVICE_ID);

			String path = ClientConstants.REST_SERVICE_NAME + projectID
					+ ClientConstants.REST_PROVIDER_STRING + providerID
					+ ClientConstants.REST_REGION_STRING + cloudRegionID
					+ ClientConstants.REST_ACCOUNTS_STRING + cloudAccountID
					+ ClientConstants.REST_PROFILES_STRING + aepMasterID
					+ ClientConstants.REST_CREATE_INSTANCE;

			LOGGER.info("******PATH for Create instance******" + path);

			try {

				try {
					appEnvInstance = (AppEnvInstance) RestClient
							.callRestService(Command.POST,
									aepConfiguration.getInstallationURL(),
									path, AppEnvInstance.class, appEnvInstance,
									ClientConstants.TYPE_XML);

					LOGGER.info("Details posted to Rest Service "
							+ appEnvInstance + " successfully !!!!");

				} catch (Exception e) {
					LOGGER.severe("Error occured while posting data for AEP Deployment instance "
							+ e);
					e.getStackTrace();
					listener.error(
							":Error occured while posting data for AEP Deployment instance "
									+ e);
					return false;
				}

				Set<AppEnvTaskOverview> taskListstoShow = new HashSet<AppEnvTaskOverview>();
				boolean isDeploymentAppComplete = false;
				while (!isDeploymentAppComplete) {

					appEnvInstance = getLatestAppEnvInstance(appEnvInstance,
							projectID, providerID, cloudRegionID,
							cloudAccountID, aepMasterID);
					// get the status of appEnvInstance, if status is SUCCEEDED
					// or FAILED then stop pooling
					if (appEnvInstance.getStatus().equals(
							ClientConstants.APP_ENV_SUCCESS_STATUS)
							|| appEnvInstance.getStatus().equals(
									ClientConstants.APP_ENV_FAIL_STATUS)) {
						isDeploymentAppComplete = true;
						break;
					} else {
						if (appEnvInstance.getStatus().equals(
								ClientConstants.APP_ENV_SCHEDULED_STATUS)) {
							// sleep till the orchestrator picks the job
							listener.getLogger()
									.println("Waiting for App Env provisioner to pick the deployment.Please wait for sometime.");
							
							Thread.sleep(5000);
							continue;
						} else if (appEnvInstance.getStatus().equals(
								ClientConstants.APP_ENV_RUNNING_STATUS)) {
							// job started running.. get the task list and show							
							updateTaskDetailsInLog(appEnvInstance, projectID,
									providerID, cloudRegionID, cloudAccountID,
									aepMasterID, taskListstoShow, listener);
							Thread.sleep(5000);
							continue;
						}
					}
				}

				if (appEnvInstance.getStatus().equals(
						ClientConstants.APP_ENV_SUCCESS_STATUS)) {
					// if the App instance completed successfully, then show the
					// VM List
					showVMsInLog(appEnvInstance, projectID, providerID,
							cloudRegionID, cloudAccountID, aepMasterID,
							listener);
				}

			} catch (Exception e) {
				LOGGER.severe("Error occured  " + e);
				listener.getLogger().append(
						":Error occured while posting data for AEP Deployment instance "
								+ e);
				return false;
			}

		} else {
			return false;
		}
		return true;
	}

	// app Instance record is created successfully. Now we need to
	// keep pooling for the app instance status
	public AppEnvInstance getLatestAppEnvInstance(
			AppEnvInstance appEnvInstance, String projectID, String providerID,
			String cloudRegionID, String cloudAccountID, String aepMasterID)
			throws Exception {

		String path = ClientConstants.REST_SERVICE_NAME + projectID
				+ ClientConstants.REST_PROVIDER_STRING + providerID
				+ ClientConstants.REST_REGION_STRING + cloudRegionID
				+ ClientConstants.REST_ACCOUNTS_STRING + cloudAccountID
				+ ClientConstants.REST_PROFILES_STRING + aepMasterID
				+ ClientConstants.REST_GET_INSTANCE + appEnvInstance.getId();

		appEnvInstance = (AppEnvInstance) RestClient.callRestService(
				Command.GET, aepConfiguration.getInstallationURL(), path,
				AppEnvInstance.class, appEnvInstance, ClientConstants.TYPE_XML);
		return appEnvInstance;
	}

	public void updateTaskDetailsInLog(AppEnvInstance appEnvInstance,
			String projectID, String providerID, String cloudRegionID,
			String cloudAccountID, String aepMasterID,
			Set<AppEnvTaskOverview> taskListstoShow, BuildListener listener)
			throws Exception {

		String path = ClientConstants.REST_SERVICE_NAME + projectID
				+ ClientConstants.REST_PROVIDER_STRING + providerID
				+ ClientConstants.REST_REGION_STRING + cloudRegionID
				+ ClientConstants.REST_ACCOUNTS_STRING + cloudAccountID
				+ ClientConstants.REST_PROFILES_STRING + aepMasterID
				+ ClientConstants.REST_GET_INSTANCE + appEnvInstance.getId()
				+ ClientConstants.REST_GET_INSTANCE_TASKS;

		LOGGER.info("path for getting task details "+path);
		
		java.util.ArrayList<AppEnvTaskOverview> taskLists = new java.util.ArrayList<AppEnvTaskOverview>();
		taskLists = (java.util.ArrayList<AppEnvTaskOverview>) RestClient.callRestService(
				Command.GET_COLLECTION, aepConfiguration.getInstallationURL(),
				path, AppEnvTaskOverview.class, null, ClientConstants.TYPE_XML);

		// show only the fresh tasklist
		if (!taskListstoShow.isEmpty()) {
			taskLists.removeAll(taskListstoShow);
		}
		// add the task list 
		taskListstoShow.addAll(taskLists);
		
		for (AppEnvTaskOverview task : taskLists) {
			listener.getLogger().append("Task executing " + task.getLongDesc());
		}

	}

	public void showVMsInLog(AppEnvInstance appEnvInstance, String projectID,
			String providerID, String cloudRegionID, String cloudAccountID,
			String aepMasterID, BuildListener listener) throws Exception {

		String path = ClientConstants.REST_SERVICE_NAME + projectID
				+ ClientConstants.REST_PROVIDER_STRING + providerID
				+ ClientConstants.REST_REGION_STRING + cloudRegionID
				+ ClientConstants.REST_ACCOUNTS_STRING + cloudAccountID
				+ ClientConstants.REST_PROFILES_STRING + aepMasterID
				+ ClientConstants.REST_GET_INSTANCE + appEnvInstance.getId()
				+ ClientConstants.REST_GET_INSTANCE_VMS;

		Set<MachineDetails> machineDetails = new HashSet<MachineDetails>();
		machineDetails = (Set<MachineDetails>) RestClient.callRestService(
				Command.GET_COLLECTION, aepConfiguration.getInstallationURL(),
				path, MachineDetails.class, null, ClientConstants.TYPE_XML);

		List<MachineDetails> vmList = (List<MachineDetails>) RestClient
				.callRestService(Command.GET_COLLECTION,
						aepConfiguration.getInstallationURL(), path,
						AppEnvInstance.class, null, ClientConstants.TYPE_XML);

		listener.getLogger().append("Deployed machine details as follows");
		for (MachineDetails machine : vmList) {
			listener.getLogger().append(
					machine.getVmName() + " having IP ad "
							+ machine.getPublicIPAddresses());
		}

	}

	/**
	 * Descriptor for {@link AppDeployer}. Used as a singleton. The class is
	 * marked as public so that it can be accessed from views.
	 * 
	 * 
	 */
	@Extension
	// This indicates to Jenkins that this is an implementation of an extension
	// point.
	public static final class DescriptorImpl extends
			BuildStepDescriptor<Publisher> {
		/**
		 * To persist global configuration information, simply store it in a
		 * field and call save().
		 * 
		 * <p>
		 * If you don't want fields to be persisted, use <tt>transient</tt>.
		 */

		private String projectUserID = "";
		private String projectID = "";
		private String selectedProfileURL = "";
		private List<CloudProvider> cloudProviders = null;
		private List<CloudRegion> cloudRegions = null;
		private List<CloudAccount> cloudAccounts = null;
		private List<AppEnvMaster> appEnvMasters = null;
		private String appEnvParams = null;

		private String selectedProviderID = null;
		private String selectedRegionsID = null;
		private String selectedAccountID = null;
		private String selectedAppMasterID = null;

		@CopyOnWrite
		private AppEnvProfile[] installations = new AppEnvProfile[0];

		@CopyOnWrite
		private List<String> cloudProviderlist;

		@CopyOnWrite
		private List<String> regionList;

		@CopyOnWrite
		private List<String> accountDetailsList;

		@CopyOnWrite
		private List<String> appProfileList;

		@CopyOnWrite
		private String appProfileParamList;

		public DescriptorImpl() {
			super();
			load();
		}

		/**
		 * Performs on-the-fly validation of the form field 'name'.
		 * 
		 * @param value
		 *            This parameter receives the value that the user has typed.
		 * @return Indicates the outcome of the validation. This is sent to the
		 *         browser.
		 */
		public FormValidation doCheckName(@QueryParameter String value)
				throws IOException, ServletException {
			if (value.length() == 0)
				return FormValidation.error("Please set a name");
			if (value.length() < 4)
				return FormValidation.warning("Isn't the name too short?");
			return FormValidation.ok();
		}

		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			// Indicates that this builder can be used with all kinds of project
			// types
			return true;
		}

		public FormValidation doCheckMandatory(@QueryParameter String value) {
			return StringUtils.isBlank(value) ? FormValidation.error(Messages
					.AppDeployer_MandatoryProperty()) : FormValidation.ok();
		}

		/**
		 * This human readable name is used in the configuration screen.
		 */
		public String getDisplayName() {
			return "App Env Profile";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData)
				throws FormException {

			List<AppEnvProfile> list = req.bindJSONToList(AppEnvProfile.class,
					formData.get("inst"));
			setInstallations(list.toArray(new AppEnvProfile[list.size()]));
			return true;
		}

		public AppEnvProfile[] getInstallations() {
			return installations;
		}

		public void setInstallations(AppEnvProfile[] installations) {
			this.installations = installations;
			save();
		}

		public List<String> getCloudProviderlist(String installationName) {
			cloudProviderlist = new ArrayList<String>();

			HttpSession sess = Stapler.getCurrentRequest().getSession(false);

			if (sess != null
					&& sess.getAttribute(ClientConstants.JPAAS_USER) != null) {
				try {
					JPaaSUser juser = (JPaaSUser) sess
							.getAttribute(ClientConstants.JPAAS_USER);
					LOGGER.info("the juser data is " + juser.getUserId());
					projectUserID = juser.getUserId();
					projectID = juser.getProjectId();
				} catch (Exception e) {
					LOGGER.severe("EXCEPTION OCCURED WHILE FETCHING SESSION DATA "
							+ e.getMessage());
				}
			}

			for (AppEnvProfile profile : installations) {
				if (profile.getName().equals(installationName)) {
					selectedProfileURL = profile.getAppEnvURL();
				}
			}

			String path = ClientConstants.REST_SERVICE_NAME + projectID
					+ ClientConstants.REST_PROVIDER_STRING;

			try {
				cloudProviders = (List<CloudProvider>) RestClient
						.callRestService(Command.GET_COLLECTION,
								selectedProfileURL, path, CloudProvider.class,
								null, ClientConstants.TYPE_XML);
			} catch (Exception e) {
				cloudProviderlist.add(installationName + " is invalid");
				LOGGER.info("Exception occured " + e.getMessage());
			}

			for (CloudProvider cloudProvider : cloudProviders) {
				cloudProviderlist.add(cloudProvider.getDisplayName() + "("
						+ cloudProvider.getProviderId() + ")");
			}
			return cloudProviderlist;
		}

		public List<String> getRegionList(String cloudProvider) {
			regionList = new ArrayList<String>();
			selectedProviderID = StringUtil.getOnlyIDFromString(cloudProvider);
			LOGGER.info("************selectedCloudProvider is *******"
					+ selectedProviderID);

			if (selectedProviderID != null) {
				try {
					String path = ClientConstants.REST_SERVICE_NAME + projectID
							+ ClientConstants.REST_PROVIDER_STRING
							+ selectedProviderID
							+ ClientConstants.REST_REGION_STRING;
					LOGGER.info("Path is " + path);
					cloudRegions = (List<CloudRegion>) RestClient
							.callRestService(Command.GET_COLLECTION,
									selectedProfileURL, path,
									CloudRegion.class, null,
									ClientConstants.TYPE_XML);

					for (CloudRegion region : cloudRegions) {
						regionList.add(region.getRegionName() + "("
								+ region.getId() + ")");
						LOGGER.info("region list---" + region.getRegionName()
								+ "(" + region.getId() + ")");
					}

				} catch (Exception e) {
					regionList.add("Invalid Regions");
				}
			} else {
				regionList.add("Invalid Regions");
			}

			return regionList;
		}

		public List<String> getAccountDetailsList(String regionName) {
			accountDetailsList = new ArrayList<String>();
			selectedRegionsID = StringUtil.getOnlyIDFromString(regionName);
			LOGGER.info("---selectedRegionsID---" + selectedRegionsID);
			if (selectedRegionsID != null) {
				try {
					String path = ClientConstants.REST_SERVICE_NAME + projectID
							+ ClientConstants.REST_PROVIDER_STRING
							+ selectedProviderID
							+ ClientConstants.REST_REGION_STRING
							+ selectedRegionsID
							+ ClientConstants.REST_ACCOUNTS_STRING;
					LOGGER.info("PATH for account " + path);
					cloudAccounts = (List<CloudAccount>) RestClient
							.callRestService(Command.GET_COLLECTION,
									selectedProfileURL, path,
									CloudAccount.class, null,
									ClientConstants.TYPE_XML);

					for (CloudAccount cloudAccount : cloudAccounts) {
						accountDetailsList.add(cloudAccount
								.getExternalCloudAccountId()
								+ "("
								+ cloudAccount.getCloudAccountId() + ")");
					}
				} catch (Exception e) {
					regionList.add("Invalid Cloud Account");
				}
			} else {
				regionList.add("Invalid Cloud Account");
			}
			return accountDetailsList;
		}

		public List<String> getAppProfileList(String accountName) {
			appProfileList = new ArrayList<String>();
			selectedAccountID = StringUtil.getOnlyIDFromString(accountName);
			if (selectedAccountID != null) {
				try {
					String path = ClientConstants.REST_SERVICE_NAME + projectID
							+ ClientConstants.REST_PROVIDER_STRING
							+ selectedProviderID
							+ ClientConstants.REST_REGION_STRING
							+ selectedRegionsID
							+ ClientConstants.REST_ACCOUNTS_STRING
							+ selectedAccountID
							+ ClientConstants.REST_PROFILES_STRING;
					LOGGER.info("path for PRofile " + path);
					appEnvMasters = (List<AppEnvMaster>) RestClient
							.callRestService(Command.GET_COLLECTION,
									selectedProfileURL, path,
									AppEnvMaster.class, null,
									ClientConstants.TYPE_XML);

					for (AppEnvMaster appEnvs : appEnvMasters) {
						appProfileList.add(appEnvs.getShortDesc() + "("
								+ appEnvs.getId() + ")");
					}
				} catch (Exception e) {
					regionList.add("Invalid Cloud Account");
				}
			} else {
				regionList.add("Invalid Cloud Account");
			}

			return appProfileList;
		}

		public String getAppProfileParamList(String appProfileMasterName) {

			selectedAppMasterID = StringUtil
					.getOnlyIDFromString(appProfileMasterName);
			if (selectedAppMasterID != null) {
				try {
					String path = ClientConstants.REST_SERVICE_NAME + projectID
							+ ClientConstants.REST_PROVIDER_STRING
							+ selectedProviderID
							+ ClientConstants.REST_REGION_STRING
							+ selectedRegionsID
							+ ClientConstants.REST_ACCOUNTS_STRING
							+ selectedAccountID
							+ ClientConstants.REST_PROFILES_STRING
							+ selectedAppMasterID
							+ ClientConstants.REST_PROFILES_PARAM;

					List<AppEnvParams> completeAppEnvParams = (List<AppEnvParams>) RestClient
							.callRestService(Command.GET_COLLECTION,
									selectedProfileURL, path,
									AppEnvParams.class, null,
									ClientConstants.TYPE_XML);

					StringBuilder paramBuild = new StringBuilder();
					if (!completeAppEnvParams.isEmpty()) {
						paramBuild.append("{");
						for (AppEnvParams params : completeAppEnvParams) {
							paramBuild.append(params.getParamName() + "="
									+ params.getParamValue());
							paramBuild.append(",");
						}
						paramBuild.append("}");
					}
					appProfileParamList = paramBuild.toString();
				} catch (Exception e) {
					regionList.add("Invalid Cloud Account");
				}
			} else {
				regionList.add("Invalid Cloud Account");
			}

			return appProfileParamList;
		}

		public void setAppProfileParamList(String appProfileParamList) {
			this.appProfileParamList = appProfileParamList;
		}

		public void setCloudProviderlist(List<String> cloudProviderlist) {
			this.cloudProviderlist = cloudProviderlist;
		}

		public void setRegionList(List<String> regionList) {
			this.regionList = regionList;
		}

		public void setAccountDetailsList(List<String> accountDetailsList) {
			this.accountDetailsList = accountDetailsList;
		}

		public void setAppProfileList(List<String> appProfileList) {
			this.appProfileList = appProfileList;
		}

		public String getAppEnvParams() {
			return appEnvParams;
		}

		public void setAppEnvParams(String appEnvParams) {
			this.appEnvParams = appEnvParams;
		}

		public String getProjectID() {
			return projectID;
		}

		public String getProjectUserID() {
			return projectUserID;
		}

		public void setProjectUserID(String projectUserID) {
			this.projectUserID = projectUserID;
		}

		public void setProjectID(String projectID) {
			this.projectID = projectID;
		}

		/**
		 * Need to check if the AEP App is up and running
		 * 
		 * @param projectID
		 * @return
		 * @throws IOException
		 * @throws ServletException
		 */
		public FormValidation doTestConnection(@QueryParameter String appEnvURL)
				throws IOException, ServletException {
			HttpSession sess = Stapler.getCurrentRequest().getSession(false);
			LOGGER.info("Testing URL connection " + appEnvURL);
			return FormValidation.ok();
		}
	}

	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.BUILD;
	}

}
