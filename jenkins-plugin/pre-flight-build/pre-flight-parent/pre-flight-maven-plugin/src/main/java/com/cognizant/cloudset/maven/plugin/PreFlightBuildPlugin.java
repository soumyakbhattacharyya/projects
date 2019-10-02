package com.cognizant.cloudset.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.cognizant.cloudset.framework.API;
import com.cognizant.cloudset.framework.Constants;
import com.cognizant.cloudset.framework.command.Command;
import com.cognizant.cloudset.framework.command.cli.BuildJobCommandParameters;
import com.cognizant.cloudset.framework.command.cli.CreateJobCommandParameters;
import com.cognizant.cloudset.framework.command.cli.ShowBuildStatusCommandParameters;
import com.cognizant.cloudset.framework.command.cli.ViewJobCommandParameters;
import com.cognizant.cloudset.framework.command.file.CreateCommandParameters;
import com.cognizant.cloudset.framework.command.file.SyncCommandParameters;
import com.cognizant.cloudset.framework.exception.PreflightCoreException;
import com.cognizant.cloudset.framework.util.GitUtil;
import com.cognizant.cloudset.framework.util.PropertyUtil;

/**
 * Goal which launches a pre-flight build
 * 
 * @goal run
 * 
 * @phase compile
 */
public class PreFlightBuildPlugin extends AbstractMojo {

	/**
	 * lifecycle parameters of maven build
	 * 
	 * @parameter expression="${run.buildCommand}"
	 * @required
	 */

	private String buildCommand;
	/**
	 * environment label to be built on.
	 * 
	 * @parameter expression="${run.environment}"
	 * @required
	 */

	private String environment;
	/**
	 * requester id of the build
	 * 
	 * @parameter expression="${run.requesterId}"
	 * @required
	 */

	private String requesterId;
	/**
	 * requester mail id of the build process
	 * 
	 * @parameter expression="${run.requesterMailId}"
	 * @required
	 */
	private String requesterMailId;

	/**
	 * name of the project or workspace to be build
	 * 
	 * @parameter expression="${run.workspaceName}"
	 * @required
	 */
	private String workspaceName;

	/**
	 * path of the project or workspace to be build
	 * 
	 * @parameter expression="${run.workspaceAbsPath}"
	 * @required
	 */
	private String workspaceAbsPath;
	/**
	 * name of the project or workspace to be build
	 * 
	 * @parameter expression="${run.isSonarEnable}"
	 */
	private String isSonarEnable;
	/**
	 * id of the project on which job will be run
	 * 
	 * @parameter expression="${run.projectID}"
	 */
	private String projectID;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {		
		API app = API._new();

		getLog().info("************************************************************");
		getLog().info("************* PRE FLIGHT BUILD - STARTS ********************");

		try {			
			app.executeCommand(Command.GIT_CREATE, getCreateCommandParameters());
			app.executeCommand(Command.GIT_SYNC, getSyncCommandParameters());
			CreateJobCommandParameters createJobCommandParameters = getCreateJobCommandParameters();
			getLog().info(">> Creating job on behalf of developer");
			app.executeCommand(Command.CREATE_JOB, createJobCommandParameters);
			getLog().info(">> Finding status of the execution");
			app.executeCommand(Command.SHOW_JOB,getShowBuildStatusCommandParameters(Constants.NAME_OF_JOB_GENERATOR, projectID));
			getLog().info(">> building generated job");
			BuildJobCommandParameters buildJobCommandParameters = getBuildJobCommandParameters(requesterId, projectID);
			app.executeCommand(Command.BUILD_JOB, buildJobCommandParameters);
			getLog().info(">> Finding status of the execution");
			app.executeCommand(Command.SHOW_JOB,getShowBuildStatusCommandParameters(buildJobCommandParameters.getJobName(), projectID));
		} catch (PreflightCoreException exp) {
			throw new MojoExecutionException("problem occured during preflight build", exp);
		} catch (Exception e) {			
			throw new MojoExecutionException("problem occured during preflight build", e);
		}	
		getLog().info("************* PRE FLIGHT BUILD - ENDS ********************");
	}

	private CreateCommandParameters getCreateCommandParameters() {
		return new CreateCommandParameters(workspaceAbsPath, workspaceName,
				Constants.LAST_PREFLIGHT_TIMESTAMP);
	}

	private CreateJobCommandParameters getCreateJobCommandParameters() {
		String checkOutFrom = PropertyUtil.get(Constants.ORIGIN);
		return new CreateJobCommandParameters(checkOutFrom, buildCommand,
				environment, requesterId, requesterMailId, workspaceName,
				isSonarEnable, projectID);
	}

	private SyncCommandParameters getSyncCommandParameters() {
		return new SyncCommandParameters(workspaceAbsPath, workspaceName,
				Constants.LAST_PREFLIGHT_TIMESTAMP);
	}

	private BuildJobCommandParameters getBuildJobCommandParameters(
			String requesterId, String projectID) {
		return new BuildJobCommandParameters(requesterId + Constants.GENERATED_JOB_POSTFIX, projectID);
	}

	private ShowBuildStatusCommandParameters getShowBuildStatusCommandParameters(
			String jobName, String projectId) {
		return new ShowBuildStatusCommandParameters(jobName, projectID);

	}

}
