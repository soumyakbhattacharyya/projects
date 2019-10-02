package hudson.plugins.cloud.vmware;

import hudson.plugins.cloud.VMWareCloud;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;
import org.jvnet.hudson.test.HudsonTestCase;

/**
 * Not Completing the Testcases since we should not be
 * testing the cloud From the Junit test case
 * 
 * 
 * 
 * @author 272959
 *
 */

public class VMwareCloudTest extends HudsonTestCase{

	private static final String ACCOUNTID = "accountId";
	private static final String VMOWNERID = "vmOwnerId";
	private static final String ACCESSID = "accessId";
	private static final String SECURITYCREDENTIAL = "securityCredential";
	private static final String PRIVATEKEY = "privateKey";
	
	
	protected void setUp() throws Exception {
		super.setUp();		
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();		
	}
	
	@Test
	public void testCloudConnectivity() throws Exception {

		PropertiesConfiguration config = new PropertiesConfiguration(
				"user.properties");
		
		VMWareCloud vmwareCloud = new VMWareCloud(config.getString(ACCOUNTID), 
				config.getString(VMOWNERID), config.getString(ACCESSID),
				 config.getString(SECURITYCREDENTIAL) ,config.getString(PRIVATEKEY),
				 null);
		
		hudson.clouds.add(vmwareCloud);
		submit(createWebClient().goTo("configure").getFormByName("config"));

		assertEqualBeans(vmwareCloud, hudson.clouds.iterator().next(),
				"region,accessId,secretKey,privateKey");
	}
	
}
