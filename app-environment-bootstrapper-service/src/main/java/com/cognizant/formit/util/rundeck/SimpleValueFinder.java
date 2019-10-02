/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.rundeck;

import com.cognizant.formit.chain.ChainExecutionContext;
import org.apache.tools.ant.Project;

/**
 * find the value from project
 *
 * @author cognizant
 */
public class SimpleValueFinder implements Value {

	private Argument argument;

	public SimpleValueFinder(Argument argument) {
		this.argument = argument;
	}

	public Argument getArgument() {
		return argument;
	}

	@Override
	public String get() {
		return argument.getProject().getUserProperty(argument.getKey());
	}
}
