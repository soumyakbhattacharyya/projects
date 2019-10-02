package hudson.plugins.publishdata.util;

import hudson.model.AbstractBuild;
import hudson.plugins.publishdata.constants.JPaaSConstant;
import hudson.plugins.publishdata.dto.BuildDataDTO;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jenkinsci.plugins.extendjob.JobPropertiesExtension;
/**
 * This Class will be used as an utility service for publishing into DB
 * @author 309657
 *
 */
public class JPaasBuildServiceProviderUtil {
	final static Logger logger = Logger.getLogger(JPaasBuildServiceProviderUtil.class.getName());
	
	public static BuildDataDTO setProjectNUserId(AbstractBuild<?, ?> build,BuildDataDTO buildData){
		
		String configBy =null;
		String projectID=null;
		JobPropertiesExtension jbProperties =null;
		
		Iterator<?> iter = build.getProject().getAllProperties().iterator();
		while (iter.hasNext()) {
			jbProperties = (JobPropertiesExtension) iter.next();
			configBy = jbProperties.getUserID();
			projectID =jbProperties.getProjectID();
		}
		
		if(configBy !=null && projectID != null){
			buildData.setProjectId(projectID);
			buildData.setConfiguredBy(configBy);
		}
		logger.info("Project id---- "+jbProperties.getProjectID());
		logger.info("Configuring User---- "+jbProperties.getUserID());
		return buildData;
	}
	
	public static BuildDataDTO populateBuildData(AbstractBuild<?, ?> build){
		
		BuildDataDTO buildData = new BuildDataDTO();
		
		Date date=new Date(new Long(build.getTimeInMillis()));
		SimpleDateFormat sdf = new SimpleDateFormat(JPaaSConstant.BUILD_AUDIT_TIME_FORMAT); 
		String buildID =modifyBuilId(build.getId());
		String happenedOn = build.getBuiltOnStr();
		String host = formatUrl(build.getAbsoluteUrl(),build.getUrl()); 
		String jobName = build.getProject().getFullName();
		String buildStatus = build.getResult().toString().toLowerCase();
		String labeledAs = build.getExecutor().getOwner().getNode().getLabelString();
		String scheduledOn = sdf.format(date);
		String timeTook =timeTakenToBuild(build.getDurationString());
		
		buildData.setBuildId(buildID);
		buildData.setHappenedOn(happenedOn);
		buildData.setHost(host);
		buildData.setJobName(jobName);
		buildData.setJobStatus(buildStatus);
		buildData.setLabeledAs(labeledAs);
		buildData.setScheduledOn(scheduledOn);
		buildData.setTook(timeTook);
		
		return buildData;
	}
	
	private static String formatUrl(String absoluteURL,String hUrl) {
		String baas_host =null; 
		try{
		URL url = new URL(absoluteURL);
        baas_host = url.getProtocol().concat("://")
        		.concat(url.getHost().concat(":")
        				.concat(new Integer(url.getPort()).toString())
        				.concat("/").concat(hUrl.substring(0,hUrl.indexOf("/"))));
		}catch(MalformedURLException ex){
			logger.log(Level.SEVERE,"URL cannot be properly constructed");
		}
		return baas_host;
	}
	
	private static String timeTakenToBuild(String buildTime){	
		if(buildTime.contains(JPaaSConstant.COUNTING)){
			buildTime= 	buildTime.substring(0,buildTime.indexOf(JPaaSConstant.COUNTING)).trim();	
		}
		
		double mill_time=0;
		int month_index=0;
		int day_index=0;
		int hr_index=0;
		int min_index=0;

		if(buildTime.contains(JPaaSConstant.MONTH)){
			month_index=buildTime.indexOf(JPaaSConstant.MONTH);
			String month_String = buildTime.substring(0,month_index);
			mill_time=new Double(month_String)*30*24*60*60*1000;
		}

		if(buildTime.contains(JPaaSConstant.DAY)){
			String day_String ="0";
			day_index=buildTime.indexOf(JPaaSConstant.DAY);
			if(month_index!=0){
				day_String = buildTime.substring(month_index+5,day_index);
			}else{
				day_String = buildTime.substring(0,day_index);
			}
			mill_time=mill_time+new Double(day_String)*24*60*60*1000;
		}

		if(buildTime.contains(JPaaSConstant.HOUR)){
			String hr_String ="0";
			hr_index=buildTime.indexOf(JPaaSConstant.HOUR);
			if(day_index!=0){
				hr_String = buildTime.substring(day_index+3,hr_index);
			}else{
				hr_String = buildTime.substring(0,hr_index);
			}
			mill_time=mill_time+new Double(hr_String)*60*60*1000;
		}
		if(buildTime.contains(JPaaSConstant.MIN)){
			String min_String ="0";
			min_index=buildTime.indexOf(JPaaSConstant.MIN);
			if(hr_index!=0){
			min_String = buildTime.substring(hr_index+4,min_index);
			}else{
				min_String = buildTime.substring(0,min_index);
			}
			mill_time=mill_time+new Double(min_String)*60*1000;
		}
		if(buildTime.contains(JPaaSConstant.SEC)){
			String sec_String ="0"; 
			if(min_index!=0){
				sec_String=	buildTime.substring(min_index+3,buildTime.indexOf(JPaaSConstant.SEC));	
			}else{
				sec_String=	buildTime.substring(0,buildTime.indexOf(JPaaSConstant.SEC));
			}
			mill_time=mill_time+new Double(sec_String)*1000;
		}
		String milliTime = new Double(mill_time).toString(); 
		milliTime=milliTime.substring(0,milliTime.indexOf("."));
		return milliTime;		
	}
	
	private static String modifyBuilId(String buildID){
		 buildID =buildID.replaceAll("[-_]","");
		return buildID;
	}
}
