package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class gKick {
    public void main(CommandSender sender, String playerName) {
        Player kicker = (Player) sender;
        if (!util.isOfflinePlayer(playerName)) {kicker.sendMessage(util.text("&c" + playerName + " is not a player")); return;}
        OfflinePlayer kickee = Bukkit.getOfflinePlayer(playerName);
        if (!gUtil.isInGuild(kicker)) {kicker.sendMessage(util.text("&cYou must be in a guild to kick someone")); return;}
        if (!gUtil.isInGuild(kickee)) {kicker.sendMessage(util.text("&cThat player is not in a guild")); return;}
        if (!gUtil.getGuildIdFromPlayer(kicker).equals(gUtil.getGuildIdFromPlayer(kickee))) {kicker.sendMessage(util.text("&This player is not in your guild")); return;}
        if (!(gUtil.rankIsAdleast(kicker, gUtil.guildRank.Mod) && gUtil.rankIsHigher(kicker, kickee))) {kicker.sendMessage(util.text("&cYou do not have a high enough rank to kick this player")); return;}
        String kickerGuildId = gUtil.getGuildIdFromPlayer(kicker);
        String kickeeGuildId = gUtil.getGuildIdFromPlayer(kickee);
        kickPlayer(kickee, kickeeGuildId);
        if (kickee.isOnline()) {
            kickee.getPlayer().sendMessage(util.secondaryColor() + "You have been kicked from " + util.primaryColor() + gUtil.getGuildName(kickeeGuildId));
        }
        gUtil.sendGuildAnnouncement(kickerGuildId, util.primaryColor() + kicker.getName() + util.secondaryColor() + " has kicked " + util.primaryColor() + kickee.getName() + util.secondaryColor() + " from your guild");
    }

    private void kickPlayer(OfflinePlayer player, String guildId) {
        gUtil.guildRank rank = gUtil.getGuildRank(player);
        List<String> rankList = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList(rank.toString() + "s");
        rankList.remove(player.getUniqueId().toString());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set(rank.toString() + "s", rankList);
        removeFromTitleList(player, guildId);
        configManager.saveGuildsDataConfig();
    }

    private void removeFromTitleList(OfflinePlayer player, String guildId) {
        List<String> titles = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList("Titles");
        for (int i = 0; i < titles.size(); i++) {
            String uuid = titles.get(i).split(":")[0];
            if (uuid.equals(player.getUniqueId().toString())) {
                titles.remove(i);
                break;
            }
        }
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("Titles", titles);
    }
}
