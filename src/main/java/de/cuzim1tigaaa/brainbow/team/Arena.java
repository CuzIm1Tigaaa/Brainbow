package de.cuzim1tigaaa.brainbow.team;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Arena {

	private final String name;
	private final Set<Team> teams;

	public Arena(String name) {
		this.name = name;
		this.teams = new HashSet<>();
	}

	public void start() {
		for(Team team : this.teams) {
			team.teleportPlayers();
			team.openDoor();
		}
	}
}