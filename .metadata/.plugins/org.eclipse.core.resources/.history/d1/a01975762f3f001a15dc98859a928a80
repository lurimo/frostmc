package br.com.frostmc.pvp.util.hability;

import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import br.com.frostmc.common.util.cooldown.CooldownAPI;
import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.pvp.scoreboard.Scoreboarding;
import br.com.frostmc.pvp.util.protection.Protection;
import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class KitAPI {
	
	public static HashMap<Player, String> Kit = new HashMap<>();
	
	public static void setKit(Player player, Kits kit) {
		Kit.put(player, kit.getName());
		WarpsAPI.setWarp(player, Warps.ARENA);
		WarpsAPI.goToArena(player);
		Protection.removeProtection(player);
		Scoreboarding.setScoreboard(player);
	}
	
	public static String getKit(Player player) {
		return Kit.get(player);
	}
	
	public static boolean isUsingKits(Player player) {
		return !Kit.get(player).equals(Kits.NENHUM.getName());
	}
	
	public static boolean hasKit(Player player, String kit) {
		String playerkit = Kit.get(player);
		return kit.equals(playerkit);
	}
	
	public static void removeKit(Player player) {
		if (getKit(player) != null) {
			if (CooldownAPI.hasCooldown(player, "Kit " + KitAPI.getKit(player).toUpperCase())) {
				CooldownAPI.removeCooldown(player, "Kit " + KitAPI.getKit(player).toUpperCase());
			}
		}
		Kit.put(player, Kits.NENHUM.getName());
	}
	
	public static void giveItens(Player player) {
		PlayerInventory inv = player.getInventory();
		inv.clear();
		player.setFireTicks(0);
		player.setHealth(20.0D);
		player.setFlying(false);
		
		inv.setItem(13, new ItemBuilder().setNome("§4Cogumelo Vermelho").setMaterial(Material.RED_MUSHROOM).setQuantia(64).finalizar());
		inv.setItem(14, new ItemBuilder().setNome("§6Cogumelo Marrom").setMaterial(Material.BROWN_MUSHROOM).setQuantia(64).finalizar());
		inv.setItem(15, new ItemBuilder().setNome("§8Pote").setMaterial(Material.BOWL).setQuantia(64).finalizar());
		
		for(int i = 0; i < 34; i++) {
			inv.addItem(new ItemBuilder().setMaterial(Material.MUSHROOM_SOUP).finalizar());
		}
		
		if (hasKit(player, Kits.PVP.getName())) {
			inv.setItem(0, new ItemBuilder().setNome("§eEspada").setMaterial(Material.STONE_SWORD).addEncantamento(Enchantment.DAMAGE_ALL).finalizar());
		} else {
			inv.setItem(0, new ItemBuilder().setNome("§eEspada").setMaterial(Material.STONE_SWORD).finalizar());
		}
		inv.setItem(8, new ItemBuilder().setNome("§eBússola").setMaterial(Material.COMPASS).finalizar());
		
		player.setGameMode(GameMode.SURVIVAL);
		player.updateInventory();
	}
	
	public static void giveKit(Player player, Kits kits) {
		PlayerInventory inv = player.getInventory();
		
		if (!kits.equals(Kits.PVP) && !kits.equals(Kits.ANCHOR) && !kits.equals(Kits.ANTISTOMPER) && !kits.equals(Kits.HULK) && !kits.equals(Kits.MAGMA) && !kits.equals(Kits.NINJA) && !kits.equals(Kits.SNAIL) && !kits.equals(Kits.STOMPER) && !kits.equals(Kits.TURTLE) && !kits.equals(Kits.VIPER) && !kits.equals(Kits.CRITICAL)) {
			if(kits.equals(Kits.GRANDPA)) {
				inv.setItem(1, new ItemBuilder().setNome("§bKit " + kits.getName()).setMaterial(Material.STICK).addUnsafeEncantamento(Enchantment.KNOCKBACK, 1).finalizar());
			} else if(kits.equals(Kits.SWITCHER)) {
				inv.setItem(1, new ItemBuilder().setNome("§bKit " + kits.getName()).setMaterial(Material.SNOW_BALL).setQuantia(16).finalizar());
			} else if(kits.equals(Kits.ARCHER)) {
				inv.setItem(16, new ItemBuilder().setNome("§aFlecha").setMaterial(Material.ARROW).finalizar());
				inv.setItem(1, new ItemBuilder().setNome("§aArco").setMaterial(Material.BOW).addEncantamento(Enchantment.ARROW_INFINITE).finalizar());
			} else if(kits.equals(Kits.GLADIATOR)) {
				inv.setItem(1, new ItemBuilder().setNome("§bKit " + kits.getName()).setMaterial(kits.getMaterial()).finalizar());
				inv.setItem(2, new ItemBuilder().setNome("§bBlocos").setMaterial(Material.COBBLESTONE).setQuantia(16).finalizar());
				player.updateInventory();
			} else if (kits.equals(Kits.valueOf(getKit(player).toUpperCase()))) {
				inv.setItem(1, new ItemBuilder().setNome("§bKit " + kits.getName()).setMaterial(kits.getMaterial()).finalizar());
			}
		}
		
		player.updateInventory();
	}
	
	public static boolean hasKitPermission(Player player, Kits kit) {
		AccountBukkit account = new AccountBukkit(player);
		if (player.hasPermission(kit.getTagPermission().getPermissionGroup()) || account.getBuy_kit_pvp().checkKit(kit.name()) || account.getDiaryPvP().checkKit(kit.name())) {
			return true;
		}
		return false;
	}
}
