
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardReciever;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardSender;
import org.usfirst.frc.team1732.robot.subsystems.drivetrain.DriveTrain;
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

	public static DriveTrain	driveTrain;
	public static VisionMain	visionMain;
	public static OI			oi;
	public static Triggers		triggers;

	@Override
	public void robotInit() {
		driveTrain = new DriveTrain();
		oi = new OI();
		visionMain = new VisionMain();
	}

	public static final SmartDashboardSender[]		smartDashboardSenders	= new SmartDashboardSender[] { driveTrain };
	public static final SmartDashboardReciever[]	smartDashboardRecievers	= new SmartDashboardReciever[] {};

	@Override
	public void robotPeriodic() {
		for (SmartDashboardSender sender : smartDashboardSenders)
			sender.sendData();
		for (SmartDashboardReciever reciever : smartDashboardRecievers)
			reciever.recieveData();
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
