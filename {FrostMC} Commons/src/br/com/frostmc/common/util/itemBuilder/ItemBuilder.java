package br.com.frostmc.common.util.itemBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.NBTTagList;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {
	
	public Material material;
	public int amount;
	public short durability;
    public boolean useMeta, glow, inquebravel, cabeça, cor;
    public String displayName, cabeçaNome;
    public HashMap<Enchantment, Integer> enchantments, unsafeEnchantments;
    public List<String> lore;
    public NBTTagCompound basicNBT;
    public NBTTagList enchNBT;
    public Color c;
    
    public ItemBuilder() {
    	this.material = Material.STONE;
        this.amount = 1;
        this.durability = 0;
        this.useMeta = false;
        this.glow = false;
        this.basicNBT = new NBTTagCompound();
        this.enchNBT = new NBTTagList();
        this.basicNBT.set("ench", this.enchNBT);
        this.inquebravel = false;
        this.cabeça = false;
        this.cabeçaNome = "";
        c = Color.WHITE;
        cor = false;
    }
    
    public Material getMaterial() { return material; }
    public int getAmount() { return amount; }
    public short getDurability() { return durability; }
    public HashMap<Enchantment, Integer> getUnsafeEnchantments() { return unsafeEnchantments; }
    public NBTTagCompound getBasicNBT() { return basicNBT; }
    public Color getC() { return c; }
    public String getCabeçaNome() { return cabeçaNome; }
    public String getDisplayName() { return displayName; }
    public HashMap<Enchantment, Integer> getEnchantments() { return enchantments; }
    public List<String> getLore() { return lore; }
    public NBTTagList getEnchNBT() { return enchNBT; }
  
    public ItemBuilder setColor(Color c) {
    	this.c = c;
	    this.cor = true;
	    return this;
    }
  
    public ItemBuilder setMaterial(Material material) {
    	this.material = material;
        return this;
    }
  
    public ItemBuilder setCabeçaNome(String cabeçaNome) {
	    this.cabeça = true;
	    this.cabeçaNome = cabeçaNome;
	    return this;
    }
  
    public ItemBuilder setQuantia(int amount) {
        if (amount > 64) {
            amount = 64;
        }
        this.amount = amount;
        return this;
    }
    
    public ItemBuilder setInquebravel() {
	    this.inquebravel = true;
	    return this;
    }
  
    public ItemBuilder setDurabilidade(int durability) {
	    this.durability = ((short)durability);
        return this;
    }
  
    public ItemBuilder setNome(String text) {
        if (!this.useMeta) {
            this.useMeta = true;
        }
        this.displayName = text.replace("&", "§");
        return this;
    }
  
    public ItemBuilder addEncantamento(Enchantment enchantment) {
        return addEncantamento(enchantment, Integer.valueOf(1));
    }
  
    public ItemBuilder addUnsafeEncantamento(Enchantment enchantment, Integer level) {
        if (this.unsafeEnchantments == null) {
            this.unsafeEnchantments = new HashMap<>();
        }
        this.unsafeEnchantments.put(enchantment, level);
        return this;
    }
    
    public ItemBuilder addEncantamento(Enchantment enchantment, Integer level) {
        if (this.enchantments == null) {
            this.enchantments = new HashMap<>();
        }
        this.enchantments.put(enchantment, level);
        return this;
    }
  
    public ItemBuilder setLore(List<String> lore) {
    	if (!this.useMeta) {
            this.useMeta = true;
    	}
        this.lore = lore;
        return this;
    }
  
    public ItemBuilder glow() {
        this.glow = true;
        return this;
    }
    
    public org.bukkit.inventory.ItemStack finalizar() {
    	org.bukkit.inventory.ItemStack stack = new org.bukkit.inventory.ItemStack(this.material);
        stack.setAmount(this.amount);
        stack.setDurability(this.durability);
        if ((this.enchantments != null) && (!this.enchantments.isEmpty())) {
             for (Map.Entry<Enchantment, Integer> entry : this.enchantments.entrySet()) {
                  stack.addEnchantment((Enchantment)entry.getKey(), ((Integer)entry.getValue()).intValue());
             }
        }
        if ((this.unsafeEnchantments != null) && (!this.unsafeEnchantments.isEmpty())) {
            for (Map.Entry<Enchantment, Integer> entry : this.unsafeEnchantments.entrySet()) {
                 stack.addUnsafeEnchantment((Enchantment)entry.getKey(), ((Integer)entry.getValue()).intValue());
            }
        }
        if (this.useMeta) {
    	    if (this.cabeça) {
			    SkullMeta meta = (SkullMeta) stack.getItemMeta();
		        if (this.lore != null) {
		    	    meta.setLore(this.lore);
		        }
		        meta.hasOwner();
			    meta.setOwner(this.cabeçaNome);
			    if (this.displayName != null) {
		            meta.setDisplayName(this.displayName.replace("&", "§"));
		        }
			    stack.setItemMeta(meta);
    	    } else {
    	    	if (this.cor) {
                    LeatherArmorMeta armorMeta = (LeatherArmorMeta)stack.getItemMeta();
                    armorMeta.setColor(c);
        		    ItemMeta meta = stack.getItemMeta();
                    if (this.displayName != null) {
                        meta.setDisplayName(this.displayName.replace("&", "§"));
                    }
                    if (this.lore != null) {
        		        meta.setLore(this.lore);
                    }
                    if (this.inquebravel) {
            	        meta.spigot().setUnbreakable(true);
                    }
                    stack.setItemMeta(armorMeta);
    	    	} else {
        		    ItemMeta meta = stack.getItemMeta();
                    if (this.displayName != null) {
                        meta.setDisplayName(this.displayName.replace("&", "§"));
                    }
                    if (this.lore != null) {
        		        meta.setLore(this.lore);
                    }
                    if (this.inquebravel) {
            	        meta.spigot().setUnbreakable(true);
                    }
            	    stack.setItemMeta(meta);
    	    	}
    	    }
        }
        if (this.glow) {
            net.minecraft.server.v1_7_R4.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
            if (nmsStack.hasTag()) {
                nmsStack.getTag().set("ench", this.enchNBT);
            } else {
               nmsStack.setTag(this.basicNBT);
            }
            stack = CraftItemStack.asCraftMirror(nmsStack);
        }
        this.material = Material.STONE;
        this.amount = 1;
        this.durability = 0;
        if (this.useMeta) {
            this.useMeta = false;
        }
        if (this.glow) {
            this.glow = false;
        }
        if (this.displayName != null) {
            this.displayName = null;
        }
        if (this.enchantments != null) {
            this.enchantments.clear();
            this.enchantments = null;
        }
        if (this.unsafeEnchantments != null) {
            this.unsafeEnchantments.clear();
            this.unsafeEnchantments = null;
        }
        if (this.lore != null) {
            this.lore = null;
        }
        if (this.cor) {
    	    this.cor = false;
        }
        if (this.c != Color.WHITE) {
    	    this.c = Color.WHITE;
        }
        if (this.inquebravel) {
    	    this.inquebravel = false;
        }
        return stack;
    }
}
