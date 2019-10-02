package hudson.plugins.cloudset.cloudsetaep;

import org.kohsuke.stapler.DataBoundConstructor;

public class AEPCloudMasterParams {

	private final String paramString;

	@DataBoundConstructor
	public AEPCloudMasterParams(String paramString) {
		this.paramString = paramString;
	}

	public String getParamString() {
		return paramString;
	}

	
	
	
}
