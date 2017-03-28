package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeInTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetUp;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GearIntakeSetUpTimedIn extends CommandGroup {

	public GearIntakeSetUpTimedIn(double time) {
		addSequential(new GearIntakeSetUp());
		addSequential(new GearIntakeInTime(time));
	}
}
