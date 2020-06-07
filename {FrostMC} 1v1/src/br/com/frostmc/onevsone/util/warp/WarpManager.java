package br.com.frostmc.onevsone.util.warp;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import br.com.frostmc.onevsone.battle1v1.OnevsoneManager;
import br.com.frostmc.onevsone.scoreboard.Scoreboarding;
import br.com.frostmc.onevsone.util.admin.Vanish;
import br.com.frostmc.onevsone.util.protection.Protection;
import br.com.frostmc.onevsone.util.warp.WarpsAPI.Warps;

public class WarpManager {
	
	public static void send(Player player) {
		player.getActivePotionEffects().clear();
		player.getInventory().setArmorContents(null);
		player.getInventory().remove(Material.COMPASS);
		player.updateInventory();
		player.setGameMode(GameMode.SURVIVAL);
		player.setAllowFlight(false);
		player.setHealth(20.0D);
		player.setFoodLevel(20);
		player.setFireTicks(0);
		player.setLevel(0);
		Protection.setProtection(player);
		player.getInventory().clear();
		OnevsoneManager.fighting.remove(player.getUniqueId());
		OnevsoneManager.opponent.remove(player.getUniqueId());
		WarpsAPI.setWarp(player, Warps.SPAWN);
		WarpsAPI.goToWarp(player, Warps.SPAWN);
		OnevsoneManager.sendItens(player);
		Scoreboarding.setScoreboard(player);
		Vanish.updateVanished();
	}
}
