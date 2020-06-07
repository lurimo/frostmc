package br.com.frostmc.lobby.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.frostmc.common.util.TeleportServer;
import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.core.util.Strings;
import br.com.frostmc.core.util.enums.ServersType;
import br.com.frostmc.lobby.FrostLobby;
import br.com.frostmc.lobby.inventory.enums.ItemsCache;
import br.com.frostmc.lobby.inventory.player.ConfigurationInventory;
import br.com.frostmc.lobby.inventory.player.HardcoreGamesInventory;
import br.com.frostmc.lobby.inventory.player.MenuInventory;
import br.com.frostmc.lobby.inventory.player.ParticleInventory;
import br.com.frostmc.lobby.inventory.player.ServersInventory;
import br.com.frostmc.lobby.inventory.player.ParticleInventory.ParticleModes;
import br.com.frostmc.lobby.util.admin.AdminManager;
import br.com.frostmc.lobby.util.crates.CrateType;

public class EventsInventory implements Listener {
	
	@EventHandler
	public void asd(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if(event.getRightClicked() instanceof Zombie) {
			event.setCancelled(true);
			Zombie zombie = (Zombie)event.getRightClicked();
			if(zombie.getCustomName().equalsIgnoreCase("§1")) {
				TeleportServer.connectPlayer(player, ServersType.PVP);
				return;
			}
		} else if(event.getRightClicked() instanceof Skeleton) {
			event.setCancelled(true);
			Skeleton skeleton = (Skeleton)event.getRightClicked();
			if(skeleton.getCustomName().equalsIgnoreCase("§2")) {
				HardcoreGamesInventory.open(player);
				return;
			}
		} else if(event.getRightClicked() instanceof Villager) {
			event.setCancelled(true);
			Villager villager = (Villager)event.getRightClicked();
			if(villager.getCustomName().equalsIgnoreCase("§3")) {
				TeleportServer.connectPlayer(player, ServersType.ONEVSONE);
				return;
			}
		} else if(event.getRightClicked() instanceof IronGolem) {
			event.setCancelled(true);
			IronGolem ironGolem = (IronGolem)event.getRightClicked();
			if(ironGolem.getCustomName().equalsIgnoreCase("§4")) {
				TeleportServer.connectPlayer(player, ServersType.GLADIATOR);
				return;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void asd(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack currentItem = player.getItemInHand();
		Block block = event.getClickedBlock();
		if (block != null) {
			if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
				if (block.getType().equals(Material.ENDER_PORTAL_FRAME)) {
					event.setCancelled(true);
					new CrateSelectInventory().generate(player, CrateType.DIAMOND);
				} else if (block.getType().equals(Material.ENDER_CHEST)) {
					event.setCancelled(true);
					new CrateSelectInventory().generate(player, CrateType.GOLD);
				} else if (block.getType().equals(Material.CHEST)) {
					event.setCancelled(true);
					new CrateSelectInventory().generate(player, CrateType.IRON);
				}
			}
		}
		if (currentItem.getType().equals((Object) ItemsCache.PLAYERS_OFF.getMaterial()) && currentItem.getItemMeta().hasDisplayName() & currentItem.getItemMeta().getDisplayName().equalsIgnoreCase(ItemsCache.PLAYERS_OFF.getName())) {
			event.setCancelled(true);
			for(Player all : Bukkit.getOnlinePlayers()) {
				if (!AdminManager.isAdmin(all)) {
					player.showPlayer(all);
				}
			}
			player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemBuilder().setNome(ItemsCache.PLAYERS_ON.getName()).setMaterial(ItemsCache.PLAYERS_ON.getMaterial()).setLore(ItemsCache.PLAYERS_ON.getLore()).finalizar());
			player.updateInventory();
			return;
		}
		if (currentItem.getType().equals((Object) ItemsCache.PLAYERS_ON.getMaterial()) && currentItem.getItemMeta().hasDisplayName() & currentItem.getItemMeta().getDisplayName().equalsIgnoreCase(ItemsCache.PLAYERS_ON.getName())) {
			event.setCancelled(true);
			for(Player all : Bukkit.getOnlinePlayers()) {
				if (!AdminManager.isAdmin(all)) {
					player.hidePlayer(all);
				}
			}
			player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemBuilder().setNome(ItemsCache.PLAYERS_OFF.getName()).setMaterial(ItemsCache.PLAYERS_OFF.getMaterial()).setLore(ItemsCache.PLAYERS_OFF.getLore()).finalizar());
			player.updateInventory();
			return;
		}
		if (currentItem.getType().equals((Object) ItemsCache.SELECT_SERVER.getMaterial()) && currentItem.getItemMeta().hasDisplayName() & currentItem.getItemMeta().getDisplayName().equalsIgnoreCase(ItemsCache.SELECT_SERVER.getName())) {
			event.setCancelled(true);
			if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
				ServersInventory.open(player);
				return;
			}
		}
		if (currentItem.getType().equals((Object) ItemsCache.SELECT_MENU.getMaterial()) && currentItem.getItemMeta().hasDisplayName() & currentItem.getItemMeta().getDisplayName().equalsIgnoreCase(ItemsCache.SELECT_MENU.getName())) {
			event.setCancelled(true);
			if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
				MenuInventory.open(player);
				return;
			}
		}
		if (currentItem.getType().equals((Object) ItemsCache.SELECT_LOBBY.getMaterial()) && currentItem.getItemMeta().hasDisplayName() & currentItem.getItemMeta().getDisplayName().equalsIgnoreCase(ItemsCache.SELECT_LOBBY.getName())) {
			event.setCancelled(true);
			if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
				player.sendMessage("§a§lLOBBY §fSomente o Lobby #1, está online no momento.");
				return;
			}
		}
		if (currentItem.getType().equals((Object) ItemsCache.SELECT_STORE.getMaterial()) && currentItem.getItemMeta().hasDisplayName() & currentItem.getItemMeta().getDisplayName().equalsIgnoreCase(ItemsCache.SELECT_STORE.getName())) {
			event.setCancelled(true);
			if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
				player.sendMessage("§a§lLOJA §fAcesse a nossa loja online: §3" + Strings.getWebstore());
				return;
			}
		}
	}
	
	public static Integer task = null;
	

	public static void cancelTask() {
		if (task == null)
			return;
		
        Bukkit.getServer().getScheduler().cancelTask(task.intValue());
        task = null;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void asd(InventoryClickEvent event) {
		Inventory inventory = event.getInventory();
		ItemStack currentItem = event.getCurrentItem();
		Entity whoClicked = event.getWhoClicked();
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) whoClicked;
			if (currentItem == null) {
				return;
			}
			if (!currentItem.hasItemMeta()) {
				return;
			}
			if (inventory.getTitle().equals(ServersInventory.title)) {
				event.setCancelled(true);
				player.closeInventory();
				if (currentItem.getItemMeta().getDisplayName().equals("§aKit PvP")) {
					TeleportServer.connectPlayer(player, ServersType.PVP);
					return;
				} else if (currentItem.getItemMeta().getDisplayName().equals("§aHardcore Games")) {
					HardcoreGamesInventory.open(player);
					return;
				} else if (currentItem.getItemMeta().getDisplayName().equals("§a1 vs 1")) {
					TeleportServer.connectPlayer(player, ServersType.ONEVSONE);
					return;
				} else if (currentItem.getItemMeta().getDisplayName().equals("§aGladiator")) {
					TeleportServer.connectPlayer(player, ServersType.GLADIATOR);
					return;
				}
			} else if (inventory.getTitle().equals(MenuInventory.titleMain)) {
				event.setCancelled(true);
				if (currentItem.getItemMeta().getDisplayName().equals("§aStatus")) {
					MenuInventory.openStatus(player);
					return;
				} else if (currentItem.getItemMeta().getDisplayName().equals("§aPerfil")) {
					MenuInventory.openPerfil(player);
					return;
				} else if (currentItem.getItemMeta().getDisplayName().equals("§aPreferências")) {
					MenuInventory.openPreferencies(player);
					return;
				}
			} else if (inventory.getTitle().equals(MenuInventory.titleStats)) {
				event.setCancelled(true);
				if (currentItem.getItemMeta().getDisplayName().equals("§cVoltar")) {
					MenuInventory.open(player);
					return;
				}
			} else if (inventory.getTitle().equals(MenuInventory.titlePerfil)) {
				event.setCancelled(true);
				if (currentItem.getItemMeta().getDisplayName().equals("§cVoltar")) {
					MenuInventory.open(player);
					return;
				}
			} else if (inventory.getTitle().equals(MenuInventory.titlePreferencies)) {
				event.setCancelled(true);
				if (currentItem.getItemMeta().getDisplayName().equals("§cVoltar")) {
					MenuInventory.open(player);
					return;
				}
				if (currentItem.getItemMeta().getDisplayName().equals("§aParticulas")) {
					ParticleInventory.open(player);
					return;
				}
				if (currentItem.getItemMeta().getDisplayName().equals("§aConfigurações")) {
					ConfigurationInventory.open(player);
					return;
				}
			} else if (inventory.getTitle().equals(ConfigurationInventory.titleMain)) {
				event.setCancelled(true);
				if (currentItem.getItemMeta().getDisplayName().equals("§cVoltar")) {
					MenuInventory.openPreferencies(player);
					return;
				}
				if (currentItem.getItemMeta().getDisplayName().equals("§aAtivado")) {
					inventory.setItem(event.getSlot(), new ItemBuilder().setNome("§cDesativado").setMaterial(Material.getMaterial(351)).setDurabilidade(8).finalizar());
					if(event.getClickedInventory().getItem(event.getSlot()-9).getType().equals(Material.FEATHER)) {
						player.chat("/fly");
						return;
					}
					if(event.getClickedInventory().getItem(event.getSlot()-9).getType().equals(Material.BOOK_AND_QUILL)) {
						player.chat("/tell off");
						return;
					}
					if(event.getClickedInventory().getItem(event.getSlot()-9).getType().equals(Material.PAPER)) {
						player.chat("/score");
						return;
					}
				}
				if (currentItem.getItemMeta().getDisplayName().equals("§cDesativado")) {
					inventory.setItem(event.getSlot(), new ItemBuilder().setNome("§aAtivado").setMaterial(Material.getMaterial(351)).setDurabilidade(10).finalizar());
					if(event.getClickedInventory().getItem(event.getSlot()-9).getType().equals(Material.FEATHER)) {
						player.chat("/fly");
						return;
					}
					if(event.getClickedInventory().getItem(event.getSlot()-9).getType().equals(Material.BOOK_AND_QUILL)) {
						player.chat("/tell on");
						return;
					}
					if(event.getClickedInventory().getItem(event.getSlot()-9).getType().equals(Material.PAPER)) {
						player.chat("/score");
						return;
					}
				}
			} else if (inventory.getTitle().equals(ParticleInventory.titleMain)) {
				event.setCancelled(true);
				if (currentItem.getItemMeta().getDisplayName().equals("§cVoltar")) {
					MenuInventory.open(player);
					return;
				}
				if (currentItem.getItemMeta().getDisplayName().equals("§aCoração")) {
					player.closeInventory();
					if (!ParticleInventory.effect.containsKey(player.getUniqueId())) {
						ParticleInventory.effect.put(player.getUniqueId(), ParticleModes.HEART);
						player.sendMessage("§a§lPARTICLE §fVocê selecionou a particula de §aCoração §fpara remover clique na mesma ou selecione outra particula.");
					} else {
						if (!ParticleInventory.effect.get(player.getUniqueId()).equals(ParticleModes.HEART)) {
							ParticleInventory.effect.remove(player.getUniqueId());
							ParticleInventory.effect.put(player.getUniqueId(), ParticleModes.HEART);
						} else {
							ParticleInventory.effect.remove(player.getUniqueId());
						}
						player.sendMessage("§c§lPARTICLE §fVocê removeu a particula de §cCoração§f.");
					}
					task = Integer.valueOf(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(FrostLobby.getInstance(), new Runnable() {
						public void run() {
							if (ParticleInventory.effect.containsKey(player.getUniqueId())) {
								if (ParticleInventory.effect.get(player.getUniqueId()).equals(ParticleModes.HEART)) {
									player.getWorld().playEffect(player.getLocation(), Effect.HEART, 200, 200);
								} else {
									cancelTask();
								}
							}
						}
					}, 5L, 5L));
					return;
				}
				if (currentItem.getItemMeta().getDisplayName().equals("§aExplosão")) {
					player.closeInventory();
					if (!ParticleInventory.effect.containsKey(player.getUniqueId())) {
						ParticleInventory.effect.put(player.getUniqueId(), ParticleModes.EXPLOSION);
						player.sendMessage("§a§lPARTICLE §fVocê selecionou a particula de §aExplosão §fpara remover clique na mesma ou selecione outra particula.");
					} else {
						if (!ParticleInventory.effect.get(player.getUniqueId()).equals(ParticleModes.EXPLOSION)) {
							ParticleInventory.effect.remove(player.getUniqueId());
							ParticleInventory.effect.put(player.getUniqueId(), ParticleModes.EXPLOSION);
						} else {
							ParticleInventory.effect.remove(player.getUniqueId());
						}
						player.sendMessage("§c§lPARTICLE §fVocê removeu a particula de §cExplosão§f.");
					}
					task = Integer.valueOf(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(FrostLobby.getInstance(), new Runnable() {
						public void run() {
							if (ParticleInventory.effect.containsKey(player.getUniqueId())) {
								if (ParticleInventory.effect.get(player.getUniqueId()).equals(ParticleModes.EXPLOSION)) {
									player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_LARGE, 200, 200);
								} else {
									cancelTask();
								}
							}
						}
					}, 5L, 5L));
					return;
				}
				if (currentItem.getItemMeta().getDisplayName().equals("§aFogo")) {
					player.closeInventory();
					if (!ParticleInventory.effect.containsKey(player.getUniqueId())) {
						ParticleInventory.effect.put(player.getUniqueId(), ParticleModes.FIRE);
						player.sendMessage("§a§lPARTICLE §fVocê selecionou a particula de §aFogo §fpara remover clique na mesma ou selecione outra particula.");
					}
					if (!ParticleInventory.effect.get(player.getUniqueId()).equals(ParticleModes.FIRE)) {
						ParticleInventory.effect.remove(player.getUniqueId());
						ParticleInventory.effect.put(player.getUniqueId(), ParticleModes.FIRE);
					} else {
						ParticleInventory.effect.remove(player.getUniqueId());
					}
					task = Integer.valueOf(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(FrostLobby.getInstance(), new Runnable() {
						public void run() {
							if (ParticleInventory.effect.containsKey(player.getUniqueId())) {
								if (ParticleInventory.effect.get(player.getUniqueId()).equals(ParticleModes.FIRE)) {
									player.getWorld().playEffect(player.getLocation(), Effect.BLAZE_SHOOT, 200, 200);
								} else {
									cancelTask();
								}
							}
						}
					}, 5L, 5L));
					return;
				}
			} else if (inventory.getTitle().equals(HardcoreGamesInventory.title)) {
				event.setCancelled(true);
				if (currentItem.getType() != Material.STAINED_GLASS_PANE && currentItem.getType() != Material.CHEST && currentItem.getType() != Material.ENDER_CHEST && currentItem.getType() != Material.GOLD_INGOT) {
					player.closeInventory();
					TeleportServer.connectPlayer(player, ServersType.valueOf("HG_" + currentItem.getItemMeta().getDisplayName().replace("§9", "").replace("§e", "").replace("§n", "").replace("§a", "").replace("§6", "").replace("§c", "").replace("§2", "").replace(".hg.frostmc.com.br", "").toUpperCase()));
					return;
				}
			}
		}
	}
	
}
