package workingFiles;

public class Primitives extends ObjectType {

	private int ID;
	private int intVal;
	private double doubleVal;
	private String stringVal;

	public Primitives(int in) {
		ID = in;
	}

	public int getID() {
		return ID;
	}

	public void setIntVal(int val) {
		this.intVal = val;
	}

	public void setDoubleVal(double val) {
		this.doubleVal = val;
	}

	public void setStringVal(String val) {
		this.stringVal = val;
	}

	public int getIntValue() {
		return this.intVal;
	}

	public double getDoubleValue() {
		return this.doubleVal;
	}

	public String getStringValue() {
		return this.stringVal;
	}
}
