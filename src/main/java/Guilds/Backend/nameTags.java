package Guilds.Backend;

import ConfigurationFiles.configManager;
import Guilds.Events.GuildCreationEvent;
import Guilds.Events.GuildDisbandEvent;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class nameTags implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String myGuild = gUtil.getGuildIdFromPlayer(player);

        ScoreboardManager scoreboardManager = Bukkit.getServer().getScoreboardManager();
        Scoreboard myScoreboard = scoreboardManager.getNewScoreboard(); //if you already have a personal scoreboard set for every player, use that one instead.
        player.setScoreboard(myScoreboard);

        myScoreboard.registerNewObjective("prefixes", "dummy"); //deprecated in recent versions
        player.setScoreboard(myScoreboard);

        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            //set the prefixes of all online player on the currently joined player's scoreboard
            String otherGuild = gUtil.getGuildIdFromPlayer(onlinePlayer);
            String relationPrefix = getRelationPrefix(myGuild, otherGuild);

            Team myTeam = myScoreboard.getTeam(otherGuild);
            if (myTeam == null) {
                myTeam = myScoreboard.registerNewTeam(otherGuild);
            }

            myTeam.addEntry(onlinePlayer.getName());
            myTeam.setPrefix(relationPrefix);


            //set the prefix of the newly joined player in the online players' scoreboard
            Scoreboard otherScoreboard = onlinePlayer.getScoreboard();
            Team onlinePlayerTeam = otherScoreboard.getTeam(myGuild);
            if (onlinePlayerTeam == null) {
                onlinePlayerTeam = otherScoreboard.registerNewTeam(myGuild);
            }

            onlinePlayerTeam.addEntry(player.getName());
            onlinePlayerTeam.setPrefix(relationPrefix);
        }
    }

    public String getRelationPrefix(String guild1, String guild2) {
        if (guild1.equals("-1")) {return util.text("&f");}
        if (guild1.equals(guild2)) {return util.text("&a");}
        List<String> enemies = configManager.getGuildsDataConfig().getConfigurationSection(guild1).getStringList("Enemies");
        if (enemies.contains(guild2)) {return util.text("&c");}
        else {return util.text("&f");}
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player quitting = event.getPlayer();
        String guild = gUtil.getGuildIdFromPlayer(quitting);

        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            Team factionTeam = online.getScoreboard().getTeam(guild);
            if (factionTeam.hasEntry(quitting.getName())) {             factionTeam.removeEntry(quitting.getName());}
        }
    }

    @EventHandler
    public void guildCreate(GuildCreationEvent event) {
        Player player = event.getPlayer();
        if (player != null) {

            Scoreboard scoreboard = player.getScoreboard();
            String guild = event.getGuildId();

            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                String otherGuild = gUtil.getGuildIdFromPlayer(online);
                String relationPrefix = getRelationPrefix(guild, otherGuild);

                //for the online player; update the scoreboard, set the newly joined player in the correct team
                Team factionTeam = online.getScoreboard().getTeam(guild);
                if (factionTeam == null) {
                    factionTeam = online.getScoreboard().registerNewTeam(guild);
                    factionTeam.setPrefix(relationPrefix);
                }
                factionTeam.addEntry(player.getName());

                //for the newly joined player; update the scoreboard, set the online player in the correct team
                Team otherFactionTeam = scoreboard.getTeam(otherGuild);
                if (otherFactionTeam == null) {
                    otherFactionTeam = scoreboard.registerNewTeam(otherGuild);
                    otherFactionTeam.setPrefix(getRelationPrefix(guild, otherGuild));
                }
                otherFactionTeam.addEntry(online.getName());
            }
        }
    }

    @EventHandler
    public void guildDisband(GuildDisbandEvent event) {
        Player player = event.getPlayer();
        if (player != null) {
            Scoreboard scoreboard = player.getScoreboard();
            String guild = event.getGuildId();
            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                String otherGuild = gUtil.getGuildIdFromPlayer(online);
                String relationPrefix = getRelationPrefix(guild, otherGuild);

                //update the prefixes because the relation might have changed
                Team team = scoreboard.getTeam(otherGuild);
                team.setPrefix(relationPrefix);

                //for the online players; put the player in the team that belongs to the NO_FACTION
                Scoreboard otherScoreboard = online.getScoreboard();
                Team otherTeam = otherScoreboard.getTeam(guild);
                if (otherTeam != null) {
                    team.removeEntry(player.getName());
                }
                /*
                Team noFactionTeam = otherScoreboard.getTeam(FactionManager.NO_FACTION.getName());
                if (noFactionTeam != null) {
                    noFactionTeam = otherScoreboard.registerNewTeam(FactionManager.NO_FACTION.getName());
                    noFactionTeam.setPrefix(relationPrefix);
                }
                noFactionTeam.addEntry(player.getName());

                 */
            }
        }
    }
}
