package org.usfirst.frc.team1732.robot.commands.ballintake;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeInTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetUp;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class BallIntakeSetTimedIn extends CommandGroup {

	public BallIntakeSetTimedIn(double time) {
		addSequential(new BallIntakeSetIn());
		addSequential(new BallIntakeInTime(time));
	}
}
