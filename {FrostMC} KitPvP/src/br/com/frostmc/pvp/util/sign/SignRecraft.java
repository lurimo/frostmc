package br.com.frostmc.pvp.util.sign;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class SignRecraft implements Listener {
	
	public String Line1 = "";
	public String Line2 = "§aRecraft";
	public String Line3 = "§7(Clique)";
	public String Line4 = "";
	
	@EventHandler
	public void signRecraft(SignChangeEvent event) {
		if (event.getLine(0).equals("recraft")) {
			event.setLine(0, Line1);
			event.setLine(1, Line2);
			event.setLine(2, Line3);
			event.setLine(3, Line4);
		}
	}
	
	@EventHandler
	public void signClickRecraft(PlayerInteractEvent e) {
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && ((e.getClickedBlock().getType() == Material.SIGN) || (e.getClickedBlock().getType() == Material.SIGN_POST) || (e.getClickedBlock().getType() == Material.WALL_SIGN))) {
			if (WarpsAPI.isInWarp(e.getPlayer(), Warps.SPAWN))
				return;
			Sign sign = (Sign) e.getClickedBlock().getState();
			if ((sign.getLine(0).equalsIgnoreCase(Line1)) 
				&& (sign.getLine(1).equalsIgnoreCase(Line2)) 
				&& (sign.getLine(2).equalsIgnoreCase(Line3)) 
				&& (sign.getLine(3).equalsIgnoreCase(Line4))) {
				
				ItemStack coguvermelho = new ItemStack(Material.RED_MUSHROOM, 64);
				ItemMeta n = coguvermelho.getItemMeta();
				n.setDisplayName("§4Cogumelo Vermelho");
				coguvermelho.setItemMeta(n);
				
				ItemStack cogumarrom = new ItemStack(Material.BROWN_MUSHROOM, 64);
				ItemMeta m = cogumarrom.getItemMeta();
				m.setDisplayName("§6Cogumelo Marrom");
				cogumarrom.setItemMeta(m);
				
				ItemStack pote = new ItemStack(Material.BOWL, 64);
				ItemMeta o = pote.getItemMeta();
				o.setDisplayName("§8Pote");
				pote.setItemMeta(o);
				
				Inventory v = Bukkit.createInventory(null, 27, "§8§nRecraft...");
				v.setItem(0, coguvermelho);
				v.setItem(1, pote);
				v.setItem(2, cogumarrom);
				v.setItem(3, coguvermelho);
				v.setItem(4, pote);
				v.setItem(5, cogumarrom);
				v.setItem(6, coguvermelho);
				v.setItem(7, pote);
				v.setItem(8, cogumarrom);
				v.setItem(9, coguvermelho);
				v.setItem(10, pote);
				v.setItem(11, cogumarrom);
				v.setItem(12, coguvermelho);
				v.setItem(13, pote);
				v.setItem(14, cogumarrom);
				v.setItem(15, coguvermelho);
				v.setItem(16, pote);
				v.setItem(17, cogumarrom);
				v.setItem(18, coguvermelho);
				v.setItem(19, pote);
				v.setItem(20, cogumarrom);
				v.setItem(21, coguvermelho);
				v.setItem(22, pote);
				v.setItem(23, cogumarrom);
				v.setItem(24, coguvermelho);
				v.setItem(25, pote);
				v.setItem(26, cogumarrom);
				e.getPlayer().openInventory(v);
			}
		}
	}

}
