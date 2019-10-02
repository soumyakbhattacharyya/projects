/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.jpaas2.api;

import com.pholser.util.properties.BoundProperty;

/**
 * consists ALL properties that drive deployment
 *
 * @author cognizant
 */
public interface ConstantProperty {

	@BoundProperty("cloudSetDB")
	String cloudSetDB();
	
	@BoundProperty("profile")
	String profile();

	@BoundProperty("xml")
	String xml();

	@BoundProperty("xsd")
	String xsd();

	@BoundProperty("dbUrl")
	String dbUrl();

	@BoundProperty("dbDriverClass")
	String dbDriverClass();

	@BoundProperty("dbUid")
	String dbUid();

	@BoundProperty("dbPwd")
	String dbPwd();

	@BoundProperty("serverEndpoint")
	String serverEndpoint();

	@BoundProperty("rundeckUid")
	String rundeckUid();

	@BoundProperty("rundeckPwd")
	String rundeckPwd();
	
	@BoundProperty("rundeckAdminToken")
	String rundeckAdminToken();
}
