package br.com.frostmc.gladiator.battle1v1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.frostmc.gladiator.FrostGladiator;
import br.com.frostmc.gladiator.scoreboard.Scoreboarding;
import br.com.frostmc.gladiator.util.jnbt.Schematic;
import br.com.frostmc.gladiator.util.protection.Protection;

public class GladiatorGenerator implements Listener {

	public static boolean generateGlass = true;
	public static HashMap<Player, Location> locationGlad = new HashMap<Player, Location>();
	public static HashMap<String, String> emluta = new HashMap<String, String>();
	public static HashMap<Player, Location> localizacao = new HashMap<Player, Location>();
	public static HashMap<Player, Block> block = new HashMap<Player, Block>();
	public static HashMap<Integer, String[]> players = new HashMap<Integer, String[]>();

	public static int nextID = 0;

	private static Location ponto_baixo;
	private static Location ponto_alto;
	
	public static Object spawnArena(Player play1, Player play2, Location l, Material mat) {
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		Location loc = new Location(play1.getWorld(), x, y + 120, z);
		Integer currentID = Integer.valueOf(nextID);
		nextID += 1;
		ArrayList<String> list = new ArrayList<String>();
		loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
		players.put(currentID, (String[]) list.toArray(new String[1]));
		if (generateGlass) {
			Random rs = new Random();
			x = rs.nextInt(5000);
			y = rs.nextInt(120);
			z = rs.nextInt(1000);
			Location lk = new Location(Bukkit.getWorld("gladiatores"), x, y, z);
			locationGlad.put(play1, lk);
			locationGlad.put(play2, lk);
			try {
				Schematic sch = Schematic.getInstance().carregarSchematics(new File(FrostGladiator.getInstance().getDataFolder(), "gladiator.schematic"));
				Schematic.getInstance().generateSchematic(Bukkit.getWorld("gladiatores"), lk, sch);
			} catch (Exception e1) {
				Bukkit.getConsoleSender().sendMessage("§cBUG!!");
				e1.printStackTrace();
			}
			
			play1.teleport(new Location(Bukkit.getWorld("gladiatores"), x+1.5D, y+1.5D, z+1.5D));
			play1.getLocation().setYaw(134);
			play2.teleport(new Location(Bukkit.getWorld("gladiatores"), x+15D, y+1.5D, z+15D));
			play2.getLocation().setYaw(44);
			Scoreboarding.setScoreboard(play1);
			Scoreboarding.setScoreboard(play2);
	         
			Location pos1 = lk.clone().add(1.5D, 13, 1.5D);
			Location pos2 = lk.clone().add(15D, 1, 15D);
			
			ponto_baixo = pos2;
	        ponto_alto = pos1;
			
			new BukkitRunnable() {
				public void run() {
					for (Block b : getBlocks(ponto_baixo, ponto_alto))
						 b.setType(Material.AIR);
				}
			}.runTaskTimer(FrostGladiator.getInstance(), 0L, 240*20);
		}

		List<Location> cuboid2 = new ArrayList<Location>();
		int bZ2;
		for (int bX = -10; bX <= 10; bX++) {
			for (bZ2 = -10; bZ2 <= 10; bZ2++) {
				for (int bY = -1; bY <= 10; bY++) {
				}
				for (Location loc1 : cuboid2) {
					loc1.getBlock().setType(Material.AIR);
				}
			}

		}
		
		localizacao.put(play1, loc);
		localizacao.put(play2, loc);

		return null;

	}
	
	public static List<Location> getLocationsFromTwoPoints(Location location1, Location location2) {
		List<Location> locations = new ArrayList<>();
		int topBlockX = (location1.getBlockX() < location2.getBlockX() ? location2.getBlockX() : location1.getBlockX());
		int bottomBlockX = (location1.getBlockX() > location2.getBlockX() ? location2.getBlockX() : location1.getBlockX());
		int topBlockY = (location1.getBlockY() < location2.getBlockY() ? location2.getBlockY() : location1.getBlockY());
		int bottomBlockY = (location1.getBlockY() > location2.getBlockY() ? location2.getBlockY() : location1.getBlockY());
		int topBlockZ = (location1.getBlockZ() < location2.getBlockZ() ? location2.getBlockZ() : location1.getBlockZ());
		int bottomBlockZ = (location1.getBlockZ() > location2.getBlockZ() ? location2.getBlockZ() : location1.getBlockZ());
		for (int x = bottomBlockX; x <= topBlockX; x++)
		for (int z = bottomBlockZ; z <= topBlockZ; z++) 
		for (int y = bottomBlockY; y <= topBlockY; y++) 
		     locations.add(new Location(location1.getWorld(), x, y, z));
		return locations;
	}
	
	public static List<Block> getBlocks(Location location1, Location location2) {
		List<Block> blocks = new ArrayList<>();
		for (Location loc : getLocationsFromTwoPoints(location1, location2)) {
			 Block b = loc.getBlock();
			 if (!b.getType().equals(Material.AIR))
			     blocks.add(b);
		}
		return blocks;
	}


	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlyaerInteract(PlayerInteractEvent e) {
		if ((e.getAction() == Action.LEFT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.GLASS) && (e.getPlayer().getGameMode() != GameMode.CREATIVE) && (emluta.containsKey(e.getPlayer().getName()))) {
			e.setCancelled(true);
			e.getClickedBlock().setType(Material.BEDROCK);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FrostGladiator.getInstance(), new Runnable() {
				public void run() {
					if (GladiatorGenerator.emluta.containsKey(e.getPlayer().getName())) {
						e.getClickedBlock().setType(Material.GLASS);
					}
				}
			}, 100L);
		}
	}

	public static void removeArena(Player p) {
		Protection.setProtection(p);
		if (locationGlad.containsKey(p)) {
			try {
				Schematic sch = Schematic.getInstance().carregarSchematics(new File(FrostGladiator.getInstance().getDataFolder(), "glad_air.schematic"));
				Schematic.getInstance().generateSchematic(Bukkit.getWorld("gladiatores"), locationGlad.get(p), sch);
			} catch (Exception e1) {
				Bukkit.getConsoleSender().sendMessage("§cBUG!!");
				e1.printStackTrace();
			}
		} else {
			return;
		}
	}

	public static String getPlayerLuta(Player p) {
		String p2 = emluta.get(p.getName());
		Player play2 = Bukkit.getPlayer(p2);
		if (play2 == null) {
			return "Jogandor não esta em luta";
		}
		return play2.getName();

	}

	public static void SpawnRandomBattle(Player p, Player p2, int wither, int glass) {
		@SuppressWarnings("deprecation")
		ItemStack icone = new ItemStack(351, 64, (short) 3);
		ItemMeta iconem = icone.getItemMeta();
		iconem.setDisplayName("");
		
		p.getInventory().clear();
		p2.getInventory().clear();
		
		if (glass == 0) {
			GladiatorGenerator.spawnArena(p, p2, new Location(Bukkit.getWorld("world"), 1000, 190, 1000), Material.GLASS);
		}
		if (glass == 1) {
			GladiatorGenerator.spawnArena(p, p2, new Location(Bukkit.getWorld("world"), 1000, 190, 1000), Material.BEDROCK);
		}
		if (glass == 2) {
			GladiatorGenerator.spawnArena(p, p2, new Location(Bukkit.getWorld("world"), 1000, 190, 1000), Material.LEAVES);
		}

		if (wither == 1) {
			while (GladiatorGenerator.emluta.containsKey(p.getName()) && emluta.containsKey(p2.getName())) {
				addPotionEffect(p, 20 * 1 * 60);
			}
		}

		if (wither == 3) {
			while (GladiatorGenerator.emluta.containsKey(p.getName()) && emluta.containsKey(p2.getName())) {
				addPotionEffect(p, 20 * 1 * 60);
			}
		}

		if (wither == 5) {
			while (GladiatorGenerator.emluta.containsKey(p.getName()) && emluta.containsKey(p2.getName())) {
				addPotionEffect(p, 20 * 1 * 60);
			}
		}
		
		emluta.put(p.getName(), p2.getName());
		emluta.put(p2.getName(), p.getName());

	}

	private static void addPotionEffect(Player p, int timer) {

		new BukkitRunnable() {

			@Override
			public void run() {

				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 9999, 1), true);

			}
		}.runTaskLater(FrostGladiator.getInstance(), timer);

	}

}
