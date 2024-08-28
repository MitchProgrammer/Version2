package com.Incrymnia.MiningLeveler.Commands;

import com.Incrymnia.MiningLeveler.Main;
import com.Incrymnia.MiningLeveler.PlayerData.PlayerData;
import com.Incrymnia.MiningLeveler.Utils.ChatColour;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetXpCommand implements CommandExecutor {

    private final Main plugin;

    public SetXpCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColour.colorize("&cUsage: /setxp <player> <xp>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColour.colorize("&cPlayer not found."));
            return true;
        }

        int xp;
        try {
            xp = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColour.colorize("&cInvalid XP number."));
            return true;
        }

        PlayerData playerData = PlayerData.getPlayerData(target);
        playerData.setXp(xp);
        sender.sendMessage(ChatColour.colorize("&aSet &e" + target.getName() + "&a's XP to &e" + xp));
        return true;
    }
}
