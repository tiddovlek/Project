package com.arlania.world.content.itemloading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.arlania.model.Item;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.entity.impl.player.Player;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

/**
 * Handles the action of loading ammunition into minigun
 * 
 * @author Lewis
 */
public class MinigunLoading {

	/**
	 * The player participating in this action.
	 */
	private final Player player;

	/**
	 * Constructs a new instance of this class which assigns {@link #player} to
	 * the parameterized value.
	 * 
	 * @param player
	 *            The player participating in this action.
	 */
	public MinigunLoading(Player player) {
		this.player = player;
	}

	/**
	 * An {@link ImmutableSet} which specifies which {@link Item#getId}'s are
	 * considered ammunition
	 */
	public static final ImmutableSet<Integer> LOADABLE_AMMUNITION = ImmutableSet.of(7108);

	/**
	 * An {@link Multiset} of {@link Item}'s which handles the contents of the
	 * bandage.
	 */
	private final Multiset<Integer> MinigunContents = HashMultiset.create();

	/**
	 * Returns {@link #MinigunContents} for public(global) use.
	 * 
	 * @return {@link #MinigunContents}.
	 */
	public Multiset<Integer> getContents() {
		return MinigunContents;
	}

	/**
	 * Handles the action of loading ammo into minigun.
	 */
	public void handleLoadMinigun(Item item) {
		if (LOADABLE_AMMUNITION.contains(item.getId())) {
			for (int ammunition : MinigunContents) {
				if (item.getId() != ammunition) {
					player.getPacketSender().sendMessage("There is already ammunition loaded in your minigun.");
					return;
				}
			}
			MinigunContents.setCount(item.getId(), MinigunContents.count(item.getId()) + item.getAmount());
			player.getPacketSender().sendMessage("Ammo: <col=329500>" + ItemDefinition.forId(item.getId()).getName()
					+ " x " + MinigunContents.count(item.getId()) + "</col>.");
			player.getInventory().delete(item);
		}
	}

	/**
	 * Handles the action of checking the contents of the minigun.
	 */
	public void handleCheckMinigun() {
		if (MinigunContents.isEmpty()) {
			player.getPacketSender().sendMessage("The minigun has no ammunition.");
			return;
		}

		for (Entry<Integer> ammunition : MinigunContents.entrySet()) {
			player.getPacketSender()
					.sendMessage("Minigun: <col=329500>" + ItemDefinition.forId(ammunition.getElement()).getName() + " x "
							+ MinigunContents.count(ammunition.getElement()) + "</col>.");
		}

	}

	/**
	 * Handles the action of unloading the contents of the bandage.
	 */
	public void handleUnloadMinigun() {
		if (MinigunContents.isEmpty()) {
			player.getPacketSender().sendMessage("Your minigun is already empty!");
			return;
		}

		for (Entry<Integer> ammunition : MinigunContents.entrySet()) {
			player.getInventory().add(new Item(ammunition.getElement(), MinigunContents.count(ammunition.getElement())));
			MinigunContents.remove(ammunition.getElement(), MinigunContents.count(ammunition.getElement()));
		}

	}

}