package hudson.plugins.publishdata.dto;

public class JPaaSDBServiceInfo {
	
	
	private String buildLogDetailsServiceUrl;
    private String buildLogDetailsServiceKeyStore;
    private String buildLogDetailsServicePasswd;
    
	 
	public String getBuildLogDetailsServiceUrl() {
		return buildLogDetailsServiceUrl;
	}
	
	public void setBuildLogDetailsServiceUrl(String buildLogDetailsServiceUrl) {
		this.buildLogDetailsServiceUrl = buildLogDetailsServiceUrl;
	}

	public String getBuildLogDetailsServiceKeyStore() {
		return buildLogDetailsServiceKeyStore;
	}
	
	public void setBuildLogDetailsServiceKeyStore(
			String buildLogDetailsServiceKeyStore) {
		this.buildLogDetailsServiceKeyStore = buildLogDetailsServiceKeyStore;
	}
	
	public String getBuildLogDetailsServicePasswd() {
		return buildLogDetailsServicePasswd;
	}
	
	public void setBuildLogDetailsServicePasswd(String buildLogDetailsServicePasswd) {
		this.buildLogDetailsServicePasswd = buildLogDetailsServicePasswd;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "-ServiceEndPoint-" + getBuildLogDetailsServiceUrl() + "-ServiceKeyStore-" + getBuildLogDetailsServiceKeyStore()
				+ "-ServicePassword-" + getBuildLogDetailsServicePasswd();
	}
}
