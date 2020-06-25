package Guilds.Commands;

import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gHome {
    public void main(CommandSender sender) {
        Player player = (Player) sender;
        if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild to go to your guild's home")); return;}
        String guildId = gUtil.getGuildIdFromPlayer(player);
        if (!gUtil.hasGuildHome(guildId)) {player.sendMessage(util.text("&cYour guild does not have a guild home")); return;}
        player.teleport(gUtil.getGuildHome(guildId));
        player.sendMessage(util.text(util.secondaryColor() + "You have been teleported to your " + util.primaryColor() + "Guild's home"));
    }
}
