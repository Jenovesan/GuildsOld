package Guilds.Commands;

import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class gList {
    public void main(CommandSender sender, String[] args) {
        HashMap<String, Integer> unsortedGuildsWithOnlinePlayers = new HashMap<>();
        LinkedHashMap<String, Integer> guildsWithOnlinePlayers = new LinkedHashMap<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            String id = gUtil.getGuildIdFromPlayer(player);
            if (id.equals("-1")) {continue;}
            unsortedGuildsWithOnlinePlayers.put(id, unsortedGuildsWithOnlinePlayers.getOrDefault(id, 0) + 1);
        }
        unsortedGuildsWithOnlinePlayers.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> guildsWithOnlinePlayers.put(x.getKey(), x.getValue()));
        int pageNumber = 1;
        if (args.length == 2 && util.isNumber(args[1])) { pageNumber = Integer.parseInt(args[1]);}
        List<String> guildOnlineMembers = new ArrayList<>();
        guildsWithOnlinePlayers.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> guildOnlineMembers.add(util.primaryColor()
                + gUtil.getGuildName(x.getKey()) + util.tertiaryColor() + " > " + util.secondaryColor() + x.getValue() + "/" + gUtil.getAllGuildMemebers(x.getKey()).size()));
        StringBuilder sb = new StringBuilder();
        String listString = "";
        for (int i = ((pageNumber * 10) - 10); i <= pageNumber; i++) {
            if (guildOnlineMembers.size() == i) {break;}
            listString = sb.append(guildOnlineMembers.get(i)).append("\n").toString();
        }
        String header = util.primaryColor() + util.text("&m") + "--" + util.text("&r") + util.tertiaryColor() + "[ " + util.secondaryColor() + "Guild List" + util.tertiaryColor() + " ]" + util.primaryColor() + util.text("&m") + "--" + "\n";
        sender.sendMessage(header + listString);
    }
}
