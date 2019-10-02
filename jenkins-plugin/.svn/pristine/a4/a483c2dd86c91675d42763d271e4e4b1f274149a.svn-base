package hudson.plugins.cloud.rbac.authentication;

import hudson.plugins.cloudset.rbac.authentication.CloudSetSecurityRealm;
import hudson.plugins.cloudset.rbac.authorization.CloudSetRoleBasedAuthorizationStrategy;
import hudson.plugins.cloudset.rbac.util.RBacUtil;

import java.util.List;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class CloudSetRbacAuthTest {
	@Rule
	public JenkinsRule j = new JenkinsRule();

	private static final String USERNAME = "userid";
	private static final String PASSWORD = "password";	
	private static final String RBAC_URL = "rbac_url";
	private static final String RBAC_SECURITY_KEY_PATH = "rbac_security_key_path";
	private static final String RBAC_SECURITY_PASSWORD = "rbac_security_password";
	private static final String UNSUCCESSFUL_USERID = "unsuccessful_userid";
	private static final int BAAS_SERVICE_ID = 1;

	@Test
	public void testSuccessfulAuthentication() throws Exception {

		PropertiesConfiguration config = new PropertiesConfiguration(
				"user.properties");
		Thread.sleep(1000);
		boolean isUserAuthenticated = RBacUtil.isUserAuthenticated(
				config.getString(USERNAME), config.getString(PASSWORD),
				config.getString(RBAC_URL),
				config.getString(RBAC_SECURITY_KEY_PATH),
				config.getString(RBAC_SECURITY_PASSWORD));

		Assert.assertTrue(isUserAuthenticated);

	}

	@Test
	public void testUnsuccessfulLogin() throws Exception {

		PropertiesConfiguration config = new PropertiesConfiguration(
				"user.properties");
		boolean isUserAuthenticated = RBacUtil.isUserAuthenticated(
				config.getString(UNSUCCESSFUL_USERID),
				config.getString(PASSWORD), config.getString(RBAC_URL),
				config.getString(RBAC_SECURITY_KEY_PATH),
				config.getString(RBAC_SECURITY_PASSWORD));

		Assert.assertTrue(!isUserAuthenticated);

	}

	@Test
	public void testSuccessfulAuthorization() throws Exception {

		PropertiesConfiguration config = new PropertiesConfiguration(
				"user.properties");
		List<String> permissionList = RBacUtil.getUserPermission(
				config.getString(USERNAME), config.getString(PASSWORD),
				config.getString(RBAC_URL),
				config.getString(RBAC_SECURITY_KEY_PATH),
				config.getString(RBAC_SECURITY_PASSWORD),BAAS_SERVICE_ID
				);
		Assert.assertNotNull(permissionList);
	}

	@Test
	public void testNoAuthorization() throws Exception {

		PropertiesConfiguration config = new PropertiesConfiguration(
				"user.properties");
		List<String> permissionList = RBacUtil.getUserPermission(
				config.getString(UNSUCCESSFUL_USERID),
				config.getString(PASSWORD), config.getString(RBAC_URL),
				config.getString(RBAC_SECURITY_KEY_PATH),
				config.getString(RBAC_SECURITY_PASSWORD),BAAS_SERVICE_ID);
		System.out.println(permissionList);
		Assert.assertTrue(permissionList.isEmpty());
	}

	@Test
	public void testLogin() throws Exception {

		PropertiesConfiguration config = new PropertiesConfiguration(
				"user.properties");
		String userName = config.getString(USERNAME);
		String password = config.getString(PASSWORD);
		j.jenkins.setSecurityRealm(new CloudSetSecurityRealm(config
				.getString(RBAC_URL), config.getString(RBAC_SECURITY_KEY_PATH),
				config.getString(RBAC_SECURITY_PASSWORD)));
		j.jenkins
				.setAuthorizationStrategy(new CloudSetRoleBasedAuthorizationStrategy(
						config.getString(RBAC_URL), config
								.getString(RBAC_SECURITY_KEY_PATH), config
								.getString(RBAC_SECURITY_PASSWORD),"10",false,false));

		HtmlPage login = j.createWebClient().goTo("login?from=%2F");
		HtmlForm loginForm = getFormByName(login, "login");
		
		loginForm.getInputByName("j_username").setValueAttribute(userName);
		loginForm.getInputByName("j_password").setValueAttribute(password);		
		Assert.assertNotNull(getFormByName(login, "login"));

	}

	
	private HtmlForm getFormByName(HtmlPage p, final String id)
			throws ElementNotFoundException {
		return getFormByAttribute(p, "name", id);
	}

	private HtmlForm getFormById(HtmlPage p, final String id)
			throws ElementNotFoundException {
		return getFormByAttribute(p, "id", id);
	}

	private HtmlForm getFormByAction(HtmlPage p, final String action)
			throws ElementNotFoundException {
		return getFormByAttribute(p, "action", action);
	}

	private HtmlForm getFormByAttribute(HtmlPage p, String name, String value)
			throws ElementNotFoundException {
		final List<HtmlForm> forms = p.getDocumentElement()
				.getElementsByAttribute("form", name, value);
		if (forms.size() == 0) {
			throw new ElementNotFoundException("form", name, value);
		}
		return forms.get(0);
	}
}
