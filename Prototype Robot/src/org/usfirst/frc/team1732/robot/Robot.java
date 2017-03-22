
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.autocommands.AutoChooser;
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
import org.usfirst.frc.team1732.robot.subsystems.PixyCamera;
import org.usfirst.frc.team1732.robot.vision.VisionMain;

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

	public static OI			oi;
	public static DriveTrain	driveTrain;
	public static GearIntake	gearIntake;
	public static PixyCamera	pixyCamera;

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
		driveTrain = new DriveTrain();
		gearIntake = new GearIntake();
		pixyCamera = new PixyCamera();

		oi = new OI();
		visionMain = new VisionMain();

		// Smartdashboard code
		dashboard = new MySmartDashboard();
		// Add items to smartdashboard
		//driveTrain.addToSmartDashboard(dashboard);
		//gearIntake.addToSmartDashboard(dashboard);
		//visionMain.addToSmartDashboard(dashboard);
		addSubsystemsToSmartDashboard();
		addAutonomousToSmartDashboard();
		addTestingToSmartDashbaord();

		addCamera();
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
		driveTrain.printInfo();
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
		driveTrain.resetEncoders();
		driveTrain.clearEncoderIntgral();
		driveTrain.clearGyroIntgral();

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
		dashboard.addItem(SmartDashboardItem.newNumberSender("robotPeriodic() frequency ms", this::getFrequency));

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

	private void addCamera() {
		CameraServer.getInstance().startAutomaticCapture();
		// Thread visionThread = new Thread(() -> {
		// // Get the UsbCamera from CameraServer
		// UsbCamera camera =
		// CameraServer.getInstance().startAutomaticCapture();
		// // Set the resolution
		// camera.setResolution(320, 240);
		//
		// // Get a CvSink. This will capture Mats from the camera
		// CvSink cvSink = CameraServer.getInstance().getVideo();
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
		// if (cvSink.grabFrame(mat) == 0) {
		// // Send the output the error.
		// outputStream.notifyError(cvSink.getError());
		// // skip the rest of the current iteration
		// continue;
		// }
		// // Put a cirlce on the image
		//// Imgproc.circle(mat, new Point(160, 180), 40, new Scalar(new
		// double[] { 255, 0, 0 }), 10);
		// // Give the output stream a new image to display
		// outputStream.putFrame(mat);
		// }
		// });
		// visionThread.setDaemon(true);
		// visionThread.start();
	}
}