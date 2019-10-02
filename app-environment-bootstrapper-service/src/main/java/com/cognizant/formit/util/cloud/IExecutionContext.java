/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.cloud;

/**
 * consists of user specific information which are required to be passed to the cloud API
 *
 * @author Cognizant
 */
public interface IExecutionContext {

	String accountNumber();

	String projectId();

	String uId();

	String reason();

	String description();

	String rollId();

	String serviceId();
}
