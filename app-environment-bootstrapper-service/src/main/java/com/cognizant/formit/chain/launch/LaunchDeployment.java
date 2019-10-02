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
import com.cognizant.formit.util.ant.AntRundeckAdapter;
import com.cognizant.formit.util.ant.AntUtil;
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
public class LaunchDeployment extends AbstractCommand {

	private static final Logger l = Logger.getLogger(LaunchDeployment.class);

	@Override
	public boolean execute(Context ctx) throws Exception {
		l.info("launching deployment");
		ChainExecutionContext context = cast(ctx);
		if (validate(context)) {
			postMessage(AppConstants.EXECUTE, AppConstants.getLoggableMsg(AppConstants.MSG_5_0_4), context);
			AntUtil.execute(context.getProject());
			postMessage(AppConstants.EXECUTE, AppConstants.getLoggableMsg(AppConstants.MSG_5_0_4_D), context);
			return CONTINUE;
		} else {
			return !CONTINUE;
		}
	}

	/**
	 * validate if launch can be done
	 *
	 * @param context
	 * @return
	 */
	protected boolean validate(ChainExecutionContext context) {
		return null != context && null != context.getProject() ? true : false;
	}
}
