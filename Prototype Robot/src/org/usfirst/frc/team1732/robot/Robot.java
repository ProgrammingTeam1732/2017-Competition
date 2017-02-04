
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.autocommands.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1732.robot.subsystems.GearIntake;
import org.usfirst.frc.team1732.robot.subsystems.LightRing;
import org.usfirst.frc.team1732.robot.subsystems.unused.BallIntake;
import org.usfirst.frc.team1732.robot.subsystems.unused.Climber;
import org.usfirst.frc.team1732.robot.subsystems.unused.Feeder;
import org.usfirst.frc.team1732.robot.subsystems.unused.Flywheel;
import org.usfirst.frc.team1732.robot.subsystems.unused.OtherShooter;
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

	public static OI			oi;
	public static DriveTrain	driveTrain;
	public static BallIntake	ballIntake;
	public static Climber		climber;
	public static Feeder		feeder;
	public static Flywheel		flywheel;
	public static GearIntake	gearIntake;
	public static OtherShooter	otherShooter;

	public static VisionMain					visionMain;
	public static MySmartDashboard				dashboard;
	public static SmartDashboardItem<Double>	distanceSetpointReciever;
	public static SmartDashboardItem<Double>	lightRingBrightness;
	public static LightRing						lightRing;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// NetworkTable.globalDeleteAll();
		driveTrain = new DriveTrain();
		// lightRing = new LightRing();
		// ballIntake = new BallIntake();
		// climber = new Climber();
		// feeder = new Feeder();
		// flywheel = new Flywheel();
		gearIntake = new GearIntake();
		// otherShooter = new OtherShooter();
		oi = new OI();
		visionMain = new VisionMain();

		// Dashboard code
		dashboard = new MySmartDashboard();
		// Senders
		dashboard = new MySmartDashboard();
		dashboard.addItem(SmartDashboardItem.newDoubleSender(	"Left Encoder Raw Counts",
																driveTrain::getLeftEncoderRawCount));
		dashboard.addItem(SmartDashboardItem.newDoubleSender(	"Right Encoder Raw Counts",
																driveTrain::getRightEncoderRawCount));
		dashboard.addItem(SmartDashboardItem.newDoubleSender("Left Encoder Counts", driveTrain::getLeftEncoderCount));
		dashboard.addItem(SmartDashboardItem.newDoubleSender("Right Encoder Counts", driveTrain::getRightEncoderCount));
		dashboard.addItem(SmartDashboardItem.newDoubleSender(	"Left Encoder Distance",
																driveTrain::getLeftEncoderDistance));
		dashboard.addItem(SmartDashboardItem.newDoubleSender(	"Right Encoder Distance",
																driveTrain::getRightEncoderDistance));
		dashboard.addItem(SmartDashboardItem.newDoubleSender(	"Left Encoder Setpoint",
																driveTrain::getLeftEncoderSetpoint));
		dashboard.addItem(SmartDashboardItem.newDoubleSender(	"Right Encoder Setpoint",
																driveTrain::getRightEncoderSetpoint));
		dashboard.addItem(SmartDashboardItem.newDoubleSender("Right Error", driveTrain::getRightEncoderError));
		dashboard.addItem(SmartDashboardItem.newDoubleSender("Left Error", driveTrain::getRightEncoderError));
		dashboard.addItem(SmartDashboardItem.newDoubleSender("Inches to gear peg", visionMain::getInchesToGearPeg));
		dashboard.addItem(SmartDashboardItem.newDoubleSender("Vision Angle", visionMain::getAngleToGearPeg));
		dashboard.addItem(SmartDashboardItem.newBooleanSender("At angle setpoint", driveTrain::isAtVisionSetpoint));
		dashboard.addItem(SmartDashboardItem.newBooleanSender(	"At left encoder setpoint?",
																driveTrain::isAtLeftEncoderSetpoint));
		dashboard.addItem(SmartDashboardItem.newBooleanSender(	"At right encoder setpoint?",
																driveTrain::isAtRightEncoderSetpoint));
		dashboard.addItem(SmartDashboardItem.newDoubleSender(	"Vision PID Angle Output",
																driveTrain::getVisionControllerOutput));
		// dashboard.addItem(SmartDashboardItem.newDoubleSender("Light Ring
		// Brighness", lightRing::getBrightness));
		// Receivers
		// distanceSetpointReciever = dashboard.addItem(SmartDashboardItem
		// .newDoubleReciever( "Vision distance setpoint",
		// DriveWithVision.DEFAULT_TARGET_INCHES,
		// DriveWithVision::setSmartDashboardDistance));
		// lightRingBrightness = dashboard
		// .addItem(SmartDashboardItem.newDoubleReciever("Light Ring
		// Brightness", .5, lightRing::setBrightness));

		// Init
		dashboard.init();
	}

	@Override
	public void robotPeriodic() {
		visionMain.run();
		dashboard.run();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		Scheduler.getInstance().add(new VisionPlaceGear());
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		// System.out.println("running");
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
