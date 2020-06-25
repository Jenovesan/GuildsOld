package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gName {
    public void main(CommandSender sender, String newName) {
        Player player = (Player) sender;
        if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild to change your guild's name")); return;}
        if (!gUtil.rankIsAdleast(player, gUtil.guildRank.valueOf(configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Set Guild Name"))))
            {player.sendMessage(util.text("&cYou must be at least " + configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Set Guild Home") + " to set you guild's name")); return;}
        if (gUtil.getGuildIdFromGuildName(newName) != null) {player.sendMessage(util.text("&cThat guild name is already in use")); return;}
        String guildId = gUtil.getGuildIdFromPlayer(player);
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("Name", newName);
        configManager.saveGuildsDataConfig();
        gUtil.sendGuildAnnouncement(guildId, util.primaryColor() + player.getName() + util.secondaryColor() + " has set your guild's name to " + util.primaryColor() + newName);
    }
}
