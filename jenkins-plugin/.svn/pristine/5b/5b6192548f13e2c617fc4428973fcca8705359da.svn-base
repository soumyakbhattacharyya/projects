/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.command.cli;

import hudson.cli.CLI;

import java.util.Arrays;
import java.util.logging.Logger;

import org.apache.maven.plugin.MojoExecutionException;

import com.cognizant.cloudset.framework.command.CommandHandler;
import com.cognizant.cloudset.framework.exception.PreflightCoreException;

/**
 * creates a new job in jenkins on behalf of the developer
 * 
 * @author cognizant
 */
public class CreateJobCommandHandler extends AbstractCommandHandler implements
		CommandHandler<CreateJobCommandParameters> {
	private static final Logger logger = Logger
			.getLogger(CreateJobCommandHandler.class.getName());

	@Override
	public void execute(CreateJobCommandParameters params)
			throws PreflightCoreException {
		// here we run the processing logic to create a job on behalf of the
		// developer
		if (null != params) {
			String[] args = params.list2Array(super.getLoginParams(params));
			logger.info("Login parameters : " + Arrays.asList(args));
			boolean hasLoggedIn = super.doLogin(args);
			if (hasLoggedIn) {
				try {
					String[] paramArr = params.list2Array(super
							.getCreateJobOperator(params));
					logger.info("Final parameters : " + Arrays.asList(paramArr));
					int status = CLI._main(paramArr);

					if (status == -1) {
						throw new PreflightCoreException.JenkinsCLIException(
								"Creating a job has returned an error");
					}
				} catch (Exception ex) {
					throw new PreflightCoreException.CreateJobException(
							"Exception thrown while creating job", ex);
				}
			} else {
				throw new PreflightCoreException.JenkinsLoginException(
						"Couldnot log into the Jenkins");
			}
			String[] logoutArgs = params.list2Array(super
					.getLogoutParams(params));
			super.doLogOut(logoutArgs, hasLoggedIn);

		} else {
			throw new PreflightCoreException.CreateJobException(
					"Null parameter is unacceptable while creating job");
		}
	}
}
