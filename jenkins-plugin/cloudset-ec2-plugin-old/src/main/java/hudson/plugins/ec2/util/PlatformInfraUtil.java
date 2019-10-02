package hudson.plugins.ec2.util;

import hudson.plugins.ec2.constant.ClientConstants;
import hudson.plugins.ec2.resource.ServiceLocator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Logger;

import com.cognizant.jpaas2.command.AbstractCommand;
import com.cognizant.jpaas2.command.CommandCatalog;
import com.cognizant.jpaas2.command.CommandFactory;
import com.cognizant.jpaas2.commons.expection.PlatformException;
import com.cognizant.jpaas2.context.ProviderContext;
import com.cognizant.jpaas2.context.RequestContext;
import com.cognizant.jpaas2.context.SecurityContext;
import com.cognizant.jpaas2.context.UserContext;
import com.cognizant.jpaas2.resource.MachineImageStateType;
import com.cognizant.jpaas2.resource.MachineImageType;
import com.cognizant.jpaas2.resource.StringProperty;
import com.cognizant.jpaas2.resource.VirtualMachineProductType;
import com.cognizant.jpaas2.resource.VirtualMachineType;
import com.cognizant.jpaas2.resource.VmStateType;
import com.cognizant.jpaas2.resourcespecification.MachineResourceSpecification;

/**
 * This class acts as an wrapper to the PlatformInfraServices this gets all the
 * required information to call the infra service client code and also takes
 * care of the exception handling part.
 * 
 * @author Cognizant
 * 
 */
public class PlatformInfraUtil {

	private static final Logger LOGGER = Logger
			.getLogger(PlatformInfraUtil.class.getName());

	/**
	 * The following 2 URL gets the infra service proxy service url from the
	 * properties file. Presently the urls are present in
	 * jpaas-integration.properties which is part of MASTER_HOME
	 */
	private static final String virtualServiceURL = ServiceLocator
			.getInstance().getEc2Virtualmachine();
	private static final String machineImageURL = ServiceLocator.getInstance()
			.getEc2Machineimage();

	/**
	 * This method checks if the virtual machine with the given vmid is in
	 * Running state or not
	 * 
	 * @param accountNumber
	 * @param instanceId
	 * @param userId
	 * @param privateCredential
	 * @param publicCredential
	 * @param providerId
	 * @return
	 */
	public static boolean isRunningVirtualMachine(String accountNumber,
			String instanceId, String userId, String privateCredential,
			String publicCredential, String providerId,String endPoint, String projectUserId,
			String projectId) {

		// Populate Information to get the instance
		RequestContext context = new RequestContext();

		context.setServiceURL(virtualServiceURL);

		ProviderContext providerContext = new ProviderContext();
		providerContext = getCloudProviderContext(providerId, providerContext,endPoint);
		providerContext.setAccessPrivate(privateCredential.getBytes());
		providerContext.setAccessPublic(publicCredential.getBytes());
		providerContext.setAccountNumber(accountNumber);
		populateUserContextInformation(context, projectUserId, projectId);
		populateSecurityContext(context);
		
		MachineResourceSpecification machineResource = new MachineResourceSpecification();
		machineResource.setVmId(instanceId);
		machineResource.setCaller(accountNumber);
		context.setProviderContext(providerContext);

		VirtualMachineType machineType = new VirtualMachineType();
		machineType.setResourceSpecification(machineResource);
		context.setResource(machineType);

		AbstractCommand command = CommandFactory.getCommand(
				CommandCatalog.GetMachine, context);
		command.setContext(context);
		try {
			command.execute();
			if (machineType.getCurrentState().equals(VmStateType.RUNNING)) {
				return true;
			} else {
				return false;
			}
		} catch (PlatformException pExp) {
			LOGGER.severe("Error occured while getting Running VirtualMachine ");
			return false;
		}
	}

	
	/**
	 * This method return all the detail of the given virtual Machine id
	 * 
	 * @param accountNumber
	 * @param instanceId
	 * @param userId
	 * @param privateCredential
	 * @param publicCredential
	 * @param providerId
	 * @return
	 */
	public static VirtualMachineType getVirtualMachine(String accountNumber,
			String instanceId, String userId, String privateCredential,
			String publicCredential, String providerId, String endPoint,String projectUserId,
			String projectId) {

		// Populate Information to get the instance
		RequestContext context = new RequestContext();
		ProviderContext providerContext = new ProviderContext();

		context.setServiceURL(virtualServiceURL);

		providerContext = getCloudProviderContext(providerId, providerContext,endPoint);
		providerContext.setAccessPrivate(privateCredential.getBytes());
		providerContext.setAccessPublic(publicCredential.getBytes());
		providerContext.setAccountNumber(accountNumber);
		
		populateUserContextInformation(context, projectUserId, projectId);
		populateSecurityContext(context);
		
		MachineResourceSpecification machineResource = new MachineResourceSpecification();
		machineResource.setVmId(instanceId);
		machineResource.setCaller(accountNumber);
		context.setProviderContext(providerContext);
		
		VirtualMachineType machineType = new VirtualMachineType();
		machineType.setResourceSpecification(machineResource);
		context.setResource(machineType);

		AbstractCommand command = CommandFactory.getCommand(
				CommandCatalog.GetMachine, context);
		command.setContext(context);
		try {
			command.execute();
		} catch (PlatformException e) {
			LOGGER.severe("Error occured while getting Running VirtualMachine ");
			return null;
		}

		machineType = (VirtualMachineType) context.getResource();
		return machineType;
	}

	/**
	 * This method returns the state of the Virtual Machine
	 * 
	 * @param accountNumber
	 * @param instanceId
	 * @param userId
	 * @param privateCredential
	 * @param publicCredential
	 * @param providerId
	 * @return
	 */
	public static String getVirtualMachineDetails(String accountNumber,
			String instanceId, String userId, String privateCredential,
			String publicCredential, String providerId,String ec2endpoint, String projectUserId,
			String projectId) {

		// Populate Information to get the instance
		RequestContext context = new RequestContext();
		ProviderContext providerContext = new ProviderContext();
		context.setServiceURL(virtualServiceURL);

		providerContext = getCloudProviderContext(providerId, providerContext,ec2endpoint);

		providerContext.setAccessPrivate(privateCredential.getBytes());
		providerContext.setAccessPublic(publicCredential.getBytes());
		providerContext.setAccountNumber(accountNumber);
		
		MachineResourceSpecification machineResource = new MachineResourceSpecification();
		machineResource.setVmId(instanceId);
		machineResource.setCaller(accountNumber);
		context.setProviderContext(providerContext);

		populateUserContextInformation(context, projectUserId, projectId);
		populateSecurityContext(context);

		VirtualMachineType machineType = new VirtualMachineType();
		machineType.setResourceSpecification(machineResource);
		context.setResource(machineType);

		AbstractCommand command = CommandFactory.getCommand(
				CommandCatalog.GetMachine, context);
		command.setContext(context);
		try {
			command.execute();
		} catch (PlatformException e) {
			LOGGER.severe("Error occured while getting Running VirtualMachine ");
			return null;
		}
		return machineType.getCurrentState().toString();
	}

	/**
	 * This method returns if the machine image with the provided image-id is
	 * active or not
	 * 
	 * @param accountNumber
	 * @param instanceId
	 * @param userId
	 * @param privateCredential
	 * @param publicCredential
	 * @param providerId
	 * @return
	 */
	public static boolean getMachineImage(String accountNumber,
			String instanceId, String userId, String privateCredential,
			String publicCredential, String providerId, String ec2endpoint,String projectUserId,
			String projectId) {

		boolean machineExist = false;
		// Populate Information to get the instance
		RequestContext context = new RequestContext();
		ProviderContext providerContext = new ProviderContext();
		context.setServiceURL(machineImageURL);

		providerContext = getCloudProviderContext(providerId, providerContext,ec2endpoint);

		providerContext.setAccessPrivate(privateCredential.getBytes());
		providerContext.setAccessPublic(publicCredential.getBytes());
		providerContext.setAccountNumber(accountNumber);

		MachineResourceSpecification machineResource = new MachineResourceSpecification();
		machineResource.setFromMachineImageId(instanceId);
		machineResource.setCaller(accountNumber);
		context.setProviderContext(providerContext);

		populateUserContextInformation(context, projectUserId, projectId);
		populateSecurityContext(context);

		MachineImageType machineImage = new MachineImageType();
		machineImage.setResourceSpecification(machineResource);
		context.setResource(machineImage);

		AbstractCommand command = CommandFactory.getCommand(
				CommandCatalog.GetImage, context);
		command.setContext(context);
		try {
			command.execute();
		} catch (PlatformException e) {
			LOGGER.severe("Error occured while getting Running Machien image ");
			return false;
		}
		if (machineImage != null
				&& machineImage.getCurrentState() != null
				&& machineImage.getCurrentState().equals(
						MachineImageStateType.ACTIVE)) {
			machineExist = true;
		}
		return machineExist;
	}

	/**
	 * This method returns number of the currently running virtual machine. This
	 * is method used for testing the cloud connection
	 * 
	 * @param accountNumber
	 * @param userId
	 * @param privateCredential
	 * @param publicCredential
	 * @param providerId
	 * @return
	 */
	public static int getVirtualMachineListCount(String accountNumber,
			String userId, String privateCredential, String publicCredential,
			String providerId, String ec2endpoint,String projectUserId, String projectId) {

		// Populate Information to get the instance
		RequestContext context = new RequestContext();
		ProviderContext providerContext = new ProviderContext();
		context.setServiceURL(virtualServiceURL);

		providerContext = getCloudProviderContext(providerId, providerContext,ec2endpoint);

		providerContext.setAccessPrivate(privateCredential.getBytes());
		providerContext.setAccessPublic(publicCredential.getBytes());
		providerContext.setAccountNumber(accountNumber);

		List<StringProperty> propertiesList = new ArrayList<StringProperty>();
		StringProperty addressing = new StringProperty();
		addressing.setKey("addressing");
		addressing.setValue(ServiceLocator.getInstance().getEc2Addressing());
		propertiesList.add(addressing);
		providerContext.setCustomProperties(propertiesList);

		context.setProviderContext(providerContext);

		MachineResourceSpecification machineResource = new MachineResourceSpecification();
		machineResource.setCaller(accountNumber);

		populateUserContextInformation(context, projectUserId, projectId);
		populateSecurityContext(context);

		VirtualMachineType machineType = new VirtualMachineType();
		machineType.setResourceSpecification(machineResource);
		context.setResource(machineType);

		AbstractCommand command = CommandFactory.getCommand(
				CommandCatalog.CountVirtualMachine, context);
		command.setContext(context);
		try {
			command.execute();
		} catch (PlatformException e) {
			LOGGER.severe("Error occured while getting Running VirtualMachine list");
			return -1;
		}
		int response = (Integer) context.getResponse();
		return response;
	}

	/**
	 * this method is used for terminating the virtual machine with the given
	 * virtualMachineID
	 * 
	 * @param accountNumber
	 * @param instanceId
	 * @param userId
	 * @param privateCredential
	 * @param publicCredential
	 * @param providerId
	 */
	public static void terminateVirtualMachine(String accountNumber,
			String instanceId, String userId, String privateCredential,
			String publicCredential, String providerId,String ec2endpoint, String projectUserId,
			String projectId) {

		// Populate Information to get the instance
		RequestContext context = new RequestContext();
		ProviderContext providerContext = new ProviderContext();
		context.setServiceURL(virtualServiceURL);

		providerContext = getCloudProviderContext(providerId, providerContext,ec2endpoint);

		providerContext.setAccessPrivate(privateCredential.getBytes());
		providerContext.setAccessPublic(publicCredential.getBytes());
		providerContext.setAccountNumber(accountNumber);

		MachineResourceSpecification machineResource = new MachineResourceSpecification();
		machineResource.setVmId(instanceId);
		machineResource.setCaller(accountNumber);
		context.setProviderContext(providerContext);

		populateUserContextInformation(context, projectUserId, projectId);
		populateSecurityContext(context);		

		VirtualMachineType machineType = new VirtualMachineType();
		machineType.setResourceSpecification(machineResource);
		context.setResource(machineType);

		try {
			AbstractCommand command = CommandFactory.getCommand(
					CommandCatalog.TerminateCommand, context);
			command.setContext(context);
			command.execute();
		} catch (Exception e) {
			LOGGER.severe("Error occured while terminating VirtualMachine ");
		}
		LOGGER.info("Terminate Instance method executed successfully");

	}

	/**
	 * 
	 * This method launches a virtual machine with the provided image id. Incase
	 * there is no public ip available, this will throw a platform exception
	 * with the error message as no more public ip available
	 * 
	 * @param accountNumber
	 * @param userId
	 * @param imageId
	 * @param kernelId
	 * @param imageType
	 * @param minInstance
	 * @param maxInstance
	 * @param key
	 * @param group
	 * @param privateCredential
	 * @param publicCredential
	 * @param providerId
	 * @return
	 * @throws PlatformException
	 */
	public static String launchVirtualMachine(String accountNumber,
			String userId, String imageId, String kernelId, String imageType,
			int minInstance, int maxInstance, String key, String group,
			String privateCredential, String publicCredential,
			String providerId,String endPoint,String projectUserId, String projectId)
			throws PlatformException {

		String virtualMachineID = "Not-Launched";
		VirtualMachineType machineType = new VirtualMachineType();
		RequestContext context = new RequestContext();
		context.setServiceURL(virtualServiceURL);

		populateUserContextInformation(context, projectUserId, projectId);
		populateSecurityContext(context);

		// Populate Information to get the instance
		ProviderContext providerContext = new ProviderContext();

		providerContext = getCloudProviderContext(providerId, providerContext,endPoint);

		providerContext.setAccessPrivate(privateCredential.getBytes());
		providerContext.setAccessPublic(publicCredential.getBytes());
		providerContext.setAccountNumber(accountNumber);

		//if (!providerId.equalsIgnoreCase(ClientConstants.VMWARE_PROVIDER)) {
			List<StringProperty> propertiesList = new ArrayList<StringProperty>();
			StringProperty addressing = new StringProperty();
			addressing.setKey("addressing");
			addressing
					.setValue(ServiceLocator.getInstance().getEc2Addressing());
			propertiesList.add(addressing);
			providerContext.setCustomProperties(propertiesList);
		//}

		context.setProviderContext(providerContext);

		context = populateContextForLaunch(context, imageId, imageType, key,
				providerId,group);

		try {
			AbstractCommand command = CommandFactory.getCommand(
					CommandCatalog.LaunchCommand, context);
			command.setContext(context);
			command.execute();
			machineType = (VirtualMachineType) context.getResource();
			virtualMachineID = machineType.getProviderVirtualMachineId();
		} catch (PlatformException e) {
			LOGGER.severe("Error occured while launching virtual machine ");
			throw e;
		} catch (Exception e) {
			LOGGER.severe("Error occured while launching virtual machine ");
			return null;
		}
		return virtualMachineID;
	}

	/**
	 * this method populates the required information to the RequestContext
	 * while launching the virtual machine
	 * 
	 * @param context
	 * @param imageId
	 * @param imageType
	 * @param key
	 * @param providerId
	 * @return
	 */
	// populate RequestContext
	private static RequestContext populateContextForLaunch(
			RequestContext context, String imageId, String imageType,
			String key, String providerId,String group) {

		// Setting the ResourceSpecifications
		MachineResourceSpecification machineResource = new MachineResourceSpecification();
		machineResource.setFromMachineImageId(imageId);

		VirtualMachineProductType productType = new VirtualMachineProductType();
		
		Random rand = new Random();
		productType.setName("VM_BaaS_" + rand.nextInt());
		machineResource.setName("VM_BaaS_" + rand.nextInt());
		
		productType.setProductId(imageType);
		machineResource.setProduct(productType);
		machineResource.setWithKeypairId(key);
				
		String []firewalls = new String[1];
		firewalls[0] = group;
		machineResource.setFirewallIds(firewalls);

		machineResource.setDescription("test for private addressing");
		VirtualMachineType machineType = new VirtualMachineType();
		machineType.setResourceSpecification(machineResource);
		context.setResource(machineType);

		return context;
	}

	/**
	 * Incase of virtual machine we need to explicitly set the CPU Count and
	 * RAM size to the RequestContext ProductType. for VMWare the ImageType
	 * contains the CPU count and RAM Size information appended with a colon(:).
	 * i.e 2:2048
	 * 
	 * @param imageType
	 * @param productType
	 * @return
	 */
	private static VirtualMachineProductType populateProductTypeForVMWare(
			String imageType, VirtualMachineProductType productType) {
		String[] types = imageType.split(":");
		Integer cpuCount = 0;
		Integer ramSize = 0;
		if (types.length >= 2) {
			cpuCount = Integer.parseInt(types[0]);
			ramSize = Integer.parseInt(types[1]);
		}
		productType.setCpuCount(cpuCount);
		productType.setRamInMb(ramSize);
		return productType;
	}

	/**
	 * This method sets the CloudProvider Information to the RequestContext
	 * object based on the cloud type
	 * 
	 * @param providerId
	 * @param providerContext
	 * @return
	 */
	private static ProviderContext getCloudProviderContext(String providerId,
			ProviderContext providerContext,String endPoint) {

		 if (providerId.equalsIgnoreCase(ClientConstants.EC2_PROVIDER)) {
			providerContext.setCloudName(ClientConstants.EC2_PROVIDER_NAME);
			providerContext.setProviderName(ClientConstants.AMAZON_PROVIDER_VALUE);
			if(endPoint== null || endPoint.isEmpty()){
				providerContext.setEndpoint(ServiceLocator.getInstance()
					.getAwsUrl());
			}
			else{
				providerContext.setEndpoint(endPoint);
			}
		} 
		return providerContext;
	}

	/**
	 * Populating the security context information which is needed for https
	 * call to infra-services The security jks file location and the password is
	 * present in properties files
	 * 
	 * @param context
	 */
	private static void populateSecurityContext(RequestContext context) {
		SecurityContext securityContext = new SecurityContext();
		securityContext.setKeyStorePath(ServiceLocator.getInstance()
				.getEc2Sslkeypath());
		securityContext.setKeyStorePassword(ServiceLocator.getInstance()
				.getEc2Sslpassword());
		context.setSecurityContext(securityContext);

	}

	/**
	 * This method populates the userContext information which is needed for
	 * Auditing the VM information. Any VM request needs to have at least
	 * projectUserid, ProjectID and service ID. The service id for BaaS is
	 * always 1, so setting the value as 1
	 * 
	 * 
	 * @param context
	 * @param projectUserId
	 * @param projectId
	 */
	private static void populateUserContextInformation(RequestContext context,
			String projectUserId, String projectId) {

		UserContext userContext = new UserContext();
		userContext.setProjectId(projectId);
		userContext.setServiceId(ClientConstants.BaaS_SERVICE_ID);
		userContext.setUserId(projectUserId);
		context.setUserContext(userContext);

	}
	
	public static URL getEc2EndpointUrl(String region) {
        try {
        	URL url = new URL(ServiceLocator.getInstance()
					.getAwsUrl());
        	
			return new URL("http://" + region.replace('_', '-').toLowerCase(Locale.ENGLISH) + "." + url.getHost() + "/");
		} catch (MalformedURLException e) {
			throw new Error(e); // Impossible
		}
    }
}
