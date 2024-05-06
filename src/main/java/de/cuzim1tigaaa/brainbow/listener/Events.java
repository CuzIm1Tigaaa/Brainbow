package de.cuzim1tigaaa.brainbow.listener;

import de.cuzim1tigaaa.brainbow.Brainbow;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Events implements Listener {

	private final Brainbow plugin;
	private final EnumSet<Material> blocks = EnumSet.of(
			Material.REDSTONE_BLOCK,
			Material.COAL_BLOCK,
			Material.LAPIS_BLOCK,
			Material.WAXED_COPPER_BLOCK,
			Material.EMERALD_BLOCK,
			Material.DIAMOND_BLOCK,
			Material.GOLD_BLOCK,
			Material.SNOW_BLOCK);

	private final int radius = 3;

	public Events(Brainbow plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void chat(AsyncPlayerChatEvent event) {

	}

	@EventHandler
	public void move(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		if(block.getType() == Material.SNOW || block.getRelative(BlockFace.UP).getType() == Material.SNOW)
			event.setCancelled(true);

		if(!blocks.contains(block.getType())) {
			player.removePotionEffect(PotionEffectType.SLOW);
			return;
		}

		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 4, false, false, false));
	}

	@EventHandler
	public void spawnCreatures(CreatureSpawnEvent event) {
		switch(event.getSpawnReason()) {
			case ENDER_PEARL, EGG -> event.setCancelled(true);
		}
	}

	@EventHandler
	public void projectileLand(ProjectileHitEvent event) {
		Projectile entity = event.getEntity();
		if(entity.getMetadata("bow").isEmpty() || !entity.getMetadata("bow").get(0).asBoolean())
			return;
		if(!(entity.getShooter() instanceof Player player))
			return;

		switch(entity.getType()) {
			case TRIDENT -> {
				entity.remove();
				LightningStrike strike = entity.getWorld().spawn(entity.getLocation(), LightningStrike.class);
				strike.setCausingPlayer(player);
				strike.setFlashes(1);
			}
			case SNOWBALL -> {
				Location location;
				if(event.getHitBlock() != null) location = event.getHitBlock().getLocation();
				else if(event.getHitEntity() != null) location = event.getHitEntity().getLocation();
				else location = entity.getLocation();

				if(location.getWorld() == null)
					return;

				World world = location.getWorld();
				Set<Location> snowLocations = new HashSet<>();
				for(int x = -radius; x <= radius; x++)
					for(int z = -radius; z <= radius; z++)
						if(x * x + z * z <= radius * radius) {
							Location blockLocation = world.getHighestBlockAt(location.getBlockX() + x,
									location.getBlockZ() + z).getLocation();
							world.getBlockAt(blockLocation.add(0, 1, 0)).setType(Material.SNOW);
							snowLocations.add(blockLocation);
						}

				Bukkit.getScheduler().runTaskLater(plugin, () -> {
					for(Location snowLocation : snowLocations)
						snowLocation.getBlock().setType(Material.AIR);
				}, 200L);
			}
			case EGG -> {
				Entity target;
				if((target = event.getHitEntity()) == null)
					return;

				Location targetLoc = target.getLocation();
				Location playerLoc = player.getLocation();

				target.teleport(playerLoc);
				//				target.playSound(playerLoc, Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 1F);
				player.teleport(targetLoc);
				player.playSound(targetLoc, Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 1F);
			}
			case WITHER_SKULL -> {
				WitherSkull skull = (WitherSkull) entity;
				if(event.getHitEntity() == null || !(event.getHitEntity() instanceof Player target))
					return;

				if(skull.isCharged()) {
					target.damage(1000);
					return;
				}
				target.damage(2);
			}
		}
	}

	@EventHandler
	public void shootBow(EntityShootBowEvent event) {
		Entity entity = event.getEntity();
		if(!(entity instanceof final Player player))
			return;

		Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		if(!blocks.contains(block.getType()))
			return;

		event.setCancelled(true);

		switch(block.getType()) {
			case REDSTONE_BLOCK -> {
				TNTPrimed tnt = player.getWorld().spawn(player.getEyeLocation(), TNTPrimed.class);
				tnt.setMetadata("bow", new FixedMetadataValue(plugin, true));
				tnt.setVelocity(player.getLocation().getDirection().multiply(event.getForce() * 3D));
				tnt.setSource(player);
			}
			case COAL_BLOCK -> {
				WitherSkull skull = player.getWorld().spawn(player.getEyeLocation(), WitherSkull.class);
				skull.setMetadata("bow", new FixedMetadataValue(plugin, true));
				skull.setVelocity(player.getLocation().getDirection().multiply(event.getForce() * 4D));
				skull.setCharged(ThreadLocalRandom.current().nextInt(0, 10) == 0);
				skull.setShooter(player);
			}
			case LAPIS_BLOCK -> {
				EnderPearl pearl = player.getWorld().spawn(player.getEyeLocation(), EnderPearl.class);
				pearl.setMetadata("bow", new FixedMetadataValue(plugin, true));
				pearl.setVelocity(player.getLocation().getDirection().multiply(event.getForce() * 3D));
				pearl.setShooter(player);
			}
			case WAXED_COPPER_BLOCK -> {
				Trident trident = player.getWorld().spawn(player.getEyeLocation(), Trident.class);
				trident.setMetadata("bow", new FixedMetadataValue(plugin, true));
				trident.setVelocity(player.getLocation().getDirection().multiply(event.getForce() * 3D));
				trident.setShooter(player);
			}
			case EMERALD_BLOCK -> {
				ThrownPotion potion = player.getWorld().spawn(player.getEyeLocation(), ThrownPotion.class);
				potion.setMetadata("bow", new FixedMetadataValue(plugin, true));

				ItemStack potionItem = new ItemStack(Material.SPLASH_POTION);
				PotionMeta potionMeta = (PotionMeta) potionItem.getItemMeta();
				assert potionMeta != null;
				potionMeta.setBasePotionType(PotionType.POISON);
				potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 100, 1), true);
				potionItem.setItemMeta(potionMeta);
				potion.setItem(potionItem);

				potion.setVelocity(player.getLocation().getDirection().multiply(event.getForce() * 2D));
				potion.setShooter(player);
			}
			case DIAMOND_BLOCK -> {
				Location location = player.getLocation();
				for(double i = 1; i <= 10; i += .25) {
					final double finalI = i;
					Bukkit.getScheduler().runTaskLater(plugin, () -> {
						Location loc = location.add(location.getDirection().multiply(finalI));
						EvokerFangs fangs = player.getWorld().spawn(loc, EvokerFangs.class);
						fangs.setMetadata("bow", new FixedMetadataValue(plugin, true));
						fangs.setOwner(player);
					}, (int) ((finalI -1) * 5L));
				}
			}
			case GOLD_BLOCK -> {
				Egg egg = player.getWorld().spawn(player.getEyeLocation(), Egg.class);
				egg.setMetadata("bow", new FixedMetadataValue(plugin, true));
				egg.setVelocity(player.getLocation().getDirection().multiply(event.getForce() * 4D));
				egg.setShooter(player);
			}
			case SNOW_BLOCK -> {
				Snowball snowball = player.getWorld().spawn(player.getEyeLocation(), Snowball.class);
				snowball.setMetadata("bow", new FixedMetadataValue(plugin, true));
				snowball.setVelocity(player.getLocation().getDirection().multiply(event.getForce() * 4D));
				snowball.setShooter(player);
			}
		}
	}
}