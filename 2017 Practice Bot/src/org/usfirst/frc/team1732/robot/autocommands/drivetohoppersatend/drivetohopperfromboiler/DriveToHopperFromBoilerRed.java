package org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromboiler;

import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToHopperFromBoilerRed extends CommandGroup {

	public DriveToHopperFromBoilerRed() {
		// backup slightly
		addSequential(new DriveEncoders(DriveToHopperFromBoilerData.DRIVE_1_SETPOINT));

		// turn towards hoppers
		addSequential(new TurnWithEncoders(DriveToHopperFromBoilerData.TURN_1_ANGLE_RED));

		// drive towards hoppers
		addSequential(new DriveEncoders(DriveToHopperFromBoilerData.DRIVE_2_SETPOINT));
	}
}
