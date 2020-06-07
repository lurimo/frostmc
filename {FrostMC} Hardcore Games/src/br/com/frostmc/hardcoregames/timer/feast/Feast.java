package br.com.frostmc.hardcoregames.timer.feast;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

import br.com.frostmc.common.util.itemBuilder.ItemBuilder;
import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.timer.Timer;

public class Feast {

	public static Location feastLoc = null;
	public static ArrayList<ItemChance> itens = new ArrayList<>();
	public static ArrayList<ItemChance> itensMinifeast = new ArrayList<>();
	public static boolean bausSpawned = false;
	
	public static void addChest() {
		Location locationFeast = Feast.feastLoc;
		
		Location enchantSpawn = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY() + 2, locationFeast.getZ());
		Location chestSpawn1 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY() + 2, locationFeast.getZ());
		Location chestSpawn2 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY() + 2, locationFeast.getZ());
		Location chestSpawn3 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY() + 2, locationFeast.getZ() + 1);
		Location chestSpawn4 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY() + 2, locationFeast.getZ() - 1);
		Location chestSpawn5 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2, locationFeast.getY() + 2, locationFeast.getZ() + 1);
		Location chestSpawn6 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2, locationFeast.getY() + 2, locationFeast.getZ() - 1);
		Location chestSpawn7 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY() + 2, locationFeast.getZ() + 2);
		Location chestSpawn8 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY() + 2, locationFeast.getZ() - 2);
		Location chestSpawn9 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY() + 2, locationFeast.getZ() + 2);
		Location chestSpawn10 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY() + 2, locationFeast.getZ() - 2);
		Location chestSpawn11 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2, locationFeast.getY() + 2, locationFeast.getZ() + 1);
		Location chestSpawn12 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2, locationFeast.getY() + 2, locationFeast.getZ() - 1);
		
		Block enchant = enchantSpawn.getBlock();
		Block chest1 = chestSpawn1.getBlock();
		Block chest2 = chestSpawn2.getBlock();
		Block chest3 = chestSpawn3.getBlock();
		Block chest4 = chestSpawn4.getBlock();
		Block chest5 = chestSpawn5.getBlock();
		Block chest6 = chestSpawn6.getBlock();
		Block chest7 = chestSpawn7.getBlock();
		Block chest8 = chestSpawn8.getBlock();
		Block chest9 = chestSpawn9.getBlock();
		Block chest10 = chestSpawn10.getBlock();
		Block chest11 = chestSpawn11.getBlock();
		Block chest12 = chestSpawn12.getBlock();
		
		if (chest1.getType().equals(Material.AIR) || chest2.getType().equals(Material.AIR) || chest3.getType().equals(Material.AIR) || chest4.getType().equals(Material.AIR) || chest5.getType().equals(Material.AIR) || chest6.getType().equals(Material.AIR) || chest7.getType().equals(Material.AIR) || chest8.getType().equals(Material.AIR) || chest9.getType().equals(Material.AIR) || chest10.getType().equals(Material.AIR) || chest11.getType().equals(Material.AIR) || chest12.getType().equals(Material.AIR) || enchant.getType().equals(Material.AIR)) {
			enchant.setType(Material.ENCHANTMENT_TABLE);
			chest1.setType(Material.CHEST);
			chest2.setType(Material.CHEST);
			chest3.setType(Material.CHEST);
			chest4.setType(Material.CHEST);
			chest5.setType(Material.CHEST);
			chest6.setType(Material.CHEST);
			chest7.setType(Material.CHEST);
			chest8.setType(Material.CHEST);
			chest9.setType(Material.CHEST);
			chest10.setType(Material.CHEST);
			chest11.setType(Material.CHEST);
			chest12.setType(Material.CHEST);
		}
	}
	
	public static void removeChest() {
		Location locationFeast = Feast.feastLoc;
		
		Location enchantSpawn = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY() + 2, locationFeast.getZ());
		Location chestSpawn1 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY() + 2, locationFeast.getZ());
		Location chestSpawn2 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY() + 2, locationFeast.getZ());
		Location chestSpawn3 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY() + 2, locationFeast.getZ() + 1);
		Location chestSpawn4 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY() + 2, locationFeast.getZ() - 1);
		Location chestSpawn5 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2, locationFeast.getY() + 2, locationFeast.getZ() + 1);
		Location chestSpawn6 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2, locationFeast.getY() + 2, locationFeast.getZ() - 1);
		Location chestSpawn7 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY() + 2, locationFeast.getZ() + 2);
		Location chestSpawn8 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY() + 2, locationFeast.getZ() - 2);
		Location chestSpawn9 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY() + 2, locationFeast.getZ() + 2);
		Location chestSpawn10 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY() + 2, locationFeast.getZ() - 2);
		Location chestSpawn11 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2, locationFeast.getY() + 2, locationFeast.getZ() + 1);
		Location chestSpawn12 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2, locationFeast.getY() + 2, locationFeast.getZ() - 1);
		
		Block enchant = enchantSpawn.getBlock();
		Block chest1 = chestSpawn1.getBlock();
		Block chest2 = chestSpawn2.getBlock();
		Block chest3 = chestSpawn3.getBlock();
		Block chest4 = chestSpawn4.getBlock();
		Block chest5 = chestSpawn5.getBlock();
		Block chest6 = chestSpawn6.getBlock();
		Block chest7 = chestSpawn7.getBlock();
		Block chest8 = chestSpawn8.getBlock();
		Block chest9 = chestSpawn9.getBlock();
		Block chest10 = chestSpawn10.getBlock();
		Block chest11 = chestSpawn11.getBlock();
		Block chest12 = chestSpawn12.getBlock();
		
		if (enchant.getType().equals(Material.ENCHANTMENT_TABLE) || chest1.getType().equals(Material.CHEST) || chest2.getType().equals(Material.CHEST) || chest3.getType().equals(Material.CHEST) || chest4.getType().equals(Material.CHEST) || chest5.getType().equals(Material.CHEST) || chest6.getType().equals(Material.CHEST) || chest7.getType().equals(Material.CHEST) || chest8.getType().equals(Material.CHEST) || chest9.getType().equals(Material.CHEST) || chest10.getType().equals(Material.CHEST) || chest11.getType().equals(Material.CHEST) || chest12.getType().equals(Material.CHEST)) {
			enchant.setType(Material.AIR);
			chest1.setType(Material.AIR);
			chest2.setType(Material.AIR);
			chest3.setType(Material.AIR);
			chest4.setType(Material.AIR);
			chest5.setType(Material.AIR);
			chest6.setType(Material.AIR);
			chest7.setType(Material.AIR);
			chest8.setType(Material.AIR);
			chest9.setType(Material.AIR);
			chest10.setType(Material.AIR);
			chest11.setType(Material.AIR);
			chest12.setType(Material.AIR);
		}
	}
	
	public static void spawnFeast(Location location) {
		FrostHG.getManager().createArena(location, 18, Material.GRASS, 0);
		Location location2 = new Location(location.getWorld(), location.getX(), location.getY(), location.getX());
		FrostHG.getManager().createArena(location2, 30, Material.AIR, 120);
	}
	
	public static void prepararFeast() {
		int x = getCoord(100), z = getCoord(200);
		feastLoc = new Location(Bukkit.getWorld("world"), x, Bukkit.getWorld("world").getHighestBlockYAt(x, z), z);
		spawnFeast(feastLoc);
	}

	public static void preencherBaus() {
		if (feastLoc == null)
			return;
		
		Location locationFeast = Feast.feastLoc;
		
		Location chestSpawn1 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY() + 2, locationFeast.getZ());
		Location chestSpawn2 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY() + 2, locationFeast.getZ());
		Location chestSpawn3 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY() + 2, locationFeast.getZ() + 1);
		Location chestSpawn4 = new Location(locationFeast.getWorld(), locationFeast.getX(), locationFeast.getY() + 2, locationFeast.getZ() - 1);
		Location chestSpawn5 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2, locationFeast.getY() + 2, locationFeast.getZ() + 1);
		Location chestSpawn6 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2, locationFeast.getY() + 2, locationFeast.getZ() - 1);
		Location chestSpawn7 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY() + 2, locationFeast.getZ() + 2);
		Location chestSpawn8 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY() + 2, locationFeast.getZ() - 2);
		Location chestSpawn9 = new Location(locationFeast.getWorld(), locationFeast.getX() - 1, locationFeast.getY() + 2, locationFeast.getZ() + 2);
		Location chestSpawn10 = new Location(locationFeast.getWorld(), locationFeast.getX() + 1, locationFeast.getY() + 2, locationFeast.getZ() - 2);
		Location chestSpawn11 = new Location(locationFeast.getWorld(), locationFeast.getX() - 2, locationFeast.getY() + 2, locationFeast.getZ() + 1);
		Location chestSpawn12 = new Location(locationFeast.getWorld(), locationFeast.getX() + 2, locationFeast.getY() + 2, locationFeast.getZ() - 1);
		
		Block chest1 = chestSpawn1.getBlock();
		Block chest2 = chestSpawn2.getBlock();
		Block chest3 = chestSpawn3.getBlock();
		Block chest4 = chestSpawn4.getBlock();
		Block chest5 = chestSpawn5.getBlock();
		Block chest6 = chestSpawn6.getBlock();
		Block chest7 = chestSpawn7.getBlock();
		Block chest8 = chestSpawn8.getBlock();
		Block chest9 = chestSpawn9.getBlock();
		Block chest10 = chestSpawn10.getBlock();
		Block chest11 = chestSpawn11.getBlock();
		Block chest12 = chestSpawn12.getBlock();
		if (chest1.getType().equals(Material.CHEST) || chest2.getType().equals(Material.CHEST) || chest3.getType().equals(Material.CHEST) || chest4.getType().equals(Material.CHEST) || chest5.getType().equals(Material.CHEST) || chest6.getType().equals(Material.CHEST) || chest7.getType().equals(Material.CHEST) || chest8.getType().equals(Material.CHEST) || chest9.getType().equals(Material.CHEST) || chest10.getType().equals(Material.CHEST) || chest11.getType().equals(Material.CHEST) || chest12.getType().equals(Material.CHEST)) {
			addChestItems((Chest) chest1.getLocation().getBlock().getState());
			addChestItems((Chest) chest2.getLocation().getBlock().getState());
			addChestItems((Chest) chest3.getLocation().getBlock().getState());
			addChestItems((Chest) chest4.getLocation().getBlock().getState());
			addChestItems((Chest) chest5.getLocation().getBlock().getState());
			addChestItems((Chest) chest6.getLocation().getBlock().getState());
			addChestItems((Chest) chest7.getLocation().getBlock().getState());
			addChestItems((Chest) chest8.getLocation().getBlock().getState());
			addChestItems((Chest) chest9.getLocation().getBlock().getState());
			addChestItems((Chest) chest10.getLocation().getBlock().getState());
			addChestItems((Chest) chest11.getLocation().getBlock().getState());
			addChestItems((Chest) chest12.getLocation().getBlock().getState());
		}
	}

	public static void addChestItems(Chest chest) {
		for (int i = 0; i < itens.size(); i++) {
			if (new Random().nextInt(100) < itens.get(i).getChance()) {
				int slot = new Random().nextInt(chest.getBlockInventory().getSize());
				chest.getBlockInventory().setItem(slot, itens.get(i).getItem());
			}
		}
		chest.update();
	}

	public static int getCoord(int range) {
		return new Random().nextBoolean() ? new Random().nextInt(range - 13) : -new Random().nextInt(range + 17);
	}

	public static void sendFeastMessage() {
		if (feastLoc == null)
			return;

		int tempo = 1200 - Timer.tempo;
		if (tempo > 60) {
			Timer.sendMSG("§6§lPARTIDA §fO Feast irá spawnar em §6" + tempo / 60 + " minutos. §f(§6" + feastLoc.getBlockX() + "§f, §6" + feastLoc.getBlockY() + "§f, §6" + feastLoc.getBlockZ() + "§f) Digite /feast para apontar a bússola.");
		} else if (tempo == 60) {
			Timer.sendMSG("§6§lPARTIDA §fO Feast irá spawnar em §6" + tempo / 60 + " minuto. §f(§6" + feastLoc.getBlockX() + "§f, §6" + feastLoc.getBlockY() + "§f, §6" + feastLoc.getBlockZ() + "§f) Digite /feast para apontar a bússola.");
		} else if (tempo < 60 && tempo > 1) {
			Timer.sendMSG("§6§lPARTIDA §fO Feast irá spawnar em §6" + tempo + " segundos. §f(§6" + feastLoc.getBlockX() + "§f, §6" + feastLoc.getBlockY() + "§f, §6" + feastLoc.getBlockZ() + "§f) Digite /feast para apontar a bússola.");
		} else if (tempo == 1) {
			Timer.sendMSG("§6§lPARTIDA §fO Feast irá spawnar em §6" + tempo + " segundo. §f(§6" + feastLoc.getBlockX() + "§f, §6" + feastLoc.getBlockY() + "§f, §6" + feastLoc.getBlockZ() + "§f) Digite /feast para apontar a bússola.");
		} else if (tempo == 0) {
			Timer.sendMSG("§6§lPARTIDA §fO Feast spawnou em (§6" + feastLoc.getBlockX() + "§f, §6" + feastLoc.getBlockY() + "§f, §6" + feastLoc.getBlockZ() + "§f) Digite /feast para apontar a bússola.");
		}
	}

	public static void addItensChance() {
		for (String código : FeastConfig.getBausConfig().getStringList("Feast.Itens")) {
			String separador[] = código.split(",");
			String material = separador[0].replace("Material:", "");
			int quantidade = Integer.valueOf(separador[1].replace("Quantidade:", ""));
			int durabilidade = Integer.valueOf(separador[2].replace("Durabilidade:", ""));
			int chance = Integer.valueOf(separador[3].replace("Chance:", ""));

			Material m = null;
			try {
				m = Material.getMaterial(material);
			} catch (NullPointerException e) {
				Bukkit.getConsoleSender().sendMessage("§cErro, Material: " + material + " não encontrado.");
			}

			if (m == null)
				continue;

			itens.add(new ItemChance(new ItemBuilder().setMaterial(m).setDurabilidade(durabilidade).finalizar(), chance, (quantidade == 1 ? 0 : quantidade)));
		}
	}
}