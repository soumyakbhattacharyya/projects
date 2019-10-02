package hudson.plugins.cloud;

import hudson.Extension;
import hudson.Util;
import hudson.model.Describable;
import hudson.model.TaskListener;
import hudson.model.Descriptor;
import hudson.model.Descriptor.FormException;
import hudson.model.Hudson;
import hudson.model.Label;
import hudson.model.Node;
import hudson.model.labels.LabelAtom;
import hudson.plugins.cloud.util.PlatformInfraUtil;
import hudson.util.FormValidation;
import hudson.util.Secret;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.Stapler;

/*import com.cts.cloudcoe.cloudplugin.action.ImageAction;
 import com.cts.cloudcoe.cloudplugin.action.InstanceAction;
 import com.cts.cloudcoe.cloudplugin.action.LaunchInstanceAction;*/

/**
 * Template of {@link VMwareSlave} to launch.
 * 
 * @author Kohsuke Kawaguchi
 */

public class VMWareSlaveTemplate implements Describable<VMWareSlaveTemplate> {
	public final String ami;
	public final String projectId;
	public final String description;
	public final String remoteFS;
	public final String sshPort;
	public final VMWareCpuType cpuType;
	public final VMWareRamType ramType;
	public final String labels;
	public final String initScript;
	public final String userData;
	public final String numExecutors;
	public final String remoteAdmin;
	public final String rootCommandPrefix;
	public final String jvmopts;
	public final String remoteAdminPassword;
	protected transient VMWareCloud parent;

	private transient/* almost final */Set<LabelAtom> labelSet;

	@DataBoundConstructor
	public VMWareSlaveTemplate(String ami,String projectId, String remoteFS, String sshPort,
			VMWareCpuType cpuType, VMWareRamType ramType, String labelString,
			String description, String initScript, String userData,
			String numExecutors, String remoteAdmin, String rootCommandPrefix,
			String jvmopts, String remoteAdminPassword) {
		// super(ami, remoteFS, sshPort, type, labelString, description,
		// initScript, userData, numExecutors, remoteAdmin, rootCommandPrefix,
		// jvmopts, remoteAdminPassword);
		this.ami = ami;
		this.projectId = projectId;
		this.remoteFS = remoteFS;
		this.sshPort = sshPort;
		this.cpuType = cpuType;
		this.ramType = ramType;
		this.labels = Util.fixNull(labelString);
		this.description = description;
		this.initScript = initScript;
		this.userData = userData;
		this.numExecutors = Util.fixNull(numExecutors).trim();
		this.remoteAdmin = remoteAdmin;
		this.remoteAdminPassword = remoteAdminPassword;
		this.rootCommandPrefix = rootCommandPrefix;
		this.jvmopts = jvmopts;
		readResolve(); // initialize
	}

	public String getRemoteAdminPassword() {
		return remoteAdminPassword;
	}

	public PrivateCloud getParent() {
		return parent;
	}

	public String getLabelString() {
		return labels;
	}

	public String getDisplayName() {
		return description + " (" + ami + ")";
	}

	public int getNumExecutors() {
		try {
			return Integer.parseInt(this.numExecutors);
		} catch (NumberFormatException e) {
			return VMwareSlave.toNumExecutorsForVMWare(cpuType);
		}
	}

	public int getSshPort() {
		try {
			return Integer.parseInt(sshPort);
		} catch (NumberFormatException e) {
			return 22;
		}
	}

	public String getRemoteAdmin() {
		return remoteAdmin;
	}

	public String getRootCommandPrefix() {
		return rootCommandPrefix;
	}

	public Set getLabelSet() {
		return labelSet;
	}

	
	public String getProjectId() {
		return projectId;
	}

	/**
	 * Does this contain the given label?
	 * 
	 * @param l
	 *            can be null to indicate "don't care".
	 */
	public boolean containsLabel(Label l) {
		return l == null || labelSet.contains(l);
	}

	/**
	 * Provisions a new VMware slave.
	 * 
	 * @return always non-null. This needs to be then added to
	 *         {@link Hudson#addNode(Node)}.
	 */
	public VMwareSlave provision(TaskListener listener) throws IOException {
		PrintStream logger = listener.getLogger();

		try {
			logger.println("Launching " + ami);
			return newSlave(false, "", "", "", "", "", "", null, "", "", "");
		} catch (FormException e) {
			throw new AssertionError(); // we should have discovered all
										// configuration issues upfront
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * CTSJPAASBAAS-4 - Provisions a new VMware slave with additional parameters
	 * 
	 * Description of field "isExistingInstance" ==> This field is responsible
	 * whether the instance is going to be persisted after the operation or not.
	 * 
	 * @return always non-null. This needs to be then added to
	 *         {@link Hudson#addNode(Node)}.
	 */
	public VMwareSlave provision(TaskListener listener, String accountNumber,
			String userId, String imageId, String kernelId, String imageType,
			int minInstance, int maxInstance, String securityCredential,
			String accessId, String cloudType, VMwarePrivateKey privateKey,
			boolean isExistingInstance, String displayLabel, String projectUserID,
			String projectID) throws IOException {
		PrintStream logger = listener.getLogger();

		try {
			logger.println("Launching " + ami);
			logger.println("With projectId " + projectId);
			logger.println("Account Id " + accountNumber);
			logger.println("Image Id " + imageId);
			logger.println("Kernel Id " + kernelId);
			logger.println("User Id " + userId);
			logger.println("Image Type " + imageType);
			logger.println("maxInstance " + maxInstance);
			logger.println("minInstance" + minInstance);
			logger.println("securityCredential" + securityCredential);
			logger.println("accessId" + accessId);
			logger.println("cloudType" + cloudType);

			String VMId = PlatformInfraUtil.launchVirtualMachine(accountNumber,
					userId, imageId, kernelId, imageType, minInstance,
					maxInstance, securityCredential, accessId, cloudType, projectUserID,
					projectId);

			return newSlave(isExistingInstance, accountNumber, userId, VMId,
					securityCredential, accessId, cloudType, privateKey, displayLabel,
					projectUserID, projectId);
		} catch (FormException e) {
			throw new AssertionError(); // we should have discovered all
										// configuration issues upfront
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Provision a new VMware slave with user credential
	 * 
	 * @param listener
	 * @param accountNumber
	 * @param userId
	 * @param imageId
	 * @param kernelId
	 * @param imageType
	 * @param minInstance
	 * @param maxInstance
	 * @param key
	 * @param group
	 * @param securityCredential
	 * @param accessId
	 * @param cloudType
	 * @param privateKey
	 * @param displayLabel
	 * @return
	 * @throws IOException
	 */
	public VMwareSlave provisionFromUI(TaskListener listener,
			String accountNumber, String userId, String imageId,
			String kernelId, String imageType, int minInstance,
			int maxInstance, String securityCredential, String accessId,
			String cloudType, VMwarePrivateKey privateKey, String displayLabel,
			String projectUserID, String projectID) throws IOException {
		PrintStream logger = listener.getLogger();

		try {
			logger.println("Launching as triggered by UI" + ami);
			logger.println("With projectId as "+projectId);
			logger.println("Account Id " + accountNumber);
			logger.println("Image Id " + imageId);
			logger.println("Kernel Id " + kernelId);

			logger.println("User Id " + userId);
			logger.println("Image Type " + imageType);
			logger.println("maxInstance " + maxInstance);
			logger.println("minInstance" + minInstance);
			logger.println("securityCredential" + securityCredential);
			logger.println("accessId" + accessId);
			logger.println("cloudType" + cloudType);

			String VMId = PlatformInfraUtil.launchVirtualMachine(accountNumber,
					userId, imageId, kernelId, imageType, minInstance,
					maxInstance, securityCredential, accessId, cloudType, projectUserID,
					projectId);
			return newSlave(false, accountNumber, userId, VMId, securityCredential,
					accessId, cloudType, privateKey, displayLabel, projectUserID, projectID);
		} catch (FormException e) {
			throw new AssertionError(); // we should have discovered all
										// configuration issues upfront
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * CTSJPAASBAAS-4 - Provisions a new VMware slave with additional parameters
	 * 
	 * @return always non-null. This needs to be then added to
	 *         {@link Hudson#addNode(Node)}.
	 */
	public VMwareSlave findToRegisterExistingInstance(TaskListener listener,
			String existingInstanceId, String accountNumber, String userId,
			String imageId, String kernelId, String imageType, int minInstance,
			int maxInstance, String securityCredential, String accessId,
			String cloudType, VMwarePrivateKey privateKey, String projectUserID,
			String projectID) throws IOException {
		PrintStream logger = listener.getLogger();

		try {
			logger.println("Finding existing instance with following instance id "
					+ ami);
			logger.println("projectId "+projectId);
			logger.println("existingInstanceId " + existingInstanceId);
			logger.println("Account Id " + accountNumber);
			logger.println("Image Id " + imageId);
			logger.println("Kernel Id " + kernelId);
			logger.println("User Id " + userId);
			logger.println("Image Type " + imageType);
			logger.println("maxInstance " + maxInstance);
			logger.println("minInstance" + minInstance);
			logger.println("securityCredential" + securityCredential);
			logger.println("accessId" + accessId);
			logger.println("cloudType" + cloudType);
			if (exists(listener, accountNumber, existingInstanceId, userId,
					securityCredential, accessId, cloudType, projectUserID, projectID)) {
				return newSlave(true, accountNumber, userId,
						existingInstanceId, securityCredential, accessId, cloudType,
						privateKey, existingInstanceId, projectUserID,
						projectID);
			}
		} catch (FormException e) {
			throw new AssertionError(); // we should have discovered all
										// configuration issues upfront
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return null;

	}

	/**
	 * This method is responsible for spawning an instance if it was not there.
	 * And this case instance is going to be exists after the operation
	 * 
	 * @param listener
	 * @param existingInstanceId
	 * @param accountNumber
	 * @param userId
	 * @param imageId
	 * @param kernelId
	 * @param imageType
	 * @param minInstance
	 * @param maxInstance
	 * @param key
	 * @param group
	 * @param securityCredential
	 * @param accessId
	 * @param cloudType
	 * @param privateKey
	 * @return
	 * @throws IOException
	 */

	public VMwareSlave findOrSpawnInstance(TaskListener listener,
			String labelName, String accountNumber, String userId,
			String imageId, String kernelId, String imageType, int minInstance,
			int maxInstance, String securityCredential, String accessId,
			String cloudType, VMwarePrivateKey privateKey, String projectUserID,
			String projectID) throws IOException {
		PrintStream logger = listener.getLogger();

		try {
			logger.println("Finding existing instance with following instance id "
					+ ami);
			logger.println("with projectId "+projectId);
			logger.println("labelName " + labelName);
			logger.println("Account Id " + accountNumber);
			logger.println("Image Id " + imageId);
			logger.println("Kernel Id " + kernelId);
			logger.println("User Id " + userId);
			logger.println("Image Type " + imageType);
			logger.println("maxInstance " + maxInstance);
			logger.println("minInstance" + minInstance);
			logger.println("securityCredential" + securityCredential);
			logger.println("accessId" + accessId);
			logger.println("cloudType" + cloudType);

			// Discovering running instances with the capability
			// String label = "";
			Label labelInstance = Hudson.getInstance().getLabel(labelName);
			Set<Node> nodeList = labelInstance.getNodes();
			int count = labelInstance.getIdleExecutors();

			if (count > 0) {
				for (Node node : nodeList) {
					if (node.toComputer().isIdle()) {
						return (VMwareSlave) node;
					}
				}
			}
			return provision(listener, accountNumber, userId, imageId,
					kernelId, imageType, minInstance, maxInstance, securityCredential,
					accessId, cloudType, privateKey, false, labelName,
					projectUserID, projectId);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	// Verfies if this an existing instance
	private boolean exists(TaskListener listener, String accountNumber,
			String existingInstanceId, String userId, String securityCredential,
			String accessId, String cloudType, String projectUserID,
			String projectID) {
		// Is this instance already registered for the given credential

		PrintStream logger = listener.getLogger();
		if (existingInstanceId != null
				&& StringUtils.contains(existingInstanceId,
						VMwareConstant.THIRDPARTY)) {
			logger.println("Removing unwanted character in instance id.");
			existingInstanceId = existingInstanceId.replace(
					VMwareConstant.THIRDPARTY, "");
		}

		boolean flag = PlatformInfraUtil.isRunningVirtualMachine(accountNumber,
				existingInstanceId, userId, securityCredential, accessId, accessId,
				projectUserID, projectId);
		logger.println("Is this an existing instance " + flag);
		return flag;
	}

	private VMwareSlave newSlave(boolean isExistingInstance,
			String accountNumber, String userId, String VMId, String securityCredential,
			String accessId, String cloudType, VMwarePrivateKey privateKey,
			String displayLabel, String projectUserID, String projectID)
			throws FormException, IOException {
		// Assert the number of execution for the EC2 Slave
		// If this is an existing instance go ahead with finding the number of
		// executors on them
		// If not, assume this as a JIT VM and hence default the number of
		// execution to 1
		int numberOfExecutor = doConsiderExecutorsForSlave() == true ? getNumExecutors()
				: 1;
		return new VMwareSlave(remoteAdminPassword, isExistingInstance,
				accountNumber, userId, securityCredential, accessId, cloudType, VMId,
				description, remoteFS, getSshPort(), numberOfExecutor,
				displayLabel, initScript, remoteAdmin, rootCommandPrefix,
				jvmopts, privateKey, projectUserID, projectID);
	}

	/**
	 * Incase of NON JIT executor use the num of executor specified in
	 * configuration
	 * 
	 * @return
	 */
	private boolean doConsiderExecutorsForSlave() {
		return this.labels.contains(VMwareConstant.JIT) ? false : true;
	}

	/**
	 * Provisions a new EC2 slave based on the currently running instance on
	 * EC2, instead of starting a new one.
	 */
	public VMwareSlave attach(String instanceId, TaskListener listener)
			throws IOException {
		PrintStream logger = listener.getLogger();

		try {
			logger.println("Attaching to " + instanceId);
			return newSlave(false, "", "", "", "", "", "", null, "", "", "");
		} catch (FormException e) {
			throw new AssertionError(); // we should have discovered all
										// configuration issues upfront
		}
	}

	/**
	 * Initializes data structure that we don't persist.
	 */
	protected Object readResolve() {
		labelSet = Label.parse(labels);
		return this;
	}

	public Descriptor<VMWareSlaveTemplate> getDescriptor() {
		return Hudson.getInstance().getDescriptor(getClass());
	}

	@Extension
	public static final class DescriptorImpl extends
			Descriptor<VMWareSlaveTemplate> {
		public String getDisplayName() {
			return null;
		}

		/**
		 * Since this shares much of the configuration with
		 * {@link SlaveComputer}, check its help page, too.
		 */
		@Override
		public String getHelpFile(String fieldName) {
			String p = super.getHelpFile(fieldName);
			if (p == null)
				p = Hudson.getInstance().getDescriptor(VMwareSlave.class)
						.getHelpFile(fieldName);
			return p;
		}

		/***
		 * Check that the AMI requested is available in the cloud and can be
		 * used.
		 */
		public FormValidation doValidateAmi(@QueryParameter String accountId,
				@QueryParameter String securityCredential,
				@QueryParameter String accessId,
				final @QueryParameter String ami,
				@QueryParameter String projectUserID,
				@QueryParameter String projectId) throws IOException,
				ServletException {

			String decryptedSecretKey = Secret.decrypt(securityCredential)
					.getPlainText();

			String providerCode = VMwareConstant.VMWareCloud;

			if (accountId == null && ami == null
					&& PrivateCloud.get().getVmOwnerId() == null) {
				return FormValidation
						.error("Test successful connectivity to the parent cloud provider first");
			}

			String accountNumber = accountId;
			String userId = PrivateCloud.get().getVmOwnerId();

			boolean isAMIValid = PlatformInfraUtil.getMachineImage(
					accountNumber, ami, userId, decryptedSecretKey, accessId,
					providerCode, projectUserID, projectId);
			if (!isAMIValid)
				// de-registered AMI causes an empty list to be
				// returned. so be defensive
				// against other possibilities
				return FormValidation
						.error("Have you tested connectivity to the parent cloud provider first, as no such AMI exists or is usable with this accessId: "
								+ ami);
				return FormValidation.ok(" Image available ");
		}
	}
}
