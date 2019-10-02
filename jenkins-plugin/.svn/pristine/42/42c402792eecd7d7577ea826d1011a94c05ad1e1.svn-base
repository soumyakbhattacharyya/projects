package hudson.plugins.ec2;

import hudson.Util;
import hudson.plugins.ec2.util.DaseinServerMapper;
import hudson.plugins.ec2.util.PlatformInfraUtil;
import hudson.slaves.SlaveComputer;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.dasein.cloud.services.server.Server;
import org.kohsuke.stapler.HttpRedirect;
import org.kohsuke.stapler.HttpResponse;

import sun.security.jca.GetInstance.Instance;

import com.cognizant.jpaas2.resource.VirtualMachineType;
//import com.cts.cloudcoe.cloudplugin.action.InstanceAction;

/**
 * @author Kohsuke Kawaguchi
 */
public class EC2Computer extends SlaveComputer {
	/**
	 * Cached description of this EC2 instance. Lazily fetched.
	 */
	private volatile Server ec2InstanceDescription;

	public EC2Computer(EC2Slave slave) {
		super(slave);
	}

	@Override
	public EC2Slave getNode() {
		return (EC2Slave) super.getNode();
	}

	public String getInstanceId() {
		return getName();
	}

	/**
	 * Gets the EC2 console output.
	 *//*
	public String getConsoleOutput()  throws EC2Exception {
		LOGGER.info("getConsoleOutput");
		InstanceAction ia = new InstanceAction();
		
		//Removing EXT# for Thrid Party vm
		
		String instanceId = this.getNode().getInstanceId();
		if(instanceId != null && StringUtils.contains(instanceId, EC2Constant.THIRDPARTY)){
			LOGGER.info("Removing EXT# from instance id.");
			instanceId = this.getNode().getInstanceId().replace(EC2Constant.THIRDPARTY, "");
		}
		return ia.getConsoleOutput(getNode().accountNumber, instanceId,
				 getNode().userId, getNode().secretKey,
				getNode().accessId, getNode().cloudType);

	}*/

	/**
	 * Obtains the instance state description in EC2.
	 * 
	 * <p>
	 * This method returns a cached state, so it's not suitable to check
	 * {@link Instance#getState()} and {@link Instance#getStateCode()} from the
	 * returned instance (but all the other fields are valid as it won't
	 * change.)
	 * 
	 * The cache can be flushed using {@link #updateInstanceDescription()}
	 */
	public Server describeInstance() /* throws EC2Exception */{
		if (ec2InstanceDescription == null)
			ec2InstanceDescription = _describeInstance();
		return ec2InstanceDescription;
	}

	/**
	 * This will flush any cached description held by
	 * {@link #describeInstance()}.
	 */
	public Server updateInstanceDescription() {
		return ec2InstanceDescription = _describeInstance();
	}

	/**
	 * Gets the current state of the instance.
	 * 
	 * <p>
	 * Unlike {@link #describeInstance()}, this method always return the current
	 * status by calling EC2.
	 */
	public InstanceState getState() {

		LOGGER.info(" Getting the state of existing instance ");
		String instanceId = this.getInstanceId();
		String accountNumber = this.getNode().accountNumber;
		String userId = this.getNode().userId;
		String accessId = this.getNode().accessId;
		String secretKey = this.getNode().secretKey;
		String cloudType = this.getNode().cloudType;
		String projectUserID = this.getNode().getProjectUserID();
		String projectID = this.getNode().getProjectID();
		// ec2InstanceDescription =_describeInstance();

		instanceId = removeThirdPartyConstant(instanceId);
		/*return InstanceState.find(new InstanceAction()
				.describeServer(accountNumber, instanceId, userId, secretKey,
						accessId, cloudType).getCurrentState().toString());*/
		
		return InstanceState.find(PlatformInfraUtil.
				getVirtualMachineDetails(accountNumber, instanceId, 
						userId, secretKey, accessId, cloudType,projectUserID,projectID));

	}

	/**
	 * Remove the third party VM constant from label
	 * @param instanceId
	 * @return instance id
	 */
	private String removeThirdPartyConstant(String instanceId)
	{
		if(instanceId != null &&StringUtils.contains(instanceId, EC2Constant.THIRDPARTY)){
			LOGGER.info("Removing EXT# from instance id.");
			instanceId = instanceId.replace(EC2Constant.THIRDPARTY, "");
		}
		return instanceId;
	}

	/**
	 * Verifies if this an existing instance or not
	 * 
	 * @param accountNumber
	 * @param instanceId
	 * @param userId
	 * @param secretKey
	 * @param accessId
	 * @param cloudType
	 * @return present or absent in boolean
	 */
	public boolean isInRunningState(String accountNumber, String instanceId,
			String userId, String secretKey, String accessId, String cloudType,String projectUserID,String projectID) {
		// Is this instance already registered for the given credential
		LOGGER.info("Verifying if this is a running instance");
		//InstanceAction insac = new InstanceAction();
		instanceId = removeThirdPartyConstant(instanceId);
		//boolean flag = insac.isInstanceRunning(accountNumber, instanceId, userId, secretKey, accessId, cloudType);
		boolean flag = PlatformInfraUtil.isRunningVirtualMachine(accountNumber, instanceId, userId, secretKey,
				accessId, accessId,projectUserID,projectID);
		
		LOGGER.info("Is this a running instance " + flag);
		return flag;
	}

	/**
	 * Number of milli-secs since the instance was started.
	 */
	public long getUptime() /* throws EC2Exception */{
		long now = System.currentTimeMillis();
		LOGGER.info("Current time in millisec :: "+now);
		long createdOn = describeInstance().getStartTime();
		GregorianCalendar cal = new GregorianCalendar();
		Date localTime = new Date(createdOn + cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET));
		LOGGER.info("Instance created on in millisec :: "+localTime.getTime());
		return now - localTime.getTime();
	}

	/**
	 * Returns uptime in the human readable form.
	 */
	public String getUptimeString() /* throws EC2Exception */{
		return Util.getTimeSpanString(getUptime());
	}

	/**
	 * Describe this instance
	 * 
	 * @return
	 */
	private Server _describeInstance() {
		LOGGER.info(" Describing instance starts with following parameters "
				+ this.getNode().accountNumber + " "
				+ this.getNode().getInstanceId() + " " + this.getNode().userId
				+ " " + this.getNode().secretKey + " "
				+ this.getNode().accessId + " " + this.getNode().cloudType);
		// return
		// EC2Cloud.get().connect().describeInstances(Collections.<String>singletonList(getNode().getInstanceId())).get(0).getInstances().get(0);
		//InstanceAction insac = new InstanceAction();
		
		String instanceId = this.getNode().getInstanceId();
		
		//Removing EXT# for Thrid Party vm
		instanceId = removeThirdPartyConstant(instanceId);
		
		/*Server server = insac.describeServer(this.getNode().accountNumber, instanceId, this.getNode().userId, this
				.getNode().secretKey, this.getNode().accessId,
				this.getNode().cloudType);*/
		
		
		VirtualMachineType machineType = PlatformInfraUtil.getVirtualMachine(this.getNode().accountNumber, instanceId, this.getNode().userId, this
				.getNode().secretKey, this.getNode().accessId, this.getNode().cloudType,
				this.getNode().getProjectUserID(),this.getNode().getProjectID());
		
		Server server = null;
		if(machineType != null){
			server = DaseinServerMapper.convertToServerFromVirtualMachineType(machineType);
		}
		
		LOGGER.info(" Describing instance ends ");
		return server;
	}

	/**
	 * When the slave is deleted, terminate the instance.
	 */
	@Override
	public HttpResponse doDoDelete() throws IOException {
		checkPermission(DELETE);
		try {
			LOGGER.info("Deleting slave");
			// TODO : if account number and userid are blank get it from somewhare and kill this
			getNode().terminateInstance(getNode().getInstanceId(),getNode().getProjectUserID(),
					getNode().getProjectID(),
					new String[] { getNode().accountNumber, getNode().userId });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return new HttpRedirect("..");
	}

	/**
	 * What username to use to run root-like commands
	 * 
	 */
	public String getRemoteAdmin() {
		return getNode().getRemoteAdmin();
	}
	
	public String getRemoteAdminPassword() {
		return getNode().getRemoteAdminPassword();
	}

	public int getSshPort() {
		return getNode().getSshPort();
	}

	public String getRootCommandPrefix() {
		return getNode().getRootCommandPrefix();
	}

	private static final Logger LOGGER = Logger.getLogger(EC2Computer.class
			.getName());
}
