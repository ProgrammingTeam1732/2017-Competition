package org.usfirst.frc.team1732.robot.vision;

public class GearTarget extends VisionTarget {

	public static final double	GEAR_TARGET_WIDTH_INCHES	= 10.25;
	public static final double	GEAR_TARGET_HEIGHT_INCHES	= 5;
	public static final double	MIN_TOTAL_SCORE				= 2;

	public GearTarget(Rectangle a) {
		super(a);
	}

	public GearTarget(Rectangle a, Rectangle b) {
		super(a, b);
	}

	@Override
	public double getScore() {
		double totalScore = 0;
		if (singleRectangle) {
			double ratioScore = scaleScore(((double) boundingBox.width / boundingBox.height)
					* (GEAR_TARGET_HEIGHT_INCHES / GEAR_TARGET_WIDTH_INCHES));
			totalScore = (Math.pow(ratioScore, 4)) * 5; // because
														// there
														// are
			// five below
			// if squaring scores below, remember that some can be negative so
			// do Math.abs()
		} else {
			// left width should be 2/10.25 = 8/41 of total width
			double leftWidthInches = 2;
			double leftWidthScore = scaleScore((left.width / boundingBox.width)
					* (GEAR_TARGET_WIDTH_INCHES / leftWidthInches));
			// Difference between the left edges of the contours should be
			// 8.25/10.25 of total width
			double dLeftInches = 8.25;
			double dLeft = right.x - left.x;
			double dLeftScore = scaleScore((dLeft / boundingBox.width) * (GEAR_TARGET_WIDTH_INCHES / dLeftInches));
			// Difference between the tops should be close to 0 relative to
			// height
			double dTop = top.y - bottom.y;
			double dTopScore = scaleScore((dTop / boundingBox.height) + 1); // add
																			// 1
			// Widths and heights should be about the same
			double widthRatioScore = scaleScore(((double) left.width) / right.width);
			double heightRatioScore = scaleScore(((double) left.height) / right.height);
			totalScore = leftWidthScore + dLeftScore + dTopScore + widthRatioScore + heightRatioScore;
			// System.out.println(totalScore);
		}
		return totalScore;
	}

	public static GearTarget getBestVisionTarget(Rectangle[] rectangles) {
		if (rectangles.length < 2) {
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
			//System.out.println("Double score: " + bestTarget.getScore());
			if (bestTarget.getScore() > MIN_TOTAL_SCORE) {
				return bestTarget;
			} else {
				return null;
			}
		} // else {
			// GearTarget bestTarget = new GearTarget(rectangles[0]);
			// // determine which two of the countours actually are the correct
			// two
			// for (int i = 0; i < rectangles.length; i++) {
			// GearTarget possible = new GearTarget(rectangles[i]);
			// if (possible.getScore() > bestTarget.getScore()) {
			// bestTarget = possible;
			// }
			// }
			// System.out.println("Single score: " + bestTarget.getScore());
			// if (bestTarget.getScore() > MIN_TOTAL_SCORE) {
			// return bestTarget;
			// } else {
			// return null;
			// }
			// }
	}

	@Override
	public String toString() {
		return String.valueOf(getScore());
	}
}