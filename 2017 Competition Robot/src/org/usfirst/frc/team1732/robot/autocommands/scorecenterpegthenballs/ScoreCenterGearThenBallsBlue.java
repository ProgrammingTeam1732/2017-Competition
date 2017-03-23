package org.usfirst.frc.team1732.robot.autocommands.scorecenterpegthenballs;

import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.gyro.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreCenterGearThenBallsBlue extends CommandGroup {

	public ScoreCenterGearThenBallsBlue() {
		// addSequential(new CommandGroup() {
		// {
		// addParallel(new InitGearIntake());
		// addSequential(new VisionPlaceGear(
		// ScoreCenterGearThenBallsData.DRIVE_BACK_DISTANCE,
		// ScoreCenterGearThenBallsData.MAX_SETPOINT, false));
		// }
		// });
		addSequential(new InitGearIntake());
		addSequential(new VisionPlaceGear(	ScoreCenterGearThenBallsData.DRIVE_BACK_DISTANCE,
											ScoreCenterGearThenBallsData.MAX_SETPOINT, false));

		// turn to face boiler
		addSequential(new TurnWithGyro(ScoreCenterGearThenBallsData.TURN_1_ANGLE_BLUE));

		// drive fast for part of distance
		addSequential(new DriveUntilEncoders(	ScoreCenterGearThenBallsData.DRIVE_1_SETPOINT,
												ScoreCenterGearThenBallsData.DRIVE_1_LEFT_SPEED,
												ScoreCenterGearThenBallsData.DRIVE_1_RIGHT_SPEED,
												ScoreCenterGearThenBallsData.DRIVE_1_STOP_AT_END));

		// use PID for rest of distance
		addSequential(new DriveEncoders(ScoreCenterGearThenBallsData.DRIVE_2_SETPOINT));

		addSequential(new DriveEncoders(ScoreCenterGearThenBallsData.DRIVE_3_SETPOINT));

		// shooting commands
		// addSequential(new
		// ShootTime(ScoreCenterGearThenBallsData.SHOOT_TIME));

		// drive towards hoppers
		// addSequential(new DriveToHopperFromBoilerBlue());
	}
}
