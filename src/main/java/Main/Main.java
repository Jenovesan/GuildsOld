package Main;

import ConfigurationFiles.configManager;
import Guilds.Backend.nameTags;
import Guilds.Commands.commandsManager;
import Util.util;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;
    public commandsManager commandsManager = new commandsManager();
    public configManager configs = new configManager();
    private static Economy econ = null;

    private final nameTags test = new nameTags();
    @Override
    public void onEnable() {
        // Plugin startup logic
        Main.instance = this;
        hookIntoVault();
        startUps();
        commands();
        events();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Guilds Successfully Enabled");

    }

    public void events() {
        //Guilds
        getServer().getPluginManager().registerEvents(test, this);
    }

    public void commands() {
        //Guilds
        getCommand("g").setExecutor(commandsManager);
    }

    public void startUps() {
        //General
        configs.setup();
    }

    public static Main getInstance(){
        return Main.instance;
    }

    public void hookIntoVault() {
        if (!setupEconomy() ) {
            Bukkit.getConsoleSender().sendMessage(util.text("&cCould not find an economy"));
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
