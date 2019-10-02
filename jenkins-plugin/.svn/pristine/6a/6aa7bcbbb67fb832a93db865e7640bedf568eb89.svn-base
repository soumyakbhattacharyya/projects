import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import com.cognizant.buildservcore.ServiceContext;
import com.cognizant.buildservcore.datafederation.DataFederationService;
import com.cognizant.buildservcore.datafederation.DataFederationSupport;
import com.cognizant.buildservcore.datafederation.ProjectInfrastructureUsage;
import com.cognizant.jpaas.build.util.BaaSBuildServiceRegistry;


public class ShowBuildDataForDashBoardTest {
	private Logger LOGGER = Logger.getLogger(ShowBuildDataForDashBoardTest.class.getName());
	private String buildDataserviceEndpoint =null;
	private String buildDataserviceTrustStoreKey =null;
	private String buildDataserviceTrustStorePassword = null;
	private String dataIntervalTime=null;
	
	@Rule
	public JenkinsRule j = new JenkinsRule();
	

	@Test
	public void showBuildDataForDashBoardTestHTTP() throws Exception{
		loadProperties("HTTP");
		DataFederationService baaslogService = BaaSBuildServiceRegistry
				.getRegistry().getDataFederationService(
						ServiceContext.getContext(buildDataserviceEndpoint+"?wsdl"));
		DataFederationSupport dataFederationSupport = baaslogService
				.getDataFederationSupport();    
	    		
	    			 ProjectInfrastructureUsage usage = dataFederationSupport.getProjectUsage("P-12121");
	    			 LOGGER.info("Jit Builder "
								+ usage.getJitBuildJobList());
						LOGGER.info("pooled list "
							+ usage.getPooledBuildJobList());
						LOGGER.info("Third party list "
								+ usage.getThirdPartyJobList());
						LOGGER.info("Data received from BuildLog Table");	
						
					System.out.println("Jit Builder "
								+ usage.getJitBuildJobList()
						+"pooled list "
							+ usage.getPooledBuildJobList()+
						"Third party list "
								+ usage.getThirdPartyJobList());	
		
	}
	
	@Test
	public void showBuildDataForDashBoardTestHTTPS() throws Exception{
		
		loadProperties("HTTPS");
		DataFederationService baaslogService = BaaSBuildServiceRegistry
				.getRegistry().getDataFederationService(
						ServiceContext.getContext(buildDataserviceEndpoint+"?wsdl"));
		DataFederationSupport dataFederationSupport = baaslogService
				.getDataFederationSupport();    
	    		
	    			 ProjectInfrastructureUsage usage = dataFederationSupport.getProjectUsage("P-12121");
	    			 LOGGER.info("Jit Builder "
								+ usage.getJitBuildJobList());
						LOGGER.info("pooled list "
							+ usage.getPooledBuildJobList());
						LOGGER.info("Third party list "
								+ usage.getThirdPartyJobList());
						LOGGER.info("Data received from BuildLog Table");	
						
					System.out.println("Jit Builder "
								+ usage.getJitBuildJobList()
						+"pooled list "
							+ usage.getPooledBuildJobList()+
						"Third party list "
								+ usage.getThirdPartyJobList());	
		
	}
	
	private void loadProperties(String protocol) throws Exception{
		Properties property = new Properties();
		property.load(new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\jpaas-integration.properties"));
		
		if(protocol.equals("HTTP")){
			buildDataserviceEndpoint =property.getProperty("datafederation.url.http");
			 buildDataserviceTrustStoreKey =System.getProperty("user.dir")+property.getProperty("datafederation.truststore.key");
				buildDataserviceTrustStorePassword =property.getProperty("datafederation.truststore.password");
				dataIntervalTime=property.getProperty("data.interval.time");
		}else if(protocol.equals("HTTPS")){
			buildDataserviceEndpoint =property.getProperty("datafederation.url.https");
			 buildDataserviceTrustStoreKey =System.getProperty("user.dir")+property.getProperty("datafederation.truststore.key");
				buildDataserviceTrustStorePassword =property.getProperty("datafederation.truststore.password");
				dataIntervalTime=property.getProperty("data.interval.time");
		}
	   
	}
}
