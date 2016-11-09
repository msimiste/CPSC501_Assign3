package workingFiles;

import connections.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;



public class Serializer {

	public Serializer() {

	}

	public Document serialize(Object obj) {

		Document doc = new Document();
		Element elem = new Element("serialize");
		Element elem1 = new Element("object");
		doc.setRootElement(elem);
		elem.addContent(elem1);
		Class<?> cls = obj.getClass();
		String name = cls.getName();
		elem1.setAttribute("class", name);
		elem1.setAttribute("id", obj.hashCode() + "");
		Field[] fields = cls.getDeclaredFields();

		for (Field f : fields) {
			String n = f.getName();
			Class<?> t = f.getType();
			String dc = f.getDeclaringClass().getName();
			Element e = new Element("field");
			elem1.addContent(e);
			e.setAttribute("name", n);
			e.setAttribute("declaringclass", dc);
			if (t.isPrimitive()) {
				Element val = new Element("value");
				f.setAccessible(true);
				try {
					Object c = f.get(obj);
					val.addContent(c.toString());
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.addContent(val);
			}
		}
		return doc;
	}

	public void fileOutput(Document doc, int serverPort) {
		/*File xmlFile = new File(System.getProperty("user.dir")
				+ "\\initialTest.xml");*/

		XMLOutputter xmlOutput = new XMLOutputter();

		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());
		try {
			/*
			 * xmlOutput.output(doc, new
			 * FileWriter(System.getProperty("user.dir") +
			 * "\\initialTest.xml"));
			 */
			Server server = new Server(serverPort);

			server.start();
			System.out.println("Server started. Type \"quit\" to close.");

			// Scanner keyboard = new Scanner(System.in);
			String ipAddress = ("172.19.1.41");
			Socket sock = new Socket(ipAddress, serverPort);
			OutputStream out = sock.getOutputStream();
			xmlOutput.output(doc, out);
			System.out.println("Made it through "+sock.getInetAddress().getHostAddress());

			server.stop();
			sock.close();
			// System.exit(0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("File Saved!");
	
	}
}
