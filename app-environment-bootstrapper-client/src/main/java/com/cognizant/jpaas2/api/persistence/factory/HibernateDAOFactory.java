/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.persistence.factory;

import com.cognizant.jpaas2.api.persistence.dao.GenericHibernateDAO;
import com.cognizant.jpaas2.api.persistence.impl.AppEnvInstanceDAO;
import com.cognizant.jpaas2.api.persistence.impl.AppEnvInstanceHibernateDAO;
import com.cognizant.jpaas2.api.persistence.HibernateUtil;
import org.hibernate.Session;

/**
 * produces custom entity focussed factories
 *
 * @author Cognizant
 */
public class HibernateDAOFactory extends DAOFactory {

	@Override
	public AppEnvInstanceDAO getAppEnvInstanceDAO() {
		return (AppEnvInstanceDAO) instantiateDAO(com.cognizant.jpaas2.api.persistence.impl.AppEnvInstanceHibernateDAO.class);
	}

	@Override
	public AppEnvInstanceDAO getAppEnvInstanceDAO(Session session) {
		return (AppEnvInstanceDAO) instantiateDAO(com.cognizant.jpaas2.api.persistence.impl.AppEnvInstanceHibernateDAO.class, session);
	}

	private GenericHibernateDAO instantiateDAO(Class daoClass) {
		try {
			GenericHibernateDAO dao = (GenericHibernateDAO) daoClass.newInstance();
			dao.setSession(getCurrentSession());
			return dao;
		} catch (Exception ex) {
			throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
		}
	}

	private GenericHibernateDAO instantiateDAO(Class daoClass, Session session) {
		try {
			GenericHibernateDAO dao = (GenericHibernateDAO) daoClass.newInstance();
			dao.setSession(session);
			return dao;
		} catch (Exception ex) {
			throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
		}
	}

	// You could override this if you don't want HibernateUtil for lookup
	protected Session getCurrentSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

	// You could override this if you don't want HibernateUtil for lookup
	protected Session getNewSession() {
		return HibernateUtil.getSessionFactory().openSession();
	}
}
