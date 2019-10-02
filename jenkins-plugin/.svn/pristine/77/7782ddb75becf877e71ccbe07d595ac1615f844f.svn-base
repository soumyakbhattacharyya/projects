package hudson.plugins;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import hudson.Extension;
import hudson.Util;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Hudson;
import hudson.model.Label;
import hudson.model.Node;
import hudson.model.TaskListener;
import hudson.model.Descriptor.FormException;
import hudson.plugins.constant.Cloud360PluginConstants;
import hudson.plugins.constant.Messages;
import hudson.plugins.util.Cloud360LaunchUtil;
import hudson.util.FormValidation;

import com.cognizant.cloudset.client.RestClient;
import com.cognizant.cloudset.constants.Cloud360Constants;
import com.cognizant.cloudset.message.Cloud360Response;
import com.cognizant.cloudset.model.Cloud;
import com.cognizant.cloudset.model.ComputeProfile;

public class Cloud360MachineTemplates implements Describable<Cloud360MachineTemplates> {
	
	public final String computeProfileId;
	private final String remoteUserId;
	private final String secretPrivateKey;
	private final String remoteFSRoot;
	private final String rootCommandPrefix;
	private final String numOfExecutors;
	private final String remoteSSHPort;
	private final String initScript;
	public final String label;
	
	protected Cloud360 parent;
	
	private String restURL;
	private String restUserID;
	private String restPassword;
	
	private RestClient client;
	
	@DataBoundConstructor
	public Cloud360MachineTemplates(String computeProfileId, 
			String remoteUserId, String secretPrivateKey, 
			String remoteFSRoot, String rootCommandPrefix, 
			String numOfExecutors, String remoteSSHPort, 
			String initScript, String label) {
		this.computeProfileId = computeProfileId;
		this.remoteUserId = remoteUserId;
		this.secretPrivateKey = secretPrivateKey;
		this.remoteFSRoot = remoteFSRoot;
		this.rootCommandPrefix = rootCommandPrefix;
		this.numOfExecutors = Util.fixNull(numOfExecutors).trim();
		this.remoteSSHPort = remoteSSHPort;
		this.initScript = initScript;
		this.label = label;
	}

	/**
	 * @return the computeProfile
	 */
	public String getComputeProfileId() {
		return computeProfileId;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * @return the remoteUserId
	 */
	public String getRemoteUserId() {
		return remoteUserId;
	}

	/**
	 * @return the secretPrivateKey
	 */
	public String getSecretPrivateKey() {
		return secretPrivateKey;
	}

	/**
	 * @return the remoteFSRoot
	 */
	public String getRemoteFSRoot() {
		return remoteFSRoot;
	}

	/**
	 * @return the numOfExecutors
	 */
	public int getNumOfExecutors() {
		try {
			return Integer.parseInt(numOfExecutors);
		} catch(Exception ex) {
			return 1;
		}
	}

	/**
	 * @return the remoteSSHPort
	 */
	public String getRemoteSSHPort() {
		return remoteSSHPort;
	}

	/**
	 * @return the initScript
	 */
	public String getInitScript() {
		return initScript;
	}

	/**
	 * @return the rootCommandPrefix
	 */
	public String getRootCommandPrefix() {
		return rootCommandPrefix;
	}

	/**
	 * @return the restURL
	 */
	public String getRestURL() {
		return restURL;
	}

	/**
	 * @return the restUserID
	 */
	public String getRestUserID() {
		return restUserID;
	}

	/**
	 * @return the restPassword
	 */
	public String getRestPassword() {
		return restPassword;
	}

	/**
	 * @return the client
	 */
	public RestClient getClient() {
		return client;
	}

	/**
	 * @return the parent
	 */
	public Cloud360 getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Cloud360 parent) {
		this.parent = parent;
		setCredentials();
		initializeClient();
	}
	
	private void setCredentials() {
		this.restURL = parent.getCloud360RestUrl();
		this.restUserID = parent.getCloud360UserId();
		this.restPassword = parent.getCloud360Password();
	}
	
	private void initializeClient() {
		client = new RestClient(restURL, restUserID, restPassword);
	}

	public Cloud360Slave provision(TaskListener listener) throws IOException {
		PrintStream logger = listener.getLogger();

		try {
			logger.println("Launching " + label);
			return newSlave("", null, "", "", 0, "", "", false);
		} catch (FormException e) {
			throw new AssertionError(); 
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	public Cloud360Slave provisionFromUI(TaskListener listener, String computeProfileId,
			String remoteUserId, String secretPrivateKey, String remoteFSRoot, String remoteSSHPort,
			int minInstance, int maxInstance, int projectUserID,
			String projectID) throws IOException{
		PrintStream logger = listener.getLogger();
		try {
			logger.println("Launching "+computeProfileId);
			logger.println("Remote user id is: "+remoteUserId);
			logger.println("Secret key: "+secretPrivateKey);
			logger.println("Remote FS root: "+remoteFSRoot);
			logger.println("Remote SSH port: "+remoteSSHPort);
			logger.println("Min Instance: "+minInstance);
			logger.println("Max Instance "+maxInstance);
			logger.println("Project user id: "+projectUserID);
			logger.println("Project Id: "+projectID);
			
			String VMId = Cloud360LaunchUtil.launchComputeInstanceFomProfile(
					computeProfileId, this.restURL, this.restUserID, this.restPassword);
			
			//for exception handling
			//If VM id is null, Jenkins will not launch a new slave.
			if(VMId == null) {
				Exception ex = new Exception("Failed to launch a new VM. Please try after some time.");
				throw ex;
			}
			
			logger.println("Launched VM with VM Id: "+VMId);
			
			return newSlaveFromUI(computeNumberOfExecutorsForSlave(), remoteUserId,
					secretPrivateKey, remoteFSRoot, remoteSSHPort,
					minInstance, maxInstance, projectUserID,
					projectID, VMId, false);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private Cloud360Slave newSlaveFromUI(int numberOfExecutor, String remoteUserId,
			String secretPrivateKey, String remoteFSRoot, String remoteSSHPort,
			int minInstance, int maxInstance, int projectUserID,
			String projectID, String VMId, boolean isExistingInstance) 
	throws FormException, IOException {
		return new Cloud360Slave(this.restURL, this.restUserID, this.restPassword,
				VMId, remoteUserId, secretPrivateKey, 
				this.rootCommandPrefix, remoteFSRoot, remoteSSHPort, 
				this.initScript, isExistingInstance, projectUserID, 
				projectID, label, numberOfExecutor);
	}
	
	public Cloud360Slave provision(TaskListener listener, String computeProfileId,
			String remoteUserId, String secretPrivateKey, String remoteFSRoot, String remoteSSHPort,
			int minInstance, int maxInstance, String displayLabel, 
			int projectUserID,String projectID, boolean isExistingInstance) throws IOException {
		
		PrintStream logger = listener.getLogger();
		try {
			logger.println("Launching "+computeProfileId);
			logger.println("Remote user id is: "+remoteUserId);
			logger.println("Secret key: "+secretPrivateKey);
			logger.println("Remote FS root: "+remoteFSRoot);
			logger.println("Remote SSH port: "+remoteSSHPort);
			logger.println("Min Instance: "+minInstance);
			logger.println("Max Instance "+maxInstance);
			logger.println("Display Name: "+displayLabel);
			logger.println("Project user id: "+projectUserID);
			logger.println("Project Id: "+projectID);
			
			String VMId = Cloud360LaunchUtil.launchComputeInstanceFomProfile(
					computeProfileId, this.restURL, this.restUserID, this.restPassword);
			
			//for exception handling
			//If VM id is null, Jenkins will not launch a new slave.
			if(VMId == null) {
				Exception ex = new Exception("Failed to launch a new VM. Please try after some time.");
				throw ex;
			}
			
			return newSlave(isExistingInstance, computeProfileId, computeNumberOfExecutorsForSlave(), 
					remoteUserId, secretPrivateKey, remoteFSRoot, 
					remoteSSHPort, minInstance, maxInstance, 
					displayLabel, projectUserID, projectID, VMId);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private Cloud360Slave newSlave(String remoteUserId,
			String secretPrivateKey, String remoteFSRoot, String remoteSSHPort,
			int projectUserID, String projectID, String VMId, 
			boolean isExistingInstance) 
	throws FormException, IOException {
		
		int numberOfExecutor = isExistingInstance == true ? getNumOfExecutors()
				: 1;
		
		return new Cloud360Slave(this.restURL, this.restUserID, this.restPassword,
				VMId, remoteUserId, secretPrivateKey, 
				this.rootCommandPrefix, remoteFSRoot, remoteSSHPort, 
				this.initScript, isExistingInstance, projectUserID, 
				projectID, label, numberOfExecutor);
	}
	
	private Cloud360Slave newSlave(boolean isExistingInstance, String computeProfileId, int numberOfExecutor,
			String remoteUserId, String secretPrivateKey, String remoteFSRoot, String remoteSSHPort,
			int minInstance, int maxInstance, String displayLabel, 
			int projectUserID, String projectID, String VMId) throws FormException, IOException {
		return new Cloud360Slave(this.restURL, this.restUserID, this.restPassword,
				VMId, remoteUserId, secretPrivateKey, 
				this.rootCommandPrefix, remoteFSRoot, remoteSSHPort, 
				this.initScript, isExistingInstance, projectUserID, 
				projectID, label, numberOfExecutor);
	}
	
	
	/**
	 * This method is responsible for spawning an instance if it was not there.
	 * And this case instance is going to be exists after the operation
	 * 
	 * @param listener
	 * @param computeProfileId
	 * @param remoteUserId
	 * @param secretPrivateKey
	 * @param remoteFSRoot
	 * @param remoteSSHPort
	 * @param minInstance
	 * @param maxInstance
	 * @param displayLabel
	 * @param projectUserID
	 * @param projectID
	 * @param isExistingInstance
	 * @return
	 * @throws IOException
	 */
	public Cloud360Slave findOrSpawnInstance(TaskListener listener, String computeProfileId,
			String remoteUserId, String secretPrivateKey, String remoteFSRoot,
			String remoteSSHPort, int minInstance, int maxInstance,
			String displayLabel, int projectUserID,String projectID)
			throws IOException {
		PrintStream logger = listener.getLogger();

		try {
			logger.println("Launching "+computeProfileId);
			logger.println("Remote user id is: "+remoteUserId);
			logger.println("Secret key: "+secretPrivateKey);
			logger.println("Remote FS root: "+remoteFSRoot);
			logger.println("Remote SSH port: "+remoteSSHPort);
			logger.println("Min Instance: "+minInstance);
			logger.println("Max Instance "+maxInstance);
			logger.println("Display Name: "+displayLabel);
			logger.println("Project user id: "+projectUserID);
			logger.println("Project Id: "+projectID);

			// Discovering running instances with the capability
			// String label = "";
			Label labelInstance = Hudson.getInstance().getLabel(displayLabel);
			Set<Node> nodeList = labelInstance.getNodes();
			int count = labelInstance.getIdleExecutors();

			if (count > 0) {
				for (Node node : nodeList) {
					if (node.toComputer().isIdle()) {
						return (Cloud360Slave) node;
					}
				}
			}
			return provision(listener, computeProfileId,
					remoteUserId, secretPrivateKey, remoteFSRoot,
					remoteSSHPort, minInstance, maxInstance,
					displayLabel, projectUserID, projectID, false);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	public Cloud360Slave attach(String instanceId, TaskListener listener)
	throws IOException {
		PrintStream logger = listener.getLogger();
		
		try {
			logger.println("Attaching to " + instanceId);
			return newSlave("", null, "", "", 0, "", "", false);
		} catch (FormException e) {
			throw new AssertionError(); 
		}
	}
	
	private int computeNumberOfExecutorsForSlave() {
		return this.label.contains(Cloud360PluginConstants.JIT) ? 1 : getNumOfExecutors();
	}
	
	public Descriptor<Cloud360MachineTemplates> getDescriptor() {
		return Hudson.getInstance().getDescriptor(getClass());
	}

	@Extension
	public static class DescriptorImpl extends Descriptor<Cloud360MachineTemplates> {

		@Override
		public String getDisplayName() {
			return null;
		}
		
		public FormValidation doValidateComputeProfiles(
				@QueryParameter String cloud360RestUrl, 
				@QueryParameter String cloud360UserId, 
				@QueryParameter String cloud360Password,
				@QueryParameter String computeProfileId) {
			
			try {
				if(computeProfileId == null || computeProfileId.isEmpty()) {
					return FormValidation.error(Messages.CLOUD360_COMPUTE_PROFILE_NOT_MENTIONED);
				}
				
				RestClient client = new RestClient(cloud360RestUrl, cloud360UserId, cloud360Password);
				Cloud360Response resp = client.getAllClouds();
				
				if(resp.getResponseCode() != Cloud360Constants.SUCCESS) {
					return FormValidation.error(Messages.CLOUD360_CONNECTED_FAILURE.
							concat(resp.getErrorObject().getTraceableErrorInfo().getErrorDescription()));
				}
				
				List<Cloud> list = (List<Cloud>) resp.getResponseResult();
				Cloud cl = (list != null) && !list.isEmpty()?list.get(0):null;
				
				if(cl == null) {
					return FormValidation.error(Messages.NO_CLOUD_FOUND);
				}
				
				resp = client.getListOfComputeProfilesForACloud(cl.getId());
				
				if(resp.getResponseCode() != Cloud360Constants.SUCCESS) {
					return FormValidation.error(Messages.CLOUD360_COMPUTE_PROFILE_NOT_FOUND);
				}
				
				List<ComputeProfile> profiles = (List<ComputeProfile>)resp.getResponseResult();
				
				if(list != null && !list.isEmpty()) {
					for(ComputeProfile prof:profiles) {
						if(prof.getId().equals(computeProfileId)) {
							return FormValidation.ok(Messages.CLOUD360_COMPUTE_PROFILE_FOUND);			
						}
					}
				}
				return FormValidation.error(Messages.CLOUD360_COMPUTE_PROFILE_NOT_FOUND);
			} catch(Exception e) {
				return FormValidation.error(Messages.CLOUD360_CONNECTED_FAILURE);
			}
		}
		
	}

}
