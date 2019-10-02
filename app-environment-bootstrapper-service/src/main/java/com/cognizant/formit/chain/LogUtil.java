/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.chain;

import com.cognizant.formit.main.AppConstants;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang.StringUtils;

/**
 * generic log utility that formats loggable message
 * @author cognizant
 */
public final class LogUtil {

	private static final AtomicInteger index = new AtomicInteger();

	/**
	 * adds a prefix
	 *
	 * @return string builder to further append
	 */
	private static final StringBuilder prefix(String uniqueRunId) {
		if (StringUtils.isNotBlank(uniqueRunId)) {
			final StringBuilder builder = new StringBuilder(20);
			return builder.append(AppConstants.CHECK_POINT).
					append(AppConstants.SEPARATOR).
					append(index.getAndIncrement()).
					append(AppConstants.SEPARATOR).
					append(uniqueRunId);
		} else {
			throw new AssertionError("Instance id of the instance unavailable");
		}
	}

	/**
	 * prepare a formatted message for the given run id
	 *
	 * @param message
	 * @param uniqueRunId
	 * @return formatted text
	 */
	public static final String log(final String message, final String uniqueRunId) {
		return prefix(uniqueRunId).append("  ").append(message).append("\n").toString();
	}
}
