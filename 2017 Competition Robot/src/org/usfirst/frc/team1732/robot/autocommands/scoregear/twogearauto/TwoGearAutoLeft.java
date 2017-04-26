package org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearauto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearAutoLeft extends CommandGroup {

	public TwoGearAutoLeft() {
		addSequential(new TwoGearAuto(true));
	}
}
