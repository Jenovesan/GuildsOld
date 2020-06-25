package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Main.Main;
import Util.util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class gWho {
    public void main(CommandSender sender, String[] args) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Player player = (Player) sender;
                String senderGuild = gUtil.getGuildIdFromPlayer(player);
                String theGuild;
                if (args.length == 2) {
                    if (util.isOfflinePlayer(args[1])) {
                        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (!gUtil.isInGuild(targetPlayer)) {player.sendMessage(util.text("&c" + args[1] + " is not in a guild")); return;}
                        theGuild = gUtil.getGuildIdFromPlayer(targetPlayer);
                    } else {
                        if (gUtil.getGuildName(args[1]) == null) {player.sendMessage(util.text("&c" + args[1] + " is not a guild")); return;}
                        theGuild = gUtil.getGuildName(args[1]);
                    }
                } else {
                    if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou are not in a guild")); return;}
                    theGuild = gUtil.getGuildIdFromPlayer(player);
                }
                player.sendMessage(gWhoMsg(theGuild, senderGuild));
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    private String gWhoMsg(String guildId, String senderGuildId) {
        String msg = "";
        ConfigurationSection section = configManager.getGuildsDataConfig().getConfigurationSection(guildId);
        msg = header(gUtil.getGuildName(guildId)) + "\n";
        msg += util.primaryColor() + "Description" + util.seperator + util.secondaryColor() + section.getString("Description") + "\n";
        msg += util.primaryColor() + "DTR" + util.seperator + getDTRString(section.getInt("DTR")) + "\n";
        msg += util.primaryColor() + "Money" + util.seperator + util.secondaryColor() + "$" + util.formatPrice((int) Math.round(section.getDouble("Money"))) + "\n";
        msg += util.primaryColor() + "Power" + util.seperator + util.secondaryColor() + gUtil.getPower(guildId) +"\n";
        msg += util.primaryColor() + "Enemies" + util.seperator + util.secondaryColor() + getEnemyString(guildId, senderGuildId) + "\n";
        msg += util.primaryColor() + "Members" + util.seperator + getMembersString(guildId) + "\n";
        msg += util.primaryColor() + getFooter(Objects.requireNonNull(gUtil.getGuildName(guildId)));
        return msg;
    }

    private String getMembersString(String guildId) {
        String string = util.secondaryColor();
        StringBuilder sb = new StringBuilder();
        List<String> members = gUtil.getAllGuildMemebers(guildId);
        for (int i = 0; i < members.size(); i++) {
            OfflinePlayer member = Bukkit.getOfflinePlayer(UUID.fromString(members.get(i)));
            String color = util.text("&7");
            if (member.isOnline()) {color = util.secondaryColor();}
            string = sb.append(color).append(configManager.getGuildsMainConfig().getString(gUtil.getGuildRank(member) + " Symbol")).toString();
            if (gUtil.getTitle(member) == null) {
                string = sb.append(member.getName()).toString();
            } else {
                string = sb.append(gUtil.getTitle(member)).append(" ").append(member.getName()).toString();
            }
            if (i != members.size() - 1) {string = sb.append(", ").toString();}
        }
        return string;
    }

    private String getEnemyString(String gWhoGuild, String senderGuild) {
        List<String> enemyGuilds = configManager.getGuildsDataConfig().getConfigurationSection(gWhoGuild).getStringList("Enemies");
        StringBuilder sb = new StringBuilder();
        String string = "";
        for (int i = 0; i < enemyGuilds.size(); i++) {
            String guild = enemyGuilds.get(i);
            String color = util.secondaryColor();
            if (gUtil.isEnemied(guild, senderGuild)) {color = util.text("&c");}
            else if (guild.equals(senderGuild)) {color = util.primaryColor();}
            string = sb.append(color).append(gUtil.getGuildName(guild)).toString();
            if (i != enemyGuilds.size() - 1) {string = sb.append(util.secondaryColor()).append(", ").toString();}
        }
        return string;
    }

    private String getDTRString(Integer DTR) {
        String color = util.text("&a");
        if (DTR < 11 && DTR > 4) {
            color = util.text("&6");
        } else if (DTR < 6 && DTR > 0) {
            color = util.text("&c");
        } else if (DTR == 0) {
            return util.text("&0RAIDABLE");
        }
        return color + DTR + "/15";
    }

    String header(String guildName) {
        return util.primaryColor() + util.text("&m") + "---------------" + util.text("&r") + util.tertiaryColor() + "[ " + util.secondaryColor() + guildName + util.tertiaryColor() + " ]" + util.primaryColor() + util.text("&m") + "---------------";
    }
    String getFooter(String guildName) {
        String footer = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32 + guildName.toCharArray().length; i++) {
            footer = sb.append("-").toString();
        }
        return util.text("&m") + footer;
    }

}
