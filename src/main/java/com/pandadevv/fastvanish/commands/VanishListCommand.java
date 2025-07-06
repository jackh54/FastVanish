package com.pandadevv.fastvanish.commands;

import com.pandadevv.fastvanish.FastVanish;
import com.pandadevv.fastvanish.managers.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class VanishListCommand implements CommandExecutor {
    
    private final FastVanish plugin;
    private final VanishManager vanishManager;
    
    public VanishListCommand(FastVanish plugin) {
        this.plugin = plugin;
        this.vanishManager = plugin.getVanishManager();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fastvanish.vanishlist")) {
            sender.sendMessage(plugin.getConfig().getString("messages.no-permission", "&cYou don't have permission to do that!"));
            return true;
        }
        
        List<UUID> vanishedPlayers = vanishManager.getVanishedPlayers().stream().toList();
        
        if (vanishedPlayers.isEmpty()) {
            sender.sendMessage(plugin.getConfig().getString("messages.no-vanished-players", "&aNo players are currently vanished."));
            return true;
        }
        
        List<String> vanishedNames = vanishedPlayers.stream()
                .map(uuid -> {
                    Player player = Bukkit.getPlayer(uuid);
                    return player != null ? player.getName() : "Unknown";
                })
                .collect(Collectors.toList());
        
        String playersList = String.join(", ", vanishedNames);
        String message = plugin.getConfig().getString("messages.vanish-list", "&aVanished players: &e{players}")
                .replace("{players}", playersList);
        
        sender.sendMessage(message);
        return true;
    }
} 