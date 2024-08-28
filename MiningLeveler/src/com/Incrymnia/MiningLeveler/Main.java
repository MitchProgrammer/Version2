package com.Incrymnia.MiningLeveler;

import com.Incrymnia.MiningLeveler.Commands.*;
import com.Incrymnia.MiningLeveler.Listeners.BlockBreakListener;
import com.Incrymnia.MiningLeveler.Listeners.PlayerChatListener;
import com.Incrymnia.MiningLeveler.Listeners.PlayerJoinListener;
import com.Incrymnia.MiningLeveler.Listeners.PlayerQuitListener;
import com.Incrymnia.MiningLeveler.Placeholder.MiningLevelingExpansion;
import com.Incrymnia.MiningLeveler.PlayerData.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        // Load config
        saveDefaultConfig();

        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);

        // Register commands
        getCommand("setlevel").setExecutor(new SetLevelCommand(this));
        getCommand("setxp").setExecutor(new SetXpCommand(this));
        getCommand("addxp").setExecutor(new AddXpCommand(this));
        getCommand("removexp").setExecutor(new RemoveXpCommand(this));
        getCommand("top").setExecutor(new TopCommand(this));
        getCommand("togglexpmessages").setExecutor(new ToggleXpMessagesCommand(this));
        getCommand("level").setExecutor(new LevelCommand(this));
        getCommand("nextlevel").setExecutor(new NextLevelCommand(this));
        getCommand("xp").setExecutor(new XpCommand(this));
        getCommand("totalxp").setExecutor(new TotalXpCommand(this));

        // Check if PlaceholderAPI is present
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("PlaceholderAPI found, registering expansion...");

            Bukkit.getScheduler().runTaskLater(this, () -> {
                try {
                    new MiningLevelingExpansion(this).register();
                    getLogger().info("MiningLevelingExpansion registered successfully.");
                } catch (Exception e) {
                    getLogger().severe("Failed to register MiningLevelingExpansion: " + e.getMessage());
                    e.printStackTrace();
                }
            }, 40L); // 2 seconds delay (40 ticks)
        } else {
            getLogger().warning("PlaceholderAPI not found. Placeholders will not be available.");
        }

        // Load configuration
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        PlayerData.saveAllPlayerData();
    }

    public static Main getInstance() {
        return instance;
    }
}
