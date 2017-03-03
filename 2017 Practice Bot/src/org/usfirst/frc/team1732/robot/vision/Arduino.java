package org.usfirst.frc.team1732.robot.vision;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.SerialPort;

/**
 * This class contains the code for interfacing with the arduino The USB should
 * be plugged into the edge usb port
 */
public class Arduino {
	private SerialPort serial;

	/**
	 * Constructs a new arduino reader Sets the baudrate Connects to the arduino
	 */
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
	private long	maxWait		= 5000;

	/**
	 * @return the String read from the arduino, could be an empty String
	 */
	public String getData() {
		String s = "";
		try {
			s = this.serial.readString(); // reads string from arduino
			startTime = System.currentTimeMillis(); // gets the start time
			while (System.currentTimeMillis() - startTime < maxWait && !s.contains("\n")) {
				s += this.serial.readString();
			} // until a new line is found (or the loop runs for 5 seconds), add
				// the read string to end of output
			if (System.currentTimeMillis() - startTime > maxWait) {
				Robot.visionMain.disableCamera();
			} // disables the camera if the while loop gets stuck
			return s;
		} catch (Exception e) {
			// System.out.println("something went wrong, " + e.getMessage());
			// e.printStackTrace();
			return null;
		}
	}
}
