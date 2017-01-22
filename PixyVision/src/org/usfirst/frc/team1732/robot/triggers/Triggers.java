package org.usfirst.frc.team1732.robot.triggers;

import org.usfirst.frc.team1732.robot.commands.DriveWithVision;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class Triggers {
	// Instantiate custom triggers
	// public variables in case other parts of the program want to use them
	public final Trigger isAutoModeTrigger = new Trigger() {
		@Override
		public boolean get() {
			return DriverStation.getInstance().isAutonomous();
		}
	};

	// If these triggers should trigger commands, add the code here

	public Triggers() {
		isAutoModeTrigger.whileActive(new DriveWithVision());
	}

}