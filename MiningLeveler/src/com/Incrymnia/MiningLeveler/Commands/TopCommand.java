package com.Incrymnia.MiningLeveler.Commands;

import com.Incrymnia.MiningLeveler.Main;
import com.Incrymnia.MiningLeveler.PlayerData.PlayerData;
import com.Incrymnia.MiningLeveler.Utils.ChatColour;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TopCommand implements CommandExecutor {

    private final Main plugin;

    public TopCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<PlayerData> topPlayers = PlayerData.getAllPlayerData().stream()
                .sorted(Comparator.comparingInt(PlayerData::getLevel).reversed())
                .limit(10)
                .collect(Collectors.toList());

        sender.sendMessage(ChatColour.colorize("&aTop 10 Players:"));
        for (int i = 0; i < topPlayers.size(); i++) {
            PlayerData data = topPlayers.get(i);
            sender.sendMessage(ChatColour.colorize("&e#" + (i + 1) + " &a" + data.getPlayerName() + " - Level " + data.getLevel()));
        }
        return true;
    }
}
