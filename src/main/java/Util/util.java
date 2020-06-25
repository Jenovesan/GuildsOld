package Util;

import ConfigurationFiles.configManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class util {
    public static String text(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static Boolean isNumber(String string) {
        if (string == null) {
            return false;
        }
        try {
            Integer integer = Integer.parseInt(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String primaryColor() {
        return ChatColor.translateAlternateColorCodes('&', configManager.getGuildsMainConfig().getString("Primary Color"));
    }
    public static String secondaryColor() {
        return ChatColor.translateAlternateColorCodes('&', configManager.getGuildsMainConfig().getString("Secondary Color"));
    }
    public static String tertiaryColor() {
        return ChatColor.translateAlternateColorCodes('&', configManager.getGuildsMainConfig().getString("Tertiary Color"));
    }
    public static String seperator = util.text(configManager.getGuildsMainConfig().getString("Seperator"));

    public static Boolean isPlayer(String name) { return !(Bukkit.getPlayerExact(name) == null); }
    public static Boolean isOfflinePlayer(String name) { return !(Bukkit.getOfflinePlayer(name) == null); }

    public static String formatPrice(Integer price) {
        Format CommaFormat = NumberFormat.getNumberInstance(Locale.US);
        if (price == null) {
            return "0";
        }
        if (CommaFormat instanceof DecimalFormat) {
            DecimalFormat DecimalFormat = (DecimalFormat) CommaFormat;
            DecimalFormat.applyPattern("#");
            DecimalFormat.setGroupingUsed(true);
            DecimalFormat.setGroupingSize(3);
        }
        return CommaFormat.format(price);
    }
}
