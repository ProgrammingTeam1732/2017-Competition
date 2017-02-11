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
		addSequential(new DriveWithVision(10));
		// addSequential(new TurnWithVision(0));
		// addSequential(new
		// Drive1DEncoders(Robot.visionMain.getInchesToGearPeg()));
		addSequential(new Wait(0.1));
		addParallel(new DriveEncoders(-40, -40));
		// addSequential(new Wait(0.0.));
		addSequential(new GearIntakeSetDown());
		addSequential(new GearIntakeOutTime(2));
		addSequential(new GearIntakeSetUp());
	}
}
