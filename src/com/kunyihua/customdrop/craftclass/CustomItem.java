package com.kunyihua.customdrop.craftclass;

import com.kunyihua.crafte.api.Bukkit.KycraftBukkitAPI;
import com.kunyihua.crafte.api.KycraftAPI;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomItem
{
    public boolean isKyc;
    public String kycItemKey;
    //物品名稱
    public String itemName;
    //物品名稱
    public int useOriginalName;
    // 物品說明
    public List<String> itemLores;
    // 物品ID(原始ID)
    public int itemID;
    // 顏色
    public byte red;
    public byte green;
    public byte blue;
    // 物品附屬ID(原始ID)
    public byte itemSubID;
    // 物品附魔
    public List<String> enchants;
    // 得到的物品數量
    public int quantity;
    // 掉落率
    public double chance;
    // 限定地圖
    public String onlyWorld;

    public CustomItem (String itemName, MemorySection config) {
        this.isKyc = itemName.toUpperCase().startsWith("KYC:");
        if (this.isKyc) {
            this.kycItemKey = itemName.toUpperCase().replace("KYC:", "");
        } else {
            this.itemName = itemName;
            this.useOriginalName = config.contains("UseCustomName") ? config.getInt("UseCustomName") : 0;
            this.itemLores = this.getItemLores(config);
            this.getItemID(config);
            this.enchants = config.contains("Enchants") ? config.getStringList("Enchants") : new ArrayList<>();
        }
        this.quantity = config.contains("Quantity") ? config.getInt("Quantity") : 1;
        this.chance = config.contains("Chance") ? config.getDouble("Chance") : 1000;
        this.onlyWorld = config.contains("OnlyWorld") ? config.getString("OnlyWorld") : "";
    }

    private List<String> getItemLores (MemorySection config) {
        List<String> itemLores = new ArrayList<>();
        if (config.contains("ItemLores")) {
            for (String string : config.getStringList("ItemLores")) {
                itemLores.add(string.replace("_", " "));
            }
        }
        return itemLores;
    }

    private void getItemID (MemorySection config) {
        if (config.contains("ItemID")) {
            String strItemID = config.getString("ItemID");
            if (strItemID.contains(":")) {
                this.itemID = Integer.parseInt(strItemID.split(":")[0]);
                if (this.itemID >= 298 && this.itemID <= 301) {
                    this.red = Byte.parseByte(strItemID.split(":")[1].split(",")[0]);
                    this.green = Byte.parseByte(strItemID.split(":")[1].split(",")[1]);
                    this.blue = Byte.parseByte(strItemID.split(":")[1].split(",")[2]);
                } else {
                    this.itemSubID = Byte.parseByte(strItemID.split(":")[1]);
                }
            } else {
                this.itemID = Integer.parseInt(strItemID);
                this.itemSubID = 0;
            }
        }
    }

    @SuppressWarnings("deprecation")
    public ItemStack getResultItem() {
        ItemStack ResultItem;
        ItemMeta newItemMeta;
        if (this.isKyc) {
            ResultItem =  new KycraftBukkitAPI().getKycAPI().getItemByItemKey(this.kycItemKey);
            newItemMeta = ResultItem.getItemMeta();
            this.itemName = newItemMeta.getDisplayName();
        } else {
            // 產生物品用
            LeatherArmorMeta LeatherArmorMeta;

            // 合成後得到的物品設定
            if (this.itemSubID != 0) {
                ResultItem = new ItemStack(Material.getMaterial(this.itemID), 1, this.itemSubID);
            } else {
                ResultItem = new ItemStack(Material.getMaterial(this.itemID));
            }
            // 判斷是否要設定顏色
            if (this.itemID == 298 || this.itemID == 299 || this.itemID == 300 || this.itemID == 301) {
                LeatherArmorMeta = (LeatherArmorMeta) ResultItem.getItemMeta();
                LeatherArmorMeta.setColor(Color.fromRGB(this.red, this.green, this.blue));
                ResultItem.setItemMeta(LeatherArmorMeta);
            }
            newItemMeta = ResultItem.getItemMeta();
            // 附魔
            for (int i = 0; i < this.enchants.size(); i++) {
                String[] EnchantsParts = this.enchants.get(i).split(":");
                int level = Integer.parseInt(EnchantsParts[1]);
                Enchantment enchantment = Enchantment.getByName(EnchantsParts[0]);
                newItemMeta.addEnchant(enchantment, level, true);
            }
            // 說明
            if (this.itemLores.size() > 0) {
                newItemMeta.setLore(this.itemLores);
            }
            // 寫入資料
            ResultItem.setItemMeta(newItemMeta);
            // 設定耐久為最高
            ResultItem.setDurability((short) 0);

        }
        // 名稱
        if (this.useOriginalName == 0) {
            newItemMeta.setDisplayName(this.itemName);
        }
        // 設定數量
        ResultItem.setAmount(this.quantity);
        // 回傳
        return ResultItem;
    }
}