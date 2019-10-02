/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.persistence.impl;

import com.cognizant.jpaas2.api.persistence.dao.GenericDAO;
import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import java.util.List;

/**
 *
 * @author cognizant
 */
public interface AppEnvInstanceDAO extends GenericDAO<AppEnvInstance, Long> {

	/**
	 * get those builds which are in scheduled status
	 *
	 * @param executionStatus
	 * @return
	 */
	List<AppEnvInstance> getScheduledBuilds(String executionStatus);

	/**
	 * finds an instance to terminate the same
	 *
	 * @param instanceId
	 * @return
	 */
	AppEnvInstance findInstanceForUniqueRunId(String instanceId);
}
