/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author cognizant
 */
public final class LogUtil {

	/**
	 * This function will convert e.stackTrace to String and call log(String)
	 *
	 * @param msg
	 * @param e
	 */
	public static void log(Exception e) {
		log(stackTraceToString(e));
	}

	/**
	 * This function will convert e.stackTrace to String and call log(String)
	 *
	 * @param msg
	 * @param e
	 */
	public static void log(String msg, Exception e) {
		log(msg + "\n" + stackTraceToString(e));
	}

	/**
	 * Default log to System.out
	 *
	 * @param msg
	 */
	public static void log(String msg) {
		System.out.println("preflight:>> "+msg);
	}

	/**
	 * Convert Exception.stackTrace to String
	 *
	 * @param e
	 * @return
	 */
	public static String stackTraceToString(Exception e) {
		StringWriter buf = new StringWriter();
		PrintWriter writer = new PrintWriter(buf);
		e.printStackTrace(writer);
		writer.flush();
		buf.flush();
		return buf.toString();
	}
}
