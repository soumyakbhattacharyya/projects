/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.chain;

/**
 * repository of sequences
 *
 * @author cognizant
 */
public interface SequenceDrivable {

	final String LAUNCH_SEQUENCE_CHAIN = System.getProperty("CONFIG_FILE_PATH") +"xml/chain/launch-deployment-chain.xml";
	final String TERMINATE_SEQUENCE_CHAIN = System.getProperty("CONFIG_FILE_PATH") + "xml/chain/terminate-deployment-chain.xml";
	final String GET_ENVIRONMENT_CHAIN = System.getProperty("CONFIG_FILE_PATH")+ "xml/chain/get-deployment-detail-chain.xml";
}
