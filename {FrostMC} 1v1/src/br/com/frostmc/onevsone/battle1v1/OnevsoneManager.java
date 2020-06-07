package br.com.frostmc.onevsone.battle1v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.onevsone.scoreboard.Scoreboarding;
import br.com.frostmc.onevsone.util.protection.Protection;
import br.com.frostmc.onevsone.util.warp.WarpsAPI;
import br.com.frostmc.onevsone.util.warp.WarpsAPI.Locais;

public class OnevsoneManager {
	
	public static HashMap<UUID, Double> health = new HashMap<UUID, Double>();
	public static HashMap<UUID, Integer> soup = new HashMap<UUID, Integer>();
	public static List<UUID> inWarp = new ArrayList<>();
	public static ArrayList<UUID> cooldown = new ArrayList<>();
	public static ArrayList<UUID> invite = new ArrayList<>();
	public static ArrayList<UUID> invited = new ArrayList<>();
	public static HashMap<UUID, Player> fighting = new HashMap<>();
	public static HashMap<UUID, String> opponent = new HashMap<>();
	public static List<Player> fast = new ArrayList<>();
	
	public static ItemStack create(Material material, String name, short color) {
		ItemStack itemStack = new ItemStack(material, 1, color);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.spigot().setUnbreakable(true);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack blaze_rod = new ItemBuilder().setNome("§a1v1 Normal").setMaterial(Material.BLAZE_ROD).glow().finalizar();
	public static ItemStack gray_color = new ItemBuilder().setNome("§71v1 Rápido").setMaterial(Material.INK_SACK).glow().setDurabilidade(8).finalizar();
	public static ItemStack green_color = new ItemBuilder().setNome("§71v1 Rápido").setMaterial(Material.INK_SACK).glow().setDurabilidade(10).finalizar();
	
	@SuppressWarnings("deprecation")
	public static void sendItens(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.getActivePotionEffects().clear();
		if (!inWarp.contains(player.getUniqueId())) {
			inWarp.add(player.getUniqueId());
		}
		for(Player all : Bukkit.getOnlinePlayers()) {
			player.showPlayer(all);
		}
		player.getInventory().setItem(3, blaze_rod);
		player.getInventory().setItem(5, gray_color);
		player.updateInventory();
	}
	
	@SuppressWarnings("deprecation")
	public static void startMatch(Player player, Player player_2) {
		WarpsAPI.goToWarp(player, Locais.ONEVSONEPOS1);
		WarpsAPI.goToWarp(player_2, Locais.ONEVSONEPOS2);
		for(Player all : Bukkit.getOnlinePlayers()) {
			player.hidePlayer(all);
			player.showPlayer(player_2);
			player_2.hidePlayer(all);
			player_2.showPlayer(player);
		}
		player.getInventory().clear();
		player_2.getInventory().clear();
		for(int i = 0; i < 9; i++) {
			player.getInventory().addItem(create(Material.MUSHROOM_SOUP, "", (short)0));
			player_2.getInventory().addItem(create(Material.MUSHROOM_SOUP, "", (short)0));
		}
		player.getInventory().setItem(0, new ItemBuilder().setNome("§eEspada").setMaterial(Material.WOOD_SWORD).addEncantamento(Enchantment.DAMAGE_ALL).finalizar());
		player_2.getInventory().setItem(0, new ItemBuilder().setNome("§eEspada").setMaterial(Material.WOOD_SWORD).addEncantamento(Enchantment.DAMAGE_ALL).finalizar());
		player.updateInventory();
		player_2.updateInventory();
		player.setGameMode(GameMode.SURVIVAL);
		player.setHealth(20.0D);
		player.setGameMode(GameMode.SURVIVAL);
		player_2.setHealth(20.0D);
		fast.remove(player_2);
		fast.remove(player);
		fighting.put(player.getUniqueId(), player_2);
		fighting.put(player_2.getUniqueId(), player);
		invite.remove(player.getUniqueId());
		invited.remove(player_2.getUniqueId());
		cooldown.remove(player.getUniqueId());
		cooldown.remove(player_2.getUniqueId());
		opponent.put(player.getUniqueId(), player_2.getName());
		opponent.put(player_2.getUniqueId(), player.getName());
		Protection.removeProtection(player);
		Protection.removeProtection(player_2);
		Scoreboarding.updateOpponent(player);
		Scoreboarding.updateOpponent(player_2);
	}

}
