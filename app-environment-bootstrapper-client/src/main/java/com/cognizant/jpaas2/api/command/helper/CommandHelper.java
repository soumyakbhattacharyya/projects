/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.command.helper;

import com.cognizant.formit.util.cloud.IExecutionContext;
import com.cognizant.formit.util.db.IConnectionPoolInitializer;
import com.cognizant.formit.util.rundeck.IRundeckConnectionInitializer;
import com.cognizant.formit.util.time.TimeUtil;
import com.cognizant.jpaas2.api.SingletonDriver;
import com.cognizant.jpaas2.api.domain.AppEnvInstance;
import com.cognizant.jpaas2.api.domain.AppEnvTaskOverview;
import com.cognizant.jpaas2.api.persistence.HibernateUtil;
import com.cognizant.jpaas2.api.persistence.factory.DAOFactory;
import com.cognizant.jpaas2.api.persistence.impl.AppEnvInstanceDAO;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;

/**
 *
 * @author ognizant
 */
public class CommandHelper {

	static public void updateInstanceStatus(AppEnvInstance appEnvInstance, AppEnvInstance.ExecutionStatus executionStatus, boolean isToProvideEndtimeStamp) {

		appEnvInstance.setStatus(executionStatus.toString());
		try {
			if (isToProvideEndtimeStamp) {
				appEnvInstance.setEndTimestamp(TimeUtil.getNow());
			}
		} catch (ParseException ex) {
			throw new RuntimeException(ex);
		}
		// newExecutor session
		Session session = HibernateUtil.getCurrentSession();
		// begin transaction
		session.beginTransaction();
		// grab dao
		DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
		AppEnvInstanceDAO appEnvInstanceDAO = factory.getAppEnvInstanceDAO(session);
		appEnvInstanceDAO.makePersistent(appEnvInstance);
		session.getTransaction().commit();

	}

	static public void updateInstanceVMDetail(AppEnvInstance appEnvInstance, String vmDetail) {

		appEnvInstance.setVmDetail(vmDetail);
		// newExecutor session
		Session session = HibernateUtil.getCurrentSession();
		// begin transaction
		session.beginTransaction();
		// grab dao
		DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
		AppEnvInstanceDAO appEnvInstanceDAO = factory.getAppEnvInstanceDAO(session);
		appEnvInstanceDAO.makePersistent(appEnvInstance);
		session.getTransaction().commit();

	}

	static public void updateInstanceTaskOverview(String uniqueRunId, String shortDesc, String longDesc, Long category) {

		// newExecutor session
		Session session = HibernateUtil.getCurrentSession();
		// begin transaction
		session.beginTransaction();
		// grab dao
		DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
		AppEnvInstanceDAO appEnvInstanceDAO = factory.getAppEnvInstanceDAO(session);
		AppEnvInstance appEnvInstance = appEnvInstanceDAO.findInstanceForUniqueRunId(uniqueRunId);
		AppEnvTaskOverview appEnvTaskOverview = new AppEnvTaskOverview(appEnvInstance, shortDesc, longDesc, category);
		appEnvInstance.getAppEnvTaskOverviews().add(appEnvTaskOverview);
		session.saveOrUpdate(appEnvTaskOverview);
		appEnvInstanceDAO.makePersistent(appEnvInstance);
		//session.flush();
		session.getTransaction().commit();
		//session.close();

	}

	/**
	 * find launch request from DB which are parked in SCHEDULED status
	 *
	 * @return list of scheduled launches
	 */
	static public AppEnvInstance findInstance(AppEnvInstance appEnvInstance) {
		// begin transaction
		Session session = HibernateUtil.getCurrentSession();
		session.beginTransaction();
		// grab dao
		DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);
		AppEnvInstanceDAO appEnvInstanceDAO = factory.getAppEnvInstanceDAO(session);
		// get result
		AppEnvInstance result = appEnvInstanceDAO.findInstanceForUniqueRunId(appEnvInstance.getInstanceId());
		session.getTransaction().commit();
		//HibernateUtil.getCurrentSession().getTransaction().commit();
		return result;
	}

	static public IConnectionPoolInitializer getConnectionPoolInitializer() {
		return new IConnectionPoolInitializer() {
			SingletonDriver driver = SingletonDriver.getInstance();
			private String dbUrl = driver.getClientProperties().dbUrl();
			private String dbDriverClass = driver.getClientProperties().dbDriverClass();
			private String dbUid = driver.getClientProperties().dbUid();
			private String dbPwd = driver.getClientProperties().dbPwd();
			private int dbInitialPoolSize = 10;
			private int dbMinPoolSize = 5;
			private int dbMaxPoolSize = 20;
			private int dbMaxIdleTime = 3600;

			public String dbUrl() {
				return dbUrl;
			}

			public String dbDriverClass() {
				return dbDriverClass;
			}

			public String dbUid() {
				return dbUid;
			}

			public String dbPwd() {
				return dbPwd;
			}

			public int dbMaxPoolSize() {
				return dbMaxPoolSize;
			}

			public int dbMinPoolSize() {
				return dbMinPoolSize;
			}

			public int dbInitialPoolSize() {
				return dbInitialPoolSize;
			}

			public int dbMaxIdleTime() {
				return dbMaxIdleTime;
			}
		};
	}

	static public IRundeckConnectionInitializer getRundeckConnectionInitializer() {
		return new IRundeckConnectionInitializer() {
			SingletonDriver driver = SingletonDriver.getInstance();

			@Override
			public String serverEndpoint() {
				return driver.getClientProperties().serverEndpoint();
			}

			@Override
			public String uid() {
				return driver.getClientProperties().rundeckUid();
			}

			@Override
			public String pwd() {
				return driver.getClientProperties().rundeckPwd();
			}
			
			@Override
			public String token() {
				return driver.getClientProperties().rundeckAdminToken();
			}
		};
	}

	static public IExecutionContext getExecutionContext(final AppEnvInstance appEnvInstance) {
		return new IExecutionContext() {
			@Override
			public String accountNumber() {
				return appEnvInstance.getAccountNumber();
			}

			@Override
			public String projectId() {
				return appEnvInstance.getProjectId();
			}

			@Override
			public String uId() {
				return appEnvInstance.getUserId();
			}

			@Override
			public String reason() {
				return appEnvInstance.getReason();
			}

			@Override
			public String description() {
				return appEnvInstance.getDescription();
			}

			@Override
			public String rollId() {
				return appEnvInstance.getRoleId();
			}

			@Override
			public String serviceId() {
				return appEnvInstance.getServiceId();
			}
		};
	}
}
