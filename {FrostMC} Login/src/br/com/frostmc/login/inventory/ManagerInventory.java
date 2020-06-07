package br.com.frostmc.login.inventory;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;

public class ManagerInventory {
	
	public static void sendItens(Player player) {
		Inventory inventory = player.getInventory();
		
		inventory.setItem(0, new ItemBuilder().setNome("§3Conectar - §7Clique para abrir").setMaterial(Material.COMPASS).setLore(Arrays.asList("§fAbra para conectar-se ao lobby.")).finalizar());

		player.updateInventory();
	}

}
