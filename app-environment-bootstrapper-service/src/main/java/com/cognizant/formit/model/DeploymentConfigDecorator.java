/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.model;

import com.cognizant.formit.model.DeploymentProfileFactory.ProfileSourceType;
import com.cognizant.formit.util.parser.XmlBinder;
import com.cognizant.formit.util.parser.XmlParser;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * Decorates configuration with the deployment profile
 */
public class DeploymentConfigDecorator {

	private IDeploymentConfig config;
	private IDeploymentProfile deploymentProfile;
	private String deploymentConfigXml;
	private String deploymentConfigXsd;
	private Node node;
	private static final Logger l = Logger.getLogger(DeploymentConfigDecorator.class);

	/*
	 * load a decorator with a profile property of a given source type
	 */
	public static DeploymentConfigDecorator newInstance(final ProfileSourceType profileSourceType, final String profileProp) {
		l.info("setting profile property for the config decorator which is available on following location :" + profileProp);
		return new DeploymentConfigDecorator(profileSourceType, profileProp);
	}

	/*
	 * load a decorator with a profile property of a given source type
	 */
	public static DeploymentConfigDecorator newInstance(final ProfileSourceType profileSourceType, final Properties properties) {
		l.info("setting profile property for the config decorator which is available on following location :" + properties);
		return new DeploymentConfigDecorator(profileSourceType, properties);
	}

	private DeploymentConfigDecorator(final ProfileSourceType profileSourceType, final String profileProp) {
		deploymentProfile = DeploymentProfileFactory.getInstance().getDP(profileSourceType, profileProp);
	}

	private DeploymentConfigDecorator(final ProfileSourceType profileSourceType, final Properties properties) {
		deploymentProfile = DeploymentProfileFactory.getInstance().getDP(profileSourceType, properties);
	}

	/**
	 * Register a bare bone deployment config and decorate it with profile data
	 *
	 * @param deploymentConfigXml
	 * @param deploymentConfigXsd
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws ConfigurationException
	 */
	public void registerPlusDecorateDeploymentConfig(final String deploymentConfigXml, final String deploymentConfigXsd) throws SAXException, ParserConfigurationException, IOException, ConfigurationException {
		this.deploymentConfigXml = deploymentConfigXml;
		this.deploymentConfigXsd = deploymentConfigXsd;

		l.info("deployment config xml is available here :" + deploymentConfigXml);
		l.info("deployment config xsd is available here :" + deploymentConfigXsd);

		// Create a dom by validating the same
		XmlParser parser = XmlParser.newInstance();
		parser.registerValidator(this.deploymentConfigXsd);
		l.info("verge of parsing configuration xml");
		final Document dom = parser.parse(this.deploymentConfigXml);
		parser.printDomTree(dom);

		// Decorate the dom with profile data
		l.info("verge of decorating config xml with these profile info :" + getDeploymentProfile().get());
		node = (Document) parser.decorateDom(dom, getDeploymentProfile().get());
		l.info("config xml decoration completes");
		parser.printDomTree(node);

	}

	/**
	 * Unmarshals decorated config to an object graph
	 *
	 * @param schemaPkg
	 * @param objFactory
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws ClassNotFoundException
	 * @throws JAXBException
	 * @throws SAXException
	 */
	public IDeploymentConfig unmarshalDecoratedConfig(final String schemaPkg, final String objFactory) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ClassNotFoundException, JAXBException, SAXException {
		XmlBinder binder = XmlBinder.newInstance(schemaPkg, objFactory);
		l.info("got binding");
		binder.registerValidator(this.deploymentConfigXsd);
		l.info("registered following deployment config :" + this.deploymentConfigXsd);
		config = binder.parse(this.node);
		l.info("parsed decorated xml");
		return getConfig();
	}

	/**
	 * @return the config
	 */
	public IDeploymentConfig getConfig() {
		return config;
	}

	/**
	 * @return the deploymentProfile
	 */
	public IDeploymentProfile getDeploymentProfile() {
		return deploymentProfile;
	}
}
