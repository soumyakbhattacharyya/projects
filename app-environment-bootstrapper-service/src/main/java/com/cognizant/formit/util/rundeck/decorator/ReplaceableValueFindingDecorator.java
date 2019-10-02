/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.rundeck.decorator;

import com.cognizant.formit.chain.ChainExecutionContext;
import com.cognizant.formit.main.AppConstants;
import com.cognizant.formit.model.RNodeFactory;
import com.cognizant.formit.util.rundeck.Argument;
import com.cognizant.formit.util.rundeck.Value;
import com.cognizant.formit.util.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.Project;

/**
 * find value corresponding to replaceable parameter
 * @author cognizant
 */
public class ReplaceableValueFindingDecorator implements Value {

	private Value value;

	public ReplaceableValueFindingDecorator(Value value) {
		this.value = value;
	}

	@Override
	public String get() {
		String key = getArgument().getKey();
		Project project = getArgument().getProject();
		if (isReplaceableKey(key)) {
			// strip of initial ${
			String valWithoutPrefixedBraces = key.substring(2);
			// strip end of the word "}" character
			String valToBeTreatedAsKey = valWithoutPrefixedBraces.substring(0, valWithoutPrefixedBraces.indexOf(AppConstants.END_BRACKET));
			return project.getUserProperty(StringUtil.getKeyNameMinusJobnamePrefix(valToBeTreatedAsKey));
		} else {
			return key;
		}
	}

	private boolean isReplaceableKey(String val) {
		return StringUtils.isNotEmpty(val)
				&& StringUtils.startsWith(val, AppConstants.DOLLAR_AND_START_BRACKET)
				&& StringUtils.endsWith(val, AppConstants.END_BRACKET)
				&& !StringUtils.endsWith(val, AppConstants._IP_SUFFIX);
	}

	@Override
	public Argument getArgument() {
		if (null != value) {
			return value.getArgument();
		}
		throw new AssertionError("argument requires to be initialized");
	}
}
