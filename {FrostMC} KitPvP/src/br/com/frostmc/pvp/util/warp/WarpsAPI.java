package br.com.frostmc.pvp.util.warp;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import br.com.frostmc.pvp.FrostPvP;

public class WarpsAPI implements Listener{
	
	public static WarpsAPI pasta = new WarpsAPI();
	
	public static File fWarps;
	public static FileConfiguration kWarps;
	
	public static WarpsAPI getConfig() {
		return pasta;
	}
	
	public WarpsAPI() {
		Plugin plugin = FrostPvP.getPlugin();
		if(!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		fWarps = new File(plugin.getDataFolder(), "warps.yml");
		if(fWarps.exists()) {
			try {
				fWarps.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		kWarps = YamlConfiguration.loadConfiguration(fWarps);
	}
	
	public FileConfiguration config() {
		return kWarps;
	}
	
	public enum Warps {
		SPAWN, ARENA, FPS, LAVACHALLENGE, ONEVSONE;
	}
	
	public enum Locais {
		ARENA1, ARENA2, ARENA3, ARENA4, ARENA5;
	}
	
	public static HashMap<Player, Warps> Warp = new HashMap<>();
	public static HashMap<Player, Locais> Local = new HashMap<>();
	
	public static void setWarp(Player p, Warps warp) {
		Warp.put(p, warp);
	}
	
	public static Warps getPlayerWarp(Player p) {
		return Warp.get(p);
	}
	
	public static String getWarp(Player p) {
		if(isInWarp(p, Warps.ARENA)) {
			return "Arena";
		}
		if(isInWarp(p, Warps.FPS)) {
			return "FPS";
		}
		if(isInWarp(p, Warps.LAVACHALLENGE)) {
			return "LavaChallenge";
		}
		if(isInWarp(p, Warps.ONEVSONE)) {
			return "1v1";
		}
		if(isInWarp(p, Warps.SPAWN)) {
			return "Spawn";
		}
		return "Spawn";
	}
	
	public static void goToArena(Player player) {
		int random = new Random().nextInt(5);
		switch(random) {
		case 0:
			goToWarpArena(player, Locais.ARENA1);
			break;
		case 1:
			goToWarpArena(player, Locais.ARENA1);
			break;
		case 2:
			goToWarpArena(player, Locais.ARENA2);
			break;
		case 3:
			goToWarpArena(player, Locais.ARENA3);
			break;
		case 4:
			goToWarpArena(player, Locais.ARENA4);
			break;
		case 5:
			goToWarpArena(player, Locais.ARENA5);
			break;
		}
	}
	
	public static boolean isInWarp(Player p, Warps warp) {
		Warps playerwarp = (Warps)Warp.get(p);
		return warp.equals(playerwarp);
	}
	
	public static boolean isInWarp(Player p, Locais local) {
		Locais playerwarp = (Locais)Local.get(p);
		return local.equals(playerwarp);
	}
	
	public static Location getLocationFromConfig(String config) {
		FileConfiguration file = kWarps;
		Location location = new Location(Bukkit.getWorld("world"), file.getDouble(config + ".x"), file.getDouble(config + ".y"), file.getDouble(config + ".z"));

		location.setPitch(file.getLong(config + ".pitch"));
		location.setYaw(file.getLong(config + ".yaw"));

		return location;
	}
	
	public static void registerInConfig(Player player, String config) {
		FileConfiguration file = FrostPvP.getPlugin().getConfig();
		Location location = player.getLocation();

		file.set(config + ".x", location.getX());
		file.set(config + ".y", location.getY());
		file.set(config + ".z", location.getZ());
		file.set(config + ".pitch", location.getPitch());
		file.set(config + ".yaw", location.getYaw());

		FrostPvP.getPlugin().saveConfig();
	}
	
	public static void setWarpLocation(Player p, Warps warp) {
		kWarps.set("Warps." + warp.toString().toUpperCase() + ".X", p.getLocation().getX());
		kWarps.set("Warps." + warp.toString().toUpperCase() + ".Y", p.getLocation().getY());
		kWarps.set("Warps." + warp.toString().toUpperCase() + ".Z", p.getLocation().getZ());
		kWarps.set("Warps." + warp.toString().toUpperCase() + ".Pitch", p.getLocation().getPitch());
		kWarps.set("Warps." + warp.toString().toUpperCase() + ".Yaw", p.getLocation().getYaw());
		kWarps.set("Warps." + warp.toString().toUpperCase() + ".World", p.getLocation().getWorld().getName());
		save();
	}
	
	public static void setWarpLocation(Player p, Locais local) {
		kWarps.set("Warps." + local.toString().toUpperCase() + ".X", p.getLocation().getX());
		kWarps.set("Warps." + local.toString().toUpperCase() + ".Y", p.getLocation().getY());
		kWarps.set("Warps." + local.toString().toUpperCase() + ".Z", p.getLocation().getZ());
		kWarps.set("Warps." + local.toString().toUpperCase() + ".Pitch", p.getLocation().getPitch());
		kWarps.set("Warps." + local.toString().toUpperCase() + ".Yaw", p.getLocation().getYaw());
		kWarps.set("Warps." + local.toString().toUpperCase() + ".World", p.getLocation().getWorld().getName());
		save();
	}
	
	public static void goToWarp(Player p, Warps warp) {
		if(kWarps.contains("Warps." + warp.toString().toUpperCase())) {
			double X = kWarps.getDouble("Warps." + warp.toString().toUpperCase() + ".X");
			double Y = kWarps.getDouble("Warps." + warp.toString().toUpperCase() + ".Y");
			double Z = kWarps.getDouble("Warps." + warp.toString().toUpperCase() + ".Z");
			float Pitch = (float)kWarps.getDouble("Warps." + warp.toString().toUpperCase() + ".Pitch");
			float Yaw = (float)kWarps.getDouble("Warps." + warp.toString().toUpperCase() + ".Yaw");
			World World = Bukkit.getWorld(kWarps.getString("Warps." + warp.toString().toUpperCase() + ".World"));
			Location loc = new Location(World, X, Y, Z, Yaw, Pitch);
			p.teleport(loc);
		} else {
			p.sendMessage("�c�lERRO �fEssa warp ainda n�o foi setada.");
		}
	}
	
	public static void goToWarp(Player p, Locais local) {
		if(kWarps.contains("Warps." + local.toString().toUpperCase())) {
			double X = kWarps.getDouble("Warps." + local.toString().toUpperCase() + ".X");
			double Y = kWarps.getDouble("Warps." + local.toString().toUpperCase() + ".Y");
			double Z = kWarps.getDouble("Warps." + local.toString().toUpperCase() + ".Z");
			float Pitch = (float)kWarps.getDouble("Warps." + local.toString().toUpperCase() + ".Pitch");
			float Yaw = (float)kWarps.getDouble("Warps." + local.toString().toUpperCase() + ".Yaw");
			World World = Bukkit.getWorld(kWarps.getString("Warps." + local.toString().toUpperCase() + ".World"));
			Location loc = new Location(World, X, Y, Z, Yaw, Pitch);
			p.teleport(loc);
		} else {
			p.sendMessage("�c�lERRO �fEsse local ainda n�o foi setado.");
		}
	}

	public static void goToWarpArena(Player p, Locais local) {
		Random r = new Random();
		int x = r.nextInt(5);
		int y = r.nextInt(5);
		int z = r.nextInt(5);
		if(kWarps.contains("Warps." + local.toString().toUpperCase())) {
			int X = kWarps.getInt("Warps." + local.toString().toUpperCase() + ".X");
			int Y = kWarps.getInt("Warps." + local.toString().toUpperCase() + ".Y");
			int Z = kWarps.getInt("Warps." + local.toString().toUpperCase() + ".Z");
			float Pitch = (float)kWarps.getDouble("Warps." + local.toString().toUpperCase() + ".Pitch");
			float Yaw = (float)kWarps.getDouble("Warps." + local.toString().toUpperCase() + ".Yaw");
			World World = Bukkit.getWorld(kWarps.getString("Warps." + local.toString().toUpperCase() + ".World"));
			Location loc = getRespawnLocation(World, X + x, Y + y, Z + z, Yaw, Pitch);
			p.teleport(loc);
		} else {
			p.sendMessage("�c�lERRO �fEsse local ainda n�o foi setado.");
		}
	}
	
	public static Location getRespawnLocation(World world, int xLocation, int yLocation, int zLocation, float Yaw, float Pitch) {
		Location loc = new Location(world, xLocation + 0.500, yLocation + 1.500, zLocation + 0.500, Yaw, Pitch);
		return loc;
	}
	
	public static Double getInConfigLocationDoubleX(String Warp) {
		return WarpsAPI.getConfig().config().getDouble("Warps." + Warp.toUpperCase() + ".X");
	}
	
	public static int getInConfigLocationIntX(String Warp) {
		return WarpsAPI.getConfig().config().getInt("Warps." + Warp.toUpperCase() + ".X");
	}
	
	public static int getInConfigLocationIntY(String Warp) {
		return WarpsAPI.getConfig().config().getInt("Warps." + Warp.toUpperCase() + ".Y");
	}
	
	public static int getInConfigLocationIntZ(String Warp) {
		return WarpsAPI.getConfig().config().getInt("Warps." + Warp.toUpperCase() + ".Z");
	}
	
	public static void save() {
		try {
			kWarps.save(fWarps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
