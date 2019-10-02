package com.cognizant.cloudset.message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.cognizant.cloudset.client.Cloud360Connector;
import com.cognizant.cloudset.command.Command;
import com.cognizant.cloudset.constants.Cloud360Constants;
import com.cognizant.cloudset.model.Application;
import com.cognizant.cloudset.model.ApplicationProfile;
import com.cognizant.cloudset.model.Cloud;
import com.cognizant.cloudset.model.ComputeProfile;
import com.cognizant.cloudset.model.Instance;
import com.cognizant.cloudset.model.InstanceStatus;
import com.cognizant.cloudset.model.Message;
import com.cognizant.cloudset.model.Provider;
import com.cognizant.cloudset.model.User;
import com.cognizant.cloudset.model.UserGroup;
import com.cognizant.jpaas2.commons.expection.PlatformException;

public class Cloud360MessageExtractor {
	
	private Cloud360Connector cloudConnector;
	
	public Cloud360MessageExtractor(String restUrl, String userId, String password) {
		cloudConnector = new Cloud360Connector(restUrl, userId, password);
	}
	
	public Cloud360Response extractMessage(int restCommand, String input) {
		
		switch(restCommand) {
		case Cloud360Constants.GET_ALL_CLOUDS:
			return extractResponse("cloud", Command.GET, null, null, "Cloud", Cloud.class);
		
		case Cloud360Constants.GET_CLOUD_DETAILS_BY_ID:
			return extractResponse("cloud/"+input, Command.GET, null, null, "Cloud", Cloud.class);
		
		case Cloud360Constants.GET_ALL_PROVIDERS:
			return extractResponse(input+"/provider", Command.GET, null, null, "Provider", Provider.class);
		
		case Cloud360Constants.GET_PROVIDER_DETAILS_BY_ID:
			return extractResponse("provider/"+input, Command.GET, null, null, "Provider", Provider.class);
		
		case Cloud360Constants.GET_ALL_USERS_FOR_A_CLOUD:
			return extractResponse(input+"/user", Command.GET, null, null, "User", User.class);
		
		case Cloud360Constants.GET_USER_DETAILS_BY_ID:
			return extractResponse("user/"+input, Command.GET, null, null, "User", User.class);
		
		case Cloud360Constants.GET_ALL_USER_GROUPS_FOR_A_CLOUD:
			return extractResponse(input+"/usergroup", Command.GET, null, null, "UserGroup", UserGroup.class);
			
		case Cloud360Constants.GET_USER_GROUP_DETAILS_BY_ID:
			return extractResponse("usergroup/"+input, Command.GET, null, null, "UserGroup", UserGroup.class);
			
		case Cloud360Constants.GET_LIST_OF_APP_PROFILES_FOR_A_CLOUD:
			return extractResponse(input+"/appprofile", Command.GET, null, null, "ApplicationProfile", ApplicationProfile.class);
			
		case Cloud360Constants.GET_APP_PROFILE_DETAILS_BY_ID:
			return extractResponse("appprofile/"+input, Command.GET, null, null, "ApplicationProfile", ApplicationProfile.class);
			
		case Cloud360Constants.GET_LIST_OF_APPS_IN_A_CLOUD:
			return extractResponse(input+"/app", Command.GET, null, null, "Application", Application.class);
			
		case Cloud360Constants.GET_APP_DETAILS_BY_ID:
			return extractResponse("app/"+input, Command.GET, null, null, "Application", Application.class);
			
		case Cloud360Constants.GET_LIST_OF_COMPUTE_PROFILES_FOR_A_CLOUD:
			return extractResponse(input+"/computeprofile", Command.GET, null, null, "ComputeProfile", ComputeProfile.class);
			
		case Cloud360Constants.GET_COMPUTE_PROFILE_DETAILS_BY_ID:
			return extractResponse("computeprofile/"+input, Command.GET, null, null, "ComputeProfile", ComputeProfile.class);
			
		case Cloud360Constants.CREATE_COMPUTE_INSTANCE_FROM_COMPUTE_PROFILE:
			return extractResponse("compute/"+input, Command.POST, null, null, "Instance", Message.class);
			
		case Cloud360Constants.DE_PROVISION_COMPUTE_INSTANCE:
			return extractResponse("compute/"+input, Command.DELETE, null, null, "Instance", Instance.class);
			
		case Cloud360Constants.START_COMPUTE_INSTANCE:
			return extractResponse("compute/"+input, Command.PUT, "action=start", null, "Instance", Instance.class);

		case Cloud360Constants.STOP_COMPUTE_INSTANCE:
			return extractResponse("compute/"+input, Command.PUT, "action=stop", null, "Instance", Instance.class);
			
		case Cloud360Constants.GET_COMPUTE_INSTANCES:
			return extractResponse(input+"/compute", Command.GET, null, null, "Instance", Instance.class);
			
		case Cloud360Constants.GET_COMPUTE_INSTANCE_DETAILS_BY_ID:
			return extractResponse("compute/"+input, Command.GET, null, null, "Instance", Instance.class);
			
		case Cloud360Constants.GET_COMPUTE_INSTANCE_STATUS:
			return extractResponse("status/"+input, Command.GET, null, null, "Instance", InstanceStatus.class);
			
		default:
			return null;
		}
	}
	
	
	private Cloud360Response extractResponse(String path, Command command, String requestBody, 
			HashMap<String, String> requestHeaders, String className, Class<?> classType) {
		Cloud360Response response = new Cloud360Response();
		try {
//			Cloud360Connector cloudConnector = new Cloud360Connector();
			HttpURLConnection connection = cloudConnector.getConnection(path);
			String responseString = cloudConnector.processRequest(connection, command.toString(), requestBody, requestHeaders);
			
			if(responseString != null && responseString.trim().length() > 0) {
				
				response.setResponseCode(Cloud360Constants.SUCCESS);
//				if(command.equals(Command.GET)) {
					response.setResponseResult(populateObjectFromJSON(responseString, className, classType));					
//				} else {
//					response.setResponseResult(populateObjectFromXML(responseString, className, classType));
//				}
			}
		} catch (PlatformException e) {
			response.setResponseCode(Cloud360Constants.ERROR);
			response.setErrorObject(e);
			e.printStackTrace();
		}
		return response;
	}
	
	private List<Object> populateObjectFromJSON(String json, String className, Class<?> classType) {
		Class<?> objClass = classType;
		Object newObj = null;
		List<Object> list = new ArrayList<Object>();
		try {
			
			JSONParser parser = new JSONParser();
			JSONObject jObject = (JSONObject)parser.parse(json);
			JSONArray jArray = (JSONArray) jObject.get(className);
			
			if(jArray != null) {
				for(int i=0;i<jArray.size();i++) {
					JSONObject obj = (JSONObject) jArray.get(i);
					
					if(obj != null) {
						newObj = objClass.newInstance();
						Field[] fields = objClass.getDeclaredFields();
						
						for(int j=0;j<fields.length;j++) {
							
							Field field = fields[j]; 
							Type type = field.getGenericType();
							
							if(type.toString().equalsIgnoreCase(Cloud360Constants.STRING_TYPE)) {
							
								field.setAccessible(true);
								String jsonField = field.toString().substring(field.toString().lastIndexOf('.')+1);
								Object value = obj.get(jsonField);
								if(value != null) {
									value = value instanceof String?(String) value:value.toString(); 
								}
								field.set(newObj, value);
								field.setAccessible(false);					
							}
						}
						list.add(newObj);
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private List<Object> populateObjectFromXML(String xml, String className, Class<?> classType) {
		Class<?> objClass = classType;
		Object newObj = null;
		List<Object> list = new ArrayList<Object>();
		try {
			
			if(xml != null) {
				xml = xml.substring(xml.indexOf('<'));
				InputStream is = new ByteArrayInputStream(xml.getBytes());
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(is);
				doc.getDocumentElement().normalize();
				
				newObj = objClass.newInstance();
				Field[] fields = objClass.getDeclaredFields();
				
				for(int j=0;j<fields.length;j++) {
					
					Field field = fields[j]; 
					Type type = field.getGenericType();
					
					if(type.toString().equalsIgnoreCase(Cloud360Constants.STRING_TYPE)) {
					
						field.setAccessible(true);
						String xmlField = field.toString().substring(field.toString().lastIndexOf('.')+1);
						
						Node node = doc.getElementsByTagName(xmlField).item(0);
						Element el = (Element) node;
						
						if(el != null) {
							Object value = el.getTextContent();
							
							if(value != null) {
								value = value instanceof String?(String) value:value.toString(); 
							}
							field.set(newObj, value);	
						}
						field.setAccessible(false);					
					}
				}
				list.add(newObj);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
