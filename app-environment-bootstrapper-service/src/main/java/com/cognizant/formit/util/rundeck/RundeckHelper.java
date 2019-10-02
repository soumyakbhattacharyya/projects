/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.rundeck;

import com.cognizant.formit.chain.ChainExecutionContext;
import com.cognizant.formit.main.AppConstants;
import com.cognizant.formit.main.Executor;
import com.cognizant.formit.main.SequentialExecutor;
import com.cognizant.formit.model.RNodeFactory;
import com.cognizant.formit.util.rundeck.decorator.IPValueFindingDecorator;
import com.cognizant.formit.util.rundeck.decorator.ReplaceableValueFindingDecorator;
import com.cognizant.formit.util.string.StringUtil;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.rundeck.api.FileType;
import org.rundeck.api.OptionsBuilder;
import org.rundeck.api.RundeckClient;
import org.rundeck.api.domain.RundeckExecution;
import org.rundeck.api.domain.RundeckJob;
import org.rundeck.api.domain.RundeckProject;

/**
 * hosts utility APIs to work with Rundeck server
 *
 * @author Cognizant
 */
public class RundeckHelper {

	// should be initialized by the invoking context
	private RundeckClient rundeckClient;
	// private static final RundeckHelper me = new RundeckHelper();
	private static final Logger l = Logger.getLogger(RundeckHelper.class);

	public RundeckHelper() {
	}

	/**
	 * create a connection to rundeck server using uid / pwd scheme typically this contextual information are expected to be supplied from
	 * external configuration file
	 *
	 * @param connectionInitializer encapsulate server endpoint, user id and pwd
	 * @return connection to be reused later
	 */
	public RundeckClient connect(IRundeckConnectionInitializer connectionInitializer) {
		if (null == rundeckClient) {
			/*rundeckClient = new RundeckClient(connectionInitializer.serverEndpoint(),
					connectionInitializer.uid(),
					connectionInitializer.pwd());*/
			l.info("Rundeck token "+connectionInitializer.token());
			rundeckClient = new RundeckClient(connectionInitializer.serverEndpoint(),
					connectionInitializer.token());
			
			
			return rundeckClient;
		} else {
			return rundeckClient;
		}
	}

	/**
	 * finds a project .. kind of safe way before querying for consecutive job this
	 *
	 * @param client
	 * @param project
	 * @return rundeck project
	 */
	public RundeckProject findProject(Project project) {

		// get the name of the project and fetch the meta definition of the same
		// from rundeck server
		RundeckProject rundeckProject = rundeckClient.getProject(project.getName());
		return (rundeckProject == null) ? null : rundeckProject;
	}

	/**
	 * Get a job for a given job id
	 *
	 * @param client
	 * @param jobId
	 * @return
	 */
	public RundeckJob findJob(String jobId) {
		return rundeckClient.getJob(jobId);
	}

	/**
	 * Get an input stream corresponding to a job
	 *
	 * @param jobId
	 * @return
	 */
	public InputStream getJobFile(String jobId) {
		InputStream stream = rundeckClient.exportJob(FileType.XML, jobId);
		return stream;
	}

	/**
	 * Prepares options to be passed while invoking a job
	 *
	 * @param map with actual parameter name - value pairs which acts as a data store for loading options
	 * @param options of the job which should be updated with the values as it is being found within the store
	 * @return properties which can be supplied to the job
	 */
	public Properties createOptions(Map<String, String> map, List<String> options) {
		OptionsBuilder optionsBuilder = new OptionsBuilder();
		for (String option : options) {
			String value = map.get(option);
			optionsBuilder.addOption(option, value);
		}
		return optionsBuilder.toProperties();
	}

	/**
	 * for a given job id, finds option for it, finds corresponding value from the project context, for cases where IP is the required
	 * value, finds it from the executor's context and finally produce a complete option for invoking the job TODO : Refactor this to use
	 * strategy
	 *
	 * @param id given job id
	 * @param _project ANT project context that drives this job (task)
	 * @param executor that hosts all available VMs tagged for executing the tasks
	 * @return option for invoking the job
	 */
	public Properties getJobOptionLoadedWithActualVal(String id, Project _project, ChainExecutionContext executionContext) {
		Properties properties = null;
		RundeckJob rundeckJob = rundeckClient.getJob(id);
		List<String> options = rundeckJob.getOptions();
		l.debug("for job represented with id :" + id + " following options have been discovered :" + options);
		if (null != options && options.size() > 0) {
			OptionsBuilder optionsBuilder = new OptionsBuilder();
			for (String key : options) {
				String val = _project.getUserProperty(key);
				l.debug("value corresponding to the key :" + key + " is following :" + val);
				// in case of referenced value of the following format ${*} ... 
				// remove ${ from begining and } from end
				// then lookup again with the resultant as key to get corresponding
				// value from project				
				if (isValidKey(val)) {
					l.debug("valid key is " + val);
					// strip of initial ${
					String valWithoutPrefixedBraces = val.substring(2);
					// strip end of the word "}" character
					String valToBeTreatedAsKey = valWithoutPrefixedBraces.substring(0, valWithoutPrefixedBraces.indexOf(AppConstants.END_BRACKET));
					l.debug("value prior to be processed for removal of preceding job reference " + valToBeTreatedAsKey);
					val = _project.getUserProperty(StringUtil.getKeyNameMinusJobnamePrefix(valToBeTreatedAsKey));
					l.debug("new value after resolving reference " + val);
				}
				// find IP for a tag
				if (StringUtils.isNotBlank(val) && val.endsWith(AppConstants._IP_SUFFIX)) {
					val = RNodeFactory.findIPFromFittingNode(val, executionContext.getVms());
				};
				// this ensures a job is being called with argument of key=val format ..
				if (!key.contains("script")) {
					optionsBuilder.addOption(key, key + "=" + val);
				} else {
					optionsBuilder.addOption(key, val);
				}
			}

			properties = optionsBuilder.toProperties();
			l.info("properties to be passed to job :" + properties);
			return properties;
		}
		return properties;
	}

	/**
	 * for a given job id, finds option for it, finds corresponding value from the project context, for cases where IP is the required
	 * value, finds it from the executor's context and finally produce a complete option for invoking the job TODO : Refactor this to use
	 * strategy
	 *
	 * @param id given job id
	 * @param _project ANT project context that drives this job (task)
	 * @param executor that hosts all available VMs tagged for executing the tasks
	 * @return option for invoking the job
	 */
	public Properties loadJobOptions(String id, Project _project, ChainExecutionContext executionContext) {
		Properties properties = null;
		RundeckJob rundeckJob = rundeckClient.getJob(id);
		List<String> options = rundeckJob.getOptions();
		l.debug("for job represented with id :" + id + " following options have been discovered :" + options);
		if (null != options && options.size() > 0) {
			OptionsBuilder optionsBuilder = new OptionsBuilder();
			for (String key : options) {
				Argument argument = new Argument(_project, executionContext, key);
				String value = new IPValueFindingDecorator(new ReplaceableValueFindingDecorator(new SimpleValueFinder(argument))).get();
				// this ensures a job is being called with argument of key=val format ..
				if (!key.contains("script")) {
					optionsBuilder.addOption(key, key + "=" + value);
				} else {
					optionsBuilder.addOption(key, value);
				}
			}
			properties = optionsBuilder.toProperties();
			l.info("properties to be passed to job :" + properties);
			return properties;
		}
		return properties;
	}

	/**
	 * find if the argument is a valid key : a valid key is of following format ${key}
	 *
	 * @param val
	 * @return
	 */
	private boolean isValidKey(String val) {
		return StringUtils.isNotEmpty(val)
				&& StringUtils.startsWith(val, AppConstants.DOLLAR_AND_START_BRACKET)
				&& StringUtils.endsWith(val, AppConstants.END_BRACKET)
				&& !StringUtils.endsWith(val, AppConstants._IP_SUFFIX);
	}

	/**
	 * Trigger a job and wait for its completion
	 *
	 * @return
	 */
	public RundeckExecution waitOnTrigger(String jobId, Properties properties) {
		if (null != properties) {
			return rundeckClient.runJob(jobId, properties);
		} else {
			return rundeckClient.runJob(jobId);
		}
	}

	public RundeckClient getRundeckClient() {
		return rundeckClient;
	}

	public void setRundeckClient(RundeckClient rundeckClient) {
		this.rundeckClient = rundeckClient;
	}
}
