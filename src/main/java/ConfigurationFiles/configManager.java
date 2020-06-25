package ConfigurationFiles;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class configManager {

    public static FileConfiguration guildsMainConfig;
    public static File fileGuildsMain;

    public static FileConfiguration guildsDataConfig;
    public static File fileGuildsData;

    public void setup() {
        //GuildsMain
       fileGuildsMain = new File(Bukkit.getServer().getPluginManager().getPlugin("Guilds").getDataFolder(), "GuildsMainConfig.yml");
       if (!fileGuildsMain.exists()) {
           try {
               fileGuildsMain.createNewFile();
           }catch (IOException e) {
               Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error when creating new GuildsMainConfig file");
           }
       }
        guildsMainConfig = YamlConfiguration.loadConfiguration(fileGuildsMain);
       loadGuildsMainDefaults();

       //GuildsData
        fileGuildsData = new File(Bukkit.getServer().getPluginManager().getPlugin("Guilds").getDataFolder(), "GuildsDataConfig.yml");
        if (!fileGuildsData.exists()) {
            try {
                fileGuildsData.createNewFile();
            }catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error when creating new GuildsDataConfig file");
            }
        }
        guildsDataConfig = YamlConfiguration.loadConfiguration(fileGuildsData);
    }

    //Guilds Main
    public static FileConfiguration getGuildsMainConfig() {
        return guildsMainConfig;
    }

    public static void saveGuildsMainConfig() {
        try {
            guildsMainConfig.save(fileGuildsMain);
        }catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Error when saving GuildsMain configuration file");
        }
    }

    public static void reloadGuildsMainConfig() {
        guildsMainConfig = YamlConfiguration.loadConfiguration(fileGuildsMain);
    }

    private void loadGuildsMainDefaults() {
        getGuildsMainConfig().addDefault("Main World Name", "world");
        getGuildsMainConfig().addDefault("Primary Color", "&2");
        getGuildsMainConfig().addDefault("Secondary Color", "&a");
        getGuildsMainConfig().addDefault("Tertiary Color", "&6");
        getGuildsMainConfig().addDefault("Seperator", " &6> ");
        getGuildsMainConfig().addDefault("Power Per Player", 2);
        getGuildsMainConfig().addDefault("Seconds Before Guild Invites Expire", "60");
        getGuildsMainConfig().addDefault("Guild Rank To Be Able To Set Guild Home", "Mod");
        getGuildsMainConfig().addDefault("Guild Rank To Be Able To Set Guild Name", "CoLeader");
        getGuildsMainConfig().addDefault("Guild Rank To Be Able To Withdraw From Guild Bank", "CoLeader");
        getGuildsMainConfig().addDefault("Guild Rank To Be Able To Enemy and Truce Guilds", "Mod");
        getGuildsMainConfig().addDefault("Guild Rank To Be Able To Claim and Unclaim", "CoLeader");
        getGuildsMainConfig().addDefault("Leader Symbol", "***");
        getGuildsMainConfig().addDefault("CoLeader Symbol", "**");
        getGuildsMainConfig().addDefault("Mod Symbol", "*");
        getGuildsMainConfig().addDefault("Member Symbol", "+");
        getGuildsMainConfig().addDefault("Recruit Symbol", "-");
        getGuildsMainConfig().addDefault("Can Only Claim In Main World", true);
        getGuildsMainConfig().addDefault("Max Claim Command Size", 5);
        getGuildsMainConfig().addDefault("Max Unclaim Command Size", 25);
        getGuildsMainConfig().addDefault("Max G Map Size", 35);
        getGuildsMainConfig().addDefault("Max Characters In Guild Name", 15);
        getGuildsMainConfig().addDefault("Max Players In A Guild", 10);
        getGuildsMainConfig().options().copyDefaults(true);
        saveGuildsMainConfig();
    }
    
    //GuildsData
    public static FileConfiguration getGuildsDataConfig() {
        return guildsDataConfig;
    }

    public static void saveGuildsDataConfig() {
        try {
            guildsDataConfig.save(fileGuildsData);
        }catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Error when saving GuildsData configuration file");
        }
    }

    public static void reloadGuildsDataConfig() {
        guildsDataConfig = YamlConfiguration.loadConfiguration(fileGuildsData);
    }
}
