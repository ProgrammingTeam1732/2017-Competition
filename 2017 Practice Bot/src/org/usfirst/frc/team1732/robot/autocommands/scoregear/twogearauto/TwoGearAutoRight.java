package org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearauto;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetUpTimedIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GrabGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.vision.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearAutoRight extends CommandGroup {

	public TwoGearAutoRight() {
		addSequential(new InitGearIntake());

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime::getValue));

		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		addSequential(new VisionPlaceGear(	TwoGearAutoData.DRIVE_1_LEFT_SETPOINT, TwoGearAutoData.DRIVE_1_RIGHT_SETPOINT,
											TwoGearAutoData.MAX_SETPOINT, true));

		// turns to face the gear on ground
		addSequential(new TurnWithEncoders(TwoGearAutoData.TURN_1_ANGLE_RIGHT));

		addSequential(new ClearTotalDistance());

		addSequential(new SetMotorSpeed(TwoGearAutoData.DRIVE_2_SPEED, TwoGearAutoData.DRIVE_2_SPEED));

		// drops gear intake
		addSequential(new GrabGear(TwoGearAutoData.GRAB_GEAR_USE_TIMEOUT, TwoGearAutoData.GRAB_GEAR_DISTANCE));

		addSequential(new SetMotorSpeed(TwoGearAutoData.DRIVE_2_STOP_SPEED, TwoGearAutoData.DRIVE_2_STOP_SPEED));

		// raises gear intake
		addParallel(new GearIntakeSetUpTimedIn(1));

		// drives back
		addSequential(new DriveEncodersGetSetpointAtRuntime(TwoGearAutoData.DRIVE_3_LEFT_SETPOINT,
															TwoGearAutoData.DRIVE_3_RIGHT_SETPOINT));

		// turns to face gear peg
		addSequential(new TurnWithEncoders(TwoGearAutoData.TURN_2_ANGLE_RIGHT));

		// scores second gear!!!
		addSequential(new VisionPlaceGear(	TwoGearAutoData.DRIVE_4_DRIVE_BACK_SETPOINT, TwoGearAutoData.MAX_SETPOINT,
											true));
	}
}
