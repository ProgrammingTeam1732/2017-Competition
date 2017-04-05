package org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromboiler;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToHopperFromBoiler extends CommandGroup {

	public DriveToHopperFromBoiler() {
		boolean isRed = Robot.isRedAlliance.getValue();

		// backup slightly
		double backUpDistance = -60;
		addSequential(new DriveEncoders(backUpDistance));

		// turn towards other side
		double faceHopperAngle = 0;
		if (isRed) {
			faceHopperAngle = -135;
		} else {
			faceHopperAngle = 135;
		}
		addSequential(new TurnWithEncoders(faceHopperAngle));

		// drive towards hoppers
		double driveToHoppersDistance = 40;
		addSequential(new DriveEncoders(driveToHoppersDistance));
	}
}
