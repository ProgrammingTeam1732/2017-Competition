package org.usfirst.frc.team1732.robot.autocommands;

import org.usfirst.frc.team1732.robot.commands.Wait;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreSideGearRight extends CommandGroup {

	public ScoreSideGearRight() {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		addSequential(new DriveEncoders(55, 55));
		addSequential(new TurnWithGyro(-60));
		addSequential(new Wait(0.2));
		addSequential(new VisionPlaceGear(-18));
	}
}
