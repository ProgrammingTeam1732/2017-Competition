package org.usfirst.frc.team1732.robot.vision;

public class VisionMain {

	public static final double	HORIZONTAL_FIELD_OF_VIEW	= 75;
	public static final double	VERTICAL_FIELD_OF_VIEW		= 47;

	// double check
	public static final int	IMAGE_WIDTH		= 320;
	public static final int	IMAGE_HEIGHT	= 200;

	private Arduino arduino;

	public GearTarget	gearTarget;
	public BoilerTarget	boilerTarget;
	private String		total	= "";
	private boolean		found	= false;
	private boolean		started	= false;

	private Rectangle[] rectangles = new Rectangle[0];

	public VisionMain() {
		arduino = new Arduino();
	}

	public void run() {
		String s = arduino.getData();
		parseData(s);
		updateGearTarget();
	}

	private void parseData(String s) {
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
					rectangles = new Rectangle[rects.length];
					for (int j = 0; j < rects.length; j++) {
						String[] data = rects[j].split(" ");
						try {
							rectangles[j] = new Rectangle(	Integer.parseInt(data[1]), Integer.parseInt(data[3]),
															Integer.parseInt(data[5]), Integer.parseInt(data[7]),
															Integer.parseInt(data[9]));
							System.out.println(j + ": " + rectangles[j]);
						} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {}
					}
				}
				total = "";
			}
		}
	}

	private void updateGearTarget() {
		try {
			gearTarget = GearTarget.getBestVisionTarget(rectangles);
		} catch (NullPointerException e) {
			e.getMessage();
		}
	}

	public double getInchesToGearPeg() {
		if (gearTarget == null)
			return -1;
		return gearTarget.getHorizontalDistance(GearTarget.GEAR_TARGET_WIDTH_INCHES, HORIZONTAL_FIELD_OF_VIEW,
												IMAGE_WIDTH);
	}

	public double getAngleToGearPeg() {
		return gearTarget.getHorizontalAngle(HORIZONTAL_FIELD_OF_VIEW, IMAGE_WIDTH);
	}
}