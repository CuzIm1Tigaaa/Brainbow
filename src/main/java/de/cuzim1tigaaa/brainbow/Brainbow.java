package de.cuzim1tigaaa.brainbow;

import de.cuzim1tigaaa.brainbow.listener.Events;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Brainbow extends JavaPlugin {

	private boolean isRunning;

	@Override
	public void onEnable() {
		isRunning = false;

		new Events(this);
	}

	@Override
	public void onDisable() {
		isRunning = false;
	}
}