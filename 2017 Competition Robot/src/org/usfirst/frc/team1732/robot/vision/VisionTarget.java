package org.usfirst.frc.team1732.robot.vision;

/**
 * This class represents a vision target for the 2017 competition. <br>
 * In this competition (Steamworks), all the vision targets are made of two
 * separate pieces of reflective tape, so it is necessary to combine them in
 * some way
 */
public abstract class VisionTarget {

	public final Rectangle	left;
	public final Rectangle	right;
	public final Rectangle	top;
	public final Rectangle	bottom;
	public final Rectangle	boundingBox;
	public final boolean	singleRectangle;

	public VisionTarget(Rectangle rect) {
		singleRectangle = true;
		boundingBox = rect;
		left = null;
		right = null;
		top = null;
		bottom = null;
	}

	public VisionTarget(Rectangle a, Rectangle b) {
		singleRectangle = false;
		if (a.x < b.x) {
			left = a;
			right = b;
		} else {
			left = b;
			right = a;
		}

		if (a.y < b.y) {
			top = a;
			bottom = b;
		} else {
			top = b;
			bottom = a;
		}

		boundingBox = new Rectangle(-1, left.x, top.y, getTotalWidth(left, right), getTotalHeight(top, bottom));
	}

	/**
	 * @return a score of how likely this pair of Rectangles is the correct
	 *         Vision target
	 */
	public abstract double getScore();

	/**
	 * Scales a score so that it gets returned as 1 - the distance to 1. <br>
	 * This will return a maximum of 1.
	 * 
	 * @param score
	 *            The score to be scaled, should have a target score of 1
	 * @return 1 - Math.abs(1 - score);
	 */
	public static double scaleScore(double score) {
		return 1 - Math.abs(1 - score);
		// return scaleScore(score, 1, 1);
	}

	/**
	 * 
	 * @param score
	 *            the input score
	 * @param maxScore
	 *            the maximum output score
	 * @param targetScore
	 *            what you want score to be
	 * @return the score scaled so that a perfect score (score == targetScore)
	 *         is scaled to a maxScore
	 */
	// public static double scaleScore(double score, double maxScore, double
	// targetScore) {
	// return maxScore - (maxScore * Math.abs(targetScore - score) /
	// targetScore);
	// }

	/**
	 * @param left
	 *            the left Rectangle
	 * @param right
	 *            the right Rectangle
	 * @return the difference in pixels between the right edge of the right
	 *         rectangle and the left edge of the left rectangle
	 */
	public static int getTotalWidth(Rectangle left, Rectangle right) {
		return right.getRightX() - left.x;
	}

	/**
	 * @param top
	 *            the top Rectangle
	 * @param bottom
	 *            the bottom Rectangle
	 * @return the difference in pixels between the bototm edge of the bottom
	 *         rectangle and the top edge of the top rectangle
	 */
	public static int getTotalHeight(Rectangle top, Rectangle bottom) {
		return bottom.getBottomY() - top.y;
	}

	public double getVerticalDistance(double targetHeight, double verticalViewAngle, double imageHeight) {
		double inchesFOV = targetHeight * imageHeight / boundingBox.height;
		return inchesFOV / (2.0 * Math.tan(Math.toRadians(verticalViewAngle / 2.0)));
	}

	public double getVerticalAngle(double verticalViewAngle, double imageHeight) {
		double percentage = (boundingBox.getCenterY() - (imageHeight / 2.0)) / imageHeight;
		return percentage * verticalViewAngle;
	}

	public double getHorizontalDistance(double targetWidth, double horizontalViewAngle, double imageWidth) {
		double inchesFOV = targetWidth * imageWidth / boundingBox.width;
		return inchesFOV / (2.0 * Math.tan(Math.toRadians(horizontalViewAngle / 2.0)));
	}

	public double getHorizontalAngle(double horizontalViewAngle, double imageWidth) {
		double percentage = (boundingBox.getCenterX() - (imageWidth / 2.0)) / imageWidth;
		return percentage * horizontalViewAngle;
	}

}