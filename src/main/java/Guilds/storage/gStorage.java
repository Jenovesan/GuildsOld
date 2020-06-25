package Guilds.storage;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class gStorage {
    static HashMap<Player, String> invitedPlayers = new HashMap<>();
    public static HashMap<Player, String> getInvitedPlayers() {
        return invitedPlayers;
    }

    static Set<Player> inGuildChat = new HashSet<>();
    public static Set<Player> getInGuildChat() {
        return inGuildChat;
    }
}
