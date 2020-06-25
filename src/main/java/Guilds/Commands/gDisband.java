package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Main.Main;
import Util.util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class gDisband {
    Set<Player> playersReadyToDisband = new HashSet<>();
    public void main(CommandSender sender) {
        Player player = (Player) sender;
        if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild to disband one")); return;}
        if (!gUtil.getGuildRank(player).equals(gUtil.guildRank.Leader)) {player.sendMessage(util.text("&cYou must be leader to disband your guild")); return;}
        if (!playersReadyToDisband.contains(player)) {
            player.sendMessage(util.secondaryColor() + "Type the command again to confirm");
            playersReadyToDisband.add(player);
            removePlayerReadyToDisbandLater(player);
            return;
        }
        playersReadyToDisband.remove(player);
        String guildId = gUtil.getGuildIdFromPlayer(player);
        gUtil.sendGuildAnnouncement(guildId, util.secondaryColor() + "Your guild has been " + util.primaryColor() + "disbanded");
        configManager.getGuildsDataConfig().set(guildId, null);
        removeFromEnemiesList(guildId);
        configManager.saveGuildsDataConfig();
    }

    private void removePlayerReadyToDisbandLater(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                playersReadyToDisband.remove(player);
            }
        }.runTaskLaterAsynchronously(Main.getInstance(), 600);
    }

    private void removeFromEnemiesList(String guildId) {
        for (String key : configManager.getGuildsDataConfig().getKeys(false)) {
            List<String> enemyList = configManager.getGuildsDataConfig().getConfigurationSection(key).getStringList("Enemies");
            if (enemyList.contains(guildId)) {
                enemyList.remove(guildId);
                configManager.getGuildsDataConfig().getConfigurationSection(key).set("Enemies", enemyList);
            }
        }
    }
}
