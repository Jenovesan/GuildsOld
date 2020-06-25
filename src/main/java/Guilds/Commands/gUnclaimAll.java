package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class gUnclaimAll {
    public void main(CommandSender sender) {
        Player player = (Player) sender;
        if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild in order to unclaim all claims")); return;}
        String guildId = gUtil.getGuildIdFromPlayer(player);
        if (!gUtil.rankIsAdleast(player, gUtil.guildRank.valueOf(configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Claim and Unclaim"))))
        {player.sendMessage(util.text("&cYou must be at least " + configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Claim and Unclaim") + " to unclaim land")); return;}
        unclaimChunks(guildId);
        gUtil.sendGuildAnnouncement(guildId, util.primaryColor() + player.getName() + util.secondaryColor() + " has unclaimed all claims");
    }

    private void unclaimChunks(String guildId) {
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("Claims", new ArrayList<>());
        configManager.saveGuildsDataConfig();
    }
}
