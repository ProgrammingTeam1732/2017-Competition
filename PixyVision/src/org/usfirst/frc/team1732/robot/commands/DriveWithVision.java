package org.usfirst.frc.team1732.robot.commands;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveWithVision extends Command {

	public double targetDistanceInches;

	public static final double	DEFAULT_TARGET_INCHES	= 10;
	private static double		smartDashboardDistance	= DEFAULT_TARGET_INCHES;

	public DriveWithVision(double aTargetDistanceInches) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(driveTrain);
		targetDistanceInches = aTargetDistanceInches;
	}

	public static void setSmartDashboardDistance(double distance) {
		smartDashboardDistance = distance;
	}

	public static DriveWithVision newCommandUseSmartDashboardDistance() {
		return new DriveWithVision(smartDashboardDistance);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// driveTrain.setDriveWithEncoders();
		// driveTrain.setDriveWithVisionController();
		driveTrain.zeroEncoders();
		driveTrain.setLeftEncoderSetpointInches(0);
		driveTrain.setRightEncoderSetpointInches(0);
		driveTrain.setVisionSetpoint(0);
	}

	// private double lastGoodDistance;

	// public static final double MAXIMUM_DISTANCE_CHANGE = 0;

	// private boolean goodDistanceInitialized = false;

	// private int framesSinceGoodFrame = 0;
	// public static final int RESET_FRAME_NUMBER = 4;

	// Called repeatedly when this Command is scheduled to run
	// boolean visionControllerEnabled = true;
	// boolean encoderControllerEnabled = false;

	public static final long	MILLISECONDS_TO_WAIT	= 500;
	private long				endTime					= System.currentTimeMillis();
	private boolean				endTimeSet				= false;

	@Override
	protected void execute() {
		// framesSinceGoodFrame++;
		double angle = visionMain.getAngleToGearPeg();
		double distance = visionMain.getInchesToGearPeg();
		double dDistance = distance - targetDistanceInches;
		double leftSetpoint = dDistance + driveTrain.getLeftEncoderDistance();
		double rightSetPoint = dDistance + driveTrain.getRightEncoderDistance();

		driveTrain.setVisionAngle(angle);
		driveTrain.setLeftEncoderSetpointInches(leftSetpoint);
		// FIXME Right encoder broken, change to right later
		driveTrain.setRightEncoderSetpointInches(leftSetpoint);
		SmartDashboard.putBoolean("At angle setpoint", driveTrain.isAtVisionSetpoint());
		if (!driveTrain.isAtVisionSetpoint()) {
			endTimeSet = false;
			// if (!visionControllerEnabled) {
			// driveTrain.setDriveWithVisionController();
			// visionControllerEnabled = true;
			// encoderControllerEnabled = false;
			// }
			SmartDashboard.putNumber("Vision PID Output", driveTrain.getVisionControllerOutput());
			driveTrain.driveRaw(driveTrain.getVisionControllerOutput(), -driveTrain.getVisionControllerOutput());
			SmartDashboard.putBoolean("Is Correcting angle?", true);
		} else {
			if (!endTimeSet)
				endTime = System.currentTimeMillis();
			endTimeSet = true;
			if (distance != -1 && finishedWait()) {
				// if(!encoderControllerEnabled) {
				// driveTrain.setDriveWithEncoders();
				// visionControllerEnabled = false;
				// encoderControllerEnabled = true;
				// }
				driveTrain.driveRaw(-driveTrain.getLeftEncoderControllerOutput(),
									-driveTrain.getRightEncoderControllerOutput());
				SmartDashboard.putBoolean("Is Correcting angle?", false);
			}
		}
		// double distance = visionMain.getInchesToGearPeg();
		// System.out.println("Running auto vision: " + distance);
		// /*if ((!goodDistanceInitialized || Math.abs(distance -
		// lastGoodDistance) < MAXIMUM_DISTANCE_CHANGE)
		// distance != -1) {*/
		// //goodDistanceInitialized = true;
		// // framesSinceGoodFrame = 0;
		// //lastGoodDistance = distance;
		// if(distance!= -1) {
		// double dDistance = distance - targetDistanceInches;
		// // FIXME (both use left)
		// double leftEncoderDistance = driveTrain.getLeftEncoderDistance();
		// // not sure if this math is correct
		// driveTrain.setEncoderSetpointInches(dDistance + leftEncoderDistance,
		// dDistance + leftEncoderDistance);
		// }

	}

	public boolean finishedWait() {
		return System.currentTimeMillis() > endTime + MILLISECONDS_TO_WAIT;
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
		// return driveTrain.isAtEncoderSetpoint();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("end of vision");
		// driveTrain.setDriveManual();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
