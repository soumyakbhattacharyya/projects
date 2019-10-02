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
 * a substep is something which is NOT a middleware installation step like war configuration and script execution
 *
 * @author Cognizant
 */
public class SubStep extends AbstractStep implements MinorStep {

	private static final Logger l = Logger.getLogger(SubStep.class);

	/**
	 * execute a substep after building options to invoke it properly
	 *
	 * @throws BuildException
	 */
	public void execute() throws BuildException {
		l.info("starting following step :" + this);
		Properties properties = super.prepareOptions(this.id);
		l.info("completed preparing options for invoking rundeck job :" + properties);
		// trigger job
		RundeckHelper rundeckHelper = getGoverningExecutor().getRundeckHelper();
		RundeckExecution execution = rundeckHelper.waitOnTrigger(id, properties);
		l.info("completed step .... outcome is following :: " + execution.getStatus());
		if (RundeckExecution.ExecutionStatus.FAILED.equals(execution.getStatus())) {
			throw new BuildException("following job has failed :" + this.id + " aborting executions");
		}
	}

	public String runOn() {
		return this.getTag();
	}
}
