/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.command;

import com.cognizant.jpaas2.api.persistence.factory.DAOFactory;
import com.cognizant.jpaas2.api.persistence.dao.GenericDAO;
import com.cognizant.jpaas2.api.persistence.impl.AppEnvInstanceDAO;
import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import com.cognizant.jpaas2.api.domain.AppEnvMaster;
import com.cognizant.jpaas2.api.domain.AppEnvParams;
import com.cognizant.jpaas2.api.domain.AppEnvTaskOverview;
import com.cognizant.jpaas2.api.persistence.HibernateUtil;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Cognizant
 */
public class AbstractTest {

	private static final HashMap<String, AppEnvInstance> instance = new HashMap<String, AppEnvInstance>(0);
	protected List<Future<String>> futures = new ArrayList<Future<String>>();
//	protected AppEnvInstance bootstrapInstance(AppEnvInstance.ExecutionStatus executionStatus) {
//
//		HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
//		DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
//		AppEnvInstanceDAO appEnvInstanceDAO = factory.getAppEnvInstanceDAO();
//		List<AppEnvInstance> result = appEnvInstanceDAO.getScheduledBuilds(AppEnvInstance.ExecutionStatus.SCHEDULED);
//		return result.get(0);
//	}
}
