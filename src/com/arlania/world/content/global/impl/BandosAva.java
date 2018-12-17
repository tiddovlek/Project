package com.arlania.world.content.global.impl;

import com.arlania.model.Position;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.World;
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


    private int[] common_drop = {18830,14010,14009,14008,14011,14013,14012,14014,14015,14016,15126,10696};
    private int[] rare_drop = {13215,13216,13217,13218,13219,13220,6199,6199,6199};
    private int[] Legendary_drop = {11988,11425};
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
    protected int getReward() {
        int reward;
        int random = Misc.random(500);
        if( random < 350) {

              reward = common_drop[RandomUtility.getRandom(common_drop.length-1)];

        } else if( random < 497) {
                reward = rare_drop[RandomUtility.getRandom(rare_drop.length-1)];

        } else {
            reward = Legendary_drop[RandomUtility.getRandom(Legendary_drop.length-1)];
        }
        return reward;
    }
    @Override
    protected int minutesTillDespawn() {
        return 10;
    }

    @Override
    protected int maximumDrops() {
        return 3;
    }
}
