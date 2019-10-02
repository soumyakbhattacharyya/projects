/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.string;

import com.cognizant.formit.main.AppConstants;
import com.cognizant.formit.model.steps.MajorStep;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.Project;

/**
 *
 * @author 239913
 */
public final class StringUtil {

	static public String translate(String from) {
		return !StringUtils.isEmpty(from) && String.valueOf(from.toUpperCase()).equals(AppConstants._CONTAINER) ? MajorStep.CONTAINER : "";
	}

	/**
	 * Deployment profile should have keys in the following format JOB_NAME_WITHOUT_"DOT(.)"_CHARACTER.rest.portion. This method removes
	 * JOB_NAME_WITHOUT_"DOT(.)"_CHARACTER portion from the key
	 *
	 * @param str
	 * @return
	 */
	static public String getKeyNameMinusJobnamePrefix(String str) {
		String result = "";
		if (StringUtils.isNotBlank(str)) {
			int firstIndex = StringUtils.indexOf(str, ".");
			result = str.substring(firstIndex + 1);
		}
		return result;
	}

	/**
	 * get instance id by parsing the name of a node
	 *
	 * @param name
	 * @return
	 */
	static public String getInstanceId(String name) {
		String result = "";
		if (StringUtils.isNotBlank(name)) {
			String[] parts = name.split(AppConstants.HASH);
			if (null != parts && (parts.length >= 3)) {
				result = parts[2];
				return result;
			}
		}
		throw new AssertionError("it is assumed that name of a node will be in following format "
				+ " GENERIC_TAG#SPECIFIC_TAG#INSTANCE_ID, for example WEB_SERVER#TOMCAT#INSTANCE_ID ");
	}

	public static void main(String[] args) {

		String result = getInstanceId("dsfvsdf");
		System.out.println(result);
	}

	/**
	 * recursive function to find value of a referenced key
	 *
	 * @param map
	 * @param key
	 * @return
	 */
	static public String finder(Project project, String key) {
		if (keyable(project.getUserProperty(key))) {
			return finder(project, extractor(project.getUserProperty(key)));
		}
		return project.getUserProperty(key);
	}

	/**
	 * extracts the val from ${val} string
	 *
	 * @param argument
	 * @return
	 */
	static public String extractor(String argument) {
		if (!StringUtils.isEmpty(argument)) {
			if (argument.startsWith(AppConstants.DOLLAR_AND_START_BRACKET)) {
				if (argument.endsWith(AppConstants.END_BRACKET) && !argument.endsWith(AppConstants._IP_SUFFIX)) {
					return argument.substring(AppConstants.STARTING_POS, argument.indexOf(AppConstants.END_BRACKET));
				}
				return argument;
			}
			return argument;
		}
		return argument;
	}

	/**
	 * validates if argument is of the form ${val}
	 *
	 * @param argument
	 * @return
	 */
	static public Boolean keyable(String argument) {
		if (!StringUtils.isEmpty(argument)) {
			if (argument.startsWith(AppConstants.DOLLAR_AND_START_BRACKET)) {
				if (argument.endsWith(AppConstants.END_BRACKET) && !argument.endsWith(AppConstants._IP_SUFFIX)) {
					return Boolean.TRUE;
				}
				return Boolean.FALSE;
			}
			return Boolean.FALSE;
		}
		return Boolean.FALSE;
	}
}
