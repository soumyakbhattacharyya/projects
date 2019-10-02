package com.cognizant.formit.util.time;

import com.cognizant.formit.util.time.TimeUtil.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.BDDMockito.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import static org.fest.assertions.api.Assertions.assertThat; // main one
import static org.fest.assertions.api.Assertions.atIndex; // for List assertion
import static org.fest.assertions.api.Assertions.entry;  // for Map assertion
import static org.fest.assertions.api.Assertions.extractProperty; // for Iterable/Array assertion
import static org.fest.assertions.api.Assertions.fail; // use when making exception tests
import static org.fest.assertions.api.Assertions.failBecauseExceptionWasNotThrown; // idem
import static org.fest.assertions.api.Assertions.filter; // for Iterable/Array assertion
import static org.fest.assertions.api.Assertions.offset; // for floating number assertion
import static org.fest.assertions.api.Assertions.anyOf; // use with Condition
public class TimeUtilTest {

	@Test
	public void getNow_A$String() throws Exception {
		Date actual = TimeUtil.getNow();				
		assertThat(actual).isToday();
	}

	
}
