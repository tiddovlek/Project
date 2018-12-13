package com.arlania.world.content.combat.restrictions;

import com.arlania.model.definitions.NpcDefinition;

public enum AttackRequirement {

    /*
            ~ Notes Regarding Changes ~
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        This system now uses arrays for the ids and kill counts. The indices of each array after the required monsters array must match!
        This means the arrays must be of the same size. If they are not a error will be thrown to console telling you which one to edit.
        In the event this happens the KillCTRestrictor will simply ignore the current setup for that monster and disable attacking.
     */
	//TEST
	TEST(-1, 0,
            new int[] {1},
            new int[] {0},
            new int[] {100}
    );
    //CYRISUS CHAIN
    //Cyrisus(433, 0, //[1] NpcId of the monster you are trying to attack. [2] Required total npc kill count across all npcs.
          //  new int[] {8349}, //Npc Ids of the 'required monsters'.
          //  new int[] {0}, //Resettable Kill Count Requirement. Use 0 if you don't want to use this.
         //   new int[] {150} //Non-Resettable Kill Count Requirement. Use 0 if you don't want to use this.
   // ),
  //  TDS(8349, 0,
       //     new int[] {1999},
         //   new int[] {0},
       //     new int[] {100}
   // ),
   // CERBERUS(1999, 0,
      //      new int[] {8549},
      //      new int[] {0},
     //       new int[] {150}
   // ),
  //  PHOENIX(8549, 0,
   //         new int[] {1624},
    //        new int[] {0},
    //        new int[] {300}
  //  ),
    //SKOTIZO CHAIN
  //  SKOTIZO(7286, 0,
  //          new int[] {499},
  //          new int[] {0},
  //          new int[] {150}
 //   ),
    //DIREWOLF CHAIN
  //  DIREWOLF(4413, 0,
       //     new int[] {6766},
         //   new int[] {0},
       //     new int[] {300}
  //  ),
  //  SHAMAN(6766, 0,
      //      new int[] {2060},
     //       new int[] {0},
     //       new int[] {150}
  //  ),
    //GLACOR CHAIN
   // GLACOR(1382, 0,
      //      new int[] {130},
      //      new int[] {0},
      //      new int[] {300}
  //  ),
  //  YETI(130, 0,
     //       new int[] {51},
     //       new int[] {0},
     //       new int[] {50}
   // );

    private final int targetId;
    private final int[] reqKillId;
    private final int[] reqKillCT;
    private final int[] requiredRunningCT;
    private final int requiredTotalCT;

    AttackRequirement(final int targetId, final int requiredTotalCT, final int[] reqKillId, final int[] reqKillCT, final int[] requiredRunningCT) {
        this.targetId = targetId;
        this.reqKillId = reqKillId;
        this.reqKillCT = reqKillCT;
        this.requiredRunningCT = requiredRunningCT;
        this.requiredTotalCT = requiredTotalCT;
    }

    public static AttackRequirement byId(final int npcId) {
        for (AttackRequirement target : values()) {
            if(target.getTargetId() == npcId)
                return target;
        }
        return null;
    }

    public int getTargetId() {
        return targetId;
    }

    public int[] getReqKillId() {
        return reqKillId;
    }

    public int getReqKillCT(int index) {
        if(reqKillCT.length >= index)
            return reqKillCT[index];
        else {
            sendError();
            return 2147000000;
        }
    }

    public int getRequiredRunningCT(int index) {
        if(requiredRunningCT.length >= index)
            return requiredRunningCT[index];
        else {
            sendError();
            return 2147000000;
        }
    }

    private void sendError() {
        System.err.println("[AttackRequirement.java][Warning]: Potentially improper enum constant setup detected!");
        System.err.println("[AttackRequirement.java][Warning]: Please make sure the indices count of each array matches!");
        System.err.println("[AttackRequirement.java][Warning]: Effected Entity: " + NpcDefinition.forId(getTargetId()).getName() + ".");
    }

    public int getRequiredTotalCT() {
        return requiredTotalCT;
    }

}