/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.chain.launch;

import com.cognizant.formit.chain.common.AbstractCommand;
import com.cognizant.formit.chain.ChainExecutionContext;
import com.cognizant.formit.chain.common.*;
import com.cognizant.formit.chain.launch.*;
import com.cognizant.formit.main.AppConstants;
import com.cognizant.formit.model.RNode;
import com.cognizant.formit.model.RNodeFactory;
import com.cognizant.formit.util.ant.AntRundeckAdapter;
import com.cognizant.jpaas2.resource.VirtualMachineType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.log4j.Logger;

/**
 * this ought to be stateless by design to facilitate multi threaded scenario read this for further info :
 * http://commons.apache.org/chain/api-release/index.html rule of thumb .. keep everything method local and pass on key - val for the
 * following steps by saving the same inside context
 *
 * @author cognizant
 */
public class TagTasksToResource extends AbstractCommand {

	private static final Logger l = Logger.getLogger(TagTasksToResource.class);

	@Override
	public boolean execute(Context ctx) throws Exception {
		l.info("tagging tasks to resources");
		ChainExecutionContext context = cast(ctx);
		if (validate(context)) {
			postMessage(AppConstants.TAG_SEQUENCE_TO_NODE, AppConstants.getLoggableMsg(AppConstants.MSG_5_0_0), context);
			AntRundeckAdapter.tagJobs(context.getProject(), context.getRundeckProject(), context.getVms(), context.getConnectionPoolInitializer());
			postMessage(AppConstants.TAG_SEQUENCE_TO_NODE, AppConstants.getLoggableMsg(AppConstants.MSG_5_0_0_D), context);
			return CONTINUE;
		} else {
			return !CONTINUE;
		}
	}

	protected boolean validate(ChainExecutionContext context) {
		return context.getProject() != null && context.getRundeckProject() != null && context.getVms() != null
				&& context.getConnectionPoolInitializer() != null ? true : false;
	}
}
