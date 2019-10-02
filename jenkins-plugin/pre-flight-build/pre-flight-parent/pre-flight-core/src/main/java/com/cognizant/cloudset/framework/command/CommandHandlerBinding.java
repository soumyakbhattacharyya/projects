/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.command;

import com.cognizant.cloudset.framework.command.CommandHandler;
import com.cognizant.cloudset.framework.command.Command;

/**
 * bind command to a specific commandhandler for a given parameter type
 */
public class CommandHandlerBinding<ParamType> {

	private Command<ParamType> command;
	private CommandHandler<ParamType> handler;

	public CommandHandlerBinding(Command<ParamType> command, CommandHandler<ParamType> handler) {
		this.command = command;
		this.handler = handler;
	}

	public Command<ParamType> getCommand() {
		return command;
	}

	public CommandHandler<ParamType> getHandler() {
		return handler;
	}
}
