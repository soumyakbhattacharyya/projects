/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.main;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * String constants
 *
 * @author Cognizant
 */
public final class AppConstants {

	public static final String _CONTAINER = "CONTAINER";
	public static final String _DRIVER = "DRIVER";
	public static final String _IP_SUFFIX = ":::IP}";
	public static final String ADDRESSING = "addressing";
	public static final String PRIVATE = "private";
	public static final String TAG = "tag";
	public static final String HASH = "#";
	public static final String DRIVING_EXECUTOR_INSTANCE = "DRIVING_EXECUTOR_INSTANCE";
	public static final String DOLLAR_AND_START_BRACKET = "${";
	public static final String END_BRACKET = "}";
	public static final String PROJECT = "project";
	//private final StringBuilder builder = new StringBuilder(20);
	public static final String CHECK_POINT = "CHECKPOINT";
	public static final String SEPARATOR = AppConstants.HASH;
	public static final int STARTING_POS = 2;
	
	public static final String VMWARE_CLOUD_NAME="vSphere";
	public static final String AMAZON_CLOUD_NAME="aws";
	public static final String MACHINE_USER_ID = "vsphere.general.purpose.remoteUser";
	public static final String GENERAL_PURPOSE_SSHPORT = "vsphere.general.purpose.sshPort";
	/**
	 * constants
	 */
	// for launch
	public final static String EXECUTOR_INITIALIZATION = "EXECUTOR_INITIALIZATION";
	public final static String VALIDATE_LAUNCH_PRECONDITION = "VALIDATE_LAUNCH_PRECONDITION";
	public final static String DISCOVER_RESOURCE_REQUIREMENT = "DISCOVER_RESOURCE_REQUIREMENT";
	public final static String SETUP_DEPLOYMENT_CONFIGURATION = "SETUP_DEPLOYMENT_CONFIGURATION";
	public final static String SPAWN_CLOUD_RESOURCE = "SPAWN_CLOUD_RESOURCE";
	public final static String REFRESH_CLOUD_RESOURCE = "REFRESH_CLOUD_RESOURCE";
	public final static String DEFINE_CLOUD_RESOURCE_IN_CMDB = "DEFINE_CLOUD_RESOURCE_IN_CMDB";
	public final static String TAG_SEQUENCE_TO_NODE = "TAG_SEQUENCE_TO_NODE";
	public final static String PREPARE_TRIGGERING_OPTION = "PREPARE_TRIGGERING_OPTION";
	public final static String EXECUTE = "EXECUTE";
	
	// for termination 
	public final static String FINDING_RESOURCES = "FINDING_RESOURCES";
	public final static String TERMINATING_CLOUD_RESOURCES = "TERMINATING_CLOUD_RESOURCES";
	public final static String REMOVING_RESOURCE_DEFINITION = "REMOVING_RESOURCE_DEFINITION";
	
	/**
	 * messages
	 */
	public final static String MSG_1_0_0 = "MSG_1_0_0";
	public final static String MSG_1_0_1 = "MSG_1_0_1";
	public final static String MSG_1_0_2 = "MSG_1_0_2";
	public final static String MSG_3_0_0 = "MSG_3_0_0";
	public final static String MSG_3_0_1 = "MSG_3_0_1";
	public final static String MSG_3_0_2 = "MSG_3_0_2";
	public final static String MSG_3_0_3 = "MSG_3_0_3";
	public final static String MSG_3_0_0_D = "MSG_3_0_0_D";
	public final static String MSG_3_0_5 = "MSG_3_0_5";
	public final static String MSG_3_0_5_D = "MSG_3_0_5_D";
	public final static String MSG_5_0_0 = "MSG_5_0_0";
	public final static String MSG_5_0_0_D = "MSG_5_0_0_D";
	public final static String MSG_5_0_2 = "MSG_5_0_2";
	public final static String MSG_5_0_2_D = "MSG_5_0_2_D";
	public final static String MSG_5_0_4 = "MSG_5_0_4";
	public final static String MSG_5_0_4_D = "MSG_5_0_4_D";
	
	// for termination
	public final static String MSG_2_0_0 = "MSG_2_0_0";
	public final static String MSG_2_0_0_D = "MSG_2_0_0_D";
	public final static String MSG_4_0_0 = "MSG_4_0_0";
	public final static String MSG_4_0_0_D = "MSG_4_0_0_D";
	public final static String MSG_6_0_0 = "MSG_6_0_0";
	public final static String MSG_6_0_0_D = "MSG_6_0_0_D";	
	
	private final static Map<String, String> MSG_STORE = Collections.unmodifiableMap(new HashMap<String, String>() {
		{
			put(MSG_1_0_0, "Asserting deployment plan");
			put(MSG_1_0_1, "Finding number of VM(s) to be provisioned");
			put(MSG_1_0_2, "Number of VM(s) to be provisioned:");
			put(MSG_3_0_0, "Instantiating VM(s) & discovering network detail");
			put(MSG_3_0_1, "Asserting requester's subscription & quota detail");
			put(MSG_3_0_2, "VM(s) launched");
			put(MSG_3_0_3, "Attaching IP(s) to VM(s)");
			put(MSG_3_0_0_D, "… Done. Proceeding to the next step … ");
			put(MSG_3_0_5, "Publishing VM(s) details");
			put(MSG_3_0_5_D, "… Done. Proceeding to the next step … ");
			put(MSG_5_0_0, "Binding installation scripts to VM(s)");
			put(MSG_5_0_0_D, "… Done. Proceeding to the next step … ");
			put(MSG_5_0_2, "Resolving application deployment properties");
			put(MSG_5_0_2_D, "… Done. Proceeding to the next step … ");
			put(MSG_5_0_4, "Starting installation & configuration of stack");
			put(MSG_5_0_4_D, "… Done");
			put(MSG_2_0_0, "Finding VM(s) which corresponds to this deployment");
			put(MSG_2_0_0_D, "… Done. Proceeding to the next step … ");
			put(MSG_4_0_0, "Terminating VM(s)");
			put(MSG_4_0_0_D, "… Done. Proceeding to the next step … ");
			put(MSG_6_0_0, "Removing resource definition");
			put(MSG_6_0_0_D, "… Done");
		}
	});

	private static String get(String index) {
		return MSG_STORE.get(index);
	}

	private static String indexify(String index) {
		String str = index.substring(4).replace("_", ".");
		if (str.endsWith(".D")) {
			str = str.substring(0, 5);
		}
		return str;
	}

	private static String prettify(String index, String val) {
		return index + " " + val;
	}

	public static String getLoggableMsg(String msgKey) {
		return prettify(indexify(msgKey), get(msgKey));
	}
}
