package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GrabBallsBackwardAndShootBlue extends CommandGroup {

	public GrabBallsBackwardAndShootBlue() {
		// drive backwards
		addSequential(new DriveEncoders(GrabBallsBackwardAndShootData.DRIVE_1_SETPOINT));

		// turn into hopper
		addSequential(new TurnWithGyro(GrabBallsBackwardAndShootData.TURN_1_ANGLE_BLUE));

		// drive forward slightly
		addSequential(new DriveEncoders(GrabBallsBackwardAndShootData.DRIVE_2_SETPOINT));

		// wait for balls to fall in
		addSequential(new Wait(GrabBallsBackwardAndShootData.WAIT_1_TIME));

		// drive forward into boiler
		addSequential(new DriveEncoders(GrabBallsBackwardAndShootData.DRIVE_3_SETPOINT));

		// turn into boiler
		// addSequential(new
		// TurnWithGyro(GrabBallsBackwardAndShootData.TURN_2_ANGLE_BLUE));
		// addSequential(new
		// DriveEncoders(GrabBallsBackwardAndShootData.DRIVE_4_SETPOINT));

		// shoot commands
		// addSequential(new
		// ShootTime(GrabBallsBackwardAndShootData.SHOOT_TIME));

		// drive towards hoppers
		// addSequential(new DriveToHopperFromBoilerBlue());
	}
}
