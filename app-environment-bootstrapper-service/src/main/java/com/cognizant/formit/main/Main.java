/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.main;

import com.cognizant.formit.exception.FormitException;
import com.cognizant.formit.model.DeploymentProfileFactory;
import com.cognizant.formit.model.IDeploymentConfig;
import com.cognizant.formit.model.RNode;
import com.cognizant.formit.util.cloud.IExecutionContext;
import com.cognizant.formit.util.db.IConnectionPoolInitializer;
import com.cognizant.formit.util.file.FileHelper;
import com.cognizant.formit.util.rundeck.IRundeckConnectionInitializer;
import com.cognizant.jpaas2.resource.VirtualMachineType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.mockito.internal.util.ListUtil;

/**
 * stand - alone entry point for the program
 *
 * @author Cognizant
 */
public class Main {

	private static Logger l = Logger.getLogger(Main.class);
	private static AtomicInteger checkpointId = new AtomicInteger();

	public static void main(String... args) throws InstantiationException, IllegalAccessException, InterruptedException {
//
//		List<VirtualMachineType> machines = new ArrayList<VirtualMachineType>(1);
//		final Executor executor = ExecutorFactory.newExecutor(ExecutorFactory.ExecutorType.SEQUENTIAL,UUID.randomUUID().toString());
//		try {
//			// launch sequence                     
//			// initialize executor TODO : flesh out initializeExecutor()            
//			executor.setBlueprintConfigFilePath("src/main/resources/xml/blueprint.xml");
//			executor.setProfileFilePath("src/main/resources/properties/deploymentProfile.properties");
//			executor.setXmlFilePath("src/main/resources/xml/infrastructureCntx.xml");
//			executor.setXsdFilePath("src/main/resources/xsd/infrastructureCntx.xsd");
//			executor.setSchemaPkg("com.cognizant.formit.entity.infrastructureContext");
//			executor.setObjFactory("com.cognizant.formit.entity.infrastructureContext.ObjectFactory");
//			executor.setConnectionPoolInitializer(new IConnectionPoolInitializer() {
//				private String dbUrl = "jdbc:mysql://10.227.125.41/rundeckdb";
//				private String dbDriverClass = "com.mysql.jdbc.Driver";
//				private String dbUid = "root";
//				private String dbPwd = "mysql";
//				private int dbInitialPoolSize = 10;
//				private int dbMinPoolSize = 5;
//				private int dbMaxPoolSize = 20;
//				private int dbMaxIdleTime = 3600;
//
//				public String dbUrl() {
//					return dbUrl;
//				}
//
//				public String dbDriverClass() {
//					return dbDriverClass;
//				}
//
//				public String dbUid() {
//					return dbUid;
//				}
//
//				public String dbPwd() {
//					return dbPwd;
//				}
//
//				public int dbMaxPoolSize() {
//					return dbMaxPoolSize;
//				}
//
//				public int dbMinPoolSize() {
//					return dbMinPoolSize;
//				}
//
//				public int dbInitialPoolSize() {
//					return dbInitialPoolSize;
//				}
//
//				public int dbMaxIdleTime() {
//					return dbMaxIdleTime;
//				}
//			});
//			executor.setRundeckConnectionInitializer(new IRundeckConnectionInitializer() {
//				@Override
//				public String serverEndpoint() {
//					return "http://10.227.125.17:4440";
//				}
//
//				@Override
//				public String uid() {
//					return "admin";
//				}
//
//				@Override
//				public String pwd() {
//					return "admin";
//				}
//			});
//			l.debug(prefix() + "executor initialized");
//			// validate blueprint
//			boolean hasValidBluePrint = executor.hasValidBlueprint();
//			l.debug(prefix() + "is blueprint valid " + hasValidBluePrint);
//			// estimate number of vms required
//			final int nvm = executor.getNumberOfRequiredVMs();
//			l.debug(prefix() + "estimated number of VM required are " + nvm);
//			// initialize deployment config
//			IDeploymentConfig deploymentConfig = executor.initializeDeploymentConfig(DeploymentProfileFactory.ProfileSourceType.PROPERTY_FILE, executor.getProfileFilePath());
//			//Properties profileProperties = executor.getSampleProfileProperties();
//			//IDeploymentConfig deploymentConfig = executor.initializeDeploymentConfig(DeploymentProfileFactory.ProfileSourceType.IN_MEMORY,profileProperties);
//			l.debug(prefix() + "deployment config has been initialized as " + deploymentConfig);
//			// initialize cloud resource manager
//			executor.setCloudResourceManager();
//			// newExecutor cloud resources
//			machines = executor.getResources(nvm, new IExecutionContext() {
//				@Override
//				public String accountNumber() {
//					return "654321";
//				}
//
//				@Override
//				public String projectId() {
//					return "P-4";
//				}
//
//				@Override
//				public String uId() {
//					return "272959";
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
//					return "0";
//				}
//
//				@Override
//				public String serviceId() {
//					return "1";
//				}
//			});
//			if (null != machines) {
//				l.debug(prefix() + "size of the list consisting of VM produced is " + machines.size());
//			}
//			// Refresh resource definitiosn
//			List<VirtualMachineType> machinesWithStateRefreshed = executor.refreshResources(new IExecutionContext() {
//				@Override
//				public String accountNumber() {
//					return "654321";
//				}
//
//				@Override
//				public String projectId() {
//					return "P-4";
//				}
//
//				@Override
//				public String uId() {
//					return "272959";
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
//					return "0";
//				}
//
//				@Override
//				public String serviceId() {
//					return "1";
//				}
//			});
//			l.debug(prefix() + " VM state have been refreshed :" + machinesWithStateRefreshed);
//			// produce RNode from these cloud resources
//			// associate the vms with task tags .. 
//			Set<RNode> nodes = executor.produceRNode(machinesWithStateRefreshed);
//			if (null != nodes) {
//				l.debug(prefix() + "RNodes :" + nodes.toString());
//			}
//			// insert the resource definition to DB
//			int numberOfNodesBeingInserted = executor.insertNodeToDB(nodes);
//			l.debug(prefix() + "following number of insertions has taken place " + numberOfNodesBeingInserted);
//
//			// see if we have valid project definition in rundeck for this
//			boolean hasValidProjectDefinition = executor.hasValidRundeckProject();
//			l.debug(prefix() + "has valid project definition @ rundeck instance :" + hasValidProjectDefinition);
//
//			// tag jobs to node on which they will be executing
//			executor.tagJobs();
//			l.debug(prefix() + "job tagging to VM completes");
//
//			// load runtime property
//			executor.loadRuntimeProperties();
//
//			// trigger
//			executor.execute();
//
//
//		} finally {
//
//			//executor.withEnvironmentProfileAs();
//			//executor.withDeploymentProfileAs().launchDeployment();
//			//executor.rollbackDeployment();
//			//executor.findExecutionStatus().now();
//			//executor.findExecutionStatus().atTheEnd();
//
//			// shut down VMs : PLEASE COMMENT THIS PORTION WHILE BEING USED ACTUALLY
//
////
////			for (VirtualMachineType machineType : machines) {
////				Boolean hasTerminated = executor.getCloudResourceManager().terminate(machineType.getProviderVirtualMachineId(), executor.getCloudResourceManager().buildVMwareExecutionContext(new IExecutionContext() {
////					@Override
////					public String accountNumber() {
////						return "654321";
////					}
////
////					@Override
////					public String projectId() {
////						return "P-4";
////					}
////
////					@Override
////					public String uId() {
////						return "272959";
////					}
////
////					@Override
////					public String reason() {
////						return "Unit-Test";
////					}
////
////					@Override
////					public String description() {
////						return "Test purpose only";
////					}
////
////					@Override
////					public String rollId() {
////						return "0";
////					}
////
////					@Override
////					public String serviceId() {
////						return "1";
////					}
////				}));
//			}
//		}
	}

	private static String prefix() {
		return "CHECKPOINT#" + checkpointId.getAndIncrement() + ": ";
	}
}
