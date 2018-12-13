package com.arlania.world.content.combat.restrictions;

import com.arlania.model.definitions.NpcDefinition;
import com.arlania.world.content.KillsTracker;
import com.arlania.world.entity.impl.npc.NPC;
import com.arlania.world.entity.impl.player.Player;

/**
 * Created by Tyler on 10/31/2017.
 */
public class KillCTRestrictor {

	public static boolean meetsRequirement(final Player player, final int npcId) {
        final AttackRequirement requirement = AttackRequirement.byId(npcId);
        //If there is no kill requirement no further consideration is required and you may attack the npc.
        if(requirement == null)
            return true;
        //If there is a requirement but your slayer task has the same NPCID you are still allowed to attack.
        if(player.getSlayer().getSlayerTask().getNpcId() == npcId) {
            return true;
        }

        //Absolute totals across all npcs. We want to consider this first to avoid unnecessarily iterating over multiple arrays.
        if(KillsTracker.getTotalKills(player) < requirement.getRequiredTotalCT()) {
            player.getPacketSender().sendMessage("@blu@You need "+requirement.getRequiredTotalCT()+" total npc kills to attack this monster.");
            return false;
        }

        final int[] requiredNpcs = requirement.getReqKillId();
        boolean canAttack = true;
        for(int i = 0; i < requiredNpcs.length; i++) {
            final KillsTracker.KillsEntry entry = KillsTracker.entryForID(player, requiredNpcs[i], false);

            //Resettable total for a single monster.
            if(entry.getAmount() < requirement.getReqKillCT(i)) {
                player.getPacketSender().sendMessage("@blu@You need "+requirement.getReqKillCT(i)+" "+NpcDefinition.forId(requirement.getReqKillId()[i]).getName()+" kills to attack this monster.");
                player.getPacketSender().sendMessage("@blu@You currently have " + requirement.getReqKillCT(i) +" "+NpcDefinition.forId(requirement.getReqKillId()[i]).getName()+" kills.");
                canAttack = false;
            }
            //Running total for a single monster.
            if(entry.getRunningTotal() < requirement.getRequiredRunningCT(i)) {
                player.getPacketSender().sendMessage("@blu@You need a total of "+requirement.getRequiredRunningCT(i)+" "+NpcDefinition.forId(requirement.getReqKillId()[i]).getName()+" kills to attack this monster.");
                player.getPacketSender().sendMessage("@blu@You currently have a total of " + entry.getRunningTotal() +" "+NpcDefinition.forId(requirement.getReqKillId()[i]).getName()+" kills.");
                canAttack = false;
            }
        }

        //If all the checks are passed then
        return canAttack;
    }

}
