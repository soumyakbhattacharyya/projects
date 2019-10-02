/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.main;

import com.cognizant.formit.entity.infrastructureContext.Context;
import com.cognizant.formit.entity.infrastructureContext.IProject;
import com.cognizant.formit.exception.FormitException;
import com.cognizant.formit.model.AuditReport;
import com.cognizant.formit.model.DeploymentConfigDecorator;
import com.cognizant.formit.model.DeploymentProfileFactory;
import com.cognizant.formit.model.IDeploymentConfig;
import com.cognizant.formit.model.RNode;
import com.cognizant.formit.model.RNodeFactory;
import com.cognizant.formit.model.steps.MajorStep;
import com.cognizant.formit.util.cloud.CloudResourceManager;
import com.cognizant.formit.util.cloud.IExecutionContext;
import com.cognizant.formit.util.db.DBHelper;
import com.cognizant.formit.util.db.IConnectionPoolInitializer;
import com.cognizant.formit.util.file.FileHelper;
import com.cognizant.formit.util.rundeck.IRundeckConnectionInitializer;
import com.cognizant.formit.util.rundeck.RundeckHelper;
import com.cognizant.jpaas2.commons.expection.PlatformException;
import com.cognizant.jpaas2.context.RequestContext;
import com.cognizant.jpaas2.resource.VirtualMachineType;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.rundeck.api.RundeckClient;
import org.rundeck.api.domain.RundeckProject;
import org.xml.sax.SAXException;

/**
 *
 * @author 239913
 */
public abstract class AbstractExecutor implements Executor {

//	private static final Logger l = Logger.getLogger(AbstractExecutor.class);
//	/**
//	 * consists of all VMs along with the tag as a key for them
//	 */
//	protected Map<String, RNode> vms = Collections.synchronizedMap(new HashMap<String, RNode>());
//	/**
//	 * works as store for supplying job options
//	 */
//	//public static Map<String, String> JOB_OPTIONS = Collections.synchronizedMap(new HashMap<String, String>());
//	// constants
//	//private static final SequentialExecutor THIS = new SequentialExecutor();
//	// defines dependency between container / executables
//	/**
//	 * Files that drive the deployment
//	 */
//	// defines dependency between container / executables
//	protected String blueprintConfigFilePath;
//	// defines valid resources
//	protected String xmlFilePath;
//	// defines xsd for validating resource definitions to be used by blueprint
//	protected String xsdFilePath;
//	// defines deployment profile
//	protected String profileFilePath;
//	// an unique run id
//	protected String uniqueRunId;
//	// project that is obtained by parsing the blueprint
//	protected Project project;
//	// deployment configuration
//	protected IDeploymentConfig deploymentConfig;
//	// cloud resource manager
//	protected CloudResourceManager cloudResourceManager;
//	// cloud resources
//	protected List<VirtualMachineType> machines = new ArrayList<VirtualMachineType>(0);
//	// external connection credential : database, rundeck connection etc
//	protected IConnectionPoolInitializer connectionPoolInitializer;
//	protected IRundeckConnectionInitializer rundeckConnectionInitializer;
//	// rundeck project
//	protected RundeckProject rundeckProject;
//	// configuration decorator that decorates infrastructure context with properties supplied at runtime
//	protected DeploymentConfigDecorator configDecorator;
//	// rundeck instance
//	protected RundeckHelper rundeckHelper;
//	/*
//	 * Reference for marshalling infrastructure xml to JAXB entities
//	 */
//	protected String schemaPkg = "com.cognizant.formit.entity.infrastructureContext";
//	protected String objFactory = "com.cognizant.formit.entity.infrastructureContext.ObjectFactory";
//
//	/**
//	 * trigger sequence of tasks
//	 */
//	@Override
//	public void execute() {
//		try {
//			project.executeTarget(project.getDefaultTarget());
//		} catch (Exception ex) {
//			throw new FormitException.ExecutionFailureException("Execution have failed", ex);
//		}
//	}
//
//	/**
//	 * Get a VM which has the same tag that of the task
//	 *
//	 * @param tag
//	 * @param insertables
//	 * @return
//	 */
//	@Override
//	public RNode findFittingNode(String tag, List<RNode> insertables) {
//		for (RNode node : insertables) {
//			if (null != node.getTags() && node.getTags().startsWith(tag)) {
//				return node;
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * Get a VM whose tag "starts with" the tag that of a task TODO : incorporate a better design to add more flexibility to choose a node
//	 *
//	 * @param tag
//	 * @param insertables
//	 * @return
//	 */
//	@Override
//	public RNode findFittingNode(String tag) {
//		Map<String, RNode> map = vms;
//		if (null != map) {
//			for (Entry<String, RNode> entry : map.entrySet()) {
//				if (null != entry.getValue().getTags() && entry.getValue().getTags().startsWith(tag)) {
//					return entry.getValue();
//				}
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * Find a VM from the VM Store TODO : refactor this
//	 *
//	 * @param tag
//	 * @return
//	 */
//	@Override
//	public String findIPFromFittingNode(String tag) {
//		String ipPlusPort = "";
//		try {
//			for (Iterator<Map.Entry<String, RNode>> itr = this.vms.entrySet().iterator();; itr.hasNext()) {
//				Entry<String, RNode> entry = itr.next();
//				l.info(entry.getKey());
//				if (StringUtils.isNotBlank(tag) && tag.matches("(.*)" + entry.getKey() + "(.*)")) {
//					ipPlusPort = entry.getValue().getHostname();
//					l.info("received following ip plus port from the matching node :" + ipPlusPort);
//					break;
//				}
//			}
//		} catch (NoSuchElementException ex) {
//			l.error(ex.getMessage());
//			// do nothing
//		}
//		String onlyIpPortion = ipPlusPort.trim().substring(0, ipPlusPort.indexOf(":"));
//		l.info(onlyIpPortion);
//		return onlyIpPortion;
//	}
//
//	@Override
//	public String getBlueprintConfigFilePath() {
//		return blueprintConfigFilePath;
//	}
//
//	@Override
//	public CloudResourceManager getCloudResourceManager() {
//		return cloudResourceManager;
//	}
//
//	@Override
//	public DeploymentConfigDecorator getConfigDecorator() {
//		return configDecorator;
//	}
//
//	@Override
//	public IConnectionPoolInitializer getConnectionPoolInitializer() {
//		return connectionPoolInitializer;
//	}
//
//	protected BuildListener getCustomBuildListener() {
//		return AuditReport.newInstance();
//	}
//
//	@Override
//	public IDeploymentConfig getDeploymentConfig() {
//		return deploymentConfig;
//	}
//
//	/**
//	 * Deployment profile should have keys in the following format JOB_NAME_WITHOUT_"DOT(.)"_CHARACTER.rest.portion. This method removes
//	 * JOB_NAME_WITHOUT_"DOT(.)"_CHARACTER portion from the key
//	 *
//	 * @param str
//	 * @return
//	 */
//	@Override
//	public String getKeyNameMinusJobnamePrefix(String str) {
//		String result = "";
//		if (StringUtils.isNotEmpty(str)) {
//			int firstIndex = StringUtils.indexOf(str, ".");
//			result = str.substring(firstIndex + 1);
//		}
//		return result;
//	}
//
//	protected DefaultLogger getLogger() {
//		DefaultLogger consoleLogger = new DefaultLogger();
//		consoleLogger.setErrorPrintStream(System.err);
//		consoleLogger.setOutputPrintStream(System.out);
//		consoleLogger.setMessageOutputLevel(Project.MSG_DEBUG);
//		return consoleLogger;
//	}
//
//	@Override
//	public List<VirtualMachineType> getMachines() {
//		return machines;
//	}
//
//	/**
//	 * find out the number of VMs required for managing deployment checks first if the supplied blueprint file is valid if found so,
//	 *
//	 * @return
//	 */
//	@Override
//	public int getNumberOfRequiredVMs() {
//		Project p = this.project;
//		int numberOfRequiredVMs = 0;
//		if (null == p) {
//			p = initiateProject(FileHelper.getFile(getBlueprintConfigFilePath()));
//		}
//		if (null != p && !p.getTargets().isEmpty()) {
//			l.info("project : " + p.getName() + " has targets ");
//			Hashtable targets = getTargets(p);
//			//Set<Map.Entry> set = targets.entrySet();
//			Iterator<Map.Entry> iterator = produceFrom(targets);
//			while (iterator.hasNext()) {
//				Target target = (Target) targets.get(iterator.next().getKey());
//				l.info("got target :" + target.getName());
//				Task[] tasks = target.getTasks();
//				// hardcoding ...
//				if (null != tasks && 0 != tasks.length && tasks.length == 1) {
//					Task task = tasks[0];
//					l.info("got task with name :" + task.getTaskName());
//					l.info("got task with type :" + task.getTaskType());
//					// assume inidividual component requires inidividual VM
//					if (translate(task.getTaskType()).equalsIgnoreCase(MajorStep.CONTAINER)) {
//						l.info("task type is of container hence will be requiring a VM");
//						numberOfRequiredVMs++;
//						l.info("numberOfRequiredVMs :" + numberOfRequiredVMs);
//					}
//				}
//			}
//		}
//
//		return numberOfRequiredVMs;
//	}
//
//	@Override
//	public String getObjFactory() {
//		return objFactory;
//	}
//
//	@Override
//	public String getProfileFilePath() {
//		return profileFilePath;
//	}
//
//	@Override
//	public Project getProject() {
//		return project;
//	}
//
//	/**
//	 * launch required number of VMs and return
//	 *
//	 * @param nvm
//	 * @param iExecutionContext
//	 * @return
//	 */
//	@Override
//	public List<VirtualMachineType> getResources(int nvm, IExecutionContext iExecutionContext) {
//		try {
//			// launch VMs : Remember to rebuild the execustion context before issueing a new launch command
//			for (int iteration = 0; iteration < nvm; iteration++) {
//				RequestContext context = cloudResourceManager.buildVMwareExecutionContext(iExecutionContext);
//				machines.add(cloudResourceManager.launch(context));
//			}
//		} catch (PlatformException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.JPaaSPlatformException(ex.getMessage(), ex.getCause());
//		}
//		return machines;
//	}
//
//	@Override
//	public IRundeckConnectionInitializer getRundeckConnectionInitializer() {
//		return rundeckConnectionInitializer;
//	}
//
//	@Override
//	public RundeckHelper getRundeckHelper() {
//		return rundeckHelper;
//	}
//
//	@Override
//	public RundeckProject getRundeckProject() {
//		return rundeckProject;
//	}
//
//	/**
//	 * do not use this .... just for testing
//	 *
//	 * @return
//	 */
//	@Override
//	public Properties getSampleProfileProperties() {
//		Properties properties = new Properties();
//		properties.setProperty("${name}", "vSphere");
//		properties.setProperty("${providerName}", "vmware");
//		properties.setProperty("${accessId}", "Sjpaasclouduser");
//		properties.setProperty("${secretKey}", "J111111#");
//		properties.setProperty("${cloudEndpoint}", "https://10.242.196.192/sdk");
//		properties.setProperty("${virtualMachineServiceURL}", "https://10.227.125.43:8243/services/VirtualMachineSoapServiceService");
//		properties.setProperty("${keypath}", "D:/sb/JPaaS2.0 - keys - (ppk-priv)/JPaaS-Integration/Cognizant-JPaaS2-dev-1.0.jks");
//		properties.setProperty("${keystorePwd}", "Cognizant-JPaaS");
//		properties.setProperty("${serviceId}", "1");
//		properties.setProperty("${keyPairId}", "jpaas_vm");
//		properties.setProperty("vsphere.box1.tag", "vsphere.general.purpose");
//		properties.setProperty("vsphere.general.purpose.mi", "421c38fe-ec6d-59b8-9b0e-e702785498fa");
//		properties.setProperty("vsphere.general.purpose.description", "vSphere machine for deployment orchestration");
//		properties.setProperty("vsphere.general.purpose.zone", "NA");
//		properties.setProperty("vsphere.general.purpose.securityGroups", "NA");
//		properties.setProperty("vsphere.general.purpose.remoteFS", "/tmp/rundeck");
//		properties.setProperty("vsphere.general.purpose.sshPort", "22");
//		properties.setProperty("vsphere.general.purpose.type", "NA");
//		properties.setProperty("vsphere.general.purpose.userData", "NA");
//		properties.setProperty("vsphere.general.purpose.remoteUser", "ubuntu");
//		properties.setProperty("vsphere.general.purpose.rootCommandPrefix", "sudo");
//		properties.setProperty("vsphere.general.purpose.subnetId", "NA");
//		properties.setProperty("vsphere.general.purpose.cpuCount", "2");
//		properties.setProperty("vsphere.general.purpose.diskSizeInGb", "1");
//		properties.setProperty("vsphere.general.purpose.name", "Ubuntu-Server-10.04-64bit-Key-Template");
//		properties.setProperty("vsphere.general.purpose.productId", "2:2048");
//		properties.setProperty("vsphere.general.purpose.ramInMb", "2048");
//		properties.setProperty("deploy-TOMCAT.webserver.script", "deploy-TOMCAT");
//		properties.setProperty("deploy-TOMCAT.server.self.ip", "${WEB_SERVER#TOMCAT:::IP}");
//		properties.setProperty("deploy-TOMCAT.database.ip", "${deploy-MYSQL.database.self.ip}");
//		properties.setProperty("deploy-TOMCAT.database.port", "${deploy-MYSQL.database.port}");
//		properties.setProperty("deploy-TOMCAT.database.schema", "chapter02db");
//		properties.setProperty("deploy-TOMCAT.database.uid", "root");
//		properties.setProperty("deploy-TOMCAT.database.pwd", "mysql");
//		properties.setProperty("deploy-TOMCAT.database.jndi.entry", "chapter02db");
//		properties.setProperty("deploy-TOMCAT.this.download.location", "http://apache.techartifact.com/mirror/tomcat/tomcat-6/v6.0.36/bin/apache-tomcat-6.0.36.tar.gz");
//		properties.setProperty("deploy-TOMCAT.this.servlet.port", "8080");
//		properties.setProperty("deploy-TOMCAT.this.servlet.uriencoding", "ISO-8859-1");
//		properties.setProperty("deploy-TOMCAT.this.ajp.port", "8010");
//		properties.setProperty("deploy-MYSQL.database.self.ip", "${DB_SERVER#MYSQL5:::IP}");
//		properties.setProperty("deploy-MYSQL.database.port", "3306");
//		properties.setProperty("deploy-MYSQL.database.script", "deploy-MYSQL");
//		return properties;
//	}
//
//	@Override
//	public String getSchemaPkg() {
//		return schemaPkg;
//	}
//
//	/*
//	 * get targets from a project
//	 */
//	protected Hashtable getTargets(Project p) {
//		return (null != p) ? p.getTargets() : null;
//	}
//
//	@Override
//	public String getUniqueRunId() {
//		return uniqueRunId;
//	}
//
//	@Override
//	public Map<String, RNode> getVms() {
//		return vms;
//	}
//
//	@Override
//	public String getXmlFilePath() {
//		return xmlFilePath;
//	}
//
//	@Override
//	public String getXsdFilePath() {
//		return xsdFilePath;
//	}
//
//	/**
//	 * verifies that the blueprint file is valid and readable & blueprint file can be marshalled to a valid project which hosts deployment
//	 * tasks
//	 *
//	 * @return
//	 */
//	@Override
//	public boolean hasValidBlueprint() {
//		// read dependency xml
//		File buildFile = FileHelper.getFile(this.getBlueprintConfigFilePath());
//		if (FileHelper.isValid(buildFile)) {
//			// initiate project
//			project = this.initiateProject(buildFile);
//		}
//		return null != project;
//	}
//
//	/**
//	 * check if a project exists in targeted rundeck server with resource definition
//	 *
//	 * @return
//	 */
//	@Override
//	public boolean hasValidRundeckProject() {
//		/*
//		 * find jobs for the given project
//		 * load profile properties
//		 * tag jobs to VMs where they will run
//		 * fire driver job
//		 */
//		RundeckHelper rundeckHelper = getRundeckHelper();
//		RundeckClient rundeckClient = rundeckHelper.connect(getRundeckConnectionInitializer());
//		// get project from rundeck & assert that it is same as that mentioned in
//		// the infra definition & blueprint definition
//		IProject rundeckProjectFromEnvironmentProfile = null;
//		if (deploymentConfig instanceof Context) {
//			rundeckProjectFromEnvironmentProfile = (((Context) deploymentConfig)).getProject();
//		}
//		String name = rundeckProjectFromEnvironmentProfile.getName();
//		String resourcePath = rundeckProjectFromEnvironmentProfile.getResource();
//		String sshKeyPath = rundeckProjectFromEnvironmentProfile.getSshKeypath();
//		rundeckProject = rundeckClient.getProject(name);
//		if (StringUtils.equalsIgnoreCase(name, rundeckProject.getName()) && StringUtils.equalsIgnoreCase(resourcePath, rundeckProject.getResourceModelProviderUrl())) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	/**
//	 * Initializes deployment configuration
//	 *
//	 * @return
//	 */
//	@Override
//	public IDeploymentConfig initializeDeploymentConfig(DeploymentProfileFactory.ProfileSourceType profileSourceType, String profilePath) {
//		try {
//			configDecorator = DeploymentConfigDecorator.newInstance(profileSourceType, profilePath);
//			configDecorator.registerPlusDecorateDeploymentConfig(this.getXmlFilePath(), this.getXsdFilePath());
//			deploymentConfig = configDecorator.unmarshalDecoratedConfig(this.getSchemaPkg(), this.getObjFactory());
//		} catch (InstantiationException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (IllegalAccessException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (ClassNotFoundException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (JAXBException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (SAXException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (ParserConfigurationException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (IOException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (ConfigurationException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		}
//		return deploymentConfig;
//	}
//
//	/**
//	 * initialize with a property object
//	 *
//	 * @param profileSourceType
//	 * @param properties
//	 * @return
//	 */
//	@Override
//	public IDeploymentConfig initializeDeploymentConfig(DeploymentProfileFactory.ProfileSourceType profileSourceType, Properties properties) {
//		try {
//			configDecorator = DeploymentConfigDecorator.newInstance(profileSourceType, properties);
//			configDecorator.registerPlusDecorateDeploymentConfig(this.getXmlFilePath(), this.getXsdFilePath());
//			deploymentConfig = configDecorator.unmarshalDecoratedConfig(this.getSchemaPkg(), this.getObjFactory());
//		} catch (InstantiationException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (IllegalAccessException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (ClassNotFoundException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (JAXBException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (SAXException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (ParserConfigurationException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (IOException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		} catch (ConfigurationException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
//		}
//		return deploymentConfig;
//	}
//
//	@Override
//	public Project initiateProject(File buildFile) {
//		if (FileHelper.isValid(buildFile)) {
//			// initiate project and parse corresponding xml file
//			Project p = new Project();
//			parse(buildFile, p);
//			test(p);
//			l.info("parsed blueprint file");
//			DefaultLogger defaultLogger = getLogger();
//			p.addBuildListener(defaultLogger);
//			l.info("added default log listener");
//			// associate custom build listener
//			BuildListener buildListener = getCustomBuildListener();
//			p.addBuildListener(buildListener);
//			l.info("added blueprint execution event listener");
//			return p;
//		} else {
//			throw new IllegalArgumentException("blueprint non - instantiable");
//		}
//	}
//
//	/**
//	 * find out the number of VMs required for managing deployment checks first if the supplied blueprint file is valid if found so,
//	 *
//	 * @return
//	 */
//	public void test(Project project) {
//		Project p = project;
//		int numberOfRequiredVMs = 0;
//		if (null == p) {
//			p = initiateProject(FileHelper.getFile(getBlueprintConfigFilePath()));
//		}
//		if (null != p && !p.getTargets().isEmpty()) {
//			l.info("project : " + p.getName() + " has targets ");
//			Hashtable targets = getTargets(p);
//			//Set<Map.Entry> set = targets.entrySet();
//			Iterator<Map.Entry> iterator = produceFrom(targets);
//			while (iterator.hasNext()) {
//				Target target = (Target) targets.get(iterator.next().getKey());
//				l.info("got target :" + target.getName());
//				Task[] tasks = target.getTasks();
//				// hardcoding ...
//				if (null != tasks && 0 != tasks.length && tasks.length == 1) {
//					Task task = tasks[0];
//					l.info("got task with name :" + task.getTaskName());
//					l.info("got task with type :" + task.getTaskType());
//					// assume inidividual component requires inidividual VM
//					if (translate(task.getTaskType()).equalsIgnoreCase(MajorStep.CONTAINER)) {
//						l.info("task type is of container hence will be requiring a VM");
//						l.info("numberOfRequiredVMs :" + numberOfRequiredVMs);
//					}
//				}
//			}
//		}
//
//
//	}
//
//	/**
//	 * insert node definitions to database
//	 *
//	 * @param nodes
//	 * @return
//	 */
//	@Override
//	public int insertNodeToDB(Set<RNode> nodes) {
//		int numberOfInsertions = 0;
//		try {
//			// ok .. now we have all RNodes properly initialized and it means that we are ready to insert the resource
//			// definition
//			DBHelper dBHelper = DBHelper.newInstance(getConnectionPoolInitializer());
//			// push resource definition to db
//			List<RNode> insertables = new ArrayList<RNode>();
//			Iterator<RNode> it = nodes.iterator();
//			while (it.hasNext()) {
//				insertables.add(it.next());
//			}
//			numberOfInsertions = dBHelper.create(insertables);
//		} catch (SQLException ex) {
//			throw new FormitException.FailedToUpdateNodeDefinitionInDBException("exception occured while updating node db", ex);
//		} catch (ClassNotFoundException ex) {
//			throw new FormitException.FailedToUpdateNodeDefinitionInDBException("exception occured while updating node db", ex);
//		} catch (PropertyVetoException ex) {
//			throw new FormitException.FailedToUpdateNodeDefinitionInDBException("exception occured while updating node db", ex);
//		}
//		return numberOfInsertions;
//	}
//
//	public void loadRuntimeProperties(Project p, Map<String, String> profile) {
//		l.info("profile :" + profile);
//		try {
//			for (Entry<String, String> entry : profile.entrySet()) {
//				l.info(entry);
//				p.setUserProperty(getKeyNameMinusJobnamePrefix(entry.getKey().trim()), entry.getValue().trim());
//			}
//		} catch (NoSuchElementException ex) {
//			l.error(ex.getMessage());
//			// do nothing
//		} finally {
//			// at this level all profile properties have been loaded
//			// now we set this executor's instance for future use
//			p.setUserProperty(AppConstants.DRIVING_EXECUTOR_INSTANCE, this.getUniqueRunId());
//		}
//	}
//
//	public void loadRuntimeProperties() {
//		try {
//			this.loadRuntimeProperties(project, configDecorator.getDeploymentProfile().get());
//		} catch (ConfigurationException ex) {
//			throw new FormitException.CustomPropertyLoadingFailureException("Tagging jobs to node has failed", ex);
//		} catch (Exception ex) {
//			throw new FormitException.CustomPropertyLoadingFailureException("Tagging jobs to node has failed", ex);
//		}
//	}
//
//	protected void parse(File buildFile, Project p) {
//		p.setUserProperty("ant.file", buildFile.getAbsolutePath());
//		p.init();
//		ProjectHelper helper = ProjectHelper.getProjectHelper();
//		p.addReference("ant.projectHelper", helper);
//		helper.parse(p, buildFile);
//	}
//
//	/*
//	 * produce an iterator from a hashtable
//	 */
//	protected Iterator<Map.Entry> produceFrom(Hashtable hashtable) {
//		l.info("creating iterator for traversing through the list of targets");
//		Set<Map.Entry> set = hashtable.entrySet();
//		Iterator<Map.Entry> iterator = set.iterator();
//		return iterator;
//	}
//
//	@Override
//	public Set<RNode> produceRNode(List<VirtualMachineType> spawnedVMs) {
//		List<VirtualMachineType> machines = spawnedVMs;
//		Project p = this.project;
//		Set<RNode> nodes = new HashSet<RNode>();
//		String componentTag = "";
//		String componentTagPlusInstanceId = "";
//		if (null != p && !p.getTargets().isEmpty()) {
//			Hashtable targets = getTargets(p);
//			//Set<Map.Entry> set = targets.entrySet();
//			Iterator<Map.Entry> iterator = produceFrom(targets);
//			int numberOfContainer = 0;
//			while (iterator.hasNext()) {
//				Target target = (Target) targets.get(iterator.next().getKey());
//				Task[] tasks = target.getTasks();
//				// hardcoding ...
//				if (null != tasks && 0 != tasks.length && tasks.length == 1) {
//					Task task = tasks[0];
//					// collect tag for a component and append an instance id to it
//					if (translate(task.getTaskType()).equalsIgnoreCase(MajorStep.CONTAINER)) {
//						String instanceId = "";
//						String dnsAddress = "";
//						numberOfContainer++;
//						VirtualMachineType machineType = null;
//						RuntimeConfigurable configurable = task.getRuntimeConfigurableWrapper();
//						Hashtable attributes = configurable.getAttributeMap();
//						Iterator<Map.Entry> attributeIterator = produceFrom(attributes);
//						Iterator<VirtualMachineType> machineIterator = machines.iterator();
//						for (; attributeIterator.hasNext() && machineIterator.hasNext();) {
//							String key = String.valueOf(attributeIterator.next().getKey());
//							if (StringUtils.equals(key, AppConstants.TAG)) {
//								// get the "value" against the "tag" attribute of the component
//								componentTag = String.valueOf(attributes.get(key));
//								// get a resource ... this is actually an oversimplified situation right now
//								// pick a vm and associate it to container
//								// TODO introduce some kind of vm selection logic that associates only a qualified
//								// node to the component that it is going to host
//								machineType = machineIterator.next();
//								instanceId = machineType.getProviderVirtualMachineId();
//								dnsAddress = machineType.getPrivateDnsAddress();
//								// construct tag of the node
//								componentTagPlusInstanceId = (new StringBuffer()).append(componentTag).append(AppConstants.HASH).append(instanceId).toString();
//								//break;
//								RNode node = RNodeFactory.newNode().withTag(componentTagPlusInstanceId).withName(componentTagPlusInstanceId).withDescription("Node that facilitates deployment").withHostname(dnsAddress).forUniqueRunId(uniqueRunId);
//								nodes.add(node);
//								this.vms.put(componentTag, node);
//								l.info("added following rnode to host a container " + node);
//								l.info("State of VM_BANK :" + this.vms);
//							}
//						}
//						// remove the node which was associated in previous loop
//						boolean hasRemoved = machines.remove(machineType);
//						l.info("Following machine " + machineType.getProviderVirtualMachineId() + " has been removed as it is used already " + " : " + hasRemoved);
//					}
//				}
//			}
//		}
//		return nodes;
//	}
//
//	/**
//	 * refresh resource states - primarily to get the IP address of the VMs by this time
//	 *
//	 * @param iExecutionContext
//	 * @return
//	 * @throws InterruptedException
//	 */
//	@Override
//	public List<VirtualMachineType> refreshResources(IExecutionContext iExecutionContext) {
//		try {
//			// refresh VMs : Remember to rebuild the execustion context before issueing a new launch command
//			List<VirtualMachineType> machinesWithStateRefreshed = new ArrayList<VirtualMachineType>(0);
//			for (VirtualMachineType machineType : machines) {
//				RequestContext context = cloudResourceManager.buildVMwareExecutionContext(iExecutionContext);
//				int counter = 10;
//				do {
//					VirtualMachineType machineWithStateRefreshed = cloudResourceManager.getState(machineType.getProviderVirtualMachineId(), context);
//					if (StringUtils.isNotEmpty(machineWithStateRefreshed.getPrivateDnsAddress())) {
//						machinesWithStateRefreshed.add(machineWithStateRefreshed);
//						break;
//					} else {
//						try {
//							Thread.sleep(5000);
//						} catch (InterruptedException ex) {
//							l.error(ex.getMessage());
//							throw new FormitException.JPaaSPlatformException(ex.getMessage(), ex.getCause());
//						}
//					}
//				} while (counter-- > 0);
//			}
//			machines = machinesWithStateRefreshed;
//		} catch (PlatformException ex) {
//			l.error(ex.getMessage());
//			throw new FormitException.JPaaSPlatformException(ex.getMessage(), ex.getCause());
//		}
//		return machines;
//	}
//
//	@Override
//	public void setBlueprintConfigFilePath(String blueprintConfigFilePath) {
//		this.blueprintConfigFilePath = blueprintConfigFilePath;
//	}
//
//	/**
//	 * sets cloud resource manager with the pre - loaded deployment configuration
//	 */
//	public void setCloudResourceManager() {
//		if (null != deploymentConfig) {
//			cloudResourceManager = CloudResourceManager.newInstance(deploymentConfig);
//		} else {
//			throw new FormitException.NullConfigurationException("Initialize configuration first");
//		}
//	}
//
//	@Override
//	public void setConfigDecorator(DeploymentConfigDecorator configDecorator) {
//		this.configDecorator = configDecorator;
//	}
//
//	@Override
//	public void setConnectionPoolInitializer(IConnectionPoolInitializer connectionPoolInitializer) {
//		this.connectionPoolInitializer = connectionPoolInitializer;
//	}
//
//	@Override
//	public void setDeploymentConfig(IDeploymentConfig deploymentConfig) {
//		this.deploymentConfig = deploymentConfig;
//	}
//
//	@Override
//	public void setMachines(List<VirtualMachineType> machines) {
//		this.machines = machines;
//	}
//
//	@Override
//	public void setObjFactory(String objFactory) {
//		this.objFactory = objFactory;
//	}
//
//	@Override
//	public void setProfileFilePath(String profileFilePath) {
//		this.profileFilePath = profileFilePath;
//	}
//
//	@Override
//	public void setProject(Project project) {
//		this.project = project;
//	}
//
//	@Override
//	public void setRundeckConnectionInitializer(IRundeckConnectionInitializer rundeckConnectionInitializer) {
//		this.rundeckConnectionInitializer = rundeckConnectionInitializer;
//		// spawn a new rundeck helper and initialize it's connection
//		RundeckHelper helper = new RundeckHelper();
//		RundeckClient client = helper.connect(rundeckConnectionInitializer);
//		helper.setRundeckClient(client);
//		this.rundeckHelper = helper;
//	}
//
//	@Override
//	public void setRundeckHelper(RundeckHelper rundeckHelper) {
//		this.rundeckHelper = rundeckHelper;
//	}
//
//	@Override
//	public void setRundeckProject(RundeckProject rundeckProject) {
//		this.rundeckProject = rundeckProject;
//	}
//
//	@Override
//	public void setSchemaPkg(String schemaPkg) {
//		this.schemaPkg = schemaPkg;
//	}
//
//	@Override
//	public void setUniqueRunId(String uniqueRunId) {
//		this.uniqueRunId = uniqueRunId;
//	}
//
//	@Override
//	public void setVms(Map<String, RNode> vms) {
//		this.vms = vms;
//	}
//
//	@Override
//	public void setXmlFilePath(String xmlFilePath) {
//		this.xmlFilePath = xmlFilePath;
//	}
//
//	@Override
//	public void setXsdFilePath(String xsdFilePath) {
//		this.xsdFilePath = xsdFilePath;
//	}
//
//	/**
//	 * This method will perform an important operation 1. Get all target for the given ANT project 2. Get the ID from the target definition
//	 * 3. Find corresponding job rundeck 4. Update the node value to the job definition
//	 *
//	 * @param p
//	 * @param rundeckProject
//	 * @throws SQLException
//	 * @throws ClassNotFoundException
//	 */
//	public void tagJobs(Project p, RundeckProject rundeckProject) throws SQLException, ClassNotFoundException, PropertyVetoException {
//		String componentTag = "";
//		if (null != p && !p.getTargets().isEmpty()) {
//			Hashtable targets = getTargets(p);
//			//Set<Map.Entry> set = targets.entrySet();
//			Iterator<Map.Entry> iterator = produceFrom(targets);
//			while (iterator.hasNext()) {
//				String nodeTag = "";
//				String jobId = "";
//				Target target = (Target) targets.get(iterator.next().getKey());
//				Task[] tasks = target.getTasks();
//				// hardcoding ...
//				if (null != tasks && 0 != tasks.length && tasks.length == 1) {
//					Task task = tasks[0];
//					// collect tag for a component and append an instance id to it
//					RuntimeConfigurable configurable = task.getRuntimeConfigurableWrapper();
//					Hashtable attributes = configurable.getAttributeMap();
//					Iterator<Map.Entry> attributeIterator = produceFrom(attributes);
//					for (; attributeIterator.hasNext();) {
//						String key = String.valueOf(attributeIterator.next().getKey());
//						if (StringUtils.equals(key, "tag")) {
//							// get the "value" against the "tag" attribute of the component
//							componentTag = String.valueOf(attributes.get(key));
//						}
//						if (StringUtils.equals(key, "id")) {
//							// get the "value" against the "tag" attribute of the component
//							jobId = String.valueOf(attributes.get(key));
//						}
//					}
//					if (AppConstants._DRIVER.equals(jobId)) {
//						continue;
//					}
//					// find a node with the tag
//					RNode node = findFittingNode(componentTag);
//					// get the tag for the node
//					if (null != node) {
//						nodeTag = node.getTags();
//						// update the database
//						DBHelper dBHelper = DBHelper.newInstance(getConnectionPoolInitializer());
//						dBHelper.updateJobDefn(jobId, rundeckProject.getName(), nodeTag);
//						l.info("updated job with node value");
//					}
//				}
//			}
//		}
//	}
//
//	public void tagJobs() {
//		try {
//			tagJobs(project, rundeckProject);
//		} catch (SQLException ex) {
//			throw new FormitException.TaggingFailureException("Tagging jobs to node has failed", ex);
//		} catch (ClassNotFoundException ex) {
//			throw new FormitException.TaggingFailureException("Tagging jobs to node has failed", ex);
//		} catch (Exception ex) {
//			throw new FormitException.TaggingFailureException("Tagging jobs to node has failed", ex);
//		}
//	}
//
//	protected String translate(String from) {
//		return !StringUtils.isEmpty(from) && String.valueOf(from.toUpperCase()).equals(AppConstants._CONTAINER) ? MajorStep.CONTAINER : "";
//	}
//
//	void withEnvironmentProfileAs() {
//		throw new UnsupportedOperationException("Not yet implemented");
//	}
}
