package org.usfirst.frc.team1732.robot.autocommands;

import org.usfirst.frc.team1732.robot.commands.Wait;
import org.usfirst.frc.team1732.robot.commands.drivetrain.Drive1DEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.Drive1DTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.flywheel.DisableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.EnableFlywheel;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Score10BallsAndGear extends CommandGroup {

	public Score10BallsAndGear(boolean isRedAlliance) {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
		double speed = 0.5;
		double time = 5;
		if (isRedAlliance) {
			addSequential(new Drive1DTime(time, speed, 0));
		} else {
			addSequential(new Drive1DTime(time, 0, speed));
		}
		addSequential(new Wait(2));
		// shoot commands
		addSequential(new EnableFlywheel());
		addSequential(new Wait(5));
		addSequential(new DisableFlywheel());
		// drive backwards slightly to avoid knocking wall
		addSequential(new Drive1DEncoders(-15, -15));
		addSequential(new TurnWithGyro(180));
		addSequential(new VisionPlaceGear());
	}
}
