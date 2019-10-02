/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.command;

import com.cognizant.formit.chain.ChainExecutionContext;
import com.cognizant.formit.chain.ChainExecutionContextFactory;
import com.cognizant.formit.chain.SequenceDriver;
import com.cognizant.formit.model.DeploymentProfileFactory;
import com.cognizant.formit.util.property.PropertyUtil;
import com.cognizant.jpaas2.api.SingletonDriver;
import com.cognizant.jpaas2.api.command.helper.CommandHelper;
import com.cognizant.jpaas2.api.command.helper.observer.LaunchCommandObserver;
import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import com.cognizant.jpaas2.api.helper.AppEnvParamsHelper;
import com.cognizant.jpaas2.api.logger.LogHelper;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 *
 * @author Cognizant
 */
public final class LaunchCommand implements Command {

	private static final Logger l = Logger.getLogger(LaunchCommand.class);
	private final AppEnvParamsHelper appEnvParamsHelper = AppEnvParamsHelper.newInstance();
	private AppEnvInstance appEnvInstance;
	private LogHelper logHelper;
	private final CommandHelper commandHelper = new CommandHelper();

	/**
	 * execute a launch request
	 */
	public void execute() {
		try {
			// newExecutor parameters which SHOULD be part of this launch
			Properties defaultProperties = appEnvParamsHelper.getSupportedProperties(appEnvInstance);
			// validate if these properties are mentioned while invoking the launch
			Properties suppliedProperties = appEnvParamsHelper.getRuntimeProperties(appEnvInstance);
			// if both are not null
			if (null != defaultProperties && null != suppliedProperties) {
				// and they matches by keys                
				if (appEnvParamsHelper.matches(suppliedProperties, defaultProperties)) {
					l.info(logHelper.getLoggable(LogHelper.EXECUTOR_INITIALIZATION));
					String blueprint = SingletonDriver.getCONFIG_FILE_PATH() + "xml/"+appEnvInstance.getAppEnvMaster().getBlueprintFileId().trim()+".xml";
					
					
					// Modified code to get the deployment profile from db so that we can use multiple deployment profiles
					//String profile = SingletonDriver.getCONFIG_FILE_PATH() + SingletonDriver.getInstance().getClientProperties().profile();
					String profile = SingletonDriver.getCONFIG_FILE_PATH() +"properties/"+appEnvInstance.getAppEnvMaster().getDeploymentFileId()+".properties";
					
					String xml = SingletonDriver.getCONFIG_FILE_PATH() + SingletonDriver.getInstance().getClientProperties().xml();
					String xsd = SingletonDriver.getCONFIG_FILE_PATH() + SingletonDriver.getInstance().getClientProperties().xsd();
					Properties sync = PropertyUtil.read(profile);
					PropertyUtil.merge(suppliedProperties, sync);
					final ChainExecutionContext executionContext = ChainExecutionContextFactory
							.newExecutionContext(appEnvInstance.getInstanceId(),
							blueprint,
							profile,
							xml,
							xsd,
							commandHelper.getConnectionPoolInitializer(),
							commandHelper.getRundeckConnectionInitializer(),
							sync,
							DeploymentProfileFactory.ProfileSourceType.IN_MEMORY, commandHelper.getExecutionContext(appEnvInstance));
					commandHelper.updateInstanceStatus(appEnvInstance, AppEnvInstance.ExecutionStatus.RUNNING, false);
					executionContext.getMessageMap().addObserver(new LaunchCommandObserver(executionContext));
					SequenceDriver.loadResource(SequenceDriver.SequenceType.LAUNCH, executionContext).triggerSequence();
					commandHelper.updateInstanceStatus(appEnvInstance, AppEnvInstance.ExecutionStatus.SUCCEEDED, false);
					commandHelper.updateInstanceVMDetail(appEnvInstance, getVmDetail(executionContext));

				}
			}else{
				l.error("Exception occurred for Properties mismatch between suppliedProperties " + suppliedProperties + " and default properties " + defaultProperties);
			}
		} catch (Exception ex) {
			commandHelper.updateInstanceStatus(appEnvInstance, AppEnvInstance.ExecutionStatus.FAILED, false);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public void setAppEnvInstance(AppEnvInstance appEnvInstance) {
		this.appEnvInstance = appEnvInstance;
		// initialize custom logger
		this.logHelper = LogHelper.newLogHelper(appEnvInstance);
	}

	public AppEnvInstance getAppEnvInstance() {
		return appEnvInstance;
	}

	private String getVmDetail(ChainExecutionContext executionContext) {
		String vmTagsConcatenated = "";
		if (null != executionContext) {
			Set<String> vmTags = executionContext.getVms().keySet();
                        
                        
			l.info("Set<String> vmTags is "+ vmTags);
			if (vmTags != null && vmTags.size() > 0) {
				for (String str : vmTags) {
                                    String hostname= "not-found";
				    if(executionContext.getVms().get(str).getHostname()!= null){
                                        hostname = executionContext.getVms().get(str).getHostname();
                                        if(hostname.contains(":")){
                                            hostname = hostname.substring(0,hostname.indexOf(":"));
                                        }
                                    }	
                                    vmTagsConcatenated = vmTagsConcatenated + executionContext.getVms().get(str).getName() +"#"+hostname+ "$";

				}
				return vmTagsConcatenated;
			} else {
				throw new AssertionError("Impossible to not find VMs though even it has been a successful deployment");
			}
		} else {
			throw new AssertionError("execution context is null");
		}
	}
}
