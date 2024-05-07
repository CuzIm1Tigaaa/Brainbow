package de.cuzim1tigaaa.brainbow.listener;

import de.cuzim1tigaaa.brainbow.Brainbow;
import org.bukkit.*;
import org.bukkit.entity.Snowball;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
                    snow.get(index).getBlock().setType(Material.SNOW), i);
        }

        int taskId = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            Location randomLocation = snow.get(ThreadLocalRandom.current().nextInt(snow.size()));
            randomLocation.getWorld().spawn(randomLocation.clone().add(.5, 3, .5), Snowball.class);
        }, snow.size(), 3L).getTaskId();
        
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Bukkit.getScheduler().cancelTask(taskId);
            for(int i = 0; i < snow.size(); i++) {
                final int index = i;
                Bukkit.getScheduler().runTaskLater(plugin, () ->
                        snow.get(index).getBlock().setType(Material.AIR), i);
            }
        }, 150L);
    }

    private Set<Location> getLocations(Location location, int radius) {
        World world = location.getWorld();
        assert world != null;

        Set<Location> snowLocations = new HashSet<>();
        for(int x = -radius; x <= radius; x++)
            for(int z = -radius; z <= radius; z++)
                if(x * x + z * z <= radius * radius) {
                    Location block = new Location(world, location.getBlockX() + x, location.getBlockY(), location.getBlockZ() + z);
                    Location blockLocation = world.getHighestBlockAt(block).getLocation();
                    snowLocations.add(blockLocation.add(0, 1, 0));
                }
        return snowLocations;
    }

}