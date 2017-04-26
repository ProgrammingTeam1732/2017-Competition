
package org.usfirst.frc.team1732.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1732.robot.autocommands.AutoChooser;
import org.usfirst.frc.team1732.robot.commands.SetRobotToStartState;
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
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShuffleBallsWithWait;
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
import org.usfirst.frc.team1732.robot.subsystems.Wings;
import org.usfirst.frc.team1732.robot.triggers.Triggers;
import org.usfirst.frc.team1732.robot.vision.VisionMain;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
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

    public static OI oi;
    public static DriveTrain driveTrain;
    public static BallIntake ballIntake;
    public static Climber climber;
    public static Feeder feeder;
    public static Flywheel flywheel;
    public static GearIntake gearIntake;
    public static Arm arm;
    public static Wings wings;
    public static PixyCamera pixyCamera;
    public static Triggers triggers;

    public static VisionMain visionMain;
    private static MySmartDashboard dashboard;

    private static AutoChooser autoChooser;
    public static SmartDashboardItem<Boolean> isRedAlliance;
    public static SmartDashboardItem<Double> autoWaitTime;
    public static SmartDashboardItem<Double> startOnWallAndShootDistance;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code. - written by WPIlib people
     */
    @Override
    public void robotInit() {
	try {
	    // initialize smartdashboard
	    initializeMySmartDashboardItems();
	    initializeSubsystems();
	    initializeVision();
	    initializeInput();

	    // Add items to smartdashboard
	    addSubsystemsToSmartDashboard();
	    addAutonomousToSmartDashboard();
	    addTestingToSmartDashbaord();
	    addCamera();
	    dashboard.addItem(SmartDashboardItem.newNumberSender("robotPeriodic() frequency ms", this::getFrequency));

	    // initially sends items that have been added to driverstation
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
    public void disabledInit() {
	setRobotToDefaultStates();
    }

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
	// setRobotToDefaultStates();
	new SetRobotToStartState().start();
    }

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

    private void initializeMySmartDashboardItems() {
	dashboard = new MySmartDashboard();

	autoWaitTime = dashboard.addItem(SmartDashboardItem.newDoubleReciever("Auto wait time", 0.0));
	startOnWallAndShootDistance = dashboard
		.addItem(SmartDashboardItem.newDoubleReciever("Start wall and shoot wait distance", 100.0));
	isRedAlliance = dashboard.addItem(SmartDashboardItem.newBooleanSender("Is Red Alliance?",
		() -> DriverStation.getInstance().getAlliance().equals(Alliance.Red)));
    }

    private void initializeSubsystems() {
	driveTrain = new DriveTrain();
	flywheel = new Flywheel();
	ballIntake = new BallIntake();
	climber = new Climber();
	feeder = new Feeder();
	arm = new Arm();
	gearIntake = new GearIntake();
	wings = new Wings();
    }

    private void initializeVision() {
	pixyCamera = new PixyCamera();
	visionMain = new VisionMain();
    }

    private void initializeInput() {
	triggers = new Triggers();
	oi = new OI();
    }

    private void addSubsystemsToSmartDashboard() {
	driveTrain.addToSmartDashboard(dashboard);
	flywheel.addToSmartDashboard(dashboard);
	ballIntake.addToSmartDashboard(dashboard);
	climber.addToSmartDashboard(dashboard);
	feeder.addToSmartDashboard(dashboard);
	arm.addToSmartDashboard(dashboard);
	wings.addToSmartDashboard(dashboard);
	gearIntake.addToSmartDashboard(dashboard);
	visionMain.addToSmartDashboard(dashboard);
    }

    private void addAutonomousToSmartDashboard() {
	autoChooser = new AutoChooser();
	autoChooser.addToSmartDashboard(dashboard);
	dashboard.addItem(SmartDashboardItem.newStringSender("AUTO:", () -> {
	    try {
		return autoChooser.getSelected().name();
	    } catch (Exception e) {
		return "Error";
	    }
	}));
    }

    private void addTestingToSmartDashbaord() {
	SmartDashboard.putData(new ShuffleBallsWithWait());
	SmartDashboard.putData(new TestVisionMain());

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

    private void addCamera() {
	int width = 320;
	int height = 240;
	int thickness = 10;
	Scalar color = new Scalar(0, 255, 0);
	Runnable visionRun = new Runnable() {
	    @Override
	    public void run() {
		// Get the UsbCamera from CameraServer
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		// Set the resolution
		camera.setResolution(width, height);

		// Get a CvSink. This will capture Mats from the camera
		CvSink cvSink = CameraServer.getInstance().getVideo(camera);
		// Setup a CvSource. This will send images back to the Dashboard
		CvSource outputStream = CameraServer.getInstance().putVideo("Video", width, height);

		// Mats are very memory expensive. Lets reuse this Mat.
		// Mats are very memory expensive. Lets reuse this Mat.
		Mat mat = new Mat();

		// This cannot be 'true'. The program will never exit if it is.
		// This
		// lets the robot stop this thread when restarting robot code or
		// deploying.
		while (!Thread.interrupted()) {
		    // Tell the CvSink to grab a frame from the camera and put
		    // it
		    // in the source mat. If there is an error notify the
		    // output.
		    if (cvSink.grabFrame(mat) == 0) {
			// Send the output the error.
			outputStream.notifyError(cvSink.getError());
			System.out.println("Error getting frame");
			// skip the rest of the current iteration
			continue;
		    }
		    // Put a border on the image
		    if (gearIntake.gearIsIn()) {// gearIntake.gearIsHeld() ||) {
			// upper edge
			Imgproc.rectangle(mat, new Point(0, 0), new Point(width, thickness), color, thickness);
			// left edge
			Imgproc.rectangle(mat, new Point(0, 0), new Point(thickness, height), color, thickness);
			// right edge
			Imgproc.rectangle(mat, new Point(width - thickness, 0), new Point(width, height), color,
				thickness);
			// bottom edge
			Imgproc.rectangle(mat, new Point(0, height - thickness), new Point(width, height), color,
				thickness);
		    }
		    // add lines for karl target
		    // Scalar lineColor = new Scalar(0, 0, 0);
		    // Imgproc.line( mat, new Point((int) (width * 0.34651), 0),
		    // new Point((int) (width * 0.031217), height), lineColor,
		    // 2);
		    // Imgproc.line( mat, new Point((int) (width * 0.537981),
		    // 0),
		    // new Point((int) (width * 0.816857), height), lineColor,
		    // 2);
		    // Give the output stream a new image to display
		    outputStream.putFrame(mat);
		}
	    }

	};
	Thread visionThread = new Thread(visionRun);
	visionThread.setDaemon(true);
	visionThread.start();

    }

    /**
     * Sets the robot to its default states Default states <br>
     * -solenoid default positions (what position they are when in disabled
     * mode) <br>
     * -motors turned off
     */
    private void setRobotToDefaultStates() {
	driveTrain.clearEncoderIntgral();
	driveTrain.clearGyroIntgral();
	// moters
	feeder.setStop(); // stop feeder
	flywheel.disableAutoControl(); // turn off flywheel
	driveTrain.driveRaw(0, 0);
	climber.setStop();
	gearIntake.setStop();
	ballIntake.setSpeedStop();
	// pnuematics
	arm.setIn();
	gearIntake.setUp();
	ballIntake.setPositionDown();
	driveTrain.shiftHighGear();
	wings.setIn();
    }
}