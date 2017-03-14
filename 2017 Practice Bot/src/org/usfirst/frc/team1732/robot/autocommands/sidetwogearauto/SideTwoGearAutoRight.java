package org.usfirst.frc.team1732.robot.autocommands.sidetwogearauto;

import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetUpTimedIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GrabGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SideTwoGearAutoRight extends CommandGroup {

	public SideTwoGearAutoRight() {
		addSequential(new InitGearIntake());

		addSequential(new DriveEncoders(SideTwoGearAutoData.DRIVE_1_SETPOINT));

		addSequential(new TurnWithGyro(SideTwoGearAutoData.TURN_1_RIGHT));

		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		addSequential(new VisionPlaceGear(	SideTwoGearAutoData.DRIVE_2_LEFT_SETPOINT,
											SideTwoGearAutoData.DRIVE_2_RIGHT_SETPOINT,
											SideTwoGearAutoData.MAX_SETPOINT));

		addSequential(new ClearTotalDistance());

		addSequential(new SetMotorSpeed(SideTwoGearAutoData.DRIVE_2_SPEED, SideTwoGearAutoData.DRIVE_2_SPEED));

		// drops gear intake
		addSequential(new GrabGear(SideTwoGearAutoData.GRAB_GEAR_USE_TIMEOUT, SideTwoGearAutoData.GRAB_GEAR_DISTANCE));

		addSequential(new SetMotorSpeed(SideTwoGearAutoData.DRIVE_2_STOP_SPEED,
										SideTwoGearAutoData.DRIVE_2_STOP_SPEED));

		// raises gear intake
		addParallel(new GearIntakeSetUpTimedIn(1));

		// drives back
		addSequential(new DriveEncodersGetSetpointAtRuntime(SideTwoGearAutoData.DRIVE_3_LEFT_SETPOINT,
															SideTwoGearAutoData.DRIVE_3_RIGHT_SETPOINT));

		// turns to face gear peg
		addSequential(new TurnWithGyro(SideTwoGearAutoData.TURN_3_ANGLE_RIGHT));

		// scores second gear!!!
		addSequential(new VisionPlaceGear(	SideTwoGearAutoData.DRIVE_4_DRIVE_BACK_SETPOINT,
											SideTwoGearAutoData.MAX_SETPOINT));
	}
}
