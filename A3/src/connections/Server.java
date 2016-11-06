
package connections;
import java.net.*;
import java.io.*;

/**
 * Originally written for CPSC441
 * @author Mike Simister
 * October 20, 2015
 *
 */
 public class Server {

	private int port;
	private ServerSocket serverSocket = null;
	private Master mt;

	public Server(int port) {
		this.port = port;
	}

	/**
	 * Initiate the main thread	 * 
	 */
	public void start() {

		try {
			this.serverSocket = new ServerSocket(this.port);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can not open port: " + port, e);
		}

		mt = new Master(serverSocket);
		System.out.println("Inside Server");
		mt.start();

	}

	/**
	 * 
	 */
	public void stop() {
		mt.setStopped(); // stop the loop in master thread
	}
}