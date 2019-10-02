/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.main;

import com.cognizant.formit.model.DeploymentConfigDecorator;
import com.cognizant.formit.model.DeploymentProfileFactory;
import com.cognizant.formit.model.IDeploymentConfig;
import com.cognizant.formit.model.RNode;
import com.cognizant.formit.util.cloud.CloudResourceManager;
import com.cognizant.formit.util.cloud.IExecutionContext;
import com.cognizant.formit.util.db.IConnectionPoolInitializer;
import com.cognizant.formit.util.rundeck.IRundeckConnectionInitializer;
import com.cognizant.formit.util.rundeck.RundeckHelper;
import com.cognizant.jpaas2.resource.VirtualMachineType;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.tools.ant.Project;
import org.rundeck.api.domain.RundeckProject;

/**
 *
 * @author Cognizant
 */
public interface Executor {
//
//	/**
//	 * trigger sequence of tasks
//	 */
//	void execute();
//
//	/**
//	 * Get a VM which has the same tag that of the task
//	 *
//	 * @param tag
//	 * @param insertables
//	 * @return
//	 */
//	RNode findFittingNode(String tag, List<RNode> insertables);
//
//	/**
//	 * Get a VM whose tag "starts with" the tag that of a task TODO : incorporate a better design to add more flexibility to choose a node
//	 *
//	 * @param tag
//	 * @param insertables
//	 * @return
//	 */
//	RNode findFittingNode(String tag);
//
//	/**
//	 * Find a VM from the VM Store TODO : refactor this
//	 *
//	 * @param tag
//	 * @return
//	 */
//	String findIPFromFittingNode(String tag);
//
//	String getBlueprintConfigFilePath();
//
//	CloudResourceManager getCloudResourceManager();
//
//	DeploymentConfigDecorator getConfigDecorator();
//
//	IConnectionPoolInitializer getConnectionPoolInitializer();
//
//	IDeploymentConfig getDeploymentConfig();
//
//	/**
//	 * Deployment profile should have keys in the following format JOB_NAME_WITHOUT_"DOT(.)"_CHARACTER.rest.portion. This method removes
//	 * JOB_NAME_WITHOUT_"DOT(.)"_CHARACTER portion from the key
//	 *
//	 * @param str
//	 * @return
//	 */
//	String getKeyNameMinusJobnamePrefix(String str);
//
//	List<VirtualMachineType> getMachines();
//
//	/*
//	 * as of now we assume number of component in the deployment blueprint = number of VMs required
//	 */
//	int getNumberOfRequiredVMs();
//
//	String getObjFactory();
//
//	String getProfileFilePath();
//
//	Project getProject();
//
//	/**
//	 * launch required number of VMs and return
//	 *
//	 * @param nvm
//	 * @param iExecutionContext
//	 * @return
//	 */
//	List<VirtualMachineType> getResources(int nvm, IExecutionContext iExecutionContext);
//
//	IRundeckConnectionInitializer getRundeckConnectionInitializer();
//
//	RundeckHelper getRundeckHelper();
//
//	RundeckProject getRundeckProject();
//
//	/**
//	 * do not use this .... just for testing
//	 *
//	 * @return
//	 */
//	Properties getSampleProfileProperties();
//
//	String getSchemaPkg();
//
//	String getUniqueRunId();
//
//	Map<String, RNode> getVms();
//
//	String getXmlFilePath();
//
//	String getXsdFilePath();
//
//	/**
//	 * verifies that the blueprint file is valid and readable & blueprint file can be marshalled to a valid project which hosts deployment
//	 * tasks
//	 *
//	 * @return
//	 */
//	boolean hasValidBlueprint();
//
//	/**
//	 * check if a project exists in targeted rundeck server with resource definition
//	 *
//	 * @return
//	 */
//	boolean hasValidRundeckProject();
//
//	/**
//	 * Initializes deployment configuration
//	 *
//	 * @return
//	 */
//	IDeploymentConfig initializeDeploymentConfig(DeploymentProfileFactory.ProfileSourceType profileSourceType, String profilePath);
//
//	/**
//	 * initialize with a property object
//	 *
//	 * @param profileSourceType
//	 * @param properties
//	 * @return
//	 */
//	IDeploymentConfig initializeDeploymentConfig(DeploymentProfileFactory.ProfileSourceType profileSourceType, Properties properties);
//
//	Project initiateProject(File buildFile);
//
//	/**
//	 * insert node definitions to database
//	 *
//	 * @param nodes
//	 * @return
//	 */
//	int insertNodeToDB(Set<RNode> nodes);
//
//	Set<RNode> produceRNode(List<VirtualMachineType> spawnedVMs);
//
//	/**
//	 * refresh resource states - primarily to get the IP address of the VMs by this time
//	 *
//	 * @param iExecutionContext
//	 * @return
//	 * @throws InterruptedException
//	 */
//	List<VirtualMachineType> refreshResources(IExecutionContext iExecutionContext);
//
//	void setBlueprintConfigFilePath(String blueprintConfigFilePath);
//
//	void setConfigDecorator(DeploymentConfigDecorator configDecorator);
//
//	void setConnectionPoolInitializer(IConnectionPoolInitializer connectionPoolInitializer);
//
//	void setDeploymentConfig(IDeploymentConfig deploymentConfig);
//
//	void setMachines(List<VirtualMachineType> machines);
//
//	void setObjFactory(String objFactory);
//
//	void setProfileFilePath(String profileFilePath);
//
//	void setProject(Project project);
//
//	void setRundeckConnectionInitializer(IRundeckConnectionInitializer rundeckConnectionInitializer);
//
//	void setRundeckHelper(RundeckHelper rundeckHelper);
//
//	void setRundeckProject(RundeckProject rundeckProject);
//
//	void setSchemaPkg(String schemaPkg);
//
//	void setUniqueRunId(String uniqueRunId);
//
//	void setVms(Map<String, RNode> vms);
//
//	void setXmlFilePath(String xmlFilePath);
//
//	void setXsdFilePath(String xsdFilePath);
//
//	public void loadRuntimeProperties();
//
//	public void tagJobs();
//
//	public void setCloudResourceManager();
//
//	public void loadRuntimeProperties(Project p, Map<String, String> profile);
//
//	/**
//	 * initialize executor
//	 *
//	 * @param blueprintFilePath
//	 * @param deploymentProfileFilePath
//	 * @param infraContextFilePath
//	 * @param infraContextXSDFilePath
//	 * @param connectionPoolInitializer
//	 * @param rundeckConnectionInitializer
//	 */
//	public void getInitialized(String blueprintFilePath, String deploymentProfileFilePath, String infraContextFilePath, String infraContextXSDFilePath, IConnectionPoolInitializer connectionPoolInitializer, IRundeckConnectionInitializer rundeckConnectionInitializer);
}
