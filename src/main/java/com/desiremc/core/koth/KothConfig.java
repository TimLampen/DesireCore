package com.desiremc.core.koth;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;

import com.desiremc.core.DesireCore;
import com.desiremc.core.configs.def.YMLClassConfig;

public class KothConfig extends YMLClassConfig {

	protected Location hill;
	protected Set<Location> spawnPoints = new HashSet<Location>();
	protected boolean setupComplete;
	protected Set<UUID> players = new HashSet<UUID>();
	
	public KothConfig() {
		super(new File(DesireCore.getInstance().getDataFolder() + "/koth.yml"));
	}

	public Location getHill() {
		return hill;
	}

	public Set<Location> getSpawnPoints() {
		return spawnPoints;
	}

	public boolean isSetupComplete() {
		return setupComplete;
	}

	public Set<UUID> getPlayers() {
		return players;
	}

}
