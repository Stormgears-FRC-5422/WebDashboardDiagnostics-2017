package org.stormgears.WebDashboard.Diagnostics;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
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

	static AHRS ahrs;
	static AHRSData ahrsData;

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

		initAHRS();

		Timer timer = new Timer("diagnosticsTimer");
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// Talons
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

				// NavX
				{
					float pitch = ahrs.getPitch();
					if (ahrsData.pitch != pitch) {
						ahrsData.pitch = pitch;
						WebDashboard.set("ahrs.pitch", pitch);
					}
					float roll = ahrs.getRoll();
					if (ahrsData.roll != roll) {
						ahrsData.roll = roll;
						WebDashboard.set("ahrs.roll", roll);
					}
					float yaw = ahrs.getYaw();
					if (ahrsData.yaw != yaw) {
						ahrsData.yaw = yaw;
						WebDashboard.set("ahrs.yaw", yaw);
					}
					double rate = ahrs.getRate();
					if (ahrsData.rate != rate) {
						ahrsData.rate = rate;
						WebDashboard.set("ahrs.rate", rate);
					}
					double angle = ahrs.getAngle();
					if (ahrsData.angle != angle) {
						ahrsData.angle = angle;
						WebDashboard.set("ahrs.angle", angle);
					}
					double angleAdjustment = ahrs.getAngleAdjustment();
					if (ahrsData.angleAdjustment != angleAdjustment) {
						ahrsData.angleAdjustment = angleAdjustment;
						WebDashboard.set("ahrs.angleAdjustment", angleAdjustment);
					}
					double byteCount = ahrs.getByteCount();
					if (ahrsData.byteCount != byteCount) {
						ahrsData.byteCount = byteCount;
						WebDashboard.set("ahrs.byteCount", byteCount);
					}
					float compassHeading = ahrs.getCompassHeading();
					if (ahrsData.compassHeading != compassHeading) {
						ahrsData.compassHeading = compassHeading;
						WebDashboard.set("ahrs.compassHeading", compassHeading);
					}
					float fusedHeading = ahrs.getFusedHeading();
					if (ahrsData.fusedHeading != fusedHeading) {
						ahrsData.fusedHeading = fusedHeading;
						WebDashboard.set("ahrs.fusedHeading", fusedHeading);
					}
					float rawAccelX = ahrs.getRawAccelX();
					if (ahrsData.rawAccelX != rawAccelX) {
						ahrsData.rawAccelX = rawAccelX;
						WebDashboard.set("ahrs.rawAccelX", rawAccelX);
					}
					float rawAccelY = ahrs.getRawAccelY();
					if (ahrsData.rawAccelY != rawAccelY) {
						ahrsData.rawAccelY = rawAccelY;
						WebDashboard.set("ahrs.rawAccelY", rawAccelY);
					}
					float rawAccelZ = ahrs.getRawAccelZ();
					if (ahrsData.rawAccelZ != rawAccelZ) {
						ahrsData.rawAccelZ = rawAccelZ;
						WebDashboard.set("ahrs.rawAccelZ", rawAccelZ);
					}
					float rawGyroX = ahrs.getRawGyroX();
					if (ahrsData.rawGyroX != rawGyroX) {
						ahrsData.rawGyroX = rawGyroX;
						WebDashboard.set("ahrs.rawGyroX", rawGyroX);
					}
					float rawGyroY = ahrs.getRawGyroY();
					if (ahrsData.rawGyroY != rawGyroY) {
						ahrsData.rawGyroY = rawGyroY;
						WebDashboard.set("ahrs.rawGyroY", rawGyroY);
					}
					float rawGyroZ = ahrs.getRawGyroZ();
					if (ahrsData.rawGyroZ != rawGyroZ) {
						ahrsData.rawGyroZ = rawGyroZ;
						WebDashboard.set("ahrs.rawGyroZ", rawGyroZ);
					}
					float rawMagX = ahrs.getRawMagX();
					if (ahrsData.rawMagX != rawMagX) {
						ahrsData.rawMagX = rawMagX;
						WebDashboard.set("ahrs.rawMagX", rawMagX);
					}
					float rawMagY = ahrs.getRawMagY();
					if (ahrsData.rawMagY != rawMagY) {
						ahrsData.rawMagY = rawMagY;
						WebDashboard.set("ahrs.rawMagY", rawMagY);
					}
					float rawMagZ = ahrs.getRawMagZ();
					if (ahrsData.rawMagZ != rawMagZ) {
						ahrsData.rawMagZ = rawMagZ;
						WebDashboard.set("ahrs.rawMagZ", rawMagZ);
					}
					float displacementX = ahrs.getDisplacementX();
					if (ahrsData.displacementX != displacementX) {
						ahrsData.displacementX = displacementX;
						WebDashboard.set("ahrs.displacementX", displacementX);
					}
					float displacementY = ahrs.getDisplacementY();
					if (ahrsData.displacementY != displacementY) {
						ahrsData.displacementY = displacementY;
						WebDashboard.set("ahrs.displacementY", displacementY);
					}
					float displacementZ = ahrs.getDisplacementZ();
					if (ahrsData.displacementZ != displacementZ) {
						ahrsData.displacementZ = displacementZ;
						WebDashboard.set("ahrs.displacementZ", displacementZ);
					}
					float velocityX = ahrs.getVelocityX();
					if (ahrsData.velocityX != velocityX) {
						ahrsData.velocityX = velocityX;
						WebDashboard.set("ahrs.velocityX", velocityX);
					}
					float velocityY = ahrs.getVelocityY();
					if (ahrsData.velocityY != velocityY) {
						ahrsData.velocityY = velocityY;
						WebDashboard.set("ahrs.velocityY", velocityY);
					}
					float velocityZ = ahrs.getVelocityZ();
					if (ahrsData.velocityZ != velocityZ) {
						ahrsData.velocityZ = velocityZ;
						WebDashboard.set("ahrs.velocityZ", velocityZ);
					}
					float tempC = ahrs.getTempC();
					if (ahrsData.tempC != tempC) {
						ahrsData.tempC = tempC;
						WebDashboard.set("ahrs.tempC", tempC);
					}
					float worldLinearAccelX = ahrs.getWorldLinearAccelX();
					if (ahrsData.worldLinearAccelX != worldLinearAccelX) {
						ahrsData.worldLinearAccelX = worldLinearAccelX;
						WebDashboard.set("ahrs.worldLinearAccelX", worldLinearAccelX);
					}
					float worldLinearAccelY = ahrs.getWorldLinearAccelY();
					if (ahrsData.worldLinearAccelY != worldLinearAccelY) {
						ahrsData.worldLinearAccelY = worldLinearAccelY;
						WebDashboard.set("ahrs.worldLinearAccelY", worldLinearAccelY);
					}
					float worldLinearAccelZ = ahrs.getWorldLinearAccelZ();
					if (ahrsData.worldLinearAccelZ != worldLinearAccelZ) {
						ahrsData.worldLinearAccelZ = worldLinearAccelZ;
						WebDashboard.set("ahrs.worldLinearAccelZ", worldLinearAccelZ);
					}
					boolean altitudeValid = ahrs.isAltitudeValid();
					if (ahrsData.altitudeValid != altitudeValid) {
						ahrsData.altitudeValid = altitudeValid;
						WebDashboard.set("ahrs.altitudeValid", altitudeValid);
					}
					boolean calibrating = ahrs.isCalibrating();
					if (ahrsData.calibrating != calibrating) {
						ahrsData.calibrating = calibrating;
						WebDashboard.set("ahrs.calibrating", calibrating);
					}
					boolean connected = ahrs.isConnected();
					if (ahrsData.connected != connected) {
						ahrsData.connected = connected;
						WebDashboard.set("ahrs.connected", connected);
					}
					boolean magneticDisturbance = ahrs.isMagneticDisturbance();
					if (ahrsData.magneticDisturbance != magneticDisturbance) {
						ahrsData.magneticDisturbance = magneticDisturbance;
						WebDashboard.set("ahrs.magneticDisturbance", magneticDisturbance);
					}
					boolean magnetometerCalibrated = ahrs.isMagnetometerCalibrated();
					if (ahrsData.magnetometerCalibrated != magnetometerCalibrated) {
						ahrsData.magnetometerCalibrated = magnetometerCalibrated;
						WebDashboard.set("ahrs.magnetometerCalibrated", magnetometerCalibrated);
					}
					boolean moving = ahrs.isMoving();
					if (ahrsData.moving != moving) {
						ahrsData.moving = moving;
						WebDashboard.set("ahrs.moving", moving);
					}
					boolean rotating = ahrs.isRotating();
					if (ahrsData.rotating != rotating) {
						ahrsData.rotating = rotating;
						WebDashboard.set("ahrs.rotating", rotating);
					}
//					String firmwareVersion = ahrs.getFirmwareVersion();
//					if (ahrsData.firmwareVersion != firmwareVersion) {
//						ahrsData.firmwareVersion = firmwareVersion;
//						WebDashboard.set("ahrs.firmwareVersion", firmwareVersion);
//					}
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

	private static void initAHRS() {
		ahrs = new AHRS(I2C.Port.kMXP);
		ahrsData = new AHRSData(
				ahrs.getPitch(),
				ahrs.getRoll(),
				ahrs.getYaw(),
				ahrs.getRate(),
				ahrs.getAngle(),
				ahrs.getAngleAdjustment(),
				ahrs.getByteCount(),
				ahrs.getCompassHeading(),
				ahrs.getFusedHeading(),
				ahrs.getRawAccelX(),
				ahrs.getRawAccelY(),
				ahrs.getRawAccelZ(),
				ahrs.getRawGyroX(),
				ahrs.getRawGyroY(),
				ahrs.getRawGyroZ(),
				ahrs.getRawMagX(),
				ahrs.getRawMagY(),
				ahrs.getRawMagZ(),
				ahrs.getDisplacementX(),
				ahrs.getDisplacementY(),
				ahrs.getDisplacementZ(),
				ahrs.getVelocityX(),
				ahrs.getVelocityY(),
				ahrs.getVelocityZ(),
				ahrs.getTempC(),
				ahrs.getWorldLinearAccelX(),
				ahrs.getWorldLinearAccelY(),
				ahrs.getWorldLinearAccelZ(),
				ahrs.isAltitudeValid(),
				ahrs.isCalibrating(),
				ahrs.isConnected(),
				ahrs.isMagneticDisturbance(),
				ahrs.isMagnetometerCalibrated(),
				ahrs.isMoving(),
				ahrs.isRotating(),
				ahrs.getFirmwareVersion()
		);
		WebDashboard.set("ahrs", ahrsData);
	}
}
