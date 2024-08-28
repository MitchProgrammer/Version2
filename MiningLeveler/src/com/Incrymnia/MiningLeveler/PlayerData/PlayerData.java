package com.Incrymnia.MiningLeveler.PlayerData;

import com.Incrymnia.MiningLeveler.Main;
import com.Incrymnia.MiningLeveler.Utils.ChatColour;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PlayerData {

    private static final ConcurrentMap<UUID, PlayerData> playerDataMap = new ConcurrentHashMap<>();

    private final Player player;
    private int level;
    private int xp;
    private boolean xpMessagesEnabled;

    private PlayerData(Player player) {
        this.player = player;
        this.level = 1; // Default level
        this.xp = 0; // Default XP
        this.xpMessagesEnabled = true; // Default XP message setting
    }

    public static PlayerData getPlayerData(Player player) {
        // Use computeIfAbsent to avoid recursive initialization
        return playerDataMap.computeIfAbsent(player.getUniqueId(), uuid -> new PlayerData(player));
    }

    public static Collection<PlayerData> getAllPlayerData() {
        return playerDataMap.values();
    }

    public void addXp(int amount) {
        this.xp += amount;
        updateLevel(); // Ensure XP is saved correctly
    }

    public void removeXp(int amount) {
        this.xp -= amount;
        if (this.xp < 0) this.xp = 0;
        // Handle level-down logic if needed
        updateLevel();
    }

    public void setLevel(int newLevel) {
        this.level = newLevel;
        updateLevel(); // Update XP based on new level if necessary
    }

    public void setXp(int newXp) {
        this.xp = newXp;
        updateLevel(); // Update level based on new XP
    }

    private void updateLevel() {
        int xpForNextLevel = getXpForNextLevel();
        while (this.xp >= xpForNextLevel) {
            this.xp -= xpForNextLevel;
            level++;
            xpForNextLevel = getXpForNextLevel(); // Recalculate for the new level

            // Send level-up message to the player
            String levelUpMessage = ChatColour.colorize("&aYou have leveled up to level &e" + level);
            player.sendMessage(levelUpMessage);
        }
    }

    private int getXpForCurrentLevel() {
        String formula = Main.getInstance().getConfig().getString("levelingFormula");
        double multiplier = Double.parseDouble(formula.replace("x", ""));
        return (int) (level * multiplier);
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public String getPlayerName() {
        return player.getName();
    }

    public boolean isXpMessagesEnabled() {
        return xpMessagesEnabled;
    }

    public static void saveAllPlayerData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("playerdata.dat"))) {
            for (PlayerData data : playerDataMap.values()) {
                out.writeObject(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int calculateXpForNextLevel() {
        String formula = Main.getInstance().getConfig().getString("levelingFormula");
        double multiplier = Double.parseDouble(formula.replace("x", ""));
        int currentLevelXp = calculateXpForLevel(level);
        return (int) Math.ceil(currentLevelXp * multiplier);
    }

    private int calculateXpForLevel(int level) {
        int initialXp = Main.getInstance().getConfig().getInt("initialXpForLevel1");
        String formula = Main.getInstance().getConfig().getString("levelingFormula");
        double multiplier = Double.parseDouble(formula.replace("x", ""));
        return (int) Math.ceil(initialXp * Math.pow(multiplier, level - 1));
    }

    public int getXpForNextLevel() {
        return calculateXpForNextLevel();
    }

    public void setXpMessagesEnabled(boolean enabled) {
        this.xpMessagesEnabled = enabled;
    }

    public int getTotalXp() {
        return this.xp; // Simplified example; could be a sum of all XP if stored differently
    }
}