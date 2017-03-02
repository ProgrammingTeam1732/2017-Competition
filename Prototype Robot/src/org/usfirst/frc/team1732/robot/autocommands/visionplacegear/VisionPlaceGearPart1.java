package org.usfirst.frc.team1732.robot.autocommands.visionplacegear;

import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.vision.DriveWithVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class VisionPlaceGearPart1 extends CommandGroup {

	public VisionPlaceGearPart1() {
		addSequential(new DriveWithVision(10));
		addSequential(new Wait(0.1));
	}
}
