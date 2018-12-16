package com.arlania.world.content.combat.magic;


import com.arlania.model.Graphic;
import com.arlania.model.container.impl.Equipment;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.player.Player;

public class CustomMagicStaff {

    public static enum CustomStaff {
        KRYPTIC_STAFF(new int[] { 13215 }, CombatSpells.KYRPTIC_SPELL.getSpell());

        private int[] itemIds;
        private CombatSpell spell;

        CustomStaff(int[] itemIds, CombatSpell spell) {
            this.itemIds = itemIds;
            this.spell = spell;
        }

        public int[] getItems() {
            return this.itemIds;
        }

        public CombatSpell getSpell() {
            return this.spell;
        }

        public static CombatSpell getSpellForWeapon(int weaponId) {
            for (CustomStaff staff : CustomStaff.values()) {
                for (int itemId : staff.getItems())
                    if (weaponId == itemId)
                        return staff.getSpell();
            }
            return null;
        }
    }

    public static boolean checkCustomStaff(Character c) {
        int weapon;
        if (!c.isPlayer())
            return false;
        Player player = (Player)c;
        weapon = player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId();
        return CustomStaff.getSpellForWeapon(weapon) != null;
    }

    public static void handleCustomStaff(Character c) {
        Player player = (Player) c;
        int weapon = player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId();
        CombatSpell spell = CustomStaff.getSpellForWeapon(weapon);
        player.setCastSpell(spell);
        player.setAutocast(true);
        player.setAutocastSpell(spell);
        player.setCurrentlyCasting(spell);
        player.setLastCombatType(CombatType.MAGIC);
    }
    public static CombatContainer getCombatContainer(Character player, Character target) {
        ((Player)player).setLastCombatType(CombatType.MAGIC);
        return new CombatContainer(player, target, 1, 1, CombatType.MAGIC, true) {
            @Override
            public void onHit(int damage, boolean accurate) {
                target.performGraphic(new Graphic(1730));
            }
        };
    }

}
