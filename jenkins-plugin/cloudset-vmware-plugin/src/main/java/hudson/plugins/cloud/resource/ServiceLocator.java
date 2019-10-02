package hudson.plugins.cloud.resource;


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
			
	public String getVmwareUrl(){
		return bound.vmwareUrl();
	}
	
	public String getVMwareInfraSslkeypath(){
		return bound.vmwareInfraSslkeypath();
	}
	
	public String getVMWareInfraSslpassword(){
		return bound.vmwareInfraSslpassword();
	}
	
	public String getVMwareInfraAddressing(){
		return bound.vmwareInfraAddressing();
	}
	
	public String getVMwareInfraMachineimage(){
		return bound.vmwareInfraMachineimage();
	}
	
	public String getVMwareInfraVirtualmachine(){
		return bound.vmwareInfraVirtualmachine();
	}
	
	public String getVmwareInfraVirtualmachineIpDiscoveryTime(){
		return bound.vmwareInfraVirtualmachineIpDiscoveryTime();
	}
}
