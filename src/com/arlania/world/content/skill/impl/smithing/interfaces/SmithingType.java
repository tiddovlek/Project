package com.arlania.world.content.skill.impl.smithing.interfaces;

import com.arlania.world.entity.impl.player.Player;

import java.util.Arrays;

/**
 * Created by Stan van der Bend on 14/09/2017.
 */
public enum SmithingType {

    EQUILIBRIUM(2381, 96, new InterfaceBox[]{
            new InterfaceBox(0, 20086, "Helmet", 1000),
            new InterfaceBox(1, 20087, "Body", 1000),
            new InterfaceBox(2, 20088, "Legs", 1000),
            new InterfaceBox(3, 19341, "Helmet", 600),
            new InterfaceBox(4, 19342, "Body", 600),
            new InterfaceBox(5, 19343, "Legs", 600),
            new InterfaceBox(6, 20061, "Whip", 800),
            new InterfaceBox(7, 4151, "Whip", 400),
            new InterfaceBox(8, 20102, "Whip", 400)
    });

    private int barId, requiredLevel;

    private InterfaceBox[] interfaceBoxes;

    SmithingType(int barId, int requiredLevel, InterfaceBox[] interfaceBoxes) {
        this.barId = barId;
        this.requiredLevel = requiredLevel;
        this.interfaceBoxes = interfaceBoxes;
    }

    public void drawBoxes(final Player player){
        Arrays.stream(interfaceBoxes).forEach(box -> box.draw(player, this));
    }

    public int getBarId() {
        return barId;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public InterfaceBox[] getInterfaceBoxes() {
        return interfaceBoxes;
    }
}