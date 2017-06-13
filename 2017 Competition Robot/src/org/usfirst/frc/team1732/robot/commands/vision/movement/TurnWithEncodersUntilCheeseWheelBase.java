package org.usfirst.frc.team1732.robot.commands.vision.movement;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class TurnWithEncodersUntilCheeseWheelBase extends Command {

    private final DoubleSupplier angle;
    private boolean foundOnce = false;

    public TurnWithEncodersUntilCheeseWheelBase(DoubleSupplier angle) {
	this.angle = angle;
    }

    public TurnWithEncodersUntilCheeseWheelBase(double angle) {
	this(() -> angle);
	requires(Robot.driveTrain);
	requires(Robot.pixyCamera);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	System.out.println("Starting cheesewheel turn");
	// Robot.driveTrain.setEncoderPIDS(0.125, 0, 0);
	// Robot.driveTrain.setEncoderDeadband(3);
	Robot.driveTrain.resetEncoders();

	double setpoint = angle.getAsDouble() / 360.0 * DriveTrain.TURNING_CIRCUMFERENCE;
	Robot.driveTrain.setLeftEncoderSetpoint(setpoint);
	Robot.driveTrain.setRightEncoderSetpoint(-setpoint);
	Robot.driveTrain.setEncoderPIDS(p, 0, d);
	Robot.driveTrain.setEncoderDeadband(DriveTrain.ENCODER_TURNING_DEADBAND_INCHES);
	// Robot.driveTrain.driveRaw(0, 0);
	// Robot.driveTrain.setEncoderPIDS(0.125, 0, 0);
	// Robot.driveTrain.setEncoderDeadband(3);
	// Robot.driveTrain.resetEncoders();
	// double setpoint = angle.getAsDouble() / 360.0 *
	// Robot.driveTrain.TURNING_CIRCUMFERENCE;
	// Robot.driveTrain.setLeftEncoderSetpoint(setpoint);
	// Robot.driveTrain.setRightEncoderSetpoint(-setpoint);
	Robot.pixyCamera.turnOnLights();
	// this.setTimeout(5);
    }

    private double previousAngle;

    private final double izone = 6; // DriveTrain.ENCODER_IZONE_TURNING
    private final double p = 0.05;
    private final double i = 0.00;
    private final double d = 0.14;
    private final double minimumTurn = 7;

    private long lastOnTarget;
    private boolean lastOnTargetCheck = true;

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	visionMain.run();
	double averageDistance = (Math.abs(driveTrain.getRightDistance()) + Math.abs(driveTrain.getLeftDistance()))
		/ 2.0;
	if (visionMain.canSeeCheeseWheel() && averageDistance > minimumTurn) {
	    if (visionMain.isCheeseWheelPIDOnTarget() && lastOnTargetCheck) {
		lastOnTarget = System.currentTimeMillis();
		lastOnTargetCheck = false;
	    }
	    if (!visionMain.isCheeseWheelPIDOnTarget()) {
		lastOnTargetCheck = true;
	    }
	    foundOnce = true;
	    double angle = visionMain.getAngleToCheeseWheel();
	    System.out.println("Sees cheesewheel");
	    if (angle != previousAngle) {
		previousAngle = angle;
		double setpoint = (angle) / 360.0 * DriveTrain.TURNING_CIRCUMFERENCE;
		Robot.driveTrain.setLeftEncoderSetpoint(setpoint + Robot.driveTrain.getLeftDistance() + setpoint);
		Robot.driveTrain.setRightEncoderSetpoint(-setpoint + Robot.driveTrain.getRightDistance() - setpoint);
	    }
	}

	if (Math.abs(driveTrain.getLeftPIDError()) < izone || Math.abs(driveTrain.getRightPIDError()) < izone) {
	    driveTrain.setEncoderPIDS(p, i, d);
	} else {
	    driveTrain.setEncoderPIDS(p, 0, d);
	}

	double leftError = Robot.driveTrain.getLeftPIDError();
	double rightError = Robot.driveTrain.getRightPIDError();
	double leftRightAdjustment = (leftError + rightError) * DriveTrain.errorDifferenceScalar;

	double left = Robot.driveTrain.getLeftPIDOutput();
	double right = Robot.driveTrain.getRightPIDOutput();

	// left = left + leftRightAdjustment;
	// right = right + leftRightAdjustment;
	Robot.driveTrain.driveRaw(left, right);

	// double left = Robot.driveTrain.getLeftPIDOutput();
	// double right = Robot.driveTrain.getRightPIDOutput();
	// Robot.driveTrain.driveRaw(left, right);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	double averageDistance = (Math.abs(driveTrain.getRightDistance()) + Math.abs(driveTrain.getLeftDistance()))
		/ 2.0;
	return (foundOnce && visionMain.isCheeseWheelPIDOnTarget()
		&& (System.currentTimeMillis() - lastOnTarget > 100 && !lastOnTargetCheck))
		&& averageDistance > minimumTurn;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("Finished cheese wheel turn");
	Robot.driveTrain.driveRaw(0, 0);
	Robot.driveTrain.resetEncoderPIDValues();
	Robot.driveTrain.resetEncoderDeadband();
	Robot.pixyCamera.turnOffLights();
    }
}
