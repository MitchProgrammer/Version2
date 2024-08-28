package com.Incrymnia.MiningLeveler.Listeners;

import com.Incrymnia.MiningLeveler.Main;
import com.Incrymnia.MiningLeveler.PlayerData.PlayerData;
import com.Incrymnia.MiningLeveler.Utils.ChatColour;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.Map;

public class BlockBreakListener implements Listener {

    private final Main plugin;
    private final Map<Material, Integer> blockXpValues = new HashMap<>();

    // Constructor
    public BlockBreakListener(Main plugin) {
        this.plugin = plugin;
        loadXpValues();
    }

    private void loadXpValues() {
        // Load XP values from config
        for (String key : plugin.getConfig().getConfigurationSection("blockXpValues").getKeys(false)) {
            Material material = Material.matchMaterial(key);
            if (material != null) {
                int xp = plugin.getConfig().getInt("blockXpValues." + key);
                blockXpValues.put(material, xp);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();

        // Check if the block type is in the XP values map
        if (blockXpValues.containsKey(blockType)) {
            int xp = blockXpValues.get(blockType);
            PlayerData playerData = PlayerData.getPlayerData(player);

            // Add XP to the player's data
            playerData.addXp(xp);

            // Send XP notification if enabled
            if (playerData.isXpMessagesEnabled()) {
                String message = ChatColour.colorize("&aYou gained &e" + xp + " &aXP for breaking a " + blockType.toString().toLowerCase().replace("_", " ") + ".");
                player.sendMessage(message);
            }
        }
    }
}
