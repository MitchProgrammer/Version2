package com.Incrymnia.MiningLeveler.Placeholder;

import com.Incrymnia.MiningLeveler.Main;
import com.Incrymnia.MiningLeveler.PlayerData.PlayerData;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class MiningLevelingExpansion extends PlaceholderExpansion {

    private final Main plugin;

    public MiningLevelingExpansion(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier() {
        return "miningleveler";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if (player == null) {
            return "";
        }

        PlayerData playerData = PlayerData.getPlayerData(player);

        switch (identifier.toLowerCase()) {
            case "level":
                return String.valueOf(playerData.getLevel());
            case "nextlevel":
                return String.valueOf(playerData.getLevel() + 1);
            case "xpthislevel":
                return String.valueOf(playerData.getXp());
            case "xpaminute":
                // Implement XP per minute calculation logic here
                return "0"; // Placeholder value
            default:
                return null;
        }
    }
}
