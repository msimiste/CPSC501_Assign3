package workingFiles;

public class Controller {

	public static void main(String[] args) {
		UserInterface UI = new UserInterface();
		UI.initializeStartMenu();
		UI.greeter();
		ObjectCreator objCreate = new ObjectCreator(UI.getChoice());	
		objCreate.createObject(UI.getChoice());
		UI.objectValuesMenu(objCreate, objCreate.getChoice());		
	}
}
