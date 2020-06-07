package br.com.frostmc.hardcoregames.timer;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.scoreboard.Scoreboarding;
import br.com.frostmc.hardcoregames.timer.feast.Feast;
import br.com.frostmc.hardcoregames.timer.minifeast.MiniFeast;
import br.com.frostmc.hardcoregames.util.FinalBattle;
import br.com.frostmc.hardcoregames.util.Schematic;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.TeleportServer;
import br.com.frostmc.hardcoregames.util.boosbar.BarAPI;
import br.com.frostmc.hardcoregames.util.hologram.hologram.FeastHologram;
import br.com.frostmc.hardcoregames.util.kit.hability.Madman;

public class Timer {

	public static Integer task = null;
	public static int tempo = 300;
	
	@SuppressWarnings("deprecation")
	public static void iniciarTimer() {
		task = Integer.valueOf(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(FrostHG.getInstance(), new Runnable() {
			public void run() {
				for (Player ons : FrostHG.getManager().getAliveGamers())
					FrostHG.getManager().checkBorder(ons, 501);
				if (FrostHG.state == State.INICIANDO) {
					Bukkit.getWorlds().get(0).setTime(0);
					Bukkit.getWorlds().get(0).setStorm(false);
					Bukkit.getWorlds().get(0).setThundering(false);
					if (FrostHG.getManager().getJogadores().size() <= 5) {
						tempo = 300;
					} else {
						tempo--;
						if (tempo == 0) {
							if (FrostHG.getManager().getJogadores().size() <= 5) {
								tempo = 120;
								Bukkit.getWorlds().get(0).setTime(0);
								Bukkit.getWorlds().get(0).setStorm(false);
								Bukkit.getWorlds().get(0).setThundering(false);
								sendMSG("§6§lPARTIDA §fPrecisamos de no mínimo §a5 jogadores §fpara iniciar!");
								return;
							}
							sendMSG("§6§lPARTIDA §fO torneio foi iniciado!");
							FrostHG.getManager().startInvencivel();
							return;
						}
						checkMSG();
					}
					for (Player ons : Bukkit.getOnlinePlayers()) {
						BarAPI.removeBar(ons);
						BarAPI.setMessage(ons, "§6Partida irá iniciar em: " + FrostHG.getManager().getTimerChat(tempo));
						Scoreboarding.updateTimer(ons, ons.getScoreboard());
						Scoreboarding.setTab(ons);
					}
					return;
				} else if (FrostHG.state == State.INVENCIVEL) {
					tempo--;
					if (tempo == 0) {
						for (Player ons : Bukkit.getOnlinePlayers()) {
							 Scoreboarding.setScoreboard(ons);
						     ons.playSound(ons.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
						     if ((ons.getLocation().getBlockY() > 128) && (FrostHG.getManager().isJogador(ons))) {
						    	  ons.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 400, 4));
						    	  ons.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 400, 4));
						    	  ons.setFireTicks(400);
						     }
						}
						FrostHG.getManager().startGame();
						sendMSG("§6§lPARTIDA §fA invencibilidade acabou!");
						return;
					}
					checkMSG();
					for (Player ons : Bukkit.getOnlinePlayers()) {
						BarAPI.removeBar(ons);
						BarAPI.setMessage(ons, "§6Invencibilidade acaba em: " + FrostHG.getManager().getTimerFormat(tempo));
						Scoreboarding.updateTimer(ons, ons.getScoreboard());
						Scoreboarding.setTab(ons);
					}
					return;
				} else if (FrostHG.state == State.JOGO) {
					tempo++;
					
					if (tempo % 360 == 0 && tempo >= 2) {
						MiniFeast.spawnMinifeast();
					}
					
					if (tempo == 15 * 60) {
						Feast.prepararFeast();
						Feast.sendFeastMessage();
					} else if (tempo % 60 == 0 && tempo >= 16 * 60 && tempo <= (1140)) {
						Feast.sendFeastMessage();
					} else if (tempo == (1140) + 30 || tempo == (1140) + 50 || tempo == (1140) + 55 || tempo == (1140) + 56|| tempo == (1140) + 57 || tempo == (1140) + 58 || tempo == (1140) + 59) {
						Feast.sendFeastMessage();
					} else if (tempo == 20 * 60) {
						Feast.bausSpawned = true;
						Feast.sendFeastMessage();
						new FeastHologram().remove();
						Feast.addChest();
						Feast.preencherBaus();
					}
					
					if (tempo == 29 * 60) {
						FinalBattle.sendArenaFinalMessage();
					} else if (tempo == (29 * 60) + 30 || tempo == (29 * 60) + 50 || tempo == (29 * 60) + 55 || tempo == (29 * 60) + 56|| tempo == (29 * 60) + 57 || tempo == (29 * 60) + 58 || tempo == (29 * 60) + 59) {
						FinalBattle.sendArenaFinalMessage();
					} else if (tempo == 30 * 60) {
						FinalBattle.sendArenaFinalMessage();
						if (!FrostHG.getManager().arenaFinal) {
							FrostHG.getManager().arenaFinal = true;
							FinalBattle.createArena();
						}
					}
					
					if (tempo % 60 == 0 && tempo >= 2400 && tempo <= (3000)) {
						FinalBattle.sendFinalKillsMessage();
					} else if (tempo == (2940) + 30 || tempo == (2940) + 50 || tempo == (2940) + 55 || tempo == (2940) + 56|| tempo == (2940) + 57 || tempo == (2940) + 58 || tempo == (2940) + 59) {
						FinalBattle.sendFinalKillsMessage();
					} else if (tempo == 3000) {
						FinalBattle.sendFinalKillsMessage();
						int kills = 0;
						Player p = null;
						for (Player player : FrostHG.getManager().getOnlinePlayers()) {
								if (FrostHG.getManager().getJogadores().contains(player.getUniqueId())) {
									if (FrostHG.getManager().kills.get(player.getUniqueId()) > kills) {
										if (p != null) {
											Gamer gamer = BukkitMain.getGamerManager().getGamer(p);
											gamer.addDeaths_hg(1);
											FrostHG.getManager().deathMessage(p, "morreu pois não possuia muitas kills");
											if (BukkitMain.getPermissions().isLight(p)) {
												FrostHG.getManager().setEspectador(p);
											} else {
												FrostHG.getManager().getJogadores().remove(p.getUniqueId());
												FrostHG.getManager().getAntiLogged().add(p.getUniqueId());
												TeleportServer.connectPlayer(p, ServersType.LOBBY);
											}
										}
										kills = FrostHG.getManager().kills.get(player.getUniqueId());
										p = player;
										Scoreboarding.setScoreboard(player);
									} else {
										Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
										gamer.addDeaths_hg(1);
										FrostHG.getManager().deathMessage(player, "morreu pois não possuia muitas kills");
									if (BukkitMain.getPermissions().isLight(player)) {
										FrostHG.getManager().setEspectador(player);
									} else {
										FrostHG.getManager().getJogadores().remove(player.getUniqueId());
										FrostHG.getManager().getAntiLogged().add(player.getUniqueId());
										TeleportServer.connectPlayer(player, ServersType.LOBBY);
									}
									Scoreboarding.setScoreboard(player);
								}
							}
						}
					}
					
					if (FrostHG.getManager().getOnlinePlayers().size() == 0) {
						Bukkit.shutdown();
						return;
					}
					if (FrostHG.getManager().getJogadores().size() == 1) {
						FrostHG.getManager().checkWinner();
						cancelTask();
						return;
					}
					Madman.check();
					for (Player ons : Bukkit.getOnlinePlayers()) {
						Scoreboarding.updateTimer(ons, ons.getScoreboard());
						Scoreboarding.setTab(ons);
						Scoreboarding.updateJogadores(ons, ons.getScoreboard());
					}
					return;
				}
			}
		}, 20, 20));
	}
	
	public static void cancelTask() {
		if (task == null)
			return;
		
        Bukkit.getServer().getScheduler().cancelTask(task.intValue());
        task = null;
	}
	
	public static void checkMSG() {
		if (((tempo >= 10 ? 1 : 0) & (tempo % 60 == 0 ? 1 : 0)) != 0) {
			 sendMSG(getMensagem(tempo));
			 Som(Sound.CLICK);
		} else if (tempo == 30) {
			sendMSG(getMensagem(tempo));
		    Som(Sound.CLICK);
		} else if (tempo == 15) {
		    sendMSG(getMensagem(tempo));
		    Som(Sound.CLICK);
		} else if (tempo == 10) {
			sendMSG(getMensagem(tempo));
		    Som(Sound.CLICK);
		} else if (tempo <= 5) {
			sendMSG(getMensagem(tempo));
		    Som(Sound.NOTE_PLING);
		}
	}
	
	public static String getMensagem(int tempo) {
		if (FrostHG.state == State.INICIANDO) {
			return "§6§lPARTIDA §fIniciando em: §a" + FrostHG.getManager().getTimerChat(tempo);
		} else if (FrostHG.state == State.INVENCIVEL) {
			return "§6§lPARTIDA §fInvencibilidade acaba em: §e" + FrostHG.getManager().getTimerChat(tempo);
		} else if (FrostHG.state == State.JOGO) {
			return "§6§lPARTIDA §fEm jogo: §e" + FrostHG.getManager().getTimerChat(tempo);
		}
		return "";
	}
	
    @SuppressWarnings("deprecation")
	public static void Som(Sound som) {
    	for (Player p : Bukkit.getOnlinePlayers()) {
		     p.playSound(p.getLocation(), som, 1.0F, 1.0F);
    	}
    }
    
    public static void sendMSG(String msg) {
		Bukkit.broadcastMessage(msg);
	}
    
    public static void spawnar(String tipo, Location loc) {
		try {
		    Schematic schematic = Schematic.carregarSchematic(new File(FrostHG.getInstance().getDataFolder(), tipo + ".schematic"));
			Schematic.spawnarSchematic(tipo, Bukkit.getWorld("world"), loc, schematic);
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}
}
