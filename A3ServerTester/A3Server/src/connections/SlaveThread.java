package connections;

import java.io.*;
import java.net.*;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import workingFiles.Deserializer;

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
			
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(input);						
			Deserializer des = new Deserializer();		
			des.deserialize(doc);
			Thread.sleep(500);			
			input.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}