package br.com.frostmc.pvp.util.feast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.pvp.FrostPvP;
import br.com.frostmc.pvp.scoreboard.Scoreboarding;
import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class Feast {
	
	public int tempo = 300;
	
	@SuppressWarnings("deprecation")
	public void next() {
		tempo--;
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (WarpsAPI.isInWarp(players, Warps.ARENA)) {
				Scoreboarding.updateFeastTime(players, players.getScoreboard());
			}
		}
		if (tempo == 5 * 60) {
			sendFeastMessage(tempo);
		}
		if (tempo % 60 == 0) {
			sendFeastMessage(tempo);
		}
		if (tempo == 30 || tempo == 20 || tempo == 10 || tempo == 5 || tempo == 4 || tempo == 3 || tempo == 2 || tempo == 1) {
			sendFeastMessage(tempo);
		}
		if (tempo == 0) {
			sendFeastMessage(tempo);
			addItems();
			tempo = 300;
		}
	}
	
	@SuppressWarnings("deprecation")
	public void sendFeastMessage(int tempo) {
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (!WarpsAPI.isInWarp(players, Warps.ARENA))
				return;
			if (tempo > 60) {
				players.sendMessage("�8[�a�lFEAST�8] �fO Feast ir� spawnar em �6" + tempo / 60 + " minutos�f. (�6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockX() + "�f, �6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockY() + "�f, �6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockZ() +  "�f) Digite /feast para apontar a b�ssola.");
			} else if (tempo == 60) {
				players.sendMessage("�8[�a�lFEAST�8] �fO Feast ir� spawnar em �6" + tempo / 60 + " minuto�f. (�6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockX() + "�f, �6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockY() + "�f, �6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockZ() +  "�f) Digite /feast para apontar a b�ssola.");
			} else if (tempo < 60 && tempo > 1) {
				players.sendMessage("�8[�a�lFEAST�8] �fO Feast ir� spawnar em �6" + tempo + " segundos�f. (�6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockX() + "�f, �6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockY() + "�f, �6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockZ() +  "�f) Digite /feast para apontar a b�ssola.");
			} else if (tempo == 1) {
				players.sendMessage("�8[�a�lFEAST�8] �fO Feast ir� spawnar em �6" + tempo + " segundo�f. (�6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockX() + "�f, �6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockY() + "�f, �6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockZ() +  "�f) Digite /feast para apontar a b�ssola.");
			} else if (tempo == 0) {
				players.sendMessage("�8[�a�lFEAST�8] �fO Feast spawnou em (�6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockX() + "�f, �6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockY() + "�f, �6" + FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1").getBlockZ() +  "�f) Digite /feast para apontar a b�ssola.");
			}
		}
	}
	
	public ArrayList<Block> chestFeast = new ArrayList<Block>();

	@SuppressWarnings("deprecation")
	public List<ItemStack> getItems() {
		List<ItemStack> items = new ArrayList<>();

		items.add(new ItemBuilder().setMaterial(Material.getMaterial(373)).setDurabilidade(16460).setQuantia(1).finalizar());
		items.add(new ItemBuilder().setMaterial(Material.getMaterial(373)).setDurabilidade(16420).setQuantia(1).finalizar());
		items.add(new ItemBuilder().setMaterial(Material.getMaterial(373)).setDurabilidade(16418).setQuantia(1).finalizar());
		items.add(new ItemBuilder().setMaterial(Material.getMaterial(373)).setDurabilidade(16420).setQuantia(1).finalizar());
		items.add(new ItemBuilder().setMaterial(Material.getMaterial(373)).setDurabilidade(16426).setQuantia(1).finalizar());
		items.add(new ItemBuilder().setMaterial(Material.getMaterial(261)).setQuantia(1).finalizar());
		items.add(new ItemBuilder().setMaterial(Material.getMaterial(262)).setQuantia(6).finalizar());
		items.add(new ItemBuilder().setMaterial(Material.getMaterial(282)).setQuantia(9).finalizar());
		items.add(new ItemBuilder().setMaterial(Material.getMaterial(384)).setQuantia(4).finalizar());
		items.add(new ItemBuilder().setMaterial(Material.getMaterial(298)).setQuantia(1).finalizar());
		items.add(new ItemBuilder().setMaterial(Material.getMaterial(299)).setQuantia(1).finalizar());
		items.add(new ItemBuilder().setMaterial(Material.getMaterial(300)).setQuantia(1).finalizar());
		items.add(new ItemBuilder().setMaterial(Material.getMaterial(301)).setQuantia(1).finalizar());
		
		return items;
	}
	
	public void addItems() {
		for (Block block : BukkitMain.getWorldEdit().getblocksFromTwoPoints(FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1"), FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos2"))) {
			if (block.getType().equals(Material.CHEST)) {
				Chest chest = (Chest) block.getLocation().getBlock().getState();
				chest.getInventory().clear();
				for (ItemStack item : getItems()) {
					chest.getInventory().setItem(new Random().nextInt(27), item);
				}
			}
		}
	}
	
	public void removeItems() {
		for (Block block : BukkitMain.getWorldEdit().getblocksFromTwoPoints(FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos1"), FrostPvP.getConfigManager().getLocationFromConfig("Feast.pos2"))) {
			if (block.getType().equals(Material.CHEST)) {
				Chest chest = (Chest) block.getLocation().getBlock().getState();
				chest.getInventory().clear();
			}
		}
	}

}