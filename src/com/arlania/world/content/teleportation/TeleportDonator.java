package com.arlania.world.content.teleportation;

public class TeleportDonator extends Teleporting {
	public static enum Donator {
		TELEPORT_1(new String[] {"Slayer", "Tower"}, new int[] {3429, 3538, 0}),
		TELEPORT_2(new String[] {"", ""}, new int[] {}),
		TELEPORT_3(new String[] {"", ""}, new int[] {}),
		TELEPORT_4(new String[] {"", ""}, new int[] {}),
		TELEPORT_5(new String[] {"", ""}, new int[] {}),
		TELEPORT_6(new String[] {"", ""}, new int[] {}),
		TELEPORT_7(new String[] {"", ""}, new int[] {}),
		TELEPORT_8(new String[] {"", ""}, new int[] {}),
		TELEPORT_9(new String[] {"", ""}, new int[] {}),
		TELEPORT_10(new String[] {"", ""}, new int[] {}),
		TELEPORT_11(new String[] {" ", " "}, new int[] {}),
		TELEPORT_12(new String[] {" ", " "}, new int[] {});

		/**
		 * Initializing the teleport names.33
		 */
		private String[] teleportName;
		/**
		 * Initializing the teleport coordinates.
		 */
		private int[] teleportCoordinates;

		/**
		 * Constructing the enumerator.
		 * @param teleportName
		 * 			The name of the teleport.
		 * @param teleportName2
		 * 			The secondary name of the teleport.
		 * @param teleportCoordinates
		 * 			The coordinates of the teleport.
		 */
		private Donator(final String[] teleportName, final int[] teleportCoordinates) {
			this.teleportName = teleportName;
			this.teleportCoordinates = teleportCoordinates;
		}
		/**
		 * Setting the teleport name.
		 * @return
		 */
		public String[] getTeleportName() {
			return teleportName;
		}
		/**
		 * Setting the teleport coordinates.
		 * @return
		 */
		public int[] getCoordinates() {
			return teleportCoordinates;
		}
	}

}
