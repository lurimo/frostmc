package br.com.frostmc.hardcoregames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.common.util.gamer.league.LeagueContager;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.player.FlyCommand;
import br.com.frostmc.hardcoregames.scoreboard.Scoreboarding;
import br.com.frostmc.hardcoregames.util.DropItem;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.combatlog.Combat;
import br.com.frostmc.hardcoregames.util.combatlog.CombatLog;
import br.com.frostmc.hardcoregames.util.kit.GladUtils;
import br.com.frostmc.hardcoregames.util.kit.hability.Gladiator;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void asd(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(null);
		if (FrostHG.getManager().getScoreboard().contains(player.getUniqueId())) {
			FrostHG.getManager().getScoreboard().remove(player.getUniqueId());
			Scoreboarding.removeScoreboard(player);
		}
		if (FrostHG.state == State.INICIANDO) {
			if (FrostHG.getManager().getJogadores().contains(player.getUniqueId())) {
				FrostHG.getManager().getJogadores().remove(player.getUniqueId());
			}
			if (FrostHG.getManager().getJogadores().size() >= 1) {
				for (Player players : FrostHG.getManager().getOnlinePlayers()) {
					Scoreboarding.updateJogadores(players, players.getScoreboard());
				}
			}
			if (FlyCommand.usingFlight.contains(player.getUniqueId())) {
				FlyCommand.usingFlight.remove(player.getUniqueId());
			}
		}
		if (FrostHG.state == State.JOGO) {
			if (Combat.inCombat(event.getPlayer().getUniqueId())) {
				CombatLog cl = Combat.combatLogs.get(player.getUniqueId());
				Player inCombat = Bukkit.getPlayer(cl.getLastHit());
				
				Gamer gamerKiller = BukkitMain.getGamerManager().getGamer(inCombat);
				Gamer gamerEntity = BukkitMain.getGamerManager().getGamer(player);

				FrostHG.getManager().kills.put(inCombat.getUniqueId(), FrostHG.getManager().kills.get(inCombat.getUniqueId()) + 1);
				gamerKiller.addKills_hg(1);
				gamerEntity.addDeaths_hg(1);

				if (Combat.combatLogs.containsKey(player.getUniqueId())) {
					Combat.combatLogs.remove(player.getUniqueId());
				}
				if (Combat.combatLogs.containsKey(inCombat.getUniqueId())) {
					Combat.combatLogs.remove(inCombat.getUniqueId());
				}

				if (Gladiator.gladArena.containsKey(player.getUniqueId())) {
					String oldArena = Gladiator.gladArena.get(player.getUniqueId());
					GladUtils glad = Gladiator.glads.get(oldArena);
					glad.teleportBack();
					DropItem.dropItems(player, glad.getBackForPlayer(player).add(0, 0.5, 0));
					glad.cancelGlad();
				}

				LeagueContager league = new LeagueContager(gamerKiller, gamerEntity);
				league.prizeLeague();

				FrostHG.getManager().getScoreboard().remove(player.getUniqueId());
				FrostHG.getManager().getAntiLogged().add(player.getUniqueId());
				FrostHG.getManager().getJogadores().remove(player.getUniqueId());
				DropItem.dropItems(player, player.getLocation());
				Scoreboarding.updateKills(inCombat, inCombat.getScoreboard());
				Scoreboarding.updateLiga(inCombat, inCombat.getScoreboard());
				if (FrostHG.getManager().getJogadores().size() >= 1) {
					for (Player players : FrostHG.getManager().getOnlinePlayers()) {
						Scoreboarding.updateJogadores(players, players.getScoreboard());
					}
				}
			} else if (Gladiator.gladArena.containsKey(player.getUniqueId())) {
				Player inCombat = Bukkit.getPlayer(Gladiator.gladArena.get(player.getUniqueId()));
				
				Gamer gamerKiller = BukkitMain.getGamerManager().getGamer(inCombat);
				Gamer gamerEntity = BukkitMain.getGamerManager().getGamer(player);

				FrostHG.getManager().kills.put(inCombat.getUniqueId(), FrostHG.getManager().kills.get(inCombat.getUniqueId()) + 1);
				gamerKiller.addKills_hg(1);
				gamerEntity.addDeaths_hg(1);

				if (Combat.combatLogs.containsKey(player.getUniqueId())) {
					Combat.combatLogs.remove(player.getUniqueId());
				}
				if (Combat.combatLogs.containsKey(inCombat.getUniqueId())) {
					Combat.combatLogs.remove(inCombat.getUniqueId());
				}

				if (Gladiator.gladArena.containsKey(player.getUniqueId())) {
					String oldArena = Gladiator.gladArena.get(player.getUniqueId());
					GladUtils glad = Gladiator.glads.get(oldArena);
					glad.teleportBack();
					DropItem.dropItems(player, glad.getBackForPlayer(player).add(0, 0.5, 0));
					glad.cancelGlad();
				}

				LeagueContager league = new LeagueContager(gamerKiller, gamerEntity);
				league.prizeLeague();
				FrostHG.getManager().getScoreboard().remove(player.getUniqueId());
				FrostHG.getManager().getAntiLogged().add(player.getUniqueId());
				FrostHG.getManager().getJogadores().remove(player.getUniqueId());
				DropItem.dropItems(player, player.getLocation());
				Scoreboarding.updateKills(inCombat, inCombat.getScoreboard());
				Scoreboarding.updateLiga(inCombat, inCombat.getScoreboard());
				FrostHG.getManager().deathMessage(player, "foi morto por " + inCombat.getName() + "(" + FrostHG.getManager().getKitAPI().getKit(inCombat) + ") usando " + FrostHG.getManager().getItem(inCombat));
				if (FrostHG.getManager().getJogadores().size() >= 1) {
					for (Player players : FrostHG.getManager().getOnlinePlayers()) {
						Scoreboarding.updateJogadores(players, players.getScoreboard());
					}
				}
			}
		}
		if (FrostHG.getManager().getJogadores().contains(player.getUniqueId())) {
			new BukkitRunnable() {
				@Override
				public void run() {
					if (!player.isOnline()) {
						FrostHG.getManager().getAntiLogged().add(player.getUniqueId());
						FrostHG.getManager().getKitAPI().removeKit(player);
						if (FrostHG.getManager().getJogadores().size() >= 1) {
							for (Player players : FrostHG.getManager().getOnlinePlayers()) {
								Scoreboarding.updateJogadores(players, players.getScoreboard());
							}
						}
						if (FrostHG.getManager().getJogadores().contains(player.getUniqueId())) {
							FrostHG.getManager().getJogadores().remove(player.getUniqueId());
						}
						FrostHG.getManager().deathMessage(player, "demorou muito para relogar e foi eliminado.");
						if (FrostHG.getManager().getJogadores().size() >= 1) {
							for (Player players : FrostHG.getManager().getOnlinePlayers()) {
								Scoreboarding.updateJogadores(players, players.getScoreboard());
							}
						}
						return;
					}
				}
			}.runTaskLater(FrostHG.getInstance(), 1200L);
		}
	}
	
}
