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
import hudson.plugins.ec2.resource.ResourceOptions;
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
	
	public final String availableZone;
	public final String subnetID;
	public final String remoteFS;
	public final String sshPort;
	public final JPaaSInstanceType type;
	public final String labels;
	public final boolean startStopSlave;
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
	public SlaveTemplate(String ami, String availableZone,String subnetID,String remoteFS, String sshPort,
			JPaaSInstanceType type, String labelString,boolean startStopSlave, String description,
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
		this.availableZone = availableZone;
		this.subnetID = subnetID;
		this.remoteFS = remoteFS;
		this.sshPort = sshPort;
		this.type = type;
		this.labels = Util.fixNull(labelString);
		this.startStopSlave = startStopSlave;
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
		
	public String getAvailableZone() {
		return availableZone;
	}

	public String getSubnetID() {
		return subnetID;
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
			return newSlave(false, "", "", "", "", "", "", null,"", "","","","","");
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
	 * 	 * 
	 * Description of field "isExistingInstance" ==> This field is responsible
	 * whether the instance is going to be persisted after the operation or not.
	 * 
	 * @return always non-null. This needs to be then added to
	 *         {@link Hudson#addNode(Node)}.
	 */
	public EC2Slave provision(TaskListener listener, ResourceOptions resourceOptions) throws /* EC2Exception, */IOException {
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
			logger.println("Account Id " + resourceOptions.getAccountNumber());
			logger.println("Image Id " + resourceOptions.getImageId());
			logger.println("Kernel Id " + resourceOptions.getKernelID());
			logger.println("Key " + resourceOptions.getKey());
			logger.println("Group " + resourceOptions.getSecurityGroup());
			logger.println("User Id " + resourceOptions.getVmLoginUserID());
			logger.println("Image Type " + resourceOptions.getImageType());
			logger.println("maxInstance " + resourceOptions.getMaxInstance());
			logger.println("minInstance" + resourceOptions.getMinInstance());
			logger.println("secretKey" + resourceOptions.getPrivateCredential());
			logger.println("accessId" + resourceOptions.getPublicCredential());			
			logger.println("projectUserID" + resourceOptions.getProjectUserId());
			logger.println("projectID" + resourceOptions.getProjectID());
						
			String VMId = PlatformInfraUtil.launchVirtualMachine(resourceOptions);
			
			return newSlave(resourceOptions.isExistingInstance(),
					computeNumberOfExecutorsForSlave(), resourceOptions.getAccountNumber(), resourceOptions.getVmLoginUserID(),
					VMId, resourceOptions.getPrivateCredential(), resourceOptions.getPublicCredential(), resourceOptions.getCloudType(), resourceOptions.getPrivateKey(),
					resourceOptions.getSlaveLabel(),resourceOptions.isStartStopMachine(),resourceOptions.getEndPoint(),
					resourceOptions.getDataCenterId(),resourceOptions.getSubnetID(),resourceOptions.getProjectUserId(),
					resourceOptions.getProjectID());
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
			ResourceOptions resourceOptions) throws /* EC2Exception, */IOException {
		PrintStream logger = listener.getLogger();
	
		try {
			logger.println("Launching as triggered by UI" + ami);			
			logger.println("Account Id " + resourceOptions.getAccountNumber());
			logger.println("Image Id " + resourceOptions.getImageId());
			logger.println("Kernel Id " + resourceOptions.getKernelID());
			logger.println("Key " + resourceOptions.getKey());
			logger.println("Group " + resourceOptions.getSecurityGroup());
			logger.println("User Id " + resourceOptions.getVmLoginUserID());
			logger.println("Image Type " + resourceOptions.getImageType());
			logger.println("maxInstance " + resourceOptions.getMaxInstance());
			logger.println("minInstance" + resourceOptions.getMinInstance());
			logger.println("secretKey" + resourceOptions.getPrivateCredential());
			logger.println("accessId" + resourceOptions.getPublicCredential());
			logger.println("cloudType" + resourceOptions.getClass());
			logger.println("projectUserID" +resourceOptions.getProjectUserId());
			logger.println("projectID" + resourceOptions.getProjectID());
			
			String VMId = PlatformInfraUtil.launchVirtualMachine(resourceOptions);

			logger.println("VMId for after launch as " + VMId);
			
			return newSlave(false, computeNumberOfExecutorsForSlave(),
					resourceOptions.getAccountNumber(), resourceOptions.getVmLoginUserID(), VMId, resourceOptions.getPrivateCredential(),  resourceOptions.getPublicCredential(),
					resourceOptions.getCloudType(), resourceOptions.getPrivateKey(), resourceOptions.getSlaveLabel(),false,
					resourceOptions.getEndPoint(),resourceOptions.getDataCenterId(),resourceOptions.getSubnetID(),resourceOptions.getProjectUserId(),resourceOptions.getProjectID());
		}catch(PlatformException pException){			
			throw new RuntimeException(pException.getTraceableErrorInfo().getErrorDescription());			
		} 
		catch (FormException e) {
			throw new AssertionError(); // we should have discovered all
										// configuration issues upfront
		} catch (Exception e) {			
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
			ResourceOptions resourceOptions)
			throws /* EC2Exception, */IOException {
		PrintStream logger = listener.getLogger();

		try {
			logger.println("Finding existing instance with following instance id "
					+ ami);
			logger.println("existingInstanceId " + resourceOptions.isExistingInstance());
			logger.println("Account Id " + resourceOptions.getAccountNumber());
			logger.println("Image Id " + resourceOptions.getImageId());
			logger.println("Kernel Id " + resourceOptions.getKernelID());
			logger.println("Key " + resourceOptions.getKey());
			logger.println("Group " + resourceOptions.getSecurityGroup());
			logger.println("User Id " + resourceOptions.getVmLoginUserID());
			logger.println("Image Type " + resourceOptions.getImageType());
			logger.println("maxInstance " + resourceOptions.getMaxInstance());
			logger.println("minInstance" + resourceOptions.getMinInstance());
			logger.println("secretKey" + resourceOptions.getPrivateCredential());
			logger.println("accessId" + resourceOptions.getPublicCredential());
			logger.println("cloudType" + resourceOptions.getCloudType());
			if (exists(listener,resourceOptions.getSlaveLabel(),resourceOptions)) {
				return newSlave(true, computeNumberOfExecutorsForSlave(),
						resourceOptions.getAccountNumber(), resourceOptions.getVmLoginUserID(), resourceOptions.getSlaveLabel(), resourceOptions.getPrivateCredential(),
						resourceOptions.getPublicCredential(),  resourceOptions.getCloudType(), 
						resourceOptions.getPrivateKey(),  resourceOptions.getSlaveLabel(),resourceOptions.isStartStopMachine(),
						resourceOptions.getEndPoint(),resourceOptions.getDataCenterId(),resourceOptions.getSubnetID(),resourceOptions.getProjectUserId(),resourceOptions.getProjectID());
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
	 * @param secretKey
	 * @param accessId
	 * @param cloudType
	 * @param privateKey
	 * @return
	 * @throws IOException
	 */

	public EC2Slave findOrSpawnInstance(TaskListener listener,
			ResourceOptions resourceOptions)
			throws /* EC2Exception, */IOException {
		PrintStream logger = listener.getLogger();

		try {
			logger.println("Finding existing instance with following instance id "
					+ ami);
			logger.println("labelName " + resourceOptions.getSlaveLabel());
			logger.println("Account Id " + resourceOptions.getAccountNumber());
			logger.println("Image Id " + resourceOptions.getImageId());
			logger.println("Kernel Id " +resourceOptions.getKernelID());
			logger.println("Key " + resourceOptions.getKey());
			logger.println("Group " + resourceOptions.getSecurityGroup());
			logger.println("User Id " + resourceOptions.getVmLoginUserID());
			logger.println("Image Type " + resourceOptions.getImageType());
			logger.println("maxInstance " + resourceOptions.getMaxInstance());
			logger.println("minInstance" + resourceOptions.getMinInstance());
			logger.println("secretKey" + resourceOptions.getPrivateCredential());
			logger.println("accessId" +resourceOptions.getPublicCredential());
			logger.println("cloudType" + resourceOptions.getCloudType());

			// Discovering running instances with the capability
			// String label = "";
			Label labelInstance = Hudson.getInstance().getLabel(resourceOptions.getSlaveLabel());
			Set<Node> nodeList = labelInstance.getNodes();
			int count = labelInstance.getIdleExecutors();

			if (count > 0) {
				for (Node node : nodeList) {
					if (node.toComputer().isIdle()) {
						return (EC2Slave) node;
					}
				}
			}		
						
			return provision(listener, resourceOptions);			

		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Verifies if the specified account credentials is present or not
	 * 
	 */
	private boolean exists(TaskListener listener, String existingInstanceId,ResourceOptions resourceOptions) {
		// Is this instance already registered for the given credential
		PrintStream logger = listener.getLogger();
		existingInstanceId = removeThirdPartyConstant(existingInstanceId,
				logger);

		if(resourceOptions.isStartStopMachine()){
			//The machine is in stopped state, we should start the machine
			try{
				PlatformInfraUtil.startVirtualMachine(existingInstanceId, resourceOptions);
				//Waiting for 1 mins.. because amazon machine takes sometime to startup
				Thread.sleep(60000);
			}catch(Exception e){
				return false;
			}
			
		}
		
		boolean flag = PlatformInfraUtil.isRunningVirtualMachine(existingInstanceId,
				resourceOptions);

		logger.println("Is this an existing instance " + flag);
		return flag;
	}

	/**
	 * Removes third party constant from the slave label,
	 * A sample SLAVE label will be
	 * EXT#instance-id_PROJECTID
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
			// the label may contain projectid, remove that as well
			if(existingInstanceId.contains(EC2Constant.PROJECT_ID_SEPARATOR)){
				existingInstanceId = existingInstanceId.substring(0,existingInstanceId.indexOf(EC2Constant.PROJECT_ID_SEPARATOR));
			}
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
			String cloudType, EC2PrivateKey privateKey, String displayLabel,String endPoint,String availableZone,String subnetId,String projectUserID,
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
				displayLabel,false, initScript, remoteAdmin, rootCommandPrefix,
				jvmopts, privateKey,endPoint,availableZone,subnetId,projectUserID,projectID);
	}

	private EC2Slave newSlave(boolean isExistingInstance, int numberOfExecutor,
			String accountNumber, String userId, String VMId, String secretKey,
			String accessId, String cloudType, EC2PrivateKey privateKey,
			String displayLabel,boolean stopWhenBuildCompleted,String endPoint,String availableZone,String subnetId,String projectUserID,String projectID) throws FormException, IOException {

		return new EC2Slave(remoteAdminPassword, isExistingInstance,
				accountNumber, userId, secretKey, accessId, cloudType, VMId,
				description, remoteFS, getSshPort(), numberOfExecutor,
				displayLabel,stopWhenBuildCompleted, initScript, remoteAdmin, rootCommandPrefix,
				jvmopts, privateKey,endPoint,availableZone,subnetId,projectUserID,projectID);
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
			return newSlave(false, "", "", "", "", "", "", null, "","","","","","");
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
				@QueryParameter String region,
				final @QueryParameter String ami,@QueryParameter String projectUserID,
				@QueryParameter String projectID) throws IOException,
				ServletException {

			// Jec2 jec2 = EC2Cloud.connect(accessId,
			// secretKey,region.ec2Endpoint);
			// HttpSession session =
			// Stapler.getCurrentRequest().getSession(false);
			String decryptedSecretKey = Secret.decrypt(secretKey)
					.getPlainText();
			
			String endPoint = PlatformInfraUtil.getEc2EndpointUrl(region).toString();						
			String providerCode = "";			
			providerCode = EC2Constant.EC2Cloud;			
			String accountNumber = accountId;
			String userId = EC2Cloud.get().getVmOwnerId();
			
			ResourceOptions resourceOptions = new ResourceOptions();
			resourceOptions.setAccountNumber(accountNumber);
			resourceOptions.setPrivateCredential(decryptedSecretKey);
			resourceOptions.setPublicCredential(accessId);
			resourceOptions.setProviderID(providerCode);
			resourceOptions.setEndPoint(endPoint);
			resourceOptions.setProjectUserId(projectUserID);
			resourceOptions.setProjectID(projectID);
			
			resourceOptions.setImageId(ami);
			resourceOptions.setVmLoginUserID(userId);
						
			
			boolean isAMIValid = PlatformInfraUtil.getMachineImage(
					ami,resourceOptions);
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