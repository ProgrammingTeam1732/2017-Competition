package org.usfirst.frc.team1732.robot.autocommands.scoremiddlegear;

import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreMiddleGear extends CommandGroup {

	public ScoreMiddleGear() {
		addSequential(new InitGearIntake());
		addSequential(new VisionPlaceGear(-40, 86, false));
	}

}