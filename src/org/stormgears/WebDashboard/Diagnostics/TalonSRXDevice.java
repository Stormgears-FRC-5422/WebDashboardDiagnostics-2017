package org.stormgears.WebDashboard.Diagnostics;

import java.util.Map;

/**
 * Created by andrew on 1/22/17.
 */
public class TalonSRXDevice extends NIDevice {
	byte devID; // 1A110000

	public TalonSRXDevice(String path, String manufacturer, String type, String id, String idType, String description, Map<Integer, NIProperty> properties, byte devID) {
		super(path, manufacturer, type, id, idType, description, properties);
		this.devID = devID;
	}

	public byte getDevID() {
		return devID;
	}
}
