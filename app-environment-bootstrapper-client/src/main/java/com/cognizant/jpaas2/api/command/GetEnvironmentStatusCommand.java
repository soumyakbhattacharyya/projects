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
import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import com.cognizant.jpaas2.api.domain.AppEnvInstanceDetail;
import com.cognizant.jpaas2.api.helper.AppEnvParamsHelper;
import com.cognizant.jpaas2.api.logger.LogHelper;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Cognizant
 */
public final class GetEnvironmentStatusCommand implements Command {

	private static final Logger l = Logger.getLogger(TearDownCommand.class);
	private AppEnvInstance appEnvInstance;
	private final CommandHelper commandHelper = new CommandHelper();
	private final AppEnvParamsHelper appEnvParamsHelper = AppEnvParamsHelper.newInstance();
	private LogHelper logHelper;

	public void execute() {

		// TODO : Get app environment detail only if deployment is a success / failure
		if (AppEnvInstance.ExecutionStatus.SUCCEEDED.toString().equals(appEnvInstance.getStatus())
				|| (AppEnvInstance.ExecutionStatus.FAILED.toString().equals(appEnvInstance.getStatus()))) {
			try {
				// newExecutor parameters which SHOULD be part of this launch
				Properties defaultProperties = appEnvParamsHelper.getSupportedProperties(appEnvInstance);
				// validate if these properties are mentioned while invoking the launch
				Properties suppliedProperties = appEnvParamsHelper.getRuntimeProperties(appEnvInstance);
				// if both are not null
				if (null != defaultProperties && null != suppliedProperties) {
					// and they matches by keys                
					if (appEnvParamsHelper.matches(suppliedProperties, defaultProperties)) {
						String blueprint = SingletonDriver.getCONFIG_FILE_PATH() + "xml/blueprint.xml";
						String profile = SingletonDriver.getCONFIG_FILE_PATH() + SingletonDriver.getInstance().getClientProperties().profile();
						String xml = SingletonDriver.getCONFIG_FILE_PATH() + SingletonDriver.getInstance().getClientProperties().xml();
						String xsd = SingletonDriver.getCONFIG_FILE_PATH() + SingletonDriver.getInstance().getClientProperties().xsd();
						Properties sync = PropertyUtil.read(profile);
						PropertyUtil.merge(suppliedProperties, sync);
						ChainExecutionContext executionContext = ChainExecutionContextFactory
								.newExecutionContext(appEnvInstance.getInstanceId(),
								blueprint,
								profile,
								xml,
								xsd,
								commandHelper.getConnectionPoolInitializer(),
								commandHelper.getRundeckConnectionInitializer(),
								sync,
								DeploymentProfileFactory.ProfileSourceType.IN_MEMORY, commandHelper.getExecutionContext(appEnvInstance));
						SequenceDriver.loadResource(SequenceDriver.SequenceType.GET_ENVIRONMENT, executionContext).triggerSequence();
						getAppEnvInstance().setMachines(executionContext.getMachines());
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	}

	public AppEnvInstance getAppEnvInstance() {
		return appEnvInstance;
	}

	public void setAppEnvInstance(AppEnvInstance appEnvInstance) {
		this.appEnvInstance = appEnvInstance;
	}
}
