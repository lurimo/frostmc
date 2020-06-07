package br.com.frostmc.hardcoregames.util.kit.hability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import br.com.frostmc.hardcoregames.FrostHG;
import br.com.frostmc.hardcoregames.util.admin.AdminManager;

public class Madman implements Listener {
	
	public static HashMap<UUID, Integer> efeitoMadman = new HashMap<>();
	public static ArrayList<UUID> madmans = new ArrayList<>();
	
	public static void check() {
		
		if (madmans.size() != 0) {
			for (UUID uuidMadmans : madmans) {
				 Player madman = Bukkit.getPlayer(uuidMadmans);
				 if (madman == null || !madman.isOnline()) {
					 continue;
				 }
			     List<Player> lista = getNearbyPlayers(madman, 15);
			     if (lista.size() < 2) {
				     continue;
			     }
			     for (Player perto : lista) {
				      int efeito = lista.size() * 2;
				      addEffect(perto.getUniqueId(), efeito);
			     }
			 }
		 }
		
		if (efeitoMadman.size() != 0) {
			for (UUID uuidInfectados : efeitoMadman.keySet()) {
				 removeEffect(uuidInfectados);
			}
		}
	}
	
	private static void removeEffect(UUID u) {
		int effect = efeitoMadman.get(u);
		effect = effect - 10;
		efeitoMadman.put(u, effect);
		if (effect <= 0) {
			efeitoMadman.remove(u);
		}
	}

	private static void addEffect(UUID u, int efeito) {
		int effect = (efeitoMadman.containsKey(u) ? efeitoMadman.get(u) : 0);
		if (effect == 0) {
			if (Bukkit.getPlayer(u) != null) {
				Bukkit.getPlayer(u).sendMessage("§4§lMADMAN §fAtenção tem um jogador com o kit §4§lMADMAN §fpor perto.");
			}
		}
		effect = effect + (efeito + 10);
		efeitoMadman.put(u, effect);
	}
	
	private static List<Player> getNearbyPlayers(Player p, int i) {
		List<Player> players = new ArrayList<Player>();
		List<Entity> entities = p.getPlayer().getNearbyEntities(i, i, i);
		for (Entity e : entities) {
			if (!(e instanceof Player)) {
				continue;
			}
			if (FrostHG.getManager().getEspectadores().contains(e.getUniqueId())) {
				continue;
			}
			if (AdminManager.isAdmin(((Player)e))) {
				continue;
			}
			players.add((Player) e);
		}
		return players;
	}

}
