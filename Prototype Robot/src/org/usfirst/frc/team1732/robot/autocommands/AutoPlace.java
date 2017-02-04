package org.usfirst.frc.team1732.robot.autocommands;

import org.usfirst.frc.team1732.robot.commands.Drive1D;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeOutTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetDown;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPlace extends CommandGroup {

	public AutoPlace() {
		long time = System.currentTimeMillis();
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
		// addSequential(new Drive1D(0.7, true));
		addSequential(new GearIntakeSetDown());
		addParallel(new GearIntakeOutTime(0.5));
		addParallel(new Drive1D(0.7, false));
	}
}
