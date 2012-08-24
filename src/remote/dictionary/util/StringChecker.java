package remote.dictionary.util;
/*
 * @Author : Hanmoi Choi 525910
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringChecker {

	private static Pattern pattern;
	private Matcher matcher;

	public static boolean isIp(String ip) {
		String patternForIP = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
				"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		pattern = Pattern.compile(patternForIP);

		return pattern.matcher(ip).matches();
	}

	public static boolean isPortNumber(String port) {
		int value = 0;

		String patternForPort = "(^[1-9]\\d*)";
		pattern = Pattern.compile(patternForPort);

		if(pattern.matcher(port).matches()) value = Integer.parseInt(port);
		else return false;

		if(value < 65536) return true;
		else return false;

	}

	public static boolean isNumber(String port) {
		int value = 0;

		String patternForPort = "(^[1-9]\\d*)";
		pattern = Pattern.compile(patternForPort);

		if(pattern.matcher(port).matches()) value = Integer.parseInt(port);
		else return false;

		if(value < 1000) return true;
		else return false;

	}

}
