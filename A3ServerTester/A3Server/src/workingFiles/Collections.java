package workingFiles;

import java.util.ArrayList;

public class Collections extends ObjectType {

	private int ID;
	private ArrayList<ObjectType> collect = new ArrayList<ObjectType>(1);
	public Collections (){
		this.ID = this.hashCode();
	}
	

	
	public void addToList(ObjectType o){
		collect.add(o);		
	}
	
	public Collections getCollection(){
		return this;
	}

	public ArrayList<ObjectType> getList(){
		return this.collect;
	}
}
