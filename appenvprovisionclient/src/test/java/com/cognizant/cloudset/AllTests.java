package com.cognizant.cloudset;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

import com.cognizant.cloudset.bean.AppEnvInstance;
import com.cognizant.cloudset.bean.AppEnvMaster;
import com.cognizant.cloudset.bean.AppEnvTaskOverview;
import com.cognizant.cloudset.bean.CloudProvider;
import com.cognizant.cloudset.bean.MachineDetails;
import com.cognizant.cloudset.client.RestClient;
import com.cognizant.cloudset.command.Command;


public class AllTests {

	private static final String TYPE_XML = "application/xml";
	private static final String AEP_SERVICE_URL = "http://54.225.108.236:8580/appenvprovisionservice-webapp-1.0";
	        
	@Test
	public void testGetAllProvider(){
		
		String projectID = "P-26902";
		String path = "/aepService/" + projectID + "/providers/";
		
		List<CloudProvider> result;
		try {
			result = (List<CloudProvider>) RestClient.callRestService(Command.GET_COLLECTION,AEP_SERVICE_URL, path, CloudProvider.class,null, TYPE_XML);
			for(CloudProvider cloudProvider:result){
				System.out.println(cloudProvider);
			}
			Assert.assertNotNull(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Test
	public void testGetProvider(){
		
		String projectID = "P-26902";
		String providerID = "4";
		String path = "/aepService/" + projectID + "/providers/"+providerID;		
		CloudProvider result;
		try {
			result = (CloudProvider) RestClient.callRestService(
					Command.GET,AEP_SERVICE_URL, path, CloudProvider.class,null, TYPE_XML);			
				System.out.println(result);
				Assert.assertNotNull(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testGetProfileList(){
		
		String projectID = "P-26902";
		String providerID = "4";
		String regionID = "3";
		String accountID ="20"; 
		//String profileID = "1";
		
		String path = "/aepService/" + projectID + "/providers/"+providerID+"/regions/"+regionID+"/accounts/"+accountID+"/profiles/";		
		System.out.println(path);
		List<AppEnvMaster> result;
		try {
			result = (List<AppEnvMaster>) RestClient.callRestService(
					Command.GET_COLLECTION,AEP_SERVICE_URL, path, AppEnvMaster.class,null, TYPE_XML);
			
			for(AppEnvMaster instance:result){
				System.out.println("----------------------");
				System.out.println("Instance id "+instance.getShortDesc());
				System.out.println("Instance desc "+instance.getLongDesc());							
				System.out.println("----------------------");
			}
			Assert.assertNotNull(result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	        
	@Test
	public void testPostinstances(){
		
		String projectID = "P-26902";
		String providerID = "4";
		String regionID = "3";
		String accountID ="20"; 
		String profileID = "8";
		
		String path = "/aepService/" + projectID + "/providers/"+providerID+"/regions/"+regionID+"/accounts/"+accountID+"/profiles/"+profileID+"/instances/createInstance";		
		AppEnvInstance instance =new AppEnvInstance();
		instance.setAccountNumber("62134720226229");
		AppEnvMaster master = new AppEnvMaster();
		master.setId(1l);
		instance.setAppEnvMaster(master);
		instance.setDescription("Junit Test");
		instance.setProjectId(projectID);
		instance.setProperties("{schema-installer-jboss5.dbschema.sqlschema-location=/EBS/Install/rundeck_file_server/executable/merchant_schema.sql,jboss5-war-installer.server.war.location=/EBS/Install/rundeck_file_server/executable/Sample.war,jboss5-war-installer.server.war.datasource.name=MerchantDS,jboss5-war-installer.server.war.databasename=merchant_schema,}");
		//instance.setProperties("{tomcat.download.location=http://apache.techartifact.com/mirror/tomcat/tomcat-6/v6.0.36/bin/apache-tomcat-6.0.36.tar.gz, schema-installer.schema.name=db.sql, tomcat.webserver.script=tomcat, mysql.database.server.location=provide database server location, tomcat.connector.keystoreFile=N/A, mysql.root.uid=root, tomcat.database.ip=${mysql.database.self.ip}, tomcat.connector.truststoreFile=N/A, schema-installer.schema.version=provided schema version, war-installer.war.name=todo-0.1.0.BUILD-SNAPSHOT.war, tomcat.http.port=8080, tomcat.database.pwd=${mysql.root.pwd}, mysql.database.self.ip=${DB_SERVER#MYSQL5:::IP}, mysql.database.server.version=provide database server version, tomcat.connector.truststorePass=N/A, tomcat.database.uid=${mysql.root.uid}, tomcat.context.reloadable=FALSE, tomcat.database.port=${mysql.port}, war-installer.war.location=/home/jpaas/rundeck_file_server/executable, tomcat.connector.keyAlias=N/A, war-installer.war.version=provided war version, schema-installer.remote.ip=${tomcat.server.self.ip}, war-installer.war.script=war-installer, tomcat.datasource.jndi.entry=N/A, tomcat.webappsDirectory=webapps, mysql.schema=provide default schema name, tomcat.httpSecure=FALSE, tomcat.copywars=TRUE, mysql.dbserver.script=mysql, schema-installer.schema.script=schema-script, tomcat.uriencoding=ISO-8859-1, tomcat.server.self.ip=${WEB_SERVER#TOMCAT:::IP}, tomcat.connector.clientAuth=N/A, tomcat.ajp.port=8080, tomcat.connector.keystorePass=N/A, tomcat.connector.keystoreType=N/A, mysql.root.pwd=rajiv, mysql.port=3306, schema-installer.schema.location=/home/jpaas/rundeck_file_server/executable/, war.location=provided war location, tomcat.connector.truststoreType=N/A}");
		instance.setStartTimestamp(new Date());
		
		instance.setRoleId("8");
		instance.setUserId("vijay.srinivasan2@cognizant.com");
		instance.setServiceId("1");
				
		try {
			instance = (AppEnvInstance)RestClient.callRestService(
					Command.POST,AEP_SERVICE_URL, path, AppEnvInstance.class,instance, TYPE_XML);			
				System.out.println(instance);
				Assert.assertNotNull(instance);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testGetAppInstanceStatus(){		
			
		String projectID = "P-26902";
		String providerID = "4";
		String regionID = "3";
		String accountID ="20"; 
		String profileID = "8";
		String appInstanceID = "191";
		
		String path = "/aepService/" + projectID + "/providers/"+providerID+"/regions/"+regionID+"/accounts/"+accountID+"/profiles/"+profileID+"/instances/"+appInstanceID+"";		
				
		try {
			AppEnvInstance result = (AppEnvInstance) RestClient.callRestService(
					Command.GET,AEP_SERVICE_URL, path, AppEnvInstance.class,null, TYPE_XML);	
			System.out.println(result.getStatus());			
			Assert.assertNotNull(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
        

	@Test
	public void testDeleteAppInstance(){		
			
		String projectID = "P-26902";
		String providerID = "4";
		String regionID = "3";
		String accountID ="20"; 
		String profileID = "8";
		String appInstanceID = "191";
		
		String path = "/aepService/" + projectID + "/providers/"+providerID+"/regions/"+regionID+"/accounts/"+accountID+"/profiles/"+profileID+"/instances/"+appInstanceID;		
				
		try {
			Response response = (Response) RestClient.callRestService(
					Command.DELETE,AEP_SERVICE_URL, path, java.lang.Boolean.class,null, TYPE_XML);			
			Assert.assertEquals(response.getStatus(), 200);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}        
}
