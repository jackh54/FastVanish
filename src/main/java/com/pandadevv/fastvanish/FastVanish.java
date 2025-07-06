package com.pandadevv.fastvanish;

import com.pandadevv.fastvanish.commands.VanishCommand;
import com.pandadevv.fastvanish.commands.VanishListCommand;
import com.pandadevv.fastvanish.listeners.VanishListener;
import com.pandadevv.fastvanish.managers.VanishManager;
import com.pandadevv.fastvanish.placeholders.VanishPlaceholders;
import org.bukkit.plugin.java.JavaPlugin;

public class FastVanish extends JavaPlugin {
    
    private static FastVanish instance;
    private VanishManager vanishManager;
    private VanishPlaceholders vanishPlaceholders;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // Save default config
        saveDefaultConfig();
        
        // Initialize managers
        vanishManager = new VanishManager(this);
        
        // Register commands
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("vanishlist").setExecutor(new VanishListCommand(this));
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new VanishListener(this), this);
        
        // Register PlaceholderAPI expansion
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            vanishPlaceholders = new VanishPlaceholders(this);
            vanishPlaceholders.register();
            getLogger().info("PlaceholderAPI integration enabled!");
        } else {
            getLogger().warning("PlaceholderAPI not found! Placeholder integration disabled.");
        }
        
        getLogger().info("FastVanish has been enabled!");
    }
    
    @Override
    public void onDisable() {
        // Save vanish status if enabled
        if (getConfig().getBoolean("general.save-vanish-status", true)) {
            vanishManager.saveVanishStatus();
        }
        
        // Unregister PlaceholderAPI expansion
        if (vanishPlaceholders != null) {
            vanishPlaceholders.unregister();
        }
        
        getLogger().info("FastVanish has been disabled!");
    }
    
    public static FastVanish getInstance() {
        return instance;
    }
    
    public VanishManager getVanishManager() {
        return vanishManager;
    }
    
    public VanishPlaceholders getVanishPlaceholders() {
        return vanishPlaceholders;
    }
} 