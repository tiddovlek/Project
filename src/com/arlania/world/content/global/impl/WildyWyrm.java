package com.arlania.world.content.global.impl;

import com.arlania.model.Position;
import com.arlania.world.content.global.GlobalBoss;
import com.arlania.world.content.global.SpawnLocation;
import com.arlania.world.entity.impl.player.Player;

/**
 * Created by Stan van der Bend on 16/12/2017.
 * project: runeworld-game-server
 * package: runeworld.world.entity.combat.strategy.global.impl
 */
public class WildyWyrm extends GlobalBoss {

    private final static int NPC_ID = 3334;
    private final static int KEY_ITEM_ID = 19933;

    private final static String RECEIVE_DROP_MESSAGE = "You received a key drop from the Wildywyrm.";

    public WildyWyrm() {
        super(NPC_ID);
    }

    @Override
    public GlobalBoss reincarnate() {
        return new WildyWyrm();
    }

    @Override
    protected void handleDrop(Player player) {
        player.giveItem(KEY_ITEM_ID, 1);
        player.sendMessage(RECEIVE_DROP_MESSAGE);
    }

    @Override
    protected SpawnLocation[] spawnLocations() {
        return new SpawnLocation[]{
                new SpawnLocation(new Position(3101, 3824, 0), "Wilderness")
        };
    }

    @Override
    protected int minutesTillRespawn() {
        return 120;
    }

    @Override
    protected int minutesTillDespawn() {
        return 10;
    }

    @Override
    protected int maximumDrops() {
        return 5;
    }
}
