package com.desiremc.core.koth;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.desiremc.core.koth.events.KothJoinEvent;
import com.desiremc.core.koth.events.KothLeaveEvent;

public class KothArena {

	public static void addPlayer(Player p) {
		//TODO: Call KothJoinEvent
		KothModule.getInstance().getPlayers().add(p.getUniqueId());
		Bukkit.getPluginManager().callEvent(new KothJoinEvent(p));
	}
	
	public static void removePlayer(Player p) {
		//TODO: Call KothLeaveEvent
		KothModule.getInstance().getPlayers().remove(p.getUniqueId());
		Bukkit.getPluginManager().callEvent(new KothLeaveEvent(p));
	}
	
	public static void addSpawn(Location loc) {
		KothModule.getInstance().getSpawns().add(loc);
	}
	
}