package workingFiles;

public class ReferenceArray extends ObjectType {
	
	private int ID;
	private ObjectType[] objArr = new ObjectType[2];
	public ReferenceArray(){
		this.ID = this.hashCode();
	}
	
	public ObjectType[] getArray(){
		return this.objArr;
	}
	
	public void setOneValue(String typeValue){		
		
		for(int i =0; i<objArr.length; i++){
			objArr[i] = parseTypeValue(typeValue);
		}
	}
	
	/*public void setVariousValues(){
		
	}*/
	
	public void setSpecificValue(int index, ObjectType o){
		objArr[index] = o;
	}
	
	private ObjectType parseTypeValue(String value){
		ObjectType ot = null;
		if(value.equalsIgnoreCase("Primitives")){
			ot = new Primitives();
		}
		else if(value.equalsIgnoreCase("PrimitiveArray")){
			ot = new PrimitiveArray();
		}
		else if(value.equalsIgnoreCase("References")){
			ot =  new References();
		}
		else if(value.equalsIgnoreCase("ReferenceArray")){
			ot = new ReferenceArray();
		}
		
		else if (value.equalsIgnoreCase("Collections")){
			ot = new Collections();
		}
		
		return ot;
	}

}
