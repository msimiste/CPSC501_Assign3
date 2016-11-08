package workingFiles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

		Element root = docIn.getRootElement();
		List<Element> children = root.getChildren();
		Object obj = null;

		for (Element node : children) {

			String name = node.getName();
			if (name.equalsIgnoreCase("object")) {
				try {

					String cl2 = node.getAttributeValue("class");
					int ind = cl2.indexOf('.') + 1;
					String cl = cl2.substring(ind);
					Class<?> cls = Class.forName(cl2);
					obj = cls.newInstance();
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
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
