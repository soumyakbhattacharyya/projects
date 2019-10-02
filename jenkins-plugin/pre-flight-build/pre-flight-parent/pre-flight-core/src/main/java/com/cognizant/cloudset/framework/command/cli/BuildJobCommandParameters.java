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
 *
 * @author cognizant
 */
public class BuildJobCommandParameters extends CommandParameters {

	private String jobName;	

	public BuildJobCommandParameters(final String jobName,final String projectID) {
		this.jobName = jobName;
		super.projectId=projectID;
	}

	@Override
	public String toString() {
		return "BuildJobCommandParameters{" + "jobName=" + jobName + '}';
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
		final BuildJobCommandParameters other = (BuildJobCommandParameters) obj;
		if ((this.jobName == null) ? (other.jobName != null) : !this.jobName.equals(other.jobName)) {
			return false;
		}
		return true;
	}

	public String getJobName() {
		return jobName;
	}
}
