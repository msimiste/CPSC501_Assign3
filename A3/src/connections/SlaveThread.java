package connections;

import java.io.*;
import java.net.*;

/**
 * Originally written for CPSC441 October 20, 2015
 * 
 * @author Mike Simister
 */
public class SlaveThread extends Thread {

	private Socket clientSocket = null;

	public SlaveThread(Socket s) {
		clientSocket = s;
	}

	public void run() {

		String s = "";
		try {
			InputStream input = clientSocket.getInputStream();

			// read the inputstream ie, the get request into a byte array
			byte[] buf = new byte[32768];
			int count = input.read(buf);

			System.out.println("SlaveThread Count" + count);

			// extract the entire request into a string
			if (count > 0) {
				s += new String(buf, 0, count);
			}
			OutputStream output = clientSocket.getOutputStream();
			System.out.println("line 41: " + s);

			output.close();
			input.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}