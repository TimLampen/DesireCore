package com.desiremc.core.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.desiremc.core.util.PingUtil;

public class PingCountTask implements Runnable {
	
	private int rot = 0;

	public void run() {
		if (this.rot % 100 == 0)
			PingUtil.clear();

		for (Player player : Bukkit.getOnlinePlayers()) {
			PingUtil.count(player);
		}

		this.rot += 1;
	}

}