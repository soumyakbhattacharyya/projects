/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.chain;

import com.cognizant.formit.main.*;
import com.cognizant.formit.model.DeploymentProfileFactory;
import com.cognizant.formit.model.DeploymentProfileFactory.ProfileSourceType;
import com.cognizant.formit.util.cloud.IExecutionContext;
import com.cognizant.formit.util.db.IConnectionPoolInitializer;
import com.cognizant.formit.util.rundeck.IRundeckConnectionInitializer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * factory for creating & retrieving already existing executors for a given run id
 *
 * @author Cognizant
 */
public class ChainExecutionContextFactory {

	private static final Map<String, ChainExecutionContext> executors = Collections.synchronizedMap(new HashMap<String, ChainExecutionContext>());
	private static final Logger l = Logger.getLogger(ChainExecutionContextFactory.class);

	/**
	 * newExecutor an executor which was associated with the run id
	 *
	 * @param runId
	 * @return executor that has been found or null
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static ChainExecutionContext getExecutionContext(final String instanceId) {
		if (StringUtils.isBlank(instanceId)) {
			throw new IllegalArgumentException("must provide runid");
		}
		if (executors.containsKey(instanceId)) {
			l.debug("found existing executor with following run id :" + instanceId);
			return executors.get(instanceId);
		}
		return null;
	}

	public static ChainExecutionContext newExecutionContext(String instanceId, String blueprint, String profile, String xml, String xsd, IConnectionPoolInitializer connectionPoolInitializer, IRundeckConnectionInitializer rundeckConnectionInitializer, Properties sync, ProfileSourceType profileSourceType, IExecutionContext context) {
		ChainExecutionContext executionContext = new ChainExecutionContext.Builder(instanceId,
				blueprint,
				profile,
				xml,
				xsd,
				connectionPoolInitializer,
				rundeckConnectionInitializer,
				sync,
				profileSourceType, context).build();
		executors.put(instanceId, executionContext);
		return executionContext;
	}

	/**
	 * Executor type : Sequential (for executing deployment steps sequentially) Parallel (for executing deployment steps parallelly)
	 */
	public static enum ExecutorType {

		SEQUENTIAL, PARALLEL;
	}
}
