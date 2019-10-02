package com.cognizant.cloudset.framework;

import com.cognizant.cloudset.framework.command.Command;
import com.cognizant.cloudset.framework.command.cli.CreateJobCommandParameters;
import com.cognizant.cloudset.framework.command.cli.ViewJobCommandParameters;

/**
 * entry point for the preflight utility
 * typical invocation will be as follows
 * TODO : document some typical usage
 *
 */
public class API {

	private ExecutionContext executionContext = new ExecutionContext();

	public <ParamType> void executeCommand(Command<ParamType> command, ParamType parameters) throws Exception {
		getExecutionContext().executeCommand(command, parameters);
	}

	private ExecutionContext getExecutionContext() {
		return executionContext;
	}

	// native factory
	public final static API _new() {
		return new API();
	}
	/*
	public static void main(String args[]){
		API app = API._new();
		try {
			//app.executeCommand(Command.CREATE_JOB, new CreateJobCommandParameters("ssh://309657@10.242.138.27:29418/spring-petclinic-master.git", "clean", "unix", "309657", "developer@acme.com","spring-petclinic-master","yes","P-26886"));
			//app.executeCommand(Command.BUILD_JOB, new BuildJobCommandParameters("309657-pb","P-26886"));
			app.executeCommand(Command.GET_JOB, new ViewJobCommandParameters("309657","P-26886"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}