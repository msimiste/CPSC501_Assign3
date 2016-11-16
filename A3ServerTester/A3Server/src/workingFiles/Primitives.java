package workingFiles;

public class Primitives extends ObjectType {

	private int ID;
	private int intVal;
	private double doubleVal;
	private char charVal;

	public Primitives() {
		this.ID = this.hashCode();
	}

	public void setIntVal(int val) {
		this.intVal = val;
	}

	public void setDoubleVal(double val) {
		this.doubleVal = val;
	}

	public void setCharval(char val) {
		this.charVal = val;
	}

	public int getIntValue() {
		return this.intVal;
	}

	public double getDoubleValue() {
		return this.doubleVal;
	}

	public char getCharValue() {
		return this.charVal;
	}
}
