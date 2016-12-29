package com.desiremc.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingUtil {

	private static Map<String, ArrayList<Long>> COUNT = new HashMap<String, ArrayList<Long>>();

	public static void clear() {
		COUNT.clear();
	}

	public static void count(Player user) {
		if (!COUNT.containsKey(user.getUniqueId().toString().toLowerCase()))
			COUNT.put(user.getUniqueId().toString().toLowerCase(), new ArrayList<Long>());
		ArrayList<Long> pingList = COUNT.get(user.getUniqueId().toString().toLowerCase());
		pingList.add(getCurrent(user));

		COUNT.remove(user.getUniqueId().toString().toLowerCase());
		COUNT.put(user.getUniqueId().toString().toLowerCase(), pingList);
	}

	public static int getReports(Player user) {
		return COUNT.get(user.getUniqueId().toString().toLowerCase()).size();
	}

	public static long getAvg(Player user) {
		if (!COUNT.containsKey(user.getUniqueId().toString().toLowerCase())
				|| COUNT.get(user.getUniqueId().toString().toLowerCase()).size() < 2)
			return getCurrent(user);
		long total = 0;

		for (long l : COUNT.get(user.getUniqueId().toString().toLowerCase())) {
			total += l;
		}

		return (total / COUNT.get(user.getUniqueId().toString().toLowerCase()).size());
	}

	public static ArrayList<Long> getSession(Player user) {
		if (!COUNT.containsKey(user.getUniqueId().toString().toLowerCase()))
			return new ArrayList<Long>();
		return COUNT.get(user.getUniqueId().toString().toLowerCase());
	}

	public static void remove(Player user) {
		remove(user.getUniqueId().toString());
	}

	public static void remove(String uuid) {
		COUNT.remove(uuid.toLowerCase());
	}

	public static long getCurrent(Player user) {
		int playerPing = ((CraftPlayer) user).getHandle().ping;
		return playerPing;
	}

}