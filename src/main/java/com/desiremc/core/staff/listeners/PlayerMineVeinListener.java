package com.desiremc.core.staff.listeners;

import com.desiremc.core.DesireCore;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.staff.StaffModule;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Timothy Lampen on 11/20/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public class PlayerMineVeinListener implements Listener {

    private ArrayList<Location> trackedBlocks = new ArrayList<Location>();
    //The majority of this code is from the FoundDiamonds plugin which is stated is free  http://prntscr.com/d9u4nm
    private HashSet<Location> counted = new HashSet<Location>();
    private final BlockFace[] horizontalFaces = {BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH,
            BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.DOWN,
            BlockFace.UP};
    private final BlockFace[] upperFaces = {BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH,
            BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.UP};
    private final BlockFace[] LowerFaces = {BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH,
            BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.DOWN};

    @EventHandler
    public void onMine(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(block.getType().toString().contains("ORE") && !trackedBlocks.contains(block.getLocation())){
            int blocks = getTotalBlocks(block);
            for(UUID uuid : StaffModule.getInstance().getAlerts()){
                if(Bukkit.getPlayer(uuid)!=null){
                    Player staff = Bukkit.getPlayer(uuid);
                    staff.sendMessage(Lang.STAFF_LISTENER_VEIN_MINER.replaceAll("%PLAYER%", player.getName()).replaceAll("%BLOCKS%", blocks + "").replaceAll("%TYPE%", block.getType().toString().split("_ORE")[0].toLowerCase()));
                }
            }
        }
    }

    public int getTotalBlocks(Block original) {
        HashSet<Location> blocks = new HashSet<Location>();
        blocks.add(original.getLocation());
        cycleHorizontalFaces(original.getType(), original, blocks, true);
        trackedBlocks.addAll(blocks);
        return blocks.size() >= 500 ? 500 : blocks.size();
    }


    private void cycleHorizontalFaces(Material mat, Block original, Set<Location> blocks, boolean counting) {
        if (blocks.size() >= 500) { return; }
        findLikeBlocks(horizontalFaces, original, mat, blocks, counting);
        if (blocks.size() >= 500) { return; }
        Block upper = original.getRelative(BlockFace.UP);
        findLikeBlocks(upperFaces, upper, mat, blocks, counting);
        if (blocks.size() >= 500) { return; }
        Block lower = original.getRelative(BlockFace.DOWN);
        findLikeBlocks(LowerFaces, lower, mat, blocks, counting);
    }

    private void findLikeBlocks(BlockFace[] faces, Block passed, Material originalMaterial, Set<Location> blocks, boolean counting) {
        for (BlockFace y : faces) {
            Block var = passed.getRelative(y);
            if (var.getType() == originalMaterial && !blocks.contains(var.getLocation())
                    || isRedstone(var) && isRedstone(originalMaterial)
                    && !blocks.contains(var.getLocation())) {
                if (counting) {
                    counted.add(var.getLocation());
                }
                blocks.add(var.getLocation());
                if (blocks.size() >= 500) { return; }
                cycleHorizontalFaces(originalMaterial, var, blocks, counting);
            }
        }
    }

    public boolean isRedstone(Block m) {
        return (m.getType() == Material.REDSTONE_ORE || m.getType() == Material.GLOWING_REDSTONE_ORE);
    }
    public boolean isRedstone(Material m) {
        return (m == Material.REDSTONE_ORE || m == Material.GLOWING_REDSTONE_ORE);
    }



}
