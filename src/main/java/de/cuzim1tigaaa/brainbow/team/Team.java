package de.cuzim1tigaaa.brainbow.team;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Team {

	private final ChatColor color;
	private final Set<Player> players;
	private final Location spawn;

	private final Set<Location> target;
	private final Set<Location> door;

	private int health;

	public Team(ChatColor color, Location spawn) {
		this.color = color;
		this.players = new HashSet<>();
		this.spawn = spawn;

		this.target = new HashSet<>();
		this.door = new HashSet<>();

		this.health = 8;
	}

	public void teleportPlayers() {
		for(Player player : this.players)
			player.teleport(this.spawn);
	}

	public void addHealth() {
		this.health++;
	}

	public void removeHealth() {
		this.health--;
	}
}