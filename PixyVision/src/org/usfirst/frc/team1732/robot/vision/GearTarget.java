package org.usfirst.frc.team1732.robot.vision;

public class GearTarget extends VisionTarget {

	public static final double GEAR_TARGET_WIDTH_INCHES = 10.25;

	public GearTarget(Rectangle a, Rectangle b) {
		super(a, b);
	}

	@Override
	public double getScore() {
		// left width should be 2/10.25 = 8/41 of total width
		double leftWidthInches = 2;
		double leftWidthScore = scaleScore(left.width
				* (boundingBox.width * leftWidthInches / GEAR_TARGET_WIDTH_INCHES));
		// Difference between the left edges of the contours should be
		// 8/10.25 of total width
		double dLeftInches = 8;
		double dLeft = right.x - left.x;
		double dLeftScore = scaleScore(dLeft / (boundingBox.width * dLeftInches / GEAR_TARGET_WIDTH_INCHES));
		// Difference between the tops should be close to 0 relative to
		// height
		double dTop = top.y - bottom.y;
		double dTopScore = scaleScore(dTop / boundingBox.height + 1); // add 1
		// Widths and heights should be about the same
		double widthRatioScore = scaleScore((double) left.width / right.width);
		double heightRatioScore = scaleScore((double) left.height / right.height);
		return leftWidthScore + dLeftScore + dTopScore + widthRatioScore + heightRatioScore;
	}

	public static GearTarget getBestVisionTarget(Rectangle[] rectangles) {
		if (rectangles.length < 2)
			return null;
		// determine which two of the counours actually are the correct two
		GearTarget bestPair = new GearTarget(rectangles[0], rectangles[1]);
		GearTarget pair;
		for (int i = 0; i < rectangles.length - 1; i++) {
			for (int j = i + 1; j < rectangles.length; j++) {
				pair = new GearTarget(rectangles[i], rectangles[j]);
				if (pair.getScore() > bestPair.getScore())
					bestPair = pair;
			}
		}
		return bestPair;
	}
}