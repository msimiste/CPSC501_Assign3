package workingFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Deserializer {

	public Deserializer() {

	}

	public Object deserialize(Document docIn) {

		// Document doc = new Document();
		String dir = System.getProperty("user.dir");
		String separator = System.getProperty("file.separator");
		String absPath = dir + separator;

		File outFile = new File(absPath, "slaveThread.xml");

		try {
			OutputStream output = new FileOutputStream(
					outFile.getAbsolutePath());
			XMLOutputter xmlOut = new XMLOutputter();
			xmlOut.setFormat(Format.getPrettyFormat());
			xmlOut.output(docIn, output);

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Element root = docIn.getRootElement();

		Object obj = null;
		obj = parseNodes(root, obj);

		Visualizer v = new Visualizer();
		v.inspect(obj, true);

		File xmlFile = new File(System.getProperty("user.dir")
				+ "\\deserialized.xml");
		XMLOutputter xmlOutput = new XMLOutputter();

		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());

		System.out.print("Deserialized: \n");
		try {
			xmlOutput.output(docIn, new FileWriter(xmlFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	private Object parseVal(String in, String val) {
		Object o = new Object();
		if ((in.equalsIgnoreCase("int")) || (in.equalsIgnoreCase("[I"))) {
			return Integer.parseInt(val);
		} else if (in.equalsIgnoreCase("double")) {
			return Double.parseDouble(val);
		} else if (in.equalsIgnoreCase("char")) {
			return val.charAt(0);
		}
		return o;
	}

	/*
	 * private Object objectHelper(Object o){
	 * 
	 * }
	 */

	private Object parseNodes(Element root, Object obj) {
		// Object obj = null;
		List<Element> children = root.getChildren();
		Element first = children.get(0);
		Class<?> cls;
		try {
			cls = Class.forName(first.getAttributeValue("class"));
			obj = cls.newInstance();
			List<Element> fields = first.getChildren();
			for (Element field : fields) {
				String fName = field.getAttributeValue("name");
				Field f = cls.getDeclaredField(fName);
				Class<?> type = f.getType();
				f.setAccessible(true);
				if (type.isPrimitive()) {
					String dClass = field.getAttributeValue("declaringclass");
					String val = field.getChild("value").getValue();
					Object oVal = parseVal(type.getName(), val);
					f.set(obj, oVal);
				} else if (type.isArray()) {
					System.out.println("Type is array!!!!!!!!!!!!!!!!!");
					String arrRef = field.getChild("reference").getValue();
					Element testElem = new Element("object");
					String typeName = type.getName();
					int len = Array.getLength(f.get(obj));
					int index = children.indexOf(root.getChild("object")
							.getAttribute("class"));
					Element arrayElement = getElemByClassId(children, typeName,
							arrRef);

					List<Element> arrayValues = arrayElement.getChildren();
					if (len == arrayValues.size()) {

						for (int i = 0; i < arrayValues.size(); i++) {
							String value = arrayValues.get(i).getValue();
							Object oVal = parseVal(type.getName(), value);
							Array.set(f.get(obj), i, oVal);
						}
					}
				} else {
					System.out.println("Type is a reference!!!!!!!!!!!!");
					String refRef = field.getChild("reference").getValue();
					String typeName = type.getName();
					Object refOb = f.get(obj);
					Element refVal = getElemByClassId(children,typeName,refRef);
					List<Element> refValues = refVal.getChildren();
					
					for (int i = 0; i < refValues.size(); i++) {
						String name = refValues.get(i)
								.getAttributeValue("name");
						String value = refValues.get(i).getChildText("value");
						String dc = refValues.get(i).getAttributeValue("declaringclass");
						Class<?> c = Class.forName(dc);
						Object o = c.newInstance();
						Field fld = c.getDeclaredField(name);
						String nm = fld.getType().getName();
						fld.setAccessible(true);
						Object oVal = parseVal(nm, value);
						fld.set(refOb, oVal);
					}
					
					f.set(obj, refOb);
					/*if (!(o.toString().contains("["))) {
						f.set(obj, o);
					}*/
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj;
	}

	private Element getElemByClassId(List<Element> list, String id1, String id2) {

		for (int i = 0; i < list.size(); i++) {
			Element tempEl = list.get(i);
			boolean first = tempEl.getAttribute("class").getValue().equals(id1);
			boolean second = tempEl.getAttribute("id").getValue().equals(id2);
			if (first && second)
				return tempEl;

		}

		return null;
	}

	/*
	 * private Object parseNodes(List<Element> children,Object obj){ //Object
	 * obj = null; for (Element node : children) { String name = node.getName();
	 * if (name.equalsIgnoreCase("object")) { //remove? //String clsName =
	 * node.getAttributeValue("class"); try { List<Element> fields =
	 * node.getChildren(); String cl2 = node.getAttributeValue("class");
	 * //String arryAppend = "java.lang.String;";
	 * 
	 * Class<?> cls = Class.forName(cl2);
	 * 
	 * for (Element field : fields) { String fName =
	 * field.getAttributeValue("name"); if((field.getAttributeValue("name")) !=
	 * null){ fName = field.getAttributeValue("name"); } else{ fName = "[I"; }
	 * 
	 * Field f = cls.getDeclaredField(fName);
	 * 
	 * f.setAccessible(true); Class<?> type = f.getType();
	 * 
	 * if (type.isPrimitive()) { obj = cls.newInstance(); String val =
	 * field.getChild("value").getValue(); Object oVal =
	 * parseVal(type.getName(),val); f.set(obj, oVal); } else
	 * if(type.isArray()){ System.out.println("Type is array!!!!!!!!!!!!!!!!!");
	 * 
	 * } else{ obj = cls.newInstance(); String dc =
	 * field.getAttributeValue("declaringclass"); String id =
	 * field.getChildText("reference"); Object o = type.getName();
	 * 
	 * //o = objectHelper(o); f.set(obj,o); f.set(o,cl); parseNodes(children,
	 * o); f.set(o,cl);
	 * 
	 * 
	 * 
	 * } }
	 * 
	 * } catch (ClassNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (InstantiationException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch
	 * (IllegalAccessException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (NoSuchFieldException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch
	 * (SecurityException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * 
	 * } }return obj; }
	 */
}
