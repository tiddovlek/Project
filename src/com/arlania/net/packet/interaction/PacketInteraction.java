package com.arlania.net.packet.interaction;

import com.arlania.model.GameObject;
import com.arlania.model.Item;
import com.arlania.model.Position;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * Handles packet interaction
 * 
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 *
 */
public abstract class PacketInteraction {

	/**
	 * The action types
	 *
	 */
	public enum ActionType {
		TYPE_1,

		TYPE_2,

		TYPE_3,

		TYPE_4,

		ITEM_ON_NPC,

		ITEM_ON_ITEM,

		ITEM_ON_OBJECT,

		ITEM_ON_GROUND_ITEM,

		ITEM_ON_PLAYER;
	}

	/**
	 * Handles button interaction
	 * 
	 * @param player
	 *            the player
	 * @param button
	 *            the button
	 * @return button interaction
	 */
	public boolean handleButtonInteraction(final Player player, final int button) {
		return false;
	}

	/**
	 * Handles item interaction
	 * 
	 * @param player
	 *            the player
	 * @param item
	 *            the item
	 * @param slot
	 *            the slot
	 * @param type
	 *            the action type
	 * @return item interaction
	 */
	public boolean handleItemInteraction(Player player, Item item, int slot, ActionType type) {
		return false;
	}

	/**
	 * Handles npc interaction
	 * 
	 * @param player
	 *            the player
	 * @param npc
	 *            the npc
	 * @param type
	 *            the type
	 * @return npc interaction
	 */
	public boolean handleNPCInteraction(Player player, NPC npc, ActionType type) {
		return false;
	}

	/**
	 * Handles object interaction
	 * 
	 * @param player
	 *            the player
	 * @param object
	 *            the object
	 * @return object interaction
	 */
	public boolean handleObjectInteraction(Player player, GameObject object) {
		return false;
	}

	/**
	 * Handles dropping items
	 * 
	 * @param player
	 *            the player
	 * @param item
	 *            the item
	 * @param slot
	 *            the item slot
	 * @return dropping items
	 */
	public boolean handleDropItem(Player player, Item item, int slot) {
		return false;
	}

	/**
	 * Handles picking up item
	 * 
	 * @param player
	 *            the player
	 * @param item
	 *            the item
	 * @param position
	 *            the position
	 * @return the packet interaction
	 */
	public boolean handlePickUpItem(Player player, Item item, Position position) {
		return false;
	}

	/**
	 * Handles item container interaction
	 * 
	 * @param player
	 *            the player
	 * @param interfaceId
	 *            the interface id
	 * @param slot
	 *            the slot
	 * @param item
	 *            the item
	 * @param type
	 *            the type
	 * @return the packet interaction
	 */
	public boolean handleItemContainerInteraction(Player player, int interfaceId, int slot, Item item, int type) {
		return false;
	}

	/**
	 * Handles closing the interface
	 * 
	 * @param player
	 *            the player
	 */
	public void handleCloseInterface(Player player) {

	}
}
