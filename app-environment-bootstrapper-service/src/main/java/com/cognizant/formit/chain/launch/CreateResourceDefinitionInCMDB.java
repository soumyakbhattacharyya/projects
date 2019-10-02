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
import com.cognizant.formit.util.cloud.CloudResourceManager;
import com.cognizant.jpaas2.resource.VirtualMachineType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * this ought to be stateless by design to facilitate multi threaded scenario read this for further info :
 * http://commons.apache.org/chain/api-release/index.html rule of thumb .. keep everything method local and pass on key - val for the
 * following steps by saving the same inside context
 *
 * @author cognizant
 */
public class CreateResourceDefinitionInCMDB extends AbstractCommand {

	private static final Logger l = Logger.getLogger(CreateResourceDefinitionInCMDB.class);

	@Override
	public boolean execute(Context ctx) throws Exception {
		l.info("creating resource definition in CMDB (database)");
		ChainExecutionContext context = cast(ctx);
		List<VirtualMachineType> argumentMachines = new ArrayList<VirtualMachineType>();
		argumentMachines.addAll(context.getMachines());
		if (validate(context)) {
			postMessage(AppConstants.DEFINE_CLOUD_RESOURCE_IN_CMDB, AppConstants.getLoggableMsg(AppConstants.MSG_3_0_5), context);
			String userID = context.getDeploymentConfiguration().getProperty(AppConstants.MACHINE_USER_ID);
			String osFamily = "unix";
			
			Set<RNode> machines = RNodeFactory.produceRNode(argumentMachines, context.getProject(), context.getVms(), context.getUniqueRunId(),userID,osFamily);
			//postMessage(AppConstants.DEFINE_CLOUD_RESOURCE_IN_CMDB, "completed producing "+(machines != null ? machines.size() : 0)+" runtime usable resource definition", context);
			if (null != machines && machines.size() == context.getMachines().size()) {
				int numberOfInsertion = RNodeFactory.insertNodeToDB(machines, context.getConnectionPoolInitializer());				
				if (0 == numberOfInsertion) {					
					return !CONTINUE;
				}
				postMessage(AppConstants.DEFINE_CLOUD_RESOURCE_IN_CMDB, AppConstants.getLoggableMsg(AppConstants.MSG_3_0_5_D), context);
				return CONTINUE;
			} else {
				return !CONTINUE;
			}
		} else {
			return !CONTINUE;
		}
	}

	/**
	 * validate if machines have already been spawned, there exists a valid project, VM collection that 
	 * holds tag vs. vm is still empty and there is a valid run id
	 * @param context
	 * @return 
	 */
	protected boolean validate(ChainExecutionContext context) {
		return null != context
				&& context.getMachines() != null
				&& context.getProject() != null
				&& context.getVms().isEmpty()
				&& !StringUtils.isBlank(context.getUniqueRunId()) ? true : false;
	}
}
