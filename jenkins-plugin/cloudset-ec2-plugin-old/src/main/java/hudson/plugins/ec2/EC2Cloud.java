package hudson.plugins.ec2;

import hudson.model.Computer;
import hudson.model.Descriptor;
import hudson.model.Hudson;
import hudson.model.Label;
import hudson.model.Node;
import hudson.plugins.ec2.constant.ClientConstants;
import hudson.plugins.ec2.util.PlatformInfraUtil;
import hudson.slaves.Cloud;
import hudson.slaves.NodeProvisioner.PlannedNode;
import hudson.util.FormValidation;
import hudson.util.Secret;
import hudson.util.StreamTaskListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.dasein.cloud.CloudException;
import org.jets3t.service.Constants;
import org.jets3t.service.Jets3tProperties;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.security.AWSCredentials;
import org.jets3t.service.utils.ServiceUtils;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

//import com.cts.cloudcoe.cloudplugin.action.InstanceAction;
//import com.cts.cloudcoe.cloudplugin.action.IpAddressAction;

/**
 * Hudson's view of EC2.
 * 
 * @author Kohsuke Kawaguchi
 */
public abstract class EC2Cloud extends Cloud {

	private final String accessId;
	private final Secret secretKey;
	private final EC2PrivateKey privateKey;
	
	/**
	 * Upper bound on how many instances we may provision.
	 */
	public final int instanceCap;
	private final List<SlaveTemplate> templates;

	/* private transient KeyPairInfo usableKeyPair; */

	/* private transient Jec2 connection; */

	protected EC2Cloud(String id, String accessId, String secretKey,
			String privateKey, String instanceCapStr,
			List<SlaveTemplate> templates) {
		super(id);
		this.accessId = accessId.trim();
		this.secretKey = Secret.fromString(secretKey.trim());
		this.privateKey = new EC2PrivateKey(privateKey);
		if (instanceCapStr.equals(""))
			this.instanceCap = Integer.MAX_VALUE;
		else
			this.instanceCap = Integer.parseInt(instanceCapStr);
		if (templates == null)
			templates = Collections.emptyList();
		this.templates = templates;
		readResolve(); // set parents
	}

	protected EC2Cloud(String id, String accessId, String secretKey,
			String privateKey, String instanceCapStr) {
		super(id);
		this.accessId = accessId.trim();
		this.secretKey = Secret.fromString(secretKey.trim());
		this.privateKey = new EC2PrivateKey(privateKey);
		if (instanceCapStr.equals(""))
			this.instanceCap = Integer.MAX_VALUE;
		else
			this.instanceCap = Integer.parseInt(instanceCapStr);
		templates = Collections.emptyList();
	}

	public abstract URL getEc2EndpointUrl() throws IOException;

	public abstract URL getS3EndpointUrl() throws IOException;

	/**
	 * CTSJPAASBAAS-8 These abstract methods, implemented by subclasses help
	 * this to discover these information from cloud specific pages
	 */
	public abstract String getSecurityGroup();

	public abstract String getSecurityKeyFileName();

	public abstract String getVmOwnerId();

	public abstract String getAccountId();
	
	public abstract AwsRegion getRegion();

	protected Object readResolve() {
		for (SlaveTemplate t : templates)
			t.parent = this;
		return this;
	}

	public String getAccessId() {
		return accessId;
	}

	public String getSecretKey() {
		return secretKey.getEncryptedValue();
	}

	public EC2PrivateKey getPrivateKey() {
		return privateKey;
	}

	public Secret getSecret() {
		return privateKey.exposeSecret();
	}

	public String getInstanceCapStr() {
		if (instanceCap == Integer.MAX_VALUE)
			return "";
		else
			return String.valueOf(instanceCap);
	}

	/**
	 * Filter and return templates which are not JIT or existing instance type
	 * for Amazon and Eucalyptus only
	 * 
	 * @return List of templates that can be used by Jenkins admin to spawn a VM
	 *         manually from UI
	 */
	public List<SlaveTemplate> getTemplates() {
		return Collections.unmodifiableList(templates);
	}

	/**
	 * Extract the slave template list
	 * 
	 * @return list of slave template
	 */
	private List<SlaveTemplate> extractSlaveTemplate() {
		List<SlaveTemplate> newTemplates = new ArrayList<SlaveTemplate>();

		for (SlaveTemplate slaveTemplate : templates) {
			String labelString = slaveTemplate.getLabelString();
			if (!(labelString.contains(EC2Constant.THIRDPARTY) || labelString
					.contains(EC2Constant.JIT))) {
				newTemplates.add(slaveTemplate);
			}
		}
		return Collections.unmodifiableList(newTemplates);
	}

	/**
	 * Filter and return templates which are not JIT or existing instance type
	 * for Amazon and Eucalyptus only
	 * 
	 * @return List of templates that can be used by Jenkins admin to spawn a VM
	 *         manually from UI
	 */
	public List<SlaveTemplate> getTemplatesForProvisionFromUI() {

		return extractSlaveTemplate();
	}


	/**
	 * Return a Template corresponding to an AMI when the AMI label does not
	 * contain JIT# and EXT# tags
	 * 
	 * @param ami
	 * @return
	 */
	public Object getTemplate(String ami) {
		for (SlaveTemplate t : templates)
			if (t.ami.equals(ami)
					&& (!t.getLabelString().contains(EC2Constant.THIRDPARTY))
					&& (!t.getLabelString().contains(EC2Constant.JIT))){
				// if(t.ami.equals(ami))
				return t;
			}
		
		return null;
	}

	/**
	 * Return a Template corresponding to a label
	 * 
	 * @param ami
	 * @return
	 */
	public Object getTemplate(Label label) {
		for (SlaveTemplate t : templates)
			if (label == null || label.matches(t.getLabelSet())){
				return t;
			}
		
		return null;
	}

	/**
	 * Return a Template corresponding to a label specific to a cloud We are
	 * adding overloaded version of this method to make sure that we send the
	 * label specific to a cloud
	 * 
	 * @param ami
	 * @return
	 */
	public Object getTemplate(Label label, Cloud c) {
		if (c instanceof AmazonEC2Cloud) {
			for (SlaveTemplate t : templates)
				if (label == null || label.matches(t.getLabelSet()))
					return t;
		} 
		return null;
	}

	/* *//**
	 * Gets the {@link KeyPairInfo} used for the launch.
	 */
	/*
	 * public synchronized KeyPairInfo getKeyPair() throws EC2Exception,
	 * IOException { if(usableKeyPair==null) usableKeyPair =
	 * privateKey.find(connect()); return usableKeyPair; }
	 *//**
	 * Counts the number of instances in cloud which are currently running
	 * <p>
	 * This includes those instances that may be started outside Hudson.
	 */
	public int countCurrentEC2Slaves(String accountNumber, String userId,
			String providerId, String ec2endpoint,String projectUsrID, String projectId)
			throws CloudException {
		// InstanceAction ia = new InstanceAction();
		return PlatformInfraUtil.getVirtualMachineListCount(accountNumber,
				userId, secretKey.getPlainText(), accessId, providerId,ec2endpoint,
				projectUsrID, projectId);
		// return ia.runningInstancesCount(accountNumber, userId,
		// secretKey.getPlainText(), accessId, providerId);
	}

	/**
	 * Debug command to attach to a running instance.
	 */
	public void doAttach(StaplerRequest req, StaplerResponse rsp,
			@QueryParameter String id) throws ServletException, IOException {
		checkPermission(PROVISION);
		SlaveTemplate t = getTemplates().get(0);

		StringWriter sw = new StringWriter();
		StreamTaskListener listener = new StreamTaskListener(sw);
		EC2Slave node = t.attach(id, listener);
		Hudson.getInstance().addNode(node);

		rsp.sendRedirect2(req.getContextPath() + "/computer/"
				+ node.getNodeName());
	}

	/**
	 * Provision a new VM for an ami
	 * 
	 * @param req
	 * @param rsp
	 * @param ami
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doProvision(StaplerRequest req, StaplerResponse rsp,
			@QueryParameter String ami) throws ServletException, IOException {

		LOGGER.info("Do provision method started with image id :: " + ami);
		String projectUserID = "";
		String projectID = "";

		if (req != null
				&& req.getSession() != null
				&& req.getSession().getAttribute(ClientConstants.PROJECT_ID) != null
				&& req.getSession().getAttribute(ClientConstants.USER_ID) != null) {
			try {				
				
				projectUserID = (String) req.getSession().getAttribute(
						ClientConstants.PROJECT_ID);
				projectID = (String) req.getSession().getAttribute(
						ClientConstants.USER_ID);
				
				if(projectUserID.isEmpty() || projectID.isEmpty()){
					throw new Exception("Exception occured while fetching data from session.Empty" +
							"userID or ProjectId found");
				}
				
			} catch (Exception e) {
				LOGGER.severe("EXCEPTION OCCURED WHILE FETCHING SESSION DATA "
						+ e.getMessage());
			}
		}

		checkPermission(PROVISION);
		if (ami == null) {
			LOGGER.info("ami is null");
			sendError("The 'ami' query parameter is missing", req, rsp);
			return;
		}

		Object t = getTemplate(ami);

		// Below logic is responsible for checking if free IPs is available or
		// not.
		String scretkeyInText = "";
		if (secretKey == null) {
			scretkeyInText = null;
		} else {
			scretkeyInText = secretKey.getPlainText();
		}

		// TODO : IP address checking has to be modified.
		/*
		 * if(t instanceof SlaveTemplate){ SlaveTemplate slaveTemplate =
		 * (SlaveTemplate)t; if(!hasAvailableIP(getAccountId(), getVmOwnerId(),
		 * scretkeyInText, accessId, getCloudType(slaveTemplate.parent.name))) {
		 * LOGGER.info("No more IP available");
		 * sendError("No more IP available for provisioning slave",req,rsp);
		 * return; } }
		 */

		if (t == null) {
			LOGGER.info("Slave template is null");
			sendError("No such AMI: " + ami, req, rsp);
			return;
		}
		StringWriter sw = new StringWriter();
		StreamTaskListener listener = new StreamTaskListener(sw);
		
		String endPoint = "";
		if(getRegion()!= null){
			AwsRegion region = getRegion();
			endPoint = region.ec2Endpoint.toString();
		}
		
		try {
			// String label = t.labels;
			LOGGER.info("Account :: " + getAccountId() + " :: keyForUser : "
					+ getSecurityKeyFileName() + " :: groupForUser "
					+ getSecurityGroup() + " :: user " + getVmOwnerId() + " endpoint as "+endPoint);
			
			
			EC2Slave node = getSlaveFromUI(listener, t, getAccountId(),
					getSecurityKeyFileName(), getSecurityGroup(),
					getVmOwnerId(),endPoint, projectUserID, projectID);
			Hudson.getInstance().addNode(node);
			LOGGER.info("Node created successfully... Do provision method end");
			rsp.sendRedirect2(req.getContextPath() + "/computer/"
					+ node.getNodeName());
		} catch (IOException e) {
			e.printStackTrace(listener.error(e.getMessage()));
			sendError(sw.toString(), req, rsp);
		} catch (Exception e) {
			// e.printStackTrace(listener.error(e.getMessage()));
			sendError(e.getMessage(), req, rsp);
			return;
		}

	}

	/**
	 * Check whether IP is available or not
	 * 
	 * @param accountNumber
	 * @param userId
	 * @param privateCredential
	 * @param publicCredential
	 * @param providerId
	 * @return boolean
	 */
	/*
	 * private boolean hasAvailableIP(String accountNumber, String userId,
	 * String privateCredential, String publicCredential, String providerId) {
	 * LOGGER.info( "Checking if IPs are availble" ); //LOGGER.info(
	 * "Private Credential :: "+privateCredential); IpAddressAction
	 * ipAddressAction = new IpAddressAction(); return
	 * ipAddressAction.isIpAddressExist(accountNumber, userId,
	 * privateCredential, publicCredential, providerId); }
	 */

	/**
	 * Get the cloud type based on cloud name
	 * 
	 * @param cloudName
	 * @return e.g 01, 02 ,..
	 */
	private String getCloudType(String cloudName) {

		switch (CloudName.valueOf(cloudName)) {
		
		case amazon:
			return EC2Constant.EC2Cloud;		
		default:
			throw new AssertionError();

		}

	}

	/**
	 * Get a new slave based on template and account
	 * 
	 * @param listener
	 * @param label
	 * @param t
	 * @param account
	 * @param keyForUser
	 * @param groupForUser
	 * @param userIdForUser
	 * @return
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private EC2Slave getSlaveFromUI(StreamTaskListener listener, Object obj,
			String account, String keyForUser, String groupForUser,
			String userIdForUser,String endPoint, String projectUserID, String projectID)
			throws IOException, SecurityException, NoSuchMethodException {

		LOGGER.info("Going to launch a new VM as triggered by UI");
		EC2Slave s = null;

		if (obj instanceof SlaveTemplate) {

			SlaveTemplate t = (SlaveTemplate) obj;
			// Get project specific information
			String accountNumber = getAccountId();
			String userId = getVmOwnerId();
			String imageId = t.ami;
			String kernelId = null;
			String imageType = t.type.getTypeId();
			int minInstance = 1;
			int maxInstance = 1;
			String key = getSecurityKeyFileName();
			String group = getSecurityGroup();
			String cloudType = getCloudType(t.parent.name);
			
			
			// String cloudName = t.parent.name;
			// LOGGER.info(" Cloud type is " + cloudName);
			s = t.provisionFromUI(listener, accountNumber, userId, imageId,
					kernelId, imageType, minInstance, maxInstance, key, group,
					secretKey.getPlainText(), accessId, cloudType, privateKey,
					t.labels,endPoint, projectUserID, projectID);

		}
		return s;

	}

	/**
	 * Spawning a new VM based on label and workload
	 * 
	 * @param Label
	 * @param excessWorkload
	 *            in int
	 * 
	 */

	public Collection<PlannedNode> provision(Label label, int excessWorkload) {
		try {
			String projectUserID = "";
			String projectID = "";		

			// Get the build that is pending in the queue for the given label
			Object obj = getTemplate(label);
			List<PlannedNode> r = new ArrayList<PlannedNode>();

			if (obj instanceof SlaveTemplate) {
				final SlaveTemplate t = (SlaveTemplate) obj;
				getVMPolicy(label, excessWorkload, r, t, projectUserID,
						projectID);

			} 

			return r;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING,
					"Failed to count the # of live instances on EC2", e);
			return Collections.emptyList();
		}
	}

	
	/**
	 * Based on VM policy get a slave against the provided label in Amazon or
	 * Eucalyptus
	 * 
	 * Policy as below :: 1. Third Party - It is a pre-existing vm. Therefore
	 * find it and register. 2. Pooled VM :: If a slave present against the
	 * label don't spawn else spawn a vm and register against the label 3. Just
	 * In Time :: Spawn a new VM , build the job and terminate it
	 * 
	 * @param label
	 * @param excessWorkload
	 * @param r
	 * @param t
	 * @throws CloudException
	 */
	private void getVMPolicy(final Label label, int excessWorkload,
			List<PlannedNode> r, final SlaveTemplate t,
			final String projectUserID, final String projectID)
			throws CloudException {
		// LOGGER.info(" Account "+getAccountId()+" VM Owner "+getVmOwnerId()+" Sec Key "+getSecretKey()+" Sec Grp "+getSecurityGroup());

		// Get project specific information
		final String accountNumber = getAccountId();
		final String userId = getVmOwnerId();
		final String imageId = t.ami;
		final String kernelId = null;
		final String imageType = t.type.getTypeId();
		final int minInstance = 1;
		final int maxInstance = 1;
		final String key = getSecurityKeyFileName();
		final String group = getSecurityGroup();
		final String cloudType = getCloudType(t.parent.name);
		final String endPoint = getRegion().ec2Endpoint.toString();

		for (; excessWorkload > 0; excessWorkload--) {
			if (countCurrentEC2Slaves(accountNumber, userId, cloudType,endPoint,
					projectUserID, projectID) >= instanceCap) {
				LOGGER.log(Level.INFO,
						"Instance cap reached, not provisioning.");
				break; // maxed out
			}

			r.add(new PlannedNode(t.getDisplayName(),
					Computer.threadPoolForRemoting.submit(new Callable<Node>() {
						public Node call() throws Exception {
							// TODO: record the output somewhere
							// If the label starts with EXT# assume this is an
							// pre - existing instance and therefore try finding
							// it ... instead of spawning a new one (Type- Third
							// Party)
							//
							// If the label starts with POOLED assume that it
							// exists if not spawn a new one and keep it in pool
							//
							// If the label starts with JIT assume a new VM will
							// be spawned and will be killed after the operation

							/**
							 * ############## VM Policy ######################
							 * 
							 * 
							 * JIT-->Spawned-->Registered-->Deregistered-->
							 * Terminated Pooled-->Spawned if not found already
							 * being registered-->Registered if newly spawned-->
							 * Deregistered conditionally*-->Terminated
							 * conditionally* Instance Id Found or else
							 * exception is
							 * thrown-->Registered-->Deregistered-->Not
							 * Terminated
							 **/

							EC2Slave s = null;
							LOGGER.info(" Label is " + label.getDisplayName());
							if (StringUtils.startsWithIgnoreCase(
									label.getDisplayName(),
									EC2Constant.THIRDPARTY)) {

								// String[] labelStrings =
								// label.getDisplayName().split("#");
								s = t.findToRegisterExistingInstance(
										new StreamTaskListener(System.out),
										label.getDisplayName(), accountNumber,
										userId, imageId, kernelId, imageType,
										minInstance, maxInstance, key, group,
										secretKey.getPlainText(), accessId,
										cloudType,privateKey,endPoint, projectUserID,
										projectID);

							} else if (StringUtils.startsWithIgnoreCase(
									label.getDisplayName(), EC2Constant.JIT)) {
								s = t.provision(new StreamTaskListener(
										System.out), accountNumber, userId,
										imageId, kernelId, imageType,
										minInstance, maxInstance, key, group,
										secretKey.getPlainText(), accessId,
										cloudType, privateKey, false, label
												.getDisplayName(),endPoint,
										projectUserID, projectID);
							} else /*
									 * if(StringUtils.startsWithIgnoreCase(label.
									 * getDisplayName(),"POOLED"))
									 */{
								// String[] labelStrings =
								// label.getDisplayName().split("#");

								s = t.findOrSpawnInstance(
										new StreamTaskListener(System.out),
										label.getDisplayName(), accountNumber,
										userId, imageId, kernelId, imageType,
										minInstance, maxInstance, key, group,
										secretKey.getPlainText(), accessId,
										cloudType, privateKey,endPoint, projectUserID,
										projectID);

							}
							Hudson.getInstance().addNode(s);
							// EC2 instances may have a long init script. If we
							// declare
							// the provisioning complete by returning without
							// the connect
							// operation, NodeProvisioner may decide that it
							// still wants
							// one more instance, because it sees that (1) all
							// the slaves
							// are offline (because it's still being launched)
							// and
							// (2) there's no capacity provisioned yet.
							//
							// deferring the completion of provisioning until
							// the launch
							// goes successful prevents this problem.
							s.toComputer().connect(false).get();
							return s;
						}
					}), t.getNumExecutors()));
		}
	}
	
	/**
	 * Checks whether able to spawn a new slave against the label
	 */
	public boolean canProvision(Label label) {
		return getTemplate(label) != null;
	}

	/**
	 * Checks whether able to spawn a new slave against the label, Creating an
	 * overloaded version of this method to send the cloud details, We need to
	 * get the template specific to the cloud
	 */
	public boolean canProvision(Label label, Cloud c) {
		return getTemplate(label, c) != null;
	}

	/**
	 * Gets the first {@link EC2Cloud} instance configured in the current
	 * Hudson, or null if no such thing exists.
	 */
	public static EC2Cloud get() {
		return Hudson.getInstance().clouds.get(EC2Cloud.class);
	}

	/***
	 * Convert a configured hostname like 'us-east-1' to a FQDN or ip address
	 */
	public static String convertHostName(String ec2HostName) {
		if (ec2HostName == null || ec2HostName.length() == 0)
			ec2HostName = "us-east-1";
		if (!ec2HostName.contains("."))
			ec2HostName = ec2HostName + ".ec2.amazonaws.com";
		return ec2HostName;
	}

	/***
	 * Convert a configured s3 endpoint to a FQDN or ip address
	 */
	public static String convertS3HostName(String s3HostName) {
		if (s3HostName == null || s3HostName.length() == 0)
			s3HostName = "s3";
		if (!s3HostName.contains("."))
			s3HostName = s3HostName + ".amazonaws.com";
		return s3HostName;
	}

	/***
	 * Convert a user entered string into a port number "" -> -1 to indicate
	 * default based on SSL setting
	 */
	public static Integer convertPort(String ec2Port) {
		if (ec2Port == null || ec2Port.length() == 0)
			return -1;
		else
			return Integer.parseInt(ec2Port);
	}

	/**
	 * Connects to S3 and returns {@link S3Service}.
	 */
	public S3Service connectS3() throws S3ServiceException, IOException {
		URL s3 = getS3EndpointUrl();

		return new RestS3Service(new AWSCredentials(accessId,
				secretKey.toString()), null, null, buildJets3tProperties(s3));
	}

	/**
	 * Builds the connection parameters for S3.
	 */
	protected Jets3tProperties buildJets3tProperties(URL s3) {
		Jets3tProperties props = Jets3tProperties
				.getInstance(Constants.JETS3T_PROPERTIES_FILENAME);
		final String s3Host = s3.getHost();
		if (!s3Host.equals("s3.amazonaws.com"))
			props.setProperty("s3service.s3-endpoint", s3Host);
		int s3Port = portFromURL(s3);
		if (s3Port != -1)
			props.setProperty("s3service.s3-endpoint-http-port",
					String.valueOf(s3Port));
		if (s3.getPath().length() > 1)
			props.setProperty("s3service.s3-endpoint-virtual-path",
					s3.getPath());
		props.setProperty("s3service.https-only", String.valueOf(isSSL(s3)));
		return props;
	}

	/**
	 * Computes the presigned URL for the given S3 resource.
	 * 
	 * @param path
	 *            String like "/bucketName/folder/folder/abc.txt" that
	 *            represents the resource to request.
	 */
	public URL buildPresignedURL(String path) throws IOException,
			S3ServiceException {
		long expires = System.currentTimeMillis() / 1000 + 60 * 60;
		String token = "GET\n\n\n" + expires + "\n" + path;

		String url = "http://s3.amazonaws.com"
				+ path
				+ "?AWSAccessKeyId="
				+ accessId
				+ "&Expires="
				+ expires
				+ "&Signature="
				+ URLEncoder.encode(ServiceUtils.signWithHmacSha1(
						secretKey.toString(), token), "UTF-8");
		return new URL(url);
	}

	/* Parse a url or return a sensible error */
	public static URL checkEndPoint(String url) throws FormValidation {
		try {
			return new URL(url);
		} catch (MalformedURLException ex) {
			throw FormValidation.error("Endpoint URL is not a valid URL");
		}
	}

	public static abstract class DescriptorImpl extends Descriptor<Cloud> {
		public JPaaSInstanceType[] getInstanceTypes() {
			return JPaaSInstanceType.values();
		}

		/**
		 * TODO: once 1.304 is released, revert to FormValidation.validateBase64
		 */
		private FormValidation validateBase64(String value,
				boolean allowWhitespace, boolean allowEmpty, String errorMessage) {
			try {
				String v = value;
				if (!allowWhitespace) {
					if (v.indexOf(' ') >= 0 || v.indexOf('\n') >= 0)
						return FormValidation.error(errorMessage);
				}
				v = v.trim();
				if (!allowEmpty && v.length() == 0)
					return FormValidation.error(errorMessage);

				com.trilead.ssh2.crypto.Base64.decode(v.toCharArray());
				return FormValidation.ok();
			} catch (IOException e) {
				return FormValidation.error(errorMessage);
			}
		}

		public FormValidation doCheckAccessId(@QueryParameter String value)
				throws IOException, ServletException {
			return validateBase64(value, false, false,
					Messages.EC2Cloud_InvalidAccessId());
		}

		public FormValidation doCheckSecretKey(@QueryParameter String value)
				throws IOException, ServletException {
			return validateBase64(value, false, false,
					Messages.EC2Cloud_InvalidSecretKey());
		}

		public FormValidation doCheckPrivateKey(@QueryParameter String value)
				throws IOException, ServletException {
			boolean hasStart = false, hasEnd = false;
			BufferedReader br = new BufferedReader(new StringReader(value));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.equals("-----BEGIN RSA PRIVATE KEY-----"))
					hasStart = true;
				if (line.equals("-----END RSA PRIVATE KEY-----"))
					hasEnd = true;
			}
			if (!hasStart)
				return FormValidation
						.error("This doesn't look like a private key at all");
			if (!hasEnd)
				return FormValidation
						.error("The private key is missing the trailing 'END RSA PRIVATE KEY' marker. Copy&paste error?");
			return FormValidation.ok();
		}

		/**
		 * Test the connection using user credential
		 * 
		 * @param ec2endpoint
		 * @param accessId
		 * @param secretKey
		 * @param privateKey
		 * @param accountId
		 * @param securityGroup
		 * @param securityKeyFileName
		 * @param vmOwnerId
		 * @return
		 * @throws IOException
		 * @throws ServletException
		 */
		protected FormValidation doTestConnection(URL ec2endpoint,
				String accessId, String secretKey, String privateKey,
				String accountId, String securityGroup,
				String securityKeyFileName, String vmOwnerId,
				String projectUserID, String projectID) throws IOException,
				ServletException {
			try {
				LOGGER.info(" Executing doTestConnection ");
				// InstanceAction insac = new InstanceAction();
				String providerCode = null;

				// if URL is going to be null then it is Eucalyptus as we are
				// explicitly assigning null to URL				
				LOGGER.info("Testing connection for Amazon cloud");
				providerCode = EC2Constant.EC2Cloud;
								// String providerCode =
				// ec2endpoint.toString().contains("amazonaws") ? "02" : "01";
				// LOGGER.info(
				// " Secret.decrypt(secretKey).getPlainText() "+Secret.decrypt(secretKey).getPlainText());
				// Hack
				String decryptedSecretKey = "";
				try {
					decryptedSecretKey = Secret.decrypt(secretKey)
							.getPlainText();

				} catch (NullPointerException npe) {
					decryptedSecretKey = secretKey;
				}

				int cntRunningInst = PlatformInfraUtil
						.getVirtualMachineListCount(accountId, vmOwnerId,
								decryptedSecretKey, accessId, providerCode,ec2endpoint.toString(),
								projectUserID, projectID);
				// int cntRunningInst = insac.runningInstancesCount(accountId,
				// vmOwnerId, decryptedSecretKey, accessId, providerCode);
				LOGGER.info(" Dtl ==> " + accountId + "  v " + vmOwnerId
						+ " s " + secretKey + " a " + accessId);
				LOGGER.info(" Number of running instances " + cntRunningInst);
				if(cntRunningInst >= 0){
						return FormValidation.ok(Messages.EC2Cloud_Success());
				}else{
					return FormValidation.error("Not able to connect to Amazon Account.Please check the entries");
				}
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Failed to check EC2 credential", e);
				return FormValidation.error(e.getMessage());
			}
		}

		public FormValidation doGenerateKey(StaplerResponse rsp,
				URL ec2EndpointUrl, String accessId, String secretKey)
				throws IOException, ServletException {
			try {

				return FormValidation.ok(Messages.EC2Cloud_Success());
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Failed to check EC2 credential", e);
				return FormValidation.error(e.getMessage());
			}
		}
	}

	private static final Logger LOGGER = Logger.getLogger(EC2Cloud.class
			.getName());

	private static boolean isSSL(URL endpoint) {
		return endpoint.getProtocol().equals("https");
	}

	private static int portFromURL(URL endpoint) {
		int ec2Port = endpoint.getPort();
		if (ec2Port == -1) {
			ec2Port = endpoint.getDefaultPort();
		}
		return ec2Port;
	}

}
