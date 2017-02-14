package org.usfirst.frc.team1732.robot.vision;

import edu.wpi.first.wpilibj.SerialPort;

public class Arduino {
	private SerialPort serial;

	public Arduino() {
		try {
			serial = new SerialPort(19200, SerialPort.Port.kUSB1);
			this.serial.disableTermination();
			System.out.println("Arduino Starting, waiting 0.5 seconds to get data");
			// String e = this.getData();
			edu.wpi.first.wpilibj.Timer.delay(0.125);

			// if(e.equals("h")) {
			// System.out.println("Arduino communications locked in");
			// }
		} catch (Exception e) {
			System.out.println("something went wrong, " + e.getMessage());
		}
	}

	private long	startTime	= System.currentTimeMillis();
	private long	maxWait		= 500;

	public String getData() {
		String s = "";
		try {
			s = this.serial.readString();
			startTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - startTime < maxWait && !s.contains("\n")) {
				s += this.serial.readString();
			}
			return s;
		} catch (Exception e) {
			System.out.println("something went wrong, " + e.getMessage());
			// e.printStackTrace();
			return s;
		}
	}
}
