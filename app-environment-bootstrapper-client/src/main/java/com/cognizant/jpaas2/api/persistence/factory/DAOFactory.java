/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.persistence.factory;

import com.cognizant.jpaas2.api.persistence.impl.AppEnvInstanceDAO;
import org.hibernate.Session;

/**
 *
 * @author cognizant
 */
public abstract class DAOFactory {

	public static final Class HIBERNATE = HibernateDAOFactory.class;

	/**
	 * Factory method for instantiation of concrete factories.
	 */
	public static DAOFactory instance(Class factory) {
		try {
			return (DAOFactory) factory.newInstance();
		} catch (Exception ex) {
			throw new RuntimeException("Couldn't create DAOFactory: " + factory);
		}
	}

	// Add your DAO interfaces here
	public abstract AppEnvInstanceDAO getAppEnvInstanceDAO();
	
	// Get a DAO with a fresh session
	public abstract AppEnvInstanceDAO getAppEnvInstanceDAO(Session session);
}
