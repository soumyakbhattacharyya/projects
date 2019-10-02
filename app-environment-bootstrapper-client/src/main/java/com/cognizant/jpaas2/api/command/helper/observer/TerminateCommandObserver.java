/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.command.helper.observer;

import com.cognizant.formit.chain.ChainExecutionContext;
import com.cognizant.formit.main.AppConstants;
import com.cognizant.jpaas2.api.command.helper.CommandHelper;
import java.util.Observable;
import java.util.Observer;

/**
 * observes a termination sequence
 *
 * @author cognizant
 */
public class TerminateCommandObserver implements Observer {

	private final ChainExecutionContext chainExecutionContext;

	public TerminateCommandObserver(ChainExecutionContext chainExecutionContext) {
		this.chainExecutionContext = chainExecutionContext;
	}

	public void update(Observable o, Object o1) {
		if (o1 instanceof String) {
			String[] stringArr = String.valueOf(o1).split(AppConstants.HASH);
			CommandHelper.updateInstanceTaskOverview(chainExecutionContext.getUniqueRunId(), stringArr[0], stringArr[3], new Long(2));
		} else {
			throw new UnsupportedOperationException();
		}

	}
}
