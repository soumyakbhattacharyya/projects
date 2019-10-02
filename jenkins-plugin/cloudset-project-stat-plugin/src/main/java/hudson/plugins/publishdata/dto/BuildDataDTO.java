package hudson.plugins.publishdata.dto;

public class BuildDataDTO {

	
	private String buildId;
	private String scheduledOn; // SCHEDULEDON-Thu 2012.02.02 at 07:04:35 PM IST
	private String labeledAs; // LABELEDAS-i-ebcb198e#JDK_ANT
	private String jobName; // -BaaSDemo
	private String configuredBy; // CONFIGUREDBY-223128
	private String projectId; // PROJECT-26779
	private String took; // TOOK-14 min
	private String happenedOn; // ONFOLLOWINGNODE-i-ebcb198e
	private String host;
	private String jobStatus;
	
	public String getBuildId() {
		return buildId;
	}

	public void setBuildId(String buildId) {
		this.buildId = buildId;
	}

	public String getScheduledOn() {
		return scheduledOn;
	}

	public void setScheduledOn(String scheduledOn) {
		this.scheduledOn = scheduledOn;
	}

	public String getLabeledAs() {
		return labeledAs;
	}

	public void setLabeledAs(String labeledAs) {
		this.labeledAs = labeledAs;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getConfiguredBy() {
		return configuredBy;
	}

	public void setConfiguredBy(String configuredBy) {
		this.configuredBy = configuredBy;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTook() {
		return took;
	}

	public void setTook(String took) {
		this.took = took;
	}

	public String getHappenedOn() {
		return happenedOn;
	}

	public void setHappenedOn(String happenedOn) {
		this.happenedOn = happenedOn;
	}

	
	

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
		
	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "-PROJECT-" + getProjectId() + "-JOB-" + getJobName()
				+ "-FORLABEL-" + getLabeledAs() + "-BUILD-" + getBuildId()
				+ "-SCHEDULEDON-" + getScheduledOn() + "-CONFIGBY-"
				+ getConfiguredBy() + "-TOOK-" + getTook() + "-ON-"
				+ getHappenedOn()+"-WITH STATUS AS- "+jobStatus +"-HOST Name- "+getHost();
	}

}
