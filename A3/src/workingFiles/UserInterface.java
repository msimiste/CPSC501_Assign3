package workingFiles;
import java.util.Scanner;

public class UserInterface {

	private int choice;

	public UserInterface() {

	}

	public void greeter() {
		System.out.println("Object Creator Menu");
		System.out.println("\tObject Primitives Only: (1)");
		System.out.println("\tObject Refers to Other Objects: (2)");
		System.out.println("\tObject Array of Primitives: (3)");
		System.out.println("\tObject Array of Objets: (4)");
		System.out.println("\tObject Collection of Objects: (5)");
		captureChoice();
	}

	private void captureChoice() {
		Scanner in = new Scanner(System.in);
		System.out.print("Your Choice a number from 1 - 5: ");
		try {
			choice = Integer.parseInt(in.next());
			if((choice>5)||(choice<1)){
				throw new NumberFormatException();
			}
		} catch (NumberFormatException n) {
			
			captureChoice();
		}
	}

	public int getChoice() {
		return this.choice;
	}
}
