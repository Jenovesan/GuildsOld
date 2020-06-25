package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class gUnclaim {
    public void main(CommandSender sender) {
        Player player = (Player) sender;
        Location location = player.getLocation();
        if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild in order to unclaim claims")); return;}
        String guildId = gUtil.getGuildIdFromPlayer(player);
        if (!gUtil.rankIsAdleast(player, gUtil.guildRank.valueOf(configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Claim and Unclaim"))))
        {player.sendMessage(util.text("&cYou must be at least " + configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Claim and Unclaim") + " to unclaim land")); return;}
        if (!gUtil.isInClaim(location, guildId)) {player.sendMessage(util.text("&cYou can only unclaim your claims")); return;}
        unclaimChunk(location, guildId);
        gUtil.sendGuildMessage(guildId, util.primaryColor() + player.getName() + util.secondaryColor() + " has unclaimed the chunk " + util.primaryColor() + location.getWorld().getName() + util.seperator + util.primaryColor() + "X:" + location.getBlockX() + " Z:" + location.getBlockZ());
    }

    private void unclaimChunk(Location location, String guildId) {
        List<String> unclaims = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList("Claims");
        unclaims.remove(location.getWorld().getName() + ":" + location.getChunk().getX() + ":" + location.getChunk().getZ());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("Claims", unclaims);
        configManager.saveGuildsDataConfig();
    }
}
