package de.cuzim1tigaaa.brainbow;

import de.cuzim1tigaaa.brainbow.files.TeamFile;
import de.cuzim1tigaaa.brainbow.listener.Events;
import de.cuzim1tigaaa.brainbow.team.Team;
import de.cuzim1tigaaa.brainbow.team.TeamUtils;
import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

@Getter
public class Brainbow extends JavaPlugin {

	private boolean isRunning;
	private TeamFile teamFile;
	private TeamUtils teamUtils;

	@Override
	public void onEnable() {
		this.isRunning = false;

		this.teamFile = new TeamFile(this);
		this.teamUtils = new TeamUtils(this);

		new Events(this);
	}

	@Override
	public void onDisable() {
		this.isRunning = false;
		if(this.teamFile != null)
			this.teamFile.saveJson();
	}

	public void startGame() {
		this.isRunning = true;

		for(Player p : getServer().getOnlinePlayers()) {
			p.getInventory().clear();
			p.setHealth(20);
			p.setFoodLevel(20);

			Bukkit.getScheduler().runTaskTimer(this, () -> {
				Team t = this.teamUtils.getPlayersTeam(p);
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						new TextComponent(t.getColor() + String.join("", Collections.nCopies(t.getHealth(), "❤"))));
			}, 0L, 20L);
		}
	}

	public Location getHighestBlockAt(Location location) {
		if(location.getBlock().getType().isAir()) {
			while(location.getBlock().getType().isAir())
				location.subtract(0, 1, 0);

			return location.add(0, 1, 0);
		}

		while(!location.getBlock().getType().isAir())
			location.add(0, 1, 0);
		return location;
	}

	public Location getHighestSolidBlockAt(Location location) {
		if(location.getBlock().getType().isAir()) {
			while(!location.getBlock().getType().isSolid())
				location.subtract(0, 1, 0);

			return location.add(0, 1, 0);
		}

		while(location.getBlock().getType().isSolid())
			location.add(0, 1, 0);
		return location;
	}
}