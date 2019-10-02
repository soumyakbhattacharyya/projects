package hudson.plugins.cloudset.cloudsetaep;

import org.kohsuke.stapler.DataBoundConstructor;

public class AEPCloudAccount {

	private final String cloudAccountName;
	private final AEPCloudEnvMasters aepCloudEnvMasters;

	@DataBoundConstructor
	public AEPCloudAccount(String cloudAccountName,AEPCloudEnvMasters aepCloudEnvMasters) {
		this.cloudAccountName = cloudAccountName;
		this.aepCloudEnvMasters=aepCloudEnvMasters;
	}

		
	public String getCloudAccountName() {
		return cloudAccountName;
	}


	public AEPCloudEnvMasters getAepCloudEnvMasters() {
		return aepCloudEnvMasters;
	}

		
}
