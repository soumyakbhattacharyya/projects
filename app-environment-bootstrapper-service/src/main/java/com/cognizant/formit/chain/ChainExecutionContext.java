/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.chain;

import com.cognizant.formit.chain.observer.ObservableMap;
import com.cognizant.formit.entity.infrastructureContext.Context;
import com.cognizant.formit.entity.infrastructureContext.IProject;
import com.cognizant.formit.exception.FormitException;
import com.cognizant.formit.main.AbstractExecutor;
import com.cognizant.formit.model.DeploymentConfigDecorator;
import com.cognizant.formit.model.DeploymentProfileFactory;
import com.cognizant.formit.model.IDeploymentConfig;
import com.cognizant.formit.model.RNode;
import com.cognizant.formit.util.ant.AntUtil;
import com.cognizant.formit.util.cloud.CloudResourceManager;
import com.cognizant.formit.util.cloud.IExecutionContext;
import com.cognizant.formit.util.db.IConnectionPoolInitializer;
import com.cognizant.formit.util.file.FileHelper;
import com.cognizant.formit.util.rundeck.IRundeckConnectionInitializer;
import com.cognizant.formit.util.rundeck.RundeckHelper;
import com.cognizant.jpaas2.resource.VirtualMachineType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.rundeck.api.RundeckClient;
import org.rundeck.api.domain.RundeckProject;
import org.xml.sax.SAXException;

/**
 * execution context for a chain of operations
 *
 * @author cognizant
 */
public class ChainExecutionContext extends ContextBase {

	public ObservableMap getMessageMap() {
		return messageMap;
	}

	public Map<String, RNode> getVms() {
		return vms;
	}

	public void setMachines(List<VirtualMachineType> machines) {
		this.machines = machines;
	}

	public String getBlueprintConfigFilePath() {
		return blueprintConfigFilePath;
	}

	public String getXmlFilePath() {
		return xmlFilePath;
	}

	public String getXsdFilePath() {
		return xsdFilePath;
	}

	public String getProfileFilePath() {
		return profileFilePath;
	}

	public Project getProject() {
		return project;
	}

	public IDeploymentConfig getDeploymentConfig() {
		return deploymentConfig;
	}

	public CloudResourceManager getCloudResourceManager() {
		return cloudResourceManager;
	}

	public List<VirtualMachineType> getMachines() {
		return machines;
	}

	public IConnectionPoolInitializer getConnectionPoolInitializer() {
		return connectionPoolInitializer;
	}

	public IRundeckConnectionInitializer getRundeckConnectionInitializer() {
		return rundeckConnectionInitializer;
	}

	public RundeckProject getRundeckProject() {
		return rundeckProject;
	}

	public DeploymentConfigDecorator getConfigDecorator() {
		return configDecorator;
	}

	public RundeckHelper getRundeckHelper() {
		return rundeckHelper;
	}

	public String getSchemaPkg() {
		return schemaPkg;
	}

	public String getObjFactory() {
		return objFactory;
	}
	/**
	 * instance id of the corresponding run
	 */
	private String uniqueRunId;
	private static final Logger l = Logger.getLogger(AbstractExecutor.class);
	/**
	 * consists of all VMs along with the tag as a key for them
	 */
	private Map<String, RNode> vms = Collections.synchronizedMap(new HashMap<String, RNode>());
	// defines dependency between container / executables
	private String blueprintConfigFilePath;
	// defines valid resources
	private String xmlFilePath;
	// defines xsd for validating resource definitions to be used by blueprint
	private String xsdFilePath;
	// defines deployment profile
	private String profileFilePath;
	// project that is obtained by parsing the blueprint
	private Project project;
	// deployment configuration
	private IDeploymentConfig deploymentConfig;
	// cloud resource manager
	private CloudResourceManager cloudResourceManager;
	// external connection credential : database, rundeck connection etc
	private IConnectionPoolInitializer connectionPoolInitializer;

	public Properties getDeploymentConfiguration() {
		return deploymentConfiguration;
	}
	private IRundeckConnectionInitializer rundeckConnectionInitializer;
	// rundeck project
	private RundeckProject rundeckProject;
	// configuration decorator that decorates infrastructure context with properties supplied at runtime
	private DeploymentConfigDecorator configDecorator;
	// rundeck instance
	private RundeckHelper rundeckHelper;
	// execution context
	private IExecutionContext executionContext;
	// deployment profile properties
	private Properties deploymentConfiguration;
	// message holder
	private ObservableMap messageMap = ObservableMap.newInstance();

	public IExecutionContext getExecutionContext() {
		return executionContext;
	}

	public void setExecutionContext(IExecutionContext executionContext) {
		this.executionContext = executionContext;
	}

	public int getNumberOfRequiredVM() {
		return numberOfRequiredVM;
	}

	public void setNumberOfRequiredVM(int numberOfRequiredVM) {
		this.numberOfRequiredVM = numberOfRequiredVM;
	}
	/**
	 * derived
	 */
	private int numberOfRequiredVM;

	public List<RNode> getNodesToBeTerminated() {
		return nodesToBeTerminated;
	}

	public void setNodesToBeTerminated(List<RNode> nodesToBeTerminated) {
		this.nodesToBeTerminated = nodesToBeTerminated;
	}
	// collection of cloud resources (without tags)
	private List<VirtualMachineType> machines = new ArrayList<VirtualMachineType>(0);
	/**
	 * required for termination
	 */
	private List<RNode> nodesToBeTerminated;
	/*
	 * Reference for marshalling infrastructure xml to JAXB entities
	 */
	private final String schemaPkg = "com.cognizant.formit.entity.infrastructureContext";
	private final String objFactory = "com.cognizant.formit.entity.infrastructureContext.ObjectFactory";

	private ChainExecutionContext(Builder builder) {

		this.uniqueRunId = builder.uniqueRunId;
		this.blueprintConfigFilePath = builder.blueprintConfigFilePath;
		this.profileFilePath = builder.profileFilePath;
		this.xmlFilePath = builder.xmlFilePath;
		this.xsdFilePath = builder.xsdFilePath;
		this.connectionPoolInitializer = builder.connectionPoolInitializer;
		this.rundeckConnectionInitializer = builder.rundeckConnectionInitializer;
		this.project = builder.project;
		this.deploymentConfig = builder.deploymentConfig;
		this.rundeckProject = builder.rundeckProject;
		this.executionContext = builder.executionContext;
		this.deploymentConfiguration = builder.deploymentConfiguration;

		// instantiate an helper instance for rundeck helper to reduce clutter
		this.rundeckHelper = new RundeckHelper();
		// initialize rundeck helper with a connection
		this.rundeckHelper.connect(rundeckConnectionInitializer);
		//this.messageMap = builder.messageMap;

	}

//	public void setMessageMap(Map<String, String> messageMap) {
//		this.messageMap = messageMap;
//	}

	public String getUniqueRunId() {
		return uniqueRunId;
	}

	public void setUniqueRunId(String uniqueRunId) {
		this.uniqueRunId = uniqueRunId;
	}

	public static final class Builder {

		/**
		 * unique instance id passed by the client
		 */
		private final String uniqueRunId;
		/**
		 * blue print that defines dependency between tasks
		 */
		private final String blueprintConfigFilePath;
		// defines valid resources
		private final String xmlFilePath;
		// defines xsd for validating resource definitions to be used by blueprint
		private final String xsdFilePath;
		// defines deployment profile
		private final String profileFilePath;
		// project that is obtained by parsing the blueprint
		private final Project project;
		/**
		 * connection credential used for connecting with database and rundeck
		 */
		private final IConnectionPoolInitializer connectionPoolInitializer;
		private final IRundeckConnectionInitializer rundeckConnectionInitializer;
		// deployment configuration
		private final IDeploymentConfig deploymentConfig;
		// rundeck project
		private final RundeckProject rundeckProject;
		// execution context
		private final IExecutionContext executionContext;
		private final Properties deploymentConfiguration;
		
		// runtime message holder ... 
		private final Map<String,String> messageMap = new HashMap<String, String>(0);
		/**
		 * for validating and marshalling xml
		 */
		private final String schemaPkg = "com.cognizant.formit.entity.infrastructureContext";
		private final String objFactory = "com.cognizant.formit.entity.infrastructureContext.ObjectFactory";

		public Builder(final String uniqueRunId,
				final String blueprintFilePath,
				final String deploymentProfileFilePath,
				final String infraContextXmlFilePath,
				final String infraContextXSDFilePath,
				final IConnectionPoolInitializer dbConnectionPoolInitializer,
				final IRundeckConnectionInitializer rundeckConnectionInitializer,
				final Properties deploymentConfiguration,
				final DeploymentProfileFactory.ProfileSourceType profileSourceType,
				final IExecutionContext executionContext) {
			this.uniqueRunId = uniqueRunId;
			this.blueprintConfigFilePath = blueprintFilePath;
			this.profileFilePath = deploymentProfileFilePath;
			this.xmlFilePath = infraContextXmlFilePath;
			this.xsdFilePath = infraContextXSDFilePath;
			this.connectionPoolInitializer = dbConnectionPoolInitializer;
			this.rundeckConnectionInitializer = rundeckConnectionInitializer;
			this.project = AntUtil.initiateProject(FileHelper.getFile(this.blueprintConfigFilePath));
			this.deploymentConfig = initializeDeploymentConfig(profileSourceType, deploymentConfiguration, xmlFilePath, xsdFilePath, schemaPkg, objFactory);
			this.deploymentConfiguration = deploymentConfiguration;
			this.rundeckProject = initializeRundeckProject(rundeckConnectionInitializer, deploymentConfig);
			this.executionContext = executionContext;
		}

		/**
		 * initialize with a property object
		 *
		 * @param profileSourceType
		 * @param properties
		 * @return
		 */
		private static IDeploymentConfig initializeDeploymentConfig(DeploymentProfileFactory.ProfileSourceType profileSourceType, Properties properties, String xml, String xsd, String schemaPkg, String objFactory) {
			try {
				DeploymentConfigDecorator configDecorator = DeploymentConfigDecorator.newInstance(profileSourceType, properties);
				configDecorator.registerPlusDecorateDeploymentConfig(xml, xsd);
				IDeploymentConfig deploymentConfig = configDecorator.unmarshalDecoratedConfig(schemaPkg, objFactory);
				return deploymentConfig;
			} catch (InstantiationException ex) {
				l.error(ex.getMessage());
				throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
			} catch (IllegalAccessException ex) {
				l.error(ex.getMessage());
				throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
			} catch (ClassNotFoundException ex) {
				l.error(ex.getMessage());
				throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
			} catch (JAXBException ex) {
				l.error(ex.getMessage());
				throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
			} catch (SAXException ex) {
				l.error(ex.getMessage());
				throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
			} catch (ParserConfigurationException ex) {
				l.error(ex.getMessage());
				throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
			} catch (IOException ex) {
				l.error(ex.getMessage());
				throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
			} catch (ConfigurationException ex) {
				l.error(ex.getMessage());
				throw new FormitException.ConfigInitializationException("exception while bootstraping configuration", ex);
			}

		}

		/**
		 * initialize rundeck project
		 *
		 * @param rundeckConnectionInitializer1
		 * @param deploymentConfig
		 * @return
		 */
		private static RundeckProject initializeRundeckProject(IRundeckConnectionInitializer rundeckConnectionInitializer1, IDeploymentConfig deploymentConfig) {
			/*
			 * find jobs for the given project
			 * load profile properties
			 * tag jobs to VMs where they will run
			 * fire driver job
			 */
			RundeckHelper rundeckHelper = new RundeckHelper();
			RundeckClient rundeckClient = rundeckHelper.connect(rundeckConnectionInitializer1);
			// get project from rundeck & assert that it is same as that mentioned in
			// the infra definition & blueprint definition
			IProject rundeckProjectFromEnvironmentProfile = null;
			if (deploymentConfig instanceof Context) {
				rundeckProjectFromEnvironmentProfile = (((Context) deploymentConfig)).getProject();
			}
			String name = rundeckProjectFromEnvironmentProfile.getName();
			String sshKeyPath = rundeckProjectFromEnvironmentProfile.getSshKeypath();
			String resourcePath = rundeckProjectFromEnvironmentProfile.getResource();
			RundeckProject rundeckProject = rundeckClient.getProject(name);
			if (StringUtils.equalsIgnoreCase(name, rundeckProject.getName()) && StringUtils.equalsIgnoreCase(resourcePath, rundeckProject.getResourceModelProviderUrl())) {
				rundeckProject.setSshKeyPath(sshKeyPath);
				return rundeckProject;
			} else {
				throw new IllegalStateException("valid rundeck project unavailable");
			}
		}

		public ChainExecutionContext build() {
			return new ChainExecutionContext(this);
		}
	}
}
