package com.arlania.net.packet.impl;

import com.arlania.model.Item;
import com.arlania.model.input.EnterAmount;
import com.arlania.world.content.gambling.GamblingManager;
import com.arlania.world.entity.impl.player.Player;

/**
 * Handles gamble offer input
 */
public class HandleGambleOfferInput extends EnterAmount {

	/**
	 * The interface id
	 */
	private int interfaceId;

	/**
	 * Handles gamble offer input
	 * 
	 * @param id
	 *            the id
	 * @param slot
	 *            the slot
	 * @param interfaceId
	 *            the interface id
	 */
	public HandleGambleOfferInput(int id, int slot, int interfaceId) {
		super(id, slot);
		this.setInterfaceId(interfaceId);
	}

	@Override
	public void handleAmount(Player player, int amount) {
		GamblingManager.handleGambleOffer(player, getInterfaceId(), new Item(getItem(), amount), getSlot());
	}

	/**
	 * Gets the interfaceId
	 * 
	 * @return the interfaceId
	 */
	public int getInterfaceId() {
		return interfaceId;
	}

	/**
	 * Sets the interfaceid
	 * 
	 * @param interfaceId
	 *            the interfaceId to set
	 */
	public void setInterfaceId(int interfaceId) {
		this.interfaceId = interfaceId;
	}
}
