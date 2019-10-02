/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.chain;

import com.cognizant.formit.exception.FormitException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.config.ConfigParser;
import org.apache.commons.chain.impl.CatalogFactoryBase;

/**
 *
 * @author cognizant
 */
public class SequenceDriver implements SequenceDrivable {

	private final ConfigParser parser;
	private final Catalog catalog;
	private ChainExecutionContext executionContext;
	private SequenceDriver.SequenceType sequenceType;

	/**
	 * instantiate a new sequence loader
	 *
	 * @param sequenceEnum
	 * @throws Exception
	 */
	private SequenceDriver(SequenceDriver.SequenceType sequenceEnum, ChainExecutionContext executionContext) throws Exception {
		this.parser = new ConfigParser();
		this.sequenceType = sequenceEnum;
		switch (sequenceEnum) {
			case LAUNCH:
				parser.parse((new File(LAUNCH_SEQUENCE_CHAIN)).toURI().toURL());
			case TERMINATE:
				parser.parse((new File(TERMINATE_SEQUENCE_CHAIN)).toURI().toURL());
			case GET_ENVIRONMENT:
				parser.parse((new File(GET_ENVIRONMENT_CHAIN)).toURI().toURL());	
		}
		// parser.parse(this.getClass().getResource(LAUNCH_SEQUENCE));
		this.catalog = CatalogFactoryBase.getInstance().getCatalog();
		this.executionContext = executionContext;;
	}

	/**
	 * factory for getting a new sequence loader instance
	 *
	 * @param sequenceEnum
	 * @return
	 */
	public static SequenceDriver loadResource(SequenceDriver.SequenceType sequenceEnum, ChainExecutionContext executionContext) {
		try {
			return new SequenceDriver(sequenceEnum, executionContext);
		} catch (Exception ex) {
			throw new FormitException.FailedToLoadSequenceException("failed to get required sequence", ex);
		}
	}

	/**
	 * triggers a new execution sequence
	 */
	public void triggerSequence() {
		try {
			Command command = catalog.getCommand(sequenceType.toString());
			if (null != command) {
				if (null != executionContext) {
					command.execute(executionContext);
				} else {
					throw new FormitException.NoExecutionContextFound("provide execution context");
				}
			} else {
				throw new FormitException.NoAppropriateSequenceFound("requested sequence unavailable");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

	}

	/**
	 * types of sequences supported
	 */
	public static enum SequenceType {

		LAUNCH, TERMINATE, GET_ENVIRONMENT;

		@Override
		public String toString() {
			switch (this) {
				case LAUNCH:
					return "launch-deployment";
				case TERMINATE:
					return "terminate-deployment";
				case GET_ENVIRONMENT:
					return "get-deployment-detail";
			}
			return null;
		}
	}
}
