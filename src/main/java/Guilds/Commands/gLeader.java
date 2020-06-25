package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class gLeader {
    public void main(CommandSender sender, String name) {
        Player leader = (Player) sender;
        if (!util.isOfflinePlayer(name)) {leader.sendMessage(util.text("&c" + name + " is not a player")); return;}
        if (!gUtil.getGuildRank(leader).equals(gUtil.guildRank.Leader)) {leader.sendMessage(util.text("&cYou must be leader to give someone leader")); return;}
        OfflinePlayer leadee = Bukkit.getOfflinePlayer(name);
        String guildId = gUtil.getGuildIdFromPlayer(leader);
        if (!guildId.equals(gUtil.getGuildIdFromPlayer(leadee))) {leader.sendMessage(util.text("&cThis player is not in your guild")); return;}
        setCoLeader(leader, guildId);
        setLeader(leadee, guildId);
        gUtil.sendGuildAnnouncement(guildId, util.primaryColor() + leader.getName() + util.secondaryColor() + " has given guild leader to " + util.primaryColor() + leadee.getName());
    }

    private void setLeader(OfflinePlayer player, String guildId) {
        gUtil.guildRank currentRank = gUtil.getGuildRank(player);
        List<String> currentRankList = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList(currentRank.toString() + "s");
        currentRankList.remove(player.getUniqueId().toString());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set(currentRank.toString() + "s", currentRankList);
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("Leader", player.getUniqueId().toString());
        configManager.saveGuildsDataConfig();
    }

    private void setCoLeader(Player player, String guildId) {
        List<String> coLeaderList = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList("CoLeaders");
        coLeaderList.add(player.getUniqueId().toString());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("CoLeaders", coLeaderList);
        configManager.saveGuildsDataConfig();
    }
}
