package com.zubovdev.voidteleport;

import com.zubovdev.voidteleport.configs.Config;
import com.zubovdev.voidteleport.listeners.PlayerDamageListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public final class VoidTeleport extends JavaPlugin {
    public static final String PluginName = "VoidTeleport";

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Plugin enabled!");

        // Setup config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Initialize config.
        Config.init();
        Config.save();

        this.registerDamageEvent();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().log(Level.INFO, "Plugin disabled!");
    }

    @SuppressWarnings("unchecked")
    private void registerDamageEvent() {
        PlayerDamageListener damageListener = new PlayerDamageListener();
        damageListener.updateWorlds((ArrayList<HashMap<String, Object>>) Config.get().get("worlds"));

        getServer().getPluginManager().registerEvents(damageListener, this);
    }
}
