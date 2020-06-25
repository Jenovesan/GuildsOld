package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class gClaim {
    public void main(CommandSender sender) {
        Player player = (Player) sender;
        String worldName = player.getWorld().getName();
        Location location = player.getLocation();
        if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild in order to claim land")); return;}
        if (!gUtil.rankIsAdleast(player, gUtil.guildRank.valueOf(configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Claim and Unclaim"))))
        {player.sendMessage(util.text("&cYou must be at least " + configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Claim and Unclaim") + " to claim land")); return;}
        if (configManager.getGuildsMainConfig().getBoolean("Can Only Claim In Main World") && !worldName.equals(configManager.getGuildsMainConfig().getString("Main World Name"))) {player.sendMessage(util.text("&cYou cannot claim in this world")); return;}
        if (!gUtil.claimIsWilderness(location.getChunk())) {player.sendMessage(util.text("&cYou can only claim wilderness")); return;}
        if (gUtil.getNumberOfClaims(gUtil.getGuildIdFromPlayer(player)).equals(gUtil.getPower(gUtil.getGuildIdFromPlayer(player)))) {player.sendMessage(util.text("&cYour guild does not have enough power to claim anymore")); return;}
        claimChunk(player, location);
        gUtil.sendGuildMessage(gUtil.getGuildIdFromPlayer(player), util.primaryColor() + player.getName() + util.secondaryColor() + " has claimed the chunk " + util.primaryColor() + location.getWorld().getName() + util.seperator + util.primaryColor() + "X:" + location.getBlockX() + " Z:" + location.getBlockZ());
    }

    private void claimChunk(Player player, Location location) {
        String guildId = gUtil.getGuildIdFromPlayer(player);
        List<String> claims = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList("Claims");
        claims.add(location.getWorld().getName() + ":" + location.getChunk().getX() + ":" + location.getChunk().getZ());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("Claims", claims);
        configManager.saveGuildsDataConfig();
    }
}
