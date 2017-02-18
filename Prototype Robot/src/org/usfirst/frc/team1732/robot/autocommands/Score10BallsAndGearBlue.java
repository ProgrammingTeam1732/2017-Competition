package org.usfirst.frc.team1732.robot.autocommands;

import org.usfirst.frc.team1732.robot.commands.Wait;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.flywheel.DisableFlywheel;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Score10BallsAndGearBlue extends CommandGroup {

	public Score10BallsAndGearBlue() {
		addSequential(new DriveEncoders(13, 13));
		addSequential(new DriveGyro(-85, -.2, .6));
		// shoot commands
		// addSequential(new EnableFlywheel());
		addSequential(new Wait(5));
		// addSequential(new DisableFlywheel());
		// drive backwards slightly to avoid knocking wall
		addSequential(new DriveEncoders(-15, -15));
		addSequential(new DriveGyro(-150, -1, -0.15));
		addSequential(new DriveEncoders(-15, -15));
		addSequential(new VisionPlaceGear(-40));
	}
}
