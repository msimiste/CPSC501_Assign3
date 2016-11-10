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
		char testCharValue = 't';

		Primitives p = (Primitives) objCreate.getObject();

		assertEquals(testIntValue, p.getIntValue());
		assertEquals(testCharValue, p.getCharValue());
	}

	@Test
	public void testCreateObjectNullValue() {

UserInterface UI = new UserInterface();
		
		ObjectCreator objCreate = new ObjectCreator(1);
		objCreate.createObject(1);
		UI.objectValuesMenu(objCreate, objCreate.getChoice());

		int testIntValue = 10;
		
		char testCharValue = 't';

		Primitives p = (Primitives) objCreate.getObject();

		assertEquals(testIntValue, p.getIntValue());
		//assertNull(testCharValue, p.getCharValue());
	}
}
