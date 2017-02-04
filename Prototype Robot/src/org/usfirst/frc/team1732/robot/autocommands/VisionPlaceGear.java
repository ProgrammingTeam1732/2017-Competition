package org.usfirst.frc.team1732.robot.autocommands;

import org.usfirst.frc.team1732.robot.commands.Drive1DEncoders;
import org.usfirst.frc.team1732.robot.commands.DriveWithVision;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeOutTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetUp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionPlaceGear extends CommandGroup {

	public VisionPlaceGear() {
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
		addSequential(new DriveWithVision(1));
		addSequential(new GearIntakeSetDown());
		addParallel(new GearIntakeOutTime(2));
		addSequential(new Drive1DEncoders(-20, -20));
		addSequential(new GearIntakeSetUp());
	}
}
