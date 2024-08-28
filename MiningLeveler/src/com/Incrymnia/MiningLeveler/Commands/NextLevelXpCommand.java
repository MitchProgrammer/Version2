package com.Incrymnia.MiningLeveler.Commands;

import com.Incrymnia.MiningLeveler.Main;
import com.Incrymnia.MiningLeveler.PlayerData.PlayerData;
import com.Incrymnia.MiningLeveler.Utils.ChatColour;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NextLevelXpCommand implements CommandExecutor {

    private final Main plugin;

    public NextLevelXpCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColour.colorize("&cOnly players can check the XP needed for the next level."));
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = PlayerData.getPlayerData(player);

        int xpForNextLevel = playerData.calculateXpForNextLevel() - playerData.getXp();
        player.sendMessage(ChatColour.colorize("&aYou need &e" + xpForNextLevel + " &aXP to reach the next level."));
        return true;
    }
}
