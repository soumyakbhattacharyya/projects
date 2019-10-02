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
public class FindCloudResources extends AbstractCommand {

	private static final Logger l = Logger.getLogger(FindCloudResources.class);

	@Override
	public boolean execute(Context ctx) throws Exception {
		l.info("finding cloud resources");
		ChainExecutionContext context = cast(ctx);
		if (validate(context)) {
			postMessage(AppConstants.FINDING_RESOURCES, AppConstants.getLoggableMsg(AppConstants.MSG_2_0_0), context);
			List<RNode> nodes = RNodeFactory.findNodeForInstanceId(context.getUniqueRunId(), context.getConnectionPoolInitializer());
			context.setNodesToBeTerminated(nodes);
			postMessage(AppConstants.FINDING_RESOURCES, AppConstants.getLoggableMsg(AppConstants.MSG_2_0_0_D), context);
			return CONTINUE;
		} else {
			return !CONTINUE;
		}
	}

	private boolean validate(ChainExecutionContext context) {
		return null != context ? true : false;
	}
}
