package com.zubovdev.voidteleport.listeners;

import com.zubovdev.voidteleport.particles.Spiral;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerDamageListener implements Listener {
    private HashMap<String, Location> worlds = new HashMap<>();

    @EventHandler
    public void onPlayerDamage(EntityDamageByBlockEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            // Entity is not a Player
            return;
        }

        if (e.getCause() != EntityDamageEvent.DamageCause.VOID) {
            // Not a void damage
            return;
        }

        Player player = (Player) e.getEntity();

        World world = player.getWorld();
        Location spawnLocation = this.worlds.get(world.getName());
        if (spawnLocation == null) {
            // This world is not applicable
            return;
        }

        // TODO: Fast bugfix
        if (spawnLocation.getWorld() == null) {
            spawnLocation.setWorld(world);
        }

        // Canceling the event
        e.setCancelled(true);

        // Canceling the damage event
        player.setFallDistance(0);

        // Teleporting player to a spawn location
        player.teleport(spawnLocation);

        // Spawn spiral player particles.
        Spiral.spawn(player);
    }

    @SuppressWarnings("unchecked")
    public void updateWorlds(@Nullable ArrayList<HashMap<String, Object>> listWorlds) {
        if (listWorlds == null) {
            return;
        }

        // Cleaning the old one
        this.worlds = new HashMap<>();

        for (HashMap<String, Object> world: listWorlds) {
            String worldName = (String) world.get("name");
            if (Objects.equals(worldName, "")) {
                // TODO: Log this.
                continue;
            }

            Location spawnLocation = Location.deserialize((Map<String, Object>) world.get("spawnLocation"));
            spawnLocation.setWorld(Bukkit.getWorld(worldName));

            // Save world in a hash map
            this.worlds.put(worldName, spawnLocation);
        }
    }
}
