package workingFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
		List<Element> children = root.getChildren();
		Object obj = null;
		obj = parseNodes(children,obj);

		
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

	private Object parseVal(String in,String val) {
		Object o = new Object();
		if (in.equalsIgnoreCase("int")) {
			return Integer.parseInt(val);
		}
		else if(in.equalsIgnoreCase("double")){
			return Double.parseDouble(val);
		}
		else if(in.equalsIgnoreCase("char")){
			return val.charAt(0);
		}
		return o;
	}

	/*private Object objectHelper(Object o){
		
	}*/
	
	private Object parseNodes(List<Element> children,Object obj){
		//Object obj = null;
		for (Element node : children) {
			String name = node.getName();
			if (name.equalsIgnoreCase("object")) {
				try {
					List<Element> fields = node.getChildren();
					String cl2 = node.getAttributeValue("class");
					Class<?> cls = Class.forName(cl2);
					obj = cls.newInstance();
					for (Element field : fields) {
						String fName = field.getAttributeValue("name");
						Field f = cls.getDeclaredField(fName);
											
						f.setAccessible(true);
						Class<?> type = f.getType();
						
						if (type.isPrimitive()) {
							String val = field.getChild("value").getValue();	
							Object oVal = parseVal(type.getName(),val);
							f.set(obj, oVal);
						}
						else if(type.isArray()){
							
						}
						else{
							String dc = field.getAttributeValue("declaringclass");
							String id = field.getChildText("reference");
							Object o = type.getName();
							
							//o = objectHelper(o);
							f.set(obj,o);
						/*	f.set(o,cl);
							parseNodes(children, o);
							f.set(o,cl)*/;
							
							
							
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
				
				
			}
		}return obj;
	}
}
