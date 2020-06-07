package br.com.frostmc.lobby.util.crates;

import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.gamer.Gamer;
import br.com.frostmc.common.util.holograms.Hologram;
import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.core.util.Longs;
import br.com.frostmc.core.util.enums.GroupsType;
import br.com.frostmc.core.util.enums.TemporaryType;
import br.com.frostmc.lobby.FrostLobby;
import br.com.frostmc.lobby.util.crates.Reward.RewardType;
import br.com.frostmc.lobby.util.crates.types.Diamond;
import br.com.frostmc.lobby.util.crates.types.Gold;
import br.com.frostmc.lobby.util.crates.types.Iron;
import br.com.frostmc.lobby.util.nms.CustomEntities;
import br.com.frostmc.lobby.util.nms.CustomItemEntity;
import br.com.frostmc.lobby.util.particle.ParticleEffect;
import br.com.frostmc.lobby.util.particle.UtilVelocity;
import net.minecraft.server.v1_7_R4.World;

public class CrateManager {

	public Hologram silverHologram, goldHologram, diamondHologram;

	public boolean initialize() {

		for (int x = -250; x < 250; x++) {
			for (int z = -250; z < 250; z++) {
				Chunk chunk = Bukkit.getWorld("world").getBlockAt(x, 64, z).getChunk();
				if (chunk.isLoaded()) {
					continue;
				}
				chunk.load(true);
			}
		}

		silverHologram = new Hologram("§fCaixa de Prata", new Location(Bukkit.getWorld("world"), -15.5D, 173D, 5.5D), true);
		silverHologram.addLine("§aClique no baú para abrir.");

		goldHologram = new Hologram("§6Caixa de Ouro", new Location(Bukkit.getWorld("world"), -15.5D, 173D, -4.5D), true);
		goldHologram.addLine("§aClique no baú para abrir.");

		diamondHologram = new Hologram("§bCaixa de Diamante", new Location(Bukkit.getWorld("world"), -16.543D, 173D, 0.5D), true);
		diamondHologram.addLine("§aClique no baú para abrir.");

		executeSpiral(diamondHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.WITCH_MAGIC, 0.3F);
		executeSpiral(diamondHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.FLAME, 0.7F);
		executeSpiral(diamondHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.FIREWORKS_SPARK, 0.7F);

		executeSpiral(goldHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.WITCH_MAGIC, 0.3F);
		executeSpiral(goldHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.FLAME, 0.7F);
		executeSpiral(goldHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.FIREWORKS_SPARK, 0.7F);

		executeSpiral(silverHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.WITCH_MAGIC, 0.3F);
		executeSpiral(silverHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.FLAME, 0.7F);
		executeSpiral(silverHologram.getLocation().clone().add(0, -1, 0), ParticleEffect.FIREWORKS_SPARK, 0.7F);
		
		spawnItems();
		return true;
	}

	public boolean spawnItems() {

		try {
			World world = ((CraftWorld) Bukkit.getWorld("world")).getHandle();
			CustomItemEntity silverItem = new CustomItemEntity(world, CraftItemStack.asNMSCopy(new ItemStack(Material.IRON_BLOCK)));
			CustomItemEntity goldItem = new CustomItemEntity(world, CraftItemStack.asNMSCopy(new ItemStack(Material.GOLD_BLOCK)));
			CustomItemEntity diamondItem = new CustomItemEntity(world,  CraftItemStack.asNMSCopy(new ItemStack(Material.DIAMOND_BLOCK)));
			CustomItemEntity item = new CustomItemEntity(world, CraftItemStack.asNMSCopy(new ItemStack(Material.DIAMOND_BLOCK)));

			CustomEntities.spawnEntity(item, diamondHologram.getLocation().getWorld().getSpawnLocation().add(0, 10, 0));
			CustomEntities.spawnEntity(silverItem, silverHologram.getLocation().clone());
			CustomEntities.spawnEntity(goldItem, goldHologram.getLocation().clone());
			CustomEntities.spawnEntity(diamondItem, diamondHologram.getLocation().clone());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean generate(CrateType crate, Gamer gamer) {
		if (crate.equals(CrateType.getType("prata"))) {
			if (gamer.getBox_iron() < 1) {
				gamer.getAccount().getPlayer().sendMessage("§e§lCAIXA §fVocê não possui nenhuma caixa do tipo " + crate.getColor() + crate.getName() + "§f.");
				return false;
			}
			if (gamer.getKeyBox_iron() < 1) {
				gamer.getAccount().getPlayer().sendMessage("§e§lCAIXA §fVocê não possui nenhuma chave do tipo " + crate.getColor() + crate.getName() + "§f.");
				return false;
			}
		} else if (crate.equals(CrateType.getType("ouro"))) {
			if (gamer.getBox_gold() < 1) {
				gamer.getAccount().getPlayer().sendMessage("§e§lCAIXA §fVocê não possui nenhuma caixa do tipo " + crate.getColor() + crate.getName() + "§f.");
				return false;
			}
			if (gamer.getKeyBox_gold() < 1) {
				gamer.getAccount().getPlayer().sendMessage("§e§lCAIXA §fVocê não possui nenhuma chave do tipo " + crate.getColor() + crate.getName() + "§f.");
				return false;
			}
		} else if (crate.equals(CrateType.getType("diamante"))) {
			if (gamer.getBox_diamond() < 1) {
				gamer.getAccount().getPlayer().sendMessage("§e§lCAIXA §fVocê não possui nenhuma caixa do tipo " + crate.getColor() + crate.getName() + "§f.");
				return false;
			}
			if (gamer.getKeyBox_diamond() < 1) {
				gamer.getAccount().getPlayer().sendMessage("§e§lCAIXA §fVocê não possui nenhuma chave do tipo " + crate.getColor() + crate.getName() + "§f.");
				return false;
			}
		}

		if (crate.equals(CrateType.getType("prata"))) {
			gamer.removeBox_iron(1);
			gamer.removeKeyBox_iron(1);
		} else if (crate.equals(CrateType.getType("ouro"))) {
			gamer.removeBox_gold(1);
			gamer.removeKeyBox_gold(1);
		} else if (crate.equals(CrateType.getType("diamante"))) {
			gamer.removeBox_diamond(1);
			gamer.removeKeyBox_diamond(1);
		}

		createInventory(gamer.getAccount().getPlayer(), crate);
		return true;
	}

	public void createInventory(Player player, CrateType crateType) {
		Inventory inventory = Bukkit.createInventory(player, 18, "§8§nAbrindo caixa de " + crateType.getName());

		for (int i = 0; i <= 17; i++) {
			if (inventory.getItem(i) != null)
				continue;
			inventory.setItem(i, new ItemBuilder().setNome("§a").setMaterial(Material.STAINED_GLASS_PANE).setInquebravel().setDurabilidade(15).finalizar());
		}
		
		inventory.setItem(4, new ItemBuilder().setNome("§3Seu prêmio").setMaterial(Material.HOPPER).setInquebravel().finalizar());

		createAnimation(player, inventory, crateType);
		player.openInventory(inventory);
	}

	private void givePrize(Player player, Crate crate) {
		Gamer gamer = BukkitMain.getGamerManager().getGamer(player);
		Reward reward = crate.getReward();
		
		if (reward.getType() == RewardType.MOEDAS) {
			gamer.addMoedas(reward.getAmmount());
			player.sendMessage("§e§lCAIXA §fVocê ganhou §6§l" + NumberFormat.getInstance().format(reward.getAmmount()) + " MOEDAS §fna caixa de " + crate.getName() + "§f.");
		} else if (reward.getType() == RewardType.FICHAS) {
			gamer.addFichas(reward.getAmmount());
			player.sendMessage("§e§lCAIXA §fVocê ganhou §3§l" + NumberFormat.getInstance().format(reward.getAmmount()) + " " + (reward.getAmmount() < 1 ? "ficha" : "fichas").toUpperCase() + " §fna caixa de " + crate.getName() + "§f.");
		} else if (reward.getType() == RewardType.DOUBLE) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "doublexp " + player.getName() + " add " + reward.getAmmount());
			player.sendMessage("§e§lCAIXA §fVocê ganhou §9§l" + reward.getAmmount() + " DOUBLE XP §fna caixa de " + crate.getName() + "§f.");
		} else if (reward.getType() == RewardType.VIP_LIGHT) {
			if (!BukkitMain.getPermissions().isLight(player)) {
				gamer.getAccount().getGroups().setSetBy("CONSOLE");
				gamer.getAccount().getGroups().setTemporaryType(TemporaryType.TEMPORARY);
				gamer.getAccount().getGroups().setGroupsType(GroupsType.LIGHT);
				gamer.getAccount().getGroups().setExpire(Longs.fromString("1d"));
				gamer.getAccount().getGroups().save();
				player.kickPlayer(gamer.getAccount().getGroups().messageOnKick());
				Bukkit.broadcastMessage("§e§lCAIXAS §fO jogador §a" + player.getName() + " §facabou de ganhar um Vip §a§lLIGHT §8(§fUm dia§8) §fna caixa de " + crate.getName() + ".");
			} else {
				gamer.getAccount().getPlayer().sendMessage("§a§lCAIXA §fVocê ganhou o Vip Light, pórem ele não irá ser setado pois o mesmo já tem o cargo. Sua caixa foi devolvida!");
			}
		} else if (reward.getType() == RewardType.VIP_PRIME) {
			if (!BukkitMain.getPermissions().isLight(player)) {
				gamer.getAccount().getGroups().setSetBy("CONSOLE");
				gamer.getAccount().getGroups().setTemporaryType(TemporaryType.TEMPORARY);
				gamer.getAccount().getGroups().setGroupsType(GroupsType.PRIME);
				gamer.getAccount().getGroups().setExpire(Longs.fromString("4d"));
				gamer.getAccount().getGroups().save();
				player.kickPlayer(gamer.getAccount().getGroups().messageOnKick());
				Bukkit.broadcastMessage("§e§lCAIXAS §fO jogador §a" + player.getName() + " §facabou de ganhar um Vip §3§lPRIME §8(§fQuatro dias§8) §fna caixa de " + crate.getName() + ".");
			} else {
				gamer.getAccount().getPlayer().sendMessage("§a§lCAIXA §fVocê ganhou o Vip Prime, pórem ele não irá ser setado pois o mesmo já tem o cargo. Sua caixa foi devolvida!");
			}
		} else if (reward.getType() == RewardType.VIP_FROST) {
			if (!BukkitMain.getPermissions().isLight(player)) {
				gamer.getAccount().getGroups().setSetBy("CONSOLE");
				gamer.getAccount().getGroups().setTemporaryType(TemporaryType.TEMPORARY);
				gamer.getAccount().getGroups().setGroupsType(GroupsType.FROST);
				gamer.getAccount().getGroups().setExpire(Longs.fromString("7d"));
				gamer.getAccount().getGroups().save();
				player.kickPlayer(gamer.getAccount().getGroups().messageOnKick());
				Bukkit.broadcastMessage("§e§lCAIXAS §fO jogador §a" + player.getName() + " §facabou de ganhar um Vip §b§lFROST §8(§fUma semana§8) §fna caixa de " + crate.getName() + ".");
			} else {
				gamer.getAccount().getPlayer().sendMessage("§a§lCAIXA §fVocê ganhou o Vip Frost, pórem ele não irá ser setado pois o mesmo já tem o cargo. Sua caixa foi devolvida!");
			}
		}
		player.closeInventory();
	}

	private void createAnimation(Player player, Inventory inventory, CrateType boxType) {
		AtomicInteger startIndex = new AtomicInteger(0);
		Crate[] boxes = new Crate[50];

		for (int i = 0; i < boxes.length; i++) {
			boxes[i] = createBoxByType(player, boxType);
		}

		new Thread(new Runnable() {
			int time = 0;
			Crate winCrate;

			@Override
			public void run() {
				while (startIndex.get() < boxes.length - 1) {
					startIndex.incrementAndGet();
					int currentIndex = 0;
					for (int i = startIndex.get(); i <= startIndex.get() + 6; i++) {
						if (i >= boxes.length - 1) {
							break;
						}
						inventory.setItem(10 + currentIndex, boxes[i].getRewardIcon());
						player.playSound(player.getLocation(), Sound.CLICK, 5, 5);

						if (currentIndex == 3)
							winCrate = boxes[i];

						currentIndex++;
					}

					if (!inventory.getViewers().contains(player)) {
						player.openInventory(inventory);
					}

					if (startIndex.get() >= boxes.length - 1) {
						player.closeInventory();
					}

					if (time * 5 >= 500) {
						time += 500;
						givePrize(player, winCrate);
						break;
					} else {
						time += 5;
					}
					try {
						Thread.sleep(time * 5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private Crate createBoxByType(Player player, CrateType crateType) {
		if (crateType == CrateType.DIAMOND)
			return new Diamond(player);
		if (crateType == CrateType.GOLD)
			return new Gold(player);
		if (crateType == CrateType.IRON)
			return new Iron(player);
		return null;
	}

	public void executeSpiral(Location crateLocation, ParticleEffect effect, float radius) {
		new BukkitRunnable() {
			double xRotation, yRotation, zRotation = 5.0D;
			double angularVelocityX = 0.01570796326794897D, angularVelocityY = 0.01847995678582231D, angularVelocityZ = 0.0202683397005793D;
			float step = 0.0F;
			double xSubtract, ySubtract, zSubtract;
			boolean enableRotation = true;
			int particles = 20;

			public void run() {
				Location location = crateLocation.clone();
				location.add(0.0D, 1.0D, 0.0D);
				location.subtract(xSubtract, ySubtract, zSubtract);
				double inc = 6.283185307179586D / particles;
				double angle = step * inc;
				Vector v = new Vector();
				v.setX(Math.cos(angle) * radius);
				v.setZ(Math.sin(angle) * radius);
				UtilVelocity.rotateVector(v, xRotation, yRotation, zRotation);
				if (this.enableRotation) {
					UtilVelocity.rotateVector(v, angularVelocityX * step, angularVelocityY * step,
							angularVelocityZ * step);
				}

				effect.setParticle(location.add(v), 0, 0, 0, 0, 1);

				step += 1.0F;
			}
		}.runTaskTimer(FrostLobby.getPlugin(FrostLobby.class), 0, 1);
	}

	public void executeOpsetSpiral(Location crateLocation, ParticleEffect effect, float radius) {
		new BukkitRunnable() {
			double xRotation, yRotation, zRotation = 5.0D;
			double angularVelocityX = 0.01570796326794897D, angularVelocityY = 0.01847995678582231D,
					angularVelocityZ = 0.0202683397005793D;
			double xSubtract, ySubtract, zSubtract;

			float step = 0.0F;
			boolean enableRotation = true;
			int particles = 20;

			public void run() {
				Location location = crateLocation.clone();
				location.add(0.0D, 1.0D, 0.0D);
				location.subtract(xSubtract, ySubtract, zSubtract);
				double inc = 6.283185307179586D / particles;
				double angle = step * inc;
				Vector v = new Vector();
				v.setX(Math.cos(angle) * radius);
				v.setZ(Math.sin(angle) * radius);
				UtilVelocity.rotateVector(v, xRotation, yRotation, zRotation);
				if (this.enableRotation) {
					UtilVelocity.rotateVector(v, angularVelocityX * step, angularVelocityY * step,
							angularVelocityZ * step);
				}

				effect.setParticle(location.add(v), 0, 0, 0, 0, 1);

				step -= 1.0F;
			}
		}.runTaskTimer(FrostLobby.getPlugin(FrostLobby.class), 0, 1);
	}

}
