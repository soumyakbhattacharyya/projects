package hudson.plugins.cloudset.cloudsetaep;

import hudson.model.Hudson;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * App env Profile Represents the Rest Service URLS
 * 
 * 
 * @author 272959
 *
 */
public class AppEnvProfile {

	public static final AppEnvProfile[] all() {
		Hudson hudson = Hudson.getInstance();
		if (hudson == null) {
			// for unit test
			return new AppEnvProfile[0];
		}
		AppDeployer.DescriptorImpl appEnvProfilerDescriptor = Hudson
				.getInstance().getDescriptorByType(
						AppDeployer.DescriptorImpl.class);
		return appEnvProfilerDescriptor.getInstallations();
	}

	public static final AppEnvProfile get(String name) {
		AppEnvProfile[] available = all();
		if (StringUtils.isEmpty(name) && available.length > 0) {
			return available[0];
		}
		for (AppEnvProfile si : available) {
			if (StringUtils.equals(name, si.getName())) {
				return si;
			}
		}
		return null;
	}

	private final String name;
	private final String appEnvURL;
		
	@DataBoundConstructor
	public AppEnvProfile(String name, String appEnvURL) {
		this.name = name;
		this.appEnvURL = appEnvURL;
	}

	public String getName() {
		return name;
	}

	public String getAppEnvURL() {
		return appEnvURL;
	}

	@Override
	public String toString() {
		return "AppEnvProfile [name=" + name + ", appEnvURL=" + appEnvURL + "]";
	}

	
		
}
