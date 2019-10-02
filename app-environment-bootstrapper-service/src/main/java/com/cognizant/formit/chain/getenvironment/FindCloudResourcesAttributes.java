/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.chain.getenvironment;

import com.cognizant.formit.chain.terminate.*;
import com.cognizant.formit.chain.launch.*;
import com.cognizant.formit.chain.common.AbstractCommand;
import com.cognizant.formit.chain.ChainExecutionContext;
import com.cognizant.formit.chain.common.*;
import com.cognizant.formit.chain.launch.*;
import com.cognizant.formit.model.RNode;
import com.cognizant.formit.util.ant.AntRundeckAdapter;
import com.cognizant.formit.util.ant.AntUtil;
import com.cognizant.formit.util.cloud.CloudResourceManager;
import com.cognizant.formit.util.cloud.IExecutionContext;
import com.cognizant.formit.util.string.StringUtil;
import com.cognizant.jpaas2.resource.VirtualMachineType;
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
public class FindCloudResourcesAttributes extends AbstractCommand {

	private static final Logger l = Logger.getLogger(TerminateCloudResources.class);

	@Override
	public boolean execute(Context ctx) throws Exception {
		l.info("terminating cloud resources");
		ChainExecutionContext context = cast(ctx);
		if (validate(context)) {
			List<RNode> nodes = context.getNodesToBeTerminated();
			CloudResourceManager cloudResourceManager = CloudResourceManager.newInstance(context.getDeploymentConfig());
			for (RNode node : nodes) {
				// get instance id				
				String instanceId = StringUtil.getInstanceId(node.getName());
				// get execution context
				IExecutionContext executionContext = context.getExecutionContext();
				// terminate by invoking appropriate API of cloudResourceManager
				// Boolean result = cloudResourceManager.terminate(instanceId, executionContext);
				VirtualMachineType result = cloudResourceManager.getState(instanceId, executionContext);
				context.getMachines().add(result);

			}
			if (Boolean.TRUE.equals(context.getMachines().size() > 0)) {
				return CONTINUE;
			} else {
				return !CONTINUE;
			}
			//l.info("finished terminating cloud resources");
			//return CONTINUE;
		} else {
			return !CONTINUE;
		}
	}

	private boolean validate(ChainExecutionContext context) {
		return null != context && null != context.getProject() ? true : false;
	}
}
