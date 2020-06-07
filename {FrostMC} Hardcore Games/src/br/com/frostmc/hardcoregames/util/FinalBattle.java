package br.com.frostmc.hardcoregames.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.commands.staffer.ArenaCommand;
import br.com.frostmc.hardcoregames.timer.Timer;

public class FinalBattle {
	
	public static void sendArenaFinalMessage() {
		if (!FrostHG.getManager().arenaFinal)
			return;
		
		int tempo = 2740 - Timer.tempo;
		if (tempo > 60) {
			Timer.sendMSG("§6§lPARTIDA §fA arena final irá spawnar em §6" + tempo / 60 + " minutos.");
		} else if (tempo == 60) {
			Timer.sendMSG("§6§lPARTIDA §fA arena final irá spawnar em §6" + tempo / 60 + " minuto.");
		} else if (tempo < 60 && tempo > 1) {
			Timer.sendMSG("§6§lPARTIDA §fA arena final irá spawnar em §6" + tempo + " segundos.");
		} else if (tempo == 1) {
			Timer.sendMSG("§6§lPARTIDA §fA arena final irá spawnar em §6" + tempo + " segundo.");
		} else if (tempo == 0) {
			Timer.sendMSG("§6§lPARTIDA §fA arena final spawnou.");
		}
	}
	
	public static void sendFinalKillsMessage() {
		if (!FrostHG.getManager().arenaFinal)
			return;
		
		int tempo = 3000 - Timer.tempo;
		if (tempo > 60) {
			Timer.sendMSG("§6§lPARTIDA §fA partida irá acabar em §6" + tempo / 60 + " minutos.");
		} else if (tempo == 60) {
			Timer.sendMSG("§6§lPARTIDA §fA partida irá acabar em §6" + tempo / 60 + " minuto.");
		} else if (tempo < 60 && tempo > 1) {
			Timer.sendMSG("§6§lPARTIDA §fA partida irá acabar em §6" + tempo + " segundos.");
		} else if (tempo == 1) {
			Timer.sendMSG("§6§lPARTIDA §fA partida irá acabar em §6" + tempo + " segundo.");
		} else if (tempo == 0) {
			Timer.sendMSG("§6§lPARTIDA §fA partida acabou.");
		}
	}
	
	public static void giveArena(Location loc, int r, Material mat, int alturaY) {
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();
		World w = loc.getWorld();
		int rSquared = r * r;
		for (int x = cx - r; x <= cx + r; x++) {
			for (int z = cz - r; z <= cz + r; z++) {
				for (int y = cy + 1; y <= cy + alturaY; y++) {
					if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
						w.getBlockAt(x, y, z).setType(mat);
					}
				}
			}
		}
	}

	public static void createArena() {
		int x = FrostHG.getManager().getSpawn().getBlockX(), z = FrostHG.getManager().getSpawn().getBlockZ();
		int y = FrostHG.getManager().getSpawn().getBlockY();
		World world = Bukkit.getWorld("world");
		Location location = new Location(world, x, y, z);
		
		for (Player players : FrostHG.getManager().getOnlinePlayers()) {
    		ArenaCommand.criarArena(location, 30, 15);
			players.teleport(new Location(world, x, y+1, z));
		}
	}

	public static void removeEntities() {
		for (Entity e : FrostHG.getManager().getSpawn().getWorld().getEntities()) {
			if (!(e instanceof Player)) {
				e.remove();
			}
		}
	}
}
