package workingFiles;

public class ObjectCreator {

	private int choice;
	private ObjectType object;

	public ObjectCreator(int c) {
		choice = c;
		if (c == 0) {
			System.exit(0);
		}
	}

	public int getChoice() {
		return choice;
	}

	public void createObject(int id) {
		switch (id) {
		case 1:
			createPrimitivesObject();
			break;
		case 2:
		}
	}

	public ObjectType getObject() {
		return this.object;
	}

	private void createPrimitivesObject() {
		Primitives p = new Primitives();
		this.object = p;
	}
}
