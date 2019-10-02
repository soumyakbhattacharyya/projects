
import hudson.plugins.publishdata.dto.BuildDataDTO;
import hudson.plugins.publishdata.dto.JPaaSDBServiceInfo;
import hudson.plugins.publishdata.util.JPaaSDBUtil;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class PublishPostBuildDataTest {
	private Logger LOGGER = Logger.getLogger(PublishPostBuildDataTest.class.getName());
	private String buildDataserviceEndpoint =null;
	private String buildDataserviceTrustStoreKey =null;
	private String buildDataserviceTrustStorePassword = null;
	private String dataIntervalTime=null;
	
	@Rule
	public JenkinsRule j = new JenkinsRule();
	
	
	@Test
	public void postBuildDataTestWithHTTP(){
		try {
			loadProperties("HTTP");
		} catch (Exception e) {}
		
		BuildDataDTO buildData =createBuildData();
		
		JPaaSDBServiceInfo dbInfo= new JPaaSDBServiceInfo();
		dbInfo.setBuildLogDetailsServiceKeyStore(buildDataserviceTrustStoreKey);
		dbInfo.setBuildLogDetailsServicePasswd(buildDataserviceTrustStorePassword);
		dbInfo.setBuildLogDetailsServiceUrl(buildDataserviceEndpoint);
		JPaaSDBUtil.postBuildData(buildData, dbInfo);
		
	}
	
	@Test
	public void postBuildDataTestWithHTTPS(){
		try {
			loadProperties("HTTPS");
		} catch (Exception e) {}
		
		BuildDataDTO buildData =createBuildData();
		
		JPaaSDBServiceInfo dbInfo= new JPaaSDBServiceInfo();
		dbInfo.setBuildLogDetailsServiceKeyStore(buildDataserviceTrustStoreKey);
		dbInfo.setBuildLogDetailsServicePasswd(buildDataserviceTrustStorePassword);
		dbInfo.setBuildLogDetailsServiceUrl(buildDataserviceEndpoint);
		JPaaSDBUtil.postBuildData(buildData, dbInfo);
		
	}
	

	private BuildDataDTO createBuildData(){
		
		BuildDataDTO buildData = new BuildDataDTO();
		
		buildData.setBuildId(createRandomBuildIdAndTimeTook()[0]);
		buildData.setHappenedOn("DummySlaveTest");
		buildData.setHost("http://10.227.87.143:8080/jobTest");
		buildData.setJobName("Dummy-slave-JobTest");
		buildData.setJobStatus("success");
		buildData.setLabeledAs("Dummy-slaveTest");
		buildData.setScheduledOn(buildScheduledTime());
		buildData.setTook(createRandomBuildIdAndTimeTook()[1]);
		buildData.setConfiguredBy("112211");
		buildData.setProjectId("P-12121");
		return buildData;
		
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
	
	private String[] createRandomBuildIdAndTimeTook(){
		String arr[] = new String[2];
		 Random randomNum = new Random();
		    Integer randomBuildId = randomNum.nextInt((10000 - 100) + 1) + 100;
		    Integer randomTimeTook = randomNum.nextInt((9000-1000)+1)+100;
		    arr[0]=randomBuildId.toString();arr[1]=randomTimeTook.toString();
		return arr;
	}
	private String buildScheduledTime(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
		return format.format(date);
	}
	
}
