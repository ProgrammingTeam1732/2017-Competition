package org.usfirst.frc.team1732.robot.vision;

public abstract class VisionTarget {

	public final Rectangle	left;
	public final Rectangle	right;
	public final Rectangle	top;
	public final Rectangle	bottom;
	public final Rectangle	boundingBox;

	public VisionTarget(Rectangle a, Rectangle b) {
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

	public abstract double getScore();

	/**
	 * 
	 * @param score
	 *            The score to be scaled, should have a target score of 1
	 * @return the scaled score (1 - the distance to 1)
	 */
	public static double scaleScore(double score) {
		return scaleScore(score, 1, 1);
	}

	/**
	 * 
	 * @param score
	 *            the input score
	 * @param maxScore
	 *            the maximum output score
	 * @param targetScore
	 *            what you want score to be
	 * @return score scaled so that a perfect score (score == targetScore) is
	 *         scaled to a maxScore
	 */
	public static double scaleScore(double score, double maxScore, double targetScore) {
		return maxScore - (maxScore * Math.abs(targetScore - score) / targetScore);
	}

	public static int getTotalWidth(Rectangle left, Rectangle right) {
		return right.getRightX() - left.x;
	}

	public static int getTotalHeight(Rectangle top, Rectangle bottom) {
		return bottom.getBottomY() - top.y;
	}

	public static class Rectangle {

		public final int x, y, width, height, signature;

		public Rectangle(int signature, int x, int y, int width, int height) {
			this.signature = signature;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		public int getRightX() {
			return x + width;
		}

		public int getBottomY() {
			return y + height;
		}

		public int getCenterX() {
			return x + width / 2;
		}

		public int getCenterY() {
			return y + width / 2;
		}
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