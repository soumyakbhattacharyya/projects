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
 * builds a generated job
 * 
 * @author cognizant
 */
public class BuildJobCommandHandler extends AbstractCommandHandler implements CommandHandler<BuildJobCommandParameters> {
	
	private static final Logger logger = Logger.getLogger(BuildJobCommandHandler.class.getName());

	@Override
	public void execute(final BuildJobCommandParameters params)
			throws PreflightCoreException {
		// here we run the processing logic to create a job on behalf of the
		// developer
		if (null != params) {		
			String[] args = params.list2Array(super.getLoginParams(params));
			boolean hasLoggedIn = super.doLogin(args);
			if (hasLoggedIn) {
				try {
					String[] buildJobArgs=params.list2Array(super.getBuildJobOperator(params));
					logger.info("Final parameters : " + Arrays.asList(buildJobArgs));
					int status = CLI._main(buildJobArgs);
					params.getJobName();
					if(status==-1){
						throw new PreflightCoreException.JenkinsCLIException("Building a job has returned an error");
					}
				} catch (Exception ex) {
					throw new PreflightCoreException.BuildJobException(
							"exception thrown while building job", ex);
				}
			} else {
				throw new PreflightCoreException.JenkinsLoginException(
						"Cannot log into Jenkins");
			}
			//logging out from jenkins 
			String[] logoutArgs = params.list2Array(super.getLogoutParams(params));
			super.doLogOut(logoutArgs,hasLoggedIn);			
		} else {
			throw new PreflightCoreException.BuildJobException(
					"null parameter is unacceptable while creating job");
		}
	}
}
