package com.arlania.model.container.impl;

import java.util.Objects;
import java.util.Optional;

import com.arlania.model.Item;
import com.arlania.model.container.ItemContainer;
import com.arlania.model.container.StackType;
import com.arlania.model.container.impl.Bank.BankSearchAttributes;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.entity.impl.player.Player;

/**
 * Represents a player's inventory item container.
 * 
 * @author relex lawl
 */

public class Inventory extends ItemContainer {

	public static final int INTERFACE_ID = 3214;

	/**
	 * The Inventory constructor.
	 * 
	 * @param player
	 *            The player who's inventory is being represented.
	 */
	public Inventory(Player player) {
		super(player);
	}

	/**
	 * Adds a set of items into the inventory.
	 *
	 * @param item
	 *            the set of items to add.
	 */
	@Override
	public void addItemSet(Item[] item) {
		for (Item addItem : item) {
			if (addItem == null) {
				continue;
			}
			add(addItem);
		}
	}

	@Override
	public int capacity() {
		return 28;
	}

	/**
	 * Deletes a set of items from the inventory.
	 *
	 * @param optional
	 *            the set of items to delete.
	 */
	public void deleteItemSet(Optional<Item[]> optional) {
		if (optional.isPresent()) {
			for (Item deleteItem : optional.get()) {
				if (deleteItem == null) {
					continue;
				}

				delete(deleteItem);
			}
		}
	}

	@Override
	public Inventory full() {
		getPlayer().getPacketSender().sendMessage("Not enough space in your inventory.");
		return this;
	}

	@Override
	public Inventory refreshItems() {
		getPlayer().getPacketSender().sendItemContainer(this, INTERFACE_ID);
		return this;
	}

	@Override
	public StackType stackType() {
		return StackType.DEFAULT;
	}

	@Override
	public Inventory switchItem(ItemContainer to, Item item, int slot, boolean sort, boolean refresh) {
		if (getItems()[slot].getId() != item.getId())
			return this;
		if (to.getFreeSlots() <= 0 && !(to.contains(item.getId()) && item.getDefinition().isStackable())) {
			to.full();
			return this;
		}
		if (to instanceof BeastOfBurden || to instanceof PriceChecker) {
			if (to instanceof PriceChecker && !item.sellable()) {
				getPlayer().getPacketSender()
						.sendMessage("You cannot do that with this item because it cannot be sold.");
				return this;
			}
			if (item.getAmount() > to.getFreeSlots()) {
				if (!item.getDefinition().isStackable()) {
					item.setAmount(to.getFreeSlots());
				}
			}
		}
		if (to instanceof Bank) {
			int checkId = ItemDefinition.forId(item.getId()).isNoted() ? item.getId() - 1 : item.getId();
			if (to.getAmount(checkId) + item.getAmount() >= Integer.MAX_VALUE
					|| to.getAmount(checkId) + item.getAmount() <= 0) {
				int canBank = (Integer.MAX_VALUE - to.getAmount(checkId));
				if (canBank == 0) {
					getPlayer().getPacketSender().sendMessage("You cannot deposit more of that item into your bank.");
					return this;
				}
				item.setAmount(canBank);
			}
		}
		delete(item, slot, refresh, to);
		if (to instanceof Bank && ItemDefinition.forId(item.getId()).isNoted()
				&& !ItemDefinition.forId(item.getId() - 1).isNoted())
			item.setId(item.getId() - 1);
		to.add(item);
		if (sort && getAmount(item.getId()) <= 0)
			sortItems();
		if (refresh) {
			refreshItems();
			to.refreshItems();
		}
		if (to instanceof Bank && getPlayer().getBankSearchingAttribtues().isSearchingBank()
				&& getPlayer().getBankSearchingAttribtues().getSearchedBank() != null) {
			BankSearchAttributes.addItemToBankSearch(getPlayer(), item);
		}
		return this;
	}

	public void add(Item[] items) {
		for (Item item : items) {
			if (Objects.nonNull(item)) {
				add(item);
			}
		}
	}

    public boolean hasItem(Item item) {
        if(contains(item.getId()) && getAmount(item.getId()) >= item.getAmount()) {
            return true;
        }
        return false;
    }
}
