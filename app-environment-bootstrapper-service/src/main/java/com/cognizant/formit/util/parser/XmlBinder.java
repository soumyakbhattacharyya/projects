/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.parser;

import com.cognizant.formit.entity.infrastructureContext.BaseObjectFactory;
import com.cognizant.formit.model.IDeploymentConfig;
import com.cognizant.formit.util.file.FileHelper;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Implementation for parsing xml file
 *
 * @author Cognizant
 *
 */
public class XmlBinder {

	static Logger logger = Logger.getLogger(XmlBinder.class);
	// Initialize variable
	private BaseObjectFactory baseObjectFactory;
	private Unmarshaller unmarshaller;
	private String schemaPkg;
	private String objFactory;

	public static XmlBinder newInstance(final String schemaPkg, final String objFactory) throws InstantiationException, IllegalAccessException, ClassNotFoundException, JAXBException {
		return new XmlBinder(schemaPkg, objFactory);
	}

	private XmlBinder(final String schemaPkg, final String objFactory) throws InstantiationException, IllegalAccessException, ClassNotFoundException, JAXBException {
		// TODO : Instantiate using Apache package
		this.schemaPkg = schemaPkg;
		this.objFactory = objFactory;
		this.baseObjectFactory = getBaseObjectFactory(objFactory);
		this.unmarshaller = getUnmarshaller(schemaPkg);
	}

	private BaseObjectFactory getBaseObjectFactory(String objFactory) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return (BaseObjectFactory) Class.forName(objFactory).newInstance();
	}

	private Unmarshaller getUnmarshaller(String schemaPkg) throws JAXBException {
		return JAXBContext.newInstance(schemaPkg).createUnmarshaller();
	}

	// unmarshals an xml to jaxb generated entity
	public IDeploymentConfig parse(final String xmlFilePath) throws JAXBException, SAXException {
		FileInputStream fileInputStream = convert(xmlFilePath);
		return unmarshal(fileInputStream);
	}

	// unmarshals a node to jaxb generated entity
	public IDeploymentConfig parse(final Node node) throws JAXBException, SAXException {
		Node nodeToBeUnmarshalled = convert(node);
		return unmarshal(nodeToBeUnmarshalled);
	}

	/**
	 * Read an xml
	 *
	 * @throws SAXException
	 * @throws JAXBException
	 */
	private FileInputStream convert(final String filePath) throws JAXBException, SAXException {

		//String xmlFilePath = xmlFilePath;
		logger.info("Read file from " + filePath);
		logger.info("Register validator");
		return FileHelper.readXMLAsFileInputStream(filePath);

	}

	private Node convert(final Node node) throws JAXBException, SAXException {

		return node;

	}

	/*
	 * Unmarshals xml file to object
	 */
	private IDeploymentConfig unmarshal(FileInputStream fileInputStream) throws JAXBException {

		IDeploymentConfig entity = (IDeploymentConfig) unmarshaller.unmarshal(fileInputStream);
		return entity;
	}

	/*
	 * Unmarshals node to object
	 */
	private IDeploymentConfig unmarshal(Node node) throws JAXBException {

		IDeploymentConfig entity = (IDeploymentConfig) unmarshaller.unmarshal(node);
		return entity;
	}
	/*
	 * Register an XSD to validate an XML and report subsequently
	 */

	public void registerValidator(final String xsdFilePath) throws JAXBException, SAXException {

		// Validation Registration Block
		SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
		logger.info("Registering xsd to validate XML from this path - " + xsdFilePath);
		Schema schema = sf.newSchema(new File(xsdFilePath));
		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new ValidationEventHandler() {
			// allow unmarshalling to continue even if there are errors
			public boolean handleEvent(ValidationEvent ve) {
				// ignore warnings
				if (ve.getSeverity() != ValidationEvent.WARNING) {
					ValidationEventLocator vel = ve.getLocator();
					StringBuffer buffer = (new StringBuffer()).append("Parsing Exception["
							+ vel.getLineNumber() + "-"
							+ vel.getColumnNumber() + "-" + ve.getMessage()
							+ "]");
					logger.info(buffer);
				}
				return true;
			}
		});

	}
}