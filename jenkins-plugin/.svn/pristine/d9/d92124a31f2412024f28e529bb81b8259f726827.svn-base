package hudson.plugins.projectstats.resource;

import hudson.plugins.projectstats.portlet.NumBuildsPortlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.pholser.util.properties.PropertyBinder;

public final class ServiceLocator {

	/**
	 * Connection cache
	 */
	private Map cache; // NOPMD - this surely will never happen

	private static ServiceLocator me;

	private Connection conn;

	private static PluginProperties bound;
	final static Logger LOGGER = Logger.getLogger(NumBuildsPortlet.class);

	static {
		try {
			// Initialize service
			me = new ServiceLocator();
			// Read properties
			PropertyBinder<PluginProperties> binder = PropertyBinder.forType(PluginProperties.class);			
			try {
				String absolutePath = (new File(".")).getAbsolutePath();
				String propFileLoc = System.getProperty("JENKINS_HOME")+"/jpaas-integration.properties";
				bound = binder.bind(new File(propFileLoc));				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOGGER.error("Not able to read jpaas-integration.properties");
				//e.printStackTrace();
			}

		} catch (ServiceLocatorException se) {
			LOGGER.error(se);
			se.printStackTrace(System.err);
		}
	}

	private ServiceLocator() throws ServiceLocatorException {
		try {
			cache = Collections.synchronizedMap(new HashMap());
		} catch (Exception ne) {
			throw new ServiceLocatorException(ne);
		}
	}

	static public ServiceLocator getInstance() {
		return me;
	}

	public Connection getConnection() throws SQLException,
			ClassNotFoundException {

		if (conn != null) {
			return conn;
		} else {
			Class.forName(bound.dbDriverClass());
			Connection connection = DriverManager.getConnection(bound.dbUrl(),
					bound.dbUid(), bound.dbPwd());

			conn = connection;
			return conn;
		}

	}
	
	public String getSchemaName(){
		
		return bound.dbSchema();
		
	}
	
	public String getTableName(){
		return bound.dbTable();
	}
	
	public String getAbsoluteTableName(){
		return getSchemaName()+"."+getTableName();
	}
	
	public String getDataFederationURL(){
		return bound.datafederationUrl();
	}
	
	public String getDataFederationTrustStoreKey(){
		return bound.datafederationTrustStoreKey();
	}
	
	public String getDataFederationTrustPassword(){
		return bound.datafederationTrustStorePassword();
	}
	

	public String getDataIntervalTime(){
		return bound.dataIntervalTime();
	}

}
