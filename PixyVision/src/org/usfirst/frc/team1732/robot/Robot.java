
package org.usfirst.frc.team1732.robot;

import java.util.ArrayList;

import org.usfirst.frc.team1732.robot.commands.DriveWithVision;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardElement;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1732.robot.triggers.Triggers;
import org.usfirst.frc.team1732.robot.vision.VisionMain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
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

	public static final ArrayList<SmartDashboardElement> smartDashboardElements = new ArrayList<SmartDashboardElement>();

	@Override
	public void robotInit() {
		driveTrain = new DriveTrain();
		oi = new OI();
		visionMain = new VisionMain();
		PowerDistributionPanel panel = new PowerDistributionPanel();
		panel.clearStickyFaults();
		smartDashboardElements.add(driveTrain);
		for (SmartDashboardElement element : smartDashboardElements) {
			element.init();
		}
	}

	@Override
	public void robotPeriodic() {
		visionMain.run();
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
		Scheduler.getInstance().add(new DriveWithVision());
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
