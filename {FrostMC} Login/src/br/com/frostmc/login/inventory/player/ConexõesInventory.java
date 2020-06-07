 package br.com.frostmc.login.inventory.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;

public class ConexõesInventory {
	
	public static String title = "§8§nConexões:";
	
	public static void giveInventory(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 9, title);
		
		inventory.setItem(0, new ItemBuilder().setNome("§aLobby").setMaterial(Material.EMERALD_BLOCK).setLore(Arrays.asList("§fClique para conectar-se", "§fao lobby principal.")).finalizar());
		
		player.openInventory(inventory);
	}

}
