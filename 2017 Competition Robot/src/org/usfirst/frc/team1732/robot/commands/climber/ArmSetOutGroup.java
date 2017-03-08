package org.usfirst.frc.team1732.robot.commands.climber;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetStopperIn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ArmSetOutGroup extends CommandGroup {

	public ArmSetOutGroup() {

		if (Robot.gearIntake.isStopperOut()) {
			addSequential(new GearIntakeSetStopperIn());
		}
		addSequential(new BallIntakeSetDown());
		if (Robot.gearIntake.isUp() && Robot.ballIntake.isPositionDown()) {
			// Robot.gearIntake.setStopperIn();
			addSequential(new ArmSetOut());
		}
	}
}
