package Guilds.Commands;

import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gPromote {
    public void main(CommandSender sender, String name) {
        Player promoter = (Player) sender;
        if (!util.isOfflinePlayer(name)) {promoter.sendMessage(util.text(name + " is not a player")); return;}
        OfflinePlayer promotee = Bukkit.getOfflinePlayer(name);
        if (!gUtil.isInGuild(promoter)) {promoter.sendMessage(util.text("&cYou must be in a guild to promote someone")); return;}
        String guildId = gUtil.getGuildIdFromPlayer(promoter);
        if (!guildId.equals(gUtil.getGuildIdFromPlayer(promotee))) {promoter.sendMessage(util.text("&cThis player is not in your guild")); return;}
        if (gUtil.rankIsHigherByAmount(promoter, promotee) < 2) {promoter.sendMessage(util.text("&cYour rank is not high enough to promote this person")); return;}
        gUtil.changeRank(promotee, 1);
        promoter.sendMessage(util.text(util.primaryColor() + name + util.secondaryColor() + " has been promoted to " + util.primaryColor() + gUtil.getGuildRank(promotee)));
        if (promotee.isOnline()) {promotee.getPlayer().sendMessage(util.text(util.secondaryColor() + "You have been promoted to " + util.primaryColor() + gUtil.getGuildRank(promotee)));}
    }

}
