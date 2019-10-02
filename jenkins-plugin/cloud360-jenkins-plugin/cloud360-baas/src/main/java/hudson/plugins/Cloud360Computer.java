package hudson.plugins;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import org.dasein.cloud.services.server.Server;
import org.kohsuke.stapler.HttpRedirect;
import org.kohsuke.stapler.HttpResponse;

import com.cognizant.cloudset.model.Instance;
import com.cognizant.jpaas2.commons.expection.PlatformException;

import hudson.Util;
import hudson.plugins.util.Cloud360LaunchUtil;
import hudson.plugins.util.Cloud360ServerMapper;
import hudson.slaves.SlaveComputer;

public class Cloud360Computer extends SlaveComputer{

	private static final Logger LOGGER = Logger.getLogger(Cloud360Computer.class
			.getName());
	
	private volatile Server cloud360InstanceDescription;
	
	public Cloud360Computer(Cloud360Slave slave) {
		super(slave);
	}
	
	@Override
	public Cloud360Slave getNode() {
		return (Cloud360Slave) super.getNode();
	}

	public String getInstanceId() {
		return getName();
	}
	
	/**
	 * Gets the current state of the instance
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public InstanceState getState() throws PlatformException {

		LOGGER.info(" Getting the state of existing instance ");
		return InstanceState.find(Cloud360LaunchUtil.getVmState(this.getNode().getInstanceId(),
				this.getNode().getRestURL(), this.getNode().getRestUserID(),
				this.getNode().getRestPassword()));
	}
	
	
	/**
	 * Verifies if this an existing instance or not
	 * 
	 * @param instanceId
	 * @param restUrl
	 * @param restUserId
	 * @param restPassword
	 * @return True if the VM is running <br> False otherwise
	 */
	public boolean isInRunningState(String instanceId, String restUrl,
			String restUserId, String restPassword) {
		
		LOGGER.info("Verifying if this is a running instance");
		try{
			boolean flag = Cloud360LaunchUtil.isVmRunning(instanceId, this.getNode().getRestURL(),
					this.getNode().getRestUserID(), this.getNode().getRestPassword());

			LOGGER.info("Is this a running instance " + flag);
			return flag;
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Number of milli-secs since the instance was started.
	 */
	public long getUptime() /* throws EC2Exception */{
		long now = System.currentTimeMillis();
		LOGGER.info("Current time in millisec :: " + now);
		long createdOn = describeInstance().getStartTime();
		GregorianCalendar cal = new GregorianCalendar();
		if(createdOn == 0) {
			return now;
		}
		Date localTime = new Date(createdOn + cal.get(Calendar.ZONE_OFFSET)
				+ cal.get(Calendar.DST_OFFSET));
		LOGGER.info("Instance created on in millisec :: " + localTime.getTime());
		return now - localTime.getTime();
	}

	/**
	 * Returns uptime in the human readable form.
	 */
	public String getUptimeString() {
		return Util.getTimeSpanString(getUptime());
	}
	
	public Server describeInstance() {
		if (cloud360InstanceDescription == null)
			cloud360InstanceDescription = _describeInstance();
		return cloud360InstanceDescription;
	}

	/**
	 * This will flush any cached description held by
	 * {@link #describeInstance()}.
	 */
	public Server updateInstanceDescription() {
		return cloud360InstanceDescription = _describeInstance();
	}
	
	private Server _describeInstance() {
		LOGGER.info(" Describing instance starts with following parameters "
				+ this.getNode().getInstanceId() + " " + this.getNode().remoteUserId
				+ " " + this.getNode().secretPrivateKey);

		String instanceId = this.getNode().getInstanceId();

		try {
			Instance virtualMachine = Cloud360LaunchUtil.getComputeInstanceFromID(instanceId,
					this.getNode().getRestURL(), this.getNode().getRestUserID(), this.getNode().getRestPassword());

			Server server = null;
			if (virtualMachine != null) {
				server = Cloud360ServerMapper.convertToServerFromComputeInstance(virtualMachine);
			}

			LOGGER.info(" Describing instance ends ");
			return server;
		} catch (Exception e) {
			return null;
		}

	}
	
	@Override
	public HttpResponse doDoDelete() throws IOException {
		checkPermission(DELETE);
		try {
			LOGGER.info("Deleting slave");
			getNode().terminateInstance(getNode().getInstanceId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return new HttpRedirect("..");
	}
	
	public String getRemoteUserId() {
		return getNode().getRemoteUserId();
	}

	public String getSecretPrivateKey() {
		return getNode().getSecretPrivateKey();
	}

	public int getLocalPort() {
		return getNode().getLocalPort();
	}

	public int getRemoteSSHPort() {
		return getNode().getRemoteSSHPort();
	}

	public String getProtocol() {
		return getNode().getProtocol();
	}

	public String getRootCommandPrefix() {
		return getNode().getRootCommandPrefix();
	}
	
	public String getRemoteFSRoot() {
		return getNode().getRemoteFSRoot();
	}
	
}
