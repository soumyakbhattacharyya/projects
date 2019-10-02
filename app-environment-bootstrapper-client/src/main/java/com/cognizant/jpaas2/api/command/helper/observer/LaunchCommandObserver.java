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
 *
 * @author 239913
 */
public class LaunchCommandObserver implements Observer {

	private final ChainExecutionContext chainExecutionContext;

	public LaunchCommandObserver(ChainExecutionContext chainExecutionContext) {
		this.chainExecutionContext = chainExecutionContext;
	}

	public void update(Observable o, Object o1) {
		if (o1 instanceof String) {
			String[] stringArr = String.valueOf(o1).split(AppConstants.HASH);
			CommandHelper.updateInstanceTaskOverview(chainExecutionContext.getUniqueRunId(), stringArr[0], stringArr[3], new Long(1));
		} else {
			throw new UnsupportedOperationException();
		}

	}
}
