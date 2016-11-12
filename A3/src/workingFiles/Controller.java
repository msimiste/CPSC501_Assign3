package workingFiles;

import org.jdom2.Document;

public class Controller {

	public static void main(String[] args) throws InterruptedException {
		UserInterface UI = new UserInterface();
		UI.initializeStartMenu();
		UI.greeter();
		ObjectCreator objCreate = new ObjectCreator(UI.getChoice());	
		ObjectType o = objCreate.createObject(UI.getChoice());
		UI.objectValuesMenu(objCreate, objCreate.getChoice());	
		Serializer serial = new Serializer();
		Document doc = serial.serialize(o);
		serial.fileOutput(doc,2225);
		Thread.sleep(500);
		//System.exit(0);		
	}
}
