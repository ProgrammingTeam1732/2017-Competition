package org.usfirst.frc.team1732.robot.autocommands.scoregear.sidetwogearauto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SideTwoGearAutoLeft extends CommandGroup {

	public SideTwoGearAutoLeft() {
		addSequential(new SideTwoGearAuto(true));
	}
}
