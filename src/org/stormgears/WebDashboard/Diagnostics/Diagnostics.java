package org.stormgears.WebDashboard.Diagnostics;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Set;

import edu.wpi.first.wpilibj.TalonSRX;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.commons.io.output.WriterOutputStream;
import org.stormgears.WebDashboard.WebDashboard;

/**
 * Created by andrew on 1/13/17.
 */
public class Diagnostics {
	/**
	 * Hooks into the various systems in which to do diagnostics (unfinished)
	 */
	public static void init() {
		/*
		 * FIXME: Hook into stdout and stderr
		 * (this is not working right now, disable it)
		 */

		// exception handler
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				log("EXCEP", "Thread: " + t);

				StringWriter out = new StringWriter();
				e.printStackTrace(new PrintWriter(out));
				log("EXCEP", out.toString());

				e.printStackTrace();
			}
		});
	}

	/**
	 * Logs a message to the Web console
	 * @param data the message to log
	 */
	public static void log(String data) {
		log("DEFAULT", data);
	}

	/**
	 * Logs a message to the Web console, with a specified tag
	 * @param type the type/module of message, displayed in [...] in the dashboard
	 * @param data the message to log
	 */
	public static void log(String type, String data) {
		log(new LogObject(type, data));
	}

	private static void log(LogObject data) {
		WebDashboard.emit("log", data);
	}

}
