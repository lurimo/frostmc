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

public class SignSoup implements Listener {
	
	public String Line1 = "";
	public String Line2 = "§aSopas";
	public String Line3 = "§7(Clique)";
	public String Line4 = "";
	
	@EventHandler
	public void signSoup(SignChangeEvent event) {
		if (event.getLine(0).equals("sopas")) {
			event.setLine(0, Line1);
			event.setLine(1, Line2);
			event.setLine(2, Line3);
			event.setLine(3, Line4);
		}
	}
	
	@EventHandler
	public void signClickSoup(PlayerInteractEvent e) {
		ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP);
		ItemMeta sopas = sopa.getItemMeta();
		sopa.setItemMeta(sopas);
		if (WarpsAPI.isInWarp(e.getPlayer(), Warps.SPAWN))
			return;
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && ((e.getClickedBlock().getType() == Material.SIGN) || (e.getClickedBlock().getType() == Material.SIGN_POST) || (e.getClickedBlock().getType() == Material.WALL_SIGN))) {
			Sign sign = (Sign) e.getClickedBlock().getState();
			if ((sign.getLine(0).equalsIgnoreCase(Line1)) 
				&& (sign.getLine(1).equalsIgnoreCase(Line2)) 
				&& (sign.getLine(2).equalsIgnoreCase(Line3)) 
				&& (sign.getLine(3).equalsIgnoreCase(Line4))) {
				Inventory v = Bukkit.createInventory(null, 36, "§8§nSopas...");
				for (int i = 0; i < 36; i++) {
					v.addItem(new ItemStack[] { new ItemStack(sopa) });
				}
				e.getPlayer().openInventory(v);
			}
		}
	}
	
}
