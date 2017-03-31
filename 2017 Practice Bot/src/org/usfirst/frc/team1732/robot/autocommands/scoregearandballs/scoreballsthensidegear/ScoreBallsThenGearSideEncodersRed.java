package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoreballsthensidegear;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.gyro.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.vision.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreBallsThenGearSideEncodersRed extends CommandGroup {

	public ScoreBallsThenGearSideEncodersRed() {
		addSequential(new InitGearIntake());

		// drive forward slightly
		addSequential(new DriveEncoders(ScoreBallsThenGearSideData.DRIVE_1_SETPOINT));

		// turn into boiler
		addSequential(new DriveGyro(ScoreBallsThenGearSideData.TURN_1_ANGLE_RED,
									ScoreBallsThenGearSideData.TURN_1_LEFT_SPEED_RED,
									ScoreBallsThenGearSideData.TURN_1_RIGHT_SPEED_RED));

		// insert shooting commands
		//		addSequential(new Wait(ScoreBallsThenGearSideData.SHOOT_TIME));
		addSequential(new ShootTime(ScoreBallsThenGearData.SHOOT_TIME));

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		// drive backwards slightly to avoid knocking wall
		addSequential(new DriveEncoders(ScoreBallsThenGearSideData.DRIVE_2_SETPOINT));

		// turn to face peg
		addSequential(new DriveGyro(ScoreBallsThenGearSideData.TURN_2_ANGLE_RED,
									ScoreBallsThenGearSideData.TURN_2_LEFT_SPEED_RED,
									ScoreBallsThenGearSideData.TURN_2_RIGHT_SPEED_RED));

		// addSequential(new
		// DriveEncoders(ScoreBallsThenGearData.DRIVE_3_SETPOINT));
		// not sure what above line was supposed to do so I commented it out

		// drive back forward 40 inches after placing gear
		addSequential(new EncoderPlaceGear(ScoreBallsThenGearSideDataScoreBallsThenGearSideData.DRIVE_4_DRIVE_BACK_SETPOINT));
		addSequential(new VisionPlaceGear(	ScoreBallsThenGearSideData.DRIVE_4_DRIVE_BACK_SETPOINT,
											ScoreBallsThenGearSideData.MAX_SETPOINT, true));

		// drive to hoppers
		// addSequential(new DriveToHopperFromRightGearPeg());
	}
}
