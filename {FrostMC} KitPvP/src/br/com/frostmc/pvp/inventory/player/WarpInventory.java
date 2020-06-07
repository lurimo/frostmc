package br.com.frostmc.pvp.inventory.player;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.pvp.util.warp.WarpsAPI;

public class WarpInventory {
	
public static String title;
	
	static {
		title = "§8§nSelecione uma Warp:";
	}
	
	@SuppressWarnings("deprecation")
	public static void giveInventory(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 9, title);
		
		inventory.setItem(0, new ItemBuilder().setNome("§fWarp §e§lFPS").setMaterial(Material.GLASS).setLore(Arrays.asList("", "§e§lJogadores nessa warp: §f" + getJogadoresForWarp("FPS"), "")).finalizar());
		inventory.setItem(1, new ItemBuilder().setNome("§fWarp §a§l1V1").setMaterial(Material.BLAZE_ROD).setLore(Arrays.asList("", "§a§lJogadores nessa warp: §f" + getJogadoresForWarp("LavaChallenge"), "")).finalizar());
		inventory.setItem(2, new ItemBuilder().setNome("§fWarp §c§lLava").setMaterial(Material.LAVA_BUCKET).setLore(Arrays.asList("", "§c§lJogadores nessa warp: §f" + getJogadoresForWarp("1v1"), "")).finalizar());
		
		player.openInventory(inventory);
	}
	
	@SuppressWarnings("deprecation")
	public static int getJogadoresForWarp(String warp) {
		int quantidade = 0;
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(WarpsAPI.getWarp(players).equalsIgnoreCase(warp)) {
				quantidade++;
			}
		}
		return quantidade;
	}

}
