package org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearmiddlethensideauto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearMiddleThenSideAutoRight extends CommandGroup {

	public TwoGearMiddleThenSideAutoRight() {
		addSequential(new TwoGearMiddleThenSideAuto(false));
	}
}
