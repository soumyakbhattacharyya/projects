/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.command.cli;

import com.cognizant.cloudset.framework.command.CommandParameters;
import com.cognizant.cloudset.framework.command.WithOperator;
import com.cognizant.cloudset.framework.Constants;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 239913
 */
public class CreateJobCommandParameters extends CommandParameters {

	protected String checkoutFrom;
	protected String buildCommand;
	protected String environment;
	protected String requesterMailId;
	protected String workspaceName;
	protected String isSonarEnable;

	public String getIsSonarEnable() {
		return isSonarEnable;
	}

	public String getWorkspaceName() {
		return workspaceName;
	}

	public String getCheckoutFrom() {
		return checkoutFrom;
	}

	public String getBuildCommand() {
		return buildCommand;
	}

	public String getEnvironment() {
		return environment;
	}

	public String getRequesterMailId() {
		return requesterMailId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((buildCommand == null) ? 0 : buildCommand.hashCode());
		result = prime * result
				+ ((checkoutFrom == null) ? 0 : checkoutFrom.hashCode());
		result = prime * result
				+ ((environment == null) ? 0 : environment.hashCode());
		result = prime * result
				+ ((requesterMailId == null) ? 0 : requesterMailId.hashCode());
		result = prime * result
				+ ((workspaceName == null) ? 0 : workspaceName.hashCode());
		result = prime * result
				+ ((isSonarEnable == null) ? 0 : isSonarEnable.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreateJobCommandParameters other = (CreateJobCommandParameters) obj;
		if (buildCommand == null) {
			if (other.buildCommand != null)
				return false;
		} else if (!buildCommand.equals(other.buildCommand))
			return false;
		if (checkoutFrom == null) {
			if (other.checkoutFrom != null)
				return false;
		} else if (!checkoutFrom.equals(other.checkoutFrom))
			return false;
		if (environment == null) {
			if (other.environment != null)
				return false;
		} else if (!environment.equals(other.environment))
			return false;
		if (requesterMailId == null) {
			if (other.requesterMailId != null)
				return false;
		} else if (!requesterMailId.equals(other.requesterMailId))
			return false;
		if (workspaceName == null) {
			if (other.workspaceName != null)
				return false;
		} else if (!workspaceName.equals(other.workspaceName))
			return false;
		if (isSonarEnable == null) {
			if (other.isSonarEnable != null)
				return false;
		} else if (!isSonarEnable.equals(other.isSonarEnable))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CreateJobCommandParameters [checkoutFrom=" + checkoutFrom
				+ ", buildCommand=" + buildCommand + ", environment="
				+ environment + ", requesterId=" + super.requesterId
				+ ", requesterMailId=" + requesterMailId + ", workspaceName="
				+ workspaceName + ", isSonarEnable=" + isSonarEnable + ", projectID=" + super.projectId + " ]";
	}

	//
	// "-s","http://localhost:8080","build","-s","-v","pb-job-generator","-p","workspace_loc=abc","-p","build_command=abc","-p","to_mail=abc","-p","requester=tester","-p","environment=abc"
	public CreateJobCommandParameters(String checkoutFrom, String buildCommand,
			String environment, String requesterId, String requesterMailId,
			String workspaceName, String isSonarEnable,String projectID) {
		this.checkoutFrom = checkoutFrom;
		this.buildCommand = buildCommand;
		this.environment = environment;
		super.requesterId = requesterId;
		this.requesterMailId = requesterMailId;
		this.workspaceName = workspaceName;
		this.isSonarEnable = isSonarEnable;
		super.projectId=projectID;
	}
}
