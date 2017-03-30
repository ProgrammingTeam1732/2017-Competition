package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear;

import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.EncoderPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreMiddleGearEncoders extends CommandGroup {

	public ScoreMiddleGearEncoders() {
		addSequential(new EncoderPlaceGear(70, -40));
	}

}