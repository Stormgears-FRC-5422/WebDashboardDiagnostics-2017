package org.stormgears.webdashboard.Diagnostics;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public
class PrintStreamInterceptor extends PrintStream {

	private final String type;

	private StringBuilder buf = new StringBuilder();
	private Method newLineMethod;

	public PrintStreamInterceptor(OutputStream out, String type) {
		super(out);

		try {
			newLineMethod = getClass().getSuperclass().getDeclaredMethod("newLine");
			newLineMethod.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		this.type = type;
	}

	@Override
	public void print(boolean b) {
		print(String.valueOf(b));
	}

	@Override
	public void print(char c) {
		print(String.valueOf(c));
	}

	@Override
	public void print(int i) {
		print(String.valueOf(i));
	}

	@Override
	public void print(long l) {
		print(String.valueOf(l));
	}

	@Override
	public void print(float f) {
		print(String.valueOf(f));
	}

	@Override
	public void print(double d) {
		print(String.valueOf(d));
	}

	@Override
	public void print(char[] s) {
		print(String.valueOf(s));
	}

	@Override
	public void print(Object obj) {
		print(String.valueOf(obj));
	}

	@Override
	public void print(String s) {
		super.print(s);

		if (s == null) {
			s = "null";
		}
		_append(s);
	}

	private void _flush() {
		Diagnostics.log(type, buf.toString());
		buf = new StringBuilder();
	}

	private void _append(String s) {
		String[] newLines = s.split("\n", -1);
		for (String a : newLines) {
			buf.append(a);
			if (a.isEmpty()) {
				_flush();
			}
		}
	}

	    /* Methods that do terminate lines */

	/**
	 * Terminates the current line by writing the line separator string.  The
	 * line separator string is defined by the system property
	 * <code>line.separator</code>, and is not necessarily a single newline
	 * character (<code>'\n'</code>).
	 */
	public void println() {
		_newLine();
	}

	/**
	 * Prints a boolean and then terminate the line.  This method behaves as
	 * though it invokes <code>{@link #print(boolean)}</code> and then
	 * <code>{@link #println()}</code>.
	 *
	 * @param x  The <code>boolean</code> to be printed
	 */
	public void println(boolean x) {
		synchronized (this) {
			print(x);
			_newLine();
		}
	}

	/**
	 * Prints a character and then terminate the line.  This method behaves as
	 * though it invokes <code>{@link #print(char)}</code> and then
	 * <code>{@link #println()}</code>.
	 *
	 * @param x  The <code>char</code> to be printed.
	 */
	public void println(char x) {
		synchronized (this) {
			print(x);
			_newLine();
		}
	}

	/**
	 * Prints an integer and then terminate the line.  This method behaves as
	 * though it invokes <code>{@link #print(int)}</code> and then
	 * <code>{@link #println()}</code>.
	 *
	 * @param x  The <code>int</code> to be printed.
	 */
	public void println(int x) {
		synchronized (this) {
			print(x);
			_newLine();
		}
	}

	/**
	 * Prints a long and then terminate the line.  This method behaves as
	 * though it invokes <code>{@link #print(long)}</code> and then
	 * <code>{@link #println()}</code>.
	 *
	 * @param x  a The <code>long</code> to be printed.
	 */
	public void println(long x) {
		synchronized (this) {
			print(x);
			_newLine();
		}
	}

	/**
	 * Prints a float and then terminate the line.  This method behaves as
	 * though it invokes <code>{@link #print(float)}</code> and then
	 * <code>{@link #println()}</code>.
	 *
	 * @param x  The <code>float</code> to be printed.
	 */
	public void println(float x) {
		synchronized (this) {
			print(x);
			_newLine();
		}
	}

	/**
	 * Prints a double and then terminate the line.  This method behaves as
	 * though it invokes <code>{@link #print(double)}</code> and then
	 * <code>{@link #println()}</code>.
	 *
	 * @param x  The <code>double</code> to be printed.
	 */
	public void println(double x) {
		synchronized (this) {
			print(x);
			_newLine();
		}
	}

	/**
	 * Prints an array of characters and then terminate the line.  This method
	 * behaves as though it invokes <code>{@link #print(char[])}</code> and
	 * then <code>{@link #println()}</code>.
	 *
	 * @param x  an array of chars to print.
	 */
	public void println(char x[]) {
		synchronized (this) {
			print(x);
			_newLine();
		}
	}

	/**
	 * Prints a String and then terminate the line.  This method behaves as
	 * though it invokes <code>{@link #print(String)}</code> and then
	 * <code>{@link #println()}</code>.
	 *
	 * @param x  The <code>String</code> to be printed.
	 */
	public void println(String x) {
		synchronized (this) {
			print(x);
			_newLine();
		}
	}

	/**
	 * Prints an Object and then terminate the line.  This method calls
	 * at first String.valueOf(x) to get the printed object's string value,
	 * then behaves as
	 * though it invokes <code>{@link #print(String)}</code> and then
	 * <code>{@link #println()}</code>.
	 *
	 * @param x  The <code>Object</code> to be printed.
	 */
	public void println(Object x) {
		String s = String.valueOf(x);
		synchronized (this) {
			print(s);
			_newLine();
		}
	}

	private void _newLine() {
		try {
			newLineMethod.invoke(this);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		_flush();
	}
}
