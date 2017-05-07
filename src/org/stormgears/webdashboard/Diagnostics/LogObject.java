package org.stormgears.webdashboard.Diagnostics;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by andrew on 1/21/17.
 */
public class LogObject {
	private String timestamp;
	private String type;
	private String log;

	public LogObject(String type, String log) {
		this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		this.type = type;
		this.log = log;
	}
}
