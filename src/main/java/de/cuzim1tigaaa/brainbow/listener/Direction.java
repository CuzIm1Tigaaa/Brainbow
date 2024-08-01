package de.cuzim1tigaaa.brainbow.listener;

import org.bukkit.Location;

public enum Direction {
	SOUTH,
	WEST,
	NORTH,
	EAST;

	private static final Direction[] directions = { SOUTH, WEST, NORTH, EAST };

	public static Direction getDirection(Location location) {
		float yaw = location.getYaw();
		yaw += 180;
		if(yaw < 0)
			yaw += 360;
		yaw %= 360;

		if(yaw > 315 || yaw <= 45)
			return NORTH;

		if(yaw > 45 && yaw <= 135)
			return EAST;

		if(yaw > 135 && yaw <= 225)
			return SOUTH;

		if(yaw > 225)
			return WEST;

		return NORTH;
	}
}