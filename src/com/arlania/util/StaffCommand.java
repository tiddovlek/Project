package com.arlania.util;


import java.util.Arrays;

import com.arlania.world.content.BankPin;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.PlayerHandler;



/**
 * Created by Stan van der Bend on 27/09/2017.
 */
public class StaffCommand {

    public static void parse(CommandTypes type, final Player player, String command){

        if (!player.getRights().isStaff())
            return;

        if(!command.contains(" "))
        {
            player.sendMessage("Please use the correct syntax, Example: '::ban stan");
            return;
        }

        final String[] split = command.split(" ");
        Arrays.stream(split).forEach(System.out::println);

        final String targetName = Misc.formatPlayerName(command.substring(split[0].length()).trim());

        final Player onlineTarget = PlayerHandler.getPlayerForName(targetName);
        final Player target = onlineTarget == null ? Misc.accessPlayer(targetName) : onlineTarget;

        if(target == null)
        {
            player.sendMessage("The player "+targetName+" could not be found.");
            return;
        }

        type.execute(player, command);

    }

  

}


