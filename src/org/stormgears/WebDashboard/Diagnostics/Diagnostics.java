package org.stormgears.WebDashboard.Diagnostics;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.ctre.CANTalon;
import org.stormgears.WebDashboard.WebDashboard;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by andrew on 1/13/17.
 */
public class Diagnostics {
	/**
	 * A list of Talon SRX devices attached to the robot
	 */
	static ArrayList<CANTalon> talons = new ArrayList<>();
	static ArrayList<TalonData> talonDatas = new ArrayList<>();

	public static void init() {
		Diagnostics.init(false);
	}

	/**
	 * Hooks into the various systems in which to do diagnostics (unfinished)
	 */
	public static void init(boolean production) {
		/*
		 * TODO: Hook into stdout and stderr
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

		// intercept system.out
		System.setOut(new PrintStreamInterceptor(System.out, "STDOUT"));

		// Place all code to run in development mode below this if statement
		if (production) {
			return;
		}

		try {
			getDevices();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Timer talonTimer = new Timer("talonTimer");
		talonTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				for (int i = 0; i < talons.size(); i++) {
					CANTalon talon = talons.get(i);
					TalonData talonData = talonDatas.get(i);

					// update the WebDashboard data
					// generated with sublime text
					// sorry
					CANTalon.ControlMode controlMode = talon.getControlMode();
					if (controlMode != talonData.controlMode) {
						WebDashboard.set("talons[" + i + "].controlMode", controlMode);
						talonData.controlMode = controlMode;
					}

					double getValue = talon.get();
					if (getValue != talonData.getValue) {
						WebDashboard.set("talons[" + i + "].getValue", getValue);
						talonData.getValue = getValue;
					}

					double busVoltage = talon.getBusVoltage();
					if (busVoltage != talonData.busVoltage) {
						WebDashboard.set("talons[" + i + "].busVoltage", busVoltage);
						talonData.busVoltage = busVoltage;
					}

					int encPosition = talon.getEncPosition();
					if (encPosition != talonData.encPosition) {
						WebDashboard.set("talons[" + i + "].encPosition", encPosition);
						talonData.encPosition = encPosition;
					}

					int encVelocity = talon.getEncVelocity();
					if (encVelocity != talonData.encVelocity) {
						WebDashboard.set("talons[" + i + "].encVelocity", encVelocity);
						talonData.encVelocity = encVelocity;
					}

					double outputCurrent = talon.getOutputCurrent();
					if (outputCurrent != talonData.outputCurrent) {
						WebDashboard.set("talons[" + i + "].outputCurrent", outputCurrent);
						talonData.outputCurrent = outputCurrent;
					}

					double outputVoltage = talon.getOutputVoltage();
					if (outputVoltage != talonData.outputVoltage) {
						WebDashboard.set("talons[" + i + "].outputVoltage", outputVoltage);
						talonData.outputVoltage = outputVoltage;
					}

					double position = talon.getPosition();
					if (position != talonData.position) {
						WebDashboard.set("talons[" + i + "].position", position);
						talonData.position = position;
					}

					double speed = talon.getSpeed();
					if (speed != talonData.speed) {
						WebDashboard.set("talons[" + i + "].speed", speed);
						talonData.speed = speed;
					}

					double temperature = talon.getTemperature();
					if (temperature != talonData.temperature) {
						WebDashboard.set("talons[" + i + "].temperature", temperature);
						talonData.temperature = temperature;
					}
				}
			}
		}, 0, 250);
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

	/**
	 * Requests the list of devices from the RoboRIO, and
	 * adds Talons to a list
	 * @throws Exception
	 */
	static void getDevices() throws Exception {
		InputStream is = getDeviceStream();
//		InputStream is = getDeviceTestingStream();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(is);

		NodeList bags = document.getElementsByTagName("PropertyBag");
		ArrayList<NIDevice> devices = new ArrayList<>();
		for (int i = 0; i < bags.getLength(); i++) {
			NIDevice dev = NIDevice.parsePropertyBag(bags.item(i));
			devices.add(dev);
			if (dev.type.equalsIgnoreCase("Talon SRX")) {
				byte talon = Byte.parseByte(dev.properties.get(0x1A110000).value);
				CANTalon canTalon = new CANTalon(talon);
				talons.add(canTalon);
				talonDatas.add(new TalonData(
						canTalon.getDeviceID(),
						canTalon.getControlMode(),
						canTalon.get(),
						canTalon.getBusVoltage(),
						canTalon.getEncPosition(),
						canTalon.getEncVelocity(),
						canTalon.getOutputCurrent(),
						canTalon.getOutputVoltage(),
						canTalon.getPosition(),
						canTalon.getSpeed(),
						canTalon.getTemperature()
				));
			}
		}

		WebDashboard.set("devices", devices);
		WebDashboard.set("talons", talonDatas);
	}

	private static InputStream getDeviceStream() throws Exception {
		URL nisys = new URL("http://roborio-5422-frc.local/nisysapi/server");
		HttpURLConnection c = (HttpURLConnection) nisys.openConnection();
		c.setRequestMethod("POST");
		c.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

		String postData = "Function=SearchForItemsAndProperties&Version=100000&Plugins=frccan,niimaqdx,ni-rio,ni-visa,nisyscfg&response_encoding=UTF-8&FilterMode=2&NbrBags=1&Tag_0_0=1001000&Type_0_0=1&Value_0_0=1&Tag_0_1=1028000&Type_0_1=1&Value_0_1=1&Tag_0_2=1038000&Type_0_2=1&Value_0_2=1&NbrTags_0=3&";
		c.setRequestProperty("Content-Length", "" + postData.length());
		c.setUseCaches(false);
		c.setDoOutput(true);

		DataOutputStream wr = new DataOutputStream(c.getOutputStream());
		wr.writeBytes(postData);
		wr.close();

		return c.getInputStream();
	}
	private static InputStream getDeviceTestingStream() throws Exception {
		URL nisys = new URL("http://71.248.165.187/untitled.xml");
		HttpURLConnection c = (HttpURLConnection) nisys.openConnection();

		c.setUseCaches(false);
		c.setDoOutput(true);

		return c.getInputStream();
	}

}
