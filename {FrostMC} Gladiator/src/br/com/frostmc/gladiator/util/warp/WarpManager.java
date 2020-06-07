package br.com.frostmc.gladiator.util.warp;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import br.com.frostmc.gladiator.battle1v1.GladiatorManager;
import br.com.frostmc.gladiator.scoreboard.Scoreboarding;
import br.com.frostmc.gladiator.util.admin.Vanish;
import br.com.frostmc.gladiator.util.protection.Protection;
import br.com.frostmc.gladiator.util.warp.WarpsAPI.Warps;

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
		GladiatorManager.fighting.remove(player.getUniqueId());
		GladiatorManager.opponent.remove(player.getUniqueId());
		GladiatorManager.sendItens(player);
		WarpsAPI.setWarp(player, Warps.SPAWN);
		WarpsAPI.goToWarp(player, Warps.SPAWN);
		Scoreboarding.setScoreboard(player);
		Vanish.updateVanished();
	}
}
