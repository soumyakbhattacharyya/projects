package hudson.plugins.projectstats.data;

import hudson.plugins.projectstats.resource.ServiceLocator;

import java.util.Calendar;
import java.util.Timer;
import java.util.logging.Logger;

import org.kohsuke.stapler.Stapler;

import com.cognizant.buildservcore.ServiceContext;
import com.cognizant.buildservcore.datafederation.DataFederationService;
import com.cognizant.buildservcore.datafederation.DataFederationSupport;
import com.cognizant.buildservcore.datafederation.ProjectInfrastructureUsage;
import com.cognizant.httpsclientutil.HttpsClientFactory;
import com.cognizant.httpsclientutil.HttpsClientUtil;
import com.cognizant.httpsclientutil.HttpsSSLException;
import com.cognizant.jpaas.build.util.BaaSBuildServiceRegistry;
import com.cognizant.jpaas2.commons.expection.JPaaSException;

public class ProjectStrategyUsageCollector {

	private static ProjectInfrastructureUsage usage = null;
	private static String projectId = null;
	private static Timer timer = new Timer();
	final static Logger LOGGER = Logger
	.getLogger(ProjectStrategyUsageCollector.class.getName());

	public static void startJob() {
		
		Calendar date = Calendar.getInstance();
		date.setTime(new java.util.Date());
		// Schedule to run in every 2 minutes
		int interval_time = Integer.parseInt(ServiceLocator.getInstance().getDataIntervalTime());
		
		try{
			timer.schedule(new ProjectStrategyUsageCollectionTask(),
					date.getTime(), 1000 * interval_time);
		}catch(Exception e){
			LOGGER.severe("Not able to start the task for " + e+" so only calling the data federation separately");
			ProjectStrategyUsageCollector.getProjectInfrastructureUsage();
		}
	}
	public static void stopJob() {
		try{
			timer.cancel();
		}catch(Exception e){
			LOGGER.severe("Task might not be running " + e);
		}
	}

	
	public static ProjectInfrastructureUsage getProjectUsageDetails() {
		if (usage == null) {
			startJob();
			return getProjectInfrastructureUsage();
		}else if(Stapler.getCurrentRequest() != null
				&& Stapler.getCurrentRequest().getSession() != null && projectId!= String.valueOf(Stapler.getCurrentRequest()
						.getSession().getAttribute("projectId"))){
			stopJob();
			startJob();
			return getProjectInfrastructureUsage();
		} else {
			return usage;
		}
	}

	public static String getProjectID() {
			if (Stapler.getCurrentRequest() != null
					&& Stapler.getCurrentRequest().getSession() != null) {
				projectId = String.valueOf(Stapler.getCurrentRequest()
						.getSession().getAttribute("projectId"));
				LOGGER.info("ProjectId is " + projectId);
			}
		return projectId;
	}

	public static ProjectInfrastructureUsage getProjectInfrastructureUsage() {

	String dataFederationURL = ServiceLocator.getInstance().getDataFederationURL()+"?wsdl";

		
		String projectId = null;
		 if (Stapler.getCurrentRequest() != null
					&& Stapler.getCurrentRequest().getSession() != null) {
			 projectId= String.valueOf(Stapler.getCurrentRequest()
						.getSession().getAttribute("PROJECTID"));
				LOGGER.info("projectId is " + projectId);
			}
		if (projectId != null) {
			getSSLInformation();
			LOGGER.info("The project id is " + projectId);
			LOGGER.info("In plugin datafederation URL " + dataFederationURL);
			DataFederationService baaslogService = BaaSBuildServiceRegistry
					.getRegistry().getDataFederationService(
							ServiceContext.getContext(dataFederationURL));
			DataFederationSupport dataFederationSupport = baaslogService
					.getDataFederationSupport();    
		    		try {
		    		usage = dataFederationSupport.getProjectUsage(projectId);
		    		LOGGER.info("Jit Builder "
							+ usage.getJitBuildJobList());
					LOGGER.info("pooled list "
						+ usage.getPooledBuildJobList());
					LOGGER.info("Third party list "
							+ usage.getThirdPartyJobList());
					LOGGER.info("Data received from BuildLog Table");
		    		} catch (JPaaSException e) {
						LOGGER.severe("Error while fetching data from DataFederation "
								+ e.getMessage());
						e.printStackTrace();
					}
			
		}

		return usage;
	}

	/**
	 * Apply SSL Information to the Request
	 */
	private static void getSSLInformation() {
		String trustStoreInformation = ServiceLocator.getInstance()
				.getDataFederationTrustStoreKey();
		String trustStorePassword = ServiceLocator.getInstance()
				.getDataFederationTrustPassword();

		LOGGER.info("TrustStore information received " + trustStoreInformation);
		LOGGER.info("Password information received ");

		HttpsClientUtil util = HttpsClientFactory.getHttpsClientUtil();
		try {
			util.setSSLEnvironment(trustStoreInformation, trustStorePassword);
		} catch (HttpsSSLException e1) {
			System.out.println(e1);
		}

	}

}
