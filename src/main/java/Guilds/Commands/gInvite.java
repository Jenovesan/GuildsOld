package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Guilds.storage.gStorage;
import Main.Main;
import Util.util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class gInvite {
    public void main(CommandSender sender, String playerString) {
        Player inviter = (Player) sender;
        if (!util.isPlayer(playerString)) {inviter.sendMessage(playerString + util.text("&c is not a player")); return;}
        Player invitee = Bukkit.getPlayerExact(playerString);
        if (!gUtil.isInGuild(inviter)) { inviter.sendMessage(util.text("&cYou must be in a guild to invite someone")); return; }
        String inviterGuildId = gUtil.getGuildIdFromPlayer(inviter);
        if (!gUtil.rankIsAdleast(inviter, gUtil.guildRank.Mod)) { inviter.sendMessage(util.text("&cYou must adleast be Mod in your guild to invite")); return;}
        if (gUtil.isInGuild(invitee)) { inviter.sendMessage(util.text("&cThat player is in a guild")); return; }
        gStorage.getInvitedPlayers().put(invitee, inviterGuildId);
        removeInviteLater(invitee);
        invitee.sendMessage(util.secondaryColor() + "You have been invited to join " + util.primaryColor() + gUtil.getGuildName(inviterGuildId));
        gUtil.sendGuildAnnouncement(inviterGuildId,util.primaryColor() + inviter.getName() +
                util.secondaryColor() + " has invited " + util.primaryColor() + invitee.getName() + util.secondaryColor() + " to the guild");
    }

    private void removeInviteLater(Player invitee) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (gStorage.getInvitedPlayers().containsKey(invitee)) {
                    if (invitee.isOnline()) { invitee.sendMessage(util.text("&cYour invitation to " + gUtil.getGuildName(gStorage.getInvitedPlayers().get(invitee)) + " has expired"));}
                    gStorage.getInvitedPlayers().remove(invitee);
                }
            }
        }.runTaskLaterAsynchronously(Main.getInstance(), Integer.parseInt(configManager.getGuildsMainConfig().getString("Seconds Before Guild Invites Expire")) * 20);
    }
}
