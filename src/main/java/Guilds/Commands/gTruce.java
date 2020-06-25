package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Main.Main;
import Util.util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class gTruce {
    HashMap<String, String> wantsToTruce = new HashMap<>();
    public void main(CommandSender sender,String name) {
        Player player = (Player) sender;
        if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild to truce a guild")); return;}
        String thisGuild = gUtil.getGuildIdFromPlayer(player);
        String thatGuild = gUtil.getGuildFromString(name);
        if (thatGuild == null) {player.sendMessage(util.text("&c" + name + " is not a guild")); return;}
        if (thisGuild.equals(thatGuild)) {player.sendMessage(util.text("&cYou cannot truce with your own guild")); return;}
        if (!gUtil.rankIsAdleast(player, gUtil.guildRank.valueOf(configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Enemy and Truce Guilds"))))
        {player.sendMessage(util.text("&cYou must be at least " + configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Enemy and Truce Guilds") + " to truce guilds")); return;}
        if (!gUtil.isEnemied(thisGuild, thatGuild)) {player.sendMessage(util.text("&cYou are not enemied with this guild")); return;}
        if (!guildsThatWantToTruce(thisGuild).contains(thatGuild)) {sendTruceRequest(thisGuild, thatGuild); return;}
        truceGuild(thisGuild, thatGuild);
        wantsToTruce.remove(thatGuild, thisGuild);
        gUtil.sendGuildMessage(thisGuild, util.secondaryColor() + "Your guild is now truced with " + util.primaryColor() + gUtil.getGuildName(thatGuild));
        gUtil.sendGuildMessage(thatGuild, util.secondaryColor() + "Your guild is now truced with " + util.primaryColor() + gUtil.getGuildName(thisGuild));
    }

    private void sendTruceRequest(String thisGuild, String thatGuild) {
        wantsToTruce.put(thisGuild, thatGuild);
        removeTruceRequestLater(thisGuild, thatGuild);
        gUtil.sendGuildMessage(thisGuild, util.secondaryColor() + "You have sent a truce request to " + util.primaryColor() + gUtil.getGuildName(thatGuild));
        gUtil.sendGuildMessage(thatGuild, util.primaryColor() + gUtil.getGuildName(thisGuild) + util.secondaryColor() + " wants to truce with your guild");
    }

    private void removeTruceRequestLater(String thisGuild, String thatGuild) {
        new BukkitRunnable() {
            @Override
            public void run() {
                wantsToTruce.remove(thisGuild, thatGuild);
            }
        }.runTaskLaterAsynchronously(Main.getInstance(), 1200);
    }

    private void truceGuild(String thisGuild, String thatGuild) {
        List<String> thisEnemies = configManager.getGuildsDataConfig().getConfigurationSection(thisGuild).getStringList("Enemies");
        thisEnemies.remove(thatGuild);
        configManager.getGuildsDataConfig().getConfigurationSection(thisGuild).set("Enemies", thisEnemies);
        configManager.saveGuildsDataConfig();

        List<String> thatEnemies = configManager.getGuildsDataConfig().getConfigurationSection(thatGuild).getStringList("Enemies");
        thatEnemies.remove(thisGuild);
        configManager.getGuildsDataConfig().getConfigurationSection(thatGuild).set("Enemies", thatEnemies);
        configManager.saveGuildsDataConfig();
    }

    private Set<String> guildsThatWantToTruce(String theGuild) {
        Set<String> guilds = new HashSet<>();
        for (Map.Entry<String, String> element : wantsToTruce.entrySet()) {
            if (element.getValue().equals(theGuild)) {guilds.add(element.getKey());}
        }
        return guilds;
    }
}
