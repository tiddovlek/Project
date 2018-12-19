package com.arlania.world.content.global.impl;

import com.arlania.model.Position;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.global.GlobalBoss;
import com.arlania.world.content.global.SpawnLocation;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * Created by Stan van der Bend on 16/12/2017.
 * project: runeworld-game-server
 * package: runeworld.world.entity.combat.strategy.global.impl
 */
public class Abbadon extends NPC {

    private final static int NPC_ID = 6303;


    private int[] common_drop = {18830,14010,14009,14008,14011,14013,14012,14014,14015,14016,15126,10696};
    private int[] rare_drop = {13215,13216,13217,13218,13219,13220,6199,6199,6199};
    private int[] Legendary_drop = {20112,20113,20114};
    private int[] Epic_drop = {21075,17847,21080,21079};

    public Abbadon() {
        super(NPC_ID,new Position(2586, 4837, 0));
    }


    public int getAggressionDistance() {
        int distance = 7;

        return distance;
    }

    protected SpawnLocation[] spawnLocations() {
        return new SpawnLocation[]{
                new SpawnLocation(new Position(2586, 4837, 0), "Abbadon boss teleport")
        };
    }


    protected int getReward() {
        int reward;
        int random = Misc.random(1000);
        if( random < 400) {
              reward = common_drop[RandomUtility.getRandom(common_drop.length-1)];
        } else if( random < 900) {
                reward = rare_drop[RandomUtility.getRandom(rare_drop.length-1)];
        } else if( random < 990) {
            reward = Legendary_drop[RandomUtility.getRandom(Legendary_drop.length-1)];
        } else {
            reward = Epic_drop[RandomUtility.getRandom(Epic_drop.length-1)];
        }
        return reward;
    }

}
