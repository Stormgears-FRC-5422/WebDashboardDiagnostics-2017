package org.stormgears.WebDashboard.Diagnostics;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 1/22/17.
 */
public class NIDevice {
	String path; // 1000000
	String manufacturer; // 1004000
	String type; // 1006000
	String id; // 101D000
	String idType; // 101E000
	String description; // 101F000

	Map<Integer, NIProperty> properties; // tag:type

	public NIDevice(String path, String manufacturer, String type, String id, String idType, String description, Map<Integer, NIProperty> properties) {
		this.path = path;
		this.manufacturer = manufacturer;
		this.type = type;
		this.id = id;
		this.idType = idType;
		this.description = description;
		this.properties = properties;
	}

	public String getPath() {
		return path;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public String getIdType() {
		return idType;
	}

	public String getDescription() {
		return description;
	}

	static NIDevice parsePropertyBag(Node propertyBag) {
		NodeList children = propertyBag.getChildNodes();
		HashMap<Integer, NIProperty> properties = new HashMap<>();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child.getNodeName().equalsIgnoreCase("Property")) { // ignore text nodes
				int tag = Integer.parseInt(child.getAttributes().getNamedItem("tag").getNodeValue(), 16);
				byte type = Byte.parseByte(child.getAttributes().getNamedItem("type").getNodeValue());
				String value = child.getTextContent();
				properties.put(tag, new NIProperty(type, value));
			}
		}

		return new NIDevice(
				properties.get(0x1000000).value,
				properties.get(0x1004000).value,
				properties.get(0x1006000).value,
				properties.get(0x101D000).value,
				properties.get(0x101E000).value,
				properties.get(0x101F000).value,
				properties
		);
	}
}
