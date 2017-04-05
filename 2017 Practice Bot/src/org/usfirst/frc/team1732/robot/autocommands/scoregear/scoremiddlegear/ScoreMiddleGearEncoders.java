package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreMiddleGearEncoders extends CommandGroup {

	public ScoreMiddleGearEncoders() {
		this(-40);
	}

	public ScoreMiddleGearEncoders(double driveBackDistance) {
		addSequential(new ScoreMiddleGear(false, driveBackDistance));
	}

}