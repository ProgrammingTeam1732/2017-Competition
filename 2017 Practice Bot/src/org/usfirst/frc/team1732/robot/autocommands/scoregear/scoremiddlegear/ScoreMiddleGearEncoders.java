package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ResetEncoderPID;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.SetEncoderPID;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreMiddleGearEncoders extends CommandGroup {

	public ScoreMiddleGearEncoders(double driveBackDistance) {
		// wait to move
		addSequential(new InitGearIntake());
		addSequential(new Wait(Robot.autoWaitTime::getValue));

		addSequential(new SetEncoderPID(0.1, 0, 0));
		addSequential(new EncoderPlaceGear(59, driveBackDistance));
		addSequential(new ResetEncoderPID());

	}

	public ScoreMiddleGearEncoders() {
		this(-40);
	}

}