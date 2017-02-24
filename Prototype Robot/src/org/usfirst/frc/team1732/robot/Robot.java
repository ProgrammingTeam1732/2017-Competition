
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.GrabBallsBackwardAndShoot;
import org.usfirst.frc.team1732.robot.autocommands.scoreballsthengear.ScoreBallsThenGear;
import org.usfirst.frc.team1732.robot.autocommands.scoregearthenballs.ScoreGearThenBalls;
import org.usfirst.frc.team1732.robot.autocommands.scoresidegear.ScoreSideGearLeft;
import org.usfirst.frc.team1732.robot.autocommands.scoresidegear.ScoreSideGearRight;
import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftHigh;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftLow;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetStorageIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetStorageOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.individual.MotorLTBackForward;
import org.usfirst.frc.team1732.robot.commands.individual.MotorLTBackReverse;
import org.usfirst.frc.team1732.robot.commands.individual.MotorLTBackStop;
import org.usfirst.frc.team1732.robot.commands.individual.MotorLTBottomForward;
import org.usfirst.frc.team1732.robot.commands.individual.MotorLTBottomReverse;
import org.usfirst.frc.team1732.robot.commands.individual.MotorLTBottomStop;
import org.usfirst.frc.team1732.robot.commands.individual.MotorLTFrontForward;
import org.usfirst.frc.team1732.robot.commands.individual.MotorLTFrontReverse;
import org.usfirst.frc.team1732.robot.commands.individual.MotorLTFrontStop;
import org.usfirst.frc.team1732.robot.commands.individual.MotorRTBackForward;
import org.usfirst.frc.team1732.robot.commands.individual.MotorRTBackReverse;
import org.usfirst.frc.team1732.robot.commands.individual.MotorRTBackStop;
import org.usfirst.frc.team1732.robot.commands.individual.MotorRTBottomForward;
import org.usfirst.frc.team1732.robot.commands.individual.MotorRTBottomReverse;
import org.usfirst.frc.team1732.robot.commands.individual.MotorRTBottomStop;
import org.usfirst.frc.team1732.robot.commands.individual.MotorRTFrontForward;
import org.usfirst.frc.team1732.robot.commands.individual.MotorRTFrontReverse;
import org.usfirst.frc.team1732.robot.commands.individual.MotorRTFrontStop;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1732.robot.subsystems.GearIntake;
import org.usfirst.frc.team1732.robot.vision.VisionMain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory. - written by WPIlib people
 */
public class Robot extends IterativeRobot {

	public static OI			oi;
	public static DriveTrain	driveTrain;
	public static GearIntake	gearIntake;

	public static VisionMain		visionMain;
	private static MySmartDashboard	dashboard;

	private static SendableChooser<Command>		autoChooser;
	private static SmartDashboardItem<Boolean>	isRedAlliance;
	private static SmartDashboardItem<Double>	distanceToDriveBackForTwoGear;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code. - written by WPIlib people
	 */
	@Override
	public void robotInit() {
		driveTrain = new DriveTrain();
		gearIntake = new GearIntake();

		oi = new OI();
		visionMain = new VisionMain();

		// Smartdashboard code
		dashboard = new MySmartDashboard();
		// Add items to smartdashboard
		driveTrain.addToSmartDashboard(dashboard);
		gearIntake.addToSmartDashboard(dashboard);
		visionMain.addToSmartDashboard(dashboard);

		dashboard.addItem(SmartDashboardItem.newNumberSender("robotPeriodic() frequency ms", this::getFrequency));

		addTestingToSmartDashbaord();
		addAutonomousToSmartDashboard();

		// Initialize smartdashboard
		dashboard.init();
	}

	@Override
	public void robotPeriodic() {
		visionMain.run(); // FIXME eventually just move this into the vision
							// commands maybe so that if the camera breaks it
							// doesn't interfere with non-camera auto modes and
							// teleop mode
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
		Scheduler.getInstance().removeAll();
		Scheduler.getInstance().add(autoChooser.getSelected());
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		Scheduler.getInstance().removeAll(); // Cancels commands
		Robot.driveTrain.clearEncoderIntgral();
		Robot.driveTrain.clearGyroIntgral();

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

	/**
	 * Gets if the alliance color is red
	 * 
	 * @return if the current alliance is red
	 */
	public static boolean isRedAlliance() {
		return isRedAlliance.getValue();
	}

	public static double getDistanceToDriveBackForTwoGear() {
		return distanceToDriveBackForTwoGear.getValue();
	}

	private void addAutonomousToSmartDashboard() {
		// Command related SmartDashboardItems
		isRedAlliance = dashboard.addItem(SmartDashboardItem
				.newBooleanSender(	"Is Red Alliance?",
									() -> DriverStation.getInstance().getAlliance().equals(Alliance.Red)));

		distanceToDriveBackForTwoGear = dashboard
				.addItem(SmartDashboardItem.newDoubleReciever("Two Gear Auto: inches from wall to go to", 24.0));

		dashboard.addItem(SmartDashboardItem.newStringSender("Selected Auto Command", () -> {
			try {
				return autoChooser.getSelected().getName();
			} catch (Exception e) {
				return "null";
			}
		}));

		// Add auto chooser
		autoChooser = new SendableChooser<Command>();
		autoChooser.addDefault("Score Middle Gear", new VisionPlaceGear(-40));
		autoChooser.addObject("Score Right Side Gear", new ScoreSideGearRight());
		autoChooser.addObject("Score Left Side Gear", new ScoreSideGearLeft());
		autoChooser.addObject("Score Gear then Balls", new ScoreGearThenBalls());
		autoChooser.addObject("Score Balls then Gear", new ScoreBallsThenGear());
		autoChooser.addObject("Grab Balls then Shoot", new GrabBallsBackwardAndShoot());
		autoChooser.addObject("Grab Balls Backward then Shoot", new GrabBallsBackwardAndShoot());
		// autoChooser.addObject("Turn 90 degres", new TurnWithGyro(90));
		SmartDashboard.putData("AutonomousChooser", autoChooser);
	}

	private void addTestingToSmartDashbaord() {

		SmartDashboard.putData(new MotorLTBackForward());
		SmartDashboard.putData(new MotorLTBackReverse());
		SmartDashboard.putData(new MotorLTBackStop());

		SmartDashboard.putData(new MotorLTBottomForward());
		SmartDashboard.putData(new MotorLTBottomReverse());
		SmartDashboard.putData(new MotorLTBottomStop());

		SmartDashboard.putData(new MotorLTFrontForward());
		SmartDashboard.putData(new MotorLTFrontReverse());
		SmartDashboard.putData(new MotorLTFrontStop());

		SmartDashboard.putData(new MotorRTBackForward());
		SmartDashboard.putData(new MotorRTBackReverse());
		SmartDashboard.putData(new MotorRTBackStop());

		SmartDashboard.putData(new MotorRTBottomForward());
		SmartDashboard.putData(new MotorRTBottomReverse());
		SmartDashboard.putData(new MotorRTBottomStop());

		SmartDashboard.putData(new MotorRTFrontForward());
		SmartDashboard.putData(new MotorRTFrontReverse());
		SmartDashboard.putData(new MotorRTFrontStop());

		SmartDashboard.putData(new ShiftHigh());
		SmartDashboard.putData(new ShiftLow());

		SmartDashboard.putData(new GearIntakeSetUp());
		SmartDashboard.putData(new GearIntakeSetDown());

		SmartDashboard.putData(new GearIntakeSetStorageOut());
		SmartDashboard.putData(new GearIntakeSetStorageIn());
	}

	private long startTime = System.currentTimeMillis();

	private long getFrequency() {
		long start = startTime;
		startTime = System.currentTimeMillis();
		return startTime - start;
	}
}