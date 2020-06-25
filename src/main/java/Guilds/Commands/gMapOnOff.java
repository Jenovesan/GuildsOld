package Guilds.Commands;

import Main.Main;
import Util.util;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class gMapOnOff {
    gMap gMap = new gMap();
    public void main(CommandSender sender, String onOrOff) {
        Player player = (Player) sender;
        if (onOrOff.equalsIgnoreCase("on") && !playersWithGMapOn.containsKey(player)) {
            playersWithGMapOn.put(player, player.getLocation().getChunk());
            if (playersWithGMapOn.size() == 1) {sendPlayersGMap();}
            player.sendMessage(util.secondaryColor() + "You have set g map to " + util.primaryColor() + "on");
        } else if (onOrOff.equalsIgnoreCase("off") && playersWithGMapOn.containsKey(player)){
            playersWithGMapOn.remove(player);
            player.sendMessage(util.secondaryColor() + "You have set g map to " + util.primaryColor() + "off");
        }
    }

    HashMap<Player, Chunk> playersWithGMapOn = new HashMap<>();
    private void sendPlayersGMap() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (playersWithGMapOn.size() == 0) {this.cancel();}
                for (Map.Entry<Player, Chunk> element : playersWithGMapOn.entrySet()) {
                    Player player = element.getKey();
                    if (!player.getLocation().getChunk().equals(element.getValue())) {
                        gMap.sendMap(player, 15);
                        playersWithGMapOn.put(player, player.getLocation().getChunk());
                    }
                }
             }
        }.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);
    }
}
