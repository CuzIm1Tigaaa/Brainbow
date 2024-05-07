package de.cuzim1tigaaa.brainbow.listener;

import de.cuzim1tigaaa.brainbow.Brainbow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.*;

public class Effects {

    private final Brainbow plugin;

    public Effects(Brainbow plugin) {
        this.plugin = plugin;
    }

    public void spawnSnow(Location location, int radius) {
        List<Location> snow = new ArrayList<>(getLocations(location, radius));
        Collections.shuffle(snow);
        
        for(int i = 0; i < snow.size(); i++) {
            final int index = i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> 
                    snow.get(index).getBlock().setType(Material.SNOW), i * 10L);
        }

        int taskId = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for(int i = 0; i < snow.size(); i++) {
                final int index = i;
                Bukkit.getScheduler().runTaskLater(plugin, () ->
                        snow.get(index).getBlock().setType(Material.AIR), i * 5L);
            }
        }, 5L, 7L).getTaskId();
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Bukkit.getScheduler().cancelTask(taskId);
            for(int i = 0; i < snow.size(); i++) {
                final int index = i;
                Bukkit.getScheduler().runTaskLater(plugin, () ->
                        snow.get(index).getBlock().setType(Material.SNOW), i * 5L);
            }
        }, 200L);
    }

    private Set<Location> getLocations(Location location, int radius) {
        World world = location.getWorld();
        assert world != null;

        Set<Location> snowLocations = new HashSet<>();
        for(int x = -radius; x <= radius; x++)
            for(int z = -radius; z <= radius; z++)
                if(x * x + z * z <= radius * radius) {
                    Location blockLocation = world.getHighestBlockAt(
                            location.getBlockX() + x, location.getBlockZ() + z).getLocation();
                    snowLocations.add(blockLocation);
                }
        return snowLocations;
    }

}
