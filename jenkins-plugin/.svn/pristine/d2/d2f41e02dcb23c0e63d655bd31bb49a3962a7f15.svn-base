package com.cognizant.cloudset.framework.util;

import java.util.Properties;
import com.cognizant.cloudset.framework.util.LogUtil;

public class PropertyUtil {
	private static final PropertyUtil instance = new PropertyUtil();
	private static final Properties property = new Properties();
	
	private PropertyUtil(){
	}
	
	public static Properties getProperty(){
		return property;
	}
	public static String get(String key){
		LogUtil.log("Retrieving value for key -- "+key);
		return property.getProperty(key);
	}
}
