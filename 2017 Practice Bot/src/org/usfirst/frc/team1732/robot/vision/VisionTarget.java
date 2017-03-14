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
	private final double	score;

	// /**
	// * Creates a new VisionTarget from a single rectangle
	// *
	// * @param rect
	// * bounding box of the blob
	// */
	// private VisionTarget(Rectangle rect) {
	// singleRectangle = true;
	// boundingBox = rect;
	// left = null;
	// right = null;
	// top = null;
	// bottom = null;
	// }

	/**
	 * Creates a new vision target from two arbitrary rectangles
	 * 
	 * @param a
	 *            first blob bounding box
	 * @param b
	 *            second blob bounding box
	 */
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
		score = this.calculateScore();
	}

	/**
	 * @return a score of how likely this pair of Rectangles is the correct
	 *         VisionTarget
	 */
	public double getScore() {
		return score;
	}

	/**
	 * Calculate this target's score
	 */
	protected abstract double calculateScore();

	/**
	 * Scales a score so that it gets returned as 1 - the distance to 1. <br>
	 * This will return a maximum of 1.
	 * 
	 * @param score
	 *            The score to be scaled, should have a target score of 1
	 * @return 1 - Math.abs(1 - score);
	 */
	private static double scaleScore(double score) {
		return 1 - Math.abs(1 - score);
	}

	public static final double defaultBase = 40;

	public static double getScore(double score, double base) {
		return Math.pow(base, scaleScore(score) - 1);
	}

	public static double getScore(double score) {
		return getScore(score, defaultBase);
	}

	// /**
	// *
	// * @param score
	// * the input score
	// * @param maxScore
	// * the maximum output score
	// * @param targetScore
	// * what you want score to be
	// * @return the score scaled so that a perfect score (score == targetScore)
	// * is scaled to a maxScore
	// */
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
	private static int getTotalWidth(Rectangle left, Rectangle right) {
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
	private static int getTotalHeight(Rectangle top, Rectangle bottom) {
		return bottom.getBottomY() - top.y;
	}

	/**
	 * Calculates the distance to the camera based on the height of the vision
	 * target in pixels
	 * 
	 * @param targetHeight
	 *            height of the target in inches / other distance unit
	 * @param verticalViewAngle
	 *            vertical view angle of the camera in degrees
	 * @param imageHeight
	 *            height of the image in pixels
	 * @return the distance to the vision target in input distance unit
	 */
	public double getVerticalDistance(double targetHeight, double verticalViewAngle, double imageHeight) {
		double inchesFOV = targetHeight * imageHeight / boundingBox.height;
		return inchesFOV / (2.0 * Math.tan(Math.toRadians(verticalViewAngle / 2.0)));
	}

	/**
	 * Calculates the angle above or below the camera of the vision target
	 * 
	 * @param verticalViewAngle
	 *            vertical view angle of the camera in preferred angle unit
	 * @param imageHeight
	 *            the height of the image in pixels
	 * @return the vertical angle to the camera (< 0 if target is above of
	 *         camera, > 0 if below of camera) in input angle unit
	 */
	public double getVerticalAngle(double verticalViewAngle, double imageHeight) {
		double percentage = (boundingBox.getCenterY() - (imageHeight / 2.0)) / imageHeight;
		return percentage * verticalViewAngle;
	}

	/**
	 * Calculates the distance to the camera based on the width of the vision
	 * target in pixels
	 * 
	 * @param targetWidth
	 *            width of the target in inches / other distance unit
	 * @param horizontalViewAngle
	 *            horizontal view angle of the camera in degrees
	 * @param imageWidth
	 *            width of the image in pixels
	 * @return the distance to the vision target in input distance unit
	 */
	public double getHorizontalDistance(double targetWidth, double horizontalViewAngle, double imageWidth) {
		double inchesFOV = targetWidth * imageWidth / boundingBox.width;
		return inchesFOV / (2.0 * Math.tan(Math.toRadians(horizontalViewAngle / 2.0)));
	}

	/**
	 * Calculates the angle left or right the camera of the vision target
	 * 
	 * @param horizontalViewAngle
	 *            horizontal view angle of the camera in preferred angle unit
	 * @param imageWidth
	 *            width of the image in pixels
	 * @return the horizontal angle to the camera (< 0 if target left of camera,
	 *         > 0 if target right of camera) in input angle unit
	 */
	public double getHorizontalAngle(double horizontalViewAngle, double imageWidth) {
		double percentage = (boundingBox.getCenterX() - (imageWidth / 2.0)) / imageWidth;
		return percentage * horizontalViewAngle;
	}

}