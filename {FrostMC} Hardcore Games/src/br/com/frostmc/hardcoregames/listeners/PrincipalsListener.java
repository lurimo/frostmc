package br.com.frostmc.hardcoregames.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.ServerOptions;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.common.util.gamer.league.LeagueContager;
import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.player.FlyCommand;
import br.com.frostmc.hardcoregames.scoreboard.Scoreboarding;
import br.com.frostmc.hardcoregames.timer.Timer;
import br.com.frostmc.hardcoregames.util.DropItem;
import br.com.frostmc.hardcoregames.util.State;
import br.com.frostmc.hardcoregames.util.TeleportServer;
import br.com.frostmc.hardcoregames.util.admin.AdminManager;
import br.com.frostmc.hardcoregames.util.combatlog.Combat;
import br.com.frostmc.hardcoregames.util.kit.GladUtils;
import br.com.frostmc.hardcoregames.util.kit.Kits;
import br.com.frostmc.hardcoregames.util.kit.hability.Gladiator;

public class PrincipalsListener implements Listener {
	
	public static boolean respawn = false;
	public ArrayList<UUID> delay = new ArrayList<>();
	
	@EventHandler
	public void asd(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (FlyCommand.usingFlight.contains(player.getUniqueId())) {
		}
	}
	
	@EventHandler
	public void asd(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getItemInHand().getType().equals(Material.MUSHROOM_SOUP) && event.getAction().name().contains("RIGHT")) {
			event.setCancelled(true);

			Damageable health = player;
			int food = player.getFoodLevel();
			double vida = health.getHealth();

			if (vida != 20.0D) {
				player.setHealth(vida + 7 > health.getMaxHealth() ? health.getMaxHealth() : vida + 7);
				player.getItemInHand().setType(Material.BOWL);
				player.updateInventory();
			} else if (food != 20) {
				player.setFoodLevel(food + 7 > 20 ? 20 : food + 7);
				player.getItemInHand().setType(Material.BOWL);
				player.updateInventory();
			}
		} else if ((event.hasItem()) && (event.getItem().getType() == Material.COMPASS) && (event.getAction() != Action.PHYSICAL)) {
			event.setCancelled(true);
			if (delay.contains(player.getUniqueId())) {
				return;
			}
			Player alvo = getRandomPlayer(player);
			if (alvo == null) {
				player.sendMessage("§e§lBÚSSOLA §fNenhum jogador encontrado, bússola apontada para o spawn.");
				player.setCompassTarget(FrostHG.getManager().getSpawn());
			} else {
				player.sendMessage("§e§lBÚSSOLA §fapontada para: §a" + alvo.getName());
				player.setCompassTarget(alvo.getLocation());
			}
			delay.add(player.getUniqueId());
			new BukkitRunnable() {
				public void run() {
					delay.remove(player.getUniqueId());
				}
			}.runTaskLater(BukkitMain.getInstance(), 20L);
		}
	}

	public Player getRandomPlayer(Player p) {
		Player target = null;
		for (UUID uuid : FrostHG.getManager().getJogadores()) {
			Player playerTarget = Bukkit.getPlayer(uuid);
			if (playerTarget != null) {
				if (!playerTarget.equals(p)) {
					if (playerTarget.getLocation().distance(p.getLocation()) >= 15.0D) {
						if (target == null) {
							target = playerTarget;
						} else {
							double distanciaAtual = target.getLocation().distance(p.getLocation());
							double novaDistancia = playerTarget.getLocation().distance(p.getLocation());
							if (distanciaAtual > novaDistancia)
								target = playerTarget;
						}
					}
				}
			}
		}
		return target;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void asd(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		if (FrostHG.state != State.JOGO)
			return;
		Player matou = (Player) e.getEntity().getKiller();
		Player morreu = (Player) e.getEntity();
		if (e.getEntity() instanceof Player) {
			Gamer gamerEntity = BukkitMain.getGamerManager().getGamer(morreu);

			new BukkitRunnable() {
				public void run() {
					e.getEntity().spigot().respawn();
				}
			}.runTaskLater(FrostHG.getInstance(), 5L);
			
			if (Combat.inCombat(morreu.getUniqueId())) {
				if (Combat.combatLogs.containsKey(morreu.getUniqueId())) {
					Combat.combatLogs.remove(morreu.getUniqueId());
			    }
			}
			
			
			@SuppressWarnings("unused")
			Location loc = morreu.getLocation();
			if (Gladiator.gladArena.containsKey(morreu.getUniqueId())) {
				loc = Gladiator.glads.get(Gladiator.gladArena.get(morreu.getUniqueId())).getBackForPlayer(morreu);
				
				String oldArena = Gladiator.gladArena.get(morreu.getUniqueId());
				GladUtils glad = Gladiator.glads.get(oldArena);
				DropItem.dropItems(morreu, glad.getBackForPlayer(morreu).add(0, 0.5, 0));
				glad.teleportBack();
				glad.cancelGlad();
			}
			
			if (e.getEntity().getKiller() instanceof Player) {
				Gamer gamerKiller = BukkitMain.getGamerManager().getGamer(matou);
				
				DropItem.dropItems(morreu, morreu.getLocation());
				FrostHG.getManager().deathMessage(morreu, "foi morto por " + matou.getName() + "(" + FrostHG.getManager().getKitAPI().getKit(matou) + ") usando " + FrostHG.getManager().getItem(matou));
				FrostHG.getManager().kills.put(matou.getUniqueId(), FrostHG.getManager().kills.get(matou.getUniqueId()) + 1);
				if (!ServerOptions.EVENTO.isActive()) {
					gamerKiller.addKills_hg(1);
					
					LeagueContager league = new LeagueContager(gamerKiller, gamerEntity);
					league.prizeLeague();
				}
				
				Scoreboarding.updateKills(matou, matou.getScoreboard());
				Scoreboarding.updateLiga(matou, matou.getScoreboard());
				Scoreboarding.updateJogadores(matou, matou.getScoreboard());

				if (FrostHG.getManager().getFirstBlood().size() == 0) {
					FrostHG.getManager().getFirstBlood().put(matou.getUniqueId(), morreu.getName());
					Bukkit.broadcastMessage("§4§lFIRSTKILL §f" + matou.getName() + " §ffoi o primeiro a matar um jogador!");
					if (matou.getInventory().getChestplate() == null) {
						matou.sendMessage("§4§lFIRSTKILL §fVocê §a§lGANHOU§f um peitoral de couro.");
						matou.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
					}
				}
				return;
			} else {
				if (!ServerOptions.EVENTO.isActive()) {
					gamerEntity.addDeaths_hg(1);
				}
				FrostHG.getManager().deathMessage(morreu, "morreu " + FrostHG.getManager().getCausa(morreu.getLastDamageCause().getCause()) + (respawn ? "." : " e foi eliminado do torneio."));
			}
			
		}
	}

	@EventHandler
	public void asd(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if (FrostHG.state == State.JOGO) {
			if (ServerOptions.EVENTO.isActive()) {
				if (!BukkitMain.getPermissions().isLight(p)) {
					new BukkitRunnable() {
						public void run() {
							FrostHG.getManager().getJogadores().remove(p.getUniqueId());
							FrostHG.getManager().getAntiLogged().add(p.getUniqueId());
							TeleportServer.connectPlayer(p, ServersType.LOBBY);
						}
					}.runTaskLater(FrostHG.getInstance(), 5L);
				} else {
					new BukkitRunnable() {
						public void run() {
							FrostHG.getManager().setEspectador(p);
						}
					}.runTaskLater(FrostHG.getInstance(), 5L);	
				}
				if (FrostHG.getManager().getJogadores().size() >= 1) {
					for (Player players : FrostHG.getManager().getOnlinePlayers()) {
						Scoreboarding.updateJogadores(players, players.getScoreboard());
					}
				}
				return;
			}
			if (Timer.tempo < 300) {
				if (!BukkitMain.getPermissions().isLight(p)) {
					new BukkitRunnable() {
						public void run() {
							new BukkitRunnable() {
								public void run() {
									FrostHG.getManager().getJogadores().remove(p.getUniqueId());
									FrostHG.getManager().getAntiLogged().add(p.getUniqueId());
									TeleportServer.connectPlayer(p, ServersType.LOBBY);
								}
							}.runTaskLater(FrostHG.getInstance(), 5L);
						}
					}.runTaskLater(FrostHG.getInstance(), 5L);	
				} else {
					new BukkitRunnable() {
						public void run() {
							FrostHG.getManager().respawn(p);
						}
					}.runTaskLater(FrostHG.getInstance(), 5L);	
				}
				if (FrostHG.getManager().getJogadores().size() >= 1) {
					for (Player players : FrostHG.getManager().getOnlinePlayers()) {
						Scoreboarding.updateJogadores(players, players.getScoreboard());
					}
				}
			} else if (Timer.tempo <= 360) {
				if (!BukkitMain.getPermissions().isPrime(p)) {
					new BukkitRunnable() {
						public void run() {
							FrostHG.getManager().getJogadores().remove(p.getUniqueId());
							FrostHG.getManager().getAntiLogged().add(p.getUniqueId());
							TeleportServer.connectPlayer(p, ServersType.LOBBY);
						}
					}.runTaskLater(FrostHG.getInstance(), 5L);
				} else {
					new BukkitRunnable() {
						public void run() {
							FrostHG.getManager().respawn(p);
						}
					}.runTaskLater(FrostHG.getInstance(), 5L);	
				}
				if (FrostHG.getManager().getJogadores().size() >= 1) {
					for (Player players : FrostHG.getManager().getOnlinePlayers()) {
						Scoreboarding.updateJogadores(players, players.getScoreboard());
					}
				}
			} else {
				if (!BukkitMain.getPermissions().isLight(p)) {
					new BukkitRunnable() {
						public void run() {
							FrostHG.getManager().getJogadores().remove(p.getUniqueId());
							FrostHG.getManager().getAntiLogged().add(p.getUniqueId());
							TeleportServer.connectPlayer(p, ServersType.LOBBY);
						}
					}.runTaskLater(FrostHG.getInstance(), 5L);
				} else {
					new BukkitRunnable() {
						public void run() {
							FrostHG.getManager().setEspectador(p);
						}
					}.runTaskLater(FrostHG.getInstance(), 5L);	
				}
				if (FrostHG.getManager().getJogadores().size() >= 1) {
					for (Player players : FrostHG.getManager().getOnlinePlayers()) {
						Scoreboarding.updateJogadores(players, players.getScoreboard());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void asd(PlayerPickupItemEvent event) {
		if (FrostHG.state == State.INICIANDO) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void fisicaDoCogu(BlockPhysicsEvent e) {
		if (e.getBlock().getType().equals(Material.BROWN_MUSHROOM)) {
			e.setCancelled(true);
		} else if (e.getBlock().getType().equals(Material.RED_MUSHROOM)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		if (FrostHG.state != State.INICIANDO) {
			if (FrostHG.getManager().getKitAPI().hasKit(player, Kits.valueOf(FrostHG.getManager().getKitAPI().getKit(player).toUpperCase()))) {
				if (FrostHG.getManager().checkItem(e.getItemDrop().getItemStack(), "§bKit " + FrostHG.getManager().getKitAPI().getKit(player))) {
					e.setCancelled(true);
				}
			}
		} else if (FrostHG.state == State.INICIANDO) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onSpawnItem(ItemSpawnEvent e) {
		if (FrostHG.state != State.INICIANDO) {
			e.setCancelled(false);
		}
	}

	@EventHandler
	public void onDamageEvent(EntityDamageEvent e) {
		if (FrostHG.state != State.JOGO) {
			e.setCancelled(true);
			return;
		}
		if (!ServerOptions.DAMAGE.isActive()) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player))
			return;
		if (FrostHG.state != State.JOGO) {
			e.setCancelled(true);
			return;
		} else if (FrostHG.state == State.JOGO) {
			Player player = (Player) e.getDamager();
			if (!ServerOptions.PVP.isActive()) {
				e.setCancelled(true);
				return;
			}
			if (AdminManager.isAdmin(player)) {
				e.setCancelled(false);
				return;
			}
		}
	}

	@EventHandler
	public void asd(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void asd(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (FrostHG.getManager().getEspectadores().contains(player.getUniqueId())) {
			if (!AdminManager.isAdmin(player)) {
				event.setCancelled(true);
				FrostHG.getManager().getOnlinePlayers().forEach(specs -> {
					if (FrostHG.getManager().getEspectadores().contains(specs.getUniqueId())) {
						specs.sendMessage("§3§l[SPEC] " + player.getDisplayName() + "§f: §7" + event.getMessage());
					}
				});
			}
		}
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		Iterator<Block> blocks = event.blockList().iterator();
		while (blocks.hasNext()) {
			Block block = blocks.next();
			if (block.getLocation().getBlockX() == 501 || block.getLocation().getBlockZ() >= 501 || block.getLocation().getBlockX() == -501 || block.getLocation().getBlockZ() == -501) {
				blocks.remove();
			}
		}
	}

	@EventHandler
	public void onBrakBlock(BlockDamageEvent event) {
		Block block = event.getBlock();
		if (block.getLocation().getBlockX() == 501 || block.getLocation().getBlockZ() == 501
				|| block.getLocation().getBlockX() == -501 || block.getLocation().getBlockZ() == -501) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBrakBlock(BlockBreakEvent event) {
		Block block = event.getBlock();
		if (block.getLocation().getBlockX() == 501 || block.getLocation().getBlockZ() == 501
				|| block.getLocation().getBlockX() == -501 || block.getLocation().getBlockZ() == -501) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void Fome(FoodLevelChangeEvent e) {
		if (FrostHG.state != State.INICIANDO) {
			e.setCancelled(true);
			Player p = (Player) e.getEntity();
			if (FrostHG.getManager().getEspectadores().contains(p.getUniqueId())) {
				e.setCancelled(true);
				return;
			}
			p.setSaturation(4.2F);
		}
	}

	@EventHandler
	protected void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		if (!(e.getDamager() instanceof Player))
			return;

		Player player = (Player) e.getEntity();
		Player damager = (Player) e.getDamager();

		if (FrostHG.state != State.JOGO)
			return;
		Scoreboard scoreboard = damager.getScoreboard();

		if (scoreboard.getObjective(DisplaySlot.BELOW_NAME) != null)
			return;
		final Objective below = scoreboard.registerNewObjective("lucas", "god");

		String kit = "§fKit: §3" + FrostHG.getManager().getKitAPI().getKit(player);
		below.setDisplayName(kit);
		below.setDisplaySlot(DisplaySlot.BELOW_NAME);

		new BukkitRunnable() {
			public void run() {
				scoreboard.clearSlot(DisplaySlot.BELOW_NAME);
				below.unregister();
			}
		}.runTaskLater(FrostHG.getInstance(), 20L);
	}

	@EventHandler
	public void asd(ServerListPingEvent event) {
		Player winner = FrostHG.getManager().getWinner();
		if (FrostHG.state == State.INICIANDO) {
			event.setMotd("iniciando: " + FrostHG.getManager().getTimerFormat(Timer.tempo));
		} else if (FrostHG.state == State.INVENCIVEL) {
			event.setMotd("invencibilidade: " + FrostHG.getManager().getTimerFormat(Timer.tempo));
		} else if (FrostHG.state == State.JOGO) {
			event.setMotd("andamento: " + FrostHG.getManager().getTimerFormat(Timer.tempo));
		} else if (FrostHG.state == State.FINAL) {
			event.setMotd("winner: " + (winner != null ? winner.getName() : "Não identificado"));
		}
	}
	
}
