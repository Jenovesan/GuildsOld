package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class gEnemy {
    public void main(CommandSender sender, String name) {
        Player player = (Player) sender;
        if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild to enemy a guild")); return;}
        String thisGuild = gUtil.getGuildIdFromPlayer(player);
        String thatGuild = gUtil.getGuildFromString(name);
        if (thatGuild == null) {player.sendMessage(util.text("&c" + name + " is not a guild")); return;}
        if (thisGuild.equals(thatGuild)) {player.sendMessage(util.text("&cYou cannot enemy your own guild")); return;}
        if (!gUtil.rankIsAdleast(player, gUtil.guildRank.valueOf(configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Enemy and Truce Guilds"))))
        {player.sendMessage(util.text("&cYou must be at least " + configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Enemy and Truce Guilds") + " to enemy guilds")); return;}
        if (gUtil.isEnemied(thisGuild, thatGuild)) {player.sendMessage(util.text("&cYou are already enemied with this guild")); return;}
        enemyGuild(thisGuild, thatGuild);
        gUtil.sendGuildMessage(thisGuild, util.secondaryColor() + "Your guild is now enemied with " + util.primaryColor() + gUtil.getGuildName(thatGuild));
        gUtil.sendGuildMessage(thatGuild, util.secondaryColor() + "Your guild is now enemied with " + util.primaryColor() + gUtil.getGuildName(thisGuild));
    }

    private void enemyGuild(String thisGuild, String thatGuild) {
        List<String> thisEnemies = configManager.getGuildsDataConfig().getConfigurationSection(thisGuild).getStringList("Enemies");
        thisEnemies.add(thatGuild);
        configManager.getGuildsDataConfig().getConfigurationSection(thisGuild).set("Enemies", thisEnemies);
        configManager.saveGuildsDataConfig();

        List<String> thatEnemies = configManager.getGuildsDataConfig().getConfigurationSection(thatGuild).getStringList("Enemies");
        thatEnemies.add(thisGuild);
        configManager.getGuildsDataConfig().getConfigurationSection(thatGuild).set("Enemies", thatEnemies);
        configManager.saveGuildsDataConfig();
    }
}
