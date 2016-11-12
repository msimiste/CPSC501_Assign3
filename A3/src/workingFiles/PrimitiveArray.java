package workingFiles;

import java.util.Random;

class PrimitiveArray extends ObjectType {

	private int ID;
	private int[] intArray = new int[10];

	public PrimitiveArray() {
		this.ID = this.hashCode();
	}

	public int getID() {
		return ID;
	}

	public void initRandomValues(int value) {

		for (int i : intArray) {
			i = 10;
		}
	}

	public int[] getArray() {
		return this.intArray;
	}

	public void setArrayRandom() {
		Random rand = new Random();
		for (int i = 0; i < intArray.length; i++) {
			intArray[i] = rand.nextInt(intArray.length);
		}
	}

	public void setArraySame(int val) {
		for(int i : intArray){
			i = val;
		}
	}
	
	public void setArrayIndex(int index, int value){
		intArray[index] = value;
	}

}
