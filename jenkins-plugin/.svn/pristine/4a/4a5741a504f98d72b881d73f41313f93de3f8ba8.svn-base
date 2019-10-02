package hudson.plugins.util;

import hudson.plugins.Cloud360;
import hudson.plugins.constant.Cloud360PluginConstants;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cognizant.cloudset.client.RestClient;
import com.cognizant.cloudset.constants.Cloud360Constants;
import com.cognizant.cloudset.message.Cloud360Response;
import com.cognizant.cloudset.model.Instance;
import com.cognizant.cloudset.model.InstanceStatus;
import com.cognizant.cloudset.model.Message;
import com.cognizant.jpaas2.commons.expection.PlatformException;

public class Cloud360LaunchUtil {
	
	private static final Logger LOGGER = Logger.getLogger(Cloud360LaunchUtil.class.getName());
	
	/**
	 * Launch a virtual machine corresponding to the compute profile id
	 * and return the virtual machine id
	 * 
	 * @param computeProfileId
	 * @param restURL
	 * @param restID
	 * @param restPasswd
	 * @return String
	 * @throws PlatformException 
	 */
	public static String launchComputeInstanceFomProfile(String computeProfileId, String restURL, String restID, String restPasswd) throws PlatformException {
		
		LOGGER.info("Launching VM from Launch Util..");
		
		String vmId = null;
		LOGGER.info("Initializing Rest client..");
		RestClient client = new RestClient(restURL, restID, restPasswd);
		
		//create instance
		Cloud360Response resp = client.createComputeInstanceFromComputeProfile(computeProfileId);
		
		if(resp != null && resp.getResponseCode()==Cloud360Constants.SUCCESS) {
			List<Message> messages = (List<Message>) resp.getResponseResult();
			
			if(messages != null && !messages.isEmpty()) {
				Message msg = messages.get(0);
				String trackingID = parseMessage(msg.getMessage());
				if(trackingID == null) {
					return null;
				}
				
				LOGGER.info("VM queued to launch. Tracking VM status with: "+trackingID);
				
				vmId = trackComputeInstance(trackingID, client);
				if(vmId == null) {
					return null;
				} else {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				//start instance
				if(startComputeInstance(vmId, client)) {
					
					//poll the instance to check the following:
					//1. IP allocation
					//2. Launch time
					//the instance will be considered up only when both these values are present.
					if(isInstanceUp(vmId, client)) {
						return vmId;
					}
				} else {
					String terminated = deProvisionComputeInstance(vmId, client);
					LOGGER.info("Unable to start up VM. Terminated VM with id: "+terminated);
				}
			}
		}
		
		return null;
	}

	/**
	 * Get virtual machine details for a given virtual machine id
	 * 
	 * @param instanceID
	 * @param restURL
	 * @param restID
	 * @param restPasswd
	 * @return Instance
	 */
	public static Instance getComputeInstanceFromID(String instanceID, String restURL, String restID, String restPasswd) {
		Instance instance = null;
		RestClient client = new RestClient(restURL, restID, restPasswd);
		Cloud360Response resp = client.getComputeInstanceDetailsById(instanceID);
		if(resp != null && resp.getResponseCode()==Cloud360Constants.SUCCESS) {
			List<Instance> list = (List<Instance>) resp.getResponseResult();
			if(list != null && !list.isEmpty()) {
				instance = list.get(0);
			}
		}
		return instance;
	}
	
	/**
	 * Terminate a virtual machine for a given virtual machine id.
	 * 
	 * @param instanceId
	 * @param restURL
	 * @param restID
	 * @param restPasswd
	 * @throws PlatformException 
	 */
	public static void terminateComputeInstance(String instanceId, String restURL, String restID, String restPasswd) 
	throws PlatformException {
		
		RestClient client = new RestClient(restURL, restID, restPasswd);
		String vmId = stopComputeInstance(instanceId, client);
		
		if(vmId != null) {
			vmId = deProvisionComputeInstance(instanceId, client);
			if(vmId == null) {
				throw new PlatformException("Error while terminating VM", null, null);
			}
		} else {
			throw new PlatformException("Error while stopping VM", null, null);
		}
	}
	
	/**
	 * @param instanceId
	 * @param restURL
	 * @param restID
	 * @param restPasswd
	 * @return True if VM is powered on <br> False otherwise
	 */
	public static boolean isVmRunning(String instanceId, String restURL, String restID, String restPasswd) {
		Instance instance = getComputeInstanceFromID(instanceId, restURL, restID, restPasswd);
		
		if(instance == null) {
			return false;
		}
		
		if(instance.getState().equalsIgnoreCase(Cloud360PluginConstants.POWERED_ON)) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param instanceId
	 * @param restURL
	 * @param restID
	 * @param restPasswd
	 * @return String
	 */
	public static String getVmState(String instanceId, String restURL, String restID, String restPasswd) {
		Instance instance = getComputeInstanceFromID(instanceId, restURL, restID, restPasswd);
		
		if(instance == null) {
			return null;
		}
		return instance.getState();
	}
	
	/**
	 * Polls Cloud360 to track the status of a created virtual machine
	 * and return the virtual machine id
	 * 
	 * @param trackingID
	 * @param client
	 * @return String
	 */
	private static String trackComputeInstance(String trackingID, RestClient client) {
		String vmID = null;
		boolean isVMLaunched = false;
		Cloud360Response resp = null;
		
		//poll cloud360 with tracking id.
		while(!isVMLaunched) {
			resp = client.getInstanceStatus(trackingID);
			if(resp != null && resp.getResponseCode() == Cloud360Constants.SUCCESS) {
				List<InstanceStatus> status = (List<InstanceStatus>) resp.getResponseResult();
				if(status != null && !status.isEmpty()) {
					
					String msg = status.get(0).getMessage();
					if(msg.contains(Cloud360Constants.REQUEST_COMPLETED)) {
						vmID = status.get(0).getId();
						isVMLaunched = true;
						
						LOGGER.info("VM creation complete. VM id is "+vmID);
						
						break;
					} else if(msg.contains(Cloud360Constants.REQUEST_FAILED)){
						LOGGER.info("Failed to create VM.");
						break;
					}
				}
			}
		}
		
		return vmID;
	}
	
	private static boolean isInstanceUp(String vmId, RestClient client) {
		Instance instance = null;
		boolean isVMUp = false;
		Cloud360Response resp = null;
		
		while(!isVMUp) {
			resp = client.getComputeInstanceDetailsById(vmId);
			if(resp != null && resp.getResponseCode() == Cloud360Constants.SUCCESS) {
				List<Instance> instances = (List<Instance>) resp.getResponseResult();
				if(instances != null && !instances.isEmpty()) {
					
					instance = instances.get(0);
					if(!instance.getIpAddress().equals("N/A") && !instance.getLaunchTime().equals("N/A")) {
						isVMUp = true;
						return true;
					}
					
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Starts the virtual machine for a given virtual machine id
	 * 
	 * @param vmId
	 * @param client
	 * @return True if the correct VM was started <br> False otherwise
	 */
	private static boolean startComputeInstance(String vmId, RestClient client) {
		Instance instance = null;
		Cloud360Response resp = client.startComputeInstance(vmId);
		
		if(resp != null && resp.getResponseCode() == Cloud360Constants.SUCCESS) {
			List<Instance> instances = (List<Instance>) resp.getResponseResult();
			if(instances != null && !instances.isEmpty()) {
				instance = instances.get(0);
			}
		}
		
		if(instance != null) {
			if(instance.getId().equals(vmId)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Stops the virtual machine for a given virtual machine id
	 * 
	 * @param vmId
	 * @param client
	 * @return String
	 */
	private static String stopComputeInstance(String vmId, RestClient client) {
		List<Instance> stopped = null;
		Cloud360Response resp = client.stopComputeInstance(vmId);
		if(resp != null && resp.getResponseCode()==Cloud360Constants.SUCCESS) {
			stopped = (List<Instance>) resp.getResponseResult();
		}
		if(stopped != null && !stopped.isEmpty()) {
			return stopped.get(0).getId();
		}
		return null;
	}
	
	/**
	 * Terminates the virtual machine for a given virtual machine id
	 * 
	 * @param vmId
	 * @param client
	 * @return String
	 */
	private static String deProvisionComputeInstance(String vmId, RestClient client) {
		List<Instance> terminated = null;
		Cloud360Response resp = client.deProvisionComputeInstance(vmId);
		if(resp != null && resp.getResponseCode()==Cloud360Constants.SUCCESS) {
			terminated = (List<Instance>) resp.getResponseResult();
		}
		if(terminated != null && !terminated.isEmpty()) {
			return terminated.get(0).getId();
		}
		return null;
	}
	
	private static String parseMessage(String message) {
		if(message.contains("ID:")) {
			return message.substring(message.indexOf("ID:")+3);
		}
		return "";
	}
	
}
