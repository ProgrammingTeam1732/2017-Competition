package org.usfirst.frc.team1732.robot.vision;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1732.robot.commands.vision.movement.DriveWithVision;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;
import org.usfirst.frc.team1732.robot.subsystems.PixyCamera;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionMain implements SmartDashboardGroup {

    private Arduino gearArduino;
    // private Arduino boilerArduino;

    private GearTarget gearTarget;
    private Rectangle[] gearRectangles = new Rectangle[0];
    private double previousGearScore = 0;
    private boolean isNewGearDataAvailable = false;

    private BoilerTarget boilerTarget;
    private Rectangle[] boilerRectangles = new Rectangle[0];
    private double previousBoilerScore = 0;
    private boolean isNewBoilerDataAvailable = false;

    // Vision Angle Stuff
    private final PIDSource gearAngleSource = getGearPIDSource();
    private final PIDController gearPID = new PIDController(gearP, gearI, gearD, gearAngleSource,
	    VisionMain::voidMethod);
    public static final double gearP = 0.04;
    public static final double gearI = 0;
    public static final double gearD = 0;

    private final PIDSource boilerAngleSource = getBoilerPIDSource();
    private final PIDController boilerPID = new PIDController(boilerP, boilerI, boilerD, boilerAngleSource,
	    VisionMain::voidMethod);
    public static final double boilerP = 0.02;
    public static final double boilerI = 0;
    public static final double boilerD = 0;

    public static final double VISION_DEADBAND_DEGREES = 2;
    public static final double MAX_OUTPUT = 0.4;
    public static final double MIN_OUTPUT = -MAX_OUTPUT;

    public static final String NAME = "Vision Main";

    public VisionMain() {
	gearArduino = new Arduino(SerialPort.Port.kUSB1);
	// boilerArduino = new Arduino(SerialPort.Port.kUSB2);
	// Vision PID
	gearPID.setAbsoluteTolerance(VISION_DEADBAND_DEGREES);
	gearPID.setContinuous(false);
	gearPID.setOutputRange(MIN_OUTPUT, MAX_OUTPUT);
	gearPID.enable();
	// outputStream = CameraServer.getInstance().putVideo( "Gear
	// Rectangles", PixyCamera.IMAGE_WIDTH,
	// PixyCamera.IMAGE_HEIGHT);
	mat = new Mat();
    }

    /**
     * Reads and parses Arduino rectangle data, updates the gear target variable
     */
    public void run() {
	if (isGearCameraEnabled()) {
	    gearRectangles = parseData(gearArduino.getData(), gearRectangles);
	    updateGearTarget();
	    // sendGearImage();
	    // gearRectangles = new Rectangle[0];
	}
	if (isBoilerCameraEnabled()) {
	    // boilerRectangles = parseData(boilerArduino.getData(),
	    // boilerRectangles);
	    // if (rectangles != null)
	    // System.out.println(rectangles.length);
	    // updateBoilerTarget();
	}
    }

    private String total = "";
    private boolean found = false;
    private boolean started = false;

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
			    output[j] = new Rectangle(Integer.parseInt(data[1]), Integer.parseInt(data[3]),
				    Integer.parseInt(data[5]), Integer.parseInt(data[7]), Integer.parseInt(data[9]));
			    // System.out.println("[" + j + ": " + rects[j] +
			    // "]");
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			}
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
	    // System.out.println(gearTarget + " " + gearRectangles[0] + " " +
	    // gearRectangles[1]);
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
	    // for (Rectangle r : boilerRectangles)
	    // System.out.println(r);
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

	return gearTarget.getHorizontalDistance(GearTarget.GEAR_TARGET_WIDTH_INCHES,
		PixyCamera.HORIZONTAL_FIELD_OF_VIEW, PixyCamera.IMAGE_WIDTH);
    }

    public double getInchesToBoiler() {
	if (boilerTarget == null)
	    return -1;
	// return gearTarget.getVerticalDistance(
	// GearTarget.GEAR_TARGET_HEIGHT_INCHES, VERTICAL_FIELD_OF_VIEW,
	// IMAGE_HEIGHT);

	return boilerTarget.getHorizontalDistance(BoilerTarget.BOILER_TARGET_WIDTH_INCHES,
		PixyCamera.HORIZONTAL_FIELD_OF_VIEW, PixyCamera.IMAGE_WIDTH);
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
	return gearTarget.getHorizontalAngle(PixyCamera.HORIZONTAL_FIELD_OF_VIEW, PixyCamera.IMAGE_WIDTH);
    }

    public double getAngleToBoiler() {
	if (boilerTarget == null) {
	    return 0;
	}
	return boilerTarget.getHorizontalAngle(PixyCamera.HORIZONTAL_FIELD_OF_VIEW, PixyCamera.IMAGE_WIDTH);
    }

    public boolean canSeeGearPeg() {
	return gearTarget != null;
    }

    public boolean canSeeBoiler() {
	return boilerTarget != null;
    }

    private PIDSource getGearPIDSource() {
	return new PIDSource() {
	    @Override
	    public void setPIDSourceType(PIDSourceType pidSource) {
	    }

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

    private PIDSource getBoilerPIDSource() {
	return new PIDSource() {
	    @Override
	    public void setPIDSourceType(PIDSourceType pidSource) {
	    }

	    @Override
	    public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	    }

	    @Override
	    public double pidGet() {
		return getAngleToBoiler();
	    }
	};
    }

    private static void voidMethod(double d) {
    }

    @Override
    public void addToSmartDashboard(MySmartDashboard dashboard) {
	String directory = NAME + "/";
	String visionDirectory = directory + "vision/";

	dashboard.addItem(SmartDashboardItem.newNumberSender(visionDirectory + "Gear Peg Score", this::getGearScore));
	dashboard
		.addItem(SmartDashboardItem.newNumberSender(visionDirectory + "Gear inches", this::getInchesToGearPeg));
	dashboard
		.addItem(SmartDashboardItem.newNumberSender(visionDirectory + "Gear degrees", this::getAngleToGearPeg));
	dashboard.addItem(
		SmartDashboardItem.newBooleanSender(visionDirectory + "Can see gear peg?", this::canSeeGearPeg));

	dashboard.addItem(SmartDashboardItem.newNumberSender(visionDirectory + "Gear Setpoint", gearPID::getSetpoint));
	dashboard.addItem(SmartDashboardItem.newNumberSender(visionDirectory + "Gear Error", gearPID::getError));
	dashboard
		.addItem(SmartDashboardItem.newBooleanSender(visionDirectory + "At gear setpoint?", gearPID::onTarget));
	dashboard.addItem(SmartDashboardItem.newNumberSender(visionDirectory + "Gear PID Output", gearPID::get));
	dashboard.addItem(SmartDashboardItem.newBooleanSender(visionDirectory + "Gear Camera Enabled",
		this::isGearCameraEnabled));
	dashboard.addItem(SmartDashboardItem.newBooleanSender(visionDirectory + "Boiler Camera Enabled",
		this::isBoilerCameraEnabled));
	dashboard.addItem(SmartDashboardItem.newDoubleReciever(visionDirectory + "Turning P Slope",
		DriveWithVision.getSlope(), DriveWithVision::setSlope));
	dashboard.addItem(SmartDashboardItem.newDoubleReciever(visionDirectory + "Turning P Lower",
		DriveWithVision.getLower(), DriveWithVision::setLower));
	dashboard.addItem(SmartDashboardItem.newDoubleReciever(visionDirectory + "Turning P Upper",
		DriveWithVision.getUpper(), DriveWithVision::setUpper));
	dashboard.addItem(SmartDashboardItem.newDoubleReciever(visionDirectory + "Turning P Middle",
		DriveWithVision.getMiddle(), DriveWithVision::setMiddle));
	SmartDashboard.putData("Vision PID", gearPID);
    }

    public void resetGearPIDValues() {
	gearPID.setPID(gearP, gearI, gearD);
    }

    public void resetBoilerPIDValues() {
	gearPID.setPID(gearP, gearI, gearD);
    }

    public boolean isGearCameraEnabled() {
	return !gearArduino.isDisabled();
    }

    public boolean isBoilerCameraEnabled() {
	return false;// !boilerArduino.isDisabled();
    }

    public void setGearSetpoint(double setpoint) {
	gearPID.setSetpoint(setpoint);
    }

    public void setBoilerSetpoint(double setpoint) {
	boilerPID.setSetpoint(setpoint);
    }

    public void setGearPIDValues(double p, double i, double d) {
	gearPID.setPID(p, i, d);
    }

    public void setBoilerPIDValues(double p, double i, double d) {
	boilerPID.setPID(p, i, d);
    }

    public double getGearPIDOutput() {
	return gearPID.get();
    }

    public double getBoilerPIDOutput() {
	return boilerPID.get();
    }

    public boolean isGearPIDOnTarget() {
	return gearPID.onTarget();
    }

    public boolean isBoilerPIDOnTarget() {
	return boilerPID.onTarget();
    }

    public double getGearScore() {
	if (gearTarget == null) {
	    return -1;
	} else {
	    return gearTarget.getScore();
	}
    }

    public double getBoilerScore() {
	if (boilerTarget == null) {
	    return -1;
	} else {
	    return boilerTarget.getScore();
	}
    }

    private CvSource outputStream;
    private Mat mat;

    public void sendGearImage() {
	mat = new Mat(PixyCamera.IMAGE_HEIGHT, PixyCamera.IMAGE_WIDTH, org.opencv.core.CvType.CV_8UC3);
	// Put a rectangle on the image
	for (Rectangle r : gearRectangles) {
	    Imgproc.rectangle(mat, new Point(r.x, r.y), new Point(r.getRightX(), r.getBottomY()),
		    new Scalar(255, 255, 255));
	}
	if (gearTarget != null) {
	    Rectangle left = gearTarget.left;
	    Rectangle right = gearTarget.right;
	    // left
	    Imgproc.rectangle(mat, new Point(left.x, left.y), new Point(left.getRightX(), left.getBottomY()),
		    new Scalar(255, 0, 0));
	    // right
	    Imgproc.rectangle(mat, new Point(right.x, right.y), new Point(right.getRightX(), right.getBottomY()),
		    new Scalar(255, 0, 0));
	}
	// Give the output stream a new image to display
	outputStream.putFrame(mat);
    }

    public void resetEncoderPIDValues() {
	// TODO Auto-generated method stub

    }
}