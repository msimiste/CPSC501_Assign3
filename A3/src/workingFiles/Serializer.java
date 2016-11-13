package workingFiles;

import connections.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {

	private Document doc;

	public Serializer() {
		doc = new Document();
		Element elem = new Element("serialized");
		doc.setRootElement(elem);
	}

	public Document serialize(Object obj) {

		Element elem1 = new Element("object");
		Element root = doc.getRootElement();
		root.addContent(elem1);
		// elem.addContent(elem1);
		Class<?> cls = obj.getClass();
		String name = cls.getName();
		elem1.setAttribute("class", name);
		elem1.setAttribute("id", obj.hashCode() + "");
		Field[] fields = cls.getDeclaredFields();

		for (Field f : fields) {

			Class<?> t = f.getType();
			f.setAccessible(true);
			Element e = new Element("field");

			if (t.isPrimitive()) {
				String n = f.getName();
				String dc = f.getDeclaringClass().getName();

				elem1.addContent(e);
				e.setAttribute("name", n);
				e.setAttribute("declaringclass", dc);
				Element val = new Element("value");

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
			} else if (t.isArray()) {
				String n = f.getName();
				String dc = f.getDeclaringClass().getName();

				elem1.addContent(e);
				e.setAttribute("name", n);
				e.setAttribute("declaringclass", dc);
				Element ref = new Element("reference");
				e.addContent(ref);
				
				Object arr;
				try {
					arr = f.get(obj);
					ref.addContent(arr.hashCode()+"");
					Element elem2 = new Element("object");
					elem2.setAttribute("class", t.getName());
					elem2.setAttribute("id",arr.hashCode()+"");
					root.addContent(elem2);
					int len = Array.getLength(arr);
					elem2.setAttribute("length",len+"");
					
					for (int i = 0; i < Array.getLength(arr); i++) {
						Element arrVal = new Element("value");
						int indexValue = Array.getInt(arr, i);
						arrVal.addContent(indexValue + "");
						elem2.addContent(arrVal);
					}
					
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				String n = f.getName();
				String dc = f.getDeclaringClass().getName();

				elem1.addContent(e);
				e.setAttribute("name", n);
				e.setAttribute("declaringclass", dc);
				
				Element ref = new Element("reference");
				f.setAccessible(true);
				String clName = f.getDeclaringClass().getName();
				try {
					Object c = f.get(obj);
					int hCode = c.hashCode();
					ref.addContent(hCode + "");
					serialize(c);

				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.addContent(ref);

			}
		}
		return doc;
	}

	public void fileOutput(Document doc, int serverPort) {
		/*
		 * File xmlFile = new File(System.getProperty("user.dir") +
		 * "\\initialTest.xml");
		 */

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
			String ipAddress = ("0.0.0.0");
			Socket sock = new Socket(ipAddress, serverPort);
			OutputStream out = sock.getOutputStream();
			xmlOutput.output(doc, out);
			System.out.println("Made it through "
					+ sock.getInetAddress().getHostAddress());

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
