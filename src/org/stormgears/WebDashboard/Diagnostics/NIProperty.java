package org.stormgears.WebDashboard.Diagnostics;

/**
 * Created by andrew on 1/22/17.
 */
public class NIProperty {
	byte type;
	String value;

	public NIProperty(byte type, String value) {
		this.type = type;
		this.value = value;
	}
}
