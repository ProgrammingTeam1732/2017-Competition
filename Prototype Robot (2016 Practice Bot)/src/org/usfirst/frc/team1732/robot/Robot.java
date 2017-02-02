
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1732.robot.subsystems.Flywheel;
import org.usfirst.frc.team1732.robot.subsystems.motors.Motor1;
import org.usfirst.frc.team1732.robot.subsystems.motors.Motor2;
import org.usfirst.frc.team1732.robot.subsystems.unused.BallIntake;
import org.usfirst.frc.team1732.robot.subsystems.unused.Climber;
import org.usfirst.frc.team1732.robot.subsystems.unused.Feeder;
import org.usfirst.frc.team1732.robot.subsystems.unused.GearIntake;
import org.usfirst.frc.team1732.robot.subsystems.unused.OtherShooter;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

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
	public static Motor1		motor1;
	public static Motor2		motor2;

	public static BallIntake	ballIntake;
	public static Climber		climber;
	public static Feeder		feeder;
	public static Flywheel		flywheel;
	public static GearIntake	gearIntake;
	public static OtherShooter	otherShooter;

	public static MySmartDashboard dashboard;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		NetworkTable.globalDeleteAll();
		driveTrain = new DriveTrain();
		// ballIntake = new BallIntake();
		// climber = new Climber();
		// feeder = new Feeder();
		flywheel = new Flywheel();
		// gearIntake = new GearIntake();
		// otherShooter = new OtherShooter();
		oi = new OI();
		// Dashboard code
		dashboard = new MySmartDashboard();
		// dashboard.addItem(SmartDashboardItem.newDoubleReciever("Flywheel P",
		// flywheel.getP(), flywheel::setP));
		// dashboard.addItem(SmartDashboardItem.newDoubleReciever("Flywheel I",
		// flywheel.getI(), flywheel::setI));
		// dashboard.addItem(SmartDashboardItem.newDoubleReciever("Flywheel D",
		// flywheel.getD(), flywheel::setD));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(	"Flywheel Setpoint", flywheel.getSetpoint(),
																flywheel::setSetpoint));
		dashboard.addItem(SmartDashboardItem.newDoubleSender("Flywheel Speed", flywheel::getSpeed));
		// dashboard.addItem(SmartDashboardItem.newDoubleReciever( "Set flywheel
		// motor speed", 0.0,
		// flywheel::setMotorSpeed));
		dashboard.addItem(SmartDashboardItem.newDoubleSender("Flywheel Output", flywheel::getMotorOutput));

		dashboard.init();
		// Thread t = new Thread() {
		// private long startTime = System.currentTimeMillis();
		// private int iterations = 0;
		//
		// @Override
		// public void run() {
		// System.out.printf("Time: %s, Iter: %d%n", "" +
		// (System.currentTimeMillis() - startTime), iterations++);
		// startTime = System.currentTimeMillis();
		// if (Robot.flywheel.getSpeed() < Robot.flywheel.getSetpoint()) {
		// Robot.flywheel.setMotorSpeed(Robot.flywheel.BANG_BANG_UPPER);
		// } else {
		// Robot.flywheel.setMotorSpeed(Robot.flywheel.BANG_BANG_FF);
		// }
		// System.out.printf( "Speed: %f, Output: %f%n%n",
		// Robot.flywheel.getSpeed(),
		// Robot.flywheel.getMotorOutput());
		// }
		// };
		// t.setDaemon(true);
		// t.start();
	}

	@Override
	public void robotPeriodic() {
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
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		System.out.println("Running");
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
