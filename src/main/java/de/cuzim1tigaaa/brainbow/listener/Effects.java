package de.cuzim1tigaaa.brainbow.listener;

import de.cuzim1tigaaa.brainbow.Brainbow;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

            for(Player player : Bukkit.getOnlinePlayers()) {
                Location toCheck = player.getLocation().clone();

                if(equals(toCheck, snow.get(index))) {
                    if(player.hasPotionEffect(PotionEffectType.SLOW))
                        continue;

                    player.setFreezeTicks(1000);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, PotionEffect.INFINITE_DURATION, 9, false, false, false));
                }
            }
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

                for(Player player : Bukkit.getOnlinePlayers()) {
                    Location toCheck = player.getLocation().clone();

                    if(equals(toCheck, snow.get(index))) {
                        player.setFreezeTicks(0);
                        player.removePotionEffect(PotionEffectType.SLOW);
                    }
                }
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
                    Location blockLocation = plugin.getHighestBlockAt(block.clone());
                    snowLocations.add(blockLocation);
                }
        return snowLocations;
    }

    public boolean equals(Location l1, Location l2) {
        return l1.getBlockX() == l2.getBlockX() && l1.getBlockZ() == l2.getBlockZ();
    }
}