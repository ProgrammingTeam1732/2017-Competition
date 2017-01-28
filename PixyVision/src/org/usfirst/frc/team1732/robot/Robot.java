
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.commands.DriveWithVision;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardNumberReciever;
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
	public static SmartDashboardNumberReciever	distanceSetpointReciever;

	@Override
	public void robotInit() {
		driveTrain = new DriveTrain();
		oi = new OI();
		visionMain = new VisionMain();
		dashboard = new MySmartDashboard();
		dashboard.addNumberSender(	"Left Encoder Counts", driveTrain.getLeftEncoderCount(),
									() -> driveTrain.getLeftEncoderCount());
		dashboard.addNumberSender(	"Right Encoder Counts", driveTrain.getRightEncoderCount(),
									() -> driveTrain.getRightEncoderCount());
		dashboard.addNumberSender(	"Left Encoder Distance", driveTrain.getLeftEncoderDistance(),
									() -> driveTrain.getLeftEncoderDistance());
		dashboard.addNumberSender(	"Right Encoder Distance", driveTrain.getRightEncoderDistance(),
									() -> driveTrain.getRightEncoderDistance());
		dashboard.addBooleanSender(	"At encoder setpoint?", driveTrain.isAtEncoderSetpoint(),
									() -> driveTrain.isAtEncoderSetpoint());
		dashboard.addStringSender(	"Current Drive Train Command", driveTrain.getCurrentCommand().toString(),
									() -> driveTrain.getCurrentCommand().toString());
		dashboard.addNumberSender(	"Left Encoder Setpoint", driveTrain.getLeftEncoderSetpoint(),
									() -> driveTrain.getLeftEncoderSetpoint());
		dashboard.addNumberSender(	"Right Encoder Setpoint", driveTrain.getRightEncoderSetpoint(),
									() -> driveTrain.getRightEncoderSetpoint());
		dashboard.addNumberSender(	"Inches to gear peg", visionMain.getInchesToGearPeg(),
									() -> visionMain.getInchesToGearPeg());
		distanceSetpointReciever = dashboard.addNumberReciever(	"Vision distance setpoint",
																DriveWithVision.DEFAULT_TARGET_INCHES);
		dashboard.init();
	}

	@Override
	public void robotPeriodic() {
		dashboard.run();
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
		Scheduler.getInstance().add(new DriveWithVision(distanceSetpointReciever.getValue()));
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
