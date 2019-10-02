package com.cognizant.jpaas2.api.command;

import com.cognizant.jpaas2.api.SingletonDriver;
import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import java.util.List;
import org.junit.Test;

public class LaunchCommandTest extends AbstractTest {

	@Test
	public void testIfLaunchCanBeDone() throws Exception {
		System.setProperty("CONFIG_FILE_PATH", "D:/Cloud-Set/Source-Code/App-Env-Code/app-environment-bootstrapper-client-0.0.1-configuration/");
		SingletonDriver driver = SingletonDriver.getInstance();
		// get all sheduled launches
		List<AppEnvInstance> result = driver.findScheduledLaunches();
		// place scheduled lauch requests into the queue
		if (result != null && result.size() > 0) {
			boolean isSuccess = driver.enQueueLaunchRequests(result);
		}
		// process requests
		driver.process();
	}
}
