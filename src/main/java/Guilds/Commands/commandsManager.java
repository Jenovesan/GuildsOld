package Guilds.Commands;

import net.minecraft.server.v1_8_R3.CommandExecute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class commandsManager extends CommandExecute implements CommandExecutor {
    gCreate gCreate = new gCreate();
    gHelp gHelp = new gHelp();
    gList gList = new gList();
    gInvite gInvite = new gInvite();
    gJoin gJoin = new gJoin();
    gLeave gLeave = new gLeave();
    gChat gChat = new gChat();
    gSethome gSethome = new gSethome();
    gHome gHome = new gHome();
    gDesc gDesc = new gDesc();
    gName gName = new gName();
    gDeinvite gDeinvite = new gDeinvite();
    gMoney gMoney = new gMoney();
    gDisband gDisband = new gDisband();
    gKick gKick = new gKick();
    gUnsethome gUnsethome = new gUnsethome();
    gPromote gPromote = new gPromote();
    gDemote gDemote = new gDemote();
    gTitle gTitle = new gTitle();
    gLeader gLeader = new gLeader();
    gEnemy gEnemy = new gEnemy();
    gTruce gTruce = new gTruce();
    gWho gWho = new gWho();
    gClaim gClaim = new gClaim();
    gUnclaim gUnclaim = new gUnclaim();
    gClaimAmount gClaimAmount = new gClaimAmount();
    gMap gMap = new gMap();
    gUnclaimAll gUnclaimAll = new gUnclaimAll();
    gUnclaimAmount gUnclaimAmount = new gUnclaimAmount();
    gMapOnOff gMapOnOff = new gMapOnOff();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("create")) {
            gCreate.main(sender, args[1]);
            return true;
        } if (args.length >= 1 && args[0].equalsIgnoreCase("list")) {
            gList.main(sender, args);
            return true;
        } if (args.length == 2 && args[0].equalsIgnoreCase("invite")) {
            gInvite.main(sender, args[1]);
            return true;
        } if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
            gJoin.main(sender, args[1]);
            return true;
        } if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
            gLeave.main(sender);
            return true;
        } if (args.length == 1 && (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("chat") || args[0].equalsIgnoreCase("p"))) {
            gChat.main(sender, args[0]);
            return true;
        } if (args.length == 1 && args[0].equalsIgnoreCase("sethome")) {
            gSethome.main(sender);
            return true;
        } if (args.length == 1 && args[0].equalsIgnoreCase("home")) {
            gHome.main(sender);
            return true;
        } if (args.length > 0 && args[0].equalsIgnoreCase("desc")) {
            gDesc.main(sender, args);
            return true;
        } if (args.length == 2 && args[0].equalsIgnoreCase("name")) {
            gName.main(sender, args[1]);
            return true;
        } if (args.length == 2 && args[0].equalsIgnoreCase("deinvite")) {
            gDeinvite.main(sender, args[1]);
            return true;
        } if (args.length >= 2 && args[0].equalsIgnoreCase("money")) {
            gMoney.main(sender, args);
            return true;
        } if (args.length == 1 && args[0].equalsIgnoreCase("disband")) {
            gDisband.main(sender);
            return true;
        } if (args.length == 2 && args[0].equalsIgnoreCase("kick")) {
            gKick.main(sender, args[1]);
            return true;
        } if (args.length == 1 && args[0].equalsIgnoreCase("unsethome")) {
            gUnsethome.main(sender);
            return true;
        } if (args.length == 2 && args[0].equalsIgnoreCase("promote")) {
            gPromote.main(sender, args[1]);
            return true;
        } if (args.length == 2 && args[0].equalsIgnoreCase("demote")) {
            gDemote.main(sender, args[1]);
            return true;
        } if (args.length == 3 && args[0].equalsIgnoreCase("title")) {
            gTitle.main(sender, args[1], args[2]);
            return true;
        } if (args.length == 2 && args[0].equalsIgnoreCase("leader")) {
            gLeader.main(sender, args[1]);
            return true;
        } if (args.length == 2 && args[0].equalsIgnoreCase("enemy")) {
            gEnemy.main(sender, args[1]);
            return true;
        } if (args.length == 2 && args[0].equalsIgnoreCase("truce")) {
            gTruce.main(sender, args[1]);
            return true;
        } if ((args.length == 1 || args.length == 2) && args[0].equalsIgnoreCase("who") || args[0].equalsIgnoreCase("show")) {
            gWho.main(sender, args);
            return true;
        } if (args.length == 1 && args[0].equalsIgnoreCase("claim")) {
            gClaim.main(sender);
            return true;
        } if (args.length == 1 && args[0].equalsIgnoreCase("unclaim")) {
            gUnclaim.main(sender);
            return true;
        } if (args.length == 2 && args[0].equalsIgnoreCase("claim")) {
            gClaimAmount.main(sender, args[1]);
            return true;
        } if (args.length == 2 && args[0].equalsIgnoreCase("map") && (args[1].equalsIgnoreCase("on") || args[1].equalsIgnoreCase("off"))) {
            gMapOnOff.main(sender, args[1]);
            return true;
        } if (args[0].equalsIgnoreCase("map")) {
            gMap.main(sender, args);
            return true;
        } if (args.length == 1 && args[0].equalsIgnoreCase("unclaimall")) {
            gUnclaimAll.main(sender);
            return true;
        } if (args.length == 2 && args[0].equalsIgnoreCase("unclaim")) {
            gUnclaimAmount.main(sender, args[1]);
            return true;
        }
        gHelp.main(sender, args);
        return false;
    }
}
