package org.usfirst.frc.team1732.robot.autocommands.twogearauto;

import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetUpTimedIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GrabGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearAutoBlue extends CommandGroup {

	public TwoGearAutoBlue() {
		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		addSequential(new VisionPlaceGear(	TwoGearAutoData.DRIVE_1_LEFT_SETPOINT,
											TwoGearAutoData.DRIVE_1_RIGHT_SETPOINT));

		// turns to face the gear on ground
		addSequential(new TurnWithGyro(TwoGearAutoData.TURN_1_ANGLE_BLUE));

		addSequential(new ClearTotalDistance());

		addSequential(new SetMotorSpeed(TwoGearAutoData.DRIVE_2_SPEED, TwoGearAutoData.DRIVE_2_SPEED));

		// drops gear intake
		addSequential(new GrabGear(TwoGearAutoData.GRAB_GEAR_TIMEOUT, TwoGearAutoData.GRAB_GEAR_USE_TIMEOUT));

		addSequential(new SetMotorSpeed(TwoGearAutoData.DRIVE_2_STOP, TwoGearAutoData.DRIVE_2_STOP));

		// raises gear intake
		addParallel(new CommandGroup() {
			{
				addSequential(new GearIntakeSetUpTimedIn(1));
				addSequential(new GearIntakeSetStop());
			}
		});

		// drives back
		addSequential(new DriveEncodersGetSetpointAtRuntime(TwoGearAutoData.DRIVE_3_LEFT_SETPOINT,
															TwoGearAutoData.DRIVE_3_RIGHT_SETPOINT));

		// turns to face gear peg
		addSequential(new TurnWithGyro(TwoGearAutoData.TURN_2_ANGLE_BLUE));

		// scores second gear!!!
		addSequential(new VisionPlaceGear(TwoGearAutoData.DRIVE_4_DRIVE_BACK_SETPOINT));
	}
}
