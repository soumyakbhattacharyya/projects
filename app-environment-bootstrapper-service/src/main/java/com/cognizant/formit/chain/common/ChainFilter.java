/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.chain.common;

import com.cognizant.formit.chain.launch.*;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;
import org.apache.log4j.Logger;

/**
 * filter is being invoked at start and end of chain execution
 * if exception is being thrown still the filter gets a notification for the same
 * @author 239913
 */
public class ChainFilter extends AbstractCommand implements Filter {

	private static final Logger l = Logger.getLogger(ChainFilter.class);

	public boolean execute(Context ctx) throws Exception {
		l.info("filter invoked");		
		return false;
	}

	@Override
	public boolean postprocess(Context context, Exception exception) {
		if (exception == null) return false;
		System.out.println("Exception "
                              + exception.getMessage()
                              + " occurred.");
		return true;
	}
}
