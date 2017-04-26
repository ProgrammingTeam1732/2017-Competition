package org.usfirst.frc.team1732.robot.commands.gearIntake.base.position;

import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.position.BallIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetIn;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.wings.WingsSetIn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearIntakeSetDown extends CommandGroup {

	public GearIntakeSetDown() {
		addSequential(new BallIntakeSetDown());
		addSequential(new WingsSetIn());
		addSequential(new ArmSetIn());
		addSequential(new Wait(0.1));
		addSequential(new GearIntakeSetDownNoCheck());
	}
}
