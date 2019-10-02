package hudson.plugins.cloudset.cloudsetaep;

import org.kohsuke.stapler.DataBoundConstructor;

public class AEPCloudProviderConfiguration {

	private final String cloudProvider;
	private final AEPCloudRegion aepCloudRegion;

	@DataBoundConstructor
	public AEPCloudProviderConfiguration(String cloudProvider,
			AEPCloudRegion aepCloudRegion) {
		this.cloudProvider = cloudProvider;
		this.aepCloudRegion = aepCloudRegion;
	}

	public String getCloudProvider() {
		return cloudProvider;
	}

	public AEPCloudRegion getAepCloudRegion() {
		return aepCloudRegion;
	}

}
