package com.desiremc.core.koth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KothDeathEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Player player;

	public KothDeathEvent(Player player) {
		this.player = player;
	}
	
	@Override
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
