/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api.persistence;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.cognizant.jpaas2.api.SingletonDriver;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author cognizant
 */
public class HibernateUtil {

	private static final SessionFactory sessionFactory = getSessionFactory();

	public static final SessionFactory getSessionFactory() {
		try {
			// Create the SessionFactory from standard (hibernate.cfg.xml) 
			// config file.
			
			String cloudSetDBURL = SingletonDriver.getCONFIG_FILE_PATH() + "xml/"+"hibernate.cfg.xml";
			File hibernateConfFile = new File(cloudSetDBURL); 
			return (null == sessionFactory) ? new AnnotationConfiguration().configure(hibernateConfFile).buildSessionFactory() : sessionFactory;
		} catch (Throwable ex) {
			// Log the exception. 
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}

	/**
	 * new session on every invocation
	 *
	 * @return new session
	 */
	public static final Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}
}
