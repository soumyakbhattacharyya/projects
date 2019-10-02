/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.parser;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author Cognizant
 */
class XmlParseErrorHandler implements ErrorHandler {

	public XmlParseErrorHandler() {
	}

	public void warning(SAXParseException exception) throws SAXException {
		exception.printStackTrace();
	}

	public void error(SAXParseException exception) throws SAXException {

		exception.printStackTrace();
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		exception.printStackTrace();
	}
}
