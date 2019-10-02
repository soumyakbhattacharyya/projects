package com.cognizant.cloudset.client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import com.cognizant.jpaas2.commons.expection.PlatformException;
import com.cognizant.jpaas2.commons.expection.TraceableErrorInfo;

import com.cognizant.cloudset.command.Command;

public class Cloud360Connector {
	
	private Properties props;
	private String cloud360Uri;
	private String userName;
	private String password;
	
	private String userInfo;
	private String authToken;
	
	public Cloud360Connector(String restUrl, String userId, String password) {
		this.cloud360Uri = restUrl;
		this.userName = userId;
		this.password = password;
		
		userInfo = userName + ":"+ password;
	    authToken  =  "Basic "+Base64.encode(userInfo.getBytes());
	}
	
	public Cloud360Connector() throws PlatformException{
		props = new Properties();
		try {
			props.load(new FileInputStream("Cloud360Rest.properties"));
			cloud360Uri = props.getProperty("cloud360.base.uri");
			userName = props.getProperty("cloud360.username");
			password = props.getProperty("cloud360.password");
			
			userInfo = userName + ":"+ password;
		    authToken  =  "Basic "+Base64.encode(userInfo.getBytes());
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new PlatformException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new PlatformException();
		}
	}
	
	public HttpURLConnection getConnection(String restApi) throws PlatformException{
		HttpURLConnection connection = null;
		try {
			String baseUrl = cloud360Uri.concat(restApi);
			URL url = new URL(baseUrl);
			connection = (HttpURLConnection) url.openConnection();
			if(connection != null) {
				return connection;
			} else {
				TraceableErrorInfo error = new TraceableErrorInfo(1000, "Cloud360RestClient", "Failed to connect to Cloud360", null);
				throw new PlatformException("Error", null, error);
			}
		} catch (IOException e) {
			e.printStackTrace();
			TraceableErrorInfo error = new TraceableErrorInfo(1000, "Cloud360RestClient", "Failed to connect to Cloud360", null);
			throw new PlatformException("Error", null, error);
		}
	}

	public String processRequest(HttpURLConnection connection, String method, String requestBody, HashMap<String, String> requestHeaders)
		throws PlatformException {
		String responseString = null;
		try {
			if(connection != null){
		        connection.setRequestProperty("Authorization", authToken);
		        connection.setRequestMethod(method);
		
		        if (requestHeaders != null) {
					Set<Entry<String, String>> requestHeadersSet = requestHeaders.entrySet();
					if(null!=requestHeadersSet){
						for (Entry<String, String> requestHeadersEntry : requestHeadersSet) {
		                	connection.setRequestProperty(requestHeadersEntry.getKey(), requestHeadersEntry.getValue());
		           	 	}
					}
		        }
		        connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
		        if (requestBody != null) {
		            connection.setDoOutput(true);
		            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
		            out.write(requestBody);
		            out.close();
		        }
		        connection.connect();
			    int responseCode = connection.getResponseCode();
			    if(responseCode != 200) {
			     	TraceableErrorInfo error = new TraceableErrorInfo(1001, "Cloud360RestClient", "Failed to complete operation", null);
					throw new PlatformException("Error", null, error);
			    }
			    responseString = getResponseString(connection);
	        }
		} catch(IOException ex) {
			ex.printStackTrace();
			TraceableErrorInfo error = new TraceableErrorInfo(1001, "Cloud360RestClient", "Failed to complete operation", null);
			throw new PlatformException("Error", null, error);
	    } finally {
	       	try {
	            if (connection != null) {
	                connection.disconnect();
	                connection = null;
	            }
		    } catch (Exception e) {
		            e.printStackTrace();
		    }
	    }
	    return responseString;
    }
	
	private String getResponseString(HttpURLConnection connection) throws IOException {
	      
        BufferedReader rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));          
        StringBuilder sb = new StringBuilder();                  
        String line; 
        try {
	        while ((line = rd.readLine()) != null) {              
	            if (line.trim().length() > 1) {
	                sb.append(line);       
	            }
	        }
        }
        finally {
        	if(rd != null){
        		rd.close();
			}
        }
        return (sb.toString());	
    }
	
}
