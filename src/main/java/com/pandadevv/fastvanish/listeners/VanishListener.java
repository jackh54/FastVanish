package com.pandadevv.fastvanish.listeners;

import com.pandadevv.fastvanish.FastVanish;
import com.pandadevv.fastvanish.managers.VanishManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;

public class VanishListener implements Listener {
    
    private final FastVanish plugin;
    private final VanishManager vanishManager;
    
    public VanishListener(FastVanish plugin) {
        this.plugin = plugin;
        this.vanishManager = plugin.getVanishManager();
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        vanishManager.handlePlayerJoin(player);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        vanishManager.handlePlayerQuit(player);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (vanishManager.isVanished(player) && !player.hasPermission("fastvanish.bypass")) {
            if (!plugin.getConfig().getBoolean("vanish.can-pickup-items", false)) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (vanishManager.isVanished(player) && !player.hasPermission("fastvanish.bypass")) {
            if (!plugin.getConfig().getBoolean("vanish.can-drop-items", false)) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (vanishManager.isVanished(player) && !player.hasPermission("fastvanish.bypass")) {
            if (!plugin.getConfig().getBoolean("vanish.can-interact", false)) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (vanishManager.isVanished(player) && !player.hasPermission("fastvanish.bypass")) {
            if (!plugin.getConfig().getBoolean("vanish.can-break-blocks", false)) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (vanishManager.isVanished(player) && !player.hasPermission("fastvanish.bypass")) {
            if (!plugin.getConfig().getBoolean("vanish.can-place-blocks", false)) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerUseItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (vanishManager.isVanished(player) && !player.hasPermission("fastvanish.bypass")) {
            if (!plugin.getConfig().getBoolean("vanish.can-use-items", false)) {
                if (event.getAction().name().contains("RIGHT_CLICK") && event.getItem() != null) {
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (vanishManager.isVanished(player) && !player.hasPermission("fastvanish.bypass")) {
                if (!plugin.getConfig().getBoolean("vanish.can-take-damage", false)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            if (vanishManager.isVanished(player) && !player.hasPermission("fastvanish.bypass")) {
                if (!plugin.getConfig().getBoolean("vanish.can-deal-damage", false)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player player) {
            if (vanishManager.isVanished(player) && !player.hasPermission("fastvanish.bypass")) {
                if (!plugin.getConfig().getBoolean("vanish.can-be-targeted", false)) {
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (vanishManager.isVanished(player) && !player.hasPermission("fastvanish.bypass")) {
                // Allow inventory interactions for vanished players
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onServerListPing(ServerListPingEvent event) {
        // Update player count to exclude vanished players
        int realCount = vanishManager.getRealPlayerCount();
        // Note: ServerListPingEvent doesn't have setNumPlayers in newer versions
        // This is handled by PlaceholderAPI integration instead
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (vanishManager.isVanished(player)) {
            // Allow movement for vanished players
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (vanishManager.isVanished(player) && !player.hasPermission("fastvanish.bypass")) {
            // Allow chat for vanished players by default
        }
    }
} 