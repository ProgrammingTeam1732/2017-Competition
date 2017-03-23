package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.commands.drivetrain.BrakeDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveEncodersWithBraking extends CommandGroup {

	public DriveEncodersWithBraking(double leftSetpoint, double rightSetpoint) {
		this(() -> leftSetpoint, () -> rightSetpoint);
	}

	public DriveEncodersWithBraking(double setpoint) {
		this(setpoint, setpoint);
	}

	public DriveEncodersWithBraking(DoubleSupplier leftSetpoint, DoubleSupplier rightSetpoint) {
		addSequential(new DriveEncodersGetSetpointAtRuntime(leftSetpoint, rightSetpoint));
		addSequential(new BrakeDrive());
	}

	public DriveEncodersWithBraking(DoubleSupplier setpoint) {
		this(setpoint, setpoint);
	}
}
