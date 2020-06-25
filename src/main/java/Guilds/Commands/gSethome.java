package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gSethome {
    public void main(CommandSender sender) {
        Player player = (Player) sender;
        if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild to use this command")); return;}
        if (!gUtil.rankIsAdleast(player, gUtil.guildRank.valueOf(configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Set Guild Home"))))
        {player.sendMessage(util.text("&cYou must be at least " + configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Set Guild Home") + " to set you guild's home")); return;}
        String guildId = gUtil.getGuildIdFromPlayer(player);
        setGuildHome(player, guildId);
        gUtil.sendGuildAnnouncement(guildId, util.primaryColor() + player.getName() + util.secondaryColor() + " has set your guild's home at " +
                util.primaryColor() + "World: " + player.getWorld().getName() + " X: " + player.getLocation().getBlockX() + " Y: " + player.getLocation().getBlockY() + " Z: " + player.getLocation().getBlockZ());
    }

    private void setGuildHome(Player player, String guildId) {
        Location location = player.getLocation();
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").set("World", location.getWorld().getName());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").set("X", location.getX());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").set("Y", location.getY());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").set("Z", location.getZ());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").set("Pitch", location.getPitch());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").set("Yaw", location.getYaw());
        configManager.saveGuildsDataConfig();
    }
}
