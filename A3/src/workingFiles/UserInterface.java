package workingFiles;

import java.util.Scanner;

public class UserInterface {

	private int choice;
	private Menu startMenu;

	public UserInterface() {

	}

	/**
	 * initial UI menu
	 */
	public void greeter() {
		this.startMenu.displayMenu();
		captureChoice(startMenu.getSize());
	}

	/**
	 * 
	 */
	public void initializeStartMenu() {
		this.startMenu = new Menu();
		this.startMenu.setTitle("Object Creator Menu");
		this.startMenu.addToMenu("Object Primitives Only: (1)");
		this.startMenu.addToMenu("Object Refers to Other Objects: (2)");
		this.startMenu.addToMenu("Object Array of Primitives: (3)");
		this.startMenu.addToMenu("Object Array of Objects: (4)");
		this.startMenu.addToMenu("Object Collection of Objects: (5)");
	}

	/**
	 * Captures the user's first choice
	 */
	private void captureChoice(int max) {
		Scanner in = new Scanner(System.in);
		System.out.print("Your Choice a number from 1 - " + max + " :");
		try {
			choice = Integer.parseInt(in.next());
			if ((choice > 5) || (choice < 1)) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException n) {

			captureChoice(max);
		}
	}

	/**
	 * getter method for the value choice
	 * 
	 * @return int, the user's choice
	 */
	public int getChoice() {
		return this.choice;
	}
}
