package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Main.Main;
import Util.util;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class gUnclaimAmount {
    public void main(CommandSender sender, String sizeString) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Player player = (Player) sender;
                if (!util.isNumber(sizeString)) {player.sendMessage(util.text("&c" + sizeString + " is not a number")); return;}
                int size = Integer.parseInt(sizeString);
                if (size > configManager.getGuildsMainConfig().getInt("Max Unclaim Command Size")) {player.sendMessage(util.text("&cYou cannot unclaim an area of the size")); return;}
                String worldName = player.getWorld().getName();
                if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild in order to unclaim land")); return;}
                if (!gUtil.rankIsAdleast(player, gUtil.guildRank.valueOf(configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Claim and Unclaim"))))
                {player.sendMessage(util.text("&cYou must be at least " + configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Claim and Unclaim") + " to unclaim land")); return;}
                Integer amountOfChunksUnclaimed = unclaimChunks(player, size);
                gUtil.sendGuildMessage(gUtil.getGuildIdFromPlayer(player), util.primaryColor() + player.getName() + util.secondaryColor() + " has unclaimed " + util.primaryColor() + amountOfChunksUnclaimed + util.secondaryColor() + " chunks");
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    private Integer unclaimChunks(Player player, Integer size) {
        Chunk playerChunk = player.getLocation().getChunk();
        String guildId = gUtil.getGuildIdFromPlayer(player);
        String worldName = player.getWorld().getName();
        int halfSize = size / 2;
        int unclaimed = 0;
        List<String> chunksToBeUnclaimed = new ArrayList<>();
        for (int x = -halfSize; x < halfSize + 1; x++) {
            for (int z = -halfSize; z < halfSize + 1; z++) {
                int chunkX = playerChunk.getX() + x;
                int chunkZ = playerChunk.getZ() + z;
                if (!gUtil.isClaimedAsync(worldName, chunkX, chunkZ, guildId)) {continue;}
                chunksToBeUnclaimed.add(worldName + ":" + chunkX + ":" + chunkZ);
                unclaimed++;
            }
        }
        List<String> claims = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList("Claims");
        claims.removeAll(chunksToBeUnclaimed);
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("Claims", claims);
        configManager.saveGuildsDataConfig();
        return unclaimed;
    }
}
