package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.placegear.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.placegear.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreMiddleGear extends CommandGroup {

	public ScoreMiddleGear(boolean useVision) {
		this(useVision, -40);
	}

	public ScoreMiddleGear(boolean useVision, double driveBackDistance) {
		addSequential(new InitGearIntake());

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));
		if (useVision) {
			addSequential(new VisionPlaceGear(driveBackDistance, 80, false));
		} else {
			addSequential(new EncoderPlaceGear(59, driveBackDistance));
		}
	}

}