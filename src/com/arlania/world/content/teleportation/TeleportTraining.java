package com.arlania.world.content.teleportation;



public class TeleportTraining extends Teleporting {
	
	public static enum Training {
		TELEPORT_1(new String[] {"Yoda",""}, new int[] {2305, 4589, 0}, 0),
		TELEPORT_2(new String[] {"Aquanite", ""}, new int[] {1885,4364,0},100),
		TELEPORT_3(new String[] {"Godzilla", ""}, new int[] {3561, 9948,0},250),
		TELEPORT_4(new String[] {"Deadly", "Assassin"}, new int[] {3169, 2982, 0},500),
		TELEPORT_5(new String[] {"Donkey", "Kong"}, new int[] {2793, 2773, 0},1000),
		TELEPORT_6(new String[] {"", ""}, new int[] {},0),
		TELEPORT_7(new String[] {"", ""}, new int[] {},0),
		TELEPORT_8(new String[] {"", ""}, new int[] {},0),
		TELEPORT_9(new String[] {"", ""}, new int[] {},0),

		TELEPORT_10(new String[] {"", ""}, new int[] {},0),
		TELEPORT_11(new String[] {"", ""}, new int[] {},0),
		TELEPORT_12(new String[] {"", ""}, new int[] {},0);
		
		/**
		 * Initializing the teleport names.
		 */
		private String[] teleportName;
		/**
		 * Initializing the teleport coordinates.
		 */
		private int[] teleportCoordinates;
		private int npcKills;

		/**
		 * Constructing the enumerator.
		 * @param teleportName
		 * 			The name of the teleport.
		 * @param teleportName2
		 * 			The secondary name of the teleport.
		 * @param teleportCoordinates
		 * 			The coordinates of the teleport.
		 */
		private Training(final String[] teleportName, final int[] teleportCoordinates, int npcKills) {
			this.teleportName = teleportName;
			this.teleportCoordinates = teleportCoordinates;
			this.npcKills = npcKills;
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

		public int getReq() {
			return npcKills;
		}
	}

}

