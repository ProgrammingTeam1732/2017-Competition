package org.usfirst.frc.team1732.robot.vision;

import edu.wpi.first.wpilibj.SerialPort;

public class Arduino {
	private SerialPort serial;

	public Arduino() {
		try {
			serial = new SerialPort(19200, SerialPort.Port.kUSB1);
			this.serial.disableTermination();
		} catch (Exception e) {
			System.out.println("something went wrong, " + e.getMessage());
		}
	}

	public String getData() {
		try {
			String s = this.serial.readString();
			while (!s.contains("\n"))
				s += this.serial.readString();
			return s;
		} catch (Exception e) {
			System.out.println("something went wrong, " + e.getMessage());
			return null;
		}
	}
}
