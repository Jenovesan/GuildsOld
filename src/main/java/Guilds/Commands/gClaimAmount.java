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

public class gClaimAmount {
    public void main(CommandSender sender, String sizeString) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Player player = (Player) sender;
                if (!util.isNumber(sizeString)) {player.sendMessage(util.text("&c" + sizeString + " is not a number")); return;}
                int size = Integer.parseInt(sizeString);
                if (size > configManager.getGuildsMainConfig().getInt("Max Claim Command Size")) {player.sendMessage(util.text("&cYou cannot claim an area of the size")); return;}
                String worldName = player.getWorld().getName();
                if (!gUtil.isInGuild(player)) {player.sendMessage(util.text("&cYou must be in a guild in order to claim land")); return;}
                if (!gUtil.rankIsAdleast(player, gUtil.guildRank.valueOf(configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Claim and Unclaim"))))
                {player.sendMessage(util.text("&cYou must be at least " + configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Claim and Unclaim") + " to claim land")); return;}
                if (configManager.getGuildsMainConfig().getBoolean("Can Only Claim In Main World") && !worldName.equals(configManager.getGuildsMainConfig().getString("Main World Name"))) {player.sendMessage(util.text("&cYou cannot claim in this world")); return;}
                Integer amountOfChunksClaimed = claimChunks(player, size);
                gUtil.sendGuildMessage(gUtil.getGuildIdFromPlayer(player), util.primaryColor() + player.getName() + util.secondaryColor() + " has claimed " + util.primaryColor() + amountOfChunksClaimed + util.secondaryColor() + " chunks");
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    private Integer claimChunks(Player player, Integer size) {
        Chunk playerChunk = player.getLocation().getChunk();
        String guildId = gUtil.getGuildIdFromPlayer(player);
        String worldName = player.getWorld().getName();
        int halfSize = size / 2;
        int claimed = 0;
        List<String> chunksToBeClaimed = new ArrayList<>();
        for (int x = -halfSize; x < halfSize + 1; x++) {
            for (int z = -halfSize; z < halfSize + 1; z++) {
                int chunkX = playerChunk.getX() + x;
                int chunkZ = playerChunk.getZ() + z;
                if (!gUtil.claimIsWildernessForAsync(worldName, chunkX, chunkZ)) {continue;}
                if (gUtil.getPower(guildId).equals(gUtil.getNumberOfClaims(guildId) + chunksToBeClaimed.size())) {player.sendMessage(util.text("&cYour guild did not have enough power to claim")); continue;}
                chunksToBeClaimed.add(worldName + ":" + chunkX + ":" + chunkZ);
                claimed++;
            }
        }
        List<String> claims = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList("Claims");
        claims.addAll(chunksToBeClaimed);
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("Claims", claims);
        configManager.saveGuildsDataConfig();
        return claimed;
    }
}
