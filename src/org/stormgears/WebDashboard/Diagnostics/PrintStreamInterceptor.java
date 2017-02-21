package org.stormgears.WebDashboard.Diagnostics;

import java.io.OutputStream;
import java.io.PrintStream;

public
class PrintStreamInterceptor extends PrintStream {

	private final String type;

	public PrintStreamInterceptor(OutputStream out, String type) {
		super(out);
		this.type = type;
	}

	@Override
	public void print(boolean b) {
		super.print(b);
		Diagnostics.log(type, String.valueOf(b));
	}

	@Override
	public void print(char c) {
		super.print(c);
		Diagnostics.log(type, String.valueOf(c));
	}

	@Override
	public void print(int i) {
		super.print(i);
		Diagnostics.log(type, String.valueOf(i));
	}

	@Override
	public void print(long l) {
		super.print(l);
		Diagnostics.log(type, String.valueOf(l));
	}

	@Override
	public void print(float f) {
		super.print(f);
		Diagnostics.log(type, String.valueOf(f));
	}

	@Override
	public void print(double d) {
		super.print(d);
		Diagnostics.log(type, String.valueOf(d));
	}

	@Override
	public void print(char[] s) {
		super.print(s);
		Diagnostics.log(type, String.valueOf(s));
	}

	@Override
	public void print(String s) {
		super.print(s);
		Diagnostics.log(type, s);
	}

	@Override
	public void print(Object obj) {
		super.print(obj);
		Diagnostics.log(type, String.valueOf(obj));
	}

//	@Override
//	public void println() {
//		super.println();
//		Diagnostics.log(type, "");
//	}
//
//	@Override
//	public void println(boolean x) {
//		super.println(x);
//		Diagnostics.log(type, String.valueOf(x));
//	}
//
//	@Override
//	public void println(char x) {
//		super.println(x);
//		Diagnostics.log(type, String.valueOf(x));
//	}
//
//	@Override
//	public void println(int x) {
//		super.println(x);
//		Diagnostics.log(type, String.valueOf(x));
//	}
//
//	@Override
//	public void println(long x) {
//		super.println(x);
//		Diagnostics.log(type, String.valueOf(x));
//	}
//
//	@Override
//	public void println(float x) {
//		super.println(x);
//		Diagnostics.log(type, String.valueOf(x));
//	}
//
//	@Override
//	public void println(double x) {
//		super.println(x);
//		Diagnostics.log(type, String.valueOf(x));
//	}
//
//	@Override
//	public void println(char[] x) {
//		super.println(x);
//		Diagnostics.log(type, String.valueOf(x));
//	}
//
//	@Override
//	public void println(String x) {
//		super.println(x);
//		Diagnostics.log(type, String.valueOf(x));
//	}
//
//	@Override
//	public void println(Object x) {
//		super.println(x);
//		Diagnostics.log(type, String.valueOf(x));
//	}
}
