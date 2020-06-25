package Guilds.Commands;

import Util.util;
import org.bukkit.command.CommandSender;

public class gHelp {
    public void main(CommandSender sender, String[] args) {
        int pageNumber = 1;
        String[] helpPages = new String[] {help1(), help2(), help3()};
        if (args.length == 2 && util.isNumber(args[1])) { pageNumber = Integer.parseInt(args[1]);}
        sender.sendMessage(header(pageNumber) + util.text("&r") + "\n" + helpPages[pageNumber - 1] + util.text("&r") + "\n" + footer(pageNumber, helpPages.length));
    }
    String header(Integer pageNumber) {
        return util.primaryColor() + util.text("&m") + "------------" + util.text("&r") + util.tertiaryColor() + "[ " + util.secondaryColor() + "Guild Help " + pageNumber + util.tertiaryColor() + " ]" + util.primaryColor() + util.text("&m") + "------------";
    }
    String footer(Integer pageNumber, Integer maxPages) {
        return util.primaryColor() + util.text("&m") + "-------------" + util.text("&r") + util.tertiaryColor() + "[ " + util.secondaryColor() + "Page " + pageNumber + "/" + maxPages + util.tertiaryColor() + " ]" + util.primaryColor() + util.text("&m") + "-------------";
    }
    String help1() {
        return
                util.primaryColor() + "/g create [name]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Creates a guild" + "\n" +
                util.primaryColor() + "/g list" + util.tertiaryColor() + " > " + util.secondaryColor() + "Returns a list of guilds with players online" + "\n" +
                util.primaryColor() + "/g invite [name]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Invites the player to your guild" + "\n" +
                util.primaryColor() + "/g join [name]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Joins the guild" + "\n" +
                util.primaryColor() + "/g leave" + util.tertiaryColor() + " > " + util.secondaryColor() + "Leaves your guild" + "\n" +
                util.primaryColor() + "/g c,chat" + util.tertiaryColor() + " > " + util.secondaryColor() + "Joins your guild chat" + "\n" +
                util.primaryColor() + "/g p" + util.tertiaryColor() + " > " + util.secondaryColor() + "Joins public chat" + "\n" +
                util.primaryColor() + "/g sethome" + util.tertiaryColor() + " > " + util.secondaryColor() + "Sets your guild home" + "\n";
    }

    String help2() {
        return
                util.primaryColor() + "/g home" + util.tertiaryColor() + " > " + util.secondaryColor() + "Teleports you to your guild's home" + "\n" +
                util.primaryColor() + "/g desc" + util.tertiaryColor() + " > " + util.secondaryColor() + "Sets your guild's description" + "\n" +
                util.primaryColor() + "/g name" + util.tertiaryColor() + " > " + util.secondaryColor() + "Sets your guild's name" + "\n" +
                util.primaryColor() + "/g deinvite" + util.tertiaryColor() + " > " + util.secondaryColor() + "De-invites the player to your guild" + "\n" +
                util.primaryColor() + "/g money b" + util.tertiaryColor() + " > " + util.secondaryColor() + "Returns your guild's balance" + "\n" +
                util.primaryColor() + "/g money d" + util.tertiaryColor() + " > " + util.secondaryColor() + "Deposits to your guild's bank" + "\n" +
                util.primaryColor() + "/g money w" + util.tertiaryColor() + " > " + util.secondaryColor() + "Withdraws from your guild's bank" + "\n" +
                util.primaryColor() + "/g disband" + util.tertiaryColor() + " > " + util.secondaryColor() + "Disbands your guild" + "\n";
    }

    String help3() {
        return
                util.primaryColor() + "/g kick [name]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Kicks the player from your guild" + "\n" +
                util.primaryColor() + "/g unsethome" + util.tertiaryColor() + " > " + util.secondaryColor() + "Unsets your guild's home" + "\n" +
                util.primaryColor() + "/g promote [name]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Promotes that player" + "\n" +
                util.primaryColor() + "/g demote [name]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Demotes that player" + "\n" +
                util.primaryColor() + "/g title [name] [title]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Titles that player" + "\n" +
                util.primaryColor() + "/g leader [name]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Sets that player as guild leader" + "\n" +
                util.primaryColor() + "/g enemy [name]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Enemies that guild" + "\n" +
                util.primaryColor() + "/g truce [name]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Sends a truce request to that guild" + "\n" +
                util.primaryColor() + "/g who,show [name]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Returns that guild's info" + "\n" +
                util.primaryColor() + "/g claim" + util.tertiaryColor() + " > " + util.secondaryColor() + "Claims the chunk you are in" + "\n" +
                util.primaryColor() + "/g unclaim" + util.tertiaryColor() + " > " + util.secondaryColor() + "Unclaims the chunk you are in" + "\n" +
                util.primaryColor() + "/g claim [size]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Claims the amount of chunks around you" + "\n" +
                util.primaryColor() + "/g unclaim [size]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Unclaims the amount of chunks around you" + "\n" +
                util.primaryColor() + "/g map" + util.tertiaryColor() + " > " + util.secondaryColor() + "Returns a map of nearby claims" + "\n" +
                util.primaryColor() + "/g map [size]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Returns a map of nearby claims in given size" + "\n" +
                util.primaryColor() + "/g unclaimall" + util.tertiaryColor() + " > " + util.secondaryColor() + "Unclaims all your guild's claims" + "\n" +
                util.primaryColor() + "/g map [on/off]" + util.tertiaryColor() + " > " + util.secondaryColor() + "Returns g map when you move into a new chunk" + "\n";
    }
}
