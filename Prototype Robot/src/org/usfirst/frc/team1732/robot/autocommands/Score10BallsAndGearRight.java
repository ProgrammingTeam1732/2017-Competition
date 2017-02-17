package org.usfirst.frc.team1732.robot.autocommands;

import org.usfirst.frc.team1732.robot.commands.Wait;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.flywheel.DisableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.EnableFlywheel;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Score10BallsAndGearRight extends CommandGroup{
	public Score10BallsAndGearRight(){
		addSequential(new DriveEncoders(13, 13));
		addSequential(new DriveGyro(85, .6, -.2));
		// shoot commands
		addSequential(new EnableFlywheel());
		addSequential(new Wait(5));
		addSequential(new DisableFlywheel());
		// drive backwards slightly to avoid knocking wall
		addSequential(new DriveEncoders(-15, -15));
		addSequential(new DriveGyro(150, .15, 1));
		addSequential(new DriveEncoders(-15, -15));
		addSequential(new VisionPlaceGear(-40));
	}
}
