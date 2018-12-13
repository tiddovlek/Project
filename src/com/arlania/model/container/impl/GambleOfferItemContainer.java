package com.arlania.model.container.impl;

import java.util.Objects;

import com.arlania.world.content.gambling.GamblingManager;
import com.arlania.world.entity.impl.player.Player;

/**
 * 
 * Handles the GamblingManager
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 *
 */
public final class GambleOfferItemContainer extends Inventory {

	/**
	 * Creates a container
	 * 
	 * @param player
	 *            the player
	 */
	public GambleOfferItemContainer(Player player) {
		super(player);
	}

	@Override
	public GambleOfferItemContainer refreshItems() {
		/**
		 * Checks other player
		 */
		if (Objects.nonNull(getPlayer().getGambling().getRequested())) {
			getPlayer().getGambling().getRequested().getPacketSender().sendItemContainer(this,
					GamblingManager.OPPONENT_INVENTORY);
		}
		getPlayer().getPacketSender().sendItemContainer(this, GamblingManager.OFFER_INVENTORY);
		getPlayer().getPacketSender().sendItemContainer(getPlayer().getInventory(), 3322);
		getPlayer().getPacketSender().sendItemContainer(getPlayer().getInventory(), INTERFACE_ID);
		return this;
	}
}