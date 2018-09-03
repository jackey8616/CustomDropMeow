package com.kunyihua.customdrop.command;

import com.kunyihua.customdrop.GlobalVar;
import com.kunyihua.customdrop.craftclass.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminCommands implements CommandExecutor {
    public AdminCommands() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                sender.sendMessage("§9==========§dCustomDrop§9==========");
                sender.sendMessage("§a/cdrop reload §f- §e重讀設定檔");
                sender.sendMessage("§a/cdrop list §f- §e列出所有生物的掉落資訊");
                return true;
            } else {
                if (args[0].equals("reload")) {
                    GlobalVar.server.resetRecipes();
                    GlobalVar.CustomItemMap.clear();
                    GlobalVar.loadConfig.ReloadConfig();
                    sender.sendMessage(ChatColor.YELLOW + "設定檔讀取完成");
                    return true;
                } else if (args[0].equals("list")) {
                    List<CustomItem> lstCustomItem = new ArrayList<CustomItem>();
                    sender.sendMessage("§9==================================");
                    for (String key : GlobalVar.CustomItemMap.keySet()) {
                        sender.sendMessage("§a「" + GlobalVar.GetEntityName(key) + "」");
                        lstCustomItem = GlobalVar.CustomItemMap.get(key);
                        for (CustomItem customItem : lstCustomItem) {
                            if (customItem.onlyWorld.equals("")) {
                                sender.sendMessage("§a" + customItem.itemName + "§a(§f" + customItem.chance + "%§a掉落§f" + customItem.quantity + "§a個)");
                            } else {
                                sender.sendMessage("§a" + customItem.itemName + "§a(§f" + customItem.chance + "%§a掉落§f" + customItem.quantity + "§a個) - 限定在§f" + customItem.onlyWorld);
                            }
                        }
                    }
                    sender.sendMessage("§9==================================");
                    return true;
                }
            }
        } else {
            sender.sendMessage("此指令不支援控制台模式!");
            return false;
        }
        return false;
    }
}