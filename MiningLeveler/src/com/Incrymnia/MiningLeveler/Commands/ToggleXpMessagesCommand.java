package com.Incrymnia.MiningLeveler.Commands;

import com.Incrymnia.MiningLeveler.Main;
import com.Incrymnia.MiningLeveler.PlayerData.PlayerData;
import com.Incrymnia.MiningLeveler.Utils.ChatColour;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleXpMessagesCommand implements CommandExecutor {

    private final Main plugin;

    public ToggleXpMessagesCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColour.colorize("&cOnly players can toggle XP messages."));
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = PlayerData.getPlayerData(player);

        boolean currentState = playerData.isXpMessagesEnabled();
        playerData.setXpMessagesEnabled(!currentState);

        player.sendMessage(ChatColour.colorize("&aXP messages are now " + (playerData.isXpMessagesEnabled() ? "&eenabled" : "&cdisabled") + "&a."));
        return true;
    }
}
