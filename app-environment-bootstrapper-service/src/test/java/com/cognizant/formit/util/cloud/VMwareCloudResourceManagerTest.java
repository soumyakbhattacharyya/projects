/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.cloud;

import com.cognizant.formit.AbstractTest;
import com.cognizant.formit.main.Executor;
import com.cognizant.formit.model.IDeploymentConfig;
import com.cognizant.formit.main.SequentialExecutor;
import com.cognizant.formit.model.DeploymentConfigDecorator;
import com.cognizant.formit.model.DeploymentProfileFactory;
import com.cognizant.jpaas2.command.AbstractCommand;
import com.cognizant.jpaas2.command.CommandCatalog;
import com.cognizant.jpaas2.command.CommandFactory;
import com.cognizant.jpaas2.commons.expection.PlatformException;
import com.cognizant.jpaas2.context.ProviderContext;
import com.cognizant.jpaas2.context.RequestContext;
import com.cognizant.jpaas2.context.SecurityContext;
import com.cognizant.jpaas2.context.UserContext;
import com.cognizant.jpaas2.resource.VirtualMachineProductType;
import com.cognizant.jpaas2.resource.VirtualMachineType;
import com.cognizant.jpaas2.resource.VmStateType;
import com.cognizant.jpaas2.resourcespecification.MachineResourceSpecification;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.configuration.ConfigurationException;
import static org.fest.assertions.api.Assertions.assertThat; // main one
import static org.fest.assertions.api.Assertions.atIndex; // for List assertion
import static org.fest.assertions.api.Assertions.entry;  // for Map assertion
import static org.fest.assertions.api.Assertions.extractProperty; // for Iterable/Array assertion
import static org.fest.assertions.api.Assertions.fail; // use when making exception tests
import static org.fest.assertions.api.Assertions.failBecauseExceptionWasNotThrown; // idem
import static org.fest.assertions.api.Assertions.filter; // for Iterable/Array assertion
import static org.fest.assertions.api.Assertions.offset; // for floating number assertion
import static org.fest.assertions.api.Assertions.anyOf; // use with Condition
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author Cognizant
 */
public class VMwareCloudResourceManagerTest extends AbstractTest {

//	/**
//	 * VMware related
//	 */
//	@Test
//	public void shouldSpawnVMwareVM() throws SAXException, InstantiationException, ParserConfigurationException, IllegalAccessException, IOException, ClassNotFoundException, ConfigurationException, JAXBException, PlatformException {
//
//		Executor deployer = super.getDeployer();
//		// build configuration
//		DeploymentConfigDecorator configDecorator = DeploymentConfigDecorator.newInstance(DeploymentProfileFactory.ProfileSourceType.PROPERTY_FILE, deployer.getProfileFilePath());
//		configDecorator.registerPlusDecorateDeploymentConfig(deployer.getXmlFilePath(), deployer.getXsdFilePath());
//		IDeploymentConfig deploymentConfig = configDecorator.unmarshalDecoratedConfig(deployer.getSchemaPkg(), deployer.getObjFactory());
//
//		// build context
//		CloudResourceManager cloudResourceManager = CloudResourceManager.newInstance(deploymentConfig);
//		RequestContext context = cloudResourceManager.buildVMwareExecutionContext(new IExecutionContext() {
//			@Override
//			public String accountNumber() {
//				return "654321";
//			}
//
//			@Override
//			public String projectId() {
//				return "P-4";
//			}
//
//			@Override
//			public String uId() {
//				return "272959";
//			}
//
//			@Override
//			public String reason() {
//				return "Unit-Test";
//			}
//
//			@Override
//			public String description() {
//				return "Test purpose only";
//			}
//
//			@Override
//			public String rollId() {
//				return "0";
//			}
//
//			@Override
//			public String serviceId() {
//				return "1";
//			}
//		});
//
//		// invoke launch api and validate if the machine is in running state
//		VirtualMachineType machine = cloudResourceManager.launch(context);
//		assertThat(machine).isNotNull();
//		String vmId = machine.getProviderVirtualMachineId();
//		assertThat(vmId).isNotEmpty();
//		machine = cloudResourceManager.getState(vmId, context);
//		assertThat(machine.getCurrentState()).isEqualTo(VmStateType.RUNNING);
//		// if the machine is in running state .. shut it down gracefully
//		Boolean hasTerminated = cloudResourceManager.terminate(vmId, null);
//		assertThat(hasTerminated).isEqualTo(Boolean.TRUE);
//	}
////    public void test_LaunchVirtualMachineVmware() {
////
////        RequestContext context = populateContextForLaunchVMware(getContext());
////        AbstractCommand launchCommand = CommandFactory.getCommand(
////                CommandCatalog.LaunchCommand, context);
////        String virtualMachineID = "";
////        
////
////        launchCommand.setContext(context);
////        try {
////            launchCommand.execute();
////            VirtualMachineType machineType = new VirtualMachineType();
////            machineType = (VirtualMachineType) context.getResource();
////            virtualMachineID = machineType.getProviderVirtualMachineId();
////            System.out.println("RESULT IS " + virtualMachineID);
////        } catch (PlatformException e) {
////            System.out.println(e);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        assertThat(virtualMachineID).isNotEmpty();
////    }
////
////    private RequestContext populateContextForLaunchVMware(RequestContext context) {
////        // populate RequestContext
////        // Setting the ResourceSpecifications
////        MachineResourceSpecification machineResource = new MachineResourceSpecification();
////        machineResource.setFromMachineImageId("421c38fe-ec6d-59b8-9b0e-e702785498fa");
////
////        VirtualMachineProductType productType = new VirtualMachineProductType();
////        productType.setCpuCount(2);
////        productType.setProductId("2:1024");
////        // productType.setDiskSizeInGb(1);
////        productType.setName("vm-launch11358989898");
////        productType.setRamInMb(1024);
////
////        machineResource.setProduct(productType);
////        machineResource.setWithKeypairId("jpaas_vm");
////        machineResource.setName("vm-launch11358989898");
////        machineResource.setDescription("test for private addressing");
////        VirtualMachineType machineType = new VirtualMachineType();
////        machineType.setResourceSpecification(machineResource);
////        context.setResource(machineType);
////        return context;
////    }
////    @Test
////    public void test_getVirtualMachineVmware() {
////
////        // Populate Information to terminate the instance
////        RequestContext context = populateContextForLaunchVMware(getContext());
////        String runningStatus = "RUNNING";
////        MachineResourceSpecification machineResource = new MachineResourceSpecification();
////        machineResource.setVmId("501cd68c-cf37-e82e-672d-fe1b3bbc69b9");
////        machineResource.setCaller("Baas");
////        VirtualMachineType machineType = new VirtualMachineType();
////        machineType.setResourceSpecification(machineResource);
////        context.setResource(machineType);
////
////        AbstractCommand launchCommand = CommandFactory.getCommand(
////                CommandCatalog.GetMachine, context);
////        launchCommand.setContext(context);
////        try {
////            launchCommand.execute();
////        } catch (PlatformException e) {
////            e.printStackTrace();
////        }
////
////        System.out.println("******machineType status*******"
////                + machineType.getCurrentState().toString());
////        assertThat(machineType.getPublicDnsAddress()).isNotEmpty();
////    }
//////
//////    public void test_getVirtualMachineListVmware() {
//////
//////        // Populate Information to terminate the instance
//////        //String runningStatus = "RUNNING";		
//////        MachineResourceSpecification machineResource = new MachineResourceSpecification();
//////        //machineResource.setVmId(virtualMachineID);
//////        machineResource.setCaller("Baas");
//////        VirtualMachineType machineType = new VirtualMachineType();
//////        machineType.setResourceSpecification(machineResource);
//////        context.setResource(machineType);
//////
//////        AbstractCommand launchCommand = CommandFactory.getCommand(
//////                CommandCatalog.ListVirtualMachine, context);
//////        launchCommand.setContext(context);
//////        try {
//////            launchCommand.execute();
//////        } catch (PlatformException e) {
//////            e.printStackTrace();
//////        }
//////
//////        System.out.println("******machineType status*******"
//////                + context.getResponse());
//////        assertNotNull(context.getResponse());
//////    }
//////
//////    public void test_getVirtualMachineListFROMDBVmware() {
//////
//////        // Populate Information to terminate the instance
//////        //String runningStatus = "RUNNING";		
//////        MachineResourceSpecification machineResource = new MachineResourceSpecification();
//////        //machineResource.setVmId(virtualMachineID);
//////        machineResource.setCaller("Baas");
//////        VirtualMachineType machineType = new VirtualMachineType();
//////        machineType.setResourceSpecification(machineResource);
//////        context.setResource(machineType);
//////
//////        AbstractCommand launchCommand = CommandFactory.getCommand(
//////                CommandCatalog.GetListVirtualMachineFromDB, context);
//////        launchCommand.setContext(context);
//////        try {
//////            launchCommand.execute();
//////        } catch (PlatformException e) {
//////            e.printStackTrace();
//////        }
//////
//////        System.out.println("******machineType status*******"
//////                + context.getResponse());
//////        assertNotNull(context.getResponse());
//////    }
//////
//////    public void test_TerminateVirtualMachineVmware() {
//////        boolean isTerminated = false;
//////        MachineResourceSpecification machineResource = new MachineResourceSpecification();
//////        machineResource.setVmId("501c961e-660a-f7cf-8395-dac48fe66bdc");
//////        machineResource.setCaller("Baas");
//////        VirtualMachineType machineType = new VirtualMachineType();
//////        machineType.setResourceSpecification(machineResource);
//////        context.setResource(machineType);
//////
//////        AbstractCommand launchCommand = CommandFactory.getCommand(
//////                CommandCatalog.TerminateCommand, context);
//////        launchCommand.setContext(context);
//////
//////        try {
//////            launchCommand.execute();
//////        } catch (PlatformException e) {
//////            e.printStackTrace();
//////        }
//////        isTerminated = (Boolean) context.getResponse();
//////        assertTrue(isTerminated);
//////    }
////
////    /**
////     * Context information which will be externalized
////     */
////    public RequestContext getContext() {
////        //static String virtualMachineServiceURL = "https://10.227.88.95:8243/services/VirtualMachineSoapServiceService";
////        String virtualMachineServiceURL = "https://10.227.125.43:8243/services/VirtualMachineSoapServiceService";
////        //static String virtualMachineServiceURL = "http://10.227.125.42:8580/jpaas2infra-webapp-1.0/services/VirtualMachineServiceGateway";
////        RequestContext context = new RequestContext();
////        ProviderContext providerContext = context.getProviderContext();
////        SecurityContext securityContext = context.getSecurityContext();
////        UserContext userContext = context.getUserContext();       
////
////        context.setServiceURL(virtualMachineServiceURL);
////        providerContext.setCloudName("vSphere");
////        /*providerContext.setAccessPrivate("vmware".getBytes());
////         providerContext.setAccessPublic("root".getBytes());*/
////        providerContext.setProviderName("vmware");
////        providerContext.setAccessPrivate("J111111#".getBytes());
////        providerContext.setAccessPublic("Sjpaasclouduser".getBytes());
////        providerContext.setEndpoint("https://10.242.196.192/sdk");
////        providerContext.setAccountNumber(""
////                + "654321");
////        userContext.setProjectId("P-4");
////        userContext.setServiceId(1);
////        userContext.setUserId(272959);
////        userContext.setRoleId(0);
////        
////        SecurityContextBuilder scb = new SecurityContextBuilder.Builder("D:/sb/JPaaS2.0 - keys - (ppk-priv)/JPaaS-Integration/Cognizant-JPaaS2-dev-1.0.jks", "Cognizant-JPaaS").build();
////        
////        securityContext.setKeyStorePath(scb.getKeyStoreFilePath());
////        securityContext.setKeyStorePassword(scb.getKeyStorePassword());
////
////        /*securityContext.setKeyStorePath("D:/Swastika/Installers/BaaS/MASTER-HOME/jpaas.jks");
////         securityContext.setKeyStorePassword("jpaasclient");*/
////
////        return context;
////
////    }
}
