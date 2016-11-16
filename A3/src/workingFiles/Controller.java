package workingFiles;

import java.util.Scanner;

import org.jdom2.Document;

public class Controller {

	public static void main(String[] args) throws InterruptedException {
			
		controller();
		System.exit(0);		
	}
	
	public static void  controller() throws InterruptedException{
		
			UserInterface UI = new UserInterface();			
			UI.greeter();
			ObjectCreator objCreate = new ObjectCreator(UI.getChoice());	
			ObjectType o = objCreate.createObject(UI.getChoice());
			UI.objectValuesMenu(objCreate, objCreate.getChoice());	
			Serializer serial = new Serializer();
			Document doc = serial.serialize(o);
			serial.fileOutput(doc,2225);
			Thread.sleep(500);
			System.out.println("************************************************");
			exitMsg();
			
	}
	
	public static void exitMsg() throws InterruptedException{
		Scanner in = new Scanner(System.in);
		String cont = "";
		
		
		System.out.print("Serialize another object? y/n: ");
		cont = in.next();
		if(!(cont.equalsIgnoreCase("n"))){
			controller();
		}
		
	}
}
