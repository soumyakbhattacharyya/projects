package hudson.cognizant.jpaas.pojo;

import org.acegisecurity.Authentication;

public class JPaaSUser {

	public JPaaSUser(Authentication authResult) {
		this.authentication = authResult;
	}

	public JPaaSUser(Authentication authResult, String accountId,
			String projectId) {
		this.authentication = authResult;
		this.accountId = accountId;
		this.projectId = projectId;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authResult) {
		this.authentication = authResult;
	}

	private Authentication authentication;

	// JPaaS attributes
	// These are stored in the configuration file of a job and therefore usable across layer  
	private String userId;
	private String accountId;
	private String projectId;
	private String securityGroup;
	private String securityKeyFile;

	public String getSecurityGroup() {
		return securityGroup;
	}

	public void setSecurityGroup(String securityGroup) {
		this.securityGroup = securityGroup;
	}

	public String getSecurityKeyFile() {
		return securityKeyFile;
	}

	public void setSecurityKeyFile(String securityKeyFile) {
		this.securityKeyFile = securityKeyFile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

}
