package workingFiles;

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
					ref.addContent(arr.hashCode() + "");
					Element elem2 = new Element("object");
					elem2.setAttribute("class", t.getName());
					elem2.setAttribute("id", arr.hashCode() + "");
					root.addContent(elem2);
					int len = Array.getLength(arr);
					elem2.setAttribute("length", len + "");
					Element arrVal;
					for (int i = 0; i < Array.getLength(arr); i++) {
						if (t.getName().contains("[L")) {
							arrVal = new Element("reference");
							Object iv = Array.get(arr, i);
							arrVal.addContent(iv.hashCode() + "");
							serialize(iv);
						} else {
							arrVal = new Element("value");
							int indexValue = Array.getInt(arr, i);
							arrVal.addContent(indexValue + "");
						}
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
		XMLOutputter xmlOutput = new XMLOutputter();

		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());
		try {

			String ipAddress = ("0.0.0.0");
			Socket sock = new Socket(ipAddress, serverPort);
			OutputStream out = sock.getOutputStream();
			xmlOutput.output(doc, out);
			sock.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
