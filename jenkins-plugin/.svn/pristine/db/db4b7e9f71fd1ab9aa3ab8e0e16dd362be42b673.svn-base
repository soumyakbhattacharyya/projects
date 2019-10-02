package com.cognizant.cloudset.framework.command.cli;

import com.cognizant.cloudset.framework.command.CommandParameters;

public class ShowBuildStatusCommandParameters extends CommandParameters {
	private String jobName;

	public ShowBuildStatusCommandParameters(final String jobName,
			final String projectID) {
		this.jobName = jobName;
		super.projectId = projectID;
	}

	@Override
	public String toString() {
		return "ShowJobCommandParameters{" + "jobName=" + jobName + '}';
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
		final ShowBuildStatusCommandParameters other = (ShowBuildStatusCommandParameters) obj;
		if ((this.jobName == null) ? (other.jobName != null) : !this.jobName
				.equals(other.jobName)) {
			return false;
		}
		return true;
	}

	public String getJobName() {
		return jobName;
	}
}
