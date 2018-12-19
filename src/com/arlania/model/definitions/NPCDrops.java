package com.arlania.model.definitions;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.arlania.model.GameMode;
import com.arlania.model.Graphic;
import com.arlania.model.GroundItem;
import com.arlania.model.Item;
import com.arlania.model.PlayerRights;
import com.arlania.model.Position;
import com.arlania.model.Skill;
import com.arlania.model.Locations.Location;
import com.arlania.model.container.impl.Bank;
import com.arlania.model.container.impl.Equipment;
import com.arlania.util.JsonLoader;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
import com.arlania.world.content.*;
import com.arlania.world.content.DropLog.DropLogEntry;
import com.arlania.world.content.clan.ClanChatManager;
import com.arlania.world.content.combat.strategy.impl.Nex;
import com.arlania.world.content.minigames.impl.WarriorsGuild;
import com.arlania.world.content.skill.impl.prayer.BonesData;
import com.arlania.world.content.skill.impl.slayer.SlayerTasks;
import com.arlania.world.content.skill.impl.summoning.BossPets;
import com.arlania.world.content.skill.impl.summoning.CharmingImp;
import com.arlania.world.entity.impl.GroundItemManager;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Controls the npc drops
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>, Gabbe &
 *         Samy
 *
 */
public class NPCDrops {

	/**
	 * The map containing all the npc drops.
	 */
	private static Map<Integer, NPCDrops> dropControllers = new HashMap<Integer, NPCDrops>();

	public static boolean doubleDrop = false;
	public static boolean yell = true;
	public static JsonLoader parseDrops() {

		ItemDropAnnouncer.init();

		return new JsonLoader() {

			@Override
			public void load(JsonObject reader, Gson builder) {
				int[] npcIds = builder.fromJson(reader.get("npcIds"),
						int[].class);
				NpcDropItem[] drops = builder.fromJson(reader.get("drops"),
						NpcDropItem[].class);

				NPCDrops d = new NPCDrops();
				d.npcIds = npcIds;
				d.drops = drops;
				for (int id : npcIds) {
					dropControllers.put(id, d);
//					System.out.println("put: "+id+" "+d);
				}
			}

			@Override
			public String filePath() {
				return "./data/def/json/drops.json";
			}
		};
	}

	/**
	 * The id's of the NPC's that "owns" this class.
	 */
	private int[] npcIds;

	/**
	 * All the drops that belongs to this class.
	 */
	private NpcDropItem[] drops;

	/**
	 * Gets the NPC drop controller by an id.
	 *
	 * @return The NPC drops associated with this id.
	 */
	public static NPCDrops forId(int id) {
		return dropControllers.get(id);
	}

	public static Map<Integer, NPCDrops> getDrops() {
		return dropControllers;
	}

	/**
	 * Gets the drop list
	 *
	 * @return the list
	 */
	public NpcDropItem[] getDropList() {
		return drops;
	}

	/**
	 * Gets the npcIds
	 *
	 * @return the npcIds
	 */
	public int[] getNpcIds() {
		return npcIds;
	}

	/**
	 * Represents a npc drop item
	 */
	public static class NpcDropItem {

		/**
		 * The id.
		 */
		private final int id;

		/**
		 * Array holding all the amounts of this item.
		 */
		private final int[] count;

		/**
		 * The chance of getting this item.
		 */
		private final int chance;

		/**
		 * New npc drop item
		 *
		 * @param id
		 *            the item
		 * @param count
		 *            the count
		 * @param chance
		 *            the chance
		 */
		public NpcDropItem(int id, int[] count, int chance) {
			this.id = id;
			this.count = count;
			this.chance = chance;
		}

		/**
		 * Gets the item id.
		 *
		 * @return The item id.
		 */
		public int getId() {
			return id;
		}

		/**
		 * Gets the chance.
		 *
		 * @return The chance.
		 */
		public int[] getCount() {
			return count;
		}

		/**
		 * Gets the chance.
		 *
		 * @return The chance.
		 */
		public DropChance getChance() {
			switch (chance) {
				case 1:
					return DropChance.ALMOST_ALWAYS; // 50% <-> 1/2
				case 2:
					return DropChance.VERY_COMMON; // 20% <-> 1/5
				case 3:
					return DropChance.COMMON; // 5% <-> 1/20
				case 4:
					return DropChance.UNCOMMON; // 2% <-> 1/50
				case 5:
					return DropChance.RARE; // 0.5% <-> 1/200
				case 6:
					return DropChance.LEGENDARY; // 0.2% <-> 1/500
				case 7:
					return DropChance.LEGENDARY_2;
				case 8:
					return DropChance.LEGENDARY_3;
				case 9:
					return DropChance.LEGENDARY_4;
				case 10:
					return DropChance.LEGENDARY_5;
				case 1000:
					return DropChance.PET;
				default:
					return DropChance.ALWAYS; // 100% <-> 1/1
			}
		}

		public WellChance getWellChance() {
			switch (chance) {
				case 1:
					return WellChance.ALMOST_ALWAYS; // 50% <-> 1/2
				case 2:
					return WellChance.VERY_COMMON; // 20% <-> 1/5
				case 3:
					return WellChance.COMMON; // 5% <-> 1/20
				case 4:
					return WellChance.UNCOMMON; // 2% <-> 1/50
				case 5:
					return WellChance.RARE; // 0.5% <-> 1/200
				case 6:
					return WellChance.LEGENDARY; // 0.2% <-> 1/320
				case 7:
					return WellChance.LEGENDARY_2; // 1/410
				case 8:
					return WellChance.LEGENDARY_3; // 1/850
				case 9:
					return WellChance.LEGENDARY_4; // 1/680
				case 10:
					return WellChance.LEGENDARY_5; // 1/900
				case 1000:
					return WellChance.PET;
				default:
					return WellChance.ALWAYS; // 100% <-> 1/1
			}
		}

		/**
		 * Gets the item
		 *
		 * @return the item
		 */
		public Item getItem() {
			int amount = 0;
			for (int i = 0; i < count.length; i++)
				amount += count[i];
			if (amount > count[0])
				amount = count[0] + RandomUtility.getRandom(count[1]);
			return new Item(id, amount);
		}
	}

	public enum DropChance {
		ALWAYS(0), ALMOST_ALWAYS(2), VERY_COMMON(5), COMMON(15), UNCOMMON(30), RARE(60), LEGENDARY(100), LEGENDARY_2(150), LEGENDARY_3(200), LEGENDARY_4(250), LEGENDARY_5(400),PET(1000);


		DropChance(int randomModifier) {
			this.random = randomModifier;
		}

		private int random;

		public int getRandom() {
			return this.random;
		}
	}

	public enum WellChance {
		ALWAYS(0), ALMOST_ALWAYS(2), VERY_COMMON(3), COMMON(8), UNCOMMON(20), NOTTHATRARE(
				50), RARE(76), LEGENDARY(160), LEGENDARY_2(205), LEGENDARY_3(425), LEGENDARY_4(340), LEGENDARY_5(450),PET(1000);


		WellChance(int randomModifier) {
			this.random = randomModifier;
		}

		private int random;

		public int getRandom() {
			return this.random;
		}
	}

	/**
	 * Drops items for a player after killing an npc. A player can max receive
	 * one item per drop chance.
	 *
	 * @param p
	 *            Player to receive drop.
	 * @param npc
	 *            NPC to receive drop FROM.
	 */
	public static void dropItems(Player p, NPC npc) {
		if (npc.getLocation() == Location.WARRIORS_GUILD)
			WarriorsGuild.handleDrop(p, npc);
		if(npc == Abbadon.getCurrent()) {
			Abbadon.handleDrop();
		}
		NPCDrops drops = NPCDrops.forId(npc.getId());
		if (drops == null)
			return;
		final boolean goGlobal = p.getPosition().getZ() >= 0 && p.getPosition().getZ() < 4;
		final boolean ringOfWealth = p.getEquipment().get(Equipment.RING_SLOT).getId() == 2572;
		final boolean ringOfWealth1 = p.getEquipment().get(Equipment.RING_SLOT).getId() == 11527;
		final boolean ringOfWealth2 = p.getEquipment().get(Equipment.RING_SLOT).getId() == 11529;
		final boolean ringOfWealth3 = p.getEquipment().get(Equipment.RING_SLOT).getId() == 11531;
		final boolean ringOfWealthLucky = p.getEquipment().get(Equipment.RING_SLOT).getId() == 11533;
		final boolean ringOfGods = p.getEquipment().get(Equipment.RING_SLOT).getId() == 12601;
		final boolean amuletOfInsanity = p.getEquipment().get(Equipment.AMULET_SLOT).getId() == 11422;
		final Position npcPos = npc.getPosition().copy();
		boolean[] dropsReceived = new boolean[12];



		if (drops.getDropList().length > 0 && p.getPosition().getZ() >= 0 && p.getPosition().getZ() < 4) {

			casketDrop(p, npc.getDefinition().getCombatLevel(), npcPos);
		}
		if (drops.getDropList().length > 0 && p.getPosition().getZ() >= 0 && p.getPosition().getZ() < 4) {
			monsterfragmentDrop(p, npc.getDefinition().getCombatLevel(), npcPos);

		}
		if (drops.getDropList().length > 0 && p.getPosition().getZ() >= 0 && p.getPosition().getZ() < 4) {
			clueDrop(p, npc.getDefinition().getCombatLevel(), npcPos);

		}

		if(Misc.inclusiveRandom(1,100) == 1) {
			drop(p, new Item(21006, 1), npc, npc.getPosition(), false);
			World.sendMessage("<shad=0>@bla@[@whi@Mythical@bla@] <shad=0>@whi@"+ p.getUsername()
					+ "@bla@ has just received <shad=0>@whi@ a Mythical hilt shard@bla@ from <shad=0>@whi@@bla@"+ npc.getDefinition().getName());
		}
		if(Misc.inclusiveRandom(1,100) == 1) {
			drop(p, new Item(21007, 1), npc, npc.getPosition(), false);
			World.sendMessage("<shad=0>@bla@[@whi@Mythical@bla@] <shad=0>@whi@"+ p.getUsername()
					+ "@bla@ has just received <shad=0>@whi@ a Mythical edge shard@bla@ from <shad=0>@whi@@bla@"+ npc.getDefinition().getName());
		}

		for (int i = 0; i < drops.getDropList().length; i++) {
			if (drops.getDropList()[i].getItem().getId() <= 0 || drops.getDropList()[i].getItem().getId() > ItemDefinition.getMaxAmountOfItems() || drops.getDropList()[i].getItem().getAmount() <= 0) {
				continue;
			}

			DropChance dropChance = drops.getDropList()[i].getChance();


			if (dropChance == DropChance.ALWAYS) {
				drop(p, drops.getDropList()[i].getItem(), npc, npcPos, goGlobal);
			} else {
				if(shouldDrop(p,npc,drops.getDropList().length, drops.getDropList(), dropsReceived, dropChance, ringOfWealth, ringOfWealth1, ringOfWealth2, ringOfWealth3, ringOfWealthLucky, amuletOfInsanity, ringOfGods, p.getGameMode() == GameMode.IRONMAN || p.getGameMode() == GameMode.HARDCORE_IRONMAN, p.getRights())) {
					if(shouldDoubleDrop(p, dropsReceived, dropChance, ringOfWealth, ringOfWealth1, ringOfWealth2, ringOfWealth3, ringOfWealthLucky, amuletOfInsanity, ringOfGods, p.getGameMode() == GameMode.IRONMAN || p.getGameMode() == GameMode.HARDCORE_IRONMAN, p.getRights())) {
						doubleDrop = true;
						drop(p, drops.getDropList()[i].getItem(), npc, npcPos, goGlobal);
						yell = false;
						drop(p, drops.getDropList()[i].getItem(), npc, npcPos, goGlobal);
						doubleDrop = false;
						yell = true;
					} else {
						drop(p, drops.getDropList()[i].getItem(), npc, npcPos, goGlobal);
						dropsReceived[dropChance.ordinal()] = true;
					}
				}
			}
		}


		if (WellOfWealth.isActive()) {
			for (int i = 0; i < drops.getDropList().length; i++) {
				if (drops.getDropList()[i].getItem().getId() <= 0 || drops.getDropList()[i].getItem().getId() > ItemDefinition.getMaxAmountOfItems() || drops.getDropList()[i].getItem().getAmount() <= 0) {
					continue;
				}

				WellChance wellChance = drops.getDropList()[i].getWellChance();

				if (wellChance == WellChance.ALWAYS) {
					drop(p, drops.getDropList()[i].getItem(), npc, npcPos, goGlobal);
				} else {
					if(shouldRecieveDrop(dropsReceived, wellChance, ringOfWealth, ringOfWealth1, ringOfWealth2, ringOfWealth3, ringOfWealthLucky, amuletOfInsanity, ringOfGods, p.getGameMode() == GameMode.IRONMAN || p.getGameMode() == GameMode.HARDCORE_IRONMAN, p.getRights())) {
						drop(p, drops.getDropList()[i].getItem(), npc, npcPos, goGlobal);
						dropsReceived[wellChance.ordinal()] = true;
					}
				}
			}
		}

	}

	public static boolean shouldDrop(Player p,NPC npc, double drops,NpcDropItem[] c, boolean[] b, DropChance chance,
									 boolean ringOfWealth, boolean ringOfWealth1, boolean ringOfWealth2, boolean ringOfWealth3, boolean ringOfWealthLucky, boolean amuletOfInsanity, boolean ringOfGods, boolean extreme, PlayerRights rights) {

		int x = 0;
		double random = chance.getRandom(); //pull the chance from the table
		double drBoost = NPCDrops.getDroprate(p);
		for (int i = 0; i < drops; i++) {
			if (random == c[i].getChance().getRandom()) {
				x++;
			}
		}
		random *= x;
		p.setDroprate(drBoost);
		if (p.getSlayer().getSlayerTask().getNpcId() == npc.getId()) {
			drBoost += 3;
			p.sendMessage("You received 3% extra dr");
	}
			random = (int)random * ((100-drBoost)/100);

		return !b[chance.ordinal()] && Misc.getRandom((int) random) == 0; //return true if random between 0 & table value is 1.
	}

	public static double getDoubleDr(Player p){
		double drBoost = 0;
		final boolean ringOfWealth = p.getEquipment().get(Equipment.RING_SLOT).getId() == 2572;

		if(p.getRights() == PlayerRights.PLAYER) {
			drBoost = 2;
		}
		if(p.getRights() == PlayerRights.OWNER) {
			drBoost = 100;
		}
		if(p.getRights() == PlayerRights.LEGENDARY_DONATOR) {
			drBoost = 5;
		}
		if(p.getRights() == PlayerRights.UBER_DONATOR) {
			drBoost = 10;
		}
		if(p.getSummoned() == 1300) {
			drBoost += 2;
		}
		if (ringOfWealth) { //if the chance from the table is greater or equal to 60, and player is wearing ring of wealth
			drBoost += 2;
		}
		if (p.getEquipment().get(Equipment.RING_SLOT).getId() == 11531) { //if the chance from the table is greater or equal to 60, and player is wearing ring of wealth
			drBoost += 4;
		}if (p.getEquipment().get(Equipment.RING_SLOT).getId() == 11533) { //if the chance from the table is greater or equal to 60, and player is wearing ring of wealth
			drBoost += 8;
		}
		if(p.getBetaTester()) {
			drBoost +=4;
		}
		return drBoost;
	}
	public static double getDroprate(Player p){
		double drBoost = 0;
		final boolean ringOfWealth = p.getEquipment().get(Equipment.RING_SLOT).getId() == 2572;

		if(p.getRights() == PlayerRights.PLAYER) {
			drBoost = 2;
		}
		if(p.getRights() == PlayerRights.OWNER) {
			drBoost = 100;
		}
		if(p.getRights() == PlayerRights.DONATOR) {
			drBoost = 2;
		}
		if(p.getRights() == PlayerRights.SUPER_DONATOR) {
			drBoost = 4;
		}
		if(p.getRights() == PlayerRights.EXTREME_DONATOR) {
			drBoost = 6;
		}
		if(p.getRights() == PlayerRights.LEGENDARY_DONATOR) {
			drBoost = 10;
		}
		if(p.getRights() == PlayerRights.UBER_DONATOR) {
			drBoost = 15;
		}

		if(p.getSummoned() == 1266) {
			drBoost += 2;
		}
		if (ringOfWealth) { //if the chance from the table is greater or equal to 60, and player is wearing ring of wealth
			drBoost += 2;
		}		if (p.getEquipment().get(Equipment.RING_SLOT).getId() == 11527) { //if the chance from the table is greater or equal to 60, and player is wearing ring of wealth
			drBoost += 4;
		}		if (p.getEquipment().get(Equipment.RING_SLOT).getId() == 11529) { //if the chance from the table is greater or equal to 60, and player is wearing ring of wealth
			drBoost += 6;
		}if (p.getEquipment().get(Equipment.RING_SLOT).getId() == 11531) { //if the chance from the table is greater or equal to 60, and player is wearing ring of wealth
			drBoost += 8;
		}if (p.getEquipment().get(Equipment.RING_SLOT).getId() == 11533) { //if the chance from the table is greater or equal to 60, and player is wearing ring of wealth
			drBoost += 12;
		}

		if(p.getBetaTester()) {
			drBoost +=4;
		}
		return drBoost;
	}

	public static boolean shouldDoubleDrop(Player p, boolean[] b, DropChance chance,
										   boolean ringOfWealth, boolean ringOfWealth1, boolean ringOfWealth2, boolean ringOfWealth3, boolean ringOfWealthLucky, boolean amuletOfInsanity, boolean ringOfGods, boolean extreme, PlayerRights rights) {
		double random = 100; //pull the chance from the table
		double drBoost = NPCDrops.getDoubleDr(p);

		p.setDoubleDropRate(drBoost);
		random = random * ((100-drBoost)/100);

		return !b[chance.ordinal()] && Misc.getRandom((int) random) == 0; //return true if random between 0 & table value is 1.
	}

	public static boolean shouldRecieveDrop(boolean[] b, WellChance chance,
											boolean ringOfWealth, boolean ringOfWealth1, boolean ringOfWealth2, boolean ringOfWealth3, boolean ringOfWealthLucky, boolean amuletOfInsanity, boolean ringOfGods, boolean extreme, PlayerRights rights) {
		int random = chance.getRandom();
		if (ringOfWealth && random >= 100) {
			random -= (random / 5);
		}
		if (ringOfWealth1 && random >= 100) {
			random -= (random / 10);
		}
		if (ringOfWealth2 && random >= 100) {
			random -= (random / 15);
		}
		if (ringOfWealth3 && random >= 100) {
			random -= (random / 20);
		}
		if (ringOfWealthLucky && random >= 100) {
			random -= (random / 25);
		}
		if (ringOfGods && random >= 100) {
			random -= (random / 20);
		}
		if (amuletOfInsanity && random >= 100) {
			random -= (random / 20);
		}
		return !b[chance.ordinal()] && RandomUtility.getRandom(random) == 1;
	}

	public static void drop(Player player, Item item, NPC npc, Position pos,
							boolean goGlobal) {
		//	if (player.getEquipment().contains(12601)) {
		//	player.getPacketSender().sendMessage("@red@Your Ring of the Gods has boosted your drop rate by 25%.");
		//}

		//if (player.getEquipment().contains(2572)) {
		//player.getPacketSender().sendMessage("@red@Your Ring of Wealth has boosted your drop rate by 10%.");
		//}
		if(npc.getId() == 2042 || npc.getId() == 2043 || npc.getId() == 2044) {
			pos = player.getPosition().copy();
		}

		String npcName = Misc.formatText(npc.getDefinition().getName());



		if (player.getInventory().contains(18337)
				&& BonesData.forId(item.getId()) != null) {
			player.getPacketSender().sendGlobalGraphic(new Graphic(777), pos);
			player.getSkillManager().addExperience(Skill.PRAYER,
					BonesData.forId(item.getId()).getBuryingXP());
			return;
		}
		int itemId = item.getId();
		int amount = item.getAmount();


		if (itemId == 6731 ||  itemId == 6914 || itemId == 7158 ||  itemId == 6889 || itemId == 6733 || itemId == 15019 || itemId == 11235 || itemId == 15020 || itemId == 15018 || itemId == 15220 || itemId == 6735 || itemId == 6737 || itemId == 6585 || itemId == 4151 || itemId == 4087 || itemId == 2577 || itemId == 2581 || itemId == 11732 || itemId == 18782 ) {
			player.getPacketSender().sendMessage("@red@ YOU HAVE RECEIVED A MEDIUM DROP, CHECK THE GROUND!");

		}

		if (itemId == CharmingImp.GOLD_CHARM
				|| itemId == CharmingImp.GREEN_CHARM
				|| itemId == CharmingImp.CRIM_CHARM
				|| itemId == CharmingImp.BLUE_CHARM) {
			if (player.getInventory().contains(6500)
					&& CharmingImp.handleCharmDrop(player, itemId, amount)) {
				return;
			}
		}

		Player toGive = player;

		boolean ccAnnounce = false;
		if(Location.inMulti(player)) {
			if(player.getCurrentClanChat() != null && player.getCurrentClanChat().getLootShare()) {
				CopyOnWriteArrayList<Player> playerList = new CopyOnWriteArrayList<Player>();
				for(Player member : player.getCurrentClanChat().getMembers()) {
					if(member != null) {
						if(member.getPosition().isWithinDistance(player.getPosition())) {
							playerList.add(member);
						}
					}
				}
				if(playerList.size() > 0) {
					toGive = playerList.get(RandomUtility.getRandom(playerList.size() - 1));
					if(toGive == null || toGive.getCurrentClanChat() == null || toGive.getCurrentClanChat() != player.getCurrentClanChat()) {
						toGive = player;
					}
					ccAnnounce = true;
				}
			}
		}

		if(itemId == 18778) { //Effigy, don't drop one if player already has one
			if(toGive.getInventory().contains(18778) || toGive.getInventory().contains(18779) || toGive.getInventory().contains(18780) || toGive.getInventory().contains(18781)) {
				return;
			}
			for(Bank bank : toGive.getBanks()) {
				if(bank == null) {
					continue;
				}
				if(bank.contains(18778) || bank.contains(18779) || bank.contains(18780) || bank.contains(18781)) {
					return;
				}
			}
		}

		if (ItemDropAnnouncer.announce(itemId)) {
			String itemName = item.getDefinition().getName();
			String itemMessage = Misc.anOrA(itemName) + " " + itemName;

			if (player.getRights() == PlayerRights.DEVELOPER) {
				GroundItemManager.spawnGroundItem(toGive, new GroundItem(item, pos,
						toGive.getUsername(), false, 150, goGlobal, 200));
			}
			switch (itemId) {
				case 14484:
					itemMessage = "a pair of Dragon Claws";
					break;
				case 20000:
				case 20001:
				case 20002:
					itemMessage = itemName;
					break;
			}
			switch (npc.getId()) {
				case 81:
					npcName = "a Cow";
					break;
				case 50:
				case 3200:
				case 8133:
				case 4540:
				case 1160:
				case 8549:
					npcName = "The " + npcName + "";
					break;
				case 51:
				case 54:
				case 5363:
				case 8349:
				case 1592:
				case 1591:
				case 1590:
				case 1615:
				case 9463:
				case 9465:
				case 9467:
				case 1382:
				case 13659:
				case 11235:
					npcName = "" + Misc.anOrA(npcName) + " " + npcName + "";
					break;
			}
			ItemDefinition drop = ItemDefinition.forId(itemId);
			NpcDefinition npcDrop = NpcDefinition.forId(npc.getId());

			if (doubleDrop && yell) {
				String message = "@blu@[Double Drop] " + toGive.getUsername()
						+ " has just his @red@" + drop.getName() + "@blu@ Doubled from " + npcDrop.getName()
						+ "!";
				World.sendMessage(message);
				yell = false;
			} else if(!doubleDrop) {
				String message = "@blu@[RARE DROP] " + toGive.getUsername()
						+ " has just received @red@" + itemMessage + "@blu@ from " + npcName
						+ "!";
				World.sendMessage(message);
			}


			if(ccAnnounce) {
				ClanChatManager.sendMessage(player.getCurrentClanChat(), "<col=16777215>[<col=255>Lootshare<col=16777215>]<col=3300CC> "+toGive.getUsername()+" received " + itemMessage + " from "+npcName+"!");
			}

			PlayerLogs.log(toGive.getUsername(), "" + toGive.getUsername() + " received " + itemMessage + " from " + npcName + "");
		}

		if (player.getEquipment().get(Equipment.AMULET_SLOT).getId() == 11423) { //if the chance from the table is greater or equal to 60, and player is wearing ring of wealth
			player.giveItem(item.getId(),item.getAmount());
		} else {
			GroundItemManager.spawnGroundItem(toGive, new GroundItem(item, pos,
					toGive.getUsername(), false, 150, goGlobal, 200));
		}
		DropLog.submit(toGive, new DropLogEntry(itemId, item.getAmount()));
	}

	public static void casketDrop(Player player, int combat, Position pos) {
		int chance = (6 + (combat / 2));
		if (RandomUtility.getRandom(combat <= 50 ? 1300 : 5000) < chance) {
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(7956), pos, player.getUsername(), false, 150, true, 200));
			player.getPacketSender().sendMessage("@or2@You have received a casket!");
		}
	}
	public static void monsterfragmentDrop(Player player, int combat, Position pos) {
		int chance = (6 + (combat / 2));
		if (RandomUtility.getRandom(combat <= 50 ? 1300 : 5000) < chance) {
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(11316, 25), pos, player.getUsername(), false, 150, true, 200));
			player.getPacketSender().sendMessage("@or2@You have received some Monster fragments!");
		}
	}
	private static final int[] CLUESBOY = new int[] { 2677, 2678, 2679, 2680, 2681, 2682, 2683, 2684, 2685};


	public static void clueDrop(Player player, int combat, Position pos) {
		int chance = (6 + (combat / 4));
		if (RandomUtility.getRandom(combat <= 80 ? 1300 : 1000) < chance) {
			int clueId = CLUESBOY[Misc.getRandom(CLUESBOY.length - 1)];
			GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(clueId), pos, player.getUsername(), false, 150, true, 200));
			player.getPacketSender().sendMessage("@or2@You have received a clue scroll!");
		}
	}

	public static class ItemDropAnnouncer {

		private static List<Integer> ITEM_LIST;

		private static final int[] TO_ANNOUNCE = new int[] {14008, 3681, 1067,1115,21011,21012,1153,3687,21002,21002,21003,21004,3680, 3679, 19023, 11425, 18957, 18954, 14484, 2108, 2109, 2110, 2105, 2106, 2107, 3954, 17848, 17847, 3683, 3684, 3685, 3686, 3666, 3682, 17849, 7614, 14009, 18932, 2094, 2093, 12681, 12861, 19886, 2098, 14596, 2099, 6821, 2100, 2101, 2102, 19670, 19008, 19019, 14010, 14011, 14012, 14013, 14014, 18894, 18903, 18905, 18912, 19672, 19673, 19674, 14015, 14016, 10887, 19780, 2581, 2577, 14472, 14474, 14476,
				6571, 11286, 11732,21050,21051,21052,21053,21054,21030,21031,21032,21033, 4087, 4585, 11335, 2513, 15501, 15259, 12282, 6573, 17291, 12601, 13748, 13750, 13752, 13746,
				20555, 12926, 11235, 18899, 13045, 13047, 13239, 12708, 13235, 20057, 20058, 20059, 15126, 19335, 15241, 18337,
				11730, 7956, 20000, 20001, 20002, 18778, 15486, 11924, 6889, 18346, 11926, 6914, 11724, 11726, 11728, 11718, 11720, 11722, 11710, 11712, 11714,
				11702, 11704, 11706, 4706, 12284, 13051, 11708, 11716, 6739, 2570, 6562, 15220, 15018, 15020, 15019, 6731, 6733, 6735, 6737,
				11981, 11982, 11983, 11984, 11985, 18917, 18916, 19066, 11986, 11987, 11988, 11989, 11990, 11991, 11992, 11993, 11994, 11995, 11996, 11997};//All Rare Boss Drops};


		private static void init() {
			ITEM_LIST = new ArrayList<Integer>();
			for (int i : TO_ANNOUNCE) {
				ITEM_LIST.add(i);
			}
		}

		public static boolean announce(int item) {
			return ITEM_LIST.contains(item);
		}
	}
}