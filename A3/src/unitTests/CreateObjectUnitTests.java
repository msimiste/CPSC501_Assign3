package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import workingFiles.ObjectCreator;
import workingFiles.Primitives;
import workingFiles.UserInterface;

public class CreateObjectUnitTests {

	@Test
	public void testCreateObjectAllValues() {
		UserInterface UI = new UserInterface();
		
		ObjectCreator objCreate = new ObjectCreator(1);
		objCreate.createObject(1);
		UI.objectValuesMenu(objCreate, objCreate.getChoice());

		int testIntValue = 10;
		String testStringValue = "This";

		Primitives p = (Primitives) objCreate.getObject();

		assertEquals(testIntValue, p.getIntValue());
		assertEquals(testStringValue, p.getStringValue());
	}

	@Test
	public void testCreateObjectNullValue() {

UserInterface UI = new UserInterface();
		
		ObjectCreator objCreate = new ObjectCreator(1);
		objCreate.createObject(1);
		UI.objectValuesMenu(objCreate, objCreate.getChoice());

		int testIntValue = 10;
		
		String testStringValue = "This";

		Primitives p = (Primitives) objCreate.getObject();

		assertEquals(testIntValue, p.getIntValue());
		assertNull(testStringValue, p.getStringValue());
	}
}
