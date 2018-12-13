package com.arlania.world.content.global;

import com.arlania.GameSettings;
import com.arlania.model.Position;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by Stan van der Bend on 16/12/2017.
 * project: runeworld-game-server
 * package: runeworld.world.entity.combat.strategy
 */
public abstract class GlobalBoss extends NPC {

    private final int cyclesTillDespawn =  Math.toIntExact(TimeUnit.MINUTES.toMillis(minutesTillDespawn()) / GameSettings.GAME_PROCESSING_CYCLE_RATE);
    private final SpawnLocation spawnLocation = Misc.random(Arrays.asList(spawnLocations()));

    public GlobalBoss(int id) {
        super(id, new Position(1, 1, 1));
        setPosition(spawnLocation.getSpawnPosition());
    }

    int cyclesOutOfCombat = 0;

    @Override
    public void sequence(){
        super.sequence();

        if(this.getCombatBuilder().isOutOfCombat())
            cyclesOutOfCombat++;
         else cyclesOutOfCombat = 0;

        if(cyclesOutOfCombat >= cyclesTillDespawn){
            GlobalBossHandler.deRegister(this);
            GlobalBossHandler.register(this);
            cyclesOutOfCombat = 0;
        }
    }

    public abstract GlobalBoss reincarnate();

    /**
     * Handles any additional behaviour upon the spawning of this {@link GlobalBoss}.
     */
    protected void spawn(){
        World.sendMessage(constructSpawnMessage());
        World.register(this);
    }

    private String constructSpawnMessage(){
        return "@red@A @whi@"+getDefinition().getName()+" @red@just spawned at @whi@"+spawnLocation.getString()+" @red@ Do ::wildywyrm to tele there!";
        
    }

    /**
     * Handles the drop after this {@link GlobalBoss} has been killed for the top {@link GlobalBoss#maximumDrops()} players.
     *
     * @param player    a rewarded {@link Player}.
     */
    protected abstract void handleDrop(Player player);

    /**
     * The potential {@link SpawnLocation}s this {@link GlobalBoss} can spawn at.
     *
     * @return all potential {@link SpawnLocation}s.
     */
    protected abstract SpawnLocation[] spawnLocations();

    /**
     * The amount of time it takes for this {@link GlobalBoss} to respawn after it has de-spawned.
     *
     * @return respawn time in minutes.
     */
    protected abstract int minutesTillRespawn();

    /**
     * The amount of time after which this {@link GlobalBoss} de-spawns due to lack of combat engagement.
     *
     * @return de-spawn time in minutes.
     */
    protected abstract int minutesTillDespawn();

    /**
     *The maximum amount of players that can receive a drop based on their damage.
     *
     * @return the maximum amount of drops.
     */
    protected abstract int maximumDrops();


}
