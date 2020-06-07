package br.com.frostmc.hardcoregames.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DropItem {
	
	public static void dropItems(Player p, Location l) {
	    ArrayList<ItemStack> itens = new ArrayList<>();
	    for (ItemStack item : p.getPlayer().getInventory().getContents()) {
	         if ((item != null) && (item.getType() != Material.AIR)) {
	        	  if (item.hasItemMeta()) {
	        		  if ((item.getItemMeta().hasDisplayName()) && (item.getItemMeta().getDisplayName().startsWith("§bKit"))) {
	        			  continue;
	        		  }
	        		  itens.add(item.clone());
	        	  } else {
	        		  itens.add(item);
	        	  }
	         }
	    }
	    for (ItemStack item : p.getPlayer().getInventory().getArmorContents()) {
	         if ((item != null) && (item.getType() != Material.AIR)) {
	              itens.add(item.clone());
	         }
	    }
	    if ((p.getPlayer().getItemOnCursor() != null) && (p.getPlayer().getItemOnCursor().getType() != Material.AIR)) {
	         itens.add(p.getPlayer().getItemOnCursor().clone());
	    }
	    dropItems(p, itens, l);
	}
	  
	@SuppressWarnings({ "deprecation", "null" })
	public static void dropItems(Player p, List<ItemStack> itens, Location l) {
	    World world = l.getWorld();
	    for (ItemStack item : itens) {
	    	 if ((item != null) || (item.getType() != Material.AIR)) {
	              if (item.hasItemMeta()) {
	                  world.dropItemNaturally(l, item.clone()).getItemStack().setItemMeta(item.getItemMeta());
	              } else {
	                  world.dropItemNaturally(l, item);
	              }
	    	 }
	    }
	    p.getPlayer().getInventory().setArmorContents(new ItemStack[4]);
	    p.getPlayer().getInventory().clear();
	    p.getPlayer().setItemOnCursor(new ItemStack(0));
	    itens.clear();
	}

}
