/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit;

import com.cognizant.formit.main.Executor;
import com.cognizant.formit.main.SequentialExecutor;
import com.cognizant.formit.main.ExecutorFactory;
import java.util.UUID;

/**
 *
 * @author Cognizant
 */
public class AbstractTest {

//	protected Executor getDeployer() throws InstantiationException, InstantiationException, IllegalAccessException {
//
//		// Deployment starts with grabbing an instance of SequentialExecutor
//		Executor target = ExecutorFactory.newExecutor(ExecutorFactory.ExecutorType.SEQUENTIAL, UUID.randomUUID().toString());
//		// given        
//		target.setBlueprintConfigFilePath("src/main/resources/xml/blueprint.xml");
//		target.setProfileFilePath("src/main/resources/properties/deploymentProfile.properties");
//		target.setXmlFilePath("src/main/resources/xml/infrastructureCntx.xml");
//		target.setXsdFilePath("src/main/resources/xsd/infrastructureCntx.xsd");
//		target.setSchemaPkg("com.cognizant.formit.entity.infrastructureContext");
//		target.setObjFactory("com.cognizant.formit.entity.infrastructureContext.ObjectFactory");
//
//		return target;
//
//	}
}
