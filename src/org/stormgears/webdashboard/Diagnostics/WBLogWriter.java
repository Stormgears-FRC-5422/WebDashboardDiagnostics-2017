package org.stormgears.webdashboard.Diagnostics;

import org.stormgears.webdashboard.WebDashboard;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by andrew on 1/14/17.
 */
class WBLogWriter extends Writer {
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
