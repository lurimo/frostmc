package br.com.frostmc.hardcoregames.inventorys;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;

public class ManagerInventory {
	
	public static String kitSelector = "�3Kits - �7Clique para abrir";
	public static String diarySelector = "�3Kit Diario - �7Clique para abrir";
	public static String spectatorSelector = "�3Teleportar - �7Clique para abrir";

	public static void sendItems(Player player) {
		Inventory inventory = player.getInventory();
		inventory.setItem(0, new ItemBuilder().setNome(kitSelector).setMaterial(Material.CHEST).setLore(Arrays.asList("�fVeja todas as suas habilidades.")).finalizar());
		inventory.setItem(1, new ItemBuilder().setNome(diarySelector).setMaterial(Material.ENDER_CHEST).setLore(Arrays.asList("�fTenha acesso a um kit por 1 dia.")).finalizar());
		player.updateInventory();
	}
	
}
