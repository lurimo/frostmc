package br.com.frostmc.gladiator.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.common.util.gamer.league.LeagueContager;
import br.com.frostmc.gladiator.FrostGladiator;
import br.com.frostmc.gladiator.battle1v1.GladiatorManager;
import br.com.frostmc.gladiator.scoreboard.Scoreboarding;
import br.com.frostmc.gladiator.util.admin.AdminManager;
import br.com.frostmc.gladiator.util.warp.WarpManager;
import br.com.frostmc.gladiator.util.warp.WarpsAPI;
import br.com.frostmc.gladiator.util.warp.WarpsAPI.Warps;

public class MorreuListener implements Listener {
	
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
			}.runTaskLater(FrostGladiator.getInstance(), 1L);
			
			Gamer gamerEntity = BukkitMain.getGamerManager().getGamer(entity.getUniqueId());
			
			if (WarpsAPI.isInWarp(entity, Warps.SPAWN)) {
				GladiatorManager.fighting.remove(entity.getUniqueId());
				GladiatorManager.opponent.remove(entity.getUniqueId());
				entity.sendMessage("�c�lMORREU �fVoc� morreu para o jogador �c" + killer.getName() + " �fe ele ficou com �e");
				killer.setGameMode(GameMode.ADVENTURE);
				killer.setHealth(20.0D);
				killer.getInventory().setArmorContents(null);
				killer.getActivePotionEffects().clear();
				GladiatorManager.fighting.remove(killer.getUniqueId());
				GladiatorManager.opponent.remove(killer.getUniqueId());
				Player[] arrayOfPlayer;
				int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
				for (int i = 0; i < j; i++) {
					Player all = arrayOfPlayer[i];
					if (AdminManager.isAdmin(all)) {
						entity.showPlayer(all);
					}
				}
				new BukkitRunnable() {
					public void run() {
						WarpManager.send(entity);
						cancel();
					}
				}.runTaskLater(FrostGladiator.getInstance(), 5L);
			} else if (killer == null) {
				entity.sendMessage("�c�lMORREU �fVoc� morreu para ninguem.");
			}
			
			gamerEntity.addDefeat_gladiator(1);
			gamerEntity.resetWinstreakAdversary_gladiator(killer);
			
			if (event.getEntity().getKiller() instanceof Player) {
				Gamer gamerKiller = BukkitMain.getGamerManager().getGamer(killer.getUniqueId());
				if (WarpsAPI.isInWarp(killer, Warps.SPAWN)) {
					killer.sendMessage("�a�lMATOU �fVoc� matou o jogador �a" + killer.getName() + " �fe ele ficou com �a");
					killer.setGameMode(GameMode.ADVENTURE);
					killer.setHealth(20.0D);
					killer.getInventory().setArmorContents(null);
					killer.getActivePotionEffects().clear();
					GladiatorManager.fighting.remove(killer.getUniqueId());
					GladiatorManager.opponent.remove(killer.getUniqueId());
					WarpManager.send(killer);
				} else {
					killer.sendMessage("�a�lMATOU �fVoc� matou o jogador �a" + entity.getName() + "�f.");
				}
				
				LeagueContager league = new LeagueContager(gamerKiller, gamerEntity);
				league.prizeLeague();
				
				gamerKiller.addVictory_gladiator(1);
				gamerKiller.addWinstreak_gladiator(1);
				
				Scoreboarding.updateOpponent(killer);
				Scoreboarding.updateStats(killer);
				return;
			}
		}
	}

	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent event) {
		Player entity = event.getPlayer();
		
		Gamer gamerEntity = BukkitMain.getGamerManager().getGamer(entity.getUniqueId());
		
		if (!GladiatorManager.fighting.containsKey(entity.getUniqueId()))
			return;
		
		Player killer = Bukkit.getPlayer(GladiatorManager.fighting.get(entity.getUniqueId()).getName());
		Gamer gamerKiller = BukkitMain.getGamerManager().getGamer(killer.getUniqueId());
		gamerEntity.addDefeat_gladiator(1);
		gamerEntity.resetWinstreakAdversary_gladiator(killer);

		GladiatorManager.fighting.remove(entity.getUniqueId());
		GladiatorManager.opponent.remove(entity.getUniqueId());
		entity.sendMessage("�c�lMORREU �fVoc� morreu para o jogador �c" + killer.getName() + " �fe ele ficou com �e");
		killer.setGameMode(GameMode.ADVENTURE);
		killer.setHealth(20.0D);
		killer.getInventory().setArmorContents(null);
		killer.getActivePotionEffects().clear();
		GladiatorManager.fighting.remove(killer.getUniqueId());
		GladiatorManager.opponent.remove(killer.getUniqueId());
		Player[] arrayOfPlayer;
		int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
		for (int i = 0; i < j; i++) {
			Player all = arrayOfPlayer[i];
			if (AdminManager.isAdmin(all)) {
				entity.showPlayer(all);
			}
		}
		WarpManager.send(killer);
		
		killer.sendMessage("�a�lMATOU �fVoc� matou o jogador �a" + entity.getName() + "�f.");
		LeagueContager league = new LeagueContager(gamerKiller, gamerEntity);
		league.prizeLeague();
		
		gamerKiller.addVictory_gladiator(1);
		gamerKiller.addWinstreak_gladiator(1);
		
		Scoreboarding.updateOpponent(killer);
		Scoreboarding.updateStats(killer);
	}
	
	@EventHandler
	public void asd(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		new BukkitRunnable() {
			public void run() {
				GladiatorManager.removeGladiator(player);
			}
		}.runTaskLater(FrostGladiator.getInstance(), 10L);
	}

}