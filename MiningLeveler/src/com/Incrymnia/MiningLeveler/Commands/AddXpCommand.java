package com.Incrymnia.MiningLeveler.Commands;

import com.Incrymnia.MiningLeveler.Main;
import com.Incrymnia.MiningLeveler.PlayerData.PlayerData;
import com.Incrymnia.MiningLeveler.Utils.ChatColour;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddXpCommand implements CommandExecutor {

    private final Main plugin;

    public AddXpCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColour.colorize("&cUsage: /addxp <player> <amount>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColour.colorize("&cPlayer not found."));
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColour.colorize("&cInvalid amount."));
            return true;
        }

        PlayerData playerData = PlayerData.getPlayerData(target);
        playerData.addXp(amount);

        sender.sendMessage(ChatColour.colorize("&aAdded &e" + amount + " &aXP to &e" + target.getName() + "&a."));
        target.sendMessage(ChatColour.colorize("&aYou have received &e" + amount + " &aXP."));
        return true;
    }
}
