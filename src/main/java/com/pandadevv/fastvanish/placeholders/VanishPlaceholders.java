package com.pandadevv.fastvanish.placeholders;

import com.pandadevv.fastvanish.FastVanish;
import com.pandadevv.fastvanish.managers.VanishManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VanishPlaceholders extends PlaceholderExpansion {
    
    private final FastVanish plugin;
    private final VanishManager vanishManager;
    
    public VanishPlaceholders(FastVanish plugin) {
        this.plugin = plugin;
        this.vanishManager = plugin.getVanishManager();
    }
    
    @Override
    public @NotNull String getIdentifier() {
        return "fastvanish";
    }
    
    @Override
    public @NotNull String getAuthor() {
        return "pandadevv";
    }
    
    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }
    
    @Override
    public boolean persist() {
        return true;
    }
    
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.equalsIgnoreCase("real_count")) {
            return String.valueOf(vanishManager.getRealPlayerCount());
        }
        
        if (params.equalsIgnoreCase("vanished")) {
            if (player == null) {
                return plugin.getConfig().getString("placeholders.not-vanished-text", "");
            }
            
            if (vanishManager.isVanished(player)) {
                return plugin.getConfig().getString("placeholders.vanished-text", "&7[Vanished]");
            } else {
                return plugin.getConfig().getString("placeholders.not-vanished-text", "");
            }
        }
        
        if (params.equalsIgnoreCase("vanished_boolean")) {
            if (player == null) {
                return "false";
            }
            return String.valueOf(vanishManager.isVanished(player));
        }
        
        return null;
    }
} 