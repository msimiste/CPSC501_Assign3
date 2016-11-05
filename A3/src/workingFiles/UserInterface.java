package workingFiles;

import java.util.Scanner;

public class UserInterface {

	private int choice;
	private Menu startMenu;
	private Menu menu = new Menu();

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
		this.startMenu.addToMenu("exit");
		this.startMenu.addToMenu("Object Primitives Only");
		this.startMenu.addToMenu("Object Refers to Other Objects");
		this.startMenu.addToMenu("Object Array of Primitives");
		this.startMenu.addToMenu("Object Array of Objects");
		this.startMenu.addToMenu("Object Collection of Objects");
	}

	public void initializeMenu(Menu m, String[] items) {
		for (String s : items) {
			m.addToMenu(s);
		}
	}

	public void objectValuesMenu(ObjectCreator o, int id) {
		switch (id) {
		case 1:
			System.out.println("Primitives Menu");
			setPrimitiveValues(o);
			break;
		case 2:
			setPrimitiveArrayMenuItems();
		}
	}

	public void displayMenu() {
		menu.displayMenu();
		captureChoice(menu.getSize());
	}

	private void setPrimitiveValues(ObjectCreator o) {
		Scanner in = new Scanner(System.in);
		primitiveHelperI(o, in);
		primitiveHelperD(o, in);
		primitiveHelperS(o, in);
		in.close();
	}

	private void primitiveHelperI(ObjectCreator o, Scanner in) {

		System.out.print("\tEnter null or an integer value: ");
		String a = in.next();

		try {
			if (!(a.equals("null"))) {
				((Primitives) o.getObject()).setIntVal(Integer.parseInt(a));
			}
		} catch (NumberFormatException n) {
			primitiveHelperI(o, in);
		}
	}

	private void primitiveHelperD(ObjectCreator o, Scanner in) {
		System.out.print("\tEnter null or a double value: ");
		String a = in.next();
		try {
			if (!(a.equals("null"))) {
				((Primitives) o.getObject())
						.setDoubleVal(Double.parseDouble(a));
			}
		} catch (NumberFormatException n) {
			primitiveHelperD(o, in);
		}
	}

	private void primitiveHelperS(ObjectCreator o, Scanner in) {
		System.out.print("\tEnter null or a string value: ");
		String a = in.next();
		if (!(a.equals("null"))) {

			((Primitives) o.getObject()).setStringVal(a);
		} else {
			((Primitives) o.getObject()).setStringVal(null);
		}
	}

	private void setPrimitiveArrayMenuItems() {
		//TODO fill in this method next
	}

	/**
	 * Captures the user's first choice
	 */
	private void captureChoice(int max) {
		Scanner in = new Scanner(System.in);
		System.out.print("Your Choice a number from 0 - " + max + " :");
		try {
			choice = Integer.parseInt(in.next());
			if ((choice > max) || (choice < 0)) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException n) {

			captureChoice(max);
		}
		in.close();
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
