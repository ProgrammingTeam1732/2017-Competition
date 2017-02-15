package org.usfirst.frc.team1732.robot.autocommands;

import org.usfirst.frc.team1732.robot.commands.Wait;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreSideGearLeft extends CommandGroup {
	public ScoreSideGearLeft() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		addSequential(new DriveEncoders(55, 55));
		addSequential(new TurnWithGyro(60));
		addSequential(new Wait(0.2));
		addSequential(new VisionPlaceGear(-25));
	}
}
