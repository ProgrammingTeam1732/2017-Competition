
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.autocommands.AutoChooser;
import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetOut;
import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetIn;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetOut;
import org.usfirst.frc.team1732.robot.commands.climber.ClimberSetStop;
import org.usfirst.frc.team1732.robot.commands.climber.ClimberSetUp;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftHigh;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftLow;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetIn;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetOut;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetStopperIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetStopperOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.individual.FlywheelForward;
import org.usfirst.frc.team1732.robot.commands.individual.FlywheelReverse;
import org.usfirst.frc.team1732.robot.commands.individual.FlywheelStop;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.BallIntake;
import org.usfirst.frc.team1732.robot.subsystems.Climber;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1732.robot.subsystems.Feeder;
import org.usfirst.frc.team1732.robot.subsystems.Flywheel;
import org.usfirst.frc.team1732.robot.subsystems.GearIntake;
import org.usfirst.frc.team1732.robot.vision.VisionMain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
	public static BallIntake	ballIntake;
	public static Climber		climber;
	public static Feeder		feeder;
	public static Flywheel		flywheel;
	public static GearIntake	gearIntake;
	public static Arm			arm;

	public static VisionMain		visionMain;
	private static MySmartDashboard	dashboard;

	private static AutoChooser					autoChooser;
	private static SmartDashboardItem<Boolean>	isRedAlliance;
	private static SmartDashboardItem<Double>	distanceToDriveBackForTwoGear;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code. - written by WPIlib people
	 */
	@Override
	public void robotInit() {
		// initialize subsystems - always do this first
		driveTrain = new DriveTrain();
		flywheel = new Flywheel();
		ballIntake = new BallIntake();
		climber = new Climber();
		feeder = new Feeder();
		arm = new Arm();
		gearIntake = new GearIntake();

		oi = new OI();
		visionMain = new VisionMain();

		// Smartdashboard code
		dashboard = new MySmartDashboard();
		// Add items to smartdashboard
		addSubsystemsToSmartDashboard();
		addAutonomousToSmartDashboard();
		addTestingToSmartDashbaord();
		dashboard.addItem(SmartDashboardItem.newNumberSender("robotPeriodic() frequency ms", this::getFrequency));

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
		autoChooser.getSelected().start();
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

	private void addSubsystemsToSmartDashboard() {
		driveTrain.addToSmartDashboard(dashboard);
		flywheel.addToSmartDashboard(dashboard);
		ballIntake.addToSmartDashboard(dashboard);
		climber.addToSmartDashboard(dashboard);
		feeder.addToSmartDashboard(dashboard);
		arm.addToSmartDashboard(dashboard);
		gearIntake.addToSmartDashboard(dashboard);
		visionMain.addToSmartDashboard(dashboard);
	}

	public static boolean isRedAlliance() {
		return isRedAlliance.getValue();
	}

	public static double getDistanceToDriveBackForTwoGear() {
		return distanceToDriveBackForTwoGear.getValue();
	}

	private void addAutonomousToSmartDashboard() {
		autoChooser = new AutoChooser();

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
	}

	private void addTestingToSmartDashbaord() {
		SmartDashboard.putData(new FlywheelForward());
		SmartDashboard.putData(new FlywheelReverse());
		SmartDashboard.putData(new FlywheelStop());

		// SmartDashboard.putData(new MotorLTBackForward());
		// SmartDashboard.putData(new MotorLTBackReverse());
		// SmartDashboard.putData(new MotorLTBackStop());
		//
		// SmartDashboard.putData(new MotorLTBottomForward());
		// SmartDashboard.putData(new MotorLTBottomReverse());
		// SmartDashboard.putData(new MotorLTBottomStop());
		//
		// SmartDashboard.putData(new MotorLTFrontForward());
		// SmartDashboard.putData(new MotorLTFrontReverse());
		// SmartDashboard.putData(new MotorLTFrontStop());
		//
		// SmartDashboard.putData(new MotorRTBackForward());
		// SmartDashboard.putData(new MotorRTBackReverse());
		// SmartDashboard.putData(new MotorRTBackStop());
		//
		// SmartDashboard.putData(new MotorRTBottomForward());
		// SmartDashboard.putData(new MotorRTBottomReverse());
		// SmartDashboard.putData(new MotorRTBottomStop());
		//
		// SmartDashboard.putData(new MotorRTFrontForward());
		// SmartDashboard.putData(new MotorRTFrontReverse());
		// SmartDashboard.putData(new MotorRTFrontStop());

		SmartDashboard.putData(new ArmSetOut());
		SmartDashboard.putData(new ArmSetIn());

		SmartDashboard.putData(new BallIntakeSetDown());
		SmartDashboard.putData(new BallIntakeSetUp());

		SmartDashboard.putData(new ShiftHigh());
		SmartDashboard.putData(new ShiftLow());

		SmartDashboard.putData(new GearIntakeSetUp());
		SmartDashboard.putData(new GearIntakeSetDown());

		SmartDashboard.putData(new GearIntakeSetStopperOut());
		SmartDashboard.putData(new GearIntakeSetStopperIn());

		SmartDashboard.putData(new FeederSetIn());
		SmartDashboard.putData(new FeederSetOut());
		SmartDashboard.putData(new FeederSetStop());

		SmartDashboard.putData(new ClimberSetStop());
		SmartDashboard.putData(new ClimberSetUp());

		SmartDashboard.putData(new BallIntakeSetOut());
		SmartDashboard.putData(new BallIntakeSetIn());
		SmartDashboard.putData(new BallIntakeSetStop());

		SmartDashboard.putData(new GearIntakeSetOut());
		SmartDashboard.putData(new GearIntakeSetIn());
		SmartDashboard.putData(new GearIntakeSetStop());
	}

	private long startTime = System.currentTimeMillis();

	private long getFrequency() {
		long start = startTime;
		startTime = System.currentTimeMillis();
		return startTime - start;
	}
}