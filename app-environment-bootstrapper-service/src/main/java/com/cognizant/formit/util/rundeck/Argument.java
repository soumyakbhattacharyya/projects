/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.rundeck;

import com.cognizant.formit.chain.ChainExecutionContext;
import org.apache.tools.ant.Project;

/**
 *
 * @author cognizant
 */
public class Argument {

	private final Project project;
	private final ChainExecutionContext executionContext;
	private final String key;

	public Argument(Project project, ChainExecutionContext executionContext, String key) {
		this.project = project;
		this.executionContext = executionContext;
		this.key = key;
	}

	public Project getProject() {
		return project;
	}

	public ChainExecutionContext getExecutionContext() {
		return executionContext;
	}

	public String getKey() {
		return key;
	}
}
