package br.com.frostmc.lobby.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.lobby.inventory.enums.ItemsCache;

public class ManagerInventory {
	
	public static void sendItens(Player player) {
		Inventory inventory = player.getInventory();
		
		inventory.setItem(8, new ItemBuilder().setNome(ItemsCache.PLAYERS_ON.getName()).setMaterial(ItemsCache.PLAYERS_ON.getMaterial()).setLore(ItemsCache.PLAYERS_ON.getLore()).finalizar());
		inventory.setItem(5, new ItemBuilder().setNome(ItemsCache.SELECT_LOBBY.getName()).setMaterial(ItemsCache.SELECT_LOBBY.getMaterial()).setLore(ItemsCache.SELECT_LOBBY.getLore()).finalizar());
		inventory.setItem(4, new ItemBuilder().setNome(ItemsCache.SELECT_MENU.getName()).setMaterial(ItemsCache.SELECT_MENU.getMaterial()).setLore(ItemsCache.SELECT_MENU.getLore()).finalizar());
		inventory.setItem(3, new ItemBuilder().setNome(ItemsCache.SELECT_STORE.getName()).setMaterial(ItemsCache.SELECT_STORE.getMaterial()).setLore(ItemsCache.SELECT_STORE.getLore()).finalizar());
		inventory.setItem(0, new ItemBuilder().setNome(ItemsCache.SELECT_SERVER.getName()).setMaterial(ItemsCache.SELECT_SERVER.getMaterial()).setLore(ItemsCache.SELECT_SERVER.getLore()).finalizar());

		player.updateInventory();
	}

}
