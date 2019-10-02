package hudson.plugins.ec2;

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
import hudson.plugins.ec2.util.PlatformInfraUtil;
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

import com.cognizant.jpaas2.commons.expection.PlatformException;



/*import com.cts.cloudcoe.cloudplugin.action.ImageAction;
 import com.cts.cloudcoe.cloudplugin.action.InstanceAction;
 import com.cts.cloudcoe.cloudplugin.action.LaunchInstanceAction;*/

/**
 * Template of {@link EC2Slave} to launch.
 * 
 * @author Kohsuke Kawaguchi
 */

public class SlaveTemplate implements Describable<SlaveTemplate> {
	public final String ami;
	public final String description;
	public final String remoteFS;
	public final String sshPort;
	public final JPaaSInstanceType type;
	public final String labels;
	public final String initScript;
	public final String userData;
	public final String numExecutors;
	public final String remoteAdmin;
	public final String rootCommandPrefix;
	public final String jvmopts;
	public final String remoteAdminPassword;
	protected transient EC2Cloud parent;

	private transient/* almost final */Set<LabelAtom> labelSet;

	@DataBoundConstructor
	public SlaveTemplate(String ami, String remoteFS, String sshPort,
			JPaaSInstanceType type, String labelString, String description,
			String initScript, String userData, String numExecutors,
			String remoteAdmin, String rootCommandPrefix, String jvmopts,
			String remoteAdminPassword) {
		// System.out.println(" ami " + ami + " remoteFS " + remoteFS
		// + " sshPort " + sshPort + " type " + type + " labelString "
		// + labelString + " description " + description + " initScript "
		// + initScript + " userData " + userData + " numExecutors "
		// + numExecutors + " remoteAdmin " + remoteAdmin
		// + " rootCommandPrefix " + rootCommandPrefix + " jvmopts "
		// + jvmopts);
		this.ami = ami;
		this.remoteFS = remoteFS;
		this.sshPort = sshPort;
		this.type = type;
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

	public EC2Cloud getParent() {
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
			return Integer.parseInt(numExecutors);
		} catch (NumberFormatException e) {
			return EC2Slave.toNumExecutors(type);
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
	 * Provisions a new EC2 slave.
	 * 
	 * @return always non-null. This needs to be then added to
	 *         {@link Hudson#addNode(Node)}.
	 */
	public EC2Slave provision(TaskListener listener) throws /* EC2Exception, */
	IOException {
		PrintStream logger = listener.getLogger();
		// Jec2 ec2 = getParent().connect();

		try {
			logger.println("Launching " + ami);
			return newSlave(false, "", "", "", "", "", "", null, "","","");
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
	 * CTSJPAASBAAS-4 - Provisions a new EC2 slave with additional parameters
	 * 
	 * Description of field "isExistingInstance" ==> This field is responsible
	 * whether the instance is going to be persisted after the operation or not.
	 * 
	 * @return always non-null. This needs to be then added to
	 *         {@link Hudson#addNode(Node)}.
	 */
	public EC2Slave provision(TaskListener listener, String accountNumber,
			String userId, String imageId, String kernelId, String imageType,
			int minInstance, int maxInstance, String key, String group,
			String secretKey, String accessId, String cloudType,
			EC2PrivateKey privateKey, boolean isExistingInstance,
			String displayLabel,String projectUserID,String projectID) throws /* EC2Exception, */IOException {
		PrintStream logger = listener.getLogger();
		// Jec2 ec2 = getParent().connect();

		try {
			logger.println("Launching " + ami);
			// KeyPairInfo keyPair = parent.getPrivateKey().find(ec2);
			// if(keyPair==null)
			// throw new
			// EC2Exception("No matching keypair found on EC2. Is the EC2 private key a valid one?");
			// Instance inst = ec2.runInstances(ami, 1, 1,
			// Collections.<String>emptyList(), userData, keyPair.getKeyName(),
			// type).getInstances().get(0);
			logger.println("Account Id " + accountNumber);
			logger.println("Image Id " + imageId);
			logger.println("Kernel Id " + kernelId);
			logger.println("Key " + key);
			logger.println("Group " + group);
			logger.println("User Id " + userId);
			logger.println("Image Type " + imageType);
			logger.println("maxInstance " + maxInstance);
			logger.println("minInstance" + minInstance);
			logger.println("secretKey" + secretKey);
			logger.println("accessId" + accessId);
			logger.println("cloudType" + cloudType);
			logger.println("projectUserID" + projectUserID);
			logger.println("projectID" + projectID);
			
			/*
			 * String VMId = (new LaunchInstanceAction()).runInstance(
			 * accountNumber, userId, imageId, kernelId, imageType, minInstance,
			 * maxInstance, key, group, secretKey, accessId, cloudType);
			 */

			String VMId = PlatformInfraUtil.launchVirtualMachine(accountNumber,
					userId, imageId, kernelId, imageType, minInstance,
					maxInstance, key, group, secretKey, accessId, cloudType,projectUserID,projectID);

			// List<ReservationDescription> instancesLst = ec2
			// .describeInstances(new String[] { VMId });
			// Instance inst = instancesLst.get(0).getInstances().get(0);
			// return newSlave(inst);
			return newSlave(isExistingInstance,
					computeNumberOfExecutorsForSlave(), accountNumber, userId,
					VMId, secretKey, accessId, cloudType, privateKey,
					displayLabel,projectUserID,projectID);
		}catch(PlatformException pException){			
			throw new RuntimeException(pException.getLocalizedMessage());			
		}		
		catch (FormException e) {
			throw new AssertionError(); // we should have discovered all
										// configuration issues upfront
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	private int computeNumberOfExecutorsForSlave() {
		return this.labels.contains(EC2Constant.JIT) ? 1 : getNumExecutors();
	}

	/**
	 * Provision a new EC2 slave with user credentials
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
	 * @param secretKey
	 * @param accessId
	 * @param cloudType
	 * @param privateKey
	 * @param displayLabel
	 * @return
	 * @throws IOException
	 */
	public EC2Slave provisionFromUI(TaskListener listener,
			String accountNumber, String userId, String imageId,
			String kernelId, String imageType, int minInstance,
			int maxInstance, String key, String group, String secretKey,
			String accessId, String cloudType, EC2PrivateKey privateKey,
			String displayLabel,String projectUserID,String projectID) throws /* EC2Exception, */IOException {
		PrintStream logger = listener.getLogger();
		// Jec2 ec2 = getParent().connect();

		try {
			logger.println("Launching as triggered by UI" + ami);
			// KeyPairInfo keyPair = parent.getPrivateKey().find(ec2);
			// if(keyPair==null)
			// throw new
			// EC2Exception("No matching keypair found on EC2. Is the EC2 private key a valid one?");
			// Instance inst = ec2.runInstances(ami, 1, 1,
			// Collections.<String>emptyList(), userData, keyPair.getKeyName(),
			// type).getInstances().get(0);
			logger.println("Account Id " + accountNumber);
			logger.println("Image Id " + imageId);
			logger.println("Kernel Id " + kernelId);
			logger.println("Key " + key);
			logger.println("Group " + group);
			logger.println("User Id " + userId);
			logger.println("Image Type " + imageType);
			logger.println("maxInstance " + maxInstance);
			logger.println("minInstance" + minInstance);
			logger.println("secretKey" + secretKey);
			logger.println("accessId" + accessId);
			logger.println("cloudType" + cloudType);
			logger.println("projectUserID" + projectUserID);
			logger.println("projectID" + projectID);
			/*
			 * String VMId = (new LaunchInstanceAction()).runInstance(
			 * accountNumber, userId, imageId, kernelId, imageType, minInstance,
			 * maxInstance, key, group, secretKey, accessId, cloudType);
			 */

			String VMId = PlatformInfraUtil.launchVirtualMachine(accountNumber,
					userId, imageId, kernelId, imageType, minInstance,
					maxInstance, key, group, secretKey, accessId, cloudType,projectUserID,projectID);

			logger.println("VMId for after launch as " + VMId);

			// List<ReservationDescription> instancesLst = ec2
			// .describeInstances(new String[] { VMId });
			// Instance inst = instancesLst.get(0).getInstances().get(0);
			// return newSlave(inst);
			return newSlave(false, computeNumberOfExecutorsForSlave(),
					accountNumber, userId, VMId, secretKey, accessId,
					cloudType, privateKey, displayLabel,projectUserID,projectID);
		}catch(PlatformException pException){			
			throw new RuntimeException(pException.getLocalizedMessage());			
		} 
		catch (FormException e) {
			throw new AssertionError(); // we should have discovered all
										// configuration issues upfront
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * CTSJPAASBAAS-4 - Provisions a new EC2 slave with additional parameters
	 * 
	 * @return always non-null. This needs to be then added to
	 *         {@link Hudson#addNode(Node)}.
	 */
	public EC2Slave findToRegisterExistingInstance(TaskListener listener,
			String existingInstanceId, String accountNumber, String userId,
			String imageId, String kernelId, String imageType, int minInstance,
			int maxInstance, String key, String group, String secretKey,
			String accessId, String cloudType, EC2PrivateKey privateKey,String projectUserID,String projectID)
			throws /* EC2Exception, */IOException {
		PrintStream logger = listener.getLogger();

		try {
			logger.println("Finding existing instance with following instance id "
					+ ami);
			logger.println("existingInstanceId " + existingInstanceId);
			logger.println("Account Id " + accountNumber);
			logger.println("Image Id " + imageId);
			logger.println("Kernel Id " + kernelId);
			logger.println("Key " + key);
			logger.println("Group " + group);
			logger.println("User Id " + userId);
			logger.println("Image Type " + imageType);
			logger.println("maxInstance " + maxInstance);
			logger.println("minInstance" + minInstance);
			logger.println("secretKey" + secretKey);
			logger.println("accessId" + accessId);
			logger.println("cloudType" + cloudType);
			if (exists(listener, accountNumber, existingInstanceId, userId,
					secretKey, accessId, cloudType,projectUserID,projectID)) {
				return newSlave(true, computeNumberOfExecutorsForSlave(),
						accountNumber, userId, existingInstanceId, secretKey,
						accessId, cloudType, privateKey, existingInstanceId,projectUserID,projectID);
			}
		} catch (FormException e) {
			throw new AssertionError(); // we should have discovered all
										// configuration issues upfront
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
	 * @param secretKey
	 * @param accessId
	 * @param cloudType
	 * @param privateKey
	 * @return
	 * @throws IOException
	 */

	public EC2Slave findOrSpawnInstance(TaskListener listener,
			String labelName, String accountNumber, String userId,
			String imageId, String kernelId, String imageType, int minInstance,
			int maxInstance, String key, String group, String secretKey,
			String accessId, String cloudType, EC2PrivateKey privateKey,String projectUserID,String projectID)
			throws /* EC2Exception, */IOException {
		PrintStream logger = listener.getLogger();

		try {
			logger.println("Finding existing instance with following instance id "
					+ ami);
			logger.println("labelName " + labelName);
			logger.println("Account Id " + accountNumber);
			logger.println("Image Id " + imageId);
			logger.println("Kernel Id " + kernelId);
			logger.println("Key " + key);
			logger.println("Group " + group);
			logger.println("User Id " + userId);
			logger.println("Image Type " + imageType);
			logger.println("maxInstance " + maxInstance);
			logger.println("minInstance" + minInstance);
			logger.println("secretKey" + secretKey);
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
						return (EC2Slave) node;
					}
				}
			}
			return provision(listener, accountNumber, userId, imageId,
					kernelId, imageType, minInstance, maxInstance, key, group,
					secretKey, accessId, cloudType, privateKey, false,
					labelName,projectUserID,projectID);
			// else{
			// return provision(listener, accountNumber, userId, imageId,
			// kernelId, imageType, minInstance, maxInstance, key, group,
			// secretKey, accessId, cloudType, privateKey, true, labelName);
			// }

			// if(!labelInstance.isEmpty()){

			/*
			 * Set<Node> nodeList = labelInstance.getNodes();
			 * if(nodeList.isEmpty()){ return provision(listener, accountNumber,
			 * userId, imageId, kernelId, imageType, minInstance, maxInstance,
			 * key, group, secretKey, accessId, cloudType, privateKey, true,
			 * labelName); }else{ return (EC2Slave)nodeList.iterator().next(); }
			 */

			/*
			 * }else{ return provision(listener, accountNumber, userId, imageId,
			 * kernelId, imageType, minInstance, maxInstance, key, group,
			 * secretKey, accessId, cloudType, privateKey, true, labelName); }
			 */

			/*
			 * if (exists(listener, accountNumber, existingInstanceId, userId,
			 * secretKey, accessId, cloudType)) { return
			 * newSlave(true,accountNumber,userId,existingInstanceId, secretKey,
			 * accessId, cloudType, privateKey, displayLabel); }
			 */

		} /*
		 * catch (FormException e) { throw new AssertionError(); // we should
		 * have discovered all // configuration issues upfront }
		 */catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Verifies if the specified account credentials is present or not
	 * 
	 */
	private boolean exists(TaskListener listener, String accountNumber,
			String existingInstanceId, String userId, String secretKey,
			String accessId, String cloudType,String projectUserID,String projectID) {
		// Is this instance already registered for the given credential
		// InstanceAction insac = new InstanceAction();
		PrintStream logger = listener.getLogger();
		existingInstanceId = removeThirdPartyConstant(existingInstanceId,
				logger);
		// boolean flag =
		// insac.isInstanceRunning(accountNumber,existingInstanceId, userId,
		// secretKey, accessId, cloudType);

		boolean flag = PlatformInfraUtil.isRunningVirtualMachine(
				accountNumber, existingInstanceId, userId, secretKey, accessId,
				cloudType,projectUserID,projectID);

		logger.println("Is this an existing instance " + flag);
		return flag;
	}

	/**
	 * Removes third party constant from the slave label
	 * 
	 * @param existingInstanceId
	 * @param logger
	 * @return instance id
	 */
	private String removeThirdPartyConstant(String existingInstanceId,
			PrintStream logger) {
		if (existingInstanceId != null
				&& StringUtils.contains(existingInstanceId,
						EC2Constant.THIRDPARTY)) {
			logger.println("Removing unwanted character in instance id.");
			existingInstanceId = existingInstanceId.replace(
					EC2Constant.THIRDPARTY, "");
		}
		return existingInstanceId;
	}

	/**
	 * Assigning number of executors for an existing instance based on instance
	 * type
	 * 
	 * @param isExistingInstance
	 * @param accountNumber
	 * @param userId
	 * @param VMId
	 * @param secretKey
	 * @param accessId
	 * @param cloudType
	 * @param privateKey
	 * @param displayLabel
	 * @return
	 * @throws FormException
	 * @throws IOException
	 */
	private EC2Slave newSlave(boolean isExistingInstance, String accountNumber,
			String userId, String VMId, String secretKey, String accessId,
			String cloudType, EC2PrivateKey privateKey, String displayLabel,String projectUserID,
			String projectID)
			throws FormException, IOException {
		// Assert the number of execution for the EC2 Slave
		// If this is an existing instance go ahead with finding the number of
		// executors on them
		// If not, assume this as a JIT VM and hence default the number of
		// execution to 1
		int numberOfExecutor = isExistingInstance == true ? getNumExecutors()
				: 1;
		return new EC2Slave(remoteAdminPassword, isExistingInstance,
				accountNumber, userId, secretKey, accessId, cloudType, VMId,
				description, remoteFS, getSshPort(), numberOfExecutor,
				displayLabel, initScript, remoteAdmin, rootCommandPrefix,
				jvmopts, privateKey,projectUserID,projectID);
	}

	private EC2Slave newSlave(boolean isExistingInstance, int numberOfExecutor,
			String accountNumber, String userId, String VMId, String secretKey,
			String accessId, String cloudType, EC2PrivateKey privateKey,
			String displayLabel,String projectUserID,String projectID) throws FormException, IOException {

		return new EC2Slave(remoteAdminPassword, isExistingInstance,
				accountNumber, userId, secretKey, accessId, cloudType, VMId,
				description, remoteFS, getSshPort(), numberOfExecutor,
				displayLabel, initScript, remoteAdmin, rootCommandPrefix,
				jvmopts, privateKey,projectUserID,projectID);
	}

	/**
	 * Provisions a new EC2 slave based on the currently running instance on
	 * EC2, instead of starting a new one.
	 */
	public EC2Slave attach(String instanceId, TaskListener listener)
			throws /* EC2Exception, */IOException {
		PrintStream logger = listener.getLogger();
		// Jec2 ec2 = getParent().connect();

		try {
			logger.println("Attaching to " + instanceId);
			// Instance inst =
			// ec2.describeInstances(Collections.singletonList(instanceId)).get(0).getInstances().get(0);
			return newSlave(false, "", "", "", "", "", "", null, "","","");
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

	public Descriptor<SlaveTemplate> getDescriptor() {
		return Hudson.getInstance().getDescriptor(getClass());
	}

	@Extension
	public static final class DescriptorImpl extends Descriptor<SlaveTemplate> {
		public String getDisplayName() {
			return null;
		}

		/**
		 * Since this shares much of the configuration with {@link EC2Computer},
		 * check its help page, too.
		 */
		@Override
		public String getHelpFile(String fieldName) {
			String p = super.getHelpFile(fieldName);
			if (p == null)
				p = Hudson.getInstance().getDescriptor(EC2Slave.class)
						.getHelpFile(fieldName);
			return p;
		}

		/***
		 * Check that the AMI requested is available in the cloud and can be
		 * used.
		 */
		public FormValidation doValidateAmi(@QueryParameter String accountId,
				@QueryParameter String secretKey,
				@QueryParameter String accessId,
				final @QueryParameter String ami,@QueryParameter String projectUserID,
				@QueryParameter String projectID) throws IOException,
				ServletException {

			// Jec2 jec2 = EC2Cloud.connect(accessId,
			// secretKey,region.ec2Endpoint);
			// HttpSession session =
			// Stapler.getCurrentRequest().getSession(false);
			String decryptedSecretKey = Secret.decrypt(secretKey)
					.getPlainText();

			// ImageAction ia = new ImageAction();

			/* if (ia != null) { */

			HttpSession sess = Stapler.getCurrentRequest().getSession(false);
			// Map<String,String> map =
			// (Map<String,String>)sess.getAttribute("cloudMap");
			// String accountId = "";
			String vmOwnerId = "";
			String providerCode = "";
			if (!(StringUtils.isEmpty(String.valueOf(sess
					.getAttribute(EC2Constant.AWS_CLOUD_PROVIDER))) || StringUtils
					.isEmpty(String.valueOf(sess
							.getAttribute(EC2Constant.EUCA_CLOUD_PROVIDER))))) {

				if (!StringUtils.isEmpty(String.valueOf(sess
						.getAttribute(EC2Constant.AWS_CLOUD_PROVIDER)))) {
					if ("Y".equals(String.valueOf(sess
							.getAttribute(EC2Constant.AWS_CLOUD_PROVIDER)))) {
						providerCode = EC2Constant.EC2Cloud;
					}
				}
				if (!StringUtils.isEmpty(String.valueOf(sess
						.getAttribute(EC2Constant.EUCA_CLOUD_PROVIDER)))) {
					if ("Y".equals(String.valueOf(sess
							.getAttribute(EC2Constant.EUCA_CLOUD_PROVIDER)))) {
						providerCode = EC2Constant.EUCACloud;
					}
				}
				if (!StringUtils.isEmpty(String.valueOf(sess
						.getAttribute(EC2Constant.VMWARE_CLOUD_PROVIDER)))) {
					System.out.println("Validating image on Vmware cloud ::");
					if ("Y".equals(String.valueOf(sess
							.getAttribute(EC2Constant.VMWARE_CLOUD_PROVIDER)))) {
						providerCode = EC2Constant.VMWareCloud;
					}
				}
			} else {
				return FormValidation
						.error("Test successful connectivity to the parent cloud provider first");
			}

			// String accountNumber = EC2Cloud.get().getAccountId();
			String accountNumber = accountId;
			String userId = EC2Cloud.get().getVmOwnerId();

			// boolean isAMIValid = ia.describeImage(accountNumber, ami,userId,
			// decryptedSecretKey, accessId, providerCode);
			boolean isAMIValid = PlatformInfraUtil.getMachineImage(
					accountNumber, ami, userId, decryptedSecretKey, accessId,
					providerCode,projectUserID,projectID);
			if (!isAMIValid)
				// de-registered AMI causes an empty list to be
				// returned. so be defensive
				// against other possibilities
				return FormValidation
						.error("Have you tested connectivity to the parent cloud provider first, as no such AMI exists or is usable with this accessId: "
								+ ami);
			return FormValidation.ok(" Image available ");
			/*
			 * } else return FormValidation.ok(); // can't test }
			 */
		}
	}
}