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
public class BandosAva extends GlobalBoss {

    private final static int NPC_ID = 4540;


    public BandosAva() {
        super(NPC_ID);
    }

    @Override
    public GlobalBoss reincarnate() {
        return new BandosAva();
    }

    @Override
    protected void handleDrop(Player player) {
    }

    @Override
    protected SpawnLocation[] spawnLocations() {
        return new SpawnLocation[]{
                new SpawnLocation(new Position(2865, 9954, 0), "Bandos Avatar boss teleport")
        };
    }

    @Override
    protected int minutesTillRespawn() {
        return 1;
    }

    @Override
    protected int minutesTillDespawn() {
        return 10;
    }

    @Override
    protected int maximumDrops() {
        return 10000000;
    }
}
