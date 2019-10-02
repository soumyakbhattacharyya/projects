/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.chain.common;

import com.cognizant.formit.chain.ChainExecutionContext;
import com.cognizant.formit.chain.LogUtil;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.tools.ant.Project;

/**
 *
 * @author cognizant
 */
public abstract class AbstractCommand implements Command {

	protected final static Boolean CONTINUE = Boolean.FALSE;

	/**
	 * casting function
	 *
	 * @param context
	 * @return
	 */
	protected ChainExecutionContext cast(Context context) {
		if (context instanceof ChainExecutionContext) {
			return (ChainExecutionContext) context;
		}
		throw new AssertionError();
	}

	protected void postMessage(String shortDesc, String longDesc, ChainExecutionContext chainExecutionContext) {
		chainExecutionContext.getMessageMap().put(shortDesc, LogUtil.log(longDesc, chainExecutionContext.getUniqueRunId()));
	}
}
