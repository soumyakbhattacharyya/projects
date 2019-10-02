/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.cloud;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.cognizant.formit.chain.ChainExecutionContext;
import com.cognizant.formit.entity.infrastructureContext.Context;
import com.cognizant.formit.entity.infrastructureContext.ICloud;
import com.cognizant.formit.entity.infrastructureContext.ITemplate;
import com.cognizant.formit.exception.FormitException;
import com.cognizant.formit.main.AppConstants;
import com.cognizant.formit.model.IDeploymentConfig;
import com.cognizant.jpaas2.command.AbstractCommand;
import com.cognizant.jpaas2.command.CommandCatalog;
import com.cognizant.jpaas2.command.CommandFactory;
import com.cognizant.jpaas2.commons.expection.PlatformException;
import com.cognizant.jpaas2.context.ProviderContext;
import com.cognizant.jpaas2.context.RequestContext;
import com.cognizant.jpaas2.context.SecurityContext;
import com.cognizant.jpaas2.context.UserContext;
import com.cognizant.jpaas2.resource.Resource;
import com.cognizant.jpaas2.resource.StringProperty;
import com.cognizant.jpaas2.resource.VirtualMachineProductType;
import com.cognizant.jpaas2.resource.VirtualMachineType;
import com.cognizant.jpaas2.resourcespecification.MachineResourceSpecification;
import com.jcraft.jsch.JSchException;

/**
 * Utility methods for managing VM resources
 * 
 * @author Cognizant
 */
public class CloudResourceManager {

	private static final Logger l = Logger
			.getLogger(CloudResourceManager.class);
	// TODO : this config should be used for supplying all deployment parameter
	// !!!! REMEMBER
	private IDeploymentConfig config;

	public static CloudResourceManager newInstance(
			IDeploymentConfig deploymentConfig) {
		l.info("cloud resource manager has been initialized with deployment config");
		return new CloudResourceManager(deploymentConfig);
	}

	private CloudResourceManager(IDeploymentConfig deploymentConfig) {
		this.config = deploymentConfig;
	}

	/*
	 * launches a virtual machine and returns remember to verify nullity of the
	 * returned value as that will signify inability of the API to spawn machine
	 * on demand
	 */
	public VirtualMachineType launch(final RequestContext context)
			throws PlatformException {
		// issue launch command and get a vm !!
		l.info("Issueing launch command to spawn a new VM");
		Resource resource = executeCommand(CommandCatalog.LaunchCommand,
				context);
		VirtualMachineType machine = null;
		if (resource instanceof VirtualMachineType) {
			machine = (VirtualMachineType) context.getResource();
		}
		// return
		return null != machine ? machine : null;
	}

	/*
	 * execute a command
	 */
	private Resource executeCommand(CommandCatalog command,
			RequestContext context) throws PlatformException {// get command
		AbstractCommand launchCommand = CommandFactory.getCommand(command,
				context);
		// set context
		launchCommand.setContext(context);
		// execute
		launchCommand.execute();
		// get response
		Resource resource = context.getResource();
		// return
		return resource;

	}

	/*
	 * terminate existing VM
	 */
	public Boolean terminate(final String instanceId,
			IExecutionContext executionContext) {
		try {
			Boolean hasTerminated = Boolean.FALSE;
			RequestContext context = this
					.buildVMwareExecutionContext(executionContext);
			MachineResourceSpecification machineResourceSpecification = new MachineResourceSpecification();
			machineResourceSpecification.setVmId(instanceId);
			machineResourceSpecification.setCaller("API");

			VirtualMachineType machine = new VirtualMachineType();
			machine.setResourceSpecification(machineResourceSpecification);
			context.setResource(machine);

			// we don't care for a resource just give a response
			executeCommand(CommandCatalog.TerminateCommand, context);
			hasTerminated = (Boolean) context.getResponse();
			return hasTerminated;
		} catch (PlatformException ex) {
			l.error(ex.getMessage());
			throw new FormitException.JPaaSPlatformException(ex.getMessage(),
					ex.getCause());
		}

	}

	// boolean isTerminated = false;
	// MachineResourceSpecification machineResource = new
	// MachineResourceSpecification();
	// machineResource.setVmId(virtualMachineID);
	// machineResource.setCaller("Baas");
	// context.setProviderContext(providerContext);
	//
	// VirtualMachineType machineType = new VirtualMachineType();
	// machineType.setResourceSpecification(machineResource);
	// context.setResource(machineType);
	//
	// AbstractCommand launchCommand = CommandFactory.getCommand(
	// CommandCatalog.TerminateCommand, context);
	// launchCommand.setContext(context);
	// try {
	// launchCommand.execute();
	// isTerminated = (Boolean) context.getResponse();
	// } catch (PlatformException e) {
	// e.printStackTrace();
	// }
	// assertTrue(isTerminated);

	/*
	 * get current state of a VM
	 */
	public VirtualMachineType getState(final String instanceId,
			IExecutionContext executionContext) throws PlatformException {
		RequestContext context = this
				.buildVMwareExecutionContext(executionContext);
		MachineResourceSpecification machineResourceSpecification = new MachineResourceSpecification();
		machineResourceSpecification.setVmId(instanceId);
		machineResourceSpecification.setCaller("API");
		VirtualMachineType machine = new VirtualMachineType();
		machine.setResourceSpecification(machineResourceSpecification);
		context.setResource(machine);
		// find the state of the vm
		Resource resource = executeCommand(CommandCatalog.GetMachine, context);
		if (resource instanceof VirtualMachineType) {
			machine = (VirtualMachineType) context.getResource();
		}
		// return
		return null != machine ? machine : null;
	}

	/*
	 * get current state of a VM
	 */
	public VirtualMachineType getState(final String instanceId,
			RequestContext context) throws PlatformException {
		MachineResourceSpecification machineResourceSpecification = new MachineResourceSpecification();
		machineResourceSpecification.setVmId(instanceId);
		machineResourceSpecification.setCaller("API");
		VirtualMachineType machine = new VirtualMachineType();
		machine.setResourceSpecification(machineResourceSpecification);
		context.setResource(machine);
		// find the state of the vm
		Resource resource = executeCommand(CommandCatalog.GetMachine, context);
		if (resource instanceof VirtualMachineType) {
			machine = (VirtualMachineType) context.getResource();
		}
		// return
		return null != machine ? machine : null;
	}

	/**
	 * build the context for communicating with jpaas infra api TODO : Execution
	 * context building will have to be refactored
	 * 
	 * @return
	 */
	public RequestContext buildEucaExecutionContext(
			IExecutionContext executionContext) {

		// TODO : Remove this hardcoding
		ICloud cloud = (((Context) this.config)).getCloud();

		final String virtualMachineServiceURL = cloud
				.getVirtualMachineServiceURL();
		l.info("virtualMachineServiceURL :" + virtualMachineServiceURL);
		final String endpoint = cloud.getUrl();
		l.info("endpoint :" + endpoint);
		final String keypath = cloud.getKeypath();
		l.info("keypath :" + keypath);
		final String keystorePwd = cloud.getKeystorePwd();
		l.info("keystorePwd :" + keystorePwd);
		final String accountNumber = executionContext.accountNumber();
		l.info("accountNumber :" + accountNumber);
		final int serviceId = Integer.parseInt(cloud.getServiceId());
		l.info("serviceId :" + serviceId);

		final String projectId = executionContext.projectId();
		l.info("projectId :" + projectId);
		final String uId = executionContext.uId();
		l.info("uId :" + uId);

		String template = cloud.getTemplates().getTemplate().getMi();
		l.info("template :" + template);
		String vmType = cloud.getTemplates().getTemplate().getType();
		l.info("vmType :" + vmType);
		// Build default context
		RequestContext context = new RequestContext();
		l.info("set service url");
		context.setServiceURL(virtualMachineServiceURL);

		ProviderContext providerContext = context.getProviderContext();
		providerContext.setCloudName(cloud.getName());
		providerContext.setProviderName(cloud.getName());
		providerContext.setAccessPrivate(cloud.getSecretKey().getBytes());
		providerContext.setAccessPublic(cloud.getAccessId().getBytes());
		providerContext.setEndpoint(endpoint);

		// TODO change xml to accomodate account number
		providerContext.setAccountNumber(accountNumber);

		l.info("created provider context");

		// TODO externalize and associate to data considered at runtime
		UserContext userContext = context.getUserContext();
		userContext.setProjectId(projectId);
		userContext.setServiceId(serviceId);
		userContext.setUserId(uId);

		l.info("created user context");

		SecurityContext securityContext = context.getSecurityContext();
		securityContext.setKeyStorePath(keypath);
		securityContext.setKeyStorePassword(keystorePwd);

		l.info("created security context");

		// TODO externalize
		List<StringProperty> propertiesList = new ArrayList<StringProperty>();
		StringProperty addressing = new StringProperty();
		addressing.setKey(AppConstants.ADDRESSING);
		addressing.setValue(AppConstants.PRIVATE);
		propertiesList.add(addressing);
		providerContext.setCustomProperties(propertiesList);

		// TODO externalize
		VirtualMachineProductType productType = new VirtualMachineProductType();
		productType.setName("formit-vm-launch " + System.currentTimeMillis());
		productType.setProductId(vmType);

		// Setting the ResourceSpecifications
		MachineResourceSpecification machineResource = new MachineResourceSpecification();
		machineResource.setFromMachineImageId(template);
		machineResource.setProduct(productType);
		machineResource.setWithKeypairId(cloud.getKeyPairId());
		machineResource.setName(executionContext.reason());
		machineResource.setDescription(executionContext.reason());

		l.info("created machine resource specification");

		VirtualMachineType machineType = new VirtualMachineType();
		machineType.setResourceSpecification(machineResource);

		context.setProviderContext(providerContext);
		context.setResource(machineType);
		return context;

	}

	/**
	 * build the context for communicating with jpaas infra api TODO : Execution
	 * context building will have to be refactored
	 * 
	 * @return
	 */
	public RequestContext buildVMwareExecutionContext(
			IExecutionContext executionContext) {

		// TODO : Remove this hardcoding
		ICloud cloud = (((Context) this.config)).getCloud();

		// instantiation
		RequestContext context = new RequestContext();
		ProviderContext providerContext = context.getProviderContext();
		SecurityContext securityContext = context.getSecurityContext();
		UserContext userContext = context.getUserContext();
		VirtualMachineProductType productType = new VirtualMachineProductType();
		VirtualMachineType machineType = new VirtualMachineType();
		MachineResourceSpecification machineResource = new MachineResourceSpecification();

		providerContext.setCloudName(cloud.getName());
		providerContext.setProviderName(cloud.getProviderName());
		providerContext.setAccessPrivate(cloud.getSecretKey().getBytes());
		providerContext.setAccessPublic(cloud.getAccessId().getBytes());
		providerContext.setEndpoint(cloud.getUrl());
		providerContext.setAccountNumber(executionContext.accountNumber());

		userContext.setProjectId(executionContext.projectId());
		userContext.setServiceId(getIntFromString(cloud.getServiceId()));
		userContext.setUserId(executionContext.uId());
		userContext.setRoleId(getIntFromString(executionContext.rollId()));

		securityContext.setKeyStorePath(cloud.getKeypath());
		securityContext.setKeyStorePassword(cloud.getKeystorePwd());

		// TODO : ensure that template is being chosen which matches the task
		// label
		ITemplate template = this.getTemplate(cloud);

		// String vmName = "VM_APP_ENVIRONMENT_BOOTSTRAPPER-" + getTimeStamp();
		String vmName = "CloudSet_RDECK_SLAVE_" + UUID.randomUUID().toString();
		l.debug(vmName);

		if (cloud.getName().contains(AppConstants.VMWARE_CLOUD_NAME)) {
			productType.setCpuCount(getIntFromString(template.getCpuCount()));
			productType.setProductId(template.getProductId());
			productType.setName(vmName);
			productType.setRamInMb(getIntFromString(template.getRamInMb()));
		} else {
			productType.setProductId(template.getProductId());
			productType.setName(vmName);
		}

		machineResource.setFromMachineImageId(template.getMi());
		machineResource.setProduct(productType);
		machineResource.setWithKeypairId(cloud.getKeyPairId());
		machineResource.setName(vmName);
		machineResource.setDescription(executionContext.description());

		machineType.setResourceSpecification(machineResource);

		context.setResource(machineType);
		context.setServiceURL(cloud.getVirtualMachineServiceURL());
		return context;
	}

	/**
	 * Get instance id of the virtual machines
	 * 
	 * @param machines
	 * @return
	 */
	public List<String> getInstanceIds(List<VirtualMachineType> machines) {
		List<String> list = new ArrayList<String>();
		for (VirtualMachineType machineType : machines) {
			list.add(machineType.getProviderVirtualMachineId());
		}
		return list;
	}

	/**
	 * get current time in string format
	 * 
	 * @return
	 */
	public String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * gets template for the VMs to be spawned TODO : ensure that template is
	 * being chosen that matches the task label
	 * 
	 * @return
	 */
	public ITemplate getTemplate(final ICloud cloud) {
		return cloud.getTemplates().getTemplate();
	}

	/**
	 * converts a string to integer
	 * 
	 * @param str
	 * @return
	 */
	private Integer getIntFromString(final String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException exception) {
			l.error(exception.getMessage());
			throw exception;
		}

	}

	/**
	 * launch required number of VMs and return
	 * 
	 * @param nvm
	 * @param iExecutionContext
	 * @return
	 */
	public List<VirtualMachineType> getResources(int nvm,
			IExecutionContext iExecutionContext) {
		List<VirtualMachineType> machines = new ArrayList<VirtualMachineType>();
		try {
			// launch VMs : Remember to rebuild the execustion context before
			// issueing a new launch command
			for (int iteration = 0; iteration < nvm; iteration++) {
				RequestContext context = this
						.buildVMwareExecutionContext(iExecutionContext);
				machines.add(this.launch(context));
			}
		} catch (PlatformException ex) {
			l.error(ex.getMessage());
			throw new FormitException.JPaaSPlatformException(ex.getMessage(),
					ex.getCause());
		}
		return machines;
	}

	/**
	 * refresh resource states - primarily to get the IP address of the VMs by
	 * this time
	 * 
	 * @param iExecutionContext
	 * @return
	 * @throws InterruptedException
	 */
	public List<VirtualMachineType> refreshResources(
			List<VirtualMachineType> machines,
			ChainExecutionContext chainContext) {
		try {
			// refresh VMs : Remember to rebuild the execustion context before
			// issueing a new launch command

			IExecutionContext iExecutionContext = chainContext
					.getExecutionContext();

			List<VirtualMachineType> machinesWithStateRefreshed = new ArrayList<VirtualMachineType>(
					0);
			for (VirtualMachineType machineType : machines) {
				RequestContext context = this
						.buildVMwareExecutionContext(iExecutionContext);
				int counter = 30;
				do {
					VirtualMachineType machineWithStateRefreshed = this
							.getState(
									machineType.getProviderVirtualMachineId(),
									context);

					String userID = chainContext.getDeploymentConfiguration()
							.getProperty(AppConstants.MACHINE_USER_ID);
					String port = chainContext.getDeploymentConfiguration()
							.getProperty(AppConstants.GENERAL_PURPOSE_SSHPORT);					
					int portNumber = 22;
					// getting the priv key from the project properties
					String priv_Key_File = chainContext.getRundeckProject().getSshKeyPath();
					if (port != null) {
						try {
							portNumber = Integer.parseInt(port);
						} catch (Exception e) {
							l.error("The port specified is not correct, Using the default port 22");
						}
					}

					if (null != machineWithStateRefreshed
							.getPrivateDnsAddress()
							&& !machineWithStateRefreshed
									.getPrivateDnsAddress().isEmpty()
							&& checkIfMachineIsSSHABLE(userID,
									machineWithStateRefreshed
											.getPrivateDnsAddress(),
									portNumber, priv_Key_File)) {
						machinesWithStateRefreshed
								.add(machineWithStateRefreshed);
						break;
					} else {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException ex) {
							l.error(ex.getMessage());
							throw new FormitException.JPaaSPlatformException(
									ex.getMessage(), ex.getCause());
						}
					}
				} while (counter-- > 0);
			}
			machines = machinesWithStateRefreshed;
		} catch (PlatformException ex) {
			l.error(ex.getMessage());
			throw new FormitException.JPaaSPlatformException(ex.getMessage(),
					ex.getCause());
		}
		l.info("Returning machine as " + machines);
		return machines;
	}

	private boolean checkIfMachineIsSSHABLE(String userID, String host,
			int port, String privKey) {

		l.info("Checking if machine is SSHABLE");
		try {
			l.info("Passing userId as " + userID);
			l.info("Passing userId as " + host);
			l.info("Passing userId as " + port);
			Thread.sleep(5000);
			SSHUtil.openSession(userID, host, port, 0, privKey);
		} catch (JSchException e) {
			l.error("Machine is not sshable " + e.getMessage());
			return false;

		} catch (InterruptedException e) {
			l.error("InterruptedException " + e.getMessage());
		}
		l.info("Able to ssh to the machine");

		return true;
	}
}
