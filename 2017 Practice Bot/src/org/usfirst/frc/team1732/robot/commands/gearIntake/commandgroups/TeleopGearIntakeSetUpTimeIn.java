package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.TeleopGearIntakeInTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetUp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Used for gear pickup during teleop
 */
public class TeleopGearIntakeSetUpTimeIn extends CommandGroup {

	public TeleopGearIntakeSetUpTimeIn(double time) {
		addSequential(new GearIntakeSetUp());
		addSequential(new TeleopGearIntakeInTime(time));
		addSequential(new GearIntakeSetStop());
		//		addSequential(new TeleopGearHold());
	}
}
