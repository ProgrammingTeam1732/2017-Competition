package org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearauto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearAutoRight extends CommandGroup {

	public TwoGearAutoRight() {
		addSequential(new TwoGearAuto(false));
	}
}
