package org.usfirst.frc.team1732.robot.vision;

import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionMain implements SmartDashboardGroup {

	public static final double	HORIZONTAL_FIELD_OF_VIEW	= 68;
	public static final double	VERTICAL_FIELD_OF_VIEW		= 47;

	// double check
	public static final int	IMAGE_WIDTH		= 320;
	public static final int	IMAGE_HEIGHT	= 200;

	private Arduino arduino;

	private GearTarget		gearTarget;
	private BoilerTarget	boilerTarget;
	private Rectangle[]		rectangles	= new Rectangle[0];

	// Vision Angle Stuff
	public final PIDSource		visionAngleSource		= this.getVisionPIDSource();
	public final PIDController	visionPID				= new PIDController(0.015, 0, 0, visionAngleSource,
																			VisionMain::voidMethod);
	public static final double	VISION_DEADBAND_DEGREES	= 3;
	public static final double	MAX_OUTPUT				= 0.5;
	public static final double	MIN_OUTPUT				= -MAX_OUTPUT;

	public static final String NAME = "Vision Main";

	public VisionMain() {
		arduino = new Arduino();

		// Vision PID
		visionPID.setAbsoluteTolerance(VISION_DEADBAND_DEGREES);
		visionPID.setContinuous(false);
		visionPID.setOutputRange(MIN_OUTPUT, MAX_OUTPUT);
		visionPID.enable();
	}

	/**
	 * Reads and parses Arduino rectangle data, updates the gear target variable
	 */
	public void run() {
		parseData(arduino.getData());
		updateGearTarget();
	}

	private String	total	= "";
	private boolean	found	= false;
	private boolean	started	= false;

	/**
	 * Parses the data and puts found rectangles into rectangles array
	 * 
	 * @param s
	 *            Data from the Arduino
	 */
	private void parseData(String s) {
		if (s == null) {
			return;
		}
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
							rectangles[j] = new Rectangle(	Integer.parseInt(data[1]), Integer.parseInt(data[3]),
															Integer.parseInt(data[5]), Integer.parseInt(data[7]),
															Integer.parseInt(data[9]));
							System.out.println(j + ": " + rectangles[j]);
						} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {}
					}
				}
				total = "";
			}
		}
	}

	/**
	 * Updates the gearTarget variable based on the newest rectangle
	 * information. <br>
	 * The gearTarget variable represents the two rectangles that most likely
	 * makeup the gearTarget.
	 */
	private void updateGearTarget() {
		try {
			gearTarget = GearTarget.getBestVisionTarget(rectangles);
		} catch (NullPointerException e) {
			e.getMessage();
		}
	}

	/**
	 * @return the distance or -1 to indicate not data
	 */
	public double getInchesToGearPeg() {
		if (gearTarget == null)
			return -1;
		// return gearTarget.getVerticalDistance(
		// GearTarget.GEAR_TARGET_HEIGHT_INCHES, VERTICAL_FIELD_OF_VIEW,
		// IMAGE_HEIGHT);

		return gearTarget.getVerticalDistance(	GearTarget.GEAR_TARGET_HEIGHT_INCHES, HORIZONTAL_FIELD_OF_VIEW,
												IMAGE_WIDTH);
	}

	/**
	 * Yes this does overlap in case it does see it
	 * 
	 * @return the angle or 0 to indicate no data
	 */
	public double getAngleToGearPeg() {
		if (gearTarget == null) {
			return 0;
		}
		return gearTarget.getHorizontalAngle(HORIZONTAL_FIELD_OF_VIEW, IMAGE_WIDTH);
	}

	public boolean canSeeGearPeg() {
		return gearTarget != null;
	}

	private PIDSource getVisionPIDSource() {
		return new PIDSource() {
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				return getAngleToGearPeg();
			}
		};
	}

	private static void voidMethod(double d) {}

	@Override
	public void addToSmartDashboard(MySmartDashboard dashboard) {
		String directory = NAME + "/";
		String visionDirectory = directory + "vision/";

		dashboard.addItem(SmartDashboardItem.newNumberSender(	visionDirectory + "Vision inches",
																this::getInchesToGearPeg));
		dashboard.addItem(SmartDashboardItem.newNumberSender(	visionDirectory + "Vision degrees",
																this::getAngleToGearPeg));
		dashboard
				.addItem(SmartDashboardItem.newBooleanSender(visionDirectory + "Can see target?", this::canSeeGearPeg));

		dashboard.addItem(SmartDashboardItem.newNumberSender(	visionDirectory + "Vision Setpoint",
																visionPID::getSetpoint));
		dashboard.addItem(SmartDashboardItem.newNumberSender(visionDirectory + "Vision Error", visionPID::getError));
		dashboard.addItem(SmartDashboardItem.newBooleanSender(	visionDirectory + "At vision setpoint?",
																visionPID::onTarget));
		dashboard.addItem(SmartDashboardItem.newNumberSender(visionDirectory + "Vision PID Output", visionPID::get));
		SmartDashboard.putData("Vision PID", visionPID);
	}
}