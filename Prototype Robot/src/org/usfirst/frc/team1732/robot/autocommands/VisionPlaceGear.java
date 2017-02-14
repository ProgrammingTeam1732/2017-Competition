package org.usfirst.frc.team1732.robot.autocommands;

import org.usfirst.frc.team1732.robot.commands.Wait;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
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
		addSequential(new Wait(0.1));
		addSequential(new GearIntakeSetDown());
		addParallel(new CommandGroup() {
			{
				addSequential(new GearIntakeOutTime(1));
				addSequential(new GearIntakeSetUp());
			}
		});
		addSequential(new DriveEncoders(-40, -40));
	}
}
