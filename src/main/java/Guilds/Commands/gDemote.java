package Guilds.Commands;

import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gDemote {
    public void main(CommandSender sender, String name) {
        Player demoter = (Player) sender;
        if (!util.isOfflinePlayer(name)) {demoter.sendMessage(util.text(name + " is not a player")); return;}
        OfflinePlayer demotee = Bukkit.getOfflinePlayer(name);
        if (!gUtil.isInGuild(demoter)) {demoter.sendMessage(util.text("&cYou must be in a guild to demote someone")); return;}
        String guildId = gUtil.getGuildIdFromPlayer(demoter);
        if (!guildId.equals(gUtil.getGuildIdFromPlayer(demotee))) {demoter.sendMessage(util.text("&cThis player is not in your guild")); return;}
        if (gUtil.rankIsHigherByAmount(demoter, demotee) < 1) {demoter.sendMessage(util.text("&cYour rank is not high enough to demote this person")); return;}
        if (gUtil.getGuildRank(demotee).equals(gUtil.guildRank.Recruit)) {demoter.sendMessage(util.text("&cThis player cannot be demoted any further")); return;}
        gUtil.changeRank(demotee, -1);
        demoter.sendMessage(util.text(util.primaryColor() + name + util.secondaryColor() + " has been demoted to " + util.primaryColor() + gUtil.getGuildRank(demotee)));
        if (demotee.isOnline()) {demotee.getPlayer().sendMessage(util.text(util.secondaryColor() + "You have been demoted to " + util.primaryColor() + gUtil.getGuildRank(demotee)));}
    }
}
