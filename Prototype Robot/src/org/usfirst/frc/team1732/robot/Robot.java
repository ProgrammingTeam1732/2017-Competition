
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.autocommands.ScoreGearAnd10Balls;
import org.usfirst.frc.team1732.robot.commands.vision.DriveWithVision;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1732.robot.subsystems.Flywheel;
import org.usfirst.frc.team1732.robot.subsystems.GearIntake;
import org.usfirst.frc.team1732.robot.subsystems.unused.BallIntake;
import org.usfirst.frc.team1732.robot.subsystems.unused.Climber;
import org.usfirst.frc.team1732.robot.subsystems.unused.Feeder;
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

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		driveTrain = new DriveTrain();
		flywheel = new Flywheel();
		gearIntake = new GearIntake();
		oi = new OI();
		visionMain = new VisionMain();

		// Smartdashboard code
		dashboard = new MySmartDashboard();
		// Add items to smartdashboard
		driveTrain.addToSmartDashboard(dashboard);
		visionMain.addToSmartDashboard(dashboard);
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(	"Turning P Slope", DriveWithVision.slope,
																DriveWithVision::setSlope));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(	"Turning P Lower", DriveWithVision.lower,
																DriveWithVision::setLower));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(	"Turning P Upper", DriveWithVision.upper,
																DriveWithVision::setUpper));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(	"Turning P Middle", DriveWithVision.middle,
																DriveWithVision::setMiddle));
		// Initialize smartdashboard
		dashboard.init();
	}

	@Override
	public void robotPeriodic() {
		visionMain.run();
		dashboard.run();
	}

	@Override
	public void disabledInit() {}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		Scheduler.getInstance().removeAll(); // Cancels commands

		// Can use a SmartDashboard chooser to select auto command
		Scheduler.getInstance().add(new ScoreGearAnd10Balls());
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		Scheduler.getInstance().removeAll(); // Cancels commands
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testInit() {}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}