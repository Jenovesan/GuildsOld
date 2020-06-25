package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class gTitle {
    public void main(CommandSender sender, String name, String title) {
        Player titler = (Player) sender;
        if (!util.isOfflinePlayer(name)) {titler.sendMessage(util.text("&c" + name + " is not a player")); return;}
        OfflinePlayer titlee = Bukkit.getOfflinePlayer(name);
        if (!gUtil.isInGuild(titler)) {titler.sendMessage(util.text("&cYou must be in a faction to change a players title")); return;}
        String guildId = gUtil.getGuildIdFromPlayer(titler);
        if (!guildId.equals(gUtil.getGuildIdFromPlayer(titlee))) {titler.sendMessage(util.text("&cThis player is not in your guild")); return;}
        if (!(gUtil.rankIsHigher(titler, titlee) || gUtil.rankIsAdleast(titler, gUtil.guildRank.CoLeader))) {titler.sendMessage(util.text("&cYou must have a higher rank to title this player")); return;}
        titlePlayer(titlee, guildId, title);
        titler.sendMessage(util.secondaryColor() + "You have added the title " + util.primaryColor() + title + util.secondaryColor() + " to " + util.primaryColor() + titlee.getName());
        if (titlee.isOnline()) {titlee.getPlayer().sendMessage(util.primaryColor() + titler.getName() + util.secondaryColor() + " has changed your title to " + util.primaryColor() + title);}
    }

    private void titlePlayer(OfflinePlayer player, String guildId, String title) {
        List<String> titles = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList("Titles");
        for (int i = 0; i < titles.size(); i++) {
            String uuid = titles.get(i).split(":")[0];
            if (uuid.equals(player.getUniqueId().toString())) {
                titles.set(i, uuid + ":" + title);
                break;
            } else if (i == (titles.size() - 1)) {
                titles.add(player.getUniqueId().toString() + ":" + title);
            }
        }
        if (titles.size() == 0) {titles.add(player.getUniqueId().toString() + ":" + title);}
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("Titles", titles);
        configManager.saveGuildsDataConfig();
    }
}
