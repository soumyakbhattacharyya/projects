/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.helper;

import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import com.cognizant.jpaas2.api.domain.AppEnvMaster;
import com.cognizant.jpaas2.api.domain.AppEnvParams;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

/**
 * helper utility functions for processing AppEndParams
 *
 * @author Cognizant
 */
public class AppEnvParamsHelper {

	/**
	 * Discover the key value pairs corresponding to the master blueprint
	 *
	 * @param appEnvInstance
	 * @return properties representation of the standard KV pairs
	 */
	public Properties getSupportedProperties(AppEnvInstance appEnvInstance) throws IOException {
		AppEnvMaster appEnvMaster = appEnvInstance.getAppEnvMaster();
		Set<AppEnvParams> supportedProperties = appEnvMaster.getAppEnvParamses();

		return convertToPropertiesRepresentation(supportedProperties);
	}

	/**
	 * Discover the properties supplied at runtime with the instance
	 *
	 * @param appEnvInstance
	 * @return
	 */
	public Properties getRuntimeProperties(AppEnvInstance appEnvInstance) throws IOException {
		String stringifiedMap = appEnvInstance.getProperties();
	
		return convertToPropertiesRepresentation(stringifiedMap);
	}

	/**
	 * Returns a properties representation
	 *
	 * @param collection
	 * @return
	 */
	public Properties convertToPropertiesRepresentation(Object arg) throws IOException {
		Properties properties = new Properties();
		if (null != arg) {
			if (arg instanceof Set) {
				Set<AppEnvParams> mySet = (Set<AppEnvParams>) arg;
				Iterator<AppEnvParams> iterator = mySet.iterator();
				while (iterator.hasNext()) {
					AppEnvParams param = iterator.next();
					properties.setProperty(param.getParamName().trim(), param.getParamValue().trim());
				}
			} else if (arg instanceof String) {
				String argAfterRemovalOfPrefixAndSuffixBrackets = trim3rdBracketPrefix(trim3rdBracketSuffix(String.valueOf(arg)));
				if (!StringUtils.isEmpty(argAfterRemovalOfPrefixAndSuffixBrackets)) {
					String[] entries = String.valueOf(argAfterRemovalOfPrefixAndSuffixBrackets).split(",");
					for (String str : entries) {
						String[] keyVal = str.split("=");
						properties.setProperty(keyVal[0].trim(), keyVal[1].trim());
					}
				}
			}
		}
		return properties;
	}

	public String trim3rdBracketPrefix(String arg) {
		if (arg != null && arg.startsWith("{")) {
			return arg.substring(1);
		} else {
			return arg;
		}
	}

	public String trim3rdBracketSuffix(String arg) {
		if (arg != null && arg.endsWith("}")) {
			return arg.substring(0, arg.length() - 1);
		} else {
			return arg;
		}
	}

	public boolean matches(Properties suppliedProperties, Properties defaultProperties) {
		boolean matches = true;
		Set<Object> set = suppliedProperties.keySet();
		Iterator itr = set.iterator();
		while (itr.hasNext()) {
			matches = matches & defaultProperties.containsKey(String.valueOf(itr.next()).trim());
			

		
		
		}
		return matches;
	}

	public static final AppEnvParamsHelper newInstance() {
		return new AppEnvParamsHelper();
	}
}
