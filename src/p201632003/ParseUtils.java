package p201632003;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseUtils {
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static int parseInt(String s, int defaultValue) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static double parseDouble(String s, double defaultValue) {
		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static Date parseDate(String s, Date defaultValue) {
		try {
			return dateFormat.parse(s);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static Date parseTime(String s, Date defaultValue) {
		try {
			return timeFormat.parse(s);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static Date parseDatetime(String s, Date defaultValue) {
		try {
			return datetimeFormat.parse(s);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static void main(String[] args) {
		System.out.println(parseInt("3", 0));
		System.out.println(parseInt(null, 3));
		System.out.println(parseDouble("3.0", 0));
		System.out.println(parseDouble(null, 3.0));
		System.out.println(parseDate("2018-05-04", null));
		System.out.println(parseDate(null, new Date()));
		System.out.println(parseTime("12:30:21", null));
		System.out.println(parseDate(null, new Date()));
		System.out.println(parseDatetime("2018-05-04 12:30:21", null));
		System.out.println(parseDatetime(null, new Date()));
	}

}
