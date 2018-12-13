package com.arlania.util.banning;

import com.arlania.world.World;
import com.arlania.world.content.PlayerPunishment;
import com.arlania.world.entity.impl.player.Player;
import com.arlania.world.entity.impl.player.PlayerHandler;
import com.arlania.world.entity.impl.player.PlayerSaving;
import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Stan van der Bend on 23/06/2017.
 */
public class BanHammer {

    public static boolean ban(BanType type, Player theBanningPlayer, String playerToBeBanned){
        // Create the path and file objects.
        Path path = Paths.get("./data/saves/characters/", playerToBeBanned + ".json");
        File file = path.toFile();

        // If the file doesn't exist, we're logging in for the first
        // time and can skip all of this.
        if (!file.exists()) {
            theBanningPlayer.sendMessage("The file for "+playerToBeBanned+" was not found!");
            return false;
        }

        // Now read the properties from the json parser.
        try (FileReader fileReader = new FileReader(file)) {

            final JsonParser fileParser = new JsonParser();
            final Object object = fileParser.parse(fileReader);

            if (object instanceof JsonNull) {
                theBanningPlayer.sendMessage("The file for "+playerToBeBanned+" is nulled!");
                return false;
            }

            JsonObject reader = (JsonObject) object;

            if(reader.has(type.getKeyWord())){
                final String value = reader.get(type.getKeyWord()).getAsString();
                type.process(playerToBeBanned, value);
                theBanningPlayer.getPacketSender().sendConsoleMessage("Player " + playerToBeBanned + "'s "+type.name()+" was successfully banned. Command logs written.");
                return true;
            }
            if(PlayerHandler.getPlayerForName(playerToBeBanned) != null)
                World.deregister(PlayerHandler.getPlayerForName(playerToBeBanned));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void regBan(Player player, String playerToBan) {
        if (!PlayerSaving.playerExists(playerToBan)) {
            player.getPacketSender().sendConsoleMessage("Player " + playerToBan + " does not exist.");
            return;
        } else {
            if (PlayerPunishment.banned(playerToBan)) {
                player.getPacketSender()
                        .sendConsoleMessage("Player " + playerToBan + " already has an active ban.");
                return;
            }
             PlayerPunishment.ban(playerToBan);
            player.getPacketSender().sendConsoleMessage(
                    "Player " + playerToBan + " was successfully banned. Command logs written.");
            Player toBan = World.getPlayerByName(playerToBan);
            if (toBan != null) {
                World.deregister(toBan);
                System.out.println("Player got banned.");
                PlayerHandler.handleLogout(toBan);
                player.getPacketSender().sendConsoleMessage("Kicked " + toBan.getUsername() + ".");
            }
        }
    }

    public static void unban(BanType type, Player player, String playerToUnban) {

        if (!PlayerSaving.playerExists(playerToUnban)) {
            player.getPacketSender().sendConsoleMessage("Player " + playerToUnban + " does not exist.");
            return;
        } else {
            if (!PlayerPunishment.banned(playerToUnban)) {
                player.getPacketSender().sendConsoleMessage("Player " + playerToUnban + " is not banned!");
                return;
            }
            PlayerPunishment.unban(playerToUnban);
            player.sendMessage(
                    "Player " + playerToUnban + " was successfully unbanned. Command logs written.");
        }
    }

    public static void unban(BanType type, Player player, Player playerToUnban) {
        unban(type, player, playerToUnban.getUsername());
    }
}

