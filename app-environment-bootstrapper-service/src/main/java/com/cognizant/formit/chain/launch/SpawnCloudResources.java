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
import com.cognizant.formit.model.IDeploymentConfig;
import com.cognizant.formit.util.ant.AntUtil;
import com.cognizant.formit.util.cloud.CloudResourceManager;
import com.cognizant.jpaas2.resource.VirtualMachineType;
import java.util.List;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;

/**
 * this ought to be stateless by design to facilitate multi threaded scenario read this for further info :
 * http://commons.apache.org/chain/api-release/index.html rule of thumb .. keep everything method local and pass on key - val for the
 * following steps by saving the same inside context
 *
 * @author cognizant
 */
public class SpawnCloudResources extends AbstractCommand {

	private static final Logger l = Logger.getLogger(SpawnCloudResources.class);
	private final static Boolean CONTINUE = Boolean.FALSE;

	@Override
	public boolean execute(Context ctx) throws Exception {
		l.info("spawining & refreshing cloud resources");		
		// spawn resources and refresh their state
		ChainExecutionContext context = cast(ctx);
		if (validate(context)) {
			postMessage(AppConstants.SPAWN_CLOUD_RESOURCE, AppConstants.getLoggableMsg(AppConstants.MSG_3_0_0), context);
			postMessage(AppConstants.SPAWN_CLOUD_RESOURCE, AppConstants.getLoggableMsg(AppConstants.MSG_3_0_1), context);
			int numberOfVMRequired = context.getNumberOfRequiredVM();
			CloudResourceManager cloudResourceManager = CloudResourceManager.newInstance(context.getDeploymentConfig());			
			List<VirtualMachineType> machineTypes = cloudResourceManager.getResources(numberOfVMRequired, context.getExecutionContext());
			postMessage(AppConstants.SPAWN_CLOUD_RESOURCE, AppConstants.getLoggableMsg(AppConstants.MSG_3_0_2), context);
			List<VirtualMachineType> refreshedMachineTypes = cloudResourceManager.refreshResources(machineTypes, context);
			postMessage(AppConstants.SPAWN_CLOUD_RESOURCE, AppConstants.getLoggableMsg(AppConstants.MSG_3_0_3), context);
			l.info("numberOfVMRequired is "+numberOfVMRequired);
			l.info("refreshedMachineTypes is "+refreshedMachineTypes);
			l.info("refreshedMachineTypes.size() is "+refreshedMachineTypes.size());
			if (null != refreshedMachineTypes && refreshedMachineTypes.size() == numberOfVMRequired) {
				
				context.setMachines(refreshedMachineTypes);
				l.info("The machine are set to context");
				postMessage(AppConstants.SPAWN_CLOUD_RESOURCE, AppConstants.getLoggableMsg(AppConstants.MSG_3_0_0_D), context);
				return CONTINUE;
			} else {
				return !CONTINUE;
			}
		} else {
			return !CONTINUE;
		}
	}
	/**
	 * validate if this deployment has non zero VM requirement and a valid deployment configuration 
	 * @param context
	 * @return 
	 */
	protected boolean validate(ChainExecutionContext context) {
		int numberOfVMRequired = context.getNumberOfRequiredVM();
		IDeploymentConfig config = context.getDeploymentConfig();
		return 0 != numberOfVMRequired && null != config ? true : false;
	}
}
