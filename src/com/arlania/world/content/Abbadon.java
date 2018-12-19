package com.arlania.world.content;

import com.arlania.model.Position;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;
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
import com.arlania.world.content.global.impl.BandosAva;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

import java.util.Map;

public class Abbadon extends NPC {

    private static long massMessageTimer = 0;


    public static int[] COMMONLOOT = {15273, 13883, 242, 248, 1620, 1451, 1457, 2362, 2360, 1459, 1276, 18831, 1726, 9244, 868, 1514, 2358, 537, 13883, 13879, 220, 1290, 2504, 4132, 1334, 1705, 4100, 4094, 4114, 6686, 1080, 1202, 11127, 1164, 6529, 15271};
    public static int[] MEDIUMLOOT = {4151, 6585, 11732, 536, 2577, 2581, 6328, 6916, 6918, 6920, 6922, 6924, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4753, 4755, 4757, 4759, 4747, 4749, 4745, 4751, 10025, 6570};
    public static int[] RARELOOT = {15606, 15608, 15610, 15612, 15614, 15616, 15618, 15620, 15622, 7671, 7673, 10840, 989, 989, 989, 989, 19335, 6739, 15259, 11720, 11722, 18782, 18782, 18782, 18782,};
    public static int[] SUPERRARELOOT = {18782, 20000, 20001, 20002, 6500, 10551, 10548, 10550, 15220, 15018, 15020, 15019, 6585, 4151, 2571, 2577, 11283, 4706, 20072, 15486, 11235, 20061, 11970, 11970, 11970, 13899, 13876, 13870, 13873, 13864, 13858, 13861, 13867, 6199, 13896, 13884, 11724, 11726, 13890, 13902, 13887, 13893, 12601, 1419, 19335, 11848, 11846, 11850, 11852, 11854, 11856, 11728, 15501};

    /**
     *
     */
    public static boolean wyrmAlive = false;
    public static int rng = 0,
    //testINTERVAL = 1*1000,
    INTERVAL = 60 * 60 * 1000,
            NPC_ID = 6303;
    /**
     *
     */
    public static final WildywyrmLocation[] LOCATIONS = {
            new WildywyrmLocation(2585, 4837, 0, "beside the Rogue's castle", "Rogue castle")
    };

    /**
     *
     */
    private static Abbadon current;
    public static String playerPanelFrame;

    /**
     * @param position
     */
    public Abbadon(Position position) {

        super(NPC_ID, position);

        // setConstitution(96500/3); //7,650
        setDefaultConstitution(10000);

    }

    /**
     *
     */
    public static void init() {

        if (massMessageTimer == 0 || (System.currentTimeMillis() - massMessageTimer >= INTERVAL)) {
            //System.out.println("massMessageTimer = "+massMessageTimer);
            //System.out.println("currentTimeMs = "+System.currentTimeMillis());
            //System.out.println("spawn wyrm called");
            massMessageTimer = System.currentTimeMillis();
            spawn();

        }

    }

    public static void sendHint(Player player) {
        player.getPacketSender().sendMessage("<col=1e56ad><img=10> " + LOCATIONS[rng].getLocation() + "!");
    }

    public static String getPlayerPanelHint() {
        return LOCATIONS[rng].getPlayerPanelHint();
    }

    /**
     *
     */
    public static void spawn() {
        if (wyrmAlive == true) { //checks if its already alive to avoid duplicates
            //System.out.println("spawn failed - wyrm is already alive");
            World.sendMessage("<col=1e56ad><img=10> [WildyWyrm]@bla@ The WildyWyrm is roaming " + LOCATIONS[rng].getLocation() + "!");
            return;
        }
        /*if(getCurrent() != null) {
        	 System.out.print("spawn failed");
            return;
        }*/

        rng = Misc.randomMinusOne(LOCATIONS.length);
        WildywyrmLocation location = LOCATIONS[rng];
        Abbadon instance = new Abbadon(location.copy());

        //System.out.println(instance.getPosition());

        World.register(instance);
        setCurrent(instance);
        //System.out.print("spawned.");
        wyrmAlive = true;
        World.sendMessage("<col=1e56ad><img=10> [WildyWyrm]@bla@ The WildyWyrm has appeared " + location.getLocation() + "!");
        World.getPlayers().forEach(p -> PlayerPanel.refreshPanel(p));

    }

    public static void handleDrop() {
        rng = Misc.randomMinusOne(LOCATIONS.length);
        WildywyrmLocation location = LOCATIONS[rng];

        Abbadon npc = new Abbadon(location.copy());
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
        /**
         *
         * @return
         */
        public static Abbadon getCurrent() {
            return current;
        }

        /**
         *
         * @param current
         */
        public static void setCurrent(Abbadon current) {
            Abbadon.current = current;
        }

        /**
         *
         * @author Nick Hartskeerl <apachenick@hotmail.com>
         *
         */
        public static class WildywyrmLocation extends Position {

            /**
             *
             */
            private String location, hint;

            /**
             *
             * @param x
             * @param y
             * @param z
             * @param location
             */
            public WildywyrmLocation(int x, int y, int z, String location, String hint) {
                super(x, y, z);
                setLocation(location);
                setHint(hint);
            }

            /**
             *
             * @return
             */


            String getLocation() {
                return location;
            }

            String getPlayerPanelHint() {
                return hint;
            }

            /**
             *
             * @param location
             */
            public void setLocation(String location) {
                this.location = location;
            }

            public void setHint(String hint) {
                this.hint = hint;
            }

        }





    }