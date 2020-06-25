package Guilds.Commands;

import Guilds.guildUtil.gUtil;
import Guilds.storage.gStorage;
import Util.util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gChat {
    public void main(CommandSender sender, String arg) {
        Player player = (Player) sender;
        if (arg.equalsIgnoreCase("c") || arg.equalsIgnoreCase("chat")) { joinGuildChat(player); return; }
        joinPublicChat(player);
    }

    private void joinGuildChat(Player player) {
        if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild to enter a guild chat")); return;};
        gStorage.getInGuildChat().add(player);
        player.sendMessage(util.text(util.secondaryColor() + "You have entered ") + util.primaryColor() + "Guild Chat");
    }

    private void joinPublicChat(Player player) {
        gStorage.getInGuildChat().remove(player);
        player.sendMessage(util.text(util.secondaryColor() + "You have entered ") + util.primaryColor() + "Public Chat");
    }
}
