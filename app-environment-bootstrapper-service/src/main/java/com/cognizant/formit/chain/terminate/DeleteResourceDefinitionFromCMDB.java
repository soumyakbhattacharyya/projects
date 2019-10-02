/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.chain.terminate;

import com.cognizant.formit.chain.launch.*;
import com.cognizant.formit.chain.common.AbstractCommand;
import com.cognizant.formit.chain.ChainExecutionContext;
import com.cognizant.formit.chain.common.*;
import com.cognizant.formit.chain.launch.*;
import com.cognizant.formit.main.AppConstants;
import com.cognizant.formit.model.RNode;
import com.cognizant.formit.model.RNodeFactory;
import com.cognizant.formit.util.ant.AntRundeckAdapter;
import com.cognizant.formit.util.ant.AntUtil;
import com.cognizant.formit.util.cloud.CloudResourceManager;
import com.cognizant.formit.util.cloud.IExecutionContext;
import com.cognizant.formit.util.string.StringUtil;
import java.util.List;
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
public class DeleteResourceDefinitionFromCMDB extends AbstractCommand {

	private static final Logger l = Logger.getLogger(DeleteResourceDefinitionFromCMDB.class);

	@Override
	public boolean execute(Context ctx) throws Exception {
		l.info("deleting resource definition from CMDB");
		ChainExecutionContext context = cast(ctx);
		if (validate(context)) {
			postMessage(AppConstants.TERMINATING_CLOUD_RESOURCES, AppConstants.getLoggableMsg(AppConstants.MSG_6_0_0), context);
			List<RNode> nodes = context.getNodesToBeTerminated();
			for (RNode node : nodes) {
				// get instance id				
				String nodeName = node.getName();
				// terminate by invoking appropriate API of cloudResourceManager
				int result = RNodeFactory.purgeNodeFromDB(nodeName, context.getUniqueRunId(), context.getConnectionPoolInitializer());
				if (0 != result) {
					// nothing				
				} else {
					return !CONTINUE;
				}
			}
			l.info("finished deleting resource definition from CMDB");
			postMessage(AppConstants.TERMINATING_CLOUD_RESOURCES, AppConstants.getLoggableMsg(AppConstants.MSG_6_0_0_D), context);
			return CONTINUE;
		} else {
			return !CONTINUE;
		}
	}

	private boolean validate(ChainExecutionContext context) {
		return null != context && null != context.getProject() ? true : false;
	}
}
