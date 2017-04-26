package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreMiddleGearVision extends CommandGroup {

	public ScoreMiddleGearVision() {
		this(-40);
	}

	public ScoreMiddleGearVision(double driveBackDistance) {
		addSequential(new ScoreMiddleGear(true, driveBackDistance));
	}

}