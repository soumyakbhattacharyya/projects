/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.model.steps;

import com.cognizant.formit.util.rundeck.RundeckHelper;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.rundeck.api.domain.RundeckExecution;

/**
 *
 * components are middle tier program viz. web server, http server, database server, queues etc. this represents the step to manage the
 * component
 */
public class ContainerDeploymentStep extends BlueprintDeploymentStep implements MajorStep {

	private static final Logger l = Logger.getLogger(ContainerDeploymentStep.class);

	/**
	 * Execute the container deployment steps. Find a job within given rundeck context. Load the job's option using the profile properties
	 * trigger the job return once the job completes successfully
	 *
	 * @throws BuildException
	 */
	public void execute() throws BuildException {
		l.info("starting following step :" + this);
		Properties properties = super.prepareOptions(this.id);
		// trigger job
		RundeckHelper rundeckHelper = getGoverningExecutor().getRundeckHelper();
		RundeckExecution execution = rundeckHelper.waitOnTrigger(id, properties);
		l.info("completed step .... out is following :: " + execution.getStatus());
		if (RundeckExecution.ExecutionStatus.FAILED.equals(execution.getStatus())) {
			throw new BuildException("Following job has failed :" + this.id);
		}
	}

	@Override
	public String toString() {
		return "ComponentDeployment{" + super.toString() + '}';
	}

	public String runOn() {
		return this.getTag();
	}
}
