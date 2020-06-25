package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gUnsethome {
    public void main(CommandSender sender) {
        Player player = (Player) sender;
        if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild to unset your guild's home")); return;}
        if (!gUtil.rankIsAdleast(player, gUtil.guildRank.valueOf(configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Set Guild Home"))))
        {player.sendMessage(util.text("&cYou must be at least " + configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Set Guild Home") + " to set you guild's home")); return;}
        String guildId = gUtil.getGuildIdFromPlayer(player);
        unsetGuildHome(guildId);
        gUtil.sendGuildAnnouncement(guildId, util.primaryColor() + player.getName() + util.secondaryColor() + " has unset the guild home");
    }

    private void unsetGuildHome(String guildId) {
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").set("World", "");
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").set("X", "");
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").set("Y", "");
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").set("Z", "");
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").set("Pitch", "");
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").set("Yaw", "");
        configManager.saveGuildsDataConfig();
    }
}
