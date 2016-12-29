package com.desiremc.core.koth;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import com.desiremc.core.module.Module;

public class KothModule extends Module {
	
	private static KothModule module;
	private static KothConfig config;
	private boolean enabled;
	private Location hill;
	private Set<Location> spawns;
	private Set<UUID> players;

	public static KothModule getInstance() {
		return module;
	}
	
	@Override
	public void onLoad() {
		//TODO: Load Config
		module = this;
		hill = getConfig().getHill();
		spawns = getConfig().getSpawnPoints();
		players = getConfig().getPlayers();
	}

	@Override
	public void onEnable() {
		setGameEnabled(config.isSetupComplete());
	}

	@Override
	public void onDisable() {
		//TODO: Save Config
	}
	
	public void setGameEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public static KothConfig getConfig() {
		return config;
	}

	public Location getHill() {
		return hill;
	}

	public Set<Location> getSpawns() {
		return spawns;
	}
	
	public Set<UUID> getPlayers() {
		return players;
	}
	
}
