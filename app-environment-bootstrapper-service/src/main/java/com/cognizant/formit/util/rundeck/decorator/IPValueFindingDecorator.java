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
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.Project;

/**
 * find the IP value corresponding to the key
 *
 * @author cognizant
 */
public class IPValueFindingDecorator implements Value {

	private Value value;

	public IPValueFindingDecorator(Value value) {
		this.value = value;
	}

	@Override
	public String get() {
		String key = getArgument().getKey();
		ChainExecutionContext executionContext = getArgument().getExecutionContext();
		if (StringUtils.isNotBlank(key) && key.endsWith(AppConstants._IP_SUFFIX)) {
			return RNodeFactory.findIPFromFittingNode(key, executionContext.getVms());
		} else {
			return getArgument().getKey();
		}
	}

	@Override
	public Argument getArgument() {
		if (null != value) {
			return value.getArgument();
		}
		throw new AssertionError("argument requires to be initialized");
	}
}
