package br.com.frostmc.lobby.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import br.com.frostmc.core.util.Strings;
import br.com.frostmc.lobby.FrostLobby;
import br.com.frostmc.lobby.commands.staffer.BuildCommand;
import br.com.frostmc.lobby.commands.staffer.BuildCommand.BuildModes;
import br.com.frostmc.lobby.inventory.ManagerInventory;
import br.com.frostmc.lobby.scoreboard.Scoreboarding;
import br.com.frostmc.lobby.util.MessagesType;
import br.com.frostmc.lobby.util.admin.Vanish;
import br.com.frostmc.lobby.util.bossbar.BarAPI;
import br.com.frostmc.lobby.util.crates.CrateManager;
import br.com.frostmc.lobby.util.warp.WarpsAPI;
import br.com.frostmc.lobby.util.warp.WarpsAPI.Warps;

public class PlayerLoginListener implements Listener {
	
	public boolean spawned = false;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player player = e.getPlayer();
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.getActivePotionEffects().clear();
		
		player.setGameMode(GameMode.SURVIVAL);
		player.setAllowFlight(false);
		player.setFoodLevel(20);
		player.setMaxHealth(4.0D);
		player.setHealth(4.0D);

		ManagerInventory.sendItens(player);
		WarpsAPI.setWarp(player, Warps.SPAWN);
		WarpsAPI.goToWarp(player, Warps.SPAWN);
		
		if (!spawned) {
			spawned = new CrateManager().spawnItems();
		}
		
		FrostLobby.scoreboard.add(player.getUniqueId());
		Scoreboarding.setScoreboard(player);
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (FrostLobby.scoreboard.contains(players.getUniqueId())) {
				Scoreboarding.updatePlayers(players);
			}
		}
		BuildCommand.buildModes.put(player.getUniqueId(), BuildModes.OFF);
		for (int i = 1;i <100; i++) {
			player.sendMessage(" ");
		}
		player.sendMessage("");
		if(((CraftPlayer)player).getHandle().playerConnection.networkManager.getVersion() < 47) {
			player.sendMessage("§9§lDICA: §7Recomendamos o uso da versão §a1.8§7 para evitar bugs indesejáveis.");
		} else {
			player.sendMessage("§3§lCONNECT §7Você foi conectado ao Lobby com sucesso..");
		}
		BarAPI.setMessage("§fCompre ja o seu §a§lVIP§0§l» §3§l" + Strings.getWebstore().replace("https://", "").replace("/", ""));
		MessagesType.sendTitleMessage(player, "§b§lFrost§f§lNetwork", "§fSeja bem-vindo ao §3§lLOBBY");
		MessagesType.sendActionBarMessage(player, "§3§lCONNECT §fConectado com sucesso.");
		Vanish.updateVanished();
	}
	
}
