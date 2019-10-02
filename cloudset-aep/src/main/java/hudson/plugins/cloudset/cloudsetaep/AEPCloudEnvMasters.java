package hudson.plugins.cloudset.cloudsetaep;

import org.kohsuke.stapler.DataBoundConstructor;

public class AEPCloudEnvMasters {

	private final String cloudEnvMasterName;
	private final String paramString;
	private final String projectUserID;
	private final String projectID;
	private final String warName;
	
	@DataBoundConstructor
	public AEPCloudEnvMasters(String cloudEnvMasterName,String paramString,String projectUserID,String projectID,String warName) {
		this.cloudEnvMasterName = cloudEnvMasterName;
		this.paramString=paramString;
		this.projectUserID = projectUserID;
		this.projectID = projectID;
		this.warName = warName;
	}

		
	public String getCloudEnvMasterName() {
		return cloudEnvMasterName;
	}


	public String getParamString() {
		return paramString;
	}


	public String getProjectUserID() {
		return projectUserID;
	}


	public String getProjectID() {
		return projectID;
	}


	public String getWarName() {
		return warName;
	}

	
		
}
