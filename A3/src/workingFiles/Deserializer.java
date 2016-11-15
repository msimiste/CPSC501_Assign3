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
			OutputStream output = new FileOutputStream(outFile.getAbsolutePath());
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

		File xmlFile = new File(System.getProperty("user.dir") + "\\deserialized.xml");
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
					int len = Array.getLength(f.get(obj));
					Object[] arr = new Object[len];
					String ref = field.getAttributeValue("reference");
					for (int i = 1; i < len; i++) {
						if (type.getComponentType().isPrimitive()) {
							List<Element> arrayValues = getElemByClassId(children, ref).getChildren();
							String value = arrayValues.get(i).getValue();
							Object oVal = parseVal(type.getName(), value);
							Array.set(f.get(obj), i, oVal);
						}
						else if(type.getComponentType().getName().contains("[L")){
							List<Element> arrayValues = getElemByClassId(children,ref).getChildren();
							String value = arrayValues.get(i).getValue();
							Class<?> tmp = Class.forName(field.getAttributeValue("class"));
							Object testOb = tmp.newInstance();
							Array.set(testOb, i, getElemByClassId(children,ref).getAttributeValue("class"));
							f.set(obj, testOb);
						}
					}

					//Object refOb = searchAndSet(children, ref);

				} else {
					System.out.println("Type is a reference!!!!!!!!!!!!");
					String refRef = field.getChild("reference").getValue();
					String typeName = type.getName();
					Object refOb = f.get(obj);
					Element refVal = getElemByClassId(children, refRef);
					List<Element> refValues = refVal.getChildren();

					for (int i = 0; i < refValues.size(); i++) {
						String name = refValues.get(i).getAttributeValue("name");
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
					/*
					 * if (!(o.toString().contains("["))) { f.set(obj, o); }
					 */
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch blockfor (int i = 0; i <
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

	// private Element getElemByClassId(List<Element> list, String id1, String
	// id2) {
	private Element getElemByClassId(List<Element> list, String ID) {
		for (int i = 0; i < list.size(); i++) {
			Element tempEl = list.get(i);
			boolean first = tempEl.getAttribute("id").getValue().equals(ID);
			if (first) {
				return tempEl;
			}

		}

		return null;
	}

	private Object searchAndSet(List<Element> children, String ref)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		Object testOb = null;
		for (Element e : children) {
			if (e.getAttributeValue("id").equals(ref)) {
				Class<?> tmp = Class.forName(e.getAttributeValue("class"));
				testOb = tmp.newInstance();
			}
		}

		return testOb;
	}

	private Object parseReference(Element field, Class<?> type, Field f, Object obj, List<Element> children)
			throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, InstantiationException,
			NoSuchFieldException, SecurityException {

		Object testOb = null;
		for (Element e : children) {
			String arrRef = e.getText();
			Element arrayElement = getElemByClassId(children, arrRef);

			if (arrayElement != null) {
				List<Element> arrayValues = arrayElement.getChildren();

				for (int i = 0; i < arrayValues.size(); i++) {
					if (e.getAttributeValue("id").equals(arrayValues.get(i).getValue())) {
						Class<?> tmp = Class.forName(e.getAttributeValue("class"));
						testOb = tmp.newInstance();
					}
				}
			}
		}

		return testOb;
	}

	/*
	 * private Object parseReference(Element field, Class<?> type, Field f,
	 * Object obj, List<Element> children) throws IllegalArgumentException,
	 * IllegalAccessException, ClassNotFoundException, InstantiationException,
	 * NoSuchFieldException, SecurityException { String refRef =
	 * field.getChild("reference").getValue(); String typeName = type.getName();
	 * Object refOb = f.get(obj); Element refVal = getElemByClassId(children,
	 * refRef); if(refVal.getChild("reference")!=null){
	 * parseReference(refVal,type,f,obj,children); } List<Element> refValues =
	 * refVal.getChildren();
	 * 
	 * for (int i = 0; i < refValues.size(); i++) { String name =
	 * refValues.get(i).getAttributeValue("name"); String value =
	 * refValues.get(i).getChildText("value"); String dc =
	 * refValues.get(i).getAttributeValue("declaringclass"); Class<?> c =
	 * Class.forName(dc); Object o = c.newInstance(); Field fld =
	 * c.getDeclaredField(name); String nm = fld.getType().getName();
	 * fld.setAccessible(true); Object oVal = parseVal(nm, value);
	 * 
	 * if(fld.getType().isArray()){ Array.set(fld.get(obj), i, oVal); } else{
	 * fld.set(o, oVal); } if(f.getType().isArray()){ Array.set(f.get(refOb), i,
	 * oVal); } else{ f.set(refOb, oVal); } }
	 * 
	 * 
	 * return refOb; }
	 */

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
