package com.arlania.world.content;

import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

public class PetBox {
	
	/*
	 * Rewards
	 */
	public static final int [] petRewards = {2090, 2103, 7582, 7583, 2771, 2772, 3620, 3621, 3622, 19935, 19936, 19937, 2758, 2759, 2760, 2761, 2762};
	
	/*
	 * Handles the opening of the donation box
	 */
	public static void OpenPetBox(Player player) {
		if (player.getInventory().getFreeSlots() < 3) {
			player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
			return;
		}
		player.getInventory().delete(19934, 1);
		player.getInventory().add(petRewards[Misc.getRandom(petRewards.length - 1)], 1);
		player.getInventory().add(995, 10000000 + RandomUtility.RANDOM.nextInt(900000000));
		player.getPacketSender().sendMessage("@blu@C@whi@o@blu@n@whi@g@blu@r@whi@a@blu@t@whi@u@blu@l@whi@a@blu@t@whi@i@blu@o@whi@n@blu@s @whi@o@blu@n @whi@y@blu@o@whi@u@blu@r @whi@n@blu@e@whi@w @blu@p@whi@e@blu@t@whi@!");
	}
	


}
