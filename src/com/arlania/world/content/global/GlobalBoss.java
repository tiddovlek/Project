package com.arlania.world.content.global;

import com.arlania.GameSettings;
import com.arlania.model.Position;
import com.arlania.model.definitions.NpcDefinition;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.content.combat.strategy.CombatStrategies;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.npc.NPCMovementCoordinator;
import com.arlania.world.entity.impl.player.Player;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by Stan van der Bend on 16/12/2017.
 * project: runeworld-game-server
 * package: runeworld.world.entity.combat.strategy
 */
public abstract class GlobalBoss extends NPC {

    /** INSTANCES **/
    private NPCMovementCoordinator movementCoordinator = new NPCMovementCoordinator(this);
    private Player spawnedFor;
    private NpcDefinition definition;

    /** INTS **/
    private final int id;
    private int constitution = 100;
    private int defaultConstitution;
    private int transformationId = -1;

    /** BOOLEANS **/
    private boolean[] attackWeakened = new boolean[3], strengthWeakened = new boolean[3];
    private boolean summoningNpc, summoningCombat;
    private boolean isDying;
    private boolean visible = true;
    private boolean healed, chargingAttack;
    private boolean findNewTarget;

    private final int cyclesTillDespawn =  Math.toIntExact(TimeUnit.MINUTES.toMillis(minutesTillDespawn()) / GameSettings.GAME_PROCESSING_CYCLE_RATE);
    private final SpawnLocation spawnLocation = Misc.random(Arrays.asList(spawnLocations()));

    public GlobalBoss(int id) {
        super(id, new Position(1, 1, 1));
        setPosition(spawnLocation.getSpawnPosition());
        NpcDefinition definition = NpcDefinition.forId(id);
        if(definition == null)
            throw new NullPointerException("NPC "+id+" is not defined!");
        this.id = id;
        this.definition = definition;
        this.defaultConstitution = definition.getHitpoints() < 100 ? 100 : definition.getHitpoints();
        this.constitution = defaultConstitution;
    }

    int cyclesOutOfCombat = 0;

    public abstract GlobalBoss reincarnate();

    /**
     * Handles any additional behaviour upon the spawning of this {@link GlobalBoss}.
     */
    protected void spawn(){
        World.sendMessage(constructSpawnMessage());
        World.register(this);
        this.findNewTarget();
    }

    private String constructSpawnMessage(){
        return "@red@A @whi@"+getDefinition().getName()+" @red@just spawned at @whi@"+spawnLocation.getString()+" !";
        
    }

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

    public abstract int getAggressionDistance();

    protected abstract int getReward();// the other global one attacks  nope not from distance i can't even attack it from distance


}
