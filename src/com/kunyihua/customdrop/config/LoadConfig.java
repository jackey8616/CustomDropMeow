package com.kunyihua.customdrop.config;

import com.kunyihua.customdrop.GlobalVar;
import com.kunyihua.customdrop.craftclass.CustomItem;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class LoadConfig {
    // 主要讀取設定用
    private FileConfiguration data = null;

    // 開檔用
    private File filePreload = null;

    public LoadConfig() {

    }

    // 重讀設定檔
    public void ReloadConfig() {
        // 確認檔案是否存在
        this.filePreload = new File(GlobalVar.pluginMainDir + "config.yml");
        if (this.filePreload.exists()) {
            // 讀取設定檔內容
            this.data = YamlConfiguration.loadConfiguration(this.filePreload);
        } else {
            // 檔案不存在，建立預設檔
            CreateDefaultConfig();
            // 重載檔案
            this.filePreload = new File(GlobalVar.pluginMainDir + "config.yml");
            // 讀取設定檔內容
            this.data = YamlConfiguration.loadConfiguration(this.filePreload);
        }

        if (data.contains("CustomDrop")) {
            // 待儲存的掉落物清單
            List<CustomItem> dropItems = new ArrayList<CustomItem>();
            // 取得生物名稱
            for (String entity_name : data.getConfigurationSection("CustomDrop").getKeys(false)) {
                // 清空暫存區
                dropItems = new ArrayList<CustomItem>();
                // 迴圈讀出掉落物
                for (String name : data.getConfigurationSection("CustomDrop." + entity_name).getKeys(false)) {
                    if (name.startsWith("KYC:") && !GlobalVar.kycraftLoaded) {
                        System.out.println("Detected KYC config with item name: " + name + " , but Kycraft is not loaded. Skip.");
                        continue;
                    } else {
                        CustomItem customItem = new CustomItem(name, (MemorySection) data.getConfigurationSection("CustomDrop." + entity_name + "." + name));
                        if (customItem.itemID > 0 || name.toUpperCase().startsWith("KYC:")) {
                            // 加入
                            dropItems.add(customItem);
                        } else {
                            // 警告
                            System.out.println(GlobalVar.detailStr + "[ReloadConfig]" + entity_name + "的掉落物" + name + "未設定ItemID!");
                        }
                    }
                }
                GlobalVar.CustomItemMap.put(entity_name, dropItems);
            }
        }
    }

    // 建立預設檔
    public void CreateDefaultConfig() {
        try {
            File createDir = new File(GlobalVar.pluginMainDir);

            if (!createDir.exists()) {
                boolean dirCreated = false;

                int retries = 15;

                while ((!dirCreated) && (retries != 0)) {
                    retries--;
                    dirCreated = createDir.mkdir();
                }

                if (!dirCreated) {
                    System.out.println(GlobalVar.detailStr + "[CreateDefaultConfig]Directory failed to create. No permissions?");
                    return;
                }
            }

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(GlobalVar.pluginMainDir + "config.yml"), "UTF-8"));
            out.write("CustomDrop:\r\n");
            out.write("#==========================#\r\n");
            out.write("#生物實體ID                #\r\n");
            out.write("#CREEPER       : 苦力怕    #\r\n");
            out.write("#SKELETON      : 骷髏      #\r\n");
            out.write("#SPIDER        : 蜘蛛      #\r\n");
            out.write("#GIANT         : 巨人      #\r\n");
            out.write("#ZOMBIE        : 殭屍      #\r\n");
            out.write("#SLIME         : 史萊姆    #\r\n");
            out.write("#GHAST         : 幽靈水母  #\r\n");
            out.write("#PIGZOMBIE     : 殭屍豬人  #\r\n");
            out.write("#ENDERMAN      : 終界使者  #\r\n");
            out.write("#CAVESPIDER    : 洞穴蜘蛛  #\r\n");
            out.write("#SILVERFISH    : 石頭蟲    #\r\n");
            out.write("#BLAZE         : 烈焰使者  #\r\n");
            out.write("#LAVASLIME     : 烈焰史萊姆#\r\n");
            out.write("#ENDERDRAGON   : 終界龍    #\r\n");
            out.write("#WITHERBOSS    : 凋零怪    #\r\n");
            out.write("#WITCH       　: 巫婆      #\r\n");
            out.write("#BAT           : 蝙蝠      #\r\n");
            out.write("#PIG           : 豬        #\r\n");
            out.write("#SHEEP         : 羊        #\r\n");
            out.write("#COW           : 牛        #\r\n");
            out.write("#CHICKEN       : 雞        #\r\n");
            out.write("#SQUID         : 章魚      #\r\n");
            out.write("#WOLF          : 狼        #\r\n");
            out.write("#MUSHROOMCOW   : 蘑菇牛    #\r\n");
            out.write("#SNOWMAN       : 雪人      #\r\n");
            out.write("#OZELOT        : 貓        #\r\n");
            out.write("#VILLAGERGOLEM : 鐵人      #\r\n");
            out.write("#ENTITYHORSE   : 馬        #\r\n");
            out.write("#VILLAGER      : 村民      #\r\n");
            out.write("#==========================#\r\n");
            out.write("  ZOMBIE:\r\n");
            out.write("#==============#\r\n");
            out.write("#掉落的物品名稱#\r\n");
            out.write("#==============#\r\n");
            out.write("    §f李逍遙的木劍§f:\r\n");
            out.write("#==========================#\r\n");
            out.write("#掉落的物品是否套用原始名稱#\r\n");
            out.write("#==========================#\r\n");
            out.write("      UseOriginalName: 1\r\n");
            out.write("#==============================#\r\n");
            out.write("#掉落的物品原始ID(例：木劍=268)#\r\n");
            out.write("#==============================#\r\n");
            out.write("      ItemID: 268\r\n");
            out.write("#==============#\r\n");
            out.write("#掉落的物品說明#\r\n");
            out.write("#==============#\r\n");
            out.write("      ItemLores:\r\n");
            out.write("      - §e李逍遙自己削出來的木劍§f\r\n");
            out.write("#=============================#\r\n");
            out.write("#掉落的物品附魔               #\r\n");
            out.write("#- <附魔>:<等級>              #\r\n");
            out.write("#PROTECTION_ENVIRONMENTAL 保護#\r\n");
            out.write("#PROTECTION_EXPLOSIONS 防爆   #\r\n");
            out.write("#PROTECTION_PROJECTILE 防彈   #\r\n");
            out.write("#PROTECTION_FIRE 抗火         #\r\n");
            out.write("#PROTECTION_FALL 輕盈(腳)     #\r\n");
            out.write("#ARROW_INFINITE 無限弓        #\r\n");
            out.write("#ARROW_DAMAGE 強力弓          #\r\n");
            out.write("#ARROW_FIRE 火燄弓            #\r\n");
            out.write("#ARROW_KNOCKBACK 弓擊退       #\r\n");
            out.write("#DAMAGE_UNDEAD 不死剋星       #\r\n");
            out.write("#DAMAGE_ALL 鋒利              #\r\n");
            out.write("#DAMAGE_ARTHROPODS 節肢剋星   #\r\n");
            out.write("#OXYGEN 氧氣(頭)              #\r\n");
            out.write("#DURABILITY 耐久              #\r\n");
            out.write("#LOOT_BONUS_BLOCKS 幸運       #\r\n");
            out.write("#LOOT_BONUS_MOBS 掠奪         #\r\n");
            out.write("#SILK_TOUCH 絲綢之觸          #\r\n");
            out.write("#WATER_WORKER 親水性          #\r\n");
            out.write("#DIG_SPEED 效率               #\r\n");
            out.write("#KNOCKBACK 擊退劍             #\r\n");
            out.write("#FIRE_ASPECT 火焰附加         #\r\n");
            out.write("#=============================#\r\n");
            out.write("      Enchants:\r\n");
            out.write("      - DAMAGE_ALL:1\r\n");
            out.write("#==================#\r\n");
            out.write("#掉落的物品數量    #\r\n");
            out.write("#註：附魔物品無效  #\r\n");
            out.write("#==================#\r\n");
            out.write("      Quantity: 1\r\n");
            out.write("#========================#\r\n");
            out.write("#掉落的機率(0.01~100)    #\r\n");
            out.write("#若無此設定則表示100%掉落#\r\n");
            out.write("#========================#\r\n");
            out.write("      Chance: 100\r\n");
            out.write("#============================#\r\n");
            out.write("#指定掉落的世界              #\r\n");
            out.write("#若無此設定則全部世界都會掉落#\r\n");
            out.write("#============================#\r\n");
            out.write("      OnlyWorld: world\r\n");
            out.write("#========#\r\n");
            out.write("#其他範例#\r\n");
            out.write("#========#\r\n");
            out.write("  PIGZOMBIE:\r\n");
            out.write("    §f集字卡-「殭」§f:\r\n");
            out.write("      ItemID: 339\r\n");
            out.write("      ItemLores:\r\n");
            out.write("      - §e集滿「殭」「屍」「豬」「人」§f\r\n");
            out.write("      - §e即可合成殭屍豬人的飲料§f\r\n");
            out.write("      Quantity: 1\r\n");
            out.write("      Chance: 25\r\n");
            out.write("    §f集字卡-「屍」§f:\r\n");
            out.write("      ItemID: 339\r\n");
            out.write("      ItemLores:\r\n");
            out.write("      - §e集滿「殭」「屍」「豬」「人」§f\r\n");
            out.write("      - §e即可合成殭屍豬人的飲料§f\r\n");
            out.write("      Quantity: 1\r\n");
            out.write("      Chance: 25\r\n");
            out.write("    §f集字卡-「豬」§f:\r\n");
            out.write("      ItemID: 339\r\n");
            out.write("      ItemLores:\r\n");
            out.write("      - §e集滿「殭」「屍」「豬」「人」§f\r\n");
            out.write("      - §e即可合成殭屍豬人的飲料§f\r\n");
            out.write("      Quantity: 1\r\n");
            out.write("      Chance: 25\r\n");
            out.write("    §f集字卡-「人」§f:\r\n");
            out.write("      ItemID: 339\r\n");
            out.write("      ItemLores:\r\n");
            out.write("      - §e集滿「殭」「屍」「豬」「人」§f\r\n");
            out.write("      - §e即可合成殭屍豬人的飲料§f\r\n");
            out.write("      Quantity: 1\r\n");
            out.write("      Chance: 25\r\n");
            out.close();
        } catch (Exception e) {
            System.out.println(GlobalVar.detailStr + "[CreateDefaultConfig]Error on create default config!");
        }
    }
}