
package org.usfirst.frc.team1732.robot;

import java.util.ArrayList;

import org.usfirst.frc.team1732.robot.commands.DriveWithVision;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardElement;
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
public class Robot extends IterativeRobot implements SmartDashboardElement {

	public static DriveTrain	driveTrain;
	public static VisionMain	visionMain;
	public static OI			oi;
	public static Triggers		triggers;

	public static final ArrayList<SmartDashboardElement> smartDashboardElements = new ArrayList<SmartDashboardElement>();

	@Override
	public void robotInit() {
		driveTrain = new DriveTrain();
		oi = new OI();
		visionMain = new VisionMain();
		smartDashboardElements.add(driveTrain);
		// smartDashboardElements.add(Robot);
		for (SmartDashboardElement element : smartDashboardElements) {
			element.init();
		}
	}

	@Override
	public void robotPeriodic() {
		for (SmartDashboardElement element : smartDashboardElements) {
			element.sendData();
			element.recieveData();
		}
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
		Scheduler.getInstance()
				.add(new DriveWithVision(SmartDashboard.getNumber(targetDistanceString, targetDistanceInches)));
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

	public static final String	targetDistanceString	= "Target Distance";
	private static double		targetDistanceInches	= 100;

	@Override
	public void init() {
		SmartDashboard.putNumber(targetDistanceString, targetDistanceInches);
	}

	@Override
	public void sendData() {

	}

	@Override
	public void recieveData() {

	}
}
