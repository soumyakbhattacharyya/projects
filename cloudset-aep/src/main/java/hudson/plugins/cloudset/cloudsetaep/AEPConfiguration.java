package hudson.plugins.cloudset.cloudsetaep;

import org.kohsuke.stapler.DataBoundConstructor;

public class AEPConfiguration {

	private final String installationURL;

	private final AEPCloudProviderConfiguration aepCloudProviderConfiguration;

	@DataBoundConstructor
	public AEPConfiguration(String installationURL,
			AEPCloudProviderConfiguration aepCloudProviderConfiguration) {
		this.installationURL = installationURL;
		this.aepCloudProviderConfiguration = aepCloudProviderConfiguration;

	}
	

	public String getInstallationURL() {
		return installationURL;
	}


	public AEPCloudProviderConfiguration getAepCloudProviderConfiguration() {
		return aepCloudProviderConfiguration;
	}
}
