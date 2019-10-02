/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.main;

import com.cognizant.formit.entity.infrastructureContext.Context;
import com.cognizant.formit.entity.infrastructureContext.IProject;
import com.cognizant.formit.exception.FormitException;
import com.cognizant.formit.model.AuditReport;
import com.cognizant.formit.model.DeploymentConfigDecorator;
import com.cognizant.formit.model.DeploymentProfileFactory;
import com.cognizant.formit.model.IDeploymentConfig;
import com.cognizant.formit.model.steps.MajorStep;
import com.cognizant.formit.model.RNode;
import com.cognizant.formit.model.RNodeFactory;
import com.cognizant.formit.util.cloud.CloudResourceManager;
import com.cognizant.formit.util.cloud.IExecutionContext;
import com.cognizant.formit.util.db.DBHelper;
import com.cognizant.formit.util.db.IConnectionPoolInitializer;
import com.cognizant.formit.util.file.FileHelper;
import com.cognizant.formit.util.rundeck.IRundeckConnectionInitializer;
import com.cognizant.formit.util.rundeck.RundeckHelper;
import com.cognizant.jpaas2.commons.expection.PlatformException;
import com.cognizant.jpaas2.context.RequestContext;
import com.cognizant.jpaas2.resource.VirtualMachineType;
import java.beans.PropertyVetoException;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.RuntimeConfigurable;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.rundeck.api.RundeckClient;
import org.rundeck.api.domain.RundeckProject;
import org.xml.sax.SAXException;

/**
 * driver for the entire deployment entry point for using the utility
 *
 * @author cognizant
 */
public class SequentialExecutor extends AbstractExecutor {

//	private static final Logger l = Logger.getLogger(SequentialExecutor.class);
//
//	SequentialExecutor() {
//	}
//
//	@Override
//	public void getInitialized(String blueprintFilePath, String deploymentProfileFilePath, String infraContextFilePath, String infraContextXSDFilePath, IConnectionPoolInitializer connectionPoolInitializer, IRundeckConnectionInitializer rundeckConnectionInitializer) {
//
//		if (!FileHelper.isValid(FileHelper.getFile(blueprintFilePath))) {
//			throw new FormitException.FailedToInitializeException("blueprint invalid");
//		}
//		if (!FileHelper.isValid(FileHelper.getFile(deploymentProfileFilePath))) {
//			throw new FormitException.FailedToInitializeException("profile invalid");
//		}
//		if (!FileHelper.isValid(FileHelper.getFile(infraContextFilePath))) {
//			throw new FormitException.FailedToInitializeException("context xml invalid");
//		}
//		if (!FileHelper.isValid(FileHelper.getFile(infraContextXSDFilePath))) {
//			throw new FormitException.FailedToInitializeException("context xsd invalid");
//		}
//		if (null == connectionPoolInitializer) {
//			throw new FormitException.FailedToInitializeException("database connectivity information have not been provided");
//		}
//		if (null == rundeckConnectionInitializer) {
//			throw new FormitException.FailedToInitializeException("database connectivity information have not been provided");
//		}
//
//
//		this.setBlueprintConfigFilePath(blueprintFilePath);
//		this.setProfileFilePath(deploymentProfileFilePath);
//		this.setXmlFilePath(infraContextFilePath);
//		this.setXsdFilePath(infraContextXSDFilePath);
//		this.setConnectionPoolInitializer(connectionPoolInitializer);
//		this.setRundeckConnectionInitializer(rundeckConnectionInitializer);
//		this.setSchemaPkg(super.schemaPkg);
//		this.setObjFactory(super.objFactory);
//
//	}
}
