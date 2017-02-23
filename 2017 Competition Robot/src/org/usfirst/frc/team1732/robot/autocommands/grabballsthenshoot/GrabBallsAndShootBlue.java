package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

class GrabBallsAndShootBlue extends CommandGroup {

	public GrabBallsAndShootBlue() {
		// drive to hopper
		addSequential(new DriveEncoders(100));
		// turn to hopper trigger
		addSequential(new TurnWithGyro(-90));
		// drive into hopper trigger
		addSequential(new DriveEncoders(50));
		// turn to be parallel with wall
		addSequential(new TurnWithGyro(-90));
		// drive forward below hopper
		addSequential(new DriveEncoders(20));
		// wait for balls to fall in
		addSequential(new Wait(1.5));
		// addSequential(new DriveEncoders(-50));
		// drive to boiler, may need to adjust
		addSequential(new DriveEncoders(80));
		// addSequential(new TurnWithGyro(45));
		// shoot
	}
}
