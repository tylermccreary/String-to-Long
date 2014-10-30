import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test for the StringToLong class
 * @author Tyler McCreary
 */
public class StringToLongTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testValidInput() {
		long i = StringToLong.stringToLong("123456");
		assertTrue (i == 123456);
		i = StringToLong.stringToLong("+123456");
		assertTrue (i == 123456);
		i = StringToLong.stringToLong("654321");
		assertTrue (i == 654321);
		i = StringToLong.stringToLong("+654321");
		assertTrue (i == 654321);
		i = StringToLong.stringToLong("-123456");
		assertTrue (i == -123456);
		i = StringToLong.stringToLong("-654321");
		assertTrue (i == -654321);
		i = StringToLong.stringToLong("3000000000");
		assertTrue (i == 3000000000L);
		i = StringToLong.stringToLong("+3000000000");
		assertTrue (i == 3000000000L);
		i = StringToLong.stringToLong("-3000000000");
		assertTrue (i == -3000000000L);
	}
	
	@Test
	public void testLongBounds() {
		long i = StringToLong.stringToLong("9223372036854775807");
		assertTrue (i == Long.MAX_VALUE);
		i = StringToLong.stringToLong("-9223372036854775808");
		assertTrue (i == Long.MIN_VALUE);
	}
	
	@Test
	public void testLongOverflow() {
		long i = StringToLong.stringToLong("9223372036854775808");
		assertTrue (i == Long.MIN_VALUE);
		i = StringToLong.stringToLong("-9223372036854775809");
		assertTrue (i == Long.MAX_VALUE);
		i = StringToLong.stringToLong("9223372036854775907");
		assertTrue (i == -9223372036854775709L);
	}
	
	@Test
	public void testZero() {
		long i = StringToLong.stringToLong("0");
		assertTrue (i == 0);
		i = StringToLong.stringToLong("-0");
		assertTrue (i == 0);
	}
	
	@Test
	public void testEmpty() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Cannot convert a string without digits.");
		StringToLong.stringToLong("");
	}
	
	@Test
	public void testNegativeEmpty() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Cannot convert a string without digits.");
		StringToLong.stringToLong("-");
	}
	
	@Test(expected = NullPointerException.class)
	public void testNull() {
		StringToLong.stringToLong(null);
	}
	
	@Test
	public void testNonDigits() {
		boolean thrown1 = false;
		boolean thrown2 = false;
		boolean thrown3 = false;
		try {
			StringToLong.stringToLong("1234'987");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertTrue(e.getMessage().equals("String must contain only digits other than an optional '-' at the beginning."));
			thrown1 = true;
		}
		try {
			StringToLong.stringToLong("a");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertTrue(e.getMessage().equals("String must contain only digits other than an optional '-' at the beginning."));
			thrown2 = true;
		}
		try {
			StringToLong.stringToLong("&");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertTrue(e.getMessage().equals("String must contain only digits other than an optional '-' at the beginning."));
			thrown3 = true;
		}
		
		assertTrue(thrown1 && thrown2 && thrown3);
	}
	
	@Test
	public void testSpace() {
		boolean thrown1 = false;
		boolean thrown2 = false;
		try {
			StringToLong.stringToLong(" ");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			thrown1 = true;
		}
		try {
			StringToLong.stringToLong("1 2");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			thrown2 = true;
		}
		
		assertTrue(thrown1 && thrown2);
	}
}
