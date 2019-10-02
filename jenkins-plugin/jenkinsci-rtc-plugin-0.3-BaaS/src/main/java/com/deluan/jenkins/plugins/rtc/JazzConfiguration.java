package com.deluan.jenkins.plugins.rtc;

import hudson.FilePath;

/**
 * @author deluan
 */
public class JazzConfiguration implements Cloneable {
    private String repositoryLocation;
    private String workspaceName;
    //private String streamName;
    private String rtcUserName;
    private String password;
    private FilePath jobWorkspace;

    public String getRepositoryLocation() {
        return repositoryLocation;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

//    public String getStreamName() {
//        return streamName;
//    }

    

    public String getPassword() {
        return password;
    }

    public FilePath getJobWorkspace() {
        return jobWorkspace;
    }

    public void setRepositoryLocation(String repositoryLocation) {
        this.repositoryLocation = repositoryLocation;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

//    public void setStreamName(String streamName) {
//        this.streamName = streamName;
//    }

    

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRtcUserName() {
		return rtcUserName;
	}

	public void setRtcUserName(String rtcUserName) {
		this.rtcUserName = rtcUserName;
	}

	public void setJobWorkspace(FilePath jobWorkspace) {
        this.jobWorkspace = jobWorkspace;
    }

    @SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException", "CloneDoesntCallSuperClone"})
    @Override
    public JazzConfiguration clone() {
        JazzConfiguration clone = new JazzConfiguration();

        clone.repositoryLocation = this.repositoryLocation;
        clone.workspaceName = this.workspaceName;
//        clone.streamName = this.streamName;
        clone.rtcUserName = this.rtcUserName;
        clone.password = this.password;
        clone.jobWorkspace = this.jobWorkspace;

        return clone;
    }
}
