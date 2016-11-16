package workingFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Deserializer {

	Map<Integer, Object> objects = new HashMap<Integer, Object>();
	public Deserializer() {

	}

	public Object deserialize(Document docIn) {
	
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
		obj = parseNodesTwo(root, obj);

		Visualizer v = new Visualizer();
		v.inspect(obj, true);
		
		for(Object o : objects.values()){
			v.inspect(o, true);
		}

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
		return o;	}

	

	private Object parseNodesTwo(Element root, Object obj) {		
		List<Element> children = root.getChildren("object");
		try {
			xmlToHashMap(children);
			fillReferenceArrays(children);
			fillRegularArrays(children);
			obj = setObject(children);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
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
	
	private Object setObject(List<Element> children) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException{
		Object obj = null;		
		int id = Integer.parseInt(children.get(0).getAttributeValue("id"));
		obj = objects.get(id);
		List<Element> fields = children.get(0).getChildren();
		Class<?> cls = obj.getClass();		
		for(Element field : fields){
			String ref = field.getChildren().get(0).getName();
			String fName = field.getAttributeValue("name");			
			if(ref.contains("reference")){
				int value = Integer.parseInt(field.getChild("reference").getValue());
				Field f = cls.getDeclaredField(fName);
				f.setAccessible(true);
				f.set(obj, objects.get(value));
			}
		}		
		return obj ;
	}

	private void fillRegularArrays( List<Element> children) throws ClassNotFoundException {

		for (Element child : children) {
			String idStr = child.getAttributeValue("id");
			int id = Integer.parseInt(idStr);
			Class<?> cls = Class.forName(child.getAttributeValue("class"));
			if ((!(cls.getName().contains("[L"))) && (cls.getName().contains("["))) {
				int length = Integer.parseInt((child.getAttributeValue("length")));				
				List<Element> fields = child.getChildren();
				Object arr = Array.newInstance(cls.getComponentType(), length);
				for (int i = 0; i < length; i++) {
					Object value = parseVal(cls.getName(), fields.get(i).getText());
					Array.set(arr, i, value);
				}
				objects.put(id, arr);
			}
		}
	}

	private void xmlToHashMap(List<Element> children) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		for (Element child : children) {
			String idStr = child.getAttributeValue("id");
			int id = Integer.parseInt(idStr);
			Class<?> cls = Class.forName(child.getAttributeValue("class"));			
			Object ob = null;
			if (!(cls.getName().contains("["))) {
				ob = cls.newInstance();
				List<Element> fields = child.getChildren();
				for (Element field : fields) {
					String fName = field.getAttributeValue("name");
					Field f = cls.getDeclaredField(fName);
					f.setAccessible(true);
					if (!f.getType().isArray()) {
						String name = field.getChildren().get(0).getName();
						if (name.equals("value")) {
							Object value = parseVal(f.getType().getName(), field.getChildText("value"));
							f.set(ob, value);
						}
					}
					if(f.get(ob) instanceof  Collection<?> ){
						Object value = parseVal(f.getType().getName(), field.getChildText("collect"));
						Object o = f.get(ob);
						((Collection<ObjectType>)o).add((ObjectType) value);
					}
				}				
				objects.put(id, ob);
			}
		}
	}

	private void fillReferenceArrays( List<Element> children)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException,
			SecurityException {
		
		for (Element child : children) {
			String idStr = child.getAttributeValue("id");
			int id = Integer.parseInt(idStr);
			Class<?> cls = Class.forName(child.getAttributeValue("class"));
			
			if ((cls.getName().contains("[L"))) {
				int length = Integer.parseInt((child.getAttributeValue("length")));				
				List<Element> fields = child.getChildren();
				Object arr = Array.newInstance(cls.getComponentType(),length);
				
				for (int i = 0; i < length; i++) {
					int ref = Integer.parseInt(fields.get(i).getValue());
					Array.set(arr, i, objects.get(ref));
				}					
				objects.put(id, arr);
			}
		}
	}	
}
