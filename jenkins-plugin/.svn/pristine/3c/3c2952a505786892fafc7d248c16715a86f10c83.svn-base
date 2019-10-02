/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.command;

import com.cognizant.cloudset.framework.Constants;
import com.cognizant.cloudset.framework.command.cli.AbstractCommandHandler;

import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;
import java.util.Map;
import org.apache.commons.collections.ListUtils;

/**
 * base class for all parameter classes to implement
 * 
 * @author cognizant
 */
public abstract class CommandParameters {
	// constructs base parameter
	public List<String> baseParameters() {
		List<String> base = new ArrayList<String>(asList(
				Constants.BASE_OPTION_SERVER_FLAG, Constants.JENKINS_URL));
		return base;
	}

	protected String projectId;
	protected String requesterId;
	
	public String getRequesterId(){
		return requesterId;
	}

	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}

	public String[] list2Array(WithOperator[] operators) {		
		List<String> placeHolder = baseParameters();
		for(WithOperator operator : operators){								
			placeHolder = operator.withOperation(placeHolder);
		}
		return placeHolder.toArray(new String[placeHolder.size()]);
	}
}