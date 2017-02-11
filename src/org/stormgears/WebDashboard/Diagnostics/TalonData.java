package org.stormgears.WebDashboard.Diagnostics;

import com.ctre.CANTalon;

/**
 * Created by andrew on 2/10/17.
 */
class TalonData {
	int deviceID;
	CANTalon.ControlMode controlMode;
	double getValue;
	double busVoltage;
	int encPosition;
	int encVelocity;
	double outputCurrent;
	double outputVoltage;
	double position;
	double speed;
	double temperature;

	public TalonData(int deviceID, CANTalon.ControlMode controlMode, double getValue, double busVoltage, int encPosition, int encVelocity, double outputCurrent, double outputVoltage, double position, double speed, double temperature) {
		this.deviceID = deviceID;
		this.controlMode = controlMode;
		this.getValue = getValue;
		this.busVoltage = busVoltage;
		this.encPosition = encPosition;
		this.encVelocity = encVelocity;
		this.outputCurrent = outputCurrent;
		this.outputVoltage = outputVoltage;
		this.position = position;
		this.speed = speed;
		this.temperature = temperature;
	}
}
