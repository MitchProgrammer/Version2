package com.Incrymnia.MiningLeveler.Utils;

import org.bukkit.ChatColor;

public class ChatColour {

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
