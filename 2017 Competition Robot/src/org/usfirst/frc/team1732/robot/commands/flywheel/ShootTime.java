package org.usfirst.frc.team1732.robot.commands.flywheel;

import org.usfirst.frc.team1732.robot.commands.Wait;
import org.usfirst.frc.team1732.robot.commands.ballandfeeder.StopIntakeAndFeeder;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * For use during automode
 */
public class ShootTime extends CommandGroup {

	public ShootTime(double time) {
		addSequential(new EnableFlywheel());
		addSequential(new Wait(1)); // wait for flywheel to spin up
		addSequential(new Shoot());
		addSequential(new Wait(time));
		addSequential(new StopIntakeAndFeeder());
	}
}
