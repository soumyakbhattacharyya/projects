/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.command;

import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import java.util.concurrent.Callable;

/**
 * command interface that all concrete commands supports
 *
 * @author Cognizant
 */
public interface Command {

	/**
	 * execute a specific command
	 */
	public void execute();

	/**
	 * set AppEnvInstance which is used as an argument for command execution
	 *
	 * @param appEnvInstance
	 */
	public void setAppEnvInstance(AppEnvInstance appEnvInstance);
}
