package br.com.frostmc.gladiator.battle1v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.gladiator.scoreboard.Scoreboarding;
import br.com.frostmc.gladiator.util.protection.Protection;
import br.com.frostmc.gladiator.util.warp.WarpManager;

public class GladiatorManager {
	
	public static HashMap<UUID, Integer> contager = new HashMap<>();
	public static List<UUID> inWarp = new ArrayList<>();
	public static ArrayList<UUID> cooldown = new ArrayList<>();
	public static ArrayList<UUID> invite = new ArrayList<>();
	public static ArrayList<UUID> invited = new ArrayList<>();
	public static HashMap<UUID, Player> fighting = new HashMap<>();
	public static HashMap<UUID, String> opponent = new HashMap<>();
	public static List<Player> fast = new ArrayList<>();
	
	public static void addTime(Player player) {
		contager.put(player.getUniqueId(), getTime(player) + 1);
	}
	
	public static int getTime(Player player) {
		if (contager.containsKey(player.getUniqueId()))
			return contager.get(player.getUniqueId());
		return 0;
	}
	
	public static void removeTime(Player player) {
		contager.remove(player.getUniqueId());
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack blaze_rod = new ItemBuilder().setNome("�aGladiator Normal").setMaterial(Material.getMaterial(101)).glow().finalizar();
	public static ItemStack gray_color = new ItemBuilder().setNome("�7Gladiator R�pido").setMaterial(Material.INK_SACK).glow().setDurabilidade(8).finalizar();
	public static ItemStack green_color = new ItemBuilder().setNome("�7Gladiator R�pido").setMaterial(Material.INK_SACK).glow().setDurabilidade(10).finalizar();
	
	@SuppressWarnings("deprecation")
	public static void sendItens(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.getActivePotionEffects().clear();
		if (!inWarp.contains(player.getUniqueId())) {
			inWarp.add(player.getUniqueId());
		}
		if (fighting.containsKey(player.getUniqueId())) {
			fighting.remove(player.getUniqueId());
		}
		if (contager.containsKey(player.getUniqueId())) {
			removeTime(player);
		}
		for(Player all : Bukkit.getOnlinePlayers()) {
			player.showPlayer(all);
		}
		player.getInventory().setItem(3, blaze_rod);
		player.getInventory().setItem(5, gray_color);
		player.updateInventory();
	}
	
	public void startGladiator(Player player_1, Player player_2) {
		startMatch(player_1, player_2);
		GladiatorGenerator.spawnArena(player_1, player_2, new Location(player_2.getWorld(), 0, 0, 0), Material.GLASS);
	}
	
	public static void removeGladiator(Player player) {
		GladiatorGenerator.removeArena(player);
		WarpManager.send(player);
	}
	
	@SuppressWarnings("deprecation")
	public static void startMatch(Player player, Player player_2) {
		for(Player all : Bukkit.getOnlinePlayers()) {
			player.hidePlayer(all);
			player_2.hidePlayer(all);
			player.showPlayer(player_2);
			player_2.showPlayer(player);
		}
		player.getInventory().clear();
		player_2.getInventory().clear();
		
		player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		
		player.getInventory().setItem(28, new ItemBuilder().setMaterial(Material.WATER_BUCKET).finalizar());
		player.getInventory().setItem(27, new ItemBuilder().setMaterial(Material.LAVA_BUCKET).finalizar());
		player.getInventory().setItem(26, new ItemBuilder().setMaterial(Material.STONE_PICKAXE).finalizar());
		player.getInventory().setItem(25, new ItemBuilder().setMaterial(Material.getMaterial(351)).setDurabilidade(3).setQuantia(64).finalizar());
		player.getInventory().setItem(24, new ItemBuilder().setMaterial(Material.getMaterial(351)).setDurabilidade(3).setQuantia(64).finalizar());
		player.getInventory().setItem(23, new ItemBuilder().setMaterial(Material.getMaterial(351)).setDurabilidade(3).setQuantia(64).finalizar());
		player.getInventory().setItem(22, new ItemBuilder().setMaterial(Material.BOWL).setQuantia(64).finalizar());
		player.getInventory().setItem(21, new ItemBuilder().setMaterial(Material.IRON_BOOTS).finalizar());
		player.getInventory().setItem(20, new ItemBuilder().setMaterial(Material.IRON_LEGGINGS).finalizar());
		player.getInventory().setItem(19, new ItemBuilder().setMaterial(Material.IRON_CHESTPLATE).finalizar());
		player.getInventory().setItem(18, new ItemBuilder().setMaterial(Material.IRON_HELMET).finalizar());
		
		player.getInventory().setItem(17, new ItemBuilder().setMaterial(Material.STONE_AXE).finalizar());
		player.getInventory().setItem(16, new ItemBuilder().setMaterial(Material.getMaterial(351)).setDurabilidade(3).setQuantia(64).finalizar());
		player.getInventory().setItem(15, new ItemBuilder().setMaterial(Material.getMaterial(351)).setDurabilidade(3).setQuantia(64).finalizar());
		player.getInventory().setItem(14, new ItemBuilder().setMaterial(Material.getMaterial(351)).setDurabilidade(3).setQuantia(64).finalizar());
		player.getInventory().setItem(13, new ItemBuilder().setMaterial(Material.BOWL).setQuantia(64).finalizar());
		player.getInventory().setItem(12, new ItemBuilder().setMaterial(Material.IRON_BOOTS).finalizar());
		player.getInventory().setItem(11, new ItemBuilder().setMaterial(Material.IRON_LEGGINGS).finalizar());
		player.getInventory().setItem(10, new ItemBuilder().setMaterial(Material.IRON_CHESTPLATE).finalizar());
		player.getInventory().setItem(9, new ItemBuilder().setMaterial(Material.IRON_HELMET).finalizar());
		player.getInventory().setItem(8, new ItemBuilder().setMaterial(Material.COBBLE_WALL).setQuantia(64).finalizar());
		player.getInventory().setItem(3, new ItemBuilder().setMaterial(Material.WOOD).setQuantia(64).finalizar());
		player.getInventory().setItem(2, new ItemBuilder().setMaterial(Material.LAVA_BUCKET).finalizar());
		player.getInventory().setItem(1, new ItemBuilder().setMaterial(Material.WATER_BUCKET).finalizar());
		player.getInventory().setItem(0, new ItemBuilder().setNome("�eEspada").setMaterial(Material.DIAMOND_SWORD).addEncantamento(Enchantment.DAMAGE_ALL).finalizar());
		player.getInventory().addItem(new ItemBuilder().setMaterial(Material.MUSHROOM_SOUP).setQuantia(11).finalizar());
		
		player_2.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		player_2.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player_2.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		player_2.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		
		player_2.getInventory().setItem(28, new ItemBuilder().setMaterial(Material.LAVA_BUCKET).finalizar());
		player_2.getInventory().setItem(27, new ItemBuilder().setMaterial(Material.LAVA_BUCKET).finalizar());
		player_2.getInventory().setItem(26, new ItemBuilder().setMaterial(Material.STONE_PICKAXE).finalizar());
		player_2.getInventory().setItem(25, new ItemBuilder().setMaterial(Material.getMaterial(351)).setDurabilidade(3).setQuantia(64).finalizar());
		player_2.getInventory().setItem(24, new ItemBuilder().setMaterial(Material.getMaterial(351)).setDurabilidade(3).setQuantia(64).finalizar());
		player_2.getInventory().setItem(23, new ItemBuilder().setMaterial(Material.getMaterial(351)).setDurabilidade(3).setQuantia(64).finalizar());
		player_2.getInventory().setItem(22, new ItemBuilder().setMaterial(Material.BOWL).setQuantia(64).finalizar());
		player_2.getInventory().setItem(21, new ItemBuilder().setMaterial(Material.IRON_BOOTS).finalizar());
		player_2.getInventory().setItem(20, new ItemBuilder().setMaterial(Material.IRON_LEGGINGS).finalizar());
		player_2.getInventory().setItem(19, new ItemBuilder().setMaterial(Material.IRON_CHESTPLATE).finalizar());
		player_2.getInventory().setItem(18, new ItemBuilder().setMaterial(Material.IRON_HELMET).finalizar());
		
		player_2.getInventory().setItem(17, new ItemBuilder().setMaterial(Material.STONE_AXE).finalizar());
		player_2.getInventory().setItem(16, new ItemBuilder().setMaterial(Material.getMaterial(351)).setDurabilidade(3).setQuantia(64).finalizar());
		player_2.getInventory().setItem(15, new ItemBuilder().setMaterial(Material.getMaterial(351)).setDurabilidade(3).setQuantia(64).finalizar());
		player_2.getInventory().setItem(14, new ItemBuilder().setMaterial(Material.getMaterial(351)).setDurabilidade(3).setQuantia(64).finalizar());
		player_2.getInventory().setItem(13, new ItemBuilder().setMaterial(Material.BOWL).setQuantia(64).finalizar());
		player_2.getInventory().setItem(12, new ItemBuilder().setMaterial(Material.IRON_BOOTS).finalizar());
		player_2.getInventory().setItem(11, new ItemBuilder().setMaterial(Material.IRON_LEGGINGS).finalizar());
		player_2.getInventory().setItem(10, new ItemBuilder().setMaterial(Material.IRON_CHESTPLATE).finalizar());
		player_2.getInventory().setItem(9, new ItemBuilder().setMaterial(Material.IRON_HELMET).finalizar());
		player_2.getInventory().setItem(8, new ItemBuilder().setMaterial(Material.COBBLE_WALL).setQuantia(64).finalizar());
		player_2.getInventory().setItem(3, new ItemBuilder().setMaterial(Material.WOOD).setQuantia(64).finalizar());
		player_2.getInventory().setItem(2, new ItemBuilder().setMaterial(Material.LAVA_BUCKET).finalizar());
		player_2.getInventory().setItem(1, new ItemBuilder().setMaterial(Material.WATER_BUCKET).finalizar());
		player_2.getInventory().setItem(0, new ItemBuilder().setNome("�eEspada").setMaterial(Material.DIAMOND_SWORD).addEncantamento(Enchantment.DAMAGE_ALL).finalizar());
		player_2.getInventory().addItem(new ItemBuilder().setMaterial(Material.MUSHROOM_SOUP).setQuantia(11).finalizar());
		
		player.updateInventory();
		player_2.updateInventory();
		player.setHealth(20.0D);
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
		Scoreboarding.setScoreboard(player);
		Scoreboarding.setScoreboard(player_2);
	}

}
