package com.Incrymnia.MiningLeveler.Listeners;

import com.Incrymnia.MiningLeveler.Main;
import com.Incrymnia.MiningLeveler.PlayerData.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final Main plugin;
    public PlayerQuitListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerData playerData = PlayerData.getPlayerData(event.getPlayer());
        // Save player data if necessary
        saveData(playerData);
    }

    private void saveData(PlayerData playerData) {
        // Implement saving logic (e.g., to a file or database)
        plugin.saveDefaultConfig();
    }
}
