package de.cuzim1tigaaa.brainbow.listener;

import de.cuzim1tigaaa.brainbow.Brainbow;
import de.cuzim1tigaaa.brainbow.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

public class Events implements Listener {

	private final Brainbow plugin;
	private final Effects effects;

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
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
		this.effects = new Effects(plugin);
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

				effects.spawnSnow(location, radius);
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
				target.damage(4);
			}
		}
	}

	@EventHandler
	public void explodeTNT(EntityExplodeEvent event) {
		Entity entity = event.getEntity();
		if(entity.getMetadata("bow").isEmpty() || !entity.getMetadata("bow").get(0).asBoolean())
			return;

		if(entity.getType() != EntityType.PRIMED_TNT)
			return;
		TNTPrimed tnt = (TNTPrimed) entity;

		Team team = plugin.getUtils().getTeamWhoseTargetWasHit(tnt.getLocation());
		if(team == null)
			return;
		team.removeHealth();

		if(!(tnt.getSource() instanceof Player player))
			return;

		if(team.getPlayers().contains(player)) {
			tnt.getSource().sendMessage("§cYou hit your own target!");
			return;
		}

		Team playersTeam;
		if((playersTeam = plugin.getUtils().getPlayersTeam(player)) != null)
			playersTeam.addHealth();

		tnt.getSource().sendMessage("§aYou hit the target of " + team.getColor() + team.getPlayers().iterator().next().getName());
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
	public void createFire(BlockIgniteEvent event) {
		if(event.getCause() == BlockIgniteEvent.IgniteCause.LIGHTNING)
			event.setCancelled(true);
	}

	@EventHandler
	public void chat(AsyncPlayerChatEvent event) {

	}
}