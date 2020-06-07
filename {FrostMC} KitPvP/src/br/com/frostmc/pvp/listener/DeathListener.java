package br.com.frostmc.pvp.listener;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.common.util.gamer.league.LeagueContager;
import br.com.frostmc.pvp.FrostPvP;
import br.com.frostmc.pvp.scoreboard.Scoreboarding;
import br.com.frostmc.pvp.util.admin.AdminManager;
import br.com.frostmc.pvp.util.combatlog.Combat;
import br.com.frostmc.pvp.util.hability.kits.Gladiator;
import br.com.frostmc.pvp.util.hability.kits.glad.GladUtils;
import br.com.frostmc.pvp.util.protection.Protection;
import br.com.frostmc.pvp.util.warp.WarpManager;
import br.com.frostmc.pvp.util.warp.WarpsAPI;
import br.com.frostmc.pvp.util.warp.WarpsAPI.Warps;

public class DeathListener implements Listener {

	@SuppressWarnings("rawtypes")
	public int itemsInInventory(Inventory inventory, Material[] search) {
		List wanted = Arrays.asList(search);
		int found = 0;
		ItemStack[] arrayOfItemStack;
		int j = (arrayOfItemStack = inventory.getContents()).length;
		for (int i = 0; i < j; i++) {
			ItemStack item = arrayOfItemStack[i];
			if ((item != null) && (wanted.contains(item.getType()))) {
				found += item.getAmount();
			}
		}
		return found;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void asd(PlayerDeathEvent event) {
		event.setDeathMessage(null);
		Player killer = (Player) event.getEntity().getKiller();
		Player entity = (Player) event.getEntity();
		if (event.getEntity() instanceof Player) {
			
			new BukkitRunnable() {
				public void run() {
					event.getEntity().spigot().respawn();
				}
			}.runTaskLater(FrostPvP.getPlugin(), 1L);
			
			Gamer gamerEntity = BukkitMain.getGamerManager().getGamer(entity.getUniqueId());
			
			if (WarpsAPI.isInWarp(entity, Warps.LAVACHALLENGE)) {
				entity.sendMessage("�4�lLAVA �fEssa warp n�o afetar� suas mortes.");
				return;
			}
			
			if (killer == null) {
				entity.sendMessage("�c�lMORREU �fVoc� morreu para ninguem.");
			} else {
				entity.sendMessage(" ");
				entity.sendMessage("�c�lVOC� FOI MORTO");
				entity.sendMessage("�7Voc� morreu para o jogador �c" + killer.getName() + "�7.");
				entity.sendMessage(" ");
			}
			
			gamerEntity.addDeaths_pvp(1);
			gamerEntity.resetKillstreakAdversary_pvp(killer);
			
			if (event.getEntity().getKiller() instanceof Player) {
				Gamer gamerKiller = BukkitMain.getGamerManager().getGamer(killer.getUniqueId());
				if (Gladiator.gladArena.containsKey(entity.getUniqueId())) {
					killer.sendMessage(" ");
					killer.sendMessage("�a�lVOC� MATOU");
					killer.sendMessage("�7Voc� matou o jogador �c" + entity.getName() + "�7.");
					killer.sendMessage(" ");
					String oldArena = Gladiator.gladArena.get(killer.getUniqueId());
					GladUtils glad = Gladiator.glads.get(oldArena);
					glad.teleportBack();
					glad.cancelGlad();
				}
				if (WarpsAPI.isInWarp(killer, Warps.ONEVSONE)) {
					int sopasEntity = itemsInInventory(entity.getInventory(), new Material[] { Material.MUSHROOM_SOUP });
					killer.sendMessage("�a�lMATOU �fVoc� matou o jogador �a" + killer.getName() + " �fe ele ficou com �a" + sopasEntity + " �fsopas restantes.");
					killer.setGameMode(GameMode.ADVENTURE);
					killer.setHealth(20.0D);
					killer.getInventory().setArmorContents(null);
					killer.getActivePotionEffects().clear();
					Player[] arrayOfPlayer;
					int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
					for (int i = 0; i < j; i++) {
						Player all = arrayOfPlayer[i];
						if (AdminManager.isAdmin(all)) {
							killer.showPlayer(all);
						}
					}
					Protection.setProtection(killer);
					WarpsAPI.setWarp(killer, Warps.ONEVSONE);
					WarpsAPI.goToWarp(killer, Warps.ONEVSONE);
				} else {
					killer.sendMessage("�a�lMATOU �fVoc� matou o jogador �a" + entity.getName() + "�f.");
				}
				
				LeagueContager league = new LeagueContager(gamerKiller, gamerEntity);
				league.prizeLeague();
				gamerKiller.addMoedas(100);
				
				gamerKiller.addKills_pvp(1);
				gamerKiller.addKillstreak_pvp(1);
				
				Scoreboarding.updateKills(killer, killer.getScoreboard());
				Scoreboarding.updateLiga(killer, killer.getScoreboard());
				Scoreboarding.updateModes(killer, killer.getScoreboard());
				Scoreboarding.updateXp(killer, killer.getScoreboard());
				Scoreboarding.updateStreak(killer, killer.getScoreboard());
				return;
			}
			
		}
	}

	@SuppressWarnings({ "static-access", "deprecation" })
	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent event) {
		Player entity = event.getPlayer();
		
		Gamer gamerEntity = BukkitMain.getGamerManager().getGamer(entity.getUniqueId());
		
		if (WarpsAPI.isInWarp(entity, Warps.SPAWN))
			return;

		if (!Combat.inCombat(entity.getUniqueId()))
			return;
		
		Player combatLog = Bukkit.getPlayer(Combat.combatLogs.get(entity.getUniqueId()).getLastHit());
		Player combatGlad = Bukkit.getPlayer(Gladiator.gladFighting.get(entity));
		Player killer = (combatGlad != null ? combatGlad : combatLog);
		Gamer gamerKiller = BukkitMain.getGamerManager().getGamer(killer.getUniqueId());
		gamerEntity.addDeaths_pvp(1);
		gamerEntity.resetKillstreakAdversary_pvp(killer);
		
		if (Gladiator.gladArena.containsKey(entity.getUniqueId())) {
			killer.sendMessage("�a�lMATOU �fVoc� matou o jogador �a" + entity.getName() + "�f.");
			String oldArena = Gladiator.gladArena.get(killer.getUniqueId());
			GladUtils glad = Gladiator.glads.get(oldArena);
			glad.teleportBack();
			glad.cancelGlad();
		}
		
		if (WarpsAPI.isInWarp(entity, Warps.ONEVSONE)) {
			int sopasKiller = itemsInInventory(killer.getInventory(), new Material[] { Material.MUSHROOM_SOUP });
			entity.sendMessage("�c�lMORREU �fVoc� morreu para o jogador �c" + killer.getName() + " �fe ele ficou com �e" + sopasKiller + " �fsopas restantes.");
			killer.setGameMode(GameMode.ADVENTURE);
			killer.setHealth(20.0D);
			killer.getInventory().setArmorContents(null);
			killer.getActivePotionEffects().clear();
			Player[] arrayOfPlayer;
			int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
			for (int i = 0; i < j; i++) {
				Player all = arrayOfPlayer[i];
				if (AdminManager.isAdmin(all)) {
					entity.showPlayer(all);
				}
			}
			Protection.setProtection(killer);
			WarpsAPI.setWarp(killer, WarpsAPI.Warps.ONEVSONE);
			WarpsAPI.goToWarp(killer, WarpsAPI.Warps.ONEVSONE);
		}
		
		killer.sendMessage("�a�lMATOU �fVoc� matou o jogador �a" + entity.getName() + "�f.");
		LeagueContager league = new LeagueContager(gamerKiller, gamerEntity);
		league.prizeLeague();
		
		gamerKiller.addKills_pvp(1);
		gamerKiller.addKillstreak_pvp(1);
		
		Scoreboarding.updateKills(killer, killer.getScoreboard());
		Scoreboarding.updateLiga(killer, killer.getScoreboard());
		Scoreboarding.updateModes(killer, killer.getScoreboard());
		Scoreboarding.updateXp(killer, killer.getScoreboard());
		Scoreboarding.updateStreak(killer, killer.getScoreboard());
	}

	@EventHandler
	public void asd(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		new BukkitRunnable() {
			public void run() {
				if (WarpsAPI.isInWarp(player, Warps.FPS)) {
					WarpManager.send(player, Warps.FPS);
				} else if (WarpsAPI.isInWarp(player, Warps.ONEVSONE)) {
					WarpManager.send(player, Warps.ONEVSONE);
				} else if (WarpsAPI.isInWarp(player, Warps.LAVACHALLENGE)) {
					WarpManager.send(player, Warps.LAVACHALLENGE);
				} else if (WarpsAPI.isInWarp(player, Warps.ARENA)) {
					WarpManager.send(player, Warps.SPAWN);
				}
			}
		}.runTaskLater(FrostPvP.getPlugin(), 5L);
	}

}
