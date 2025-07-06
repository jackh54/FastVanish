package com.pandadevv.fastvanish.commands;

import com.pandadevv.fastvanish.FastVanish;
import com.pandadevv.fastvanish.managers.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VanishCommand implements CommandExecutor, TabCompleter {
    
    private final FastVanish plugin;
    private final VanishManager vanishManager;
    
    public VanishCommand(FastVanish plugin) {
        this.plugin = plugin;
        this.vanishManager = plugin.getVanishManager();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fastvanish.vanish")) {
            sender.sendMessage(plugin.getConfig().getString("messages.no-permission", "&cYou don't have permission to do that!"));
            return true;
        }
        
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("&cThis command can only be used by players!");
                return true;
            }
            
            Player player = (Player) sender;
            toggleVanish(player, player);
            return true;
        }
        
        if (args.length == 1) {
            if (!sender.hasPermission("fastvanish.vanish.others")) {
                sender.sendMessage(plugin.getConfig().getString("messages.no-permission", "&cYou don't have permission to do that!"));
                return true;
            }
            
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(plugin.getConfig().getString("messages.player-not-found", "&cPlayer not found!"));
                return true;
            }
            
            if (sender instanceof Player) {
                toggleVanish((Player) sender, target);
            } else {
                toggleVanish(null, target);
            }
            return true;
        }
        
        sender.sendMessage("&cUsage: /vanish [player]");
        return true;
    }
    
    private void toggleVanish(Player executor, Player target) {
        boolean isVanished = vanishManager.isVanished(target);
        
        if (isVanished) {
            vanishManager.unvanishPlayer(target);
            String message = plugin.getConfig().getString("messages.unvanished", "&cYou are no longer vanished!");
            target.sendMessage(message);
            
            if (executor != null && !executor.equals(target)) {
                String otherMessage = plugin.getConfig().getString("messages.unvanished-other", "&cYou have unvanished &e{player}&c!")
                        .replace("{player}", target.getName());
                executor.sendMessage(otherMessage);
            }
        } else {
            vanishManager.vanishPlayer(target);
            String message = plugin.getConfig().getString("messages.vanished", "&aYou are now vanished!");
            target.sendMessage(message);
            
            if (executor != null && !executor.equals(target)) {
                String otherMessage = plugin.getConfig().getString("messages.vanished-other", "&aYou have vanished &e{player}&a!")
                        .replace("{player}", target.getName());
                executor.sendMessage(otherMessage);
            }
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1 && sender.hasPermission("fastvanish.vanish.others")) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
} 