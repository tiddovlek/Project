package com.arlania.world.content.chests;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.GameObject;
import com.arlania.model.Item;
import com.arlania.model.PlayerRights;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.entity.impl.player.Player;

public class WildyWyrmChest {

	public static void handleChest(final Player p, final GameObject chest) {
		if(!p.getClickDelay().elapsed(2000)) 
			return;
		if(!p.getInventory().contains(19933)) {
			p.getPacketSender().sendMessage("This chest can only be opened with a WildyWyrm key.");
			return;
		}
		p.performAnimation(new Animation(827));
		if (p.getRights() == PlayerRights.DONATOR) {
			if (Misc.getRandom(15) == 5) {
				p.getPacketSender().sendMessage("WildyWyrm Key has been saved as a donator benefit");
			} else {
				p.getInventory().delete(19933, 1);
			}
		}
		if (p.getRights() == PlayerRights.SUPER_DONATOR || p.getRights() == PlayerRights.SUPPORT) {
			if (Misc.getRandom(12) == 5) {
				p.getPacketSender().sendMessage("WildyWyrm Key has been saved as a donator benefit");
			} else {
				p.getInventory().delete(19933, 1);
			}
		}
		if (p.getRights() == PlayerRights.EXTREME_DONATOR || p.getRights() == PlayerRights.MODERATOR) {
			if (Misc.getRandom(9) == 5) {
				p.getPacketSender().sendMessage("WildyWyrm Key has been saved as a donator benefit");
			} else {
				p.getInventory().delete(19933, 1);
			}
		}
		if (p.getRights() == PlayerRights.LEGENDARY_DONATOR  || p.getRights() == PlayerRights.ADMINISTRATOR) {
			if (Misc.getRandom(6) == 5) {
				p.getPacketSender().sendMessage("WildyWyrm Key has been saved as a donator benefit");
			} else {
				p.getInventory().delete(19933, 1);
			}
		}
		if (p.getRights() == PlayerRights.UBER_DONATOR || p.getRights() == PlayerRights.DEVELOPER) {
			if (Misc.getRandom(3) == 2) {
				p.getPacketSender().sendMessage("WildyWyrm Key has been saved as a donator benefit");
			} else {
				p.getInventory().delete(19933, 1);
			}
		}
		if (p.getRights() == PlayerRights.PLAYER || p.getRights() == PlayerRights.YOUTUBER) {
			p.getInventory().delete(19933, 1);
		}
		p.getPacketSender().sendMessage("You open the chest..");
	
					Item[] loot = itemRewards[Misc.getRandom(itemRewards.length - 1)];
					for(Item item : loot) {
						p.getInventory().add(item);
					}
					p.getInventory().add(995, 50000 + RandomUtility.RANDOM.nextInt(100000));
				
					//CustomObjects.objectRespawnTask(p, new GameObject(173 , chest.getPosition().copy(), 10, 0), chest, 10);
				
	}

	private static final Item[][] itemRewards =  {
			{new Item(995, 10000000)}, //10m
			{new Item(995, 12000000)}, //12m
			{new Item(995, 15000000)}, //15m
			{new Item(995, 20000000)}, //20m
			{new Item(995, 25000000)}, //25m
			{new Item(995, 50000000)}, //50m
			{new Item(995, 10000000)}, //10m
			{new Item(995, 12000000)}, //12m
			{new Item(995, 15000000)}, //15m
			{new Item(995, 20000000)}, //20m
			{new Item(995, 25000000)}, //25m
			{new Item(995, 50000000)}, //50m
			{new Item(995, 10000000)}, //10m
			{new Item(995, 12000000)}, //12m
			{new Item(995, 15000000)}, //15m
			{new Item(995, 20000000)}, //20m
			{new Item(995, 25000000)}, //25m
			{new Item(995, 50000000)}, //50m
			{new Item(392, 100)}, //MANTA RAYS
			{new Item(392, 250)}, //MANTA RAYS
			{new Item(392, 500)}, //MANTA RAYS
			{new Item(1514, 100)}, //Magic logs
			{new Item(1514, 250)}, //Magic logs
			{new Item(1514, 500)}, //Magic logs
			{new Item(3025, 25)}, //Super restore
			{new Item(3025, 50)}, //Super restore
			{new Item(3025, 100)}, //Super restore
			{new Item(6686, 25)}, //Brew
			{new Item(6686, 50)}, //Brew
			{new Item(6686, 100)}, //Brew
			{new Item(2, 250)}, //Cannonballs
			{new Item(2, 500)}, //Cannonballs
			{new Item(2, 1000)}, //Cannonballs
			{new Item(995, 10000000)}, //10m
			{new Item(995, 12000000)}, //12m
			{new Item(995, 15000000)}, //15m
			{new Item(995, 20000000)}, //20m
			{new Item(995, 25000000)}, //25m
			{new Item(995, 50000000)}, //50m
			{new Item(995, 10000000)}, //10m
			{new Item(995, 12000000)}, //12m
			{new Item(995, 15000000)}, //15m
			{new Item(995, 20000000)}, //20m
			{new Item(995, 25000000)}, //25m
			{new Item(995, 50000000)}, //50m
			{new Item(995, 10000000)}, //10m
			{new Item(995, 12000000)}, //12m
			{new Item(995, 15000000)}, //15m
			{new Item(995, 20000000)}, //20m
			{new Item(995, 25000000)}, //25m
			{new Item(995, 50000000)}, //50m
			{new Item(392, 100)}, //MANTA RAYS
			{new Item(392, 250)}, //MANTA RAYS
			{new Item(392, 500)}, //MANTA RAYS
			{new Item(1514, 100)}, //Magic logs
			{new Item(1514, 250)}, //Magic logs
			{new Item(1514, 500)}, //Magic logs
			{new Item(3025, 25)}, //Super restore
			{new Item(3025, 50)}, //Super restore
			{new Item(3025, 100)}, //Super restore
			{new Item(6686, 25)}, //Brew
			{new Item(6686, 50)}, //Brew
			{new Item(6686, 100)}, //Brew
			{new Item(2, 250)}, //Cannonballs
			{new Item(2, 500)}, //Cannonballs
			{new Item(2, 1000)}, //Cannonballs
			{new Item(392, 100)}, //MANTA RAYS
			{new Item(392, 250)}, //MANTA RAYS
			{new Item(392, 500)}, //MANTA RAYS
			{new Item(1514, 100)}, //Magic logs
			{new Item(1514, 250)}, //Magic logs
			{new Item(1514, 500)}, //Magic logs
			{new Item(3025, 25)}, //Super restore
			{new Item(3025, 50)}, //Super restore
			{new Item(3025, 100)}, //Super restore
			{new Item(6686, 25)}, //Brew
			{new Item(6686, 50)}, //Brew
			{new Item(6686, 100)}, //Brew
			{new Item(2, 250)}, //Cannonballs
			{new Item(2, 500)}, //Cannonballs
			{new Item(2, 1000)}, //Cannonballs
			{new Item(995, 10000000)}, //10m
			{new Item(995, 12000000)}, //12m
			{new Item(995, 15000000)}, //15m
			{new Item(995, 20000000)}, //20m
			{new Item(995, 25000000)}, //25m
			{new Item(995, 50000000)}, //50m
			{new Item(995, 10000000)}, //10m
			{new Item(995, 12000000)}, //12m
			{new Item(995, 15000000)}, //15m
			{new Item(995, 20000000)}, //20m
			{new Item(995, 25000000)}, //25m
			{new Item(995, 50000000)}, //50m
			{new Item(995, 10000000)}, //10m
			{new Item(995, 12000000)}, //12m
			{new Item(995, 15000000)}, //15m
			{new Item(995, 20000000)}, //20m
			{new Item(995, 25000000)}, //25m
			{new Item(995, 50000000)}, //50m
			{new Item(392, 100)}, //MANTA RAYS
			{new Item(392, 250)}, //MANTA RAYS
			{new Item(392, 500)}, //MANTA RAYS
			{new Item(1514, 100)}, //Magic logs
			{new Item(1514, 250)}, //Magic logs
			{new Item(1514, 500)}, //Magic logs
			{new Item(3025, 25)}, //Super restore
			{new Item(3025, 50)}, //Super restore
			{new Item(3025, 100)}, //Super restore
			{new Item(6686, 25)}, //Brew
			{new Item(6686, 50)}, //Brew
			{new Item(6686, 100)}, //Brew
			{new Item(2, 250)}, //Cannonballs
			{new Item(2, 500)}, //Cannonballs
			{new Item(2, 1000)}, //Cannonballs
			{new Item(995, 10000000)}, //10m
			{new Item(995, 12000000)}, //12m
			{new Item(995, 15000000)}, //15m
			{new Item(995, 20000000)}, //20m
			{new Item(995, 25000000)}, //25m
			{new Item(995, 50000000)}, //50m
			{new Item(995, 10000000)}, //10m
			{new Item(995, 12000000)}, //12m
			{new Item(995, 15000000)}, //15m
			{new Item(995, 20000000)}, //20m
			{new Item(995, 25000000)}, //25m
			{new Item(995, 50000000)}, //50m
			{new Item(995, 10000000)}, //10m
			{new Item(995, 12000000)}, //12m
			{new Item(995, 15000000)}, //15m
			{new Item(995, 20000000)}, //20m
			{new Item(995, 25000000)}, //25m
			{new Item(995, 50000000)}, //50m
			{new Item(392, 100)}, //MANTA RAYS
			{new Item(392, 250)}, //MANTA RAYS
			{new Item(392, 500)}, //MANTA RAYS
			{new Item(1514, 100)}, //Magic logs
			{new Item(1514, 250)}, //Magic logs
			{new Item(1514, 500)}, //Magic logs
			{new Item(3025, 25)}, //Super restore
			{new Item(3025, 50)}, //Super restore
			{new Item(3025, 100)}, //Super restore
			{new Item(392, 100)}, //MANTA RAYS
			{new Item(392, 250)}, //MANTA RAYS
			{new Item(392, 500)}, //MANTA RAYS
			{new Item(1514, 100)}, //Magic logs
			{new Item(1514, 250)}, //Magic logs
			{new Item(1514, 500)}, //Magic logs
			{new Item(3025, 25)}, //Super restore
			{new Item(3025, 50)}, //Super restore
			{new Item(3025, 100)}, //Super restore
			{new Item(392, 100)}, //MANTA RAYS
			{new Item(392, 250)}, //MANTA RAYS
			{new Item(392, 500)}, //MANTA RAYS
			{new Item(1514, 100)}, //Magic logs
			{new Item(1514, 250)}, //Magic logs
			{new Item(1514, 500)}, //Magic logs
			{new Item(3025, 25)}, //Super restore
			{new Item(3025, 50)}, //Super restore
			{new Item(3025, 100)}, //Super restore
			{new Item(392, 100)}, //MANTA RAYS
			{new Item(392, 250)}, //MANTA RAYS
			{new Item(392, 500)}, //MANTA RAYS
			{new Item(1514, 100)}, //Magic logs
			{new Item(1514, 250)}, //Magic logs
			{new Item(1514, 500)}, //Magic logs
			{new Item(3025, 25)}, //Super restore
			{new Item(3025, 50)}, //Super restore
			{new Item(3025, 100)}, //Super restore
			{new Item(392, 100)}, //MANTA RAYS
			{new Item(392, 250)}, //MANTA RAYS
			{new Item(392, 500)}, //MANTA RAYS
			{new Item(1514, 100)}, //Magic logs
			{new Item(1514, 250)}, //Magic logs
			{new Item(1514, 500)}, //Magic logs
			{new Item(3025, 25)}, //Super restore
			{new Item(3025, 50)}, //Super restore
			{new Item(3025, 100)}, //Super restore
			{new Item(6686, 25)}, //Brew
			{new Item(6686, 50)}, //Brew
			{new Item(6686, 100)}, //Brew
			{new Item(569, 100)}, //FUEL
			{new Item(569, 100)}, //FUEL
			{new Item(569, 100)}, //FUEL
			{new Item(569, 100)}, //FUEL
			{new Item(569, 100)}, //FUEL
			{new Item(569, 100)}, //FUEL
			{new Item(569, 100)}, //FUEL
			{new Item(569, 100)}, //FUEL
			{new Item(569, 100)}, //FUEL
			{new Item(569, 100)}, //FUEL
			{new Item(569, 100)}, //FUEL
			{new Item(20079, 1)}, //INFERNAL WHIP
			{new Item(20080, 1)}, //INFERNAL PROTECTOR
			{new Item(20090, 1)}, //LAVA SPIRITSHIELD
			{new Item(20086, 1)}, //Conquerer
			{new Item(20087, 1)}, //Conquerer
			{new Item(20088, 1)}, //Conquerer
			{new Item(20089, 1)}, //rainbow brutal whip
			{new Item(2, 250)}, //Cannonballs
			{new Item(2, 500)}, //Cannonballs
			{new Item(2, 1000)}, //Cannonballs
			{new Item(13045, 1)}, //BLUDGEON
			{new Item(13047, 1)}, //DAGGER
			{new Item(20555, 1)}, //DWH
			{new Item(12926, 1)}, //BLOWPIPE
			{new Item(20061, 1)}, //VINE WHIP
			{new Item(6821, 1)}, //MONEY ORB
			{new Item(1969, 1)}, //REKT
			
		};
}
