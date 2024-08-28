package com.Incrymnia.MiningLeveler.Listeners;

import com.Incrymnia.MiningLeveler.Main;
import com.Incrymnia.MiningLeveler.PlayerData.PlayerData;
import com.Incrymnia.MiningLeveler.Utils.ChatColour;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerChatListener implements Listener {

    private final Main plugin;
    private LuckPerms luckPerms;

    private Map<String, String> levelColors;

    public PlayerChatListener(Main plugin) {
        this.plugin = plugin;

        this.luckPerms = Bukkit.getServicesManager().getRegistration(LuckPerms.class).getProvider();

        // Load color configuration when listener is created
        FileConfiguration config = plugin.getConfig();
        levelColors = new HashMap<>();
        for (String key : config.getConfigurationSection("levels.ranges").getKeys(false)) {
            String colorCode = config.getString("levels.ranges." + key);
            levelColors.put(key, colorCode);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        updatePlayerDisplayName(player);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        updateChatFormat(event, player);
    }

    private void updatePlayerDisplayName(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player);
        int level = playerData.getLevel();
        String colour = getColourForLevel(level);
        String displayName = ChatColour.colorize(colour + "&7[Mining Level " + level + "] &f" + player.getName());
        player.setDisplayName(displayName);
        player.setPlayerListName(displayName);
    }

    private void updateChatFormat(AsyncPlayerChatEvent event, Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player);
        int level = playerData.getLevel();
        String colour = getColourForLevel(level);
        // Use '%s' for the rank and message placeholders
        String format = ChatColour.colorize(colour + "[" + level + "] &7[&f%s&7] " + player.getName() + ": %s");
        // Pass the rank and message directly to event.setFormat
        event.setFormat(String.format(format, getPlayerRank(player), event.getMessage()));
    }

    private String getColourForLevel(int level) {
        for (Map.Entry<String, String> entry : levelColors.entrySet()) {
            String[] range = entry.getKey().split("-");
            int min = Integer.parseInt(range[0]);
            int max = Integer.parseInt(range[1]);
            if (level >= min && level <= max) {
                return entry.getValue();
            }
        }
        return "#FFFFFF"; // Default color if no range matches
    }

    private String getPlayerRank(Player player) {
        // Fetch the LuckPerms user object for the player
        UserManager userManager = luckPerms.getUserManager();
        User user = userManager.getUser(player.getUniqueId());

        if (user == null) {
            return "Member"; // Default value if user is not found
        }

        // Get the player's primary group
        String primaryGroup = user.getPrimaryGroup();
        return primaryGroup != null ? primaryGroup : "Member";
    }
}
