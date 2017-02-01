
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
		dashboard.addItem(SmartDashboardItem.newDoubleSender("Left Encoder Counts", driveTrain::getLeftEncoderCount));
		dashboard.addItem(SmartDashboardItem.newDoubleSender("Right Encoder Counts", driveTrain::getRightEncoderCount));
		dashboard.addItem(SmartDashboardItem.newDoubleSender(	"Left Encoder Distance",
																driveTrain::getLeftEncoderDistance));
		dashboard.addItem(SmartDashboardItem.newDoubleSender(	"Right Encoder Distance",
																driveTrain::getRightEncoderDistance));
		dashboard.addItem(SmartDashboardItem.newBooleanSender("At encoder setpoint?", driveTrain::isAtEncoderSetpoint));
		dashboard.addItem(SmartDashboardItem.newDoubleSender(	"Left Encoder Setpoint",
																driveTrain::getLeftEncoderSetpoint));
		dashboard.addItem(SmartDashboardItem.newDoubleSender(	"Right Encoder Setpoint",
																driveTrain::getRightEncoderSetpoint));
		dashboard.addItem(SmartDashboardItem.newDoubleSender("Inches to gear peg", visionMain::getInchesToGearPeg));

		// Receivers
		distanceSetpointReciever = dashboard.addItem(SmartDashboardItem
				.newDoubleReciever(	"Vision distance setpoint", DriveWithVision.DEFAULT_TARGET_INCHES,
									DriveWithVision::setSmartDashboardDistance));
		dashboard.addItem(SmartDashboardItem.newDoubleSender("Vision Angle", visionMain::getAngleToGearPeg));
		dashboard.init();
	}

	@Override
	public void robotPeriodic() {
		dashboard.run();
		visionMain.run();
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
		Scheduler.getInstance().add(DriveWithVision.newCommandUseSmartDashboardDistance());
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
