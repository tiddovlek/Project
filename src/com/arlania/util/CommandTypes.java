package com.arlania.util;


import java.util.function.Consumer;
import java.util.function.Predicate;

import com.arlania.model.PlayerRights;
import com.arlania.util.banning.BanHammer;
import com.arlania.util.banning.BanType;
import com.arlania.world.World;
import com.arlania.world.content.PlayerPunishment;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.PlayerHandler;

import static com.arlania.model.PlayerRights.*;

public enum CommandTypes implements Command {
  
    BAN("ban", MODERATOR) {
        @Override void execute(Player... players) {
                BanHammer.ban(BanType.USER_BAN, players[0], players[1].getUsername());
                World.deregister(players[1]);
                  
        }
    },
    IP_BAN("ipban", MODERATOR) {
        @Override void execute(Player... players) {
                BanHammer.ban(BanType.IP, players[0], players[1].getUsername());
                World.deregister(players[1]);
        }
    },
    JAIL("jail", SUPPORT) {
        @Override void execute(Player... players) { PlayerPunishment.Jail.jailPlayer(players[1]); }
    },
    MUTE("mute", SUPPORT) {
        @Override void execute(Player... players) {
        	PlayerPunishment.mute(players[1].getUsername()); 
        	players[0].sendMessage("Succesfully muted "+players[1].getUsername()+".");
        	}
    },
    UN_BAN("unban", MODERATOR){
        @Override void execute(Player... players) { 
        	BanHammer.unban(BanType.USER_BAN, players[1], players[1]);
        
        }
    },
    UN_IP_BAN("unipban", MODERATOR){
        @Override void execute(Player... players) { BanHammer.unban(BanType.IP, players[0], players[1]); }
    },
    UN_JAIL("unjail", SUPPORT) {
        @Override void execute(Player... players) { PlayerPunishment.Jail.unjail(players[1]); }
    },
    UN_MUTE("unmute", SUPPORT) {
        @Override void execute(Player... players) {
        	PlayerPunishment.unmute(players[1].getUsername()); 
        	players[0].sendMessage("Succesfully unmuted "+players[1].getUsername()+".");
            
        	}
    };
	
    private String command;
    protected PlayerRights rights;

    CommandTypes(String command, PlayerRights minRights) {
        this.command = command;
        this.rights = minRights;
    }

    public String getCommand() {
        return command;
    }

    abstract void execute(final Player... players);

    @Override
    public void execute(final Player player, String command) {

        final String[] split = command.split(" ");
        final String targetName = Misc.formatPlayerName(command.substring(split[0].length()).trim());

        final Player onlineTarget = PlayerHandler.getPlayerForName(targetName);
        final Player target = onlineTarget == null ? Misc.accessPlayer(targetName) : onlineTarget;

        if(target == null)
        {
            player.sendMessage("The player "+targetName+" could not be found.");
            return;
        }

        execute(player, target);
    }

    private static Consumer<Player> printTo(Player initiator){
        return player -> initiator.sendMessage("Found a match with "+player.getUsername());
    }

    private static Predicate<Player> hasEqualIp(Player target){
        return player -> player.getHostAddress().equals(target.getHostAddress());
    }

    private static Predicate<Player> hasEqualSerial(Player target){
        return player -> player.getSerialNumber().equals(target.getSerialNumber());
    }

}
