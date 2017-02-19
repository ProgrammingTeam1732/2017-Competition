package org.usfirst.frc.team1732.robot.vision;

@Deprecated
public class VisionThread implements Runnable {

	public static final double	HORIZONTAL_FIELD_OF_VIEW	= 75;
	public static final double	VERTICAL_FIELD_OF_VIEW		= 47;

	// double check
	public static final int	IMAGE_WIDTH		= 320;
	public static final int	IMAGE_HEIGHT	= 200;

	public GearTarget	gearTarget;
	public BoilerTarget	boilerTarget;
	public Arduino		arduino	= new Arduino();
	private String		total	= "";
	private boolean		found	= false;
	private boolean		started	= false;

	@Override
	public void run() {
		Rectangle[] rectangles = new Rectangle[0];
		while (!Thread.interrupted()) {
			String s = arduino.getData();
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
							} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {

							}
						}
						try {
							gearTarget = GearTarget.getBestVisionTarget(rectangles);
							if (gearTarget != null) {
								System.out.println(gearTarget.boundingBox);
								System.out.println("Distance: " + getInchesToGearPeg());
								System.out.println();
							}
						} catch (NullPointerException e) {

						}
					}
					total = "";
				}
				// change this later
				// rectangles = new Rectangle[0];
				// System.out.println(s);
			}
		}
		System.out.println("Thread inturrupted");
	}
	// public void run(){
	// while(!Thread.interrupted()){
	// String s = arduino.getData();
	// if()
	// System.out.println(s);
	// }
	// }

	public synchronized double getInchesToGearPeg() {
		if (gearTarget == null)
			return -1;
		return gearTarget.getHorizontalDistance(GearTarget.GEAR_TARGET_WIDTH_INCHES, HORIZONTAL_FIELD_OF_VIEW,
												IMAGE_WIDTH);
	}

	public synchronized double getAngleToGearPeg() {
		return gearTarget.getHorizontalAngle(HORIZONTAL_FIELD_OF_VIEW, IMAGE_WIDTH);
	}
}
