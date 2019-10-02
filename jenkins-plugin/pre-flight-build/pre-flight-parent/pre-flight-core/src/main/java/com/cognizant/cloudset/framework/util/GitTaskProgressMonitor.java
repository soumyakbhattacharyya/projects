/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.util;

import com.cognizant.cloudset.framework.util.LogUtil;
import org.eclipse.jgit.lib.ProgressMonitor;

/**
 * monitors progress of a lengthy operation with git
 *
 * @author cognizant
 */
public class GitTaskProgressMonitor implements ProgressMonitor {

	@Override
	public void start(int totalTasks) {
		LogUtil.log("started interaction with the repository");
	}

	@Override
	public void beginTask(String title, int totalWork) {
		LogUtil.log("initiating following task :" + title);
	}

	@Override
	public void update(int completed) {
		LogUtil.log("updated :" + completed);
	}

	@Override
	public void endTask() {
		LogUtil.log("completed interaction with the repository");
	}

	@Override
	public boolean isCancelled() {
		return false;
	}
}