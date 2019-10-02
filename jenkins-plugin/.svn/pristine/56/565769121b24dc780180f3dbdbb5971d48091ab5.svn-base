package hudson.plugins;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.model.Descriptor.FormException;
import hudson.model.Node.Mode;
import hudson.model.Slave.SlaveDescriptor;
import hudson.model.Computer;
import hudson.model.Hudson;
import hudson.model.Slave;
import hudson.plugins.util.Cloud360LaunchUtil;
import hudson.slaves.NodeProperty;
import hudson.slaves.ComputerLauncher;
import hudson.slaves.RetentionStrategy;

public class Cloud360Slave extends Slave{
	
	private static final Logger LOGGER = Logger.getLogger(Cloud360Slave.class
			.getName());
	
	public final String remoteUserId; // e.g. 'ubuntu'
	public final String secretPrivateKey; // e.g. password for ubuntu
	public final String rootCommandPrefix; // e.g. 'sudo'
	private final String remoteFSRoot;
	private final String remoteSSHPort;
	public final String initScript;
	public final boolean isExistingInstance;
	public final int projectUserID;
	public final String projectID;
	
	private final String restURL;
	private final String restUserID;
	private final String restPassword;
	
	public Cloud360Slave(String restURL, String restUserID, String restPassword,
			String instanceId, String remoteUserId, 
			String secretPrivateKey, String rootCommandPrefix, 
			String remoteFSRoot, String remoteSSHPort, 
			String initScript, boolean isExistingInstance, 
			int projectUserID, String projectID, String label, int numOfExecutors) 
	throws FormException, IOException{
		this(restURL, restUserID, restPassword,
				instanceId, remoteUserId, secretPrivateKey, 
				rootCommandPrefix, remoteFSRoot, remoteSSHPort,
				initScript, isExistingInstance, projectUserID,
				projectID, label, Collections.<NodeProperty<?>> emptyList(), 
				Mode.NORMAL, numOfExecutors);
	}

	@DataBoundConstructor
	public Cloud360Slave(String restURL, String restUserID, String restPassword,
			String instanceId, String remoteUserId, 
			String secretPrivateKey, String rootCommandPrefix, 
			String remoteFSRoot, String remoteSSHPort, 
			String initScript, boolean isExistingInstance, 
			int projectUserID, String projectID, String label, 
			List<? extends NodeProperty<?>> nodeProperties,
			Mode mode, int numOfExecutors) 
	throws FormException, IOException{
		super(instanceId, "", remoteFSRoot, numOfExecutors, mode,
				label, new Cloud360ComputerLauncher(),
				new Cloud360RetentionStrategy(), nodeProperties);
		
		this.restURL = restURL;
		this.restUserID = restUserID;
		this.restPassword = restPassword;
		this.remoteUserId = remoteUserId;
		this.secretPrivateKey = secretPrivateKey;
		this.rootCommandPrefix = rootCommandPrefix;
		this.remoteFSRoot = remoteFSRoot;
		this.remoteSSHPort = remoteSSHPort;
		this.initScript = initScript;
		this.isExistingInstance = isExistingInstance;
		this.projectUserID = projectUserID;
		this.projectID = projectID;
	}
	
	public String getInstanceId() {
		return getNodeName();
	}

	@Override
	public Computer createComputer() {
		return new Cloud360Computer(this);
	}

	/**
	 * CTSJPAASBAAS-5 - Terminates the instance in Cloud360 with additional
	 * parameters
	 * 
	 * This method is responsible for terminating the Instance in below two
	 * cases :: 1. If the Instance is going to be deleted from UI 2. If the
	 * Instance is going to be deleted due to Retention Strategy
	 * 
	 * Both the cases Instance will be deleted and Unregistered.
	 * 
	 */
	public void terminateInstance(String instanceIdToBeTerminated) throws Exception {

		String instanceId = instanceIdToBeTerminated;
		LOGGER.info(" Terminating instance with following information "
				+ instanceId + " instanceId ");

		LOGGER.info("terminateWithDeregister is true with additional params");

		try {
			Cloud360LaunchUtil.terminateComputeInstance(instanceId, this.restURL, this.restUserID, this.restPassword);

		} catch (Exception e) {
			LOGGER.severe("Exception occured while terminate as "
					+ e.getMessage());
		}
		LOGGER.info("Removing the node from Jenkins registered nodes : "
				+ getInstanceId());
		Hudson.getInstance().removeNode(this);
	}
	
	/**
	 * @return the remoteUserId
	 */
	public String getRemoteUserId() {
		if (remoteUserId == null || remoteUserId.length() == 0)
			return "root";
		return remoteUserId;
	}

	/**
	 * @return the secretPrivateKey
	 */
	public String getSecretPrivateKey() {
		if (secretPrivateKey == null || secretPrivateKey.length() == 0)
			return "";
		return secretPrivateKey;
	}

	/**
	 * @return the rootCommandPrefix
	 */
	public String getRootCommandPrefix() {
		if (rootCommandPrefix == null || rootCommandPrefix.length() == 0)
			return "";
		return rootCommandPrefix + " ";
	}
	
	public int getLocalPort() {
		return 22;
	}

	/**
	 * @return the remoteFSRoot
	 */
	public String getRemoteFSRoot() {
		return remoteFSRoot;
	}

	/**
	 * @return the remoteSSHPort
	 */
	public int getRemoteSSHPort() {
		int remotePort = Integer.parseInt(remoteSSHPort);
		return remotePort != 0 ? remotePort : 22;
	}

	public String getProtocol() {
		return "tcp";
	}
	
	/**
	 * @return the initScript
	 */
	public String getInitScript() {
		return initScript;
	}

	/**
	 * @return the isExistingInstance
	 */
	public boolean isExistingInstance() {
		return isExistingInstance;
	}

	/**
	 * @return the projectUserID
	 */
	public int getProjectUserID() {
		return projectUserID;
	}

	/**
	 * @return the projectID
	 */
	public String getProjectID() {
		return projectID;
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



	@Extension
	public static final class DescriptorImpl extends SlaveDescriptor {
		public String getDisplayName() {
			return "Cloud360 Cloud";
		}

		@Override
		public boolean isInstantiable() {
			return false;
		}
	}
}
