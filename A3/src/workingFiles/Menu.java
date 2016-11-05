package workingFiles;

import java.util.ArrayList;

public class Menu {

	private ArrayList<String> menu;
	private String title;

	public Menu() {
		this.menu = new ArrayList<String>();
	}

	public void addToMenu(String item) {
		menu.add(item);
	}

	public void addToMenu(String[] items) {
		for (String s : items) {
			menu.add(s);
		}
	}

	public void displayMenu() {
		System.out.println(this.title);
		for (String s : menu) {
			System.out.println("\t" + s + ": " + "(" + (menu.indexOf(s)) + ")");
		}		
	}

	public int getSize() {
		if (menu != null) {
			return menu.size();
		} else
			return 0;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String t) {
		this.title = t;
	}
}
