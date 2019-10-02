/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.rundeck;

/**
 * represents credentials being used to connect to a rundeck server instance
 *
 * @author Cognizant
 */
public interface IRundeckConnectionInitializer {

	String serverEndpoint();

	String uid();

	String pwd();
	
	String token();
}
