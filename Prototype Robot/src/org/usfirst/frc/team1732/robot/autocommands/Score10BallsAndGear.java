package org.usfirst.frc.team1732.robot.autocommands;

import org.usfirst.frc.team1732.robot.commands.Wait;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.flywheel.DisableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.EnableFlywheel;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Score10BallsAndGear extends CommandGroup {

	public Score10BallsAndGear(boolean isRedAlliance) {
		double speed = 0.5;
		double time = 5;
		// if (isRedAlliance) {
		// addSequential(new DriveTime(time, speed, 0));
		// } else {
		// addSequential(new DriveTime(time, 0, speed));
		// }

		// addSequential(new Wait(4));
		addSequential(new DriveGyro(-85, -.2, .6));
		// shoot commands
		addSequential(new EnableFlywheel());
		addSequential(new Wait(5));
		addSequential(new DisableFlywheel());
		// drive backwards slightly to avoid knocking wall
		addSequential(new DriveEncoders(-15, -15));
		addSequential(new DriveGyro(-150, -1, -0.2));
		addSequential(new DriveEncoders(-15, -15));
		addSequential(new VisionPlaceGear());
	}
}
