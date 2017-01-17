package org.usfirst.frc.team1732.robot.vision;

import org.usfirst.frc.team1732.robot.vision.VisionTarget.Rectangle;

public class VisionThread implements Runnable {

	public GearTarget	gearTarget;
	public BoilerTarget	boilerTarget;

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			// parse code in here
			int length = 0;
			Rectangle[] rectangles = new Rectangle[length];
			// calculate best gear and boiler target
			gearTarget = GearTarget.getBestVisionTarget(rectangles);
		}
	}

	public double getInchesToGearPeg() {
		return gearTarget.getHorizontalDistance(GearTarget.GEAR_TARGET_WIDTH_INCHES, HORIZONTAL_FIELD_OF_VIEW,
												IMAGE_WIDTH);
	}

	public double getAngleToGearPeg() {
		return gearTarget.getHorizontalAngle(HORIZONTAL_FIELD_OF_VIEW, IMAGE_WIDTH);
	}

	public static final double	HORIZONTAL_FIELD_OF_VIEW	= 67;
	public static final double	VERTICAL_FIELD_OF_VIEW		= 67;

	// double check
	public static final int	IMAGE_WIDTH		= 640;
	public static final int	IMAGE_HEIGHT	= 480;
}
