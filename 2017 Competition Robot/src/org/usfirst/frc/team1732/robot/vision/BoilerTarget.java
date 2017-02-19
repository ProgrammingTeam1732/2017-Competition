package org.usfirst.frc.team1732.robot.vision;

public class BoilerTarget extends VisionTarget {

	public static final double BOILER_TARGET_HEIGHT_INCHES = 10;

	public BoilerTarget(Rectangle a, Rectangle b) {
		super(a, b);
	}

	@Override
	public double getScore() {
		// top height should be 2/10 of the total height
		double topHeightInches = 4;
		double topHeightScore = scaleScore(top.height
				* (boundingBox.height * topHeightInches / BOILER_TARGET_HEIGHT_INCHES));
		// Difference between the top edges of the contours should be
		// 6/10 of total height
		double dTopInches = 6;
		double dTop = bottom.y - top.y;
		double dTopScore = scaleScore(dTop / (boundingBox.width * dTopInches / BOILER_TARGET_HEIGHT_INCHES));
		// Difference between the left edges should be close to 0 relative to
		// width
		double dLeft = right.x - left.x;
		double dLeftScore = scaleScore(dLeft / boundingBox.width + 1); // add 1
		// Widths and heights should be about the same
		double widthRatioScore = scaleScore((double) left.width / right.width);
		double heightRatioScore = scaleScore((double) left.height / right.height);
		return topHeightScore + dLeftScore + dTopScore + widthRatioScore + heightRatioScore;
	}

	public static BoilerTarget getBestVisionTarget(Rectangle[] rectangles) {
		if (rectangles.length < 2)
			return null;
		// determine which two of the counours actually are the correct two
		BoilerTarget bestPair = new BoilerTarget(rectangles[0], rectangles[1]);
		BoilerTarget pair;
		for (int i = 0; i < rectangles.length - 1; i++) {
			for (int j = i + 1; j < rectangles.length; j++) {
				pair = new BoilerTarget(rectangles[i], rectangles[j]);
				if (pair.getScore() > bestPair.getScore())
					bestPair = pair;
			}
		}
		return bestPair;
	}

}