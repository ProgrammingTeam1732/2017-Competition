package org.usfirst.frc.team1732.robot.commands.flywheel;

import org.usfirst.frc.team1732.robot.commands.ballandfeeder.StopIntakeAndFeeder;
import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetOut;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * For use during automode
 */
public class ShootTime extends CommandGroup {

	public ShootTime(double timeSeconds) {
		addSequential(new EnableFlywheel());
		addSequential(new Wait(0.25)); // wait for flywheel to spin up
		addSequential(new BallIntakeSetDown()); // sets ball intake down
		addSequential(new FeederSetOut()); // sets feeder to go out (towards
											// flywheel)
		addSequential(new BallIntakeSetIn()); // sets intake to go in (towards
												// flywheel)
		addSequential(new Wait(timeSeconds));
		addSequential(new StopIntakeAndFeeder());
	}
}
