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
		case 3:
			return createPrimitiveArrayObject();
		case 4:
			return createReferenceArrayObject();
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

	private References createReferencesObject() {
		References r = new References();
		this.object = r;
		return r;
	}

	private PrimitiveArray createPrimitiveArrayObject() {
		PrimitiveArray pa = new PrimitiveArray();
		this.object = pa;
		return pa;
	}

	private ReferenceArray createReferenceArrayObject() {
		ReferenceArray ra = new ReferenceArray();
		this.object = ra;
		return ra;
	}
}
