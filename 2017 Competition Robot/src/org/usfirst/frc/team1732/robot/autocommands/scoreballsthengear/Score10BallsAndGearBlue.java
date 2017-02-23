package org.usfirst.frc.team1732.robot.autocommands.scoreballsthengear;

import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

class Score10BallsAndGearBlue extends CommandGroup {

	public Score10BallsAndGearBlue() {
		addSequential(new DriveEncoders(15, 15));
		addSequential(new DriveGyro(-80, -.3, .7));
		// shoot commands
		// addSequential(new EnableFlywheel());
		addSequential(new Wait(5));
		// addSequential(new DisableFlywheel());
		// drive backwards slightly to avoid knocking wall
		addSequential(new DriveEncoders(-25, -25));
		addSequential(new DriveGyro(-140, -1, -0.0));
		addSequential(new DriveEncoders(-15, -15));
		addSequential(new VisionPlaceGear(-40));
	}
}
