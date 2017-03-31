
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.autocommands.AutoChooser;
import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.motor.BallIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.motor.BallIntakeSetOut;
import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.motor.BallIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.position.BallIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.position.BallIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetIn;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetOut;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetStop;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.DisableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.manual.FlywheelForward;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.manual.FlywheelReverse;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.manual.FlywheelStop;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetIn;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetOut;
import org.usfirst.frc.team1732.robot.commands.climber.ClimberSetStop;
import org.usfirst.frc.team1732.robot.commands.climber.ClimberSetUp;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftHigh;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftLow;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorleftback.MotorLTBackForward;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorleftback.MotorLTBackReverse;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorleftback.MotorLTBackStop;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorleftbottom.MotorLTBottomForward;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorleftbottom.MotorLTBottomReverse;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorleftbottom.MotorLTBottomStop;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorleftfront.MotorLTFrontForward;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorleftfront.MotorLTFrontReverse;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorleftfront.MotorLTFrontStop;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorrightback.MotorRTBackForward;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorrightback.MotorRTBackReverse;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorrightback.MotorRTBackStop;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorrightbottom.MotorRTBottomForward;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorrightbottom.MotorRTBottomReverse;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorrightbottom.MotorRTBottomStop;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorrightfront.MotorRTFrontForward;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorrightfront.MotorRTFrontReverse;
import org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorrightfront.MotorRTFrontStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.stopper.GearIntakeSetStopperIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.stopper.GearIntakeSetStopperOut;
import org.usfirst.frc.team1732.robot.commands.vision.TestVisionMain;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.BallIntake;
import org.usfirst.frc.team1732.robot.subsystems.Climber;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1732.robot.subsystems.Feeder;
import org.usfirst.frc.team1732.robot.subsystems.Flywheel;
import org.usfirst.frc.team1732.robot.subsystems.GearIntake;
import org.usfirst.frc.team1732.robot.subsystems.PixyCamera;
import org.usfirst.frc.team1732.robot.triggers.Triggers;
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
	public static PixyCamera	pixyCamera;
	public static Triggers		triggers;

	public static VisionMain		visionMain;
	private static MySmartDashboard	dashboard;

	private static AutoChooser					autoChooser;
	public static SmartDashboardItem<Boolean>	isRedAlliance;
	public static SmartDashboardItem<Double>	autoWaitTime;
	public static SmartDashboardItem<Double>	startOnWallAndShootDistance;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code. - written by WPIlib people
	 */
	@Override
	public void robotInit() {
		try {
			// initialize subsystems - always do this first
			driveTrain = new DriveTrain();
			flywheel = new Flywheel();
			ballIntake = new BallIntake();
			climber = new Climber();
			feeder = new Feeder();
			arm = new Arm();
			gearIntake = new GearIntake();
			pixyCamera = new PixyCamera();
			triggers = new Triggers();
			autoChooser = new AutoChooser();

			oi = new OI();
			visionMain = new VisionMain();

			// Smartdashboard code
			dashboard = new MySmartDashboard();
			// Add items to smartdashboard
			addSubsystemsToSmartDashboard();
			addAutonomousToSmartDashboard();
			addTestingToSmartDashbaord();

			dashboard.addItem(SmartDashboardItem.newNumberSender("robotPeriodic() frequency ms", this::getFrequency));
			autoWaitTime = dashboard.addItem(SmartDashboardItem.newDoubleReciever("Auto wait time", 0.0));
			startOnWallAndShootDistance = dashboard
					.addItem(SmartDashboardItem.newDoubleReciever("Start wall and shoot wait distance", 100.0));
			SmartDashboard.putData(new TestVisionMain());
			// addCamera();

			// Initialize smartdashboard
			dashboard.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void robotPeriodic() {
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
		// new TurnLightsOn().start();
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
		autoChooser.addToSmartDashboard(dashboard);
	}

	private void addAutonomousToSmartDashboard() {
		isRedAlliance = dashboard.addItem(SmartDashboardItem
				.newBooleanSender(	"Is Red Alliance?",
									() -> DriverStation.getInstance().getAlliance().equals(Alliance.Red)));

		dashboard.addItem(SmartDashboardItem.newStringSender("Selected Auto Command", () -> {
			try {
				return autoChooser.getSelected().getName();
			} catch (Exception e) {
				return "null";
			}
		}));
	}

	private void addTestingToSmartDashbaord() {
		//		SmartDashboard.putData(new ShuffleBallsWithWait());
		SmartDashboard.putData(new FlywheelForward());
		SmartDashboard.putData(new FlywheelReverse());
		SmartDashboard.putData(new FlywheelStop());

		SmartDashboard.putData(new EnableFlywheel());
		SmartDashboard.putData(new DisableFlywheel());

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

	@SuppressWarnings("unused")
	private void addCamera() {
		// Thread visionThread = new Thread(() -> {
		//
		// // Setup a CvSource. This will send images back to the Dashboard
		// CvSource outputStream = CameraServer.getInstance().putVideo("Video",
		// 320, 240);
		//
		// // Mats are very memory expensive. Lets reuse this Mat.
		// Mat mat = new Mat();
		//
		// // This cannot be 'true'. The program will never exit if it is. This
		// // lets the robot stop this thread when restarting robot code or
		// // deploying.
		// while (!Thread.interrupted()) {
		// // Tell the CvSink to grab a frame from the camera and put it
		// // in the source mat. If there is an error notify the output.
		//
		// mat = new Mat(240, 320, org.opencv.core.CvType.CV_8UC(3));
		// Imgproc.rectangle(img, pt1, pt2, color);
		// outputStream.putFrame(mat);
		// }
		// });
		// visionThread.setDaemon(true);
		// visionThread.start();
	}
}