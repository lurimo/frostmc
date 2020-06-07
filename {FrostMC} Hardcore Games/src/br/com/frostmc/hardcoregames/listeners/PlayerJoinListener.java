package br.com.frostmc.hardcoregames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.ServerOptions;
import br.com.frostmc.core.data.mysql.bukkit.AccountBukkit;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.inventorys.ManagerInventory;
import br.com.frostmc.hardcoregames.scoreboard.Scoreboarding;
import br.com.frostmc.hardcoregames.timer.Timer;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.kit.Kits;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void asd(PlayerLoginEvent e) {
		Player player = e.getPlayer();
		AccountBukkit account = new AccountBukkit(player);
		if (!ServerOptions.EVENTO.isActive()) {
			if (account.getBlacklist().exists()) {
				e.setKickMessage("§4§lBLACKLIST §fVocê está impossibilitado de jogar evento, Adquira §C§lUN-BLACKLIST §fpara poder entrar jogar. §3" + Strings.getWebstore());
				e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_WHITELIST, e.getKickMessage());
				return;
			}
		}
		if (FrostHG.state == State.INVENCIVEL) {
			if (!FrostHG.getManager().getAntiLogged().contains(player.getUniqueId())) {
				if (!FrostHG.getManager().getJogadores().contains(player.getUniqueId())) {
					if (!BukkitMain.getInstance().getPermissions().isLight(player)) {
						e.setKickMessage("§6§lPARTIDA §fO torneio já iniciou, Adquira §e§lVIP §fpara poder entrar após o inicio. §3" + Strings.getWebstore());
						e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_WHITELIST, e.getKickMessage());
						return;
					} else {
						e.allow();
					}
				}
			} else {
				e.allow();
			}
		} else if (FrostHG.state == State.JOGO) {
			if (Timer.tempo < 300) {
				if (!FrostHG.getManager().getAntiLogged().contains(player.getUniqueId())) {
					if (!BukkitMain.getInstance().getPermissions().isLight(player)) {
						if (!FrostHG.getManager().getDeathMessage().containsKey(player.getUniqueId())) {
							e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER, "§6§lPARTIDA §fO torneio já iniciou, Adquira §e§lVIP §fpara poder espectar. §3"  + Strings.getWebstore());
							return;
						} else {
							e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER, FrostHG.getManager().getDeathMessage().get(player.getUniqueId()));
							return;
						}
					}
				} else {
					e.allow();
				}
			} else if (Timer.tempo <= 360) {
				if (!FrostHG.getManager().getAntiLogged().contains(player.getUniqueId())) {
					if (!BukkitMain.getInstance().getPermissions().isPrime(player)) {
						if (!FrostHG.getManager().getDeathMessage().containsKey(player.getUniqueId())) {
							e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER, "§6§lPARTIDA §fO torneio já iniciou, Adquira §e§lVIP §fpara poder espectar. §3" + Strings.getWebstore());
							return;
						} else {
							e.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER, FrostHG.getManager().getDeathMessage().get(player.getUniqueId()));
							return;
						}
					}
				} else {
					e.allow();
				}
			}
		}
	}
	
	@EventHandler
	public void asd(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(null);
		if (FrostHG.state == State.INICIANDO) {
			player.setAllowFlight(false);
			player.setFlying(false);
			player.setGameMode(GameMode.SURVIVAL);
			FrostHG.getManager().setupPlayer(player);
			FrostHG.getManager().getKitAPI().setKit(player, Kits.NENHUM);
			FrostHG.getManager().getScoreboard().add(player.getUniqueId());
			player.teleport(FrostHG.getManager().getSpawn());
			ManagerInventory.sendItems(player);
			if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId())) {
				FrostHG.getManager().getScoreboard().add(player.getUniqueId());
			}
			if (!FrostHG.getManager().getJogadores().contains(player.getUniqueId())) {
				FrostHG.getManager().getJogadores().add(player.getUniqueId());
			}
			for (int integer = 1; integer < 100; integer++) {
				player.sendMessage(" ");
			}
			player.teleport(new Location(Bukkit.getWorld("world"), 62.5, 137, 61.5));
			Scoreboarding.setScoreboard(player);
			for (Player players : FrostHG.getManager().getOnlinePlayers()) {
				Scoreboarding.updateJogadores(players, players.getScoreboard());
			}
		} else if (FrostHG.state == State.INVENCIVEL) {
			if (!FrostHG.getManager().getAntiLogged().contains(player.getUniqueId()) && !FrostHG.getManager().getDeathMessage().containsKey(player.getUniqueId())) {
				if (FrostHG.getManager().getJogadores().contains(player.getUniqueId())) {
					if (FrostHG.getManager().getKitAPI().getKit(player) != null) {
						FrostHG.getManager().getKitAPI().setKit(player, Kits.valueOf(FrostHG.getManager().getKitAPI().getKit(player)));
					} else {
						FrostHG.getManager().getKitAPI().setKit(player, Kits.NENHUM);
					}
					player.updateInventory();
				} else {
					if (!FrostHG.getManager().getJogadores().contains(player.getUniqueId())) {
						FrostHG.getManager().getJogadores().add(player.getUniqueId());
					}
					FrostHG.getManager().kills.put(player.getUniqueId(), 0);
					FrostHG.getManager().setupPlayer(player);
					FrostHG.getManager().getKitAPI().setKit(player, Kits.NENHUM);
					player.updateInventory();
				}
				if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId())) {
					FrostHG.getManager().getScoreboard().add(player.getUniqueId());
				}
				Scoreboarding.setScoreboard(player);
			} else {
				FrostHG.getManager().setEspectador(player);
				if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId())) {
					FrostHG.getManager().getScoreboard().add(player.getUniqueId());
				}
				Scoreboarding.setScoreboard(player);
			}
			for (Player players : FrostHG.getManager().getOnlinePlayers()) {
				Scoreboarding.updateJogadores(players, players.getScoreboard());
			}
		} else if (FrostHG.state == State.JOGO) {
			if (Timer.tempo < 300) {
				if (BukkitMain.getInstance().getPermissions().isLight(player)) {
					if (!FrostHG.getManager().getAntiLogged().contains(player.getUniqueId()) && !FrostHG.getManager().getDeathMessage().containsKey(player.getUniqueId())) {
						if (FrostHG.getManager().getJogadores().contains(player.getUniqueId())) {
							if (FrostHG.getManager().getKitAPI().getKit(player) != null) {
								FrostHG.getManager().getKitAPI().setKit(player, Kits.valueOf(FrostHG.getManager().getKitAPI().getKit(player)));
							} else {
								FrostHG.getManager().getKitAPI().setKit(player, Kits.NENHUM);
							}
							player.updateInventory();
						} else {
							if (!FrostHG.getManager().getJogadores().contains(player.getUniqueId())) {
								FrostHG.getManager().getJogadores().add(player.getUniqueId());
							}
							FrostHG.getManager().kills.put(player.getUniqueId(), 0);
							FrostHG.getManager().setupPlayer(player);
							FrostHG.getManager().getKitAPI().setKit(player, Kits.NENHUM);
							player.updateInventory();
						}
						if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId())) {
							FrostHG.getManager().getScoreboard().add(player.getUniqueId());
						}
						Scoreboarding.setScoreboard(player);
					} else {
						FrostHG.getManager().setEspectador(player);
						if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId())) {
							FrostHG.getManager().getScoreboard().add(player.getUniqueId());
						}
						Scoreboarding.setScoreboard(player);
					}
				}
				for (Player players : FrostHG.getManager().getOnlinePlayers()) {
					Scoreboarding.updateJogadores(players, players.getScoreboard());
				}
			} else if (Timer.tempo <= 360) {
				if (BukkitMain.getInstance().getPermissions().isPrime(player)) {
					if (!FrostHG.getManager().getAntiLogged().contains(player.getUniqueId()) && !FrostHG.getManager().getDeathMessage().containsKey(player.getUniqueId())) {
						if (FrostHG.getManager().getJogadores().contains(player.getUniqueId())) {
							player.updateInventory();
							if (FrostHG.getManager().getKitAPI().getKit(player) != null) {
								FrostHG.getManager().getKitAPI().setKit(player, Kits.valueOf(FrostHG.getManager().getKitAPI().getKit(player)));
							} else {
								FrostHG.getManager().getKitAPI().setKit(player, Kits.NENHUM);
							}
						} else {
							FrostHG.getManager().getJogadores().add(player.getUniqueId());
							FrostHG.getManager().kills.put(player.getUniqueId(), 0);
							FrostHG.getManager().setupPlayer(player);
							FrostHG.getManager().getKitAPI().setKit(player, Kits.NENHUM);
						}
						if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId())) {
							FrostHG.getManager().getScoreboard().add(player.getUniqueId());
						}
						Scoreboarding.setScoreboard(player);
					} else {
						FrostHG.getManager().setEspectador(player);
						if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId())) {
							FrostHG.getManager().getScoreboard().add(player.getUniqueId());
						}
						Scoreboarding.setScoreboard(player);
					}
				}
			} else {
				FrostHG.getManager().setEspectador(player);
				if (!FrostHG.getManager().getScoreboard().contains(player.getUniqueId())) {
					FrostHG.getManager().getScoreboard().add(player.getUniqueId());
				}
				Scoreboarding.setScoreboard(player);
			}
			for (Player players : FrostHG.getManager().getOnlinePlayers()) {
				Scoreboarding.updateJogadores(players, players.getScoreboard());
			}
		}
	}
	
}
