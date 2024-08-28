package com.Incrymnia.MiningLeveler.Commands;

import com.Incrymnia.MiningLeveler.Main;
import com.Incrymnia.MiningLeveler.PlayerData.PlayerData;
import com.Incrymnia.MiningLeveler.Utils.ChatColour;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelCommand implements CommandExecutor {

    private final Main plugin;

    public LevelCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColour.colorize("&cOnly players can check their level."));
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = PlayerData.getPlayerData(player);

        player.sendMessage(ChatColour.colorize("&aYour current level is &e" + playerData.getLevel() + "&a."));
        return true;
    }
}
