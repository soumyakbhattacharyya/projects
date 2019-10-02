package com.cognizant.formit.util.cloud;

import com.cognizant.formit.AbstractTest;
import com.cognizant.formit.main.Executor;
import com.cognizant.formit.model.IDeploymentConfig;
import com.cognizant.formit.main.SequentialExecutor;
import com.cognizant.formit.model.DeploymentConfigDecorator;
import com.cognizant.formit.model.DeploymentProfileFactory;
import com.cognizant.formit.util.cloud.CloudResourceManager.*;
import com.cognizant.jpaas2.command.AbstractCommand;
import com.cognizant.jpaas2.command.CommandCatalog;
import com.cognizant.jpaas2.command.CommandFactory;
import com.cognizant.jpaas2.commons.expection.PlatformException;
import com.cognizant.jpaas2.context.ProviderContext;

import com.cognizant.jpaas2.context.RequestContext;
import com.cognizant.jpaas2.context.SecurityContext;
import com.cognizant.jpaas2.context.UserContext;
import com.cognizant.jpaas2.resource.StringProperty;
import com.cognizant.jpaas2.resource.VirtualMachineProductType;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.BDDMockito.*;

import com.cognizant.jpaas2.resource.VirtualMachineType;
import com.cognizant.jpaas2.resource.VmStateType;
import com.cognizant.jpaas2.resourcespecification.MachineResourceSpecification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import static org.fest.assertions.api.Assertions.assertThat; // main one
import static org.fest.assertions.api.Assertions.atIndex; // for List assertion
import static org.fest.assertions.api.Assertions.entry;  // for Map assertion
import static org.fest.assertions.api.Assertions.extractProperty; // for Iterable/Array assertion
import static org.fest.assertions.api.Assertions.fail; // use when making exception tests
import static org.fest.assertions.api.Assertions.failBecauseExceptionWasNotThrown; // idem
import static org.fest.assertions.api.Assertions.filter; // for Iterable/Array assertion
import static org.fest.assertions.api.Assertions.offset; // for floating number assertion
import static org.fest.assertions.api.Assertions.anyOf; // use with Condition

public class CloudResourceManagerTest extends AbstractTest {

//	@Test
//	public void shouldSpawnEucaVM() throws SAXException, InstantiationException, ParserConfigurationException, IllegalAccessException, IOException, ClassNotFoundException, ConfigurationException, JAXBException, PlatformException {

//		Executor deployer = super.getDeployer();
//		// build configuration
//		DeploymentConfigDecorator configDecorator = DeploymentConfigDecorator.newInstance(DeploymentProfileFactory.ProfileSourceType.PROPERTY_FILE, deployer.getProfileFilePath());
//		configDecorator.registerPlusDecorateDeploymentConfig(deployer.getXmlFilePath(), deployer.getXsdFilePath());
//		IDeploymentConfig deploymentConfig = configDecorator.unmarshalDecoratedConfig(deployer.getSchemaPkg(), deployer.getObjFactory());
//
//		// build context
//		CloudResourceManager cloudResourceManager = CloudResourceManager.newInstance(deploymentConfig);
//		RequestContext context = cloudResourceManager.buildEucaExecutionContext(new IExecutionContext() {
//			@Override
//			public String accountNumber() {
//				return "13015590742642677901";
//			}
//
//			@Override
//			public String projectId() {
//				return "P-4";
//			}
//
//			@Override
//			public String uId() {
//				return "335393";
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
//				throw new UnsupportedOperationException("Not supported yet.");
//			}
//
//			@Override
//			public String serviceId() {
//				throw new UnsupportedOperationException("Not supported yet.");
//			}
//		});
//
//		// invoke launch api and validate if the machine is in running state
//		VirtualMachineType machine = cloudResourceManager.launch(context);
//		assertThat(machine).isNotNull();
//		String vmId = machine.getProviderVirtualMachineId();
//		assertThat(vmId).isNotEmpty();
//		machine = cloudResourceManager.getState(vmId, context);
//		System.out.println(vmId);
//		assertThat(machine.getCurrentState()).isEqualTo(VmStateType.RUNNING);
//		// if the machine is in running state .. shut it down gracefully
//		Boolean hasTerminated = cloudResourceManager.terminate(vmId, null);
//		assertThat(hasTerminated).isEqualTo(Boolean.TRUE);
//	}
}
