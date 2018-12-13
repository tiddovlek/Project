package com.arlania.util.banning;

import com.arlania.model.PlayerRights;
import com.arlania.net.security.ConnectionHandler;
import com.arlania.world.content.PlayerPunishment;
import com.arlania.world.entity.impl.player.Player;

/**
 * Created by stanbend on 23/06/2017.
 */
public enum BanType implements BanProcessor {

    USER_BAN("username"){
        @Override
        public boolean checkRequiredPermissions(Player player) {
            return player.getRights().isStaff() && player.getRights().ordinal() >= PlayerRights.MODERATOR.ordinal();
        }

        @Override
        public void process(String... inputs) {
            final String playerName = inputs[0];
            PlayerPunishment.ban(playerName);
        }
    },
    CPU("last-serial"){
        @Override
        public boolean checkRequiredPermissions(Player player) {
            return player.getRights().isStaff() && player.getRights().ordinal() >= PlayerRights.ADMINISTRATOR.ordinal();
        }

        @Override
        public void process(String... inputs) {
            final String playerName = inputs[0];
            final String cpu = inputs[1];
            ConnectionHandler.banComputer(playerName, cpu);
        }
    },
    IP("ip-address"){
        @Override
        public boolean checkRequiredPermissions(Player player) {
            return player.getRights().isStaff() && player.getRights().ordinal() >= PlayerRights.ADMINISTRATOR.ordinal();
        }
        @Override
        public void process(String... inputs) {
            final String ip = inputs[0];
            PlayerPunishment.addBannedIP(ip);
        }
    };
    private String keyWord;

    BanType(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWord() {
        return keyWord;
    }

public abstract boolean checkRequiredPermissions(Player  player);
}
