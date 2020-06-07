package br.com.frostmc.login.util.holograms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import br.com.frostmc.common.BukkitMain;
import br.com.frostmc.common.util.holograms.Hologram;
import br.com.frostmc.login.util.npc.NpcManager;

public class HologramManager {
	
	public static int integer = 0;
	
	@SuppressWarnings("deprecation")
	public void next() {
		integer++;
		if (integer == 30) {
			setHolograms();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!BukkitMain.getPermissions().isModPlus(player)) {
					player.sendMessage("§a§lHOLOGRAMA §fOs hologramas foram atualizados!");
					return;
				}
			}
			integer = 0;
		}
	}

	public Hologram onEntryHologram, mob_zombie_Hologram;

	public boolean initialize() {
		return updateHolograms();
	}

	public boolean updateHolograms() {
		return setHolograms();
	}
	
	@SuppressWarnings("deprecation")
	public boolean setHolograms() {
		if (onEntryHologram != null) {
			onEntryHologram.remove();
			onEntryHologram = null;
		}
		
		Location locationOnEntry = new Location(Bukkit.getServer().getWorld("world"), -4.5, 129, -1.5);
		onEntryHologram = new Hologram("§b§lFrost§b§lNetwork", locationOnEntry, true);
		onEntryHologram.addLine(" ");
		onEntryHologram.addLine("§fOlá, seja bem-vindo(a)!");
		onEntryHologram.addLine("§fLogue-se para ter acesso total ao servidor.");
		onEntryHologram.addLine("§fApós se logar, utilize a bússola");
		onEntryHologram.addLine("§fpara acessar o lobby principal.");

		for (Player player : Bukkit.getOnlinePlayers()) {
			onEntryHologram.hide(player);
			onEntryHologram.show(player);

		}

		/////////////////////////////////
		
		if (mob_zombie_Hologram != null) {
			mob_zombie_Hologram.remove();
			mob_zombie_Hologram = null;
		}
		
		Location mob_zombie_Location = new Location(Bukkit.getServer().getWorld("world"), -9.5, 12, -1); //-4 129 -1
		mob_zombie_Hologram = new Hologram("§bLobby", mob_zombie_Location, true);
		mob_zombie_Hologram.addLine("§fClique para entrar.");
		mob_zombie_Location = new Location(Bukkit.getServer().getWorld("world"), -9.5, 12, -1.5); //-9.5 127 -1.5
		NpcManager.spawnZombie(mob_zombie_Location, "§1");
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			mob_zombie_Hologram.hide(player);
			mob_zombie_Hologram.show(player);
		}
		
		/////////////////////////////////

		return true;

	}
}