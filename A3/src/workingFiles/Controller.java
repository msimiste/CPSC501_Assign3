package workingFiles;

import org.jdom2.Document;

public class Controller {

	public static void main(String[] args) {
		UserInterface UI = new UserInterface();
		UI.initializeStartMenu();
		UI.greeter();
		ObjectCreator objCreate = new ObjectCreator(UI.getChoice());	
		objCreate.createObject(UI.getChoice());
		UI.objectValuesMenu(objCreate, objCreate.getChoice());	
		Serializer serial = new Serializer();
		Document doc = serial.serialize(objCreate.getObject());
		serial.fileOutput(doc,2225);
		System.exit(0);		
	}
}
