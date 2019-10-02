/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.command;

import com.cognizant.jpaas2.api.domain.AppEnvInstance;

/**
 * Factory for producing a command
 *
 * @author Cognizant
 */
public enum CommandFactory {

	/**
	 * List of available commands
	 */
	GET_DEPLOYMENT_STATUS(GetDeploymentStatusCommand.class),
	GET_ENVIRONMENT_STATUS(GetEnvironmentStatusCommand.class),
	LAUNCH(LaunchCommand.class),
	TEAR_DOWN(TearDownCommand.class);
	private Class<? extends Command> mappedClass;

	private CommandFactory(Class<? extends Command> c) {
		mappedClass = c;
	}

	/**
	 * get a new command instance
	 *
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Command newCommand(AppEnvInstance appEnvInstance) throws InstantiationException, IllegalAccessException {
		Command command = mappedClass.newInstance();
		// set the instance that it uses for driving the deployment
		command.setAppEnvInstance(appEnvInstance);
		return command;
	}
}
