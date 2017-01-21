
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.vision.Arduino;
import org.usfirst.frc.team1732.robot.vision.BoilerTarget;
import org.usfirst.frc.team1732.robot.vision.GearTarget;
import org.usfirst.frc.team1732.robot.vision.Rectangle;
import org.usfirst.frc.team1732.robot.vision.VisionThread;

import com.ctre.CANTalon;

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

	public static VisionThread visionRunnable;
	public static OI oi;
	private final CANTalon left1 = new CANTalon(15);
	private final CANTalon left2 = new CANTalon(14);
	private final CANTalon left3 = new CANTalon(13);

	private final CANTalon right1 = new CANTalon(21);
	private final CANTalon right2 = new CANTalon(20);
	private final CANTalon right3 = new CANTalon(19);
	private Arduino arduino;
	public static final double HORIZONTAL_FIELD_OF_VIEW = 75;
	public static final double VERTICAL_FIELD_OF_VIEW = 47;

	// double check
	public static final int IMAGE_WIDTH = 320;
	public static final int IMAGE_HEIGHT = 200;

	public GearTarget gearTarget;
	public BoilerTarget boilerTarget;
	private String total = "";
	private boolean found = false;
	private boolean started = false;
	private double distance = 0;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		visionRunnable = new VisionThread();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */

	@Override
	public void autonomousInit() {
		/*Thread thread = new Thread(visionRunnable);
		thread.setDaemon(true);
		thread.start();*/
		arduino = new Arduino();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		String s = arduino.getData();
		parseData(s);
		//double distance = visionRunnable.getInchesToGearPeg();
		// if (distance != -37) {
		System.out.println("Distance:" + distance);
		double speed = distance / (60);
		if (speed < .2)
			speed = .2;
		if (distance < 10)
			speed = 0;
		System.out.println(distance + "\n" + speed);
		left1.set(speed);
		left2.set(-speed);
		left3.set(-speed);
		right1.set(-speed);
		right2.set(speed);
		right3.set(speed);
		// }
		// System.out.println("Auto:" + visionRunnable.getInchesToGearPeg());
	}
	private Rectangle[] rectangles;
	private void parseData(String s){
		if (s.contains("Starting"))
			started = true;
		for (int i = 0; i < s.length() && started; i++) {
			char c = s.charAt(i);
			if (found && c != '^')
				total += c;
			if (c == '$')
				found = true;
			else if (c == '^') {
				found = false;
				if (total != "") {
					String[] rects = total.split("\n");
					rectangles = new Rectangle[rects.length];
					for (int j = 0; j < rects.length; j++) {
						String[] data = rects[j].split(" ");
						try {
							rectangles[j] = new Rectangle(Integer.parseInt(data[1]), Integer.parseInt(data[3]),
									Integer.parseInt(data[5]), Integer.parseInt(data[7]),
									Integer.parseInt(data[9]));
							System.out.println(j + ": " + rectangles[j]);
						} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
							distance = 0;
						}
					}
					try {
						gearTarget = GearTarget.getBestVisionTarget(rectangles);
						if (gearTarget != null) {
							//System.out.println(gearTarget.boundingBox);
							distance = getInchesToGearPeg();
							//System.out.println("Distance: " + getInchesToGearPeg());
							//System.out.println();
						}
					} catch (NullPointerException e) {
						distance = 0;
					}
				}
				total = "";
			}
			// change this later
			// rectangles = new Rectangle[0];
			// System.out.println(s);
		}
	}
	public double getInchesToGearPeg() {
		if (gearTarget == null)
			return -1;
		return gearTarget.getHorizontalDistance(GearTarget.GEAR_TARGET_WIDTH_INCHES, HORIZONTAL_FIELD_OF_VIEW,
				IMAGE_WIDTH);
	}

	public double getAngleToGearPeg() {
		return gearTarget.getHorizontalAngle(HORIZONTAL_FIELD_OF_VIEW, IMAGE_WIDTH);
	}
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
