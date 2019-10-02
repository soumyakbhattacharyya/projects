package hudson.plugins.ec2.resource;


import java.io.File;
import java.io.IOException;
import java.util.Map;

import java.util.logging.Logger;
import com.pholser.util.properties.PropertyBinder;

public final class ServiceLocator {

	/**
	 * Connection cache
	 */
	private static ServiceLocator me;
	
	private static PluginProperties bound;
	final static Logger LOGGER = Logger.getLogger(ServiceLocator.class.getName());

	static {
		try {
			// Initialize service
			me = new ServiceLocator();
			// Read properties
			String JENKINS_HOME = System.getProperty("JENKINS_HOME");
			
			
			Map<String,String> envMap = System.getenv();
			//LOGGER.info("--------------------------------------------------------");
			//LOGGER.info(""+envMap);
			//LOGGER.info("--------------------------------------------------------");
			if(JENKINS_HOME == null || JENKINS_HOME.isEmpty()){
				JENKINS_HOME = envMap.get("JENKINS_HOME");
			}
			
			PropertyBinder<PluginProperties> binder = PropertyBinder.forType(PluginProperties.class);			
			try {				
				String propFileLoc = JENKINS_HOME+"/jpaas-integration.properties";
				bound = binder.bind(new File(propFileLoc));		
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (ServiceLocatorException se) {
			LOGGER.severe(""+se);
			se.printStackTrace(System.err);
		}
	}

	private ServiceLocator() throws ServiceLocatorException {		
	}

	static public ServiceLocator getInstance() {
		return me;
	}
	
	public String getAwsUrl(){
		return bound.awsUrl();
	}
	
	public String getEc2Sslkeypath(){
		return bound.ec2Sslkeypath();
	}
	
	public String getEc2Sslpassword(){
		return bound.ec2Sslpassword();
	}
	
	public String getEc2Addressing(){
		return bound.ec2Addressing();
	}
	
	public String getEc2Machineimage(){
		return bound.ec2Machineimage();
	}
	
	public String getEc2Virtualmachine(){
		return bound.ec2Virtualmachine();
	}
	
	public String getEc2VirtualmachineIpDiscoveryTime(){
		return bound.ec2VirtualmachineIpDiscoveryTime();
	}
	
	public String getEc2PooledTime(){
		return bound.ec2PooledTime();
	}
}
