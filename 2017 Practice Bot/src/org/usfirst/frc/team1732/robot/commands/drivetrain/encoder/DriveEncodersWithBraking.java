package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.commands.drivetrain.BrakeDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveEncodersWithBraking extends CommandGroup {

	public DriveEncodersWithBraking(double leftSetpoint, double rightSetpoint, double offset) {
		this(() -> leftSetpoint, () -> rightSetpoint, offset);
	}

	public DriveEncodersWithBraking(double setpoint, double offset) {
		this(setpoint, setpoint, offset);
	}

	public DriveEncodersWithBraking(DoubleSupplier leftSetpoint, DoubleSupplier rightSetpoint, double offset) {
		addSequential(new DriveEncodersGetSetpointAtRuntime(leftSetpoint, rightSetpoint, offset));
		addSequential(new BrakeDrive());
	}

	public DriveEncodersWithBraking(DoubleSupplier setpoint, double offset) {
		this(setpoint, setpoint, offset);
	}
}
