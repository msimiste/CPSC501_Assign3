package connections;

public class StartUp {

	public static void main(String[] args) {
	
		Server s = new Server(2225);
		s.start();

	}

}
