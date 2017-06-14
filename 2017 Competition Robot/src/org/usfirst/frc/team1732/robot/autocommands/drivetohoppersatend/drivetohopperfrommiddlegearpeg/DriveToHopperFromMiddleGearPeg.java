package org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfrommiddlegearpeg;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToHopperFromMiddleGearPeg extends CommandGroup {

	public DriveToHopperFromMiddleGearPeg() {
		boolean isRed = Robot.isRedAlliance.getValue();

		// drive back from peg
		double driveBackDistance = -30;
		addSequential(new DriveEncoders(driveBackDistance));

		// turn towards hoppers (side without boiler)
		double turnToHoppersAngle = 0;
		if (isRed) {
			turnToHoppersAngle = -45;
		} else {
			turnToHoppersAngle = 45;
		}
		addSequential(new TurnWithEncoders(turnToHoppersAngle));

		// drive towards hoppers
		double driveTowardsHoppersDistance = 60;
		addSequential(new DriveEncoders(driveTowardsHoppersDistance));

		// turn toward other side of field
		double turnToOtherSideAngle = 0;
		if (isRed) {
			turnToOtherSideAngle = 45;
		} else {
			turnToOtherSideAngle = -45;
		}
		addSequential(new TurnWithEncoders(turnToOtherSideAngle));

		// drive down field
		double driveDownFieldDistance = 60;
		addSequential(new DriveEncoders(driveDownFieldDistance));
	}
}
