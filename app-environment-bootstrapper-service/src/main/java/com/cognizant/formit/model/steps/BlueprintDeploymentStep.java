/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.model.steps;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;

/**
 * does nothing except being the default target that triggers rest jobs blueprint is the boss!!
 */
public class BlueprintDeploymentStep extends AbstractStep implements MajorStep {

	private static final Logger l = Logger.getLogger(BlueprintDeploymentStep.class);

	// execute the task
	public void execute() throws BuildException {
		l.info("Doing nothing here ... just a placeholder for future use " + this);
	}

	@Override
	public String toString() {
		return "BlueprintDeployment{" + super.toString() + '}';
	}

	public String runOn() {
		return this.getTag();
	}
}
