/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.persistence.impl;

import com.cognizant.jpaas2.api.persistence.dao.GenericHibernateDAO;
import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import com.cognizant.jpaas2.api.domain.AppEnvInstance.ExecutionStatus;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 239913
 */
public class AppEnvInstanceHibernateDAO extends GenericHibernateDAO<AppEnvInstance, Long> implements AppEnvInstanceDAO {

	public AppEnvInstanceHibernateDAO() {
	}

	public List<AppEnvInstance> findByExample(AppEnvInstance exampleInstance) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<AppEnvInstance> getScheduledBuilds(String executionStatus) {
		return findByCriteria(Restrictions.eq("status", executionStatus));
	}

	public AppEnvInstance findInstanceForUniqueRunId(String instanceId) {
		return findUnique(Restrictions.eq("instanceId", instanceId));
	}
}
