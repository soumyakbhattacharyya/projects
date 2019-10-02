package com.cognizant.jpaas2.api.command;

import com.cognizant.jpaas2.api.command.GetEnvironmentStatusCommand.*;
import com.cognizant.jpaas2.api.command.helper.CommandHelper;
import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.BDDMockito.*;


import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.fest.assertions.api.Assertions.assertThat; // main one
import static org.fest.assertions.api.Assertions.atIndex; // for List assertion
import static org.fest.assertions.api.Assertions.entry;  // for Map assertion
import static org.fest.assertions.api.Assertions.extractProperty; // for Iterable/Array assertion
import static org.fest.assertions.api.Assertions.fail; // use when making exception tests
import static org.fest.assertions.api.Assertions.failBecauseExceptionWasNotThrown; // idem
import static org.fest.assertions.api.Assertions.filter; // for Iterable/Array assertion
import static org.fest.assertions.api.Assertions.offset; // for floating number assertion
import static org.fest.assertions.api.Assertions.anyOf; // use with Condition

public class GetEnvironmentStatusCommandTest extends AbstractTest {

	@Test
	public void execute_A$() throws Exception {
		AppEnvInstance envInstanceSample = new AppEnvInstance();
		envInstanceSample.setInstanceId("13-1359361008215-1");
		AppEnvInstance envInstance = CommandHelper.findInstance(envInstanceSample);
		Command target = CommandFactory.GET_ENVIRONMENT_STATUS.newCommand(envInstance);
		((GetEnvironmentStatusCommand) target).setAppEnvInstance(envInstance);
		target.execute();
		assertThat(envInstance.getMachines()).isNotEmpty();

	}
}
