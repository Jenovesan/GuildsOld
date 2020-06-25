package Guilds.Commands;

import Guilds.guildUtil.gUtil;
import Guilds.storage.gStorage;
import Util.util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gDeinvite {
    public void main(CommandSender sender, String name) {
        Player deInviter = (Player) sender;
        String guildId = gUtil.getGuildIdFromPlayer(deInviter);
        if (!util.isPlayer(name)) {deInviter.sendMessage(util.text(name + "&c is not a player")); return;}
        Player deInvitee = Bukkit.getPlayerExact(name);
        if (!gUtil.isInGuild(deInviter)) {deInviter.sendMessage(util.text("&cYou must be in a guild to deinvite a player")); return;}
        if (!gUtil.rankIsAdleast(deInviter, gUtil.guildRank.Mod)) {deInviter.sendMessage(util.text("&cYou must adleast be Mod in your guild to de-invite")); return;}
        if (!(gStorage.getInvitedPlayers().containsKey(deInvitee) && gStorage.getInvitedPlayers().get(deInvitee).equals(guildId))) {deInviter.sendMessage(util.text(name + "&c is not invited to your guild")); return;}
        gStorage.getInvitedPlayers().remove(deInvitee);
        gUtil.sendGuildAnnouncement(guildId,util.primaryColor() + deInviter.getName() +
                util.secondaryColor() + " has de-invited " + util.primaryColor() + deInvitee.getName() + util.secondaryColor() + " to the guild");
    }
}
