package unitTests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import workingFiles.UserInterface;

public class UserInterfaceUnitTests {

	// Set up the stream to capture the program's output to the console.
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);
	}

	@Test()
	public void testUserChoice() {

		UserInterface UI = new UserInterface();
		UI.greeter();
		int testInt = 5;
		assertEquals(testInt, UI.getChoice());
	}
	
	
}
