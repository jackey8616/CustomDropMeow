package com.kunyihua.customdrop;

import com.kunyihua.customdrop.command.AdminCommands;
import com.kunyihua.customdrop.config.LoadConfig;
import com.kunyihua.customdrop.event.EntityDeathEvents;
import org.bukkit.plugin.java.JavaPlugin;

//import com.kunyihua.crafte.scoreboard.Scoreboard;

public class Main extends JavaPlugin {
    // ���J
    public void onEnable() {
        // ���U
        getServer().getPluginManager().registerEvents(new EntityDeathEvents(), this);
        getCommand("cdrop").setExecutor(new AdminCommands());
        // �]�w�D����
        GlobalVar.main = this;
        // �]�w���A��
        GlobalVar.server = this.getServer();
        // Ū���]�w��
        GlobalVar.loadConfig = new LoadConfig();
        GlobalVar.loadConfig.ReloadConfig();
        // �T��
        GlobalVar.Print("CustomDrop is enabled!");
    }

    // ���X
    public void onDisable() {
        // �M���X����
        GlobalVar.server.resetRecipes();
        // �M����Ӫ�
        GlobalVar.CustomItemMap.clear();
        // �T��
        GlobalVar.Print("CustomDrop is disable!");
    }
}