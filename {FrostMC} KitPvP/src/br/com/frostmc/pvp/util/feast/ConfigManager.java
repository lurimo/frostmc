package br.com.frostmc.pvp.util.feast;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import br.com.frostmc.pvp.FrostPvP;

public class ConfigManager {
	
	public String getConfig(String obj) {
		return FrostPvP.getPlugin().getConfig().getString("Mysql." + obj);
	}

	/* Config Methods */

	public void registerInConfig(Player player, String config) {
		FileConfiguration file = FrostPvP.getPlugin().getConfig();
		Location location = player.getLocation();

		file.set(config + ".x", location.getX());
		file.set(config + ".y", location.getY());
		file.set(config + ".z", location.getZ());
		file.set(config + ".pitch", location.getPitch());
		file.set(config + ".yaw", location.getYaw());

		FrostPvP.getPlugin().saveConfig();
	}
	
	public void registerInConfig(Object where, Object toSet) {
		FileConfiguration file = FrostPvP.getPlugin().getConfig();
		file.set(String.valueOf(where), String.valueOf(toSet));
		FrostPvP.getPlugin().saveConfig();
	}

	public Location getLocationFromConfig(String config) {
		FileConfiguration file = FrostPvP.getPlugin().getConfig();
		Location location = new Location(Bukkit.getWorld("world"), file.getDouble(config + ".x"), file.getDouble(config + ".y"), file.getDouble(config + ".z"));

		location.setPitch(file.getLong(config + ".pitch"));
		location.setYaw(file.getLong(config + ".yaw"));

		return location;
	}

	public void teleportPlayer(Player player, String config) {
		FileConfiguration file = FrostPvP.getPlugin().getConfig();

		if (!file.contains(config + ".x"))
			return;

		player.teleport(getLocationFromConfig(config));
	}

	public int getMaxId(String config, int start) {
		FileConfiguration file = FrostPvP.getPlugin().getConfig();
		int max = 0;

		for (int i = start; i < 100; i++)
			if (file.contains(config + i))
				max = i;

		return max;
	}

}
