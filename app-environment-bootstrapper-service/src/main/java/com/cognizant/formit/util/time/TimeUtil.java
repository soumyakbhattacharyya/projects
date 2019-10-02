/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.formit.util.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author cognizant
 */
public final class TimeUtil {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * get current time being formated in user supplied
	 *
	 * @param format default is "yyyy/MM/dd HH:mm:ss";
	 * @return date representation of current time
	 * @throws ParseException
	 */
	static public Date getNow(final String format) throws ParseException {
		String applicableFormat = DEFAULT_DATE_FORMAT;
		DateFormat dateFormat = new SimpleDateFormat(applicableFormat);
		Date date = new Date(System.currentTimeMillis());
		return dateFormat.parse(dateFormat.format(date));
	}

	/**
	 * get current time being formated in default pattern
	 *
	 * @return
	 * @throws ParseException
	 */
	static public Date getNow() throws ParseException {
		String applicableFormat = DEFAULT_DATE_FORMAT;
		return getNow(applicableFormat);
	}
}
