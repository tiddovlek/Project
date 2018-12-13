package com.arlania.world.content;

import com.arlania.world.entity.impl.player.Player;

/**
 * Created by Tyler on 11/7/2017.
 */
public class PVMRanking {

    private PVMRank rank = PVMRank.BRONZE;
    private Player player = null;

    public PVMRanking(Player player) {
        this.player = player;
    }

    public String getRank() {
        return rank.name();
    }

    public void setRank(String rankName) {
        for(PVMRank rank : PVMRank.values())
            if(rank.name().equalsIgnoreCase(rankName))
                this.rank = rank;
    }

    public void check() {
        final int totalKills = KillsTracker.getTotalKills(player);
        final PVMRank nextRank = getNextRank(rank);
        if(nextRank != null) {
            System.out.println(totalKills);
            if (totalKills >= nextRank.getRequirement()) {
                promote(nextRank);
            }
        }
    }

    private void promote(PVMRank rank) {
        this.rank = rank;
        player.sendMessage("Congratulations! You have been promoted to " + rank.name().toLowerCase() + " division PvM ranking!");
    }

    private PVMRank getNextRank(PVMRank rank) {
        switch (rank) {
            case BRONZE:
                return PVMRank.SILVER;
            case SILVER:
                return PVMRank.GOLD;
            case GOLD:
                return PVMRank.PLATNUM;
            case PLATNUM:
                return PVMRank.ONYX;
        }
        return null;
    }

    enum PVMRank {
        BRONZE(0), SILVER(1000), GOLD(5000), PLATNUM(10000), ONYX(15000);

        private int requirement;


        PVMRank(int requirement) {
            this.requirement = requirement;
        }

        public int getRequirement(){
            return requirement;
        }
    }

}
