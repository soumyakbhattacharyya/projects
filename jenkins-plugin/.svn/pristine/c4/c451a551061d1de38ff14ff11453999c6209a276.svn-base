/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.command.file;

import com.cognizant.cloudset.framework.command.CommandParameters;

/**
 *
 * @author cognizant
 */
public class SyncCommandParameters extends CommandParameters {

	private String workspaceAbsPath;
	private String workspaceName;
	private String lastPreflightTimeStampFileName;

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + (this.workspaceAbsPath != null ? this.workspaceAbsPath.hashCode() : 0);
		hash = 97 * hash + (this.workspaceName != null ? this.workspaceName.hashCode() : 0);
		hash = 97 * hash + (this.lastPreflightTimeStampFileName != null ? this.lastPreflightTimeStampFileName.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return "CreateCommandParameters{" + "workspaceAbsPath=" + workspaceAbsPath + ", workspaceName=" + workspaceName + ", lastPreflightTimeStampFileName=" + lastPreflightTimeStampFileName + '}';
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SyncCommandParameters other = (SyncCommandParameters) obj;
		if ((this.workspaceAbsPath == null) ? (other.workspaceAbsPath != null) : !this.workspaceAbsPath.equals(other.workspaceAbsPath)) {
			return false;
		}
		if ((this.workspaceName == null) ? (other.workspaceName != null) : !this.workspaceName.equals(other.workspaceName)) {
			return false;
		}
		if ((this.lastPreflightTimeStampFileName == null) ? (other.lastPreflightTimeStampFileName != null) : !this.lastPreflightTimeStampFileName.equals(other.lastPreflightTimeStampFileName)) {
			return false;
		}
		return true;
	}

	public String getWorkspaceAbsPath() {

		return workspaceAbsPath;
	}

	public void setWorkspaceAbsPath(String workspaceAbsPath) {
		this.workspaceAbsPath = workspaceAbsPath;
	}

	public String getWorkspaceName() {
		return workspaceName;
	}

	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	public String getLastPreflightTimeStampFileName() {
		return lastPreflightTimeStampFileName;
	}

	public void setLastPreflightTimeStampFileName(String lastPreflightTimeStampFileName) {
		this.lastPreflightTimeStampFileName = lastPreflightTimeStampFileName;
	}

	public SyncCommandParameters(String workspaceAbsPath, String workspaceName, String lastPreflightTimeStampFileName) {
		this.workspaceAbsPath = workspaceAbsPath;
		this.workspaceName = workspaceName;
		this.lastPreflightTimeStampFileName = lastPreflightTimeStampFileName;
	}
}
