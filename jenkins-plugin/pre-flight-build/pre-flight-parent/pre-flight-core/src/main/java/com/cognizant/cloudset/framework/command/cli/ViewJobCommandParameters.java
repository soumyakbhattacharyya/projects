/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.command.cli;

import com.cognizant.cloudset.framework.command.CommandParameters;
import com.cognizant.cloudset.framework.Constants;
import java.util.List;
import java.util.Map;

/**
 * parameter to verify if a job exists on the server
 *
 * @author cognizant
 */
public class ViewJobCommandParameters extends CommandParameters {

	private String jobName;
	// this is updated to true in case jobName does exists on the server
	private boolean present;

	public boolean isPresent() {
		return present;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}

	public ViewJobCommandParameters(final String jobName,final String projectID) {
		this.jobName = jobName;
		super.projectId=projectID;
	}

	@Override
	public String toString() {
		return "ViewJobCommandParameters{" + "jobName=" + jobName + '}';
	}

	@Override
	public int hashCode() {
		int hash = 7;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ViewJobCommandParameters other = (ViewJobCommandParameters) obj;
		if ((this.jobName == null) ? (other.jobName != null) : !this.jobName.equals(other.jobName)) {
			return false;
		}
		return true;
	}

	public String getJobName() {
		return jobName;
	}
}
