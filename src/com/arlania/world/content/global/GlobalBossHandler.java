package com.arlania.world.content.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.arlania.GameSettings;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.definitions.ItemDefinition;
import com.arlania.world.World;
import com.arlania.world.content.combat.CombatBuilder;
import com.arlania.world.content.combat.CombatFactory;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * Created by Stan van der Bend on 16/12/2017.
 * project: runeworld-game-server
 * package: runeworld.world.entity.combat.strategy.global
 */
public final class GlobalBossHandler {

    private final static List<GlobalBoss> GLOBAL_BOSSES = new ArrayList<>();

    public static void init(){

    }

    static void register(GlobalBoss globalBoss){
        GLOBAL_BOSSES.add(globalBoss);

        final long millisTillRespawn = TimeUnit.MINUTES.toMillis(globalBoss.minutesTillRespawn());
        final int cyclesTillRespawn = Math.toIntExact(millisTillRespawn / GameSettings.GAME_PROCESSING_CYCLE_RATE);

        System.out.println("A "+globalBoss.getDefinition().getName()+" will spawn in "+cyclesTillRespawn+" cycles.");

        globalBoss.spawn();
    }
    static void deRegister(GlobalBoss globalBoss){
    	System.out.println("Deregistered global "+globalBoss.getDefinition().getName());
        GLOBAL_BOSSES.remove(globalBoss);
        World.deregister(globalBoss);
    }

    public static void onDeath(GlobalBoss npc) {
        handleDrop(npc);
        deRegister(npc);
        register(npc.reincarnate());
    }
    private static void handleDrop(NPC npc) {
    	final int damageMapSize = npc.getCombatBuilder().getDamageMap().size();

        if(npc.getCombatBuilder().getDamageMap().size() == 0)
            return;

        final Map<Player, Integer> killers = new HashMap<>();

        for(Map.Entry<Player, CombatBuilder.CombatDamageCache> entry : npc.getCombatBuilder().getDamageMap().entrySet()) {

            if(entry == null)  {
                continue;
            }

            final long timeout = entry.getValue().getStopwatch().elapsed();

            if(timeout > CombatFactory.DAMAGE_CACHE_TIMEOUT) {
                continue;

            }

            final Player player = entry.getKey();
            player.sendMessage("GGGggggggggggggggg");

            killers.put(player, entry.getValue().getDamage());
        }

        npc.getCombatBuilder().getDamageMap().clear();

        List<Map.Entry<Player, Integer>> result = sortEntries(killers);
        int count = 0;

        for(Map.Entry<Player, Integer> entry : result) {

            final Player killer = entry.getKey();
            int reward = 3;
            killer.giveItem(reward,1);
            killer.sendMessage("<shad=0>@bla@[@mag@Boss reward@bla@] You received 1x @mag@"+ ItemDefinition.forId(reward).getName()+" @bla@in your inventory!");
            if(++count >= 3)
                break;
        }
    }



    private static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> sortEntries(Map<K, V> map) {
        final List<Map.Entry<K, V>> sortedEntries = new ArrayList<>(map.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        return sortedEntries;
    }

    public static List<GlobalBoss> getBosses(){
    	return GLOBAL_BOSSES;
    }
}
