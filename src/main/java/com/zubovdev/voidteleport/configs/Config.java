package com.zubovdev.voidteleport.configs;

import com.zubovdev.voidteleport.VoidTeleport;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.Level;

public class Config {
    private static File file;
    private static FileConfiguration config;

    private static final String fileNameConfig = "config.yml";

    /**
     * Initializes the static Config class.
     */
    public static void init() {
        // Getting the VoidTeleport plugin.
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(VoidTeleport.PluginName);
        if (plugin == null) {
            // Something goes wrong, log it.
            Bukkit.getLogger().log(
                    Level.WARNING,
                    MessageFormat.format("Cannot get plugin {0}", VoidTeleport.PluginName)
            );
            return;
        }

        file = new File(plugin.getDataFolder(), fileNameConfig);

        // We don't know, is file actually exists.
        try {
            if (file.createNewFile()) {
                plugin.getLogger().log(
                        Level.INFO,
                        MessageFormat.format("New config file with name {0} was created", fileNameConfig)
                );
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, e.toString());
            return;
        }

        // config is empty, so just reload it.
        reload();
    }

    /**
     * Getter
     * @return FileConfiguration
     */
    public static FileConfiguration get() {
        return config;
    }

    /**
     * Saves configuration to a file.
     */
    public static void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, e.toString());
        }
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
