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

	public ObjectType createObject(int id) {
		switch (id) {
		case 1:
			return createPrimitivesObject();
		case 2:
			return createReferencesObject();
			
		}
		return null;
	}

	public ObjectType getObject() {
		return this.object;
	}

	private Primitives createPrimitivesObject() {
		Primitives p = new Primitives();
		this.object = p;
		return p;
	}
	private References createReferencesObject(){
		References r = new References();
		this.object = r;
		return r;
	}
}
