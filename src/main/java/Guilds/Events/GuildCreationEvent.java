package Guilds.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GuildCreationEvent extends Event {
    Player p;
    String gId;

    public GuildCreationEvent(Player player, String guildId) {
        gId = guildId;
        p = player;
    }

    public Player getPlayer() {
        return p;
    }

    public String getGuildId() {
        return gId;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHanderList() {
        return handlers;
    }
}
