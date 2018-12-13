package com.arlania.world.content.minigames.impl.weapon;

import java.util.*;

import com.arlania.model.Item;

/**
 * The enumerated type that will hold every
 * weapon (in order) that will be given to a
 * {@link Player} upon killing another player
 * in an active {@link WeaponGame}.
 * 
 * @author Jacob G. <Skype: Eclips3.HF>
 */
public enum WeaponGameWeapons {

	UNARMED(-1),
	MITHRIL_DAGGER(1209),
	ADAMANT_DAGGER(1211),
	RUNE_DAGGER(1213),
	DRAGON_DAGGER(1215),
	ABYSSAL_WHIP(4151),
	SARADOMIN_SWORD(11730),
	LAVA_WHIP(11692),
	LIME_WHIP(11690),
	ARMADYL_GODSWORD(11694);
	
	private final int itemId;
	
	private static final Map<Integer, WeaponGameWeapons> WEAPONS = new HashMap<>(10);
	
	static {
		Arrays.stream(values()).forEach(v -> WEAPONS.put(v.getItemId(), v));
	}
	
	private WeaponGameWeapons(int itemId) {
		this.itemId = itemId;
	}
	
	public int getItemId() {
		return itemId;
	}
	
	public static WeaponGameWeapons forItem(Optional<Item> item) {
		if (item.isPresent()) {
			return WEAPONS.get(item.get().getId());
		}
		
		return WEAPONS.get(WeaponGameWeapons.UNARMED);
	}
	
}
