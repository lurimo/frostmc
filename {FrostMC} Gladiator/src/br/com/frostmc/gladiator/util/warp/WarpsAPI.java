package br.com.frostmc.gladiator.util.warp;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import br.com.frostmc.gladiator.FrostGladiator;

public class WarpsAPI implements Listener{
	
	public static WarpsAPI pasta = new WarpsAPI();
	
	public static File fWarps;
	public static FileConfiguration kWarps;
	
	public static WarpsAPI getConfig() {
		return pasta;
	}
	
	public WarpsAPI() {
		Plugin plugin = FrostGladiator.getInstance();
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
		SPAWN;
	}
	
	public static HashMap<Player, Warps> Warp = new HashMap<>();
	
	public static void setWarp(Player p, Warps warp) {
		Warp.put(p, warp);
	}
	
	public static Warps getPlayerWarp(Player p) {
		return Warp.get(p);
	}
	
	public static boolean isInWarp(Player p, Warps warp) {
		Warps playerwarp = (Warps)Warp.get(p);
		return warp.equals(playerwarp);
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
	
	public static Double getInConfigLocationDoubleY(String Warp) {
		return WarpsAPI.getConfig().config().getDouble("Warps." + Warp.toUpperCase() + ".Y");
	}
	
	public static int getInConfigLocationIntY(String Warp) {
		return WarpsAPI.getConfig().config().getInt("Warps." + Warp.toUpperCase() + ".Y");
	}
	
	public static void save() {
		try {
			kWarps.save(fWarps);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
