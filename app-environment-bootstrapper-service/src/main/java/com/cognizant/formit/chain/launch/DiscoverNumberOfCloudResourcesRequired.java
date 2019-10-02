/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.chain.launch;

import com.cognizant.formit.chain.common.AbstractCommand;
import com.cognizant.formit.chain.ChainExecutionContext;
import com.cognizant.formit.chain.LogUtil;
import com.cognizant.formit.chain.common.*;
import com.cognizant.formit.chain.launch.*;
import com.cognizant.formit.main.AppConstants;
import com.cognizant.formit.util.ant.AntUtil;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;

/**
 * this ought to be stateless by design to facilitate multi threaded scenario read this for further info :
 * http://commons.apache.org/chain/api-release/index.html rule of thumb .. keep everything method local and pass on key - val for the
 * following steps by saving the same inside context
 *
 * @author cognizant
 */
public class DiscoverNumberOfCloudResourcesRequired extends AbstractCommand {

	private final static Logger l = Logger.getLogger(DiscoverNumberOfCloudResourcesRequired.class);

	@Override
	public boolean execute(Context ctx) throws Exception {
		l.info("discovering number of cloud resources");
		// discover the number of VMs required for the deployment
		ChainExecutionContext context = super.cast(ctx);
		if (validate(context)) {
			
			
			Project project = context.getProject();
			if (null != project) {
				postMessage(AppConstants.DISCOVER_RESOURCE_REQUIREMENT, AppConstants.getLoggableMsg(AppConstants.MSG_1_0_0), context);
				postMessage(AppConstants.DISCOVER_RESOURCE_REQUIREMENT, AppConstants.getLoggableMsg(AppConstants.MSG_1_0_1), context);
				int requiredVM = AntUtil.getNumberOfRequiredVMs(project);
				if (requiredVM == 0) {
					postMessage(AppConstants.DISCOVER_RESOURCE_REQUIREMENT, "no resource required !!! aborting", context);
					return !CONTINUE;
				} else {
					postMessage(AppConstants.DISCOVER_RESOURCE_REQUIREMENT, AppConstants.getLoggableMsg(AppConstants.MSG_1_0_2) + requiredVM, context);
					context.setNumberOfRequiredVM(requiredVM);
					return CONTINUE;
				}
			} else {
				return !CONTINUE;
			}
		} else {
			return !CONTINUE;
		}
	}

	/**
	 * validate if the project is valid
	 * @param context
	 * @return 
	 */
	protected boolean validate(ChainExecutionContext context) {
		// get project		
		Project project = context.getProject();
		return null != project ? true : false;
	}
}
