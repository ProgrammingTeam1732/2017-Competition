
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.commands.DriveWithVision;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1732.robot.triggers.Triggers;
import org.usfirst.frc.team1732.robot.vision.VisionMain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static DriveTrain					driveTrain;
	public static VisionMain					visionMain;
	public static OI							oi;
	public static Triggers						triggers;
	public static MySmartDashboard				dashboard;
	public static SmartDashboardItem<Double>	distanceSetpointReciever;

	@Override
	public void robotInit() {
		driveTrain = new DriveTrain();
		oi = new OI();
		visionMain = new VisionMain();

		// Dashboard code
		// Senders
		dashboard = new MySmartDashboard();
		dashboard.addDoubleSender("Left Encoder Counts", () -> driveTrain.getLeftEncoderCount());
		dashboard.addDoubleSender("Right Encoder Counts", () -> driveTrain.getRightEncoderCount());
		dashboard.addDoubleSender("Left Encoder Distance", () -> driveTrain.getLeftEncoderDistance());
		dashboard.addDoubleSender("Right Encoder Distance", () -> driveTrain.getRightEncoderDistance());
		dashboard.addBooleanSender("At encoder setpoint?", () -> driveTrain.isAtEncoderSetpoint());
		// dashboard.addStringSender("Current Drive Train Command", () ->
		// driveTrain.getCurrentCommand().toString());
		dashboard.addDoubleSender("Left Encoder Setpoint", () -> driveTrain.getLeftEncoderSetpoint());
		dashboard.addDoubleSender("Right Encoder Setpoint", () -> driveTrain.getRightEncoderSetpoint());
		dashboard.addDoubleSender("Inches to gear peg", () -> visionMain.getInchesToGearPeg());
		// Receivers
		distanceSetpointReciever = dashboard.addDoubleReciever(	"Vision distance setpoint",
																DriveWithVision.DEFAULT_TARGET_INCHES);
		// dashboard.addDoubleSender("Vision Angle", () ->
		// visionMain.getAngleToGearPeg());
		dashboard.init();
	}

	@Override
	public void robotPeriodic() {
		dashboard.run();
		visionMain.run();
		SmartDashboard.putNumber("Angle to gear", visionMain.getAngleToGearPeg());
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		Scheduler.getInstance().add(new DriveWithVision(10));
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testInit() {

	}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

}
