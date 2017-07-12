package org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromsidegearpeg;

import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToHopperFromSideGearPeg extends CommandGroup {

    public DriveToHopperFromSideGearPeg(boolean isLeft) {
	// drive back a little
	double driveBackDistance = -20;
	addSequential(new DriveEncoders(driveBackDistance));
	// turn to face down field
	double angle = (isLeft) ? -80 : 80;
	addSequential(new TurnWithEncoders(angle));
	// drive down field
	double driveDownDistance = 40;
	addSequential(new DriveEncoders(driveDownDistance));
    }
}
