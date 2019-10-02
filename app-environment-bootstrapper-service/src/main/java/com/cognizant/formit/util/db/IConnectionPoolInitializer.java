/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.db;

/**
 * credectial to initialize a connection (or a pool of it)
 *
 * @author cognizant
 */
public interface IConnectionPoolInitializer {

	String dbUrl();

	String dbDriverClass();

	String dbUid();

	String dbPwd();

	int dbMaxPoolSize();

	int dbMinPoolSize();

	int dbInitialPoolSize();

	int dbMaxIdleTime();
}
