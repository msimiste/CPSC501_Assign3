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
		String dir = System.getProperty("user.dir");
		String separator = System.getProperty("file.separator");
		String absPath = dir + separator;
		
		File outFile = new File(absPath,"slaveThread.xml");
		try {
			InputStream input = clientSocket.getInputStream();
			
			
			SAXBuilder builder = new SAXBuilder();

			// read the inputstream ie, the get request into a byte array
			byte[] buf = new byte[32768];
			int count = input.read(buf);

			System.out.println("SlaveThread Count" + count);

			// extract the entire request into a string
			if (count > 0) {
				s += new String(buf, 0, count);
			}
			OutputStream output = new FileOutputStream(outFile.getAbsolutePath());
			output.write(buf,0,count);
			
			
			System.out.println("line 41: " + s);
			Document doc = builder.build(outFile);
			Deserializer des = new Deserializer();
			/*output.write(s.getBytes());
			output.flush();*/
			des.deserialize(doc);
			Thread.sleep(500);
			

			output.close();
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
	

	/**
	 * 
	* @param path The path which represents the folder structure on both locally and on the server
	 * @param fileName The name of the file
	 * @return a newly created file
	 */		
	private File makeFileAndDir(String path, String fileName) {
		// concatenate the file path
		String dir = System.getProperty("user.dir");
		String separator = System.getProperty("file.separator");
		String absPath = dir + separator + path;

		File outFile = new File(absPath, fileName);

		outFile.getParentFile().mkdirs();
		try {
			outFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return outFile;
	}
}