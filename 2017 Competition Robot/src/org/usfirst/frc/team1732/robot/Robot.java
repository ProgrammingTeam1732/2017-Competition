
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.autocommands.GrabBallsAndShootBlue;
import org.usfirst.frc.team1732.robot.autocommands.GrabBallsAndShootRed;
import org.usfirst.frc.team1732.robot.autocommands.GrabBallsBackwardAndShoot;
import org.usfirst.frc.team1732.robot.autocommands.Score10BallsAndGearBlue;
import org.usfirst.frc.team1732.robot.autocommands.Score10BallsAndGearRed;
import org.usfirst.frc.team1732.robot.autocommands.ScoreGearAnd10BallsBlue;
import org.usfirst.frc.team1732.robot.autocommands.ScoreGearAnd10BallsRed;
import org.usfirst.frc.team1732.robot.autocommands.ScoreSideGearLeft;
import org.usfirst.frc.team1732.robot.autocommands.ScoreSideGearRight;
import org.usfirst.frc.team1732.robot.autocommands.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.BallIntake.BallIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.BallIntake.BallIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.BallIntake.BallIntakeSetOut;
import org.usfirst.frc.team1732.robot.commands.BallIntake.BallIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetIn;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetOut;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftHigh;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftLow;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetStorageIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetStorageOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.individual.FlywheelForward;
import org.usfirst.frc.team1732.robot.commands.individual.FlywheelReverse;
import org.usfirst.frc.team1732.robot.commands.individual.FlywheelStop;
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
import org.usfirst.frc.team1732.robot.commands.vision.DriveWithVision;
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
	public static Arm			arm;

	public static VisionMain					visionMain;
	private static MySmartDashboard				dashboard;
	private static Command						autoCommand;
	private static SendableChooser<Command>		autoChooser;
	private static SmartDashboardItem<Boolean>	isRedAlliance;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
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
		// Command related SmartDashboardItems
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

		// Add auto chooser
		autoChooser = new SendableChooser<Command>();
		autoChooser.addDefault("Vision Place Gear", new VisionPlaceGear(-40));
		autoChooser.addObject("Score Side Gear Right", new ScoreSideGearRight());
		autoChooser.addObject("Score Side Gear Left", new ScoreSideGearLeft());
		autoChooser.addObject("Score Gear then 10 Balls", new ScoreGearAnd10BallsRed());
		autoChooser.addObject("Score 10 Balls then Gear", new Score10BallsAndGearRed());
		autoChooser.addObject("Grab Balls then Shoot", new GrabBallsAndShootRed());
		autoChooser.addObject("Grab Balls Backward then Shoot", new GrabBallsBackwardAndShoot());
		SmartDashboard.putData("AutonomousChooser", autoChooser);
		// SmartDashboard.putBoolean("IsRedAlliance?", false);
		autoCommand = new VisionPlaceGear(-40);
		SmartDashboard.putString("Current Command", autoCommand.getName());
		// Initialize smartdashboard
		dashboard.init();
		SmartDashboard.putData(new FlywheelForward());
		SmartDashboard.putData(new FlywheelReverse());
		SmartDashboard.putData(new FlywheelStop());

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
		
		SmartDashboard.putData(new GearIntakeSetStorageOut());
		SmartDashboard.putData(new GearIntakeSetStorageIn());
		
	}

	@Override
	public void robotPeriodic() {
		visionMain.run();
		dashboard.run();
	}

	/**
	 * Gets if the alliance color is red
	 * 
	 * @return if the current alliance is red
	 */
	public static boolean isRedAlliance() {
		return isRedAlliance.getValue();
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
		// SmartDashboard.getBoolean("IsRedAlliance?", false);

		autoCommand = autoChooser.getSelected();

		if (autoCommand.getName().equals(new ScoreGearAnd10BallsRed().getName()))
			if (!isRedAlliance.getValue())
				autoCommand = new ScoreGearAnd10BallsBlue();

		if (autoCommand.getName().equals(new Score10BallsAndGearRed().getName()))
			if (!isRedAlliance.getValue())
				autoCommand = new Score10BallsAndGearBlue();

		if (autoCommand.getName().equals(new GrabBallsAndShootRed().getName()))
			if (!isRedAlliance.getValue())
				autoCommand = new GrabBallsAndShootBlue();

		Scheduler.getInstance().add(autoCommand);
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
}