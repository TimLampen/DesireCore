package com.desiremc.core.classes.classes;

import com.desiremc.core.classes.ActiveItem;
import com.desiremc.core.classes.ArmorType;
import com.desiremc.core.classes.ClassModule;
import com.desiremc.core.classes.ClassType;
import com.desiremc.core.staff.StaffModule;
import com.desiremc.core.util.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Timothy Lampen on 12/9/2016.
 * Copyright © 2016 Tim Plugins
 * Under no circumstances are you allowed to edit, copy, remove, or tamper with this file
 * unless given direct permission by myself.
 * If you have any problems or issues contact me at timplugins@gmail.com
 */
public abstract class HCFClass {
    
    private HashMap<ClassType, ArrayList<UUID>> players = new HashMap<>();
    private HashMap<ClassType, HashMap<Material, ActiveItem>> activeItems = new HashMap<>();
    private HashMap<ClassType, HashMap<Material, PotionEffect>> passiveItems = new HashMap<>();
    private HashMap<ClassType, ArrayList<PotionEffect>> pots = new HashMap<>();

    /**
     * @param player the player that is being checked (their armor)
     * @param type the type of armor the class is.
     * @return true if the player has the ability to be apart of the certain class
     */
    public boolean isApplicable(Player player, ArmorType type){
        boolean isapp = true;
        int i = 0;
        if(player==null){
            return false;
        }
        for(ItemStack is : player.getInventory().getArmorContents()){
            if(is==null){
                return false;
            }
            if(!is.getType().toString().contains(type.toString())){
                isapp = false;
            }
            i++;
        }
        return isapp && i==4;
    }

    /**
     * @return the ClassType of the specific class
     */
    public abstract ClassType getClassType();

    /**
     * @return the ArmorType of the specific class
     */
    public abstract ArmorType getArmorType();

    //A method that is ran every second in the ClassModule, for all classes
    public abstract void tick();

    /**
     * @param player the player who is holding the item
     * @param is the material of the item which may be a passive item for the player's class
     */
    public abstract void checkPassiveItems(Player player, Material is);

    /**
     * @param player the player who is holding the item
     * @param is the material of the item which may be a active item for the player's class
     */
    public abstract void checkActiveItems(Player player, Material is);

    //I used this for adding the potion effects to the players in the class
    public abstract void effect(Player player);

    //For any variables in the general part of the config section for each class. Called in the plugin load after loading the variables that are the same for all classes
    public abstract void classSpecificLoad();

    //For saving any changes or any needed information to the classes.yml file, called in the plugin disable
    public abstract void classSpecificSave();

    /**
     * @param player the uuid of the player that you want to add to the class
     */
    public void addPlayer(UUID player){
        if(players.containsKey(getClassType())){
            players.get(getClassType()).add(player);
        }
        else{
            ArrayList<UUID> uuids = new ArrayList<>();
            uuids.add(player);
            players.put(getClassType(), uuids);
        }
    }

    /**
     * @param player the uuid of the player that you want to remove from the class
     */
    public void removePlayer(UUID player){
        if(Bukkit.getPlayer(player)!=null){
            Player pPlayer = Bukkit.getPlayer(player);
            if(pPlayer.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                pPlayer.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
        }
        if(players.containsKey(getClassType())) {
            if (players.get(getClassType()).contains(player)) {
                players.get(getClassType()).remove(player);
            }
        }
    }

    /**
     */
    public ArrayList<UUID> getPlayers(){
        if(players.containsKey(getClassType())){
            return  players.get(getClassType());
        }
        return new ArrayList<>();
    }

    /**
     * @return the passive items and their potion effects
     */
    public HashMap<Material, PotionEffect> getPassiveItems(){
        return passiveItems.get(getClassType());
    }

    /**
     * @return the passive items and their potion effects / arguments
     */
    public HashMap<Material, ActiveItem> getActiveItems(){
        return activeItems.get(getClassType());
    }

    /**
     * @return the constant potion effects for that class
     */
    public ArrayList<PotionEffect> getPots(){
        return pots.get(getClassType());
    }

    public void addPotionEffect(Player player, PotionEffect effect){
        if(player.hasPotionEffect(effect.getType())){
            for(PotionEffect potion : player.getActivePotionEffects()){
                if(potion.getType().equals(effect.getType())){
                    if(effect.getAmplifier()>=potion.getAmplifier()){
                        player.addPotionEffect(effect, true);
                        break;
                    }
                }
            }
        }
        else{
            player.addPotionEffect(effect, true);
        }
    }

    public void load(){
        String sType = getClassType().toString().toLowerCase();
        ArrayList<PotionEffect> tempPots = new ArrayList<>();
        for(String effect : ClassModule.getInstance().getClassConfig().getConfig().getConfigurationSection("classes." + sType + ".constant").getKeys(false)){
            int amp = ClassModule.getInstance().getClassConfig().getConfig().getInt("classes." + sType + ".constant." + effect + ".amplifier");
            int dura = 25;
            if(effect.equals("NIGHT_VISION")){
                dura = 250;
            }
            tempPots.add(new PotionEffect(PotionEffectType.getByName(effect), dura, amp));
        }
        pots.put(getClassType(), tempPots);

        if(ClassModule.getInstance().getClassConfig().getConfig().getConfigurationSection("classes." + sType + ".passiveitems")!=null) {
            HashMap<Material, PotionEffect> tempPassiveItems = new HashMap<>();
            for (String mat : ClassModule.getInstance().getClassConfig().getConfig().getConfigurationSection("classes." + sType + ".passiveitems").getKeys(false)) {
                Material material = Material.valueOf(mat);
                PotionEffectType ptype = PotionEffectType.getByName(ClassModule.getInstance().getClassConfig().getConfig().getString("classes." + sType + ".passiveitems." + mat + ".potioneffect"));
                int amp = ClassModule.getInstance().getClassConfig().getConfig().getInt("classes." + sType + ".passiveitems." + mat + ".amplifier");
                tempPassiveItems.put(material, new PotionEffect(ptype, 25, amp));
            }
            passiveItems.put(getClassType(), tempPassiveItems);
        }

        if(ClassModule.getInstance().getClassConfig().getConfig().getConfigurationSection("classes." + sType + ".activeitems")!=null) {
            HashMap<Material, ActiveItem> tempActiveItems = new HashMap<>();
            for (String sMat : ClassModule.getInstance().getClassConfig().getConfig().getConfigurationSection("classes." + sType + ".activeitems").getKeys(false)) {
                Material mat = Material.valueOf(sMat);
                PotionEffectType ptype = PotionEffectType.getByName(ClassModule.getInstance().getClassConfig().getConfig().getString("classes." + sType + ".activeitems." + sMat + ".potioneffect"));
                int amp = ClassModule.getInstance().getClassConfig().getConfig().getInt("classes." + sType + ".activeitems." + sMat + ".amplifier");
                int duration = ClassModule.getInstance().getClassConfig().getConfig().getInt("classes." + sType + ".activeitems." + sMat + ".duration") * 20;
                int arg = ClassModule.getInstance().getClassConfig().getConfig().getInt("classes." + sType + ".activeitems." + sMat + ".arg");

                boolean enemies = false;
                if(ClassModule.getInstance().getClassConfig().getConfig().get("classes." + sType + ".activeitems." + sMat + ".enemies")!=null) {
                    enemies = ClassModule.getInstance().getClassConfig().getConfig().getBoolean("classes." + sType + ".activeitems." + sMat + ".enemies");
                }
                tempActiveItems.put(mat, new ActiveItem(new PotionEffect(ptype, duration, amp), enemies, arg));
            }
            activeItems.put(getClassType(), tempActiveItems);

            if(ClassModule.getInstance().getClassConfig().getConfig().getConfigurationSection("players." + sType)!=null){
                for(String s : ClassModule.getInstance().getClassConfig().getConfig().getConfigurationSection("players." + sType).getKeys(false)){
                    addPlayer(UUID.fromString(s));
                }
            }
        }
    }

    public void save(){
        Bukkit.broadcastMessage("11111");
        ArrayList<UUID> players = getPlayers();
        Bukkit.broadcastMessage(players.size() + " 11111121");
        ClassModule.getInstance().getClassConfig().getConfig().set("players." + getClassType().toString().toLowerCase(), players);
        ClassModule.getInstance().getClassConfig().save();
    }
}
