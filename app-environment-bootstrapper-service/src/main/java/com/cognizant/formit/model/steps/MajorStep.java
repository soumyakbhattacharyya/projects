/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.model.steps;

/**
 *
 * @author Cognizant
 */
public interface MajorStep extends Step {

	/*
	 * Container types of steps require a new VM by default
	 */
	String CONTAINER = "CONTAINER";
	/*
	 * Executables can only exists within the context of a container
	 */
	String EXECUTABLE = "EXECUTABLE";
	/*
	 * Driver runs on RunDeck server only
	 */
	String DRIVER = "DRIVER";
}
