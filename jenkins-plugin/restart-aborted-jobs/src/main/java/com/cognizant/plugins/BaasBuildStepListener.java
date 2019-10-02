package com.cognizant.plugins;

import hudson.Extension;
import hudson.model.BuildListener;
import hudson.model.BuildStepListener;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildStep;

import com.cognizant.monitor.AbortedJobsMonitor;

@Extension
public class BaasBuildStepListener extends BuildStepListener{
	
	private AbortedJobsMonitor abortedJobsMonitor;

	@Override
	public void finished(AbstractBuild build, BuildStep buildStep,
			BuildListener listener, boolean arg3) {
		abortedJobsMonitor = AbortedJobsMonitor.getInstance();
		abortedJobsMonitor.onJobFinished(build);
	}

	@Override
	public void started(AbstractBuild build, BuildStep buildStep, BuildListener listener) {
		abortedJobsMonitor = AbortedJobsMonitor.getInstance();
		abortedJobsMonitor.onJobStarted(build);
	}

}
