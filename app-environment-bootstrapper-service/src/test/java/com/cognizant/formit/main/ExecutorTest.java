package com.cognizant.formit.main;

import com.cognizant.formit.AbstractTest;
import com.cognizant.formit.model.IDeploymentConfig;
import com.cognizant.formit.entity.infrastructureContext.Context;
import com.cognizant.formit.entity.infrastructureContext.IProject;
import com.cognizant.formit.main.SequentialExecutor.*;
import com.cognizant.formit.model.DeploymentConfigDecorator;
import com.cognizant.formit.model.DeploymentProfileFactory;
import com.cognizant.formit.util.cloud.IExecutionContext;
import com.cognizant.formit.model.RNode;
import com.cognizant.formit.model.RNodeFactory;
import com.cognizant.formit.util.cloud.CloudResourceManager;
import com.cognizant.formit.util.db.DBHelper;
import com.cognizant.formit.util.db.IConnectionPoolInitializer;
import com.cognizant.formit.util.file.FileHelper;
import com.cognizant.formit.util.rundeck.IRundeckConnectionInitializer;
import com.cognizant.formit.util.rundeck.RundeckHelper;
import com.cognizant.jpaas2.context.RequestContext;
import com.cognizant.jpaas2.resource.VirtualMachineType;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.Project;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.BDDMockito.*;

import static org.fest.assertions.api.Assertions.assertThat; // main one
import static org.fest.assertions.api.Assertions.atIndex; // for List assertion
import static org.fest.assertions.api.Assertions.entry;  // for Map assertion
import static org.fest.assertions.api.Assertions.extractProperty; // for Iterable/Array assertion
import static org.fest.assertions.api.Assertions.fail; // use when making exception tests
import static org.fest.assertions.api.Assertions.failBecauseExceptionWasNotThrown; // idem
import static org.fest.assertions.api.Assertions.filter; // for Iterable/Array assertion
import static org.fest.assertions.api.Assertions.offset; // for floating number assertion
import static org.fest.assertions.api.Assertions.anyOf; // use with Condition
import org.rundeck.api.RundeckClient;
import org.rundeck.api.domain.RundeckProject;

public class ExecutorTest extends AbstractTest {

//	@Test
//	public void shouldDeployValidSampleBlueprint() throws Exception {
//
//		// Deployment starts with grabbing an instance of SequentialExecutor
//		Executor target = super.getDeployer();
//
//		// read dependency xml
//		File buildFile = FileHelper.getFile(target.getBlueprintConfigFilePath());
//		assertThat(buildFile).isFile();
//
//		// initiate project
//		Project p = target.initiateProject(buildFile);
//		assertThat(p).isNotNull();
//
//		// estimate number of vms required
//		final int nvm = target.getNumberOfRequiredVMs();
//		assertThat(nvm).isEqualTo(2);
//
//		// spawn estimated number of vms
//		// but first create a deployment config that the cloud resource manager can use
//		DeploymentConfigDecorator configDecorator = DeploymentConfigDecorator.newInstance(DeploymentProfileFactory.ProfileSourceType.PROPERTY_FILE, target.getProfileFilePath());
//		configDecorator.registerPlusDecorateDeploymentConfig(target.getXmlFilePath(), target.getXsdFilePath());
//		IDeploymentConfig deploymentConfig = configDecorator.unmarshalDecoratedConfig(target.getSchemaPkg(), target.getObjFactory());
//		// this deployment config now on will drive rest of the deployment process                
//		CloudResourceManager cloudResourceManager = CloudResourceManager.newInstance(deploymentConfig);
//
//		// machines are spawned right now and may be in process to get public DNS address
//		List<VirtualMachineType> machines = new ArrayList<VirtualMachineType>();
//
//		// machinesWithStaterefreshed are exactly same machines with public DNS address
//		List<VirtualMachineType> machinesWithStateRefreshed = new ArrayList<VirtualMachineType>();
//
//		for (int iteration = 0; iteration < nvm; iteration++) {
//			// launch VMs : Remember to rebuild the execustion context before issueing a new launch command
//			RequestContext context = cloudResourceManager.buildVMwareExecutionContext(new IExecutionContext() {
//				@Override
//				public String accountNumber() {
//					return "13015590742642677901";
//				}
//
//				@Override
//				public String projectId() {
//					return "P-4";
//				}
//
//				@Override
//				public String uId() {
//					return "335393";
//				}
//
//				@Override
//				public String reason() {
//					return "Unit-Test";
//				}
//
//				@Override
//				public String description() {
//					return "Test purpose only";
//				}
//
//				@Override
//				public String rollId() {
//					throw new UnsupportedOperationException("Not supported yet.");
//				}
//
//				@Override
//				public String serviceId() {
//					throw new UnsupportedOperationException("Not supported yet.");
//				}
//			});
//			machines.add(cloudResourceManager.launch(context));
//		}
//		assertThat(machines).hasSize(nvm);
//
//		Set<String> setOfPublicDNS = new HashSet<String>();
//
//		// validate that the VMs have unique public addresses
//		for (int iteration = 0; iteration < nvm; iteration++) {
//			// launch VMs : Remember to rebuild the execustion context
//			RequestContext context = cloudResourceManager.buildVMwareExecutionContext(new IExecutionContext() {
//				@Override
//				public String accountNumber() {
//					return "13015590742642677901";
//				}
//
//				@Override
//				public String projectId() {
//					return "P-4";
//				}
//
//				@Override
//				public String uId() {
//					return "335393";
//				}
//
//				@Override
//				public String reason() {
//					return "Unit-Test";
//				}
//
//				@Override
//				public String description() {
//					return "Test purpose only";
//				}
//
//				@Override
//				public String rollId() {
//					throw new UnsupportedOperationException("Not supported yet.");
//				}
//
//				@Override
//				public String serviceId() {
//					throw new UnsupportedOperationException("Not supported yet.");
//				}
//			});
//			VirtualMachineType machine = machines.get(iteration);
//			String vmId = machine.getProviderVirtualMachineId();
//			VirtualMachineType machineWithStateRefreshed = cloudResourceManager.getState(vmId, context);
//			machinesWithStateRefreshed.add(machineWithStateRefreshed);
//			setOfPublicDNS.add(machineWithStateRefreshed.getPublicDnsAddress());
//		}
//
//		assertThat(setOfPublicDNS).doesNotHaveDuplicates();
//
//		// associate the vms with task tags .. 
//		Set<RNode> nodes = target.produceRNode(machinesWithStateRefreshed);
//		// assert that we have unique nodes only
//		assertThat(nodes).hasSize(nvm);
//		// now verify 
//		// iterate the list, get individual node, collect their tag, split it against "#"
//		// collect last element of the resultant string array
//		// verify that the instance is one of the newly spawned one
//		List<String> list = cloudResourceManager.getInstanceIds(machines);
//		for (RNode node : nodes) {
//			String tag = node.getTags();
//			assertThat(tag).isNotEmpty();
//			String[] parts = tag.split(AppConstants.HASH);
//			assertThat(parts.length).isGreaterThan(1);
//			int numberOfParts = parts.length;
//			String possibleInstanceId = parts[numberOfParts - 1];
//			assertThat(list).contains(possibleInstanceId);
//		}
//
//		// ok .. now we have all RNodes properly initialized and it means that we are ready to insert the resource 
//		// definition        
//		DBHelper dBHelper = DBHelper.newInstance(new IConnectionPoolInitializer() {
//			private String dbUrl = "jdbc:mysql://10.227.125.41/rundeckdb";
//			private String dbDriverClass = "com.mysql.jdbc.Driver";
//			private String dbUid = "root";
//			private String dbPwd = "mysql";
//			private int dbInitialPoolSize = 10;
//			private int dbMinPoolSize = 5;
//			private int dbMaxPoolSize = 20;
//			private int dbMaxIdleTime = 3600;
//
//			public String dbUrl() {
//				return dbUrl;
//			}
//
//			public String dbDriverClass() {
//				return dbDriverClass;
//			}
//
//			public String dbUid() {
//				return dbUid;
//			}
//
//			public String dbPwd() {
//				return dbPwd;
//			}
//
//			public int dbMaxPoolSize() {
//				return dbMaxPoolSize;
//			}
//
//			public int dbMinPoolSize() {
//				return dbMinPoolSize;
//			}
//
//			public int dbInitialPoolSize() {
//				return dbInitialPoolSize;
//			}
//
//			public int dbMaxIdleTime() {
//				return dbMaxIdleTime;
//			}
//		});
//		// push resource definition to db
//		List<RNode> insertables = new ArrayList<RNode>();
//		Iterator<RNode> it = nodes.iterator();
//		int numberOfInsertions = 0;
//		while (it.hasNext()) {
//			insertables.add(it.next());
//		}
//		numberOfInsertions = dBHelper.create(insertables);
//		assertThat(numberOfInsertions).isEqualTo(insertables.size());
//		// database has been updated with the resource definition        
//		/**
//		 * ******
//		 */
//		/*
//		 * find jobs for the given project
//		 * load profile properties
//		 * tag jobs to VMs where they will run
//		 * fire driver job
//		 */
//		RundeckHelper rundeckHelper = target.getRundeckHelper();
//		RundeckClient rundeckClient = rundeckHelper.connect(new IRundeckConnectionInitializer() {
//			@Override
//			public String serverEndpoint() {
//				return "http://10.227.125.17:4440";
//			}
//
//			@Override
//			public String uid() {
//				return "admin";
//			}
//
//			@Override
//			public String pwd() {
//				return "admin";
//			}
//		});
//		// get project from rundeck & assert that it is same as that mentioned in 
//		// the infra definition & blueprint definition
//		IProject rundeckProjectFromEnvironmentProfile = null;
//		if (deploymentConfig instanceof Context) {
//			rundeckProjectFromEnvironmentProfile = (((Context) deploymentConfig)).getProject();
//		}
//		String name = rundeckProjectFromEnvironmentProfile.getName();
//		String resourcePath = rundeckProjectFromEnvironmentProfile.getResource();
//		String sshKeyPath = rundeckProjectFromEnvironmentProfile.getSshKeypath();
//
//		RundeckProject rundeckProject = rundeckClient.getProject(name);
//
//		// check rundeck has same definition
//		assertThat(rundeckProject.getName().equals(name));
//
//		// check blueprint has the definition ... by name mathing as of now
//		assertThat(p.getName().equals(name));
//		// if we have reached here ... rundeck project == blueprint project == contexted project
//
//		// get jobs from rundeck for the given project and tag them  
//		// get a ant target ... get corresponding job ... get RNode for this job ... update job
//		target.tagJobs();
//		// verify        
//
//		// assert that all jobs corresponding to ANT targets are updated to required node label
//
//		// load project with the properties found at runtime
//
//
//		Map<String, String> profile = configDecorator.getDeploymentProfile().get();
//
//		target.loadRuntimeProperties(p, profile);
//
//		p.executeTarget(p.getDefaultTarget());
//
//	}
//
//	@Test
//	public void shouldNotDeployInvalidSampleBlueprint() throws Exception {
//		// SequentialExecutor target = new SequentialExecutor();
//		// given
//		// e.g. : given(mocked.called()).willReturn(1);
//		// when
//		// target.deploy();
//		// then
//		// e.g. : verify(mocked).called();
//		// TODO : complete this major test before making public release
//		assertThat(2 == 2);
//	}
}
