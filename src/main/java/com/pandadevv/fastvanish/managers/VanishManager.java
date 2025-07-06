package com.pandadevv.fastvanish.managers;

import com.pandadevv.fastvanish.FastVanish;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VanishManager {
    
    private final FastVanish plugin;
    private final Set<UUID> vanishedPlayers;
    private final File dataFile;
    private final FileConfiguration dataConfig;
    
    public VanishManager(FastVanish plugin) {
        this.plugin = plugin;
        this.vanishedPlayers = ConcurrentHashMap.newKeySet();
        this.dataFile = new File(plugin.getDataFolder(), "data.yml");
        this.dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        
        loadVanishStatus();
    }
    
    public boolean isVanished(Player player) {
        return vanishedPlayers.contains(player.getUniqueId());
    }
    
    public boolean isVanished(UUID uuid) {
        return vanishedPlayers.contains(uuid);
    }
    
    public void vanishPlayer(Player player) {
        vanishedPlayers.add(player.getUniqueId());
        updatePlayerVisibility(player, false);
        updatePlayerAbilities(player);
    }
    
    public void unvanishPlayer(Player player) {
        vanishedPlayers.remove(player.getUniqueId());
        updatePlayerVisibility(player, true);
        updatePlayerAbilities(player);
    }
    
    public Set<UUID> getVanishedPlayers() {
        return new HashSet<>(vanishedPlayers);
    }
    
    public int getRealPlayerCount() {
        return Bukkit.getOnlinePlayers().size() - vanishedPlayers.size();
    }
    
    private void updatePlayerVisibility(Player player, boolean visible) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.equals(player)) continue;
            
            if (visible) {
                onlinePlayer.showPlayer(plugin, player);
            } else {
                onlinePlayer.hidePlayer(plugin, player);
            }
        }
    }
    
    private void updatePlayerAbilities(Player player) {
        boolean vanished = isVanished(player);
        
        // Update player abilities based on vanish status
        if (vanished) {
            player.setAllowFlight(true);
            player.setFlying(true);
        } else {
            if (!player.hasPermission("fastvanish.keep-flight")) {
                player.setAllowFlight(false);
                player.setFlying(false);
            }
        }
    }
    
    public void loadVanishStatus() {
        if (!dataFile.exists()) {
            return;
        }
        
        if (plugin.getConfig().getBoolean("general.save-vanish-status", true)) {
            for (String uuidString : dataConfig.getStringList("vanished-players")) {
                try {
                    UUID uuid = UUID.fromString(uuidString);
                    vanishedPlayers.add(uuid);
                    
                    Player player = Bukkit.getPlayer(uuid);
                    if (player != null && player.isOnline()) {
                        updatePlayerVisibility(player, false);
                        updatePlayerAbilities(player);
                    }
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Invalid UUID in data.yml: " + uuidString);
                }
            }
        }
    }
    
    public void saveVanishStatus() {
        if (!plugin.getConfig().getBoolean("general.save-vanish-status", true)) {
            return;
        }
        
        dataConfig.set("vanished-players", vanishedPlayers.stream()
                .map(UUID::toString)
                .toList());
        
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save vanish status: " + e.getMessage());
        }
    }
    
    public void handlePlayerJoin(Player player) {
        if (isVanished(player)) {
            updatePlayerVisibility(player, false);
            updatePlayerAbilities(player);
        }
    }
    
    public void handlePlayerQuit(Player player) {
        // Player data is already saved in the Set, no additional action needed
    }
} 