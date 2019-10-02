/**
 * 
 */
package hudson.plugins.publishdata.constants;

import com.pholser.util.properties.BoundProperty;

/**
 * Represents jpaas-integration.properties component at runtime
 */
public interface JPaaSIntegrationProperties {

	/**
	 * BaaS - JPaaS provision DB integration parameters Credential to connect
	 * JPaaS DB for pushing build end data
	 */

	@BoundProperty("db.url")
	String dbUrl();

	@BoundProperty("db.driver.class")
	String dbDriverClass();

	@BoundProperty("db.uid")
	String dbUid();

	@BoundProperty("db.pwd")
	String dbPwd();

	@BoundProperty("db.schema")
	String dbSchema();

	@BoundProperty("db.table")
	String dbTable();

	@BoundProperty("db.initialPoolSize")
	int dbInitialPoolSize();

	@BoundProperty("db.minPoolSize")
	int dbMinPoolSize();

	@BoundProperty("db.maxPoolSize")
	int dbMaxPoolSize();
	
	@BoundProperty("db.maxIdleTime")
	int dbMaxIdleTime();

	/**
	 * BaaS - ACLService integration parameters Credential to connect JPaaS
	 * ACLService for ensuring RBAC features of BaaS
	 */

	@BoundProperty("acl.service.endpoint")
	String aclServiceEndpoint();

	/**
	 * BaaS - RTC Q integration parameters Credential to connect JPaaS RTC
	 * -Integration service for querying RTC Q In case BaaS runs in -DMODE=RTC,
	 * following property should indicate the RTC integration service's endpoint
	 */

	@BoundProperty("rtc.service.endpoint")
	String rtcServiceEndpoint();
	

	/**
	 * BaaS - Build RemoteServiceInvokable endpoint via which BaaS posts or consumes build related data 
	 */

	@BoundProperty("build.dataservice.endpoint")
	String buildDataserviceEndpoint();
	
	@BoundProperty("build.dataservice.truststore.key")
	String buildDataserviceTrustStoreKey();
	
	@BoundProperty("build.dataservice.trust.password")
	String buildDataserviceTrustStorePassword();

	@BoundProperty("baashelp.portal.url")
	String baashelpPortalURL();

}
