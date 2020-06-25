package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class gLeave {
    public void main(CommandSender sender) {
        Player player = (Player) sender;
        if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou are not in a guild")); return;}
        String guildId = gUtil.getGuildIdFromPlayer(player);
        if (gUtil.getGuildRank(player).equals(gUtil.guildRank.Leader)) {player.sendMessage(util.text("&cYou must disband the guild to leave it")); return;}
        leaveGuild(player, guildId);
        player.sendMessage(util.secondaryColor() + "You left " + util.primaryColor() + gUtil.getGuildName(guildId));
        gUtil.sendGuildAnnouncement(guildId, util.primaryColor() + player.getName() + util.secondaryColor() + " has left your guild");
    }

    private void leaveGuild(Player player, String guildId) {
        gUtil.guildRank rank = gUtil.getGuildRank(player);
        List<String> rankList = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList(rank.toString() + "s");
        rankList.remove(player.getUniqueId().toString());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set(rank.toString() + "s", rankList);
        configManager.saveGuildsDataConfig();
    }
}
