package org.usfirst.frc.team1732.robot.vision;

/**
 * Class that represents a possible boiler target
 */
public class BoilerTarget extends VisionTarget {

	public static final double	BOILER_TARGET_HEIGHT_INCHES	= 10;
	public static final double	BOILER_TARGET_WIDTH_INCHES	= 15;

	public static final double	BOILER_TARGET_UPPER_TAPE_HEIGHT		= 4;
	public static final double	BOILER_TARGET_EMPTY_SPACE_HEIGHT	= 4;
	public static final double	BOILER_TARGET_LOWER_TAPE_HEIGHT		= 2;

	public static final double MIN_TOTAL_SCORE = 0;

	public BoilerTarget(Rectangle a, Rectangle b) {
		super(a, b);
	}

	@Override
	protected double calculateScore() {
		// top height should be 2/10 of the total height
		double topHeightInches = 4;
		double topHeightScore = getScore(top.height
				* (boundingBox.height * topHeightInches / BOILER_TARGET_HEIGHT_INCHES));
		// Difference between the top edges of the contours should be
		// 6/10 of total height
		double dTopInches = 6;
		double dTop = bottom.y - top.y;
		double dTopScore = getScore(dTop / (boundingBox.width * dTopInches / BOILER_TARGET_HEIGHT_INCHES));
		// Difference between the left edges should be close to 0 relative to
		// width
		double dLeft = right.x - left.x;
		double dLeftScore = getScore(dLeft / boundingBox.width + 1); // add 1
		// Widths and heights should be about the same
		double widthRatioScore = getScore((double) left.width / right.width);
		double heightRatioScore = getScore((double) left.height / right.height);
		return topHeightScore + dLeftScore + dTopScore + widthRatioScore + heightRatioScore;
	}

	/**
	 * @param rectangles
	 *            the array of rectangle blobs the camera sees
	 * @return the pair of rectangles most likely to represent a BoilerTarget
	 */
	public static BoilerTarget getBestVisionTarget(Rectangle[] rectangles) {
		if (rectangles == null || rectangles.length < 2) {
			return null;
		} else {// else if (rectangles.length > 1) {
			BoilerTarget bestTarget = new BoilerTarget(rectangles[0], rectangles[1]);
			for (int i = 0; i < rectangles.length - 1; i++) {
				for (int j = i + 1; j < rectangles.length; j++) {
					BoilerTarget possiblePair = new BoilerTarget(rectangles[i], rectangles[j]);
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