package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetSpeed;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetStop;
import org.usfirst.frc.team1732.robot.commands.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.ShuffleBallsWithWait;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GrabBallsKeylineAndShootBlue extends CommandGroup {

	public GrabBallsKeylineAndShootBlue() {
		addSequential(new InitGearIntake());
		// Drive Along Keyline To Hopper
		addSequential(new DriveEncoders(GrabBallsKeylineAndShootData.DRIVE_1_SETPOINT));
		// Turn to Face Hopper
		addSequential(new DriveTime(GrabBallsKeylineAndShootData.TURN_TO_HOPPER_TIME,
									GrabBallsKeylineAndShootData.TURN_TO_HOPPER_LEFT_SPEED_BLUE,
									GrabBallsKeylineAndShootData.TURN_TO_HOPPER_RIGHT_SPEED_BLUE));
		// addSequential(new DriveTime(1, .1, .4));
		// addSequential(new DriveTime(.5, .3, .3));
		addSequential(new FeederSetSpeed(GrabBallsKeylineAndShootData.CONVEYOR_SPEED));
		// Drive Into Hopper
		addSequential(new DriveTime(GrabBallsKeylineAndShootData.DRIVE_INTO_HOPPER_TIME,
									GrabBallsKeylineAndShootData.DRIVE_INTO_HOPPER_LEFT_SPEED,
									GrabBallsKeylineAndShootData.DRIVE_INTO_HOPPER_RIGHT_SPEED));
		// addSequential(new
		// DriveTime(GrabBallsKeylineAndShootData.DRIVE_3_SETPOINT, .4));
		// Wait to fill up balls
		addSequential(new Wait(GrabBallsKeylineAndShootData.WAIT_1_TIME));

		addSequential(new FeederSetStop());

		// Drive Backwards
		addSequential(new DriveTime(GrabBallsKeylineAndShootData.DRIVE_BACK_TIME,
									GrabBallsKeylineAndShootData.DRIVE_BACK_SPEED));
		// Turn to face boiler
		addSequential(new TurnWithEncoders(GrabBallsKeylineAndShootData.TURN_2_ANGLE_BLUE));
		addParallel(new EnableFlywheel());

		// Drive to boiler
		addSequential(new DriveEncoders(GrabBallsKeylineAndShootData.DRIVE_4_SETPOINT));

		// Turn to face boiler
		addSequential(new DriveTime(GrabBallsKeylineAndShootData.TURN_3_TIME_BLUE,
									GrabBallsKeylineAndShootData.DRIVE_LEFT_SPEED_BLUE,
									GrabBallsKeylineAndShootData.DRIVE_RIGHT_SPEED_BLUE));
		// addSequential(new DriveTime(.2, 1, .2));
		// Creep Forward while shooting
		addParallel(new DriveTime(	GrabBallsKeylineAndShootData.SHOOT_TIME,
									GrabBallsKeylineAndShootData.CREEP_TOWARD_BOILER_SPEED));
		// Shuffle balls while shooting
		addParallel(new CommandGroup() {
			{
				addSequential(new Wait(2));
				addSequential(new ShuffleBallsWithWait());
			}
		});

		addSequential(new ShootTime(GrabBallsKeylineAndShootData.SHOOT_TIME));
	}
}