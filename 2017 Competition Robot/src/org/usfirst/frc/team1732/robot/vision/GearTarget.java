package org.usfirst.frc.team1732.robot.vision;

public class GearTarget extends VisionTarget {

	public static final double	GEAR_TARGET_WIDTH_INCHES	= 10.25;
	public static final double	GEAR_TARGET_HEIGHT_INCHES	= 5;
	public static final double	GEAR_TARGET_AREA			= GEAR_TARGET_WIDTH_INCHES * GEAR_TARGET_HEIGHT_INCHES;
	public static final double	GEAR_TARGET_TAPE_WIDTH		= 2;
	public static final double	GEAR_TARGET_TAPE_AREA		= GEAR_TARGET_HEIGHT_INCHES * GEAR_TARGET_TAPE_WIDTH;

	/**
	 * The minimum score (max of 5) a gearTarget needs in order to be considered
	 * a valid gear target
	 */
	public static final double MIN_TOTAL_SCORE = 2;

	// public GearTarget(Rectangle a) {
	// super(a);
	// }

	public GearTarget(Rectangle a, Rectangle b) {
		super(a, b);
	}

	@Override
	protected double calculateScore() {
		double totalScore = 0;

		double gearLeftWidth_WholeWidthRatio = GEAR_TARGET_TAPE_WIDTH / GEAR_TARGET_WIDTH_INCHES;
		double imageLeftWidth_WholeWidthRatio = (double) left.width / boundingBox.width;
		double leftWidthScore = getScore(imageLeftWidth_WholeWidthRatio / gearLeftWidth_WholeWidthRatio);
		totalScore += leftWidthScore;

		double imageRightWidth_WholeWidthRatio = (double) right.width / boundingBox.width;
		double rightWidthScore = getScore(imageRightWidth_WholeWidthRatio / gearLeftWidth_WholeWidthRatio);
		totalScore += rightWidthScore;

		// Difference between the left edges of the tapes should be
		// 8.25/10.25 of total width
		double dLeftPixels = right.x - left.x;
		double imageDLeft_WidthRatio = dLeftPixels / boundingBox.width;
		double dLeftInches = GEAR_TARGET_WIDTH_INCHES - GEAR_TARGET_TAPE_WIDTH;
		double gearDLeftInches_WidthInchesRatio = dLeftInches / GEAR_TARGET_WIDTH_INCHES;
		double dLeftScore = getScore(imageDLeft_WidthRatio / gearDLeftInches_WidthInchesRatio);

		totalScore += dLeftScore;

		// Difference between the tops should be close to 0 relative to
		// height
		double dTop = top.y - bottom.y;
		double dTopScore = getScore((dTop / boundingBox.height) + 1);
		totalScore += dTopScore;

		// Widths and heights should be about the same
		double widthRatioScore = getScore(((double) left.width) / right.width);
		totalScore += widthRatioScore;
		double heightRatioScore = getScore(((double) left.height) / right.height);
		totalScore += heightRatioScore;

		double imageWidthHeightRatio = boundingBox.width / boundingBox.height;
		double gearWidthHeightRatio = GEAR_TARGET_WIDTH_INCHES / GEAR_TARGET_HEIGHT_INCHES;
		double widthHeightRatioScore = getScore(imageWidthHeightRatio / gearWidthHeightRatio);
		widthHeightRatioScore = widthHeightRatioScore * widthHeightRatioScore;
		totalScore += widthHeightRatioScore;

		double gearTargetTapeAreaTotalAreaRatio = GEAR_TARGET_TAPE_AREA * 2.0 / GEAR_TARGET_AREA;
		double imageTapeAreaTotalAreaRatio = (left.getArea() + right.getArea()) / boundingBox.getArea();
		double areaScore = getScore(imageTapeAreaTotalAreaRatio / gearTargetTapeAreaTotalAreaRatio);
		areaScore = areaScore * areaScore;
		totalScore += areaScore;

		return totalScore;
	}

	/**
	 * @param rectangles
	 *            input array of rectangles from camera
	 * @return which pair of rectangles is most likely to be the gear target, or
	 *         null if none are good enough
	 */
	public static GearTarget getBestVisionTarget(Rectangle[] rectangles) {
		if (rectangles == null || rectangles.length < 2) {
			return null;
		} else {// else if (rectangles.length > 1) {
			GearTarget bestTarget = new GearTarget(rectangles[0], rectangles[1]);
			for (int i = 0; i < rectangles.length - 1; i++) {
				for (int j = i + 1; j < rectangles.length; j++) {
					GearTarget possiblePair = new GearTarget(rectangles[i], rectangles[j]);
					if (possiblePair.getScore() > bestTarget.getScore())
						bestTarget = possiblePair;
				}
			}
			// System.out.println("Double score: " + bestTarget.getScore());
			if (bestTarget.getScore() > MIN_TOTAL_SCORE) {
				return bestTarget;
			} else {
				return null;
			}
		}
	}

	@Override
	public String toString() {
		return String.valueOf(getScore());
	}
}