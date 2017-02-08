package org.usfirst.frc.team1732.robot.autocommands;

import org.usfirst.frc.team1732.robot.commands.drivetrain.Drive1DEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeOutTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.vision.DriveWithVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionPlaceGear extends CommandGroup {

	public VisionPlaceGear() {
		addSequential(new DriveWithVision(15));
		addSequential(new GearIntakeSetDown());
		addParallel(new GearIntakeOutTime(2));
		addSequential(new Drive1DEncoders(-40, -40));
		addSequential(new GearIntakeSetUp());
	}
}
