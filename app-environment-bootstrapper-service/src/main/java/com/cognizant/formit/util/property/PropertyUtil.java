/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.property;

import com.cognizant.formit.util.file.FileHelper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author Cognizant
 */
public final class PropertyUtil {

	private PropertyUtil() {
	}

//	public static final PropertyUtil newPropertyHelper() {
//		return new PropertyUtil();
//	}
	/**
	 * converts a property found at the profilePropPath to a map of key - value pair
	 *
	 * @param profilePropPath
	 * @return
	 * @throws ConfigurationException
	 */
	public static Map<String, String> prop2Map(String profilePropPath) throws ConfigurationException {
		Map<String, String> profileMap = new HashMap<String, String>();
		Configuration config = new PropertiesConfiguration(profilePropPath);
		Iterator iterator = config.getKeys();
		while (iterator.hasNext()) {
			final String name = String.valueOf(iterator.next());
			profileMap.put(name, config.getString(name));
		}
		return profileMap;

	}

	/**
	 * read and return a property
	 *
	 * @param profilePropPath
	 * @return
	 * @throws IOException
	 */
	static public Properties file2Prop(String profilePropPath) throws IOException {
		Properties properties = new Properties();
		properties.load(FileHelper.getStream(profilePropPath));
		return properties;

	}

	/**
	 * convert a map to property
	 *
	 * @param map
	 * @return
	 */
	static public Properties mapToProperties(Map<String, String> map) {
		Properties p = new Properties();
		Set<Map.Entry<String, String>> set = map.entrySet();
		for (Map.Entry<String, String> entry : set) {
			p.put(entry.getKey(), entry.getValue());
		}
		return p;
	}

	/**
	 * convert a property representation to map
	 *
	 * @param props
	 * @return
	 */
	static public Map<String, String> propertiesToMap(Properties props) {
		HashMap<String, String> hm = new HashMap<String, String>();
		Enumeration<Object> e = props.keys();
		while (e.hasMoreElements()) {
			String s = (String) e.nextElement();
			hm.put(s, props.getProperty(s));
		}
		return hm;
	}

	/**
	 * loads the property
	 *
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	static public InputStream getPropertiesToStream(String filePath) throws FileNotFoundException {
		return new FileInputStream(filePath);
	}

	/**
	 * merges content of source to sync
	 *
	 * @param source
	 * @param sync
	 */
	static public void merge(Properties source, Properties sync) {
		sync.putAll(propertiesToMap(source));
	}

	/**
	 * get properties representation of a file
	 *
	 * @param file
	 * @return
	 */
	static public Properties read(final String filePath) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(getPropertiesToStream(filePath));
		return properties;
	}
}
