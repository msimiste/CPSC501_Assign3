package workingFiles;

public class References extends ObjectType {

	private int ID;
	private Primitives prims = new Primitives();

	public References() {
		this.ID = this.hashCode();
	}

	public Primitives getPrimReference() {
		return this.prims;
	}
}
