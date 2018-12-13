package com.arlania.world.content.upgrade;

import com.arlania.model.definitions.ItemDefinition;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

public class Upgrade {

    public static enum UpgItem {
        STARTER_HELM(1153, new int[]{17415}, 2, 1157),
        STARTER_PLATE(1115, new int[]{17415}, 2, 1119),
        STARTER_LEGS(1067, new int[]{17415}, 2, 1069),
        STARTER_AXE(1323, new int[]{17415}, 2, 1325);

        private int[] reqItem;
        private int startItem;
        private int chance;
        private int EndItem;

        UpgItem(int startItem, int[] reqItem, int chance, int EndItem) {
            this.startItem = startItem;
            this.reqItem = reqItem;
            this.chance = chance;
            this.EndItem = EndItem;
        }

        public int getStartItem() {
            return this.startItem;//should work ye okay also the boolean?
        }

        public int[] getReqItem() {
            return this.reqItem;
        }

        public int getChance() {
            return this.chance;
        }

        public int getEndItem() {
            return this.EndItem;
        }

        public static boolean checkReq(Player p, int itemId) { //test it and let me see
            for (UpgItem item : UpgItem.values()) {
                if (item.startItem == itemId) {
                    for (int i = 0; i < item.getReqItem().length; i++) {
                        if (p.getInventory().contains(item.reqItem[i])) {
                            p.getInventory().delete(item.reqItem[i], 1);
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
            return false;
        }

        public static int getRandom(int itemId) {
            for (UpgItem item : UpgItem.values()) {
                if (item.startItem == itemId) {
                    return item.chance;
                }
            }
            return 0;
        }

        public static int addEndItem(int itemId) {
            for (UpgItem item : UpgItem.values()) {
                if (item.startItem == itemId) {
                    return item.EndItem;
                }
            }
            return 0;
        }

        public static int checkItem(Player p, int itemId) {
            for (UpgItem item : UpgItem.values()) {

                if (item.startItem == itemId) {
                    return itemId;
                } else {
                    p.getPacketSender().sendMessage("This item can't be upgraded!");
                }
            }
            return 0;
        }
    }


    public static void init(Player p, int itemId) {
        if (UpgItem.checkReq(p, itemId)) {
            int random = Misc.getRandom(UpgItem.getRandom(itemId));
            if (random == 0) {
                p.getPacketSender().sendMessage("" + random);
                p.getPacketSender().sendMessage("@gr2@[Succesfull] @bla@You have upgraded your@gr2@" + ItemDefinition.forId(itemId).getName());
                p.getInventory().delete(itemId, 1);
                p.getInventory().add(UpgItem.addEndItem(itemId), 1);
            } else {
                p.getPacketSender().sendMessage("@red@[Failed] @bla@You have failed to Upgrade your @red@" + ItemDefinition.forId(itemId).getName());
                p.getPacketSender().sendMessage("" + random);
                if(ItemDefinition.forId(itemId).getName().contains("Starter")){
                    p.getPacketSender().sendMessage("@red@ Starter gear can't break!");
                } else {
                    p.getInventory().delete(itemId, 1);
                    p.getInventory().add(UpgItem.addEndItem(itemId), 1);
                }
            }
        } else {
            World.sendMessage("You don't have the Requirements to upgrade a @or3@" + ItemDefinition.forId(itemId).getName());
        }
    }

    public static void getItems() {

    }
}
