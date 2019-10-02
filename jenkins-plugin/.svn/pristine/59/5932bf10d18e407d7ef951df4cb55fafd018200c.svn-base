package hudson.plugins.ec2.resource;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pholser.util.properties.PropertyBinder;

public final class ServiceLocator {

	/**
	 * Connection cache
	 */
	private static ServiceLocator me;
	
	private static PluginProperties bound;
	final static Logger LOGGER = Logger.getLogger(ServiceLocator.class);

	static {
		try {
			// Initialize service
			me = new ServiceLocator();
			// Read properties
			PropertyBinder<PluginProperties> binder = PropertyBinder.forType(PluginProperties.class);			
			try {				
				String propFileLoc = System.getProperty("JENKINS_HOME")+"/jpaas-integration.properties";
				bound = binder.bind(new File(propFileLoc));				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ServiceLocatorException se) {
			LOGGER.error(se);
			se.printStackTrace(System.err);
		}
	}

	private ServiceLocator() throws ServiceLocatorException {		
	}

	static public ServiceLocator getInstance() {
		return me;
	}
	
	public String getEucalyptusUrl(){
		return bound.eucalyptusUrl();
	}
	
	public String getAwsUrl(){
		return bound.awsUrl();
	}
	
	public String getVmwareUrl(){
		return bound.vmwareUrl();
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
}
