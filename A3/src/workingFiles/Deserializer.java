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
		
		File outFile = new File(absPath,"slaveThread.xml");
		
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
		List<Element> children = root.getChildren();		
		Object obj = null;

		for (Element node : children) {

			String name = node.getName();
			if (name.equalsIgnoreCase("object")) {
				try {
					List<Element> fields = node.getChildren();
					System.out.println("Ho Wai Testing");
					String cl2 = node.getAttributeValue("class");
					int ind = cl2.indexOf('.') + 1;
					String cl = cl2.substring(ind);
					Class<?> cls = Class.forName(cl2);
					obj = cls.newInstance();
					for(Element field : fields){
						cls = obj.getClass();
						String fieldName = field.getName();
						String fName = field.getAttributeValue("name");
						Field f = cls.getField(fName);
						field.getChild("value").getValue();
						f.setAccessible(true);
						Class<?> type = f.getType();
						if(type.isPrimitive()){
							
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
				
				
				Visualizer v = new Visualizer();
				v.inspect(obj, true);
			}
		}

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
}
