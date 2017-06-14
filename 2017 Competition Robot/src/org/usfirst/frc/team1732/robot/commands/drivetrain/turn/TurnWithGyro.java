package org.usfirst.frc.team1732.robot.commands.drivetrain.turn;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnWithGyro extends Command {

    private static int id = 0;

    private final double setpoint;

    public TurnWithGyro(double degreesSetpoint) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot.driveTrain);
	setpoint = degreesSetpoint;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	System.out.println("Start TurnWithGyro " + id);
	driveTrain.resetGyro();
	driveTrain.gyroController.setSetpoint(setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	double output = driveTrain.gyroController.getOutput();

	double leftOutput = output;
	double rightOutput = -output;

	// double leftError = driveTrain.getLeftPIDError();
	// double rightError = driveTrain.getRightPIDError();
	// double leftRightAdjustment = (leftError + rightError) *
	// driveTrain.errorDifferenceScalar;
	// leftOutput = output - leftRightAdjustment;
	// rightOutput = output + leftRightAdjustment;

	driveTrain.driveRaw(leftOutput, rightOutput);
	System.out.println(output);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return Robot.driveTrain.gyroController.onTarget();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("End TurnWithGyro " + id);
	id++;
	Robot.driveTrain.driveRaw(0, 0);
    }
}
