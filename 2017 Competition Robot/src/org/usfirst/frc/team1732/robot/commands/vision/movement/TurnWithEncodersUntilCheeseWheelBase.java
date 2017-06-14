package org.usfirst.frc.team1732.robot.commands.vision.movement;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.drivetrain.DriveTrain;
import org.usfirst.frc.team1732.robot.subsystems.drivetrain.PIDData;

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
	Robot.driveTrain.resetEncoders();

	double setpoint = angle.getAsDouble() / 360.0 * DriveTrain.TURNING_CIRCUMFERENCE;
	Robot.driveTrain.mainController.setSetpoint(setpoint, -setpoint);
	Robot.driveTrain.mainController.setPIDValues(pidValues);
	Robot.pixyCamera.turnOnLights();
	// this.setTimeout(5);
    }

    private double previousAngle;

    private final PIDData pidValues = new PIDData(0.05, 0, 0.14, 0, 6, driveTrain.turningPID.deadband, 1, -1);
    private final double minimumTurn = 7;

    private long lastOnTarget;
    private boolean lastOnTargetCheck = true;

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	visionMain.run();
	double averageDistance = (Math.abs(driveTrain.leftEncoder.getDistance())
		+ Math.abs(driveTrain.rightEncoder.getDistance())) / 2.0;
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
		double leftSet = setpoint + Robot.driveTrain.leftEncoder.getDistance() + setpoint;
		double rightSet = -setpoint + Robot.driveTrain.rightEncoder.getDistance() - setpoint;
		Robot.driveTrain.mainController.adjustSetpoint(leftSet, rightSet);
	    }
	}

	double leftError = Robot.driveTrain.mainController.left.getError();
	double rightError = Robot.driveTrain.mainController.right.getError();
	double leftRightAdjustment = (leftError + rightError) * DriveTrain.ERROR_DIFFERENCE_SCALAR;

	double left = Robot.driveTrain.mainController.left.getOutput();
	double right = Robot.driveTrain.mainController.right.getOutput();

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
	double averageDistance = (Math.abs(driveTrain.leftEncoder.getDistance())
		+ Math.abs(driveTrain.rightEncoder.getDistance())) / 2.0;
	return (foundOnce && visionMain.isCheeseWheelPIDOnTarget()
		&& (System.currentTimeMillis() - lastOnTarget > 100 && !lastOnTargetCheck))
		&& averageDistance > minimumTurn;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("Finished cheese wheel turn");
	Robot.driveTrain.driveRaw(0, 0);
	Robot.driveTrain.mainController.resetPIDValues();
	Robot.pixyCamera.turnOffLights();
    }
}
