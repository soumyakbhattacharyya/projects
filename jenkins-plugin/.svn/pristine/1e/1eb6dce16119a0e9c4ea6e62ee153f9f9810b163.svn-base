/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework;

import java.util.ArrayList;
import java.util.List;

import com.cognizant.cloudset.framework.command.Command;
import com.cognizant.cloudset.framework.command.CommandHandler;
import com.cognizant.cloudset.framework.command.CommandHandlerBinding;
import com.cognizant.cloudset.framework.command.cli.BuildJobCommandHandler;
import com.cognizant.cloudset.framework.command.cli.CreateJobCommandHandler;
import com.cognizant.cloudset.framework.command.cli.ShowBuildStatusCommandHandler;
import com.cognizant.cloudset.framework.command.cli.ViewJobCommandHandler;
import com.cognizant.cloudset.framework.command.file.CreateCommandHandler;
import com.cognizant.cloudset.framework.command.file.SyncCommandHandler;

/**
 * ExecutionContext
 *
 * Handles registering command handlers (binding them to commands) and executing them subsequently
 *
 * @author cognizant
 */
public class ExecutionContext {

	private List<CommandHandlerBinding> commandHandlerBindings = new ArrayList<CommandHandlerBinding>();

	public ExecutionContext() {
		super();
		this.initializeApplication();
	}

	public void initializeApplication() {
		registerCommandHandler(Command.CREATE_JOB, new CreateJobCommandHandler());
		registerCommandHandler(Command.BUILD_JOB, new BuildJobCommandHandler());
		registerCommandHandler(Command.GIT_CREATE, new CreateCommandHandler());
		registerCommandHandler(Command.GIT_SYNC, new SyncCommandHandler());
		registerCommandHandler(Command.GET_JOB, new ViewJobCommandHandler());
		registerCommandHandler(Command.SHOW_JOB, new ShowBuildStatusCommandHandler());
	}

	public <ParamType> void registerCommandHandler(Command<ParamType> command,
			CommandHandler<ParamType> handler) {
		CommandHandler<ParamType> existingHandler = getCommandHandler(command);
		if (existingHandler != null) {
			System.out.println("Command [" + command.getName() + "] already has a handler ["
					+ existingHandler.getClass().getName() + "] but is being set to [" + handler.getClass().getName()
					+ "]");
		}
		commandHandlerBindings.add(new CommandHandlerBinding<ParamType>(command, handler));
	}

	private <ParamType, ReturnType> CommandHandler<ParamType> getCommandHandler(Command<ParamType> command) {
		if (null != commandHandlerBindings && commandHandlerBindings.size() != 0) {
			for (CommandHandlerBinding<ParamType> binding : commandHandlerBindings) {
				if (binding.getCommand().getName().equals(command.getName())) {
					return binding.getHandler();
				}
			}
		}
		return null;
	}

	public <ParamType> void executeCommand(Command<ParamType> command,
			ParamType parameters) throws Exception {
		CommandHandler<ParamType> handler = getCommandHandler(command);
		if (handler == null) {
			throw new NullPointerException("No CommandHandler registered for Command [" + command.getName() + "]");
		}
		handler.execute(parameters);
	}
}
