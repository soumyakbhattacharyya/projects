/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.cloudset.framework.util;

import java.util.Date;

/**
 * simple date utilities
 *
 * @author cognizant
 */
public class DateUtil {

	// returns System.currentTimeMillis() in String format
	public static String timestampToStringifiedDate() {
		return (new Date(System.currentTimeMillis())).toString();
	}

	// returns System.currentTimeMillis() in String format
	public static String timestampToStringifiedDate(long timestamp) {
		return (new Date(timestamp)).toString();
	}
}
