package br.com.frostmc.pvp.util.warp;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.pvp.inventory.ManagerInventory;
import br.com.frostmc.pvp.scoreboard.Scoreboarding;
import br.com.frostmc.pvp.util.TeleportServer;
import br.com.frostmc.pvp.util.admin.AdminManager;
import br.com.frostmc.pvp.util.admin.Vanish;
import br.com.frostmc.pvp.util.hability.KitAPI;
import br.com.frostmc.pvp.util.protection.Protection;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class WarpManager implements Listener {
	
	public static void send(Player player, Warps warp) {
		KitAPI.removeKit(player);
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
		if (!AdminManager.isAdmin(player)) {
			WarpsAPI.setWarp(player, warp);
			WarpsAPI.goToWarp(player, warp);
		} else {
			WarpsAPI.setWarp(player, warp);
			WarpsAPI.goToWarp(player, warp);
			player.sendMessage("§a§lWARP §fVocê está monitorando na warp §e§l" + WarpsAPI.getWarp(player));
			return;
		}
		switch(warp) {
		case SPAWN:
			player.getInventory().clear();
			ManagerInventory.sendItens(player);
			break;
		case ARENA:
			break;
		case FPS:
			player.getInventory().clear();
			player.getInventory().setItem(13, new ItemBuilder().setNome("§4Cogumelo Vermelho").setMaterial(Material.RED_MUSHROOM).setQuantia(64).finalizar());
			player.getInventory().setItem(14, new ItemBuilder().setNome("§6Cogumelo Marrom").setMaterial(Material.BROWN_MUSHROOM).setQuantia(64).finalizar());
			player.getInventory().setItem(15, new ItemBuilder().setNome("§8Pote").setMaterial(Material.BOWL).setQuantia(64).finalizar());
			
			for(int i = 0; i < 34; i++) {
				player.getInventory().addItem(new ItemBuilder().setMaterial(Material.MUSHROOM_SOUP).finalizar());
			}
			player.getInventory().setItem(0, new ItemBuilder().setNome("§eEspada").setMaterial(Material.WOOD_SWORD).addEncantamento(Enchantment.DAMAGE_ALL).finalizar());
			player.updateInventory();
			break;
		case LAVACHALLENGE:
			player.getInventory().clear();
			player.getInventory().setItem(13, new ItemBuilder().setNome("§4Cogumelo Vermelho").setMaterial(Material.RED_MUSHROOM).setQuantia(64).finalizar());
			player.getInventory().setItem(14, new ItemBuilder().setNome("§6Cogumelo Marrom").setMaterial(Material.BROWN_MUSHROOM).setQuantia(64).finalizar());
			player.getInventory().setItem(15, new ItemBuilder().setNome("§8Pote").setMaterial(Material.BOWL).setQuantia(64).finalizar());
			
			for(int i = 0; i < 34; i++) {
				player.getInventory().addItem(new ItemBuilder().setMaterial(Material.MUSHROOM_SOUP).finalizar());
			}
			player.getInventory().setItem(0, new ItemBuilder().setNome("§eEspada").setMaterial(Material.STONE_SWORD).finalizar());
			player.updateInventory();
			break;
		case ONEVSONE:
			TeleportServer.connectPlayer(player, ServersType.ONEVSONE);
			break;
		}
		Scoreboarding.setScoreboard(player);
		Vanish.updateVanished();
	}
	
	@EventHandler
	public void asd(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(WarpsAPI.isInWarp(player, Warps.FPS) && Protection.hasProtection(player) && event.getTo().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.STONE)) {
			for(int i = 0; i < 50; i++) {
				Protection.removeProtection(player);
			}
		}
		if(WarpsAPI.isInWarp(player, Warps.LAVACHALLENGE) && Protection.hasProtection(player) && event.getTo().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.IRON_BLOCK)) {
			Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
			gamer.addXp(2);
			gamer.addLava_easy_pvp(1);
			player.sendMessage("§9§lXP §fVocê ganhou §9§l2XP");
			player.sendMessage("§4§lCHALLENGE §fVocê completou o nivel §aFácil§f.");
			WarpManager.send(player, Warps.LAVACHALLENGE);
			Scoreboarding.updateXp(player, player.getScoreboard());
			Scoreboarding.updateLava_easy(player, player.getScoreboard());
			return;
		} else if(WarpsAPI.isInWarp(player, Warps.LAVACHALLENGE) && Protection.hasProtection(player) && event.getTo().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.GOLD_BLOCK)) {
			Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
			gamer.addXp(5);
			gamer.addLava_medium_pvp(1);
			player.sendMessage("§9§lXP §fVocê ganhou §9§l5XP");
			player.sendMessage("§4§lCHALLENGE §fVocê completou o nivel §eMédio§f.");
			WarpManager.send(player, Warps.LAVACHALLENGE);
			Scoreboarding.updateXp(player, player.getScoreboard());
			Scoreboarding.updateLava_medium(player, player.getScoreboard());
			return;
		} else if(WarpsAPI.isInWarp(player, Warps.LAVACHALLENGE) && Protection.hasProtection(player) && event.getTo().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.DIAMOND_BLOCK)) {
			Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
			gamer.addXp(10);
			gamer.addLava_hard_pvp(1);
			player.sendMessage("§9§lXP §fVocê ganhou §9§l10XP");
			player.sendMessage("§4§lCHALLENGE §fVocê completou o nivel §cDíficil§f.");
			WarpManager.send(player, Warps.LAVACHALLENGE);
			Scoreboarding.updateXp(player, player.getScoreboard());
			Scoreboarding.updateLava_hard(player, player.getScoreboard());
			return;
		} else if(WarpsAPI.isInWarp(player, Warps.LAVACHALLENGE) && Protection.hasProtection(player) && event.getTo().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.EMERALD_BLOCK)) {
			Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
			gamer.addXp(20);
			gamer.addLava_extreme_pvp(1);
			player.sendMessage("§9§lXP §fVocê ganhou §9§l20XP");
			player.sendMessage("§4§lCHALLENGE §fVocê completou o nivel §4Extreme§f.");
			Bukkit.broadcastMessage("§8[§6§lLAVA§8] §f" + player.getName() + " completou o nivel §4EXTREME §fda Lava Challenge!");
			WarpManager.send(player, Warps.LAVACHALLENGE);
			Scoreboarding.updateXp(player, player.getScoreboard());
			Scoreboarding.updateLava_extreme(player, player.getScoreboard());
			return;
		}
	}
}
