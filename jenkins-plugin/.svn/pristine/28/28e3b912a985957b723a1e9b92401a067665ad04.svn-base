package hudson.plugins.cloud;

import hudson.Util;
import hudson.plugins.cloud.util.PlatformInfraUtil;
import hudson.slaves.SlaveComputer;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.HttpRedirect;
import org.kohsuke.stapler.HttpResponse;

//import sun.security.jca.GetInstance.Instance;

import com.cognizant.jpaas2.resource.VirtualMachineType;

/**
 * @author Kohsuke Kawaguchi
 */
public class VMwareComputer extends SlaveComputer {
	/**
	 * Cached description of this VMware instance. Lazily fetched.
	 */
	private volatile VirtualMachineType vmwareInstanceDescription;

	public VMwareComputer(VMwareSlave slave) {
		super(slave);
	}

	@Override
	public VMwareSlave getNode() {
		return (VMwareSlave) super.getNode();
	}

	public String getInstanceId() {
		return getName();
	}

	/**
	 * Obtains the instance state description in VMware.
	 * 
	 * <p>
	 * This method returns a cached state, so it's not suitable to check
	 * {@link Instance#getState()} and {@link Instance#getStateCode()} from the
	 * returned instance (but all the other fields are valid as it won't
	 * change.)
	 * 
	 * The cache can be flushed using {@link #updateInstanceDescription()}
	 */
	public VirtualMachineType describeInstance() {
		if (vmwareInstanceDescription == null)
			vmwareInstanceDescription = _describeInstance();
		return vmwareInstanceDescription;
	}

	/**
	 * This will flush any cached description held by
	 * {@link #describeInstance()}.
	 */
	public VirtualMachineType updateInstanceDescription() {
		return vmwareInstanceDescription = _describeInstance();
	}

	/**
	 * Gets the current state of the instance.
	 * 
	 * <p>
	 * Unlike {@link #describeInstance()}, this method always return the current
	 * status by calling VMware.
	 */
	public InstanceState getState() {

		LOGGER.info(" Getting the state of existing instance ");
		String instanceId = this.getInstanceId();
		String accountNumber = this.getNode().accountNumber;
		String userId = this.getNode().userId;
		String accessId = this.getNode().accessId;
		String securityCredential = this.getNode().securityCredential;
		String cloudType = this.getNode().cloudType;
		String projectUserID = this.getNode().getProjectUserID();
		String projectID = this.getNode().getProjectID();

		instanceId = removeThirdPartyConstant(instanceId);

		return InstanceState.find(PlatformInfraUtil.getVirtualMachineDetails(
				accountNumber, instanceId, userId, securityCredential,
				accessId, cloudType, projectUserID, projectID));

	}

	/**
	 * Remove the third party VM constant from label
	 * 
	 * @param instanceId
	 * @return instance id
	 */
	private String removeThirdPartyConstant(String instanceId) {
		if (instanceId != null
				&& StringUtils.contains(instanceId, VMwareConstant.THIRDPARTY)) {
			LOGGER.info("Removing EXT# from instance id.");
			instanceId = instanceId.replace(VMwareConstant.THIRDPARTY, "");
		}
		return instanceId;
	}

	/**
	 * Verifies if this an existing instance or not
	 * 
	 * @param accountNumber
	 * @param instanceId
	 * @param userId
	 * @param securityCredential
	 * @param accessId
	 * @param cloudType
	 * @return present or absent in boolean
	 */
	public boolean isInRunningState(String accountNumber, String instanceId,
			String userId, String securityCredential, String accessId,
			String cloudType, String projectUserID, String projectID) {
		// Is this instance already registered for the given credential
		LOGGER.info("Verifying if this is a running instance");
		// InstanceAction insac = new InstanceAction();
		instanceId = removeThirdPartyConstant(instanceId);
		boolean flag = PlatformInfraUtil.isRunningVirtualMachine(accountNumber,
				instanceId, userId, securityCredential, accessId, accessId,
				projectUserID, projectID);

		LOGGER.info("Is this a running instance " + flag);
		return flag;
	}

	/**
	 * Number of milli-secs since the instance was started.
	 */
	public long getUptime() {
		long now = System.currentTimeMillis();
		LOGGER.info("Current time in millisec :: " + now);
		long createdOn = describeInstance().getCreationTimestamp();
		GregorianCalendar cal = new GregorianCalendar();
//		Date localTime = new Date(createdOn + cal.get(Calendar.ZONE_OFFSET)
//				+ cal.get(Calendar.DST_OFFSET));
		Date localTime = new Date(createdOn);
		LOGGER.info("Instance created on in millisec :: " + localTime.getTime());
		return now - localTime.getTime();
	}

	/**
	 * Returns uptime in the human readable form.
	 */
	public String getUptimeString() {
		return Util.getTimeSpanString(getUptime());
	}

	/**
	 * Describe this instance
	 * 
	 * @return
	 */
	private VirtualMachineType _describeInstance() {
		LOGGER.info(" Describing instance starts with following parameters "
				+ this.getNode().accountNumber + " "
				+ this.getNode().getInstanceId() + " " + this.getNode().userId
				+ " " + this.getNode().securityCredential + " "
				+ this.getNode().accessId + " " + this.getNode().cloudType);

		String instanceId = this.getNode().getInstanceId();

		// Removing EXT# for Thrid Party vm
		instanceId = removeThirdPartyConstant(instanceId);

		VirtualMachineType machineType = PlatformInfraUtil.getVirtualMachine(
				this.getNode().accountNumber, instanceId,
				this.getNode().userId, this.getNode().securityCredential, this
						.getNode().accessId, this.getNode().cloudType, this
						.getNode().getProjectUserID(), this.getNode()
						.getProjectID());

		LOGGER.info(" Describing instance ends ");
		return machineType;
	}

	/**
	 * When the slave is deleted, terminate the instance.
	 */
	@Override
	public HttpResponse doDoDelete() throws IOException {
		checkPermission(DELETE);
		try {
			LOGGER.info("Deleting slave");
			// TODO : if account number and userid are blank get it from
			// somewhare and kill this
			getNode().terminateInstance(getNode().getInstanceId(),
					getNode().getProjectUserID(), getNode().getProjectID(),
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

	private static final Logger LOGGER = Logger.getLogger(VMwareComputer.class
			.getName());
}
