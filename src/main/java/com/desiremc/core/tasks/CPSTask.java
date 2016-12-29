package com.desiremc.core.tasks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CPSTask implements Runnable {

	public Map<String, Double> clicks = new HashMap<String, Double>();
	private static int CLICKS_PER_SECOND_CAP = 18;

	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (this.clicks.containsKey(player.getUniqueId().toString().toLowerCase())) {
				double count = this.clicks.get(player.getUniqueId().toString().toLowerCase());
				if (count >= CLICKS_PER_SECOND_CAP) {
					//TODO: Ban/Kick Player (Justin will decide)
				}
			}
			this.clicks.remove(player.getUniqueId().toString().toLowerCase());
			this.clicks.put(player.getUniqueId().toString().toLowerCase(), 0.0D);
		}
	}

	public Map<String, Double> getClicks() {
		return clicks;
	}

}
