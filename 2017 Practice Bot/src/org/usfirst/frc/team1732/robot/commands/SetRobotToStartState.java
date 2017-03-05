package org.usfirst.frc.team1732.robot.commands;

import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetIn;
import org.usfirst.frc.team1732.robot.commands.climber.ClimberSetStop;
import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetStop;
import org.usfirst.frc.team1732.robot.commands.flywheel.DisableFlywheel;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetStopperOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SetRobotToStartState extends CommandGroup {

	public SetRobotToStartState() {
		addSequential(new ArmSetIn());
		addSequential(new ClimberSetStop());
		addSequential(new SetMotorSpeed(0, 0));
		addSequential(new FeederSetStop());
		addSequential(new DisableFlywheel());
		addSequential(new GearIntakeSetUp());
		addSequential(new GearIntakeSetStop());
		addSequential(new BallIntakeSetDown());
		addSequential(new BallIntakeSetStop());
	}
}
