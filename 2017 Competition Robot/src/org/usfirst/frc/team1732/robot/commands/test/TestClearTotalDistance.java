package org.usfirst.frc.team1732.robot.commands.test;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.BrakeDrive;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersGetSetpointAtRuntime;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestClearTotalDistance extends CommandGroup {

    public TestClearTotalDistance() {
	addSequential(new ClearTotalDistance());
	addSequential(new DriveTime(3, 0.3));
	// addSequential(new DriveTime(1, 0));
	addSequential(new BrakeDrive());
	DoubleSupplier d = () -> {
	    double l = Robot.driveTrain.getTotalLeftDistance();
	    double r = Robot.driveTrain.getTotalRightDistance();
	    double distance = (l + r) / -2.0;
	    System.out.println("Left: " + l);
	    System.out.println("Right: " + r);
	    System.out.println("Back Distance: " + distance);
	    return distance;
	};
	addSequential(new DriveEncodersGetSetpointAtRuntime(d));
	addSequential(new BrakeDrive());
    }

}
