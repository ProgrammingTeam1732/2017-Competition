package org.usfirst.frc.team1732.robot.autocommands.scoregearthenballs;

import java.util.function.BooleanSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.scoresidegear.ScoreSideGearPart1;
import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.helpercommands.CommandSwitcher;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreGearThenBalls extends CommandGroup {

	public ScoreGearThenBalls() {
		// GEAR SCORING

		// Positions robot in front of gear peg
		BooleanSupplier isLeftSide = () -> !Robot.isRedAlliance();
		addSequential(new ScoreSideGearPart1(isLeftSide));
		// only using Part1 of ScoreSideGear because we want to drive back a
		// custom distance

		// places the gear, drives back
		double distanceStop = 100; // determine this with tape measure
		addSequential(new VisionPlaceGear(	() -> distanceStop - Robot.driveTrain.getLeftDistance(),
											() -> distanceStop - Robot.driveTrain.getRightDistance()));

		// BALL SCORING

		// turn to face boiler
		double angle = -146;
		addSequential(new CommandSwitcher(Robot::isRedAlliance, new TurnWithGyro(angle), new TurnWithGyro(-angle)));

		// drive fast for part of distance
		addSequential(new DriveUntilEncoders(65, 0.7, 0.7, false));

		// use PID for rest of distance
		addSequential(new DriveEncoders(45));

		// shooting commands
		// addSequential(new ShootTime(5));
	}
}
