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
 * Handles the action of loading crystals into bandages
 * 
 * @author Lewis
 */
public class CorruptBandagesLoading {

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
	public CorruptBandagesLoading(Player player) {
		this.player = player;
	}

	/**
	 * An {@link ImmutableSet} which specifies which {@link Item#getId}'s are
	 * considered crystals.
	 */
	public static final ImmutableSet<Integer> LOADABLE_CRYSTALS = ImmutableSet.of(13209);

	/**
	 * An {@link Multiset} of {@link Item}'s which handles the contents of the
	 * bandage.
	 */
	private final Multiset<Integer> CorruptBandagesContents = HashMultiset.create();

	/**
	 * Returns {@link #CorruptBandagesContents} for public(global) use.
	 * 
	 * @return {@link #CorruptBandagesContents}.
	 */
	public Multiset<Integer> getContents() {
		return CorruptBandagesContents;
	}

	/**
	 * Handles the action of loading crystals into the bandage.
	 */
	public void handleLoadCorruptBandages(Item item) {
		if (LOADABLE_CRYSTALS.contains(item.getId())) {
			for (int crystal : CorruptBandagesContents) {
				if (item.getId() != crystal) {
					player.getPacketSender().sendMessage("There are already crystals loaded in your bandage.");
					return;
				}
			}
			CorruptBandagesContents.setCount(item.getId(), CorruptBandagesContents.count(item.getId()) + item.getAmount());
			player.getPacketSender().sendMessage("Bandage: <col=329500>" + ItemDefinition.forId(item.getId()).getName()
					+ " x " + CorruptBandagesContents.count(item.getId()) + "</col>.");
			player.getInventory().delete(item);
		}
	}

	/**
	 * Handles the action of checking the contents of the Bandage.
	 */
	public void handleCheckCorruptBandages() {
		if (CorruptBandagesContents.isEmpty()) {
			player.getPacketSender().sendMessage("The bandage has no crystals in it.");
			return;
		}

		for (Entry<Integer> crystal : CorruptBandagesContents.entrySet()) {
			player.getPacketSender()
					.sendMessage("Bandages: <col=329500>" + ItemDefinition.forId(crystal.getElement()).getName() + " x "
							+ CorruptBandagesContents.count(crystal.getElement()) + "</col>.");
		}

	}

	/**
	 * Handles the action of unloading the contents of the bandage.
	 */
	public void handleUnloadCorruptBandages() {
		if (CorruptBandagesContents.isEmpty()) {
			player.getPacketSender().sendMessage("Your bandage is already empty!");
			return;
		}

		for (Entry<Integer> crystal : CorruptBandagesContents.entrySet()) {
			player.getInventory().add(new Item(crystal.getElement(), CorruptBandagesContents.count(crystal.getElement())));
			CorruptBandagesContents.remove(crystal.getElement(), CorruptBandagesContents.count(crystal.getElement()));
		}

	}

}
