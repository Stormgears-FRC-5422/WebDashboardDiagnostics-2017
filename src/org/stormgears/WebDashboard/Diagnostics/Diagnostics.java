package org.stormgears.WebDashboard.Diagnostics;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

import org.apache.commons.io.output.TeeOutputStream;
import org.apache.commons.io.output.WriterOutputStream;
import org.stormgears.WebDashboard.WebDashboard;

/**
 * Created by andrew on 1/13/17.
 */
public class Diagnostics {
	/**
	 * Hooks into the various systems in which to do diagnostics
	 */
	public static void init() {
		/*
		 * Hook into stdout and stderr
		 */
		TeeOutputStream out = new TeeOutputStream(System.out, new WriterOutputStream((new WBLogWriter("STDOUT")), "UTF-8"));
		PrintStream outPs = new PrintStream(out);
		System.setOut(outPs);

		TeeOutputStream err = new TeeOutputStream(System.err, new WriterOutputStream((new WBLogWriter("STDERR")), "UTF-8"));
		PrintStream errPs = new PrintStream(err);
		System.setOut(errPs);
	}
}
