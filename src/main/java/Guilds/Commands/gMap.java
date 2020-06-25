package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Main.Main;
import Util.util;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class gMap {
    public void main(CommandSender sender,String[] args) {
        Player player = (Player) sender;
        int mapSize = 15;
        if (args.length == 2 && util.isNumber(args[1])) {mapSize = Integer.parseInt(args[1]);}
        if (mapSize > configManager.getGuildsMainConfig().getInt("Max G Map Size")) {player.sendMessage(util.text("&cMax size is ") + configManager.getGuildsMainConfig().getInt("Max G Map Size")); return;}
        sendMap(player, mapSize);
    }

    public void sendMap(Player player, Integer mapSize) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Chunk playerChunk = player.getLocation().getChunk();
                String worldName = player.getWorld().getName();
                int halfSize = mapSize / 2;
                String map = "";
                HashMap<String, String> factionsList = new HashMap<>();
                StringBuilder sb = new StringBuilder();
                String[] colors = new String[] {util.text("&9"), util.text("&a"), util.text("&b"), util.text("&c"), util.text("&d"), util.text("&e"), util.text("&f"), util.text("&1"), util.text("&2"), util.text("&3"), util.text("&4"), util.text("&5"), util.text("&6"),
                        util.text("&0"), util.text("&8") };
                map = sb.append(northAndSouth(mapSize, "N")).toString();
                for (int z = -halfSize; z <= halfSize; z++) {
                    if (z == 0) {map = sb.append(util.text("&6")).append("W").toString();}
                    else {map = sb.append("  ").toString();}
                    for (int x = -halfSize; x <= halfSize; x++) {
                        int chunkX = playerChunk.getX() + x;
                        int chunkZ = playerChunk.getZ() + z;
                        if (x == 0 && z == 0) {map = sb.append(util.text("&a")).append("✦").toString(); continue;}
                        if (gUtil.claimIsWildernessForAsync(worldName, chunkX, chunkZ)) {map = sb.append(util.text("&7■")).toString(); continue;}
                        String guildAtLoc = gUtil.getGuildAtChunkForAsync(worldName, chunkX, chunkZ);
                        if (factionsList.containsKey(guildAtLoc)) {map = sb.append(factionsList.get(guildAtLoc)).append("■").toString(); continue;}
                        if (factionsList.size() < colors.length) {factionsList.put(guildAtLoc, colors[factionsList.size()]);}
                        else {factionsList.put(guildAtLoc, colors[factionsList.size() - (15 * (factionsList.size() / colors.length))]);}
                        map = sb.append(factionsList.get(guildAtLoc)).append("■").toString();
                    }
                    if (z == 0) {map = sb.append(util.text("&6")).append(" E").toString();}
                    map = sb.append("\n").toString();
                }
                map = sb.append(northAndSouth(mapSize, "S")).toString();
                player.sendMessage(map + factionsListMsg(factionsList));
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    private String factionsListMsg(HashMap<String, String> factionsList) {
        String msg = "";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> element : factionsList.entrySet()) {
            msg = sb.append(element.getValue()).append(gUtil.getGuildName(element.getKey())).append(util.text("&7")).append(", ").toString();
        }
        return msg;
    }

    private String northAndSouth(Integer mapSize, String letter) {
        String string = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mapSize / 1.6; i++) {
            if (i < 2) {string = sb.append(" ").toString(); continue;}
            if (i != (mapSize / 3) + 1) {string = sb.append(util.text("&7")).append("=-").toString();}
            else {string = sb.append(util.text("&6")).append(" ").append(letter).append(util.text("&7")).append(" -").toString();}
        }
            string = sb.append("=").append("\n").toString();
        return string;
    }
}
