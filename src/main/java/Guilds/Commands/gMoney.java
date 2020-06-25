package Guilds.Commands;

import ConfigurationFiles.configManager;
import Guilds.guildUtil.gUtil;
import Main.Main;
import Util.util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gMoney {
    gHelp gHelp = new gHelp();
    public void main(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (!gUtil.isInGuild(player)) { player.sendMessage(util.text("&cYou must be in a guild to access your faction's bank")); return;}
        String guildId = gUtil.getGuildIdFromPlayer(player);
        if (args.length == 2 && args[1].equalsIgnoreCase("b")) { player.sendMessage(util.primaryColor() + "Guild's Balance" + util.tertiaryColor() + util.seperator + util.secondaryColor() + "$" + getGuildMoney(guildId)); return;}
        if (args.length == 3 && args[1].equalsIgnoreCase("d") && util.isNumber(args[2])) {depositMoney(player, Double.parseDouble(args[2])); return;}
        if (args.length == 3 && args[1].equalsIgnoreCase("w") && util.isNumber(args[2])) {withdrawMoney(player, Double.parseDouble(args[2])); return;}
        gHelp.main(sender, args);
    }

    public Double getGuildMoney(String guildId) { return configManager.getGuildsDataConfig().getConfigurationSection(guildId).getDouble("Money"); }

    private void depositMoney(Player player, Double amount) {
        double playerBalance = Main.getEconomy().getBalance(player);
        if (playerBalance < amount) {player.sendMessage(util.text("&cYou cannot afford to deposit this much")); return;}
        String guildId = gUtil.getGuildIdFromPlayer(player);
        Main.getEconomy().withdrawPlayer(player, amount);
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("Money", getGuildMoney(guildId) + amount);
        configManager.saveGuildsDataConfig();
        gUtil.sendGuildMessage(guildId, util.primaryColor() + player.getName() + util.secondaryColor() + " has deposited " + util.primaryColor() + "$" + util.formatPrice((int) Math.round(amount)) + util.secondaryColor() + " to your guild's bank");
    }

    private void withdrawMoney(Player player, Double amount) {
       if (!gUtil.rankIsAdleast(player, gUtil.guildRank.valueOf(configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Withdraw From Guild Bank"))))
        {player.sendMessage(util.text("&cYou must be at least " + configManager.getGuildsMainConfig().getString("Guild Rank To Be Able To Withdraw From Guild Bank") + " to withdraw from your guild's bank")); return;}
        String guildId = gUtil.getGuildIdFromPlayer(player);
       if (getGuildMoney(guildId) < amount) {player.sendMessage(util.text("&cYour guild cannot afford to withdraw this much")); return;}
        configManager.getGuildsDataConfig().getConfigurationSection(guildId).set("Money", getGuildMoney(guildId) - amount);
        configManager.saveGuildsDataConfig();
        Main.getEconomy().depositPlayer(player, amount);
        gUtil.sendGuildAnnouncement(guildId, util.primaryColor() + player.getName() + util.secondaryColor() + " has withdrawn " + util.primaryColor() + "$" + util.formatPrice((int) Math.round(amount)) + util.secondaryColor() + " from your guild's bank");
    }
}
