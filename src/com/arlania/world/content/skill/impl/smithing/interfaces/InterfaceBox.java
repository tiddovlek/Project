package com.arlania.world.content.skill.impl.smithing.interfaces;

import com.arlania.model.Item;
import com.arlania.world.entity.impl.player.Player;

/**
 * Created by Stan van der Bend on 14/09/2017.
 */
public class InterfaceBox {

    private final int index, itemId;
    private final String descriptionText;
    private final int requiredBars;

    InterfaceBox(int index, int itemId, String descriptionText, int requiredBars) {
        this.index = index;
        this.itemId = itemId;
        this.descriptionText = descriptionText;
        this.requiredBars = requiredBars;
    }

    void draw(final Player player, final SmithingType type){

        final String barsDetails = getPrefixColourItemRequirements(player, type.getBarId(), requiredBars) + requiredBars + " Bars";
        final String description = getPrefixColourLevelRequirements(player, type.getRequiredLevel()) + descriptionText;

        final int column_id = SmithingInterface.getColumnId(index);
        final int column_slot_index = SmithingInterface.getSlot(index, column_id);
        player.getPacketSender().sendString(SmithingInterface.getStringBarsId(index), barsDetails);
        player.getPacketSender().sendString(SmithingInterface.getStringDescId(index), description);
        player.getPacketSender().sendSmithingData(itemId, column_slot_index, column_id, requiredBars);

    }

    private String getPrefixColourItemRequirements(Player player, int id, int amount) {
        if (player.getInventory().getAmount(id) >= amount)
            return "@gre@";
        return "@red@";
    }

    private String getPrefixColourLevelRequirements(Player player, int requriedLevel) {
        if (player.getSkillManager().getMaxLevel(13) >= requriedLevel)
            return "@whi@";
        return "@bla@";
    }
}