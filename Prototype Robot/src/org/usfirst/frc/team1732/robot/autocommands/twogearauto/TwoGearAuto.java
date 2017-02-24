package org.usfirst.frc.team1732.robot.autocommands.twogearauto;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.helpercommands.CommandSwitcher;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearAuto extends CommandGroup {

	public TwoGearAuto() {
		// clears total distance
		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		DoubleSupplier leftSetpoint = () -> Robot.getDistanceToDriveBackForTwoGear()
				- Robot.driveTrain.getTotalLeftDistance();
		DoubleSupplier rightSetpoint = () -> Robot.getDistanceToDriveBackForTwoGear()
				- Robot.driveTrain.getTotalRightDistance();
		addSequential(new VisionPlaceGear(leftSetpoint, rightSetpoint));

		// turns to face the gear on ground
		double angle = -90;
		TurnWithGyro ifRedTurn1 = new TurnWithGyro(angle);
		TurnWithGyro ifBlueTurn1 = new TurnWithGyro(-angle);
		addSequential(new CommandSwitcher(Robot::isRedAlliance, ifRedTurn1, ifBlueTurn1));

		// drops gear intake
		// addSequential(new GearIntakeSetDown());
		// addSequenital(new GearIntakeSetIn());

		double distance = 30;
		// drives forward to pickup gear
		addSequential(new DriveEncoders(distance));
		// addSequential(new Wait(0.25)); uncomment this later if we have
		// trouble holding gear

		// raises gear intake
		// addSequential(new GearIntakeSetUp());
		// addSequenital(new GearIntakeSetStop());

		// drives back
		addSequential(new DriveEncoders(-distance));

		// turns to face gear peg
		double angle2 = 90;
		TurnWithGyro ifRedTurn2 = new TurnWithGyro(angle2);
		TurnWithGyro ifBlueTurn2 = new TurnWithGyro(-angle2);
		addSequential(new CommandSwitcher(Robot::isRedAlliance, ifRedTurn2, ifBlueTurn2));

		// scores second gear!!!
		addSequential(new VisionPlaceGear(-25));
	}
}
