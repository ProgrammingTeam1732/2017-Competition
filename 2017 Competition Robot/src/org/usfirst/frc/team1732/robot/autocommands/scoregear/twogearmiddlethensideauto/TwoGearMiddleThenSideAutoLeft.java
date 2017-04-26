package org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearmiddlethensideauto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearMiddleThenSideAutoLeft extends CommandGroup {

	public TwoGearMiddleThenSideAutoLeft() {
		addSequential(new TwoGearMiddleThenSideAuto(true));
	}
}
