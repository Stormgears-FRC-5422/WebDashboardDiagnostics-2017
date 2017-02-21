package org.stormgears.WebDashboard.Diagnostics;

/**
 * Created by andrew on 2/21/17.
 */
public class AHRSData {
	float pitch;
	float roll;
	float yaw;
	double rate;
	double angle;
	double angleAdjustment;
	double byteCount;
	float compassHeading;
	float fusedHeading;
	float rawAccelX;
	float rawAccelY;
	float rawAccelZ;
	float rawGyroX;
	float rawGyroY;
	float rawGyroZ;
	float rawMagX;
	float rawMagY;
	float rawMagZ;
	float displacementX;
	float displacementY;
	float displacementZ;
	float velocityX;
	float velocityY;
	float velocityZ;
	float tempC;
	float worldLinearAccelX;
	float worldLinearAccelY;
	float worldLinearAccelZ;
	boolean altitudeValid;
	boolean calibrating;
	boolean connected;
	boolean magneticDisturbance;
	boolean magnetometerCalibrated;
	boolean moving;
	boolean rotating;
	String firmwareVersion;

	public AHRSData(float pitch, float roll, float yaw, double rate, double angle, double angleAdjustment, double byteCount, float compassHeading, float fusedHeading, float rawAccelX, float rawAccelY, float rawAccelZ, float rawGyroX, float rawGyroY, float rawGyroZ, float rawMagX, float rawMagY, float rawMagZ, float displacementX, float displacementY, float displacementZ, float velocityX, float velocityY, float velocityZ, float tempC, float worldLinearAccelX, float worldLinearAccelY, float worldLinearAccelZ, boolean altitudeValid, boolean calibrating, boolean connected, boolean magneticDisturbance, boolean magnetometerCalibrated, boolean moving, boolean rotating, String firmwareVersion) {
		this.pitch = pitch;
		this.roll = roll;
		this.yaw = yaw;
		this.rate = rate;
		this.angle = angle;
		this.angleAdjustment = angleAdjustment;
		this.byteCount = byteCount;
		this.compassHeading = compassHeading;
		this.fusedHeading = fusedHeading;
		this.rawAccelX = rawAccelX;
		this.rawAccelY = rawAccelY;
		this.rawAccelZ = rawAccelZ;
		this.rawGyroX = rawGyroX;
		this.rawGyroY = rawGyroY;
		this.rawGyroZ = rawGyroZ;
		this.rawMagX = rawMagX;
		this.rawMagY = rawMagY;
		this.rawMagZ = rawMagZ;
		this.displacementX = displacementX;
		this.displacementY = displacementY;
		this.displacementZ = displacementZ;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
		this.tempC = tempC;
		this.worldLinearAccelX = worldLinearAccelX;
		this.worldLinearAccelY = worldLinearAccelY;
		this.worldLinearAccelZ = worldLinearAccelZ;
		this.altitudeValid = altitudeValid;
		this.calibrating = calibrating;
		this.connected = connected;
		this.magneticDisturbance = magneticDisturbance;
		this.magnetometerCalibrated = magnetometerCalibrated;
		this.moving = moving;
		this.rotating = rotating;
		this.firmwareVersion = firmwareVersion;
	}
}
