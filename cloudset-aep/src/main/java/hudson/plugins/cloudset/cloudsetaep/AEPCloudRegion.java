package hudson.plugins.cloudset.cloudsetaep;

import org.kohsuke.stapler.DataBoundConstructor;

public class AEPCloudRegion {

	private final String regionName;
	private final AEPCloudAccount aepCloudAccount;
	
	@DataBoundConstructor
	public AEPCloudRegion(String regionName,AEPCloudAccount aepCloudAccount) {
		this.regionName = regionName;
		this.aepCloudAccount=aepCloudAccount;
	}

		
	public String getRegionName() {
		return regionName;
	}

	public AEPCloudAccount getAepCloudAccount() {
		return aepCloudAccount;
	}

		
}
