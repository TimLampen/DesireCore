
package com.desiremc.core.extras.listeners;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Shaun on 2016-12-19.
 */
public class ItemShowListener implements Listener{
    //todo complete
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if(event.getMessage().contains("@i@") && false){
            TextComponent comp = new TextComponent();
            comp.setText(event.getMessage().replace("@i@", "[ITEM]"));
            //comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new BaseComponent[]));
            for(Player online : Bukkit.getOnlinePlayers()){

                online.spigot().sendMessage(comp);
            }
        }
    }
}
