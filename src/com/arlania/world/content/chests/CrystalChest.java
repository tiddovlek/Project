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

public class CrystalChest {

	public static void handleChest(final Player p, final GameObject chest) {
		if(!p.getClickDelay().elapsed(2000)) 
			return;
		if(!p.getInventory().contains(989)) {
			p.getPacketSender().sendMessage("This chest can only be opened with a Crystal key.");
			return;
		}
		p.performAnimation(new Animation(827));
		if (p.getRights() == PlayerRights.DONATOR) {
			if (Misc.getRandom(15) == 5) {
				p.getPacketSender().sendMessage("Crystal Key has been saved as a donator benefit");
			} else {
				p.getInventory().delete(989, 1);
			}
		}
		if (p.getRights() == PlayerRights.SUPER_DONATOR || p.getRights() == PlayerRights.SUPPORT) {
			if (Misc.getRandom(12) == 5) {
				p.getPacketSender().sendMessage("Crystal Key has been saved as a donator benefit");
			} else {
				p.getInventory().delete(989, 1);
			}
		}
		if (p.getRights() == PlayerRights.EXTREME_DONATOR || p.getRights() == PlayerRights.MODERATOR) {
			if (Misc.getRandom(9) == 5) {
				p.getPacketSender().sendMessage("Crystal Key has been saved as a donator benefit");
			} else {
				p.getInventory().delete(989, 1);
			}
		}
		if (p.getRights() == PlayerRights.LEGENDARY_DONATOR  || p.getRights() == PlayerRights.ADMINISTRATOR) {
			if (Misc.getRandom(6) == 5) {
				p.getPacketSender().sendMessage("Crystal Key has been saved as a donator benefit");
			} else {
				p.getInventory().delete(989, 1);
			}
		}
		if (p.getRights() == PlayerRights.UBER_DONATOR || p.getRights() == PlayerRights.DEVELOPER) {
			if (Misc.getRandom(3) == 2) {
				p.getPacketSender().sendMessage("Crystal Key has been saved as a donator benefit");
			} else {
				p.getInventory().delete(989, 1);
			}
		}
		if (p.getRights() == PlayerRights.PLAYER || p.getRights() == PlayerRights.YOUTUBER) {
			p.getInventory().delete(989, 1);
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
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(3481, 1)}, //gilded plate
			{new Item(3483, 1)}, //gilded legs
			{new Item(3485, 1)}, //gilded skirt
			{new Item(3486, 1)}, //gilded helm
			{new Item(3488, 1)}, //gilded shield
			{new Item(2581, 1)}, //robin
			{new Item(2577, 1)}, //rangers
			{new Item(2581, 1)}, //robin
			{new Item(2577, 1)}, //rangers
			{new Item(2581, 1)}, //robin
			{new Item(2577, 1)}, //rangers
			{new Item(11235, 1)}, //dbow
			{new Item(11235, 1)}, //dbow
			{new Item(11235, 1)}, //dbow
			{new Item(11235, 1)}, //dbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(15241, 1)}, //hand cannon
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(19670, 1)}, //vote scroll
			{new Item(6585, 1)}, //fury
			{new Item(4151, 1)}, //whip
			{new Item(19670, 1)}, //vote scroll
			{new Item(6585, 1)}, //fury
			{new Item(4151, 1)}, //whip
			{new Item(19670, 1)}, //vote scroll
			{new Item(6585, 1)}, //fury
			{new Item(4151, 1)}, //whip
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(7368, 1)}, //studded t
			{new Item(7364, 1)}, //studded t
			{new Item(7368, 1)}, //studded t
			{new Item(7364, 1)}, //studded t
			{new Item(7368, 1)}, //studded t
			{new Item(7364, 1)}, //studded t
			{new Item(7362, 1)}, //studded g
			{new Item(7366, 1)}, //studded g
			{new Item(7362, 1)}, //studded g
			{new Item(7366, 1)}, //studded g
			{new Item(7362, 1)}, //studded g
			{new Item(7366, 1)}, //studded g
			{new Item(2587, 1)}, //black t
			{new Item(2583, 1)}, //black t
			{new Item(2589, 1)}, //black t
			{new Item(2585, 1)}, //back t
			{new Item(2595, 1)}, //black g
			{new Item(2591, 1)}, //black g
			{new Item(2597, 1)}, //black g
			{new Item(2593, 1)}, //black g
			{new Item(2587, 1)}, //black t
			{new Item(2583, 1)}, //black t
			{new Item(2589, 1)}, //black t
			{new Item(2585, 1)}, //back t
			{new Item(2595, 1)}, //black g
			{new Item(2591, 1)}, //black g
			{new Item(2597, 1)}, //black g
			{new Item(2593, 1)}, //black g
			{new Item(2587, 1)}, //black t
			{new Item(2583, 1)}, //black t
			{new Item(2589, 1)}, //black t
			{new Item(2585, 1)}, //back t
			{new Item(2595, 1)}, //black g
			{new Item(2591, 1)}, //black g
			{new Item(2597, 1)}, //black g
			{new Item(2593, 1)}, //black g
			{new Item(7319, 1)}, //red boater
			{new Item(7321, 1)}, //orange boater
			{new Item(7323, 1)}, //green boater
			{new Item(7325, 1)}, //blue boater
			{new Item(7327, 1)}, //black boater
			{new Item(7319, 1)}, //red boater
			{new Item(7321, 1)}, //orange boater
			{new Item(7323, 1)}, //green boater
			{new Item(7325, 1)}, //blue boater
			{new Item(7327, 1)}, //black boater
			{new Item(7319, 1)}, //red boater
			{new Item(7321, 1)}, //orange boater
			{new Item(7323, 1)}, //green boater
			{new Item(7325, 1)}, //blue boater
			{new Item(7327, 1)}, //black boater
			{new Item(10672, 1)}, //addy h shield
			{new Item(10666, 1)}, //addy h shield
			{new Item(10669, 1)}, //addy h shield
			{new Item(10675, 1)}, //addy h shield
			{new Item(10678, 1)}, //addy h shield
			{new Item(7332, 1)}, //black h shield
			{new Item(7350, 1)}, //black h shield
			{new Item(7338, 1)}, //black h shield
			{new Item(7344, 1)}, //black h shield
			{new Item(7356, 1)}, //black h shield
			{new Item(10672, 1)}, //addy h shield
			{new Item(10666, 1)}, //addy h shield
			{new Item(10669, 1)}, //addy h shield
			{new Item(10675, 1)}, //addy h shield
			{new Item(10678, 1)}, //addy h shield
			{new Item(7332, 1)}, //black h shield
			{new Item(7350, 1)}, //black h shield
			{new Item(7338, 1)}, //black h shield
			{new Item(7344, 1)}, //black h shield
			{new Item(7356, 1)}, //black h shield
			{new Item(10672, 1)}, //addy h shield
			{new Item(10666, 1)}, //addy h shield
			{new Item(10669, 1)}, //addy h shield
			{new Item(10675, 1)}, //addy h shield
			{new Item(10678, 1)}, //addy h shield
			{new Item(7332, 1)}, //black h shield
			{new Item(7350, 1)}, //black h shield
			{new Item(7338, 1)}, //black h shield
			{new Item(7344, 1)}, //black h shield
			{new Item(7356, 1)}, //black h shield
			{new Item(2671, 1)}, //guthix
			{new Item(2669, 1)}, //guthix
			{new Item(2675, 1)}, //guthix
			{new Item(2673, 1)}, //guthix
			{new Item(2663, 1)}, //sara
			{new Item(2661, 1)}, //sara
			{new Item(2667, 1)}, //sara
			{new Item(2665, 1)}, //sara
			{new Item(2655, 1)}, //zammy
			{new Item(2653, 1)}, //zammy
			{new Item(2659, 1)}, //zammy
			{new Item(2657, 1)}, //zammy
			{new Item(7394, 1)}, //wizzy g
			{new Item(7390, 1)}, //wizzy g
			{new Item(7386, 1)}, //wizzy g
			{new Item(2579, 1)}, //wizard boots
			{new Item(7396, 1)}, //wizzy t
			{new Item(7392, 1)}, //wizzy t
			{new Item(7388, 1)}, //wizzy t
			{new Item(6918, 1)}, //infinity
			{new Item(6916, 1)}, //infinity
			{new Item(6924, 1)}, //infinity
			{new Item(6920, 1)}, //infinity
			{new Item(6922, 1)}, //infinity
			{new Item(2671, 1)}, //guthix
			{new Item(2669, 1)}, //guthix
			{new Item(2675, 1)}, //guthix
			{new Item(2673, 1)}, //guthix
			{new Item(2663, 1)}, //sara
			{new Item(2661, 1)}, //sara
			{new Item(2667, 1)}, //sara
			{new Item(2665, 1)}, //sara
			{new Item(2655, 1)}, //zammy
			{new Item(2653, 1)}, //zammy
			{new Item(2659, 1)}, //zammy
			{new Item(2657, 1)}, //zammy
			{new Item(7394, 1)}, //wizzy g
			{new Item(7390, 1)}, //wizzy g
			{new Item(7386, 1)}, //wizzy g
			{new Item(2579, 1)}, //wizard boots
			{new Item(7396, 1)}, //wizzy t
			{new Item(7392, 1)}, //wizzy t
			{new Item(7388, 1)}, //wizzy t
			{new Item(6918, 1)}, //infinity
			{new Item(6916, 1)}, //infinity
			{new Item(6924, 1)}, //infinity
			{new Item(6920, 1)}, //infinity
			{new Item(6922, 1)}, //infinity
			{new Item(987, 1)}, //loop
			{new Item(985, 1)}, //tooth
			{new Item(987, 1)}, //loop
			{new Item(985, 1)}, //tooth
			{new Item(987, 1)}, //loop
			{new Item(985, 1)}, //tooth
			{new Item(987, 1)}, //loop
			{new Item(987, 1)}, //loop
			{new Item(2633, 1)}, //blue beret
			{new Item(2635, 1)}, //black beret
			{new Item(2631, 1)}, //highwayman mask
			{new Item(2633, 1)}, //blue beret
			{new Item(2635, 1)}, //black beret
			{new Item(2631, 1)}, //highwayman mask
			{new Item(2633, 1)}, //blue beret
			{new Item(2635, 1)}, //black beret
			{new Item(2631, 1)}, //highwayman mask
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(3481, 1)}, //gilded plate
			{new Item(3483, 1)}, //gilded legs
			{new Item(3485, 1)}, //gilded skirt
			{new Item(3486, 1)}, //gilded helm
			{new Item(3488, 1)}, //gilded shield
			{new Item(2581, 1)}, //robin
			{new Item(2577, 1)}, //rangers
			{new Item(2581, 1)}, //robin
			{new Item(2577, 1)}, //rangers
			{new Item(2581, 1)}, //robin
			{new Item(2577, 1)}, //rangers
			{new Item(11235, 1)}, //dbow
			{new Item(11235, 1)}, //dbow
			{new Item(11235, 1)}, //dbow
			{new Item(11235, 1)}, //dbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(15241, 1)}, //hand cannon
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(19670, 1)}, //vote scroll
			{new Item(6585, 1)}, //fury
			{new Item(4151, 1)}, //whip
			{new Item(19670, 1)}, //vote scroll
			{new Item(6585, 1)}, //fury
			{new Item(4151, 1)}, //whip
			{new Item(19670, 1)}, //vote scroll
			{new Item(6585, 1)}, //fury
			{new Item(4151, 1)}, //whip
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(7368, 1)}, //studded t
			{new Item(7364, 1)}, //studded t
			{new Item(7368, 1)}, //studded t
			{new Item(7364, 1)}, //studded t
			{new Item(7368, 1)}, //studded t
			{new Item(7364, 1)}, //studded t
			{new Item(7362, 1)}, //studded g
			{new Item(7366, 1)}, //studded g
			{new Item(7362, 1)}, //studded g
			{new Item(7366, 1)}, //studded g
			{new Item(7362, 1)}, //studded g
			{new Item(7366, 1)}, //studded g
			{new Item(2587, 1)}, //black t
			{new Item(2583, 1)}, //black t
			{new Item(2589, 1)}, //black t
			{new Item(2585, 1)}, //back t
			{new Item(2595, 1)}, //black g
			{new Item(2591, 1)}, //black g
			{new Item(2597, 1)}, //black g
			{new Item(2593, 1)}, //black g
			{new Item(2587, 1)}, //black t
			{new Item(2583, 1)}, //black t
			{new Item(2589, 1)}, //black t
			{new Item(2585, 1)}, //back t
			{new Item(2595, 1)}, //black g
			{new Item(2591, 1)}, //black g
			{new Item(2597, 1)}, //black g
			{new Item(2593, 1)}, //black g
			{new Item(2587, 1)}, //black t
			{new Item(2583, 1)}, //black t
			{new Item(2589, 1)}, //black t
			{new Item(2585, 1)}, //back t
			{new Item(2595, 1)}, //black g
			{new Item(2591, 1)}, //black g
			{new Item(2597, 1)}, //black g
			{new Item(2593, 1)}, //black g
			{new Item(7319, 1)}, //red boater
			{new Item(7321, 1)}, //orange boater
			{new Item(7323, 1)}, //green boater
			{new Item(7325, 1)}, //blue boater
			{new Item(7327, 1)}, //black boater
			{new Item(7319, 1)}, //red boater
			{new Item(7321, 1)}, //orange boater
			{new Item(7323, 1)}, //green boater
			{new Item(7325, 1)}, //blue boater
			{new Item(7327, 1)}, //black boater
			{new Item(7319, 1)}, //red boater
			{new Item(7321, 1)}, //orange boater
			{new Item(7323, 1)}, //green boater
			{new Item(7325, 1)}, //blue boater
			{new Item(7327, 1)}, //black boater
			{new Item(10672, 1)}, //addy h shield
			{new Item(10666, 1)}, //addy h shield
			{new Item(10669, 1)}, //addy h shield
			{new Item(10675, 1)}, //addy h shield
			{new Item(10678, 1)}, //addy h shield
			{new Item(7332, 1)}, //black h shield
			{new Item(7350, 1)}, //black h shield
			{new Item(7338, 1)}, //black h shield
			{new Item(7344, 1)}, //black h shield
			{new Item(7356, 1)}, //black h shield
			{new Item(10672, 1)}, //addy h shield
			{new Item(10666, 1)}, //addy h shield
			{new Item(10669, 1)}, //addy h shield
			{new Item(10675, 1)}, //addy h shield
			{new Item(10678, 1)}, //addy h shield
			{new Item(7332, 1)}, //black h shield
			{new Item(7350, 1)}, //black h shield
			{new Item(7338, 1)}, //black h shield
			{new Item(7344, 1)}, //black h shield
			{new Item(7356, 1)}, //black h shield
			{new Item(10672, 1)}, //addy h shield
			{new Item(10666, 1)}, //addy h shield
			{new Item(10669, 1)}, //addy h shield
			{new Item(10675, 1)}, //addy h shield
			{new Item(10678, 1)}, //addy h shield
			{new Item(7332, 1)}, //black h shield
			{new Item(7350, 1)}, //black h shield
			{new Item(7338, 1)}, //black h shield
			{new Item(7344, 1)}, //black h shield
			{new Item(7356, 1)}, //black h shield
			{new Item(2671, 1)}, //guthix
			{new Item(2669, 1)}, //guthix
			{new Item(2675, 1)}, //guthix
			{new Item(2673, 1)}, //guthix
			{new Item(2663, 1)}, //sara
			{new Item(2661, 1)}, //sara
			{new Item(2667, 1)}, //sara
			{new Item(2665, 1)}, //sara
			{new Item(2655, 1)}, //zammy
			{new Item(2653, 1)}, //zammy
			{new Item(2659, 1)}, //zammy
			{new Item(2657, 1)}, //zammy
			{new Item(7394, 1)}, //wizzy g
			{new Item(7390, 1)}, //wizzy g
			{new Item(7386, 1)}, //wizzy g
			{new Item(2579, 1)}, //wizard boots
			{new Item(7396, 1)}, //wizzy t
			{new Item(7392, 1)}, //wizzy t
			{new Item(7388, 1)}, //wizzy t
			{new Item(6918, 1)}, //infinity
			{new Item(6916, 1)}, //infinity
			{new Item(6924, 1)}, //infinity
			{new Item(6920, 1)}, //infinity
			{new Item(6922, 1)}, //infinity
			{new Item(2671, 1)}, //guthix
			{new Item(2669, 1)}, //guthix
			{new Item(2675, 1)}, //guthix
			{new Item(2673, 1)}, //guthix
			{new Item(2663, 1)}, //sara
			{new Item(2661, 1)}, //sara
			{new Item(2667, 1)}, //sara
			{new Item(2665, 1)}, //sara
			{new Item(2655, 1)}, //zammy
			{new Item(2653, 1)}, //zammy
			{new Item(2659, 1)}, //zammy
			{new Item(2657, 1)}, //zammy
			{new Item(7394, 1)}, //wizzy g
			{new Item(7390, 1)}, //wizzy g
			{new Item(7386, 1)}, //wizzy g
			{new Item(2579, 1)}, //wizard boots
			{new Item(7396, 1)}, //wizzy t
			{new Item(7392, 1)}, //wizzy t
			{new Item(7388, 1)}, //wizzy t
			{new Item(6918, 1)}, //infinity
			{new Item(6916, 1)}, //infinity
			{new Item(6924, 1)}, //infinity
			{new Item(6920, 1)}, //infinity
			{new Item(6922, 1)}, //infinity
			{new Item(987, 1)}, //loop
			{new Item(985, 1)}, //tooth
			{new Item(987, 1)}, //loop
			{new Item(985, 1)}, //tooth
			{new Item(987, 1)}, //loop
			{new Item(985, 1)}, //tooth
			{new Item(987, 1)}, //loop
			{new Item(987, 1)}, //loop
			{new Item(2633, 1)}, //blue beret
			{new Item(2635, 1)}, //black beret
			{new Item(2631, 1)}, //highwayman mask
			{new Item(2633, 1)}, //blue beret
			{new Item(2635, 1)}, //black beret
			{new Item(2631, 1)}, //highwayman mask
			{new Item(2633, 1)}, //blue beret
			{new Item(2635, 1)}, //black beret
			{new Item(2631, 1)}, //highwayman mask
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(3481, 1)}, //gilded plate
			{new Item(3483, 1)}, //gilded legs
			{new Item(3485, 1)}, //gilded skirt
			{new Item(3486, 1)}, //gilded helm
			{new Item(3488, 1)}, //gilded shield
			{new Item(2581, 1)}, //robin
			{new Item(2577, 1)}, //rangers
			{new Item(2581, 1)}, //robin
			{new Item(2577, 1)}, //rangers
			{new Item(2581, 1)}, //robin
			{new Item(2577, 1)}, //rangers
			{new Item(11235, 1)}, //dbow
			{new Item(11235, 1)}, //dbow
			{new Item(11235, 1)}, //dbow
			{new Item(11235, 1)}, //dbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(15241, 1)}, //hand cannon
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(19670, 1)}, //vote scroll
			{new Item(6585, 1)}, //fury
			{new Item(4151, 1)}, //whip
			{new Item(19670, 1)}, //vote scroll
			{new Item(6585, 1)}, //fury
			{new Item(4151, 1)}, //whip
			{new Item(19670, 1)}, //vote scroll
			{new Item(6585, 1)}, //fury
			{new Item(4151, 1)}, //whip
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(7368, 1)}, //studded t
			{new Item(7364, 1)}, //studded t
			{new Item(7368, 1)}, //studded t
			{new Item(7364, 1)}, //studded t
			{new Item(7368, 1)}, //studded t
			{new Item(7364, 1)}, //studded t
			{new Item(7362, 1)}, //studded g
			{new Item(7366, 1)}, //studded g
			{new Item(7362, 1)}, //studded g
			{new Item(7366, 1)}, //studded g
			{new Item(7362, 1)}, //studded g
			{new Item(7366, 1)}, //studded g
			{new Item(2587, 1)}, //black t
			{new Item(2583, 1)}, //black t
			{new Item(2589, 1)}, //black t
			{new Item(2585, 1)}, //back t
			{new Item(2595, 1)}, //black g
			{new Item(2591, 1)}, //black g
			{new Item(2597, 1)}, //black g
			{new Item(2593, 1)}, //black g
			{new Item(2587, 1)}, //black t
			{new Item(2583, 1)}, //black t
			{new Item(2589, 1)}, //black t
			{new Item(2585, 1)}, //back t
			{new Item(2595, 1)}, //black g
			{new Item(2591, 1)}, //black g
			{new Item(2597, 1)}, //black g
			{new Item(2593, 1)}, //black g
			{new Item(2587, 1)}, //black t
			{new Item(2583, 1)}, //black t
			{new Item(2589, 1)}, //black t
			{new Item(2585, 1)}, //back t
			{new Item(2595, 1)}, //black g
			{new Item(2591, 1)}, //black g
			{new Item(2597, 1)}, //black g
			{new Item(2593, 1)}, //black g
			{new Item(7319, 1)}, //red boater
			{new Item(7321, 1)}, //orange boater
			{new Item(7323, 1)}, //green boater
			{new Item(7325, 1)}, //blue boater
			{new Item(7327, 1)}, //black boater
			{new Item(7319, 1)}, //red boater
			{new Item(7321, 1)}, //orange boater
			{new Item(7323, 1)}, //green boater
			{new Item(7325, 1)}, //blue boater
			{new Item(7327, 1)}, //black boater
			{new Item(7319, 1)}, //red boater
			{new Item(7321, 1)}, //orange boater
			{new Item(7323, 1)}, //green boater
			{new Item(7325, 1)}, //blue boater
			{new Item(7327, 1)}, //black boater
			{new Item(10672, 1)}, //addy h shield
			{new Item(10666, 1)}, //addy h shield
			{new Item(10669, 1)}, //addy h shield
			{new Item(10675, 1)}, //addy h shield
			{new Item(10678, 1)}, //addy h shield
			{new Item(7332, 1)}, //black h shield
			{new Item(7350, 1)}, //black h shield
			{new Item(7338, 1)}, //black h shield
			{new Item(7344, 1)}, //black h shield
			{new Item(7356, 1)}, //black h shield
			{new Item(10672, 1)}, //addy h shield
			{new Item(10666, 1)}, //addy h shield
			{new Item(10669, 1)}, //addy h shield
			{new Item(10675, 1)}, //addy h shield
			{new Item(10678, 1)}, //addy h shield
			{new Item(7332, 1)}, //black h shield
			{new Item(7350, 1)}, //black h shield
			{new Item(7338, 1)}, //black h shield
			{new Item(7344, 1)}, //black h shield
			{new Item(7356, 1)}, //black h shield
			{new Item(10672, 1)}, //addy h shield
			{new Item(10666, 1)}, //addy h shield
			{new Item(10669, 1)}, //addy h shield
			{new Item(10675, 1)}, //addy h shield
			{new Item(10678, 1)}, //addy h shield
			{new Item(7332, 1)}, //black h shield
			{new Item(7350, 1)}, //black h shield
			{new Item(7338, 1)}, //black h shield
			{new Item(7344, 1)}, //black h shield
			{new Item(7356, 1)}, //black h shield
			{new Item(2671, 1)}, //guthix
			{new Item(2669, 1)}, //guthix
			{new Item(2675, 1)}, //guthix
			{new Item(2673, 1)}, //guthix
			{new Item(2663, 1)}, //sara
			{new Item(2661, 1)}, //sara
			{new Item(2667, 1)}, //sara
			{new Item(2665, 1)}, //sara
			{new Item(2655, 1)}, //zammy
			{new Item(2653, 1)}, //zammy
			{new Item(2659, 1)}, //zammy
			{new Item(2657, 1)}, //zammy
			{new Item(7394, 1)}, //wizzy g
			{new Item(7390, 1)}, //wizzy g
			{new Item(7386, 1)}, //wizzy g
			{new Item(2579, 1)}, //wizard boots
			{new Item(7396, 1)}, //wizzy t
			{new Item(7392, 1)}, //wizzy t
			{new Item(7388, 1)}, //wizzy t
			{new Item(6918, 1)}, //infinity
			{new Item(6916, 1)}, //infinity
			{new Item(6924, 1)}, //infinity
			{new Item(6920, 1)}, //infinity
			{new Item(6922, 1)}, //infinity
			{new Item(2671, 1)}, //guthix
			{new Item(2669, 1)}, //guthix
			{new Item(2675, 1)}, //guthix
			{new Item(2673, 1)}, //guthix
			{new Item(2663, 1)}, //sara
			{new Item(2661, 1)}, //sara
			{new Item(2667, 1)}, //sara
			{new Item(2665, 1)}, //sara
			{new Item(2655, 1)}, //zammy
			{new Item(2653, 1)}, //zammy
			{new Item(2659, 1)}, //zammy
			{new Item(2657, 1)}, //zammy
			{new Item(7394, 1)}, //wizzy g
			{new Item(7390, 1)}, //wizzy g
			{new Item(7386, 1)}, //wizzy g
			{new Item(2579, 1)}, //wizard boots
			{new Item(7396, 1)}, //wizzy t
			{new Item(7392, 1)}, //wizzy t
			{new Item(7388, 1)}, //wizzy t
			{new Item(6918, 1)}, //infinity
			{new Item(6916, 1)}, //infinity
			{new Item(6924, 1)}, //infinity
			{new Item(6920, 1)}, //infinity
			{new Item(6922, 1)}, //infinity
			{new Item(987, 1)}, //loop
			{new Item(985, 1)}, //tooth
			{new Item(987, 1)}, //loop
			{new Item(985, 1)}, //tooth
			{new Item(987, 1)}, //loop
			{new Item(985, 1)}, //tooth
			{new Item(987, 1)}, //loop
			{new Item(987, 1)}, //loop
			{new Item(2633, 1)}, //blue beret
			{new Item(2635, 1)}, //black beret
			{new Item(2631, 1)}, //highwayman mask
			{new Item(2633, 1)}, //blue beret
			{new Item(2635, 1)}, //black beret
			{new Item(2631, 1)}, //highwayman mask
			{new Item(2633, 1)}, //blue beret
			{new Item(2635, 1)}, //black beret
			{new Item(2631, 1)}, //highwayman mask
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1969, 1), new Item(995, 100000)}, //set 1 SPINACH ROLL
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(1631, 1)}, //dragonstone
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(15332, 1)}, //overload
			{new Item(3481, 1)}, //gilded plate
			{new Item(3483, 1)}, //gilded legs
			{new Item(3485, 1)}, //gilded skirt
			{new Item(3486, 1)}, //gilded helm
			{new Item(3488, 1)}, //gilded shield
			{new Item(2581, 1)}, //robin
			{new Item(2577, 1)}, //rangers
			{new Item(2581, 1)}, //robin
			{new Item(2577, 1)}, //rangers
			{new Item(2581, 1)}, //robin
			{new Item(2577, 1)}, //rangers
			{new Item(11235, 1)}, //dbow
			{new Item(11235, 1)}, //dbow
			{new Item(11235, 1)}, //dbow
			{new Item(11235, 1)}, //dbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(861, 1)}, //msbow
			{new Item(9185, 1)}, //rcbow
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(5680, 1)}, //dds
			{new Item(10828, 1)}, //nezzy helm
			{new Item(15241, 1)}, //hand cannon
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(4587, 1)}, //dscim
			{new Item(4153, 1)}, //gmaul
			{new Item(11732, 1)}, //dboots
			{new Item(1079, 1)}, //rune legs
			{new Item(1127, 1)}, //rune pl8
			{new Item(2572, 1)}, //row
			{new Item(19670, 1)}, //vote scroll
			{new Item(6585, 1)}, //fury
			{new Item(4151, 1)}, //whip
			{new Item(19670, 1)}, //vote scroll
			{new Item(6585, 1)}, //fury
			{new Item(4151, 1)}, //whip
			{new Item(19670, 1)}, //vote scroll
			{new Item(6585, 1)}, //fury
			{new Item(4151, 1)}, //whip
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1632, 5)}, //dstone
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(1275, 1)}, //r pick
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(15259, 1)}, //d pick
			{new Item(15273, 50)}, //cooked rocks
			{new Item(15271, 50)}, //raw rocks
			{new Item(1359, 1)}, //rune axe
			{new Item(6739, 1)}, //d axe
			{new Item(7368, 1)}, //studded t
			{new Item(7364, 1)}, //studded t
			{new Item(7368, 1)}, //studded t
			{new Item(7364, 1)}, //studded t
			{new Item(7368, 1)}, //studded t
			{new Item(7364, 1)}, //studded t
			{new Item(7362, 1)}, //studded g
			{new Item(7366, 1)}, //studded g
			{new Item(7362, 1)}, //studded g
			{new Item(7366, 1)}, //studded g
			{new Item(7362, 1)}, //studded g
			{new Item(7366, 1)}, //studded g
			{new Item(2587, 1)}, //black t
			{new Item(2583, 1)}, //black t
			{new Item(2589, 1)}, //black t
			{new Item(2585, 1)}, //back t
			{new Item(2595, 1)}, //black g
			{new Item(2591, 1)}, //black g
			{new Item(2597, 1)}, //black g
			{new Item(2593, 1)}, //black g
			{new Item(2587, 1)}, //black t
			{new Item(2583, 1)}, //black t
			{new Item(2589, 1)}, //black t
			{new Item(2585, 1)}, //back t
			{new Item(2595, 1)}, //black g
			{new Item(2591, 1)}, //black g
			{new Item(2597, 1)}, //black g
			{new Item(2593, 1)}, //black g
			{new Item(2587, 1)}, //black t
			{new Item(2583, 1)}, //black t
			{new Item(2589, 1)}, //black t
			{new Item(2585, 1)}, //back t
			{new Item(2595, 1)}, //black g
			{new Item(2591, 1)}, //black g
			{new Item(2597, 1)}, //black g
			{new Item(2593, 1)}, //black g
			{new Item(7319, 1)}, //red boater
			{new Item(7321, 1)}, //orange boater
			{new Item(7323, 1)}, //green boater
			{new Item(7325, 1)}, //blue boater
			{new Item(7327, 1)}, //black boater
			{new Item(7319, 1)}, //red boater
			{new Item(7321, 1)}, //orange boater
			{new Item(7323, 1)}, //green boater
			{new Item(7325, 1)}, //blue boater
			{new Item(7327, 1)}, //black boater
			{new Item(7319, 1)}, //red boater
			{new Item(7321, 1)}, //orange boater
			{new Item(7323, 1)}, //green boater
			{new Item(7325, 1)}, //blue boater
			{new Item(7327, 1)}, //black boater
			{new Item(10672, 1)}, //addy h shield
			{new Item(10666, 1)}, //addy h shield
			{new Item(10669, 1)}, //addy h shield
			{new Item(10675, 1)}, //addy h shield
			{new Item(10678, 1)}, //addy h shield
			{new Item(7332, 1)}, //black h shield
			{new Item(7350, 1)}, //black h shield
			{new Item(7338, 1)}, //black h shield
			{new Item(7344, 1)}, //black h shield
			{new Item(7356, 1)}, //black h shield
			{new Item(10672, 1)}, //addy h shield
			{new Item(10666, 1)}, //addy h shield
			{new Item(10669, 1)}, //addy h shield
			{new Item(10675, 1)}, //addy h shield
			{new Item(10678, 1)}, //addy h shield
			{new Item(7332, 1)}, //black h shield
			{new Item(7350, 1)}, //black h shield
			{new Item(7338, 1)}, //black h shield
			{new Item(7344, 1)}, //black h shield
			{new Item(7356, 1)}, //black h shield
			{new Item(10672, 1)}, //addy h shield
			{new Item(10666, 1)}, //addy h shield
			{new Item(10669, 1)}, //addy h shield
			{new Item(10675, 1)}, //addy h shield
			{new Item(10678, 1)}, //addy h shield
			{new Item(7332, 1)}, //black h shield
			{new Item(7350, 1)}, //black h shield
			{new Item(7338, 1)}, //black h shield
			{new Item(7344, 1)}, //black h shield
			{new Item(7356, 1)}, //black h shield
			{new Item(2671, 1)}, //guthix
			{new Item(2669, 1)}, //guthix
			{new Item(2675, 1)}, //guthix
			{new Item(2673, 1)}, //guthix
			{new Item(2663, 1)}, //sara
			{new Item(2661, 1)}, //sara
			{new Item(2667, 1)}, //sara
			{new Item(2665, 1)}, //sara
			{new Item(2655, 1)}, //zammy
			{new Item(2653, 1)}, //zammy
			{new Item(2659, 1)}, //zammy
			{new Item(2657, 1)}, //zammy
			{new Item(7394, 1)}, //wizzy g
			{new Item(7390, 1)}, //wizzy g
			{new Item(7386, 1)}, //wizzy g
			{new Item(2579, 1)}, //wizard boots
			{new Item(7396, 1)}, //wizzy t
			{new Item(7392, 1)}, //wizzy t
			{new Item(7388, 1)}, //wizzy t
			{new Item(6918, 1)}, //infinity
			{new Item(6916, 1)}, //infinity
			{new Item(6924, 1)}, //infinity
			{new Item(6920, 1)}, //infinity
			{new Item(6922, 1)}, //infinity
			{new Item(2671, 1)}, //guthix
			{new Item(2669, 1)}, //guthix
			{new Item(2675, 1)}, //guthix
			{new Item(2673, 1)}, //guthix
			{new Item(2663, 1)}, //sara
			{new Item(2661, 1)}, //sara
			{new Item(2667, 1)}, //sara
			{new Item(2665, 1)}, //sara
			{new Item(2655, 1)}, //zammy
			{new Item(2653, 1)}, //zammy
			{new Item(2659, 1)}, //zammy
			{new Item(2657, 1)}, //zammy
			{new Item(7394, 1)}, //wizzy g
			{new Item(7390, 1)}, //wizzy g
			{new Item(7386, 1)}, //wizzy g
			{new Item(2579, 1)}, //wizard boots
			{new Item(7396, 1)}, //wizzy t
			{new Item(7392, 1)}, //wizzy t
			{new Item(7388, 1)}, //wizzy t
			{new Item(6918, 1)}, //infinity
			{new Item(6916, 1)}, //infinity
			{new Item(6924, 1)}, //infinity
			{new Item(6920, 1)}, //infinity
			{new Item(6922, 1)}, //infinity
			{new Item(987, 1)}, //loop
			{new Item(985, 1)}, //tooth
			{new Item(987, 1)}, //loop
			{new Item(985, 1)}, //tooth
			{new Item(987, 1)}, //loop
			{new Item(985, 1)}, //tooth
			{new Item(987, 1)}, //loop
			{new Item(987, 1)}, //loop
			{new Item(2633, 1)}, //blue beret
			{new Item(2635, 1)}, //black beret
			{new Item(2631, 1)}, //highwayman mask
			{new Item(2633, 1)}, //blue beret
			{new Item(2635, 1)}, //black beret
			{new Item(2631, 1)}, //highwayman mask
			{new Item(2633, 1)}, //blue beret
			{new Item(2635, 1)}, //black beret
			{new Item(2631, 1)}, //highwayman mask
			{new Item(18989, 1)}, //CUSTOM Doom blade
			{new Item(2092, 1)}, //CUSTOM PET CENTAUR
			{new Item(995, 15000)}, //15k
			{new Item(995, 15000)}, //15k
			{new Item(995, 15000)}, //15k
			{new Item(995, 15000)}, //15k
			{new Item(995, 15000)}, //15k
			{new Item(995, 15000)}, //15k
			{new Item(995, 15000)}, //15k
			{new Item(995, 15000)}, //15k
			{new Item(995, 15000)}, //15k
			{new Item(995, 15000)}, //15k
			{new Item(995, 15000)}, //15k
			{new Item(995, 15000)}, //15k
			
			
		};
	
}
