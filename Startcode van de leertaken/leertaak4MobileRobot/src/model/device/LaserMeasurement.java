package model.device;

/**
 * Title    :   The Mobile Robot Explorer Simulation Environment v2.0
 * Copyright:   GNU General Public License as published by the Free Software Foundation
 * Company  :   Hanze University of Applied Sciences
 *
 * @author Dustin Meijer        (2012)
 * @author Alexander Jeurissen  (2012)
 * @author Davide Brugali       (2002)
 * @version 2.0
 */

// Dustin :
// This class can be made default so it's only visible for in the package,
// however the discussion remains if default is really a good visibility modifier to apply.
// This was a private inner class, so atleast this is a step forward.
public class LaserMeasurement {

	protected double distance;
	protected double direction;

	protected LaserMeasurement(double distance, double direction) {
		this.set(distance, direction);
		this.processDirectionValue();
	}

	protected void set(double distance, double direction) {
		this.distance = distance;
		this.direction = direction;
	}


	private void processDirectionValue() {
		while (direction >= 2.0 * Math.PI)
			direction -= 2.0 * Math.PI;
		while (direction < 0.0)
			direction += 2.0 * Math.PI;
	}

}
