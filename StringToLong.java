
/**
 * A class to convert a string to a long
 * @author Tyler McCreary
 */
public class StringToLong {
	public static final int ASCII_ZERO = 48;
	public static final String NULL_STRING = "The string cannot be null";
	public static final String EMPTY_STRING = "Cannot convert a string without digits.";
	public static final String ILLEGAL_CHAR = "String must contain only digits other than an optional '-' at the beginning.";
	
	/**
	 * Class for converting a string to a long
	 * @param s the string to be converted
	 * @return the string as a long
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	static long stringToLong(String s) throws IllegalArgumentException, NullPointerException {
		long result = 0;
		boolean positive = true;
		
		if (s == null) {
			throw new NullPointerException(NULL_STRING);
		}
		if (s.length() == 0) {
			throw new IllegalArgumentException(EMPTY_STRING);
		}
		
		char sign = s.charAt(0);
		if (sign == '-' || sign == '+') {
			s = s.substring(1);
			if (s.length() == 0) {
				throw new IllegalArgumentException(EMPTY_STRING);
			}
			if (sign == '-') {
				positive = false;
			}
		}

		int i = 0;
		while (i < s.length()) {
			int asciiVal = (int) s.charAt(i);
			long digit = asciiVal - ASCII_ZERO;
			if (digit < 0 || digit > 9) {
				throw new IllegalArgumentException(ILLEGAL_CHAR);
			} else {
				if (positive) {
					result += digit * (long) Math.pow(10, s.length() - i - 1);
				} else {
					result -= digit * (long) Math.pow(10, s.length() - i - 1);
				}
			}
			i++;
		}
		
		return result;
	}
}