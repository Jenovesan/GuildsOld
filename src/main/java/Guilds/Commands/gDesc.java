package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class gDesc {
    public void main(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild to use this command")); return;}
        if (!gUtil.rankIsAdleast(player, gUtil.guildRank.CoLeader)) {player.sendMessage(util.text("&cYou must be at least CoLeader to change your guild's description"));}
        StringBuilder sb = new StringBuilder();
        String[] descriptionList = Arrays.copyOfRange(args, 1, args.length);
        String description = "";
        for (String string : descriptionList) { description = sb.append(string).append(" ").toString(); }
        String guildId = gUtil.getGuildIdFromPlayer(player);
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("Description", description);
        configManager.saveGuildsDataConfig();
        gUtil.sendGuildAnnouncement(guildId, util.primaryColor() + player.getName() + util.secondaryColor() + " has set your guild's description");
    }
}
