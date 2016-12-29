package com.desiremc.core;

import com.desiremc.core.classes.ClassModule;
import com.desiremc.core.combatlogger.CombatLoggerModule;
import com.desiremc.core.commands.CommandManager;
import com.desiremc.core.commands.DesireCommand;
import com.desiremc.core.configs.Config;
import com.desiremc.core.crates.KeyConfig;
import com.desiremc.core.crates.KeyModule;
import com.desiremc.core.deathban.DeathBanModule;
import com.desiremc.core.end.EndModule;
import com.desiremc.core.enderchest.EnderChestModule;
import com.desiremc.core.extras.ExtraModule;
import com.desiremc.core.lang.Lang;
import com.desiremc.core.module.Module;
import com.desiremc.core.shops.ShopModule;
import com.desiremc.core.sql.SQL;
import com.desiremc.core.staff.StaffModule;
import com.desiremc.core.tasks.CPSTask;
import com.desiremc.core.tasks.PingCountTask;
import com.desiremc.core.util.TimeSpan;
import com.desiremc.core.wrench.WrenchModule;
import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DesireCore extends JavaPlugin implements PluginMessageListener {

    private static DesireCore instance;

    private final List<Module> modules;

    private Economy eco;
    private Chat chat;

    private final ShopModule shopModule;
    private final WrenchModule wrenchModule;
    private final DeathBanModule deathBanModule;
    private final KeyModule keyModule;
    private final EndModule endModule;
    private final ClassModule classModule;
    private final EnderChestModule enderChestModule;
    private final StaffModule staffModule;
    private final ExtraModule extraModule;
    private final CombatLoggerModule combatLoggerModule;

    private final CommandManager commandManager;

    private final SQL sql;

    private final Config config;

    private EffectManager effectManager;
    
	private CPSTask cpsTask;


    // Language System
    private final Lang lang;

    public DesireCore(){
        instance = this;

        config = new Config();
        lang = new Lang();

        modules = new ArrayList<>();

        wrenchModule = new WrenchModule();
        keyModule = new KeyModule();
        deathBanModule = new DeathBanModule();
        enderChestModule = new EnderChestModule();
        staffModule = new StaffModule();
        shopModule = new ShopModule();
        endModule = new EndModule();
        classModule = new ClassModule();
        extraModule = new ExtraModule();
        combatLoggerModule = new CombatLoggerModule();

        commandManager = new CommandManager();

        registerModules(
                deathBanModule,
                wrenchModule,
                keyModule,
                enderChestModule,
                staffModule,
                shopModule,
                endModule,
                classModule,
                extraModule,
                combatLoggerModule
        );

        sql = new SQL();

    }

    @Override
    public void onLoad(){


        // KEEP THIS HERE
        loadModules();
    }

    @Override
    public void onEnable() {
        EffectLib lib = EffectLib.instance();
        effectManager = new EffectManager(lib);

        setupEconomy();
        setupChat();

        // KEEP THIS HERE
        enableModules();

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new PingCountTask(), TimeSpan.SECOND.getTicks(5), TimeSpan.SECOND.getTicks(5));

        cpsTask = new CPSTask();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, cpsTask, 20, 20);

        //not needed until we expand into bungeecord
        /*getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);*/

    }

    @Override
    public void onDisable() {


        // KEEP THIS HERE
        disableModules();
    }


    public EffectManager getEffectManager(){
        return effectManager;
    }

    public void registerCommand(DesireCommand cmd){
        getCommandManager().registerCommand(cmd);
    }

    public boolean isDebugEnabled() {
        return Config.DEBUG;
    }

    public Config getPluginConfig(){
        return config;
    }

    public static DesireCore getInstance() {
        return instance;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }


    public SQL getSql() {
        return sql;
    }

    public static void registerListeners(Listener... listeners) {
        for (Listener l : listeners)
            DesireCore
                    .getInstance()
                    .getServer()
                    .getPluginManager()
                    .registerEvents(l
                            , DesireCore.getInstance());
    }

    public DeathBanModule getDeathBanModule() {
        return deathBanModule;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }

    private boolean setupChat() {
       /* RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;*/
       return true;
    }

    public Economy getEco(){
        return eco;
    }

    public Chat getChat(){
        return chat;
    }


    public static void registerModules(Module... modules){
        Collections.addAll(DesireCore.getInstance().modules, modules);
    }

    private void loadModules(){
        for(Module module: modules){
            module.onLoad();
        }
    }

    private void enableModules(){
        for(Module module: modules){
            module.onEnable();
        }
    }

    private void disableModules(){
        for(Module module: modules){
            module.onDisable();
        }
    }

	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		
	}

}