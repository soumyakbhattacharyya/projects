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
import com.cognizant.jpaas2.api.command.helper.observer.TerminateCommandObserver;
import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import com.cognizant.jpaas2.api.helper.AppEnvParamsHelper;
import com.cognizant.jpaas2.api.logger.LogHelper;
import com.cognizant.jpaas2.api.persistence.HibernateUtil;
import com.cognizant.jpaas2.api.persistence.factory.DAOFactory;
import com.cognizant.jpaas2.api.persistence.impl.AppEnvInstanceDAO;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * Command to teardown an instance as long as it is not in a starting / running state
 *
 * @author Cognizant
 */
public final class TearDownCommand implements Command {

	private static final Logger l = Logger.getLogger(TearDownCommand.class);
	private AppEnvInstance appEnvInstance;
	private final CommandHelper commandHelper = new CommandHelper();
	private final AppEnvParamsHelper appEnvParamsHelper = AppEnvParamsHelper.newInstance();
	private LogHelper logHelper;

	public void execute() {
		// find the instance
		// tear down an instance if it is not running
		if (!AppEnvInstance.ExecutionStatus.RUNNING.toString().equalsIgnoreCase(getAppEnvInstance().getStatus())
				&& !AppEnvInstance.ExecutionStatus.SCHEDULED.toString().equalsIgnoreCase(getAppEnvInstance().getStatus())) {
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
						String profile = SingletonDriver.getCONFIG_FILE_PATH() +"properties/"+appEnvInstance.getAppEnvMaster().getDeploymentFileId()+".properties";
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
						executionContext.getMessageMap().addObserver(new TerminateCommandObserver(executionContext));
						SequenceDriver.loadResource(SequenceDriver.SequenceType.TERMINATE, executionContext).triggerSequence();
						commandHelper.updateInstanceStatus(appEnvInstance, AppEnvInstance.ExecutionStatus.TORNDOWN_COMPLETED, true);

					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				commandHelper.updateInstanceStatus(appEnvInstance, AppEnvInstance.ExecutionStatus.TORNDOWN_FAILED, false);
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
