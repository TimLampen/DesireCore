package com.desiremc.core.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BungeeUtil {

	public static void send(Player user, String destination) {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
			dataOutputStream.writeUTF("Connect");
			dataOutputStream.writeUTF(destination);
			user.sendPluginMessage(Bukkit.getServer().getPluginManager().getPlugin("DesireCore"), "BungeeCord",
					byteArrayOutputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void kickPlayer(Player user, String reason) {
	    try {
	    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    	DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
	    	dataOutputStream.writeUTF("KickPlayer");
	    	dataOutputStream.writeUTF(user.getName());
	    	dataOutputStream.writeUTF(reason);
	    	user.sendPluginMessage(Bukkit.getServer().getPluginManager().getPlugin("DesireCore"), "BungeeCord", byteArrayOutputStream.toByteArray());
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	}

}
