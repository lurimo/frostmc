package br.com.frostmc.onevsone.listeners;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.common.util.gamer.league.LeagueContager;
import br.com.frostmc.onevsone.Frost1v1;
import br.com.frostmc.onevsone.battle1v1.OnevsoneManager;
import br.com.frostmc.onevsone.scoreboard.Scoreboarding;
import br.com.frostmc.onevsone.util.warp.WarpManager;
import br.com.frostmc.onevsone.util.warp.WarpsAPI;
import br.com.frostmc.onevsone.util.warp.WarpsAPI.Warps;

public class MorreuListener implements Listener {

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
		Player killer = (Player) event.getEntity().getKiller();
		Player entity = (Player) event.getEntity();
		if (event.getEntity() instanceof Player) {
			
			new BukkitRunnable() {
				public void run() {
					event.getEntity().spigot().respawn();
				}
			}.runTaskLater(Frost1v1.getPlugin(), 1L);
			
			Gamer gamerEntity = BukkitMain.getGamerManager().getGamer(entity.getUniqueId());
			
			if (WarpsAPI.isInWarp(entity, Warps.SPAWN)) {
				OnevsoneManager.health.put(entity.getUniqueId(), killer.getHealth());
				OnevsoneManager.health.put(killer.getUniqueId(), entity.getHealth());
				OnevsoneManager.soup.put(killer.getUniqueId(), itemsInInventory(entity.getInventory(), new Material[] { Material.MUSHROOM_SOUP }));
				OnevsoneManager.soup.put(entity.getUniqueId(), itemsInInventory(killer.getInventory(), new Material[] { Material.MUSHROOM_SOUP }));
				entity.sendMessage("�8[�c�l1v1�8] �fVoc� perdeu o 1v1 contra �c" + killer.getName() + " �fele ficou com �c" + NumberFormat.getInstance().format(OnevsoneManager.health.get(entity.getUniqueId()) / 2) + " cora��es �fe �c" + OnevsoneManager.soup.get(entity.getUniqueId()) + " " + (OnevsoneManager.soup.get(entity.getUniqueId()) > 1 ? "sopas" : "sopa") + " �frestantes.");
				new BukkitRunnable() {
					public void run() {
						OnevsoneManager.health.remove(entity.getUniqueId());
						OnevsoneManager.soup.remove(entity.getUniqueId());
						WarpManager.send(entity);
					}
				}.runTaskLater(Frost1v1.getPlugin(), 5L);
			} else if (killer == null) {
				entity.sendMessage("�8[�c�l1v1�8] �fVoc� morreu para ningu�m.");
			}
			
			gamerEntity.addDefeat_1v1(1);
			gamerEntity.resetWinstreakAdversary_1v1(killer);
			
			if (event.getEntity().getKiller() instanceof Player) {
				Gamer gamerKiller = BukkitMain.getGamerManager().getGamer(killer.getUniqueId());
				if (WarpsAPI.isInWarp(killer, Warps.SPAWN)) {
					killer.sendMessage("�8[�a�l1v1�8] �fVoc� venceu o 1v1 contra �a" + entity.getName() + " �fele ficou com �a" + OnevsoneManager.soup.get(killer.getUniqueId()) + " " + (OnevsoneManager.soup.get(killer.getUniqueId()) > 1 ? "sopas" : "sopa") + " �frestantes.");
					OnevsoneManager.health.remove(killer.getUniqueId());
					OnevsoneManager.soup.remove(killer.getUniqueId());
					WarpManager.send(killer);
				} else {
					killer.sendMessage("�8[�a�l1v1�8] �fVoc� matou o jogador �a" + entity.getName() + "�f.");
				}
				
				LeagueContager league = new LeagueContager(gamerKiller, gamerEntity);
				league.prizeLeague();
				
				gamerKiller.addVictory_1v1(1);
				gamerKiller.addWinstreak_1v1(1);
				
				Scoreboarding.updateOpponent(killer);
				Scoreboarding.updateStats(killer);
				return;
			}
			
		}
	}

	@SuppressWarnings({ "static-access", "deprecation" })
	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent event) {
		Player entity = event.getPlayer();
		
		Gamer gamerEntity = BukkitMain.getGamerManager().getGamer(entity.getUniqueId());
		
		if (!OnevsoneManager.fighting.containsKey(entity.getUniqueId()))
			return;
		
		Player killer = Bukkit.getPlayer(OnevsoneManager.fighting.get(entity.getUniqueId()).getName());
		Gamer gamerKiller = BukkitMain.getGamerManager().getGamer(killer.getUniqueId());
		gamerEntity.addDefeat_1v1(1);
		gamerEntity.resetWinstreakAdversary_1v1(killer);

		OnevsoneManager.fighting.remove(entity.getUniqueId());
		OnevsoneManager.opponent.remove(entity.getUniqueId());
		OnevsoneManager.fighting.remove(killer.getUniqueId());
		OnevsoneManager.opponent.remove(killer.getUniqueId());
		
		OnevsoneManager.health.remove(killer.getUniqueId());
		OnevsoneManager.soup.remove(killer.getUniqueId());
		WarpManager.send(killer);
		killer.sendMessage("�8[�a�l1v1�8] �fVoc� venceu o 1v1 contra �a" + entity.getName() + " �fpois o mesmo deslogou em batalha.");
		LeagueContager league = new LeagueContager(gamerKiller, gamerEntity);
		league.prizeLeague();
		
		gamerKiller.addVictory_1v1(1);
		gamerKiller.addWinstreak_1v1(1);
		
		Scoreboarding.updateOpponent(killer);
		Scoreboarding.updateStats(killer);
	}

}