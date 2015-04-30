package org.nercel.ccnu.edu.fileextractor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

	public static Date convertString2Date(String dateString, String datePattern) {
		Date date = null;
		try {
			date = DateUtils.parseDate(dateString, datePattern);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public static long convertDate2Long(Date date) {
		return date.getTime();
	}

	public static long convertString2Long(String dateString, String datePattern) {
		return convertDate2Long(convertString2Date(dateString, datePattern));
	}

	public static Date convertLong2Date(long date) {
		return new Date(date);
	}

	public static String convertLong2String(long date, String datePattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
		String dateString = formatter.format(convertLong2Date(date));
		return dateString;
	}
}