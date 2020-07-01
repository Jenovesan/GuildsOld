package Guilds.guildUtil;

import ConfigurationFiles.configManager;
import Guilds.storage.gStorage;
import Util.util;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.IntStream;

public class gUtil {
    public static Boolean isInGuild(OfflinePlayer player) { return !getGuildIdFromPlayer(player).equals("-1"); }

    public static String getGuildIdFromPlayer(OfflinePlayer player) { //returns an id | id = -1 if null
        String uuid = player.getUniqueId().toString();
        Set<String> keys = configManager.getGuildsDataConfig().getKeys(false);
        for (String key : keys) {
            if (configManager.getGuildsDataConfig().getConfigurationSection(key).getString("Leader").equals(uuid) || configManager.getGuildsDataConfig().getConfigurationSection(key).getStringList("CoLeaders").stream().anyMatch(uuid::equalsIgnoreCase) || configManager.getGuildsDataConfig().getConfigurationSection(key).getStringList("Mods").stream().anyMatch(uuid::equalsIgnoreCase) || configManager.getGuildsDataConfig().getConfigurationSection(key).getStringList("Members").stream().anyMatch(uuid::equalsIgnoreCase) || configManager.getGuildsDataConfig().getConfigurationSection(key).getStringList("Recruits").stream().anyMatch(uuid::equalsIgnoreCase)) {
                return key;
            }
        }
        return "-1";
    }

    public static String getGuildName(String id) {
        if (configManager.getGuildsDataConfig().getConfigurationSection(id) == null) {
            return null;
        }
        return configManager.getGuildsDataConfig().getConfigurationSection(id).getString("Name");
    }

    public static List<String> getAllGuildMemebers(String id) {
        List<String> members = new ArrayList<>();
        members.add(configManager.getGuildsDataConfig().getConfigurationSection(id).getString("Leader"));
        members.addAll(configManager.getGuildsDataConfig().getConfigurationSection(id).getStringList("CoLeaders"));
        members.addAll(configManager.getGuildsDataConfig().getConfigurationSection(id).getStringList("Mods"));
        members.addAll(configManager.getGuildsDataConfig().getConfigurationSection(id).getStringList("Members"));
        members.addAll(configManager.getGuildsDataConfig().getConfigurationSection(id).getStringList("Recruits"));
        return members;
    }

    public static List<Player> getOnlineGuildMembers(String guildId) {
        List<Player> onlineGuildMembers = new ArrayList<>();
        for (String uuid : getAllGuildMemebers(guildId)) {
            if (Bukkit.getPlayer(UUID.fromString(uuid)) == null) {continue;}
            onlineGuildMembers.add(Bukkit.getPlayer(UUID.fromString(uuid)));
        }
        return onlineGuildMembers;
    }

    public static Integer getNumberOfMembers(String guildId) { return getAllGuildMemebers(guildId).size(); }

    public enum guildRank { Recruit, Member, Mod, CoLeader, Leader}
    public static guildRank getGuildRank(OfflinePlayer player) {
        String uuid = player.getUniqueId().toString();
        String id = getGuildIdFromPlayer(player);
        if (configManager.getGuildsDataConfig().getConfigurationSection(id).getStringList("Recruits").contains(uuid)) { return guildRank.Recruit; }
        if (configManager.getGuildsDataConfig().getConfigurationSection(id).getStringList("Members").contains(uuid)) { return guildRank.Member; }
        if (configManager.getGuildsDataConfig().getConfigurationSection(id).getStringList("Mods").contains(uuid)) { return guildRank.Mod; }
        if (configManager.getGuildsDataConfig().getConfigurationSection(id).getStringList("CoLeaders").contains(uuid)) { return guildRank.CoLeader; }
        else { return guildRank.Leader; }
    }

    public static Boolean rankIsAdleast(OfflinePlayer player, guildRank rank) {
        guildRank[] rankings = new guildRank[] {guildRank.Recruit, guildRank.Member, guildRank.Mod, guildRank.CoLeader, guildRank.Leader};
        int rankingInt = IntStream.range(0, rankings.length).filter(i -> rank == rankings[i]).findFirst().orElse(-1);
        guildRank playerRank = getGuildRank(player);
        int playerRankingInt = IntStream.range(0, rankings.length).filter(i -> playerRank == rankings[i]).findFirst().orElse(-1);
        return playerRankingInt >= rankingInt;
    }

    public static Boolean rankIsHigher(OfflinePlayer higher, OfflinePlayer lower) {
        guildRank[] rankings = new guildRank[] {guildRank.Recruit, guildRank.Member, guildRank.Mod, guildRank.CoLeader, guildRank.Leader};
        guildRank higherRank = getGuildRank(higher);
        int higherRankInt = IntStream.range(0, rankings.length).filter(i -> higherRank == rankings[i]).findFirst().orElse(-1);
        guildRank lowerRank = getGuildRank(lower);
        int lowerRankInt = IntStream.range(0, rankings.length).filter(i -> lowerRank == rankings[i]).findFirst().orElse(-1);
        return higherRankInt > lowerRankInt;
    }

    public static Integer rankIsHigherByAmount(OfflinePlayer higher, OfflinePlayer lower) {
        guildRank[] rankings = new guildRank[] {guildRank.Recruit, guildRank.Member, guildRank.Mod, guildRank.CoLeader, guildRank.Leader};
        guildRank higherRank = getGuildRank(higher);
        int higherRankInt = IntStream.range(0, rankings.length).filter(i -> higherRank == rankings[i]).findFirst().orElse(-1);
        guildRank lowerRank = getGuildRank(lower);
        int lowerRankInt = IntStream.range(0, rankings.length).filter(i -> lowerRank == rankings[i]).findFirst().orElse(-1);
        return higherRankInt - lowerRankInt;
    }

    public static void sendGuildMessage(String guildId, String message) {
        for (String guildMemberUUID : getAllGuildMemebers(guildId)) {
            Player guildMember = Bukkit.getPlayer(UUID.fromString(guildMemberUUID));
            if (guildMember == null) {continue;}
            guildMember.sendMessage(message);
        }
    }

    public static void sendGuildAnnouncement(String guildId, String announcement) {
        for (String guildMemberUUID : getAllGuildMemebers(guildId)) {
            if (guildMemberUUID.equals("")) {continue;}
            Player guildMember = Bukkit.getPlayer(UUID.fromString(guildMemberUUID));
            if (guildMember == null) {continue;}
            guildMember.sendMessage(util.primaryColor() + "G Announcement" + util.tertiaryColor() + util.seperator + announcement);
            guildMember.playSound(guildMember.getLocation(), Sound.NOTE_PLING, 1, 1);
        }
    }

    public static String getGuildIdFromGuildName(String guildName) {
        for (String key : configManager.getGuildsDataConfig().getKeys(false)) {
            if (configManager.getGuildsDataConfig().getConfigurationSection(key).getString("Name").equalsIgnoreCase(guildName)) {return key;}
        }
        return null;
    }

    public static String getGuildFromString(String string) {
        if (util.isOfflinePlayer(string)) {return getGuildIdFromPlayer(Bukkit.getPlayerExact(string));}
        return getGuildIdFromGuildName(string);
    }

    public static Boolean hasGuildHome(String guildId) { return  !configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").getString("World").equals("");}

    public static Location getGuildHome(String guildId) {
        return new Location(
                Bukkit.getWorld(configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").getString("World")),
                Double.parseDouble(configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").getString("X")),
                Double.parseDouble(configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").getString("Y")),
                Double.parseDouble(configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").getString("Z")),
                Float.parseFloat(configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").getString("Yaw")),
                Float.parseFloat(configManager.getGuildsDataConfig().getConfigurationSection(guildId).getConfigurationSection("GuildHome").getString("Pitch")));
    }

    public static void changeRank(OfflinePlayer player, Integer amount) {
        String guildId = getGuildIdFromPlayer(player);
        guildRank rank = getGuildRank(player);
        guildRank[] rankings = new guildRank[] {guildRank.Recruit, guildRank.Member, guildRank.Mod, guildRank.CoLeader, guildRank.Leader};
        int rankingInt = IntStream.range(0, rankings.length).filter(i -> rank == rankings[i]).findFirst().orElse(-1);
        guildRank newRank = rankings[rankingInt + amount];
        List<String> currentRankList = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList(rank.toString() + "s");
        currentRankList.remove(player.getUniqueId().toString());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set(rank.toString() + "s", currentRankList);
        List<String> newRankList = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList(newRank.toString() + "s");
        newRankList.add(player.getUniqueId().toString());
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set(newRank + "s", newRankList);
        configManager.saveGuildsDataConfig();
    }

    public static Boolean isEnemied(String guild1Id, String guild2Id) { return configManager.getGuildsDataConfig().getConfigurationSection(guild1Id).getStringList("Enemies").contains(guild2Id);}

    public static String getTitle(OfflinePlayer player) {
        String guildId = getGuildIdFromPlayer(player);
        List<String> titles = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList("Titles");
        for (String title : titles) {
            if (title.contains(player.getUniqueId().toString())) {return title.split(":")[1];}
        }
        return null;
    }

    public static Integer getPower(String guildId) { return getNumberOfMembers(guildId) * configManager.getGuildsMainConfig().getInt("Power Per Player");}

    public static Integer getNumberOfClaims(String guildId) {return configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList("Claims").size();}

    public static Boolean claimIsWilderness(Chunk chunk) {
        for (String key : configManager.getGuildsDataConfig().getKeys(false)) {
            List<String> claims = configManager.getGuildsDataConfig().getConfigurationSection(key).getStringList("Claims");
            for (String claim : claims) {
                if (claim.equals(chunk.getWorld().getName() + ":" + chunk.getX() + ":" + chunk.getZ())) { return false; }
            }
        }
        return true;
    }

    public static Boolean claimIsWildernessForAsync(String worldName, Integer chunkX, Integer chunkZ) {
        for (String key : configManager.getGuildsDataConfig().getKeys(false)) {
            List<String> claims = configManager.getGuildsDataConfig().getConfigurationSection(key).getStringList("Claims");
            for (String claim : claims) {
                if (claim.equals(worldName + ":" + chunkX + ":" + chunkZ)) { return false; }
            }
        }
        return true;
    }

    public static Boolean isInClaim(Location location, String guildId) {
        Chunk playerChunk = location.getChunk();
        List<String> claims = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList("Claims");
        for (String claim : claims) {
            String world = claim.split(":")[0];
            Chunk thisChunk = Bukkit.getWorld(world).getChunkAt(Integer.parseInt(claim.split(":")[1]), Integer.parseInt(claim.split(":")[2]));
            if (playerChunk.equals(thisChunk)) {return true;}
        }
        return false;
    }

    public static Boolean isClaimedAsync(String worldName, Integer chunkX, Integer chunkZ, String guildId) {
        List<String> claims = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList("Claims");
        for (String claim : claims) {
            if (claim.equals(worldName + ":" + chunkX + ":" + chunkZ)) {return true;}
        }
        return false;
    }

    public static String getGuildAtChunk(Chunk chunk) {
        for (String key : configManager.getGuildsDataConfig().getKeys(false)) {
            List<String> claims = configManager.getGuildsDataConfig().getConfigurationSection(key).getStringList("Claims");
            for (String claim : claims) {
                if (claim.equals(chunk.getWorld().getName() + ":" + chunk.getX() + ":" + chunk.getZ())) { return key; }
            }
        }
        return null;
    }

    public static String getGuildAtChunkForAsync(String worldName, Integer chunkX, Integer chunkZ) {
        for (String key : configManager.getGuildsDataConfig().getKeys(false)) {
            List<String> claims = configManager.getGuildsDataConfig().getConfigurationSection(key).getStringList("Claims");
            for (String claim : claims) {
                if (claim.equals(worldName + ":" + chunkX + ":" + chunkZ)) { return key; }
            }
        }
        return null;
    }

    public enum guildRelation{Nuetral, Same, Enemy}

    public static guildRelation getGuildRelation(String guild1, String guild2) {
        if (guild1.equals(guild2)) {return guildRelation.Same;}
        try {List<String> enemies = configManager.getGuildsDataConfig().getConfigurationSection(guild1).getStringList("Enemies"); if (enemies.contains(guild2)) {return guildRelation.Enemy;}}
        catch (Exception ignored) {}
        return guildRelation.Nuetral;
    }

    public static Integer getDTR(String guildId) { return configManager.getGuildsDataConfig().getConfigurationSection(guildId).getInt("DTR");}

    public static Set<Chunk> getClaims(String guildId) {
        Set<Chunk> chunks = new HashSet<>();
        List<String> claims = configManager.getGuildsDataConfig().getConfigurationSection(guildId).getStringList("Claims");
        for (String claim : claims) { chunks.add(Bukkit.getWorld(claim.split(":")[0]).getChunkAt(Integer.parseInt(claim.split(":")[1]), Integer.parseInt(claim.split(":")[2]))); }
        return chunks;
    }

    public static double getValue(String guildId) { return configManager.getGuildsDataConfig().getConfigurationSection(guildId).getDouble("value"); }

    public static Boolean guildIsEmpty(String guildId) { return configManager.getGuildsDataConfig().getConfigurationSection(guildId).getBoolean("Empty"); }

    public static Boolean isInGuildAdminMode(Player player) { return gStorage.getGAdmins().contains(player);}

    public static OfflinePlayer getGuildLeader(String guildId) {return Bukkit.getOfflinePlayer(UUID.fromString(configManager.getGuildsDataConfig().getConfigurationSection(guildId).getString("Leader")));}
}
