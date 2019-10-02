/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.chain.common;

import com.cognizant.formit.chain.launch.*;
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
public class InitializeExecutionContext implements Command {

	private static final Logger l = Logger.getLogger(InitializeExecutionContext.class);

	public boolean execute(Context ctx) throws Exception {
		l.info("initializing execution context");
		l.info("finished initializing execution context");
		return false;
	}
}
