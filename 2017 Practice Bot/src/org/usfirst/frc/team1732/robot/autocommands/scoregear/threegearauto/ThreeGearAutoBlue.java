package org.usfirst.frc.team1732.robot.autocommands.scoregear.threegearauto;

import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.gyro.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetUpTimedIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GrabGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.vision.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ThreeGearAutoBlue extends CommandGroup {

	public ThreeGearAutoBlue() {
		addSequential(new InitGearIntake());

		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		addSequential(new VisionPlaceGear(	ThreeGearAutoData.DRIVE_1_LEFT_SETPOINT,
											ThreeGearAutoData.DRIVE_1_RIGHT_SETPOINT, ThreeGearAutoData.MAX_SETPOINT,
											true));

		// turns to face the gear on ground
		// addSequential(new TurnWithGyro(ThreeGearAutoData.TURN_1_ANGLE_BLUE));
		addSequential(new TurnWithGyro(ThreeGearAutoData.TURN_1_ANGLE_BLUE));

		addSequential(new ClearTotalDistance());

		addSequential(new SetMotorSpeed(ThreeGearAutoData.DRIVE_2_SPEED, ThreeGearAutoData.DRIVE_2_SPEED));

		// drops gear intake
		addSequential(new GrabGear(ThreeGearAutoData.GRAB_GEAR_USE_TIMEOUT, ThreeGearAutoData.GRAB_GEAR_TIMEOUT));

		addSequential(new SetMotorSpeed(ThreeGearAutoData.DRIVE_2_STOP, ThreeGearAutoData.DRIVE_2_STOP));

		// raises gear intake
		addParallel(new CommandGroup() {
			{
				addSequential(new GearIntakeSetUpTimedIn(1));
				addSequential(new GearIntakeSetStop());
			}
		});

		// drives back
		addSequential(new DriveEncodersGetSetpointAtRuntime(ThreeGearAutoData.DRIVE_3_LEFT_SETPOINT,
															ThreeGearAutoData.DRIVE_3_RIGHT_SETPOINT));

		// turns to face gear peg
		// addSequential(new TurnWithGyro(ThreeGearAutoData.TURN_2_ANGLE_BLUE));
		addSequential(new TurnWithGyro(ThreeGearAutoData.TURN_2_ANGLE_BLUE));

		addSequential(new ClearTotalDistance());

		// scores second gear!!!
		addSequential(new VisionPlaceGear(	ThreeGearAutoData.DRIVE_1_LEFT_SETPOINT,
											ThreeGearAutoData.DRIVE_1_RIGHT_SETPOINT, ThreeGearAutoData.MAX_SETPOINT,
											true));

		// turns to face the gear on ground
		// addSequential(new TurnWithGyro(ThreeGearAutoData.TURN_1_ANGLE_BLUE));
		addSequential(new TurnWithGyro(ThreeGearAutoData.TURN_2_ANGLE_BLUE));

		addSequential(new ClearTotalDistance());

		addSequential(new SetMotorSpeed(ThreeGearAutoData.DRIVE_2_SPEED, ThreeGearAutoData.DRIVE_2_SPEED));

		// drops gear intake
		addSequential(new GrabGear(ThreeGearAutoData.GRAB_GEAR_USE_TIMEOUT, ThreeGearAutoData.GRAB_GEAR_TIMEOUT));

		addSequential(new SetMotorSpeed(ThreeGearAutoData.DRIVE_2_STOP, ThreeGearAutoData.DRIVE_2_STOP));

		// raises gear intake
		addParallel(new CommandGroup() {
			{
				addSequential(new GearIntakeSetUpTimedIn(1));
				addSequential(new GearIntakeSetStop());
			}
		});

		// drives back
		addSequential(new DriveEncodersGetSetpointAtRuntime(ThreeGearAutoData.DRIVE_3_LEFT_SETPOINT,
															ThreeGearAutoData.DRIVE_3_RIGHT_SETPOINT));

		// turns to face gear peg
		// addSequential(new TurnWithGyro(ThreeGearAutoData.TURN_2_ANGLE_BLUE));
		addSequential(new TurnWithGyro(ThreeGearAutoData.TURN_1_ANGLE_BLUE));

		// scores third gear!!!
		addSequential(new VisionPlaceGear(	ThreeGearAutoData.DRIVE_4_DRIVE_BACK_SETPOINT, ThreeGearAutoData.MAX_SETPOINT,
											true));
	}
}
