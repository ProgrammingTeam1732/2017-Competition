package org.usfirst.frc.team1732.robot.vision;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.vision.DriveWithVision;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionMain implements SmartDashboardGroup {

	private Arduino	gearArduino;
	private Arduino	boilerArduino;

	private GearTarget	gearTarget;
	private Rectangle[]	gearRectangles			= new Rectangle[0];
	private double		previousGearScore		= 0;
	private boolean		isNewGearDataAvailable	= false;

	private BoilerTarget	boilerTarget;
	private Rectangle[]		boilerRectangles			= new Rectangle[0];
	private double			previousBoilerScore			= 0;
	private boolean			isNewBoilerDataAvailable	= false;

	// Vision Angle Stuff
	private final PIDSource		visionAngleSource	= this.getVisionPIDSource();
	private final PIDController	visionPID			= new PIDController(visionP, visionI, visionD, visionAngleSource,
																		VisionMain::voidMethod);
	public static final double	visionP				= 0.02;
	public static final double	visionI				= 0;
	public static final double	visionD				= 0;

	public static final double	VISION_DEADBAND_DEGREES	= 5;
	public static final double	MAX_OUTPUT				= 0.4;
	public static final double	MIN_OUTPUT				= -MAX_OUTPUT;

	public static final String NAME = "Vision Main";

	public VisionMain() {
		gearArduino = new Arduino(SerialPort.Port.kUSB1);
		boilerArduino = new Arduino(SerialPort.Port.kUSB2);
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
		if (isGearCameraEnabled()) {
			gearRectangles = parseData(gearArduino.getData(), gearRectangles);
			boilerRectangles = parseData(boilerArduino.getData(), boilerRectangles);
			// if (rectangles != null)
			// System.out.println(rectangles.length);
			updateGearTarget();
			updateBoilerTarget();
		}
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
	private Rectangle[] parseData(String s, Rectangle[] previous) {
		if (s == null) {
			System.out.println("string is null");
			return previous;
		}
		Rectangle[] output = previous;
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
					output = new Rectangle[rects.length];
					for (int j = 0; j < rects.length; j++) {
						String[] data = rects[j].split(" ");
						try {
							output[j] = new Rectangle(	Integer.parseInt(data[1]), Integer.parseInt(data[3]),
														Integer.parseInt(data[5]), Integer.parseInt(data[7]),
														Integer.parseInt(data[9]));
							// System.out.println(j + ": " + rectangles[j]);
						} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {}
					}
				}
				total = "";
			}
		}
		return output;
	}

	/**
	 * Updates the gearTarget variable based on the newest rectangle
	 * information. <br>
	 * The gearTarget variable represents the two rectangles that most likely
	 * makeup the gearTarget.
	 */
	private void updateGearTarget() {
		try {
			gearTarget = GearTarget.getBestVisionTarget(gearRectangles);
			double score = 0;
			if (gearTarget != null)
				score = gearTarget.getScore();
			if (roundToNDigits(previousGearScore, 5) == roundToNDigits(score, 5)) {
				isNewGearDataAvailable = false;
			} else {
				isNewGearDataAvailable = true;
			}
			previousGearScore = score;
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void updateBoilerTarget() {
		try {
			for (Rectangle r : boilerRectangles)
				System.out.println(r);
			boilerTarget = BoilerTarget.getBestVisionTarget(boilerRectangles);
			double score = 0;
			if (boilerTarget != null)
				score = boilerTarget.getScore();
			if (roundToNDigits(previousBoilerScore, 5) == roundToNDigits(score, 5)) {
				isNewBoilerDataAvailable = false;
			} else {
				isNewBoilerDataAvailable = true;
			}
			previousBoilerScore = score;
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private double roundToNDigits(double d, int n) {
		double scaler = Math.pow(10, n);
		int expanded = (int) Math.round(scaler * d);
		return expanded / scaler;
	}

	public boolean isNewGearDataAvailable() {
		return isNewGearDataAvailable;
	}

	public boolean isNewBoilerDataAvailable() {
		return isNewBoilerDataAvailable;
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

		return gearTarget.getVerticalDistance(	GearTarget.GEAR_TARGET_HEIGHT_INCHES,
												Robot.pixyCamera.HORIZONTAL_FIELD_OF_VIEW,
												Robot.pixyCamera.IMAGE_WIDTH);
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
		return gearTarget.getHorizontalAngle(Robot.pixyCamera.HORIZONTAL_FIELD_OF_VIEW, Robot.pixyCamera.IMAGE_WIDTH);
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

		dashboard.addItem(SmartDashboardItem.newNumberSender(visionDirectory + "Gear Peg Score", this::getGearScore));
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
		dashboard.addItem(SmartDashboardItem.newBooleanSender(	visionDirectory + "Gear Camera Enabled",
																this::isGearCameraEnabled));
		dashboard.addItem(SmartDashboardItem.newBooleanSender(	visionDirectory + "Boiler Camera Enabled",
																this::isBoilerCameraEnabled));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(	visionDirectory + "Turning P Slope",
																DriveWithVision.getSlope(), DriveWithVision::setSlope));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(	visionDirectory + "Turning P Lower",
																DriveWithVision.getLower(), DriveWithVision::setLower));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(	visionDirectory + "Turning P Upper",
																DriveWithVision.getUpper(), DriveWithVision::setUpper));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(	visionDirectory + "Turning P Middle",
																DriveWithVision.getMiddle(),
																DriveWithVision::setMiddle));
		SmartDashboard.putData("Vision PID", visionPID);
	}

	public void resetPIDValues() {
		visionPID.setPID(visionP, visionI, visionD);
	}

	public boolean isGearCameraEnabled() {
		return !gearArduino.isDisabled();
	}

	public boolean isBoilerCameraEnabled() {
		return !boilerArduino.isDisabled();
	}

	public void setVisionSetpoint(double setpoint) {
		visionPID.setSetpoint(setpoint);
	}

	public void setPIDValues(double p, double i, double d) {
		visionPID.setPID(p, i, d);
	}

	public double getVisionPIDOutput() {
		return visionPID.get();
	}

	public boolean isVisionPIDOnTarget() {
		return visionPID.onTarget();
	}

	public double getGearScore() {
		if (gearTarget == null) {
			return -1;
		} else {
			return gearTarget.getScore();
		}
	}
}