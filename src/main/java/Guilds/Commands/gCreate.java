package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Util.util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class gCreate {
    public void main(CommandSender sender, String guildName) {
        Player player = (Player) sender;
        if (guildName.toCharArray().length > configManager.getGuildsMainConfig().getInt("Max Characters In Guild Name")) {player.sendMessage(util.text("&cYour guild name is too long")); return;}
        if (!guildNameExists(guildName)) {
            if (!gUtil.isInGuild(player)) {
                createGuild(player.getUniqueId(), guildName);
                player.sendMessage(util.secondaryColor() + "You have successfully created the guild " + util.primaryColor() + guildName);
            } else {
                player.sendMessage(util.text("&cYou cannot create a guild if you are already in one"));
            }
        } else {
            sender.sendMessage(util.text("&cThis guild name is already in use"));
        }
    }

    private Boolean guildNameExists(String name) { return getGuildNames().stream().anyMatch(name::equalsIgnoreCase);}

    private Set<String> getGuildNames() {
        Set<String> keys = configManager.getGuildsDataConfig().getKeys(false);
        Set<String> guildNames = new HashSet<>();
        for (String key : keys) {
             guildNames.add(configManager.getGuildsDataConfig().getConfigurationSection(key).getString("Name"));
        }
        return guildNames;
    }

    private List<Integer> getGuildIds() {
        return configManager.getGuildsDataConfig().getKeys(false).stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    public void createGuild(UUID uuid, String guildName) {
        String key = "0";
        if (!getGuildNames().isEmpty()) {key = String.valueOf(Collections.max(getGuildIds()) + 1);}
        configManager.getGuildsDataConfig().createSection(key); //Id
        configManager.getGuildsDataConfig().set(key + ".Name", guildName);
        configManager.getGuildsDataConfig().set(key + ".Description", "Default description"); //Description
        configManager.getGuildsDataConfig().set(key + ".DTR", 15); //DTR
        configManager.getGuildsDataConfig().set(key + ".Money", 0); //Money
        configManager.getGuildsDataConfig().set(key + ".Power", configManager.getGuildsMainConfig().getInt("Power Per Player")); //Power
        configManager.getGuildsDataConfig().set(key + ".Enemies", new ArrayList<>()); //Enemies
        configManager.getGuildsDataConfig().set(key + ".Claims", new ArrayList<>()); //Claims
        configManager.getGuildsDataConfig().set(key + ".Titles", new ArrayList<>()); //Titles
        configManager.getGuildsDataConfig().set(key + ".Leader", uuid.toString()); //leader
        configManager.getGuildsDataConfig().set(key + ".CoLeaders", new ArrayList<>()); //co-leaders
        configManager.getGuildsDataConfig().set(key + ".Mods", new ArrayList<>()); //mods
        configManager.getGuildsDataConfig().set(key + ".Members", new ArrayList<>()); //members
        configManager.getGuildsDataConfig().set(key + ".Recruits", new ArrayList<>()); //recruits
        configManager.getGuildsDataConfig().set(key + ".GuildHome", ""); //Guild Home
        configManager.getGuildsDataConfig().set(key + ".GuildHome.World", ""); //Guild Home
        configManager.getGuildsDataConfig().set(key + ".GuildHome.X", ""); //Guild Home X
        configManager.getGuildsDataConfig().set(key + ".GuildHome.Y", ""); //Guild Home Y
        configManager.getGuildsDataConfig().set(key + ".GuildHome.Z", ""); //Guild Home Z
        configManager.getGuildsDataConfig().set(key + ".GuildHome.Pitch", ""); //Guild Home Pitch
        configManager.getGuildsDataConfig().set(key + ".GuildHome.Yaw", ""); //Guild Home Yaw
        configManager.saveGuildsDataConfig();
    }
}
