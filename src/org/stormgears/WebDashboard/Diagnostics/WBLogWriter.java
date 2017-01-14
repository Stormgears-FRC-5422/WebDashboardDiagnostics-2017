package org.stormgears.WebDashboard.Diagnostics;

import org.stormgears.WebDashboard.WebDashboard;

import java.io.IOException;
import java.io.Writer;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by andrew on 1/14/17.
 */
class WBLogWriter extends Writer {

	private class LogObject {
		private String timestamp;
		private String type;
		private String log;

		public LogObject(String type, String log) {
			this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			this.type = type;
			this.log = log;
		}
	}

	private final String logName;
	public WBLogWriter(String logName) {
		this.logName = logName;
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		String toWrite = String.copyValueOf(cbuf, off, len);
		WebDashboard.emit("log", new LogObject(logName, toWrite));
	}

	@Override
	public void flush() throws IOException {
		// TODO: ???
	}

	@Override
	public void close() throws IOException {
		WebDashboard.emit("log", new LogObject("WARN", "Robot closed " + logName + "!"));
	}
}