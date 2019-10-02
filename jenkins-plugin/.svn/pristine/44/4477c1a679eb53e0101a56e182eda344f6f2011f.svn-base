package hudson.plugins.publishdata.util;

import hudson.plugins.publishdata.dto.BuildDataDTO;
import hudson.plugins.publishdata.dto.JPaaSDBServiceInfo;

import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import com.cognizant.buildservcore.ServiceContext;
import com.cognizant.buildservcore.datafederation.BuildLogDetails;
import com.cognizant.buildservcore.datafederation.DataFederationService;
import com.cognizant.buildservcore.datafederation.DataFederationSupport;
import com.cognizant.jpaas.build.util.BaaSBuildServiceRegistry;
import com.cognizant.jpaas2.commons.expection.JPaaSException;

public class JPaaSDBUtil {
	private static final Logger LOGGER = Logger.getLogger(JPaaSDBUtil.class.getName());
	
	private JPaaSDBUtil(){}
	
	/**
	 * Post build end information to JPaaS provision DB via web service
	 * This return 1 to signify success and 0 for exceptional situations
	 */
	public static int postBuildData(BuildDataDTO b,JPaaSDBServiceInfo dbInfo) {
    	String endPoint =dbInfo.getBuildLogDetailsServiceUrl();
    	
    	getSSLInformation(dbInfo);    	
    	
    	DataFederationService baaslogService = BaaSBuildServiceRegistry.getRegistry().getDataFederationService(ServiceContext.getContext(endPoint));
    	int result = -1;
    	if(baaslogService.hasDataFederationSupport()){
    		DataFederationSupport dataFederationSupport =  baaslogService.getDataFederationSupport();
    		BuildLogDetails buildLogDetails = populateBuildLog(b);
    		try {
    			LOGGER.info( "Posting build data" );
				result = dataFederationSupport.createBaasBuildLog(buildLogDetails);
				LOGGER.info( "Has data been posted successfully " + (result != 0));				
    		} catch (JPaaSException e) {
    			LOGGER.severe( "Error while Posting build data "+e);
				e.printStackTrace();				
			}
    	}    
    	return result;
    }
	
	private static BuildLogDetails populateBuildLog(BuildDataDTO buildDTO){
		BuildLogDetails baasBuildLogDtoInput = new BuildLogDetails();
		if(buildDTO!= null){
			
			baasBuildLogDtoInput.setBuildId(new Long(buildDTO.getBuildId()));
			baasBuildLogDtoInput.setProjectId(buildDTO.getProjectId());
			baasBuildLogDtoInput.setJobId(buildDTO.getJobName());
			baasBuildLogDtoInput.setConfiguredBy(buildDTO.getConfiguredBy());
			baasBuildLogDtoInput.setDurationMillisec(buildDTO.getTook());
			baasBuildLogDtoInput.setHappenedOn(buildDTO.getHappenedOn());
			baasBuildLogDtoInput.setHost(buildDTO.getHost());
			baasBuildLogDtoInput.setLabel(buildDTO.getLabeledAs());
			baasBuildLogDtoInput.setScheduledOn(buildDTO.getScheduledOn());
			baasBuildLogDtoInput.setStatus(buildDTO.getJobStatus());
			
		}
		return baasBuildLogDtoInput;
	}

	/**
	 * Apply SSL Information to the Request
	 */
	private static void getSSLInformation(JPaaSDBServiceInfo dbInfo) {
		String trustStoreInformation = dbInfo.getBuildLogDetailsServiceKeyStore();
		String trustStorePassword = dbInfo.getBuildLogDetailsServicePasswd();

		LOGGER.info("TrustStore information received " + trustStoreInformation);
		LOGGER.info("Password information received ");

		System.setProperty("javax.net.ssl.trustStore", trustStoreInformation);
		System.setProperty("javax.net.ssl.trustStorePassword",
				trustStorePassword);

		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String string, SSLSession ssls) {
				return true;
			}
		});
	}
	
	
	

}
