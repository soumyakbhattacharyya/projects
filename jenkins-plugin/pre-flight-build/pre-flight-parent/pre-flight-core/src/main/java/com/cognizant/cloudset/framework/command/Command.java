/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.command;

import com.cognizant.cloudset.framework.command.cli.BuildJobCommandParameters;
import com.cognizant.cloudset.framework.command.cli.CreateJobCommandParameters;
import com.cognizant.cloudset.framework.command.cli.ShowBuildStatusCommandParameters;
import com.cognizant.cloudset.framework.command.cli.ViewJobCommandParameters;
import com.cognizant.cloudset.framework.command.file.CreateCommandParameters;
import com.cognizant.cloudset.framework.command.file.SyncCommandParameters;

/**
 * a placeholder to hold laundry list of available commands
 *
 * @author cognizant
 */
public class Command<ParamType> {

	public static final Command<CreateJobCommandParameters> CREATE_JOB = new Command<CreateJobCommandParameters>("CREATE_JOB");
	public static final Command<BuildJobCommandParameters> BUILD_JOB = new Command<BuildJobCommandParameters>("BUILD_JOB");
	public static final Command<CreateCommandParameters> GIT_CREATE = new Command<CreateCommandParameters>("GIT_CREATE");
	public static final Command<SyncCommandParameters> GIT_SYNC = new Command<SyncCommandParameters>("GIT_SYNC");
	public static final Command<ViewJobCommandParameters> GET_JOB = new Command<ViewJobCommandParameters>("GET_JOB");
	public static final Command<ShowBuildStatusCommandParameters> SHOW_JOB = new Command<ShowBuildStatusCommandParameters>("SHOW_JOB");
	private String name;

	private Command(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
