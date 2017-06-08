package org.usfirst.frc.team1732.robot.autocommands.scoregear.sidetwogearauto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SideTwoGearAutoRight extends CommandGroup {

	public SideTwoGearAutoRight() {
		addSequential(new SideTwoGearAuto(false));
	}
}
