/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Map;
import javax.xml.XMLConstants;

// JAXP
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;


// DOM
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * generic utility for xml dom manipulation using jaxp attributes
 *
 * @author Cognizant
 */
public class XmlParser {

	private static final Logger l = Logger.getLogger(XmlParser.class);
	private Validator validator;
	private Schema schema;
	private DocumentBuilder builder;
	private Document doc;
	private DocumentBuilderFactory factory;

	public static XmlParser newInstance() {
		return new XmlParser();
	}

	// parse xml
	public Document parse(String xmlFilePath) throws ParserConfigurationException, IOException, SAXException {

		try {
			// Validate the DOM tree

			validator.validate(new StreamSource(xmlFilePath));
			l.info("xml file available from " + xmlFilePath + " passed validation");

			// Get Document Builder Factory
			factory = DocumentBuilderFactory.newInstance();

			// Turn on validation and namespaces
			// factory.setValidating(true);
			factory.setNamespaceAware(true);

			// Associate validating schema
			factory.setSchema(schema);

			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(xmlFilePath));
			l.info("parsed xml file");

		} catch (ParserConfigurationException e) {
			l.error("The underlying parser does not "
					+ "support the requested features.");
			throw e;
		} catch (FactoryConfigurationError e) {
			l.error("Error occurred obtaining Document "
					+ "Builder Factory.");
			throw e;
		}
		return doc;
	}

	/**
	 * registers an xsd to validate parsing
	 *
	 * @param xsdFilePath
	 * @throws SAXException
	 */
	public void registerValidator(String xsdFilePath) throws SAXException {
		SchemaFactory constraintFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Source constraints = new StreamSource(new File(xsdFilePath));
		if (null != constraints) {
			l.debug("read xsd from following path :" + xsdFilePath);
		}
		schema = constraintFactory.newSchema(constraints);
		l.debug("defined schema");
		validator = schema.newValidator();
		l.debug("spawnned new validator");
		ErrorHandler mySchemaErrorHandler = new XmlParseErrorHandler();
		validator.setErrorHandler(mySchemaErrorHandler);
		l.debug("registered error handler");
	}

	/**
	 * Prints the specified node, recursively.
	 */
	public void printDomTree(Node node) {
		int type = node.getNodeType();
		switch (type) {
			// print the document element
			case Node.DOCUMENT_NODE: {
				l.debug("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				printDomTree(((Document) node).getDocumentElement());
				break;
			}

			// print element and any attributes
			case Node.ELEMENT_NODE: {
				l.debug("<");
				l.debug(node.getNodeName());

				NamedNodeMap attrs = node.getAttributes();
				for (int i = 0; i < attrs.getLength(); i++) {
					printDomTree(attrs.item(i));
				}

				l.debug(">");

				if (node.hasChildNodes()) {
					NodeList children = node.getChildNodes();
					for (int i = 0; i < children.getLength(); i++) {
						printDomTree(children.item(i));
					}
				}

				l.debug("</");
				l.debug(node.getNodeName());
				l.debug('>');

				break;
			}

			// Print attribute nodes
			case Node.ATTRIBUTE_NODE: {
				l.debug(" " + node.getNodeName() + "=\""
						+ ((Attr) node).getValue() + "\"");
				break;
			}

			// handle entity reference nodes
			case Node.ENTITY_REFERENCE_NODE: {
				l.debug("&");
				l.debug(node.getNodeName());
				l.debug(";");
				break;
			}

			// print cdata sections
			case Node.CDATA_SECTION_NODE: {
				l.debug("<![CDATA[");
				l.debug(node.getNodeValue());
				l.debug("]]>");
				break;
			}

			// print text
			case Node.TEXT_NODE: {
				l.debug(node.getNodeValue());
				break;
			}

			case Node.COMMENT_NODE: {
				l.debug("<!--");
				l.debug(node.getNodeValue());
				l.debug("-->");
				break;
			}

			// print processing instruction
			case Node.PROCESSING_INSTRUCTION_NODE: {
				l.debug("<?");
				l.debug(node.getNodeName());
				String data = node.getNodeValue();
				{
					l.debug(" ");
					l.debug(data);
				}
				l.debug("?>");
				break;
			}
		}
	} // printDomTree(Node)

	/**
	 * Replaces named parameters with supplied value
	 *
	 * @param node
	 * @param map
	 * @return
	 */
	public Node decorateDom(Node node, Map<String, String> map) {
		int type = node.getNodeType();
		switch (type) {
			// print the document element
			case Node.DOCUMENT_NODE: {
				decorateDom(((Document) node).getDocumentElement(), map);
				break;
			}

			// print element and any attributes
			case Node.ELEMENT_NODE: {
				NamedNodeMap attrs = node.getAttributes();
				for (int i = 0; i < attrs.getLength(); i++) {
					decorateDom(attrs.item(i), map);
				}



				if (node.hasChildNodes()) {
					NodeList children = node.getChildNodes();
					for (int i = 0; i < children.getLength(); i++) {
						decorateDom(children.item(i), map);
					}
				}


				break;
			}

			// Print attribute nodes
			case Node.ATTRIBUTE_NODE: {

				break;
			}

			// handle entity reference nodes
			case Node.ENTITY_REFERENCE_NODE: {

				updateNodeVal(node.getNodeValue(), map.get(node.getNodeValue()), node, map);
				break;
			}

			// print cdata sections
			case Node.CDATA_SECTION_NODE: {

				updateNodeVal(node.getNodeValue(), map.get(node.getNodeValue()), node, map);
				break;
			}

			// print text
			case Node.TEXT_NODE: {

				updateNodeVal(node.getNodeValue(), map.get(node.getNodeValue()), node, map);
				break;
			}

			case Node.COMMENT_NODE: {

				updateNodeVal(node.getNodeValue(), map.get(node.getNodeValue()), node, map);
				break;
			}

			// print processing instruction
			case Node.PROCESSING_INSTRUCTION_NODE: {

				String data = node.getNodeValue();
				updateNodeVal(data, map.get(data), node, map);
				break;
			}
		}
		return node;
	} // printDomTree(Node)

	// Update node value by taking the corresponding map's entry
	private Node updateNodeVal(String oldValAsKey, String newVal, Node forNode, Map<String, String> map) {
		if (map.containsKey(oldValAsKey)) {
			forNode.setNodeValue(map.get(oldValAsKey));
			return forNode;
		}
		return forNode;
	}

	public Document readXmlFromInputstream(InputStream is) throws SAXException, IOException,
			ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setValidating(false);
		dbf.setIgnoringComments(false);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setNamespaceAware(true);
		// dbf.setCoalescing(true);
		// dbf.setExpandEntityReferences(true);

		DocumentBuilder db = null;
		db = dbf.newDocumentBuilder();
		db.setEntityResolver(new NullResolver());

		// db.setErrorHandler( new MyErrorHandler());

		return db.parse(is);
	}
}

class NullResolver implements EntityResolver {

	public InputSource resolveEntity(String publicId, String systemId) throws SAXException,
			IOException {
		return new InputSource(new StringReader(""));
	}
}
