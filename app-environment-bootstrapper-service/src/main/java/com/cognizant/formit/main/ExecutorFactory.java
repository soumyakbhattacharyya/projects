/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.main;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * factory for creating & retrieving already existing executors for a given run id
 *
 * @author Cognizant
 */
public class ExecutorFactory {
//
//	private static final Map<String, Executor> executors = Collections.synchronizedMap(new HashMap<String, Executor>());
//	private static final Logger l = Logger.getLogger(ExecutorFactory.class);
//
//	/**
//	 * newExecutor an executor which was associated with the run id
//	 *
//	 * @param runId
//	 * @return executor that has been found or null
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 */
//	public static Executor getExecutor(final String instanceId) {
//		if (StringUtils.isBlank(instanceId)) {
//			throw new IllegalArgumentException("must provide runid");
//		}
//		if (executors.containsKey(instanceId)) {
//			l.debug("found existing executor with following run id :" + instanceId);
//			return executors.get(instanceId);
//		}
//		return null;
//	}
//
//	/**
//	 * create a new executor and keep it safe in the executor bank as of ver. 1.0.0 it supports producing only executors that runs steps
//	 * sequentially TODO : Change this when utility supports parallel execution
//	 *
//	 * @param executorType
//	 * @param instanceId
//	 * @return new executor
//	 */
//	public static Executor newExecutor(final ExecutorType executorType, final String instanceId) {
//		if (StringUtils.isNotBlank(instanceId)) {
//			switch (executorType) {
//				case SEQUENTIAL:
//					Executor executor = new SequentialExecutor();
//					executor.setUniqueRunId(instanceId);
//					executors.put(instanceId, executor);
//					l.debug("created new sequential executor with instance id " + instanceId);
//					return executor;
//			}
//		}
//		throw new IllegalStateException("unable to create new executor");
//	}
//
//	/**
//	 * Executor type : Sequential (for executing deployment steps sequentially) Parallel (for executing deployment steps parallelly)
//	 */
//	public static enum ExecutorType {
//
//		SEQUENTIAL, PARALLEL;
//	}
}
