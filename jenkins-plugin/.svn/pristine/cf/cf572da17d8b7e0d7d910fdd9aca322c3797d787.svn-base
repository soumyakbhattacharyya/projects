package hudson.plugins.projectstats.data;

import java.util.TimerTask;

import org.apache.log4j.Logger;

public class ProjectStrategyUsageCollectionTask extends TimerTask{

	final static Logger LOGGER = Logger.getLogger(ProjectStrategyUsageCollectionTask.class);
	
	public void run() {
		ProjectStrategyUsageCollector.getProjectInfrastructureUsage();
	}
}
