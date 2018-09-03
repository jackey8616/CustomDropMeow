package com.kunyihua.customdrop;

import com.kunyihua.customdrop.command.AdminCommands;
import com.kunyihua.customdrop.config.LoadConfig;
import com.kunyihua.customdrop.event.EntityDeathEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EntityDeathEvents(), this);
        getCommand("cdrop").setExecutor(new AdminCommands());
        GlobalVar.main = this;
        GlobalVar.server = this.getServer();
        GlobalVar.kycraftLoaded = this.getServer().getPluginManager().getPlugin("Kycraft") != null;
        GlobalVar.loadConfig = new LoadConfig();
        GlobalVar.loadConfig.ReloadConfig();
        GlobalVar.Print("CustomDrop is enabled!");
    }

    public void onDisable() {
        GlobalVar.server.resetRecipes();
        GlobalVar.CustomItemMap.clear();
        GlobalVar.Print("CustomDrop is disable!");
    }
}