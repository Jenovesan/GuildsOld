package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Guilds.storage.gStorage;
import Util.util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class gJoin {
    public void main(CommandSender sender, String string) {
        Player player = (Player) sender;
        if (gUtil.isInGuild(player)) { player.sendMessage(util.text("&cYou cannot join another guild until you leave the guild you are currently in")); return; }
        if (Bukkit.getPlayerExact(string) != null && !gUtil.isInGuild(Bukkit.getPlayerExact(string))) {player.sendMessage(util.text("&c") + Bukkit.getPlayerExact(string).getName() + util.text(" is not in a guild")); return; }
        if (gUtil.getGuildFromString(string) == null) {player.sendMessage(util.text("&c") + string + util.text(" is not a guild")); return; }
        String guildId = gUtil.getGuildFromString(string);
        if (!(gStorage.getInvitedPlayers().containsKey(player) || gStorage.getInvitedPlayers().getOrDefault(player, "").equals(guildId))){ player.sendMessage(util.text("&cYou are not invited to this guild")); return; }
        if (gUtil.getNumberOfMembers(guildId) >= configManager.getGuildsMainConfig().getInt("Max Players In A Guild")) {player.sendMessage(util.text("&cThis guild is full")); return;}
        gUtil.sendGuildAnnouncement(guildId, util.primaryColor() + player.getName() + util.secondaryColor() + " has joined your guild");
        joinGuild(player, guildId);
        gStorage.getInvitedPlayers().remove(player);
        player.sendMessage(util.secondaryColor() + "You have successfully joined " + util.primaryColor() + gUtil.getGuildName(guildId));
    }

    private void joinGuild(Player player, String guildId) {
        List<String> recruits = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList("Recruits");
        recruits.add(player.getUniqueId().toString());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("Recruits", recruits);
        configManager.saveGuildsDataConfig();
    }
}
