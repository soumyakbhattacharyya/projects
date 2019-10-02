package com.cognizant.jpaas2.api.command;

import com.cognizant.jpaas2.api.SingletonDriver;
import com.cognizant.jpaas2.api.command.TearDownCommand.*;
import com.cognizant.jpaas2.api.command.helper.CommandHelper;
import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.BDDMockito.*;


import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TearDownCommandTest extends AbstractTest {

	@Test
	public void testIfTerminationCanBeDone() throws Exception {
//		// this is how client's API invoker is supposed to call to terminate 
//		AppEnvInstance envInstanceSample = new AppEnvInstance();
//		envInstanceSample.setInstanceId("13-1359361008215-1");
//		AppEnvInstance envInstance = CommandHelper.findInstance(envInstanceSample);
//		Command target = CommandFactory.TEAR_DOWN.newCommand(envInstance);
//		((TearDownCommand) target).setAppEnvInstance(envInstance);
//		target.execute();
//		// then
//		envInstance.getStatus().equals(AppEnvInstance.ExecutionStatus.TORNDOWN.toString());
		System.setProperty("CONFIG_FILE_PATH", "D:/Cloud-Set/Source-Code/App-Env-Code/app-environment-bootstrapper-client-0.0.1-configuration/");
		SingletonDriver driver = SingletonDriver.getInstance();
		// get all sheduled launches
		List<AppEnvInstance> result = driver.findScheduledTerminations();
		// place scheduled lauch requests into the queue
		if (result != null && result.size() > 0) {
			boolean isSuccess = driver.enQueueTerminationRequests(result);
		}
		// process requets
		driver.process();
	}
}
